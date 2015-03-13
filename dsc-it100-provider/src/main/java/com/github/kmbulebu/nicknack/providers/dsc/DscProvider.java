package com.github.kmbulebu.nicknack.providers.dsc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import rx.Observable;
import rx.functions.Action1;
import rx.subjects.PublishSubject;

import com.github.kmbulebu.dsc.it100.ConfigurationBuilder;
import com.github.kmbulebu.dsc.it100.IT100;
import com.github.kmbulebu.dsc.it100.Labels;
import com.github.kmbulebu.dsc.it100.commands.read.ReadCommand;
import com.github.kmbulebu.dsc.it100.commands.write.StatusRequestCommand;
import com.github.kmbulebu.dsc.it100.commands.write.WriteCommand;
import com.github.kmbulebu.nicknack.core.actions.Action;
import com.github.kmbulebu.nicknack.core.actions.ActionDefinition;
import com.github.kmbulebu.nicknack.core.actions.ActionFailureException;
import com.github.kmbulebu.nicknack.core.actions.ActionParameterException;
import com.github.kmbulebu.nicknack.core.events.Event;
import com.github.kmbulebu.nicknack.core.events.EventDefinition;
import com.github.kmbulebu.nicknack.core.providers.OnEventListener;
import com.github.kmbulebu.nicknack.core.providers.Provider;
import com.github.kmbulebu.nicknack.core.providers.ProviderConfiguration;
import com.github.kmbulebu.nicknack.core.providers.settings.SettingDefinition;
import com.github.kmbulebu.nicknack.core.states.State;
import com.github.kmbulebu.nicknack.core.states.StateDefinition;
import com.github.kmbulebu.nicknack.providers.dsc.actions.AbstractDscActionDefinition;
import com.github.kmbulebu.nicknack.providers.dsc.actions.CommandOutputActionDefinition;
import com.github.kmbulebu.nicknack.providers.dsc.actions.PartitionArmAwayActionDefinition;
import com.github.kmbulebu.nicknack.providers.dsc.actions.PartitionArmNoEntryDelayActionDefinition;
import com.github.kmbulebu.nicknack.providers.dsc.actions.PartitionArmStayActionDefinition;
import com.github.kmbulebu.nicknack.providers.dsc.actions.PartitionArmWithCodeActionDefinition;
import com.github.kmbulebu.nicknack.providers.dsc.actions.PartitionDisarmActionDefinition;
import com.github.kmbulebu.nicknack.providers.dsc.events.EntryDelayInProgressEventDefinition;
import com.github.kmbulebu.nicknack.providers.dsc.events.ExitDelayInProgressEventDefinition;
import com.github.kmbulebu.nicknack.providers.dsc.events.PartitionArmedEventDefinition;
import com.github.kmbulebu.nicknack.providers.dsc.events.PartitionDisarmedEventDefinition;
import com.github.kmbulebu.nicknack.providers.dsc.events.PartitionInAlarmEventDefinition;
import com.github.kmbulebu.nicknack.providers.dsc.events.ZoneOpenCloseEventDefinition;
import com.github.kmbulebu.nicknack.providers.dsc.internal.CommandToEventMapper;
import com.github.kmbulebu.nicknack.providers.dsc.internal.CommandToStateMapper;
import com.github.kmbulebu.nicknack.providers.dsc.internal.Partition;
import com.github.kmbulebu.nicknack.providers.dsc.internal.Partitions;
import com.github.kmbulebu.nicknack.providers.dsc.internal.Zone;
import com.github.kmbulebu.nicknack.providers.dsc.internal.Zones;
import com.github.kmbulebu.nicknack.providers.dsc.settings.ActivePartitionsSettingDefinition;
import com.github.kmbulebu.nicknack.providers.dsc.settings.ActiveZonesSettingDefinition;
import com.github.kmbulebu.nicknack.providers.dsc.settings.HostSettingDefinition;
import com.github.kmbulebu.nicknack.providers.dsc.settings.PortSettingDefinition;
import com.github.kmbulebu.nicknack.providers.dsc.states.PartitionState;
import com.github.kmbulebu.nicknack.providers.dsc.states.PartitionStateDefinition;
import com.github.kmbulebu.nicknack.providers.dsc.states.ZoneState;
import com.github.kmbulebu.nicknack.providers.dsc.states.ZoneStateDefinition;

public class DscProvider implements Provider, Action1<ReadCommand> {
	
	private static final Logger LOG = LogManager.getLogger();
	
	public static final UUID PROVIDER_UUID = UUID.fromString("873b5fe3-b69d-4c80-a10e-9c04c0673776");
	
	//private Configuration configuration;
	
	private List<EventDefinition> eventDefinitions = null;
	private Map<UUID, ActionDefinition> actionDefinitions = null;
	private List<StateDefinition> stateDefinitions = null;
	private OnEventListener onEventListener = null;
	private CommandToEventMapper eventMapper;
	private CommandToStateMapper stateMapper;
	
	private Zones zones;
	private Partitions partitions;
	private Labels labels;
	
	private IT100 it100;
	
	private Set<Integer> activeZones = null;
	private Set<Integer> activePartitions = null;
	
	private final List<SettingDefinition<?>> settingDefinitions;
	private final HostSettingDefinition hostSettingDefinition;
	private final PortSettingDefinition portSettingDefinition;
	private final ActivePartitionsSettingDefinition activePartitionsSettingDefinition;
	private final ActiveZonesSettingDefinition activeZonesSettingDefinition;
	
	public DscProvider() {
		settingDefinitions = new ArrayList<SettingDefinition<?>>(1);
		hostSettingDefinition = new HostSettingDefinition();
		settingDefinitions.add(hostSettingDefinition);
		
		portSettingDefinition = new PortSettingDefinition();
		settingDefinitions.add(portSettingDefinition);
		
		activePartitionsSettingDefinition = new ActivePartitionsSettingDefinition();
		settingDefinitions.add(activePartitionsSettingDefinition);
		
		activeZonesSettingDefinition = new ActiveZonesSettingDefinition();
		settingDefinitions.add(activeZonesSettingDefinition);
	}
	
	@Override
	public UUID getUuid() {
		return PROVIDER_UUID;
	}

	@Override
	public String getName() {
		return "DSC Security System";
	}

	@Override
	public String getAuthor() {
		return "NickNack";
	}

	@Override
	public int getVersion() {
		return 1;
	}

	@Override
	public Collection<EventDefinition> getEventDefinitions() {
		return eventDefinitions;
	}

	@Override
	public Collection<ActionDefinition> getActionDefinitions() {
		if (actionDefinitions == null) {
			return null;
		} else {
			return actionDefinitions.values();
		}
	}

	@Override
	public Map<String, String> getAttributeDefinitionValues(UUID attributeDefinitionUuid) {
		return Collections.emptyMap();
	}
	
	@Override
	public List<? extends SettingDefinition<?>> getSettingDefinitions() {
		return Collections.unmodifiableList(settingDefinitions);
	}

	@Override
	public void init(ProviderConfiguration configuration, OnEventListener onEventListener) {
		this.eventDefinitions = new ArrayList<>(6);
		this.eventDefinitions.add(ZoneOpenCloseEventDefinition.INSTANCE);
		this.eventDefinitions.add(PartitionArmedEventDefinition.INSTANCE);
		this.eventDefinitions.add(PartitionInAlarmEventDefinition.INSTANCE);
		this.eventDefinitions.add(PartitionDisarmedEventDefinition.INSTANCE);
		this.eventDefinitions.add(EntryDelayInProgressEventDefinition.INSTANCE);
		this.eventDefinitions.add(ExitDelayInProgressEventDefinition.INSTANCE);
		
		this.stateDefinitions = new ArrayList<>(1);
		this.stateDefinitions.add(ZoneStateDefinition.INSTANCE);
		this.stateDefinitions.add(PartitionStateDefinition.INSTANCE);
		
		this.actionDefinitions = new HashMap<>();
		
		final String host = configuration.getValue(hostSettingDefinition);
		final Integer port = Integer.parseInt(configuration.getValue(portSettingDefinition));
		
		final List<String> activeZoneList = configuration.getValues(activePartitionsSettingDefinition);
		if (activeZoneList != null && !activeZoneList.isEmpty()) {
			activeZones = new HashSet<>();
			for (String zone : activeZoneList) {
				activeZones.add(Integer.parseInt(zone));
			}
		}
		
		final List<String> activePartitionList = configuration.getValues(activePartitionsSettingDefinition);
		if (activePartitionList != null && !activePartitionList.isEmpty()) {
			activePartitions = new HashSet<>();
			for (String partition : activePartitionList) {
				activePartitions.add(Integer.parseInt(partition));
			}
		}

		it100 = new IT100(new ConfigurationBuilder().withRemoteSocket(host, port).build());
		
		// Start communicating with IT-100.
		try {
			it100.connect();
		} catch (Exception e) {
			LOG.fatal("Could not connect to DSC IT-100: " + e.getMessage(), e);
			return;
		}
		
		final Observable<ReadCommand> readObservable = it100.getReadObservable();
		final PublishSubject<WriteCommand> writeObservable = it100.getWriteObservable();
		
		// Begin tracking zone state and request the initial state.
		zones = new Zones();
		partitions = new Partitions();
		stateMapper = new CommandToStateMapper(zones, partitions, activeZones, activePartitions);
		writeObservable.onNext(new StatusRequestCommand());
		
		// Labels gives us friendly names to our zones.
		labels = new Labels(readObservable, writeObservable);
		eventMapper = new CommandToEventMapper(labels, activeZones, activePartitions);
		
		this.onEventListener = onEventListener;
		
		this.actionDefinitions.put(CommandOutputActionDefinition.DEF_UUID, new CommandOutputActionDefinition(writeObservable));
		this.actionDefinitions.put(PartitionArmAwayActionDefinition.DEF_UUID, new PartitionArmAwayActionDefinition(writeObservable));
		this.actionDefinitions.put(PartitionArmStayActionDefinition.DEF_UUID, new PartitionArmStayActionDefinition(writeObservable));
		this.actionDefinitions.put(PartitionDisarmActionDefinition.DEF_UUID, new PartitionDisarmActionDefinition(writeObservable));
		this.actionDefinitions.put(PartitionArmNoEntryDelayActionDefinition.DEF_UUID, new PartitionArmNoEntryDelayActionDefinition(writeObservable));
		this.actionDefinitions.put(PartitionArmWithCodeActionDefinition.DEF_UUID, new PartitionArmWithCodeActionDefinition(writeObservable));
		
		// TODO Delay a reasonable amount of time before creating events.
		readObservable.ofType(ReadCommand.class).subscribe(this);
	}
	
	@Override
	public void shutdown() throws Exception {
		onEventListener = null;
		
		if (it100 != null) {
			it100.disconnect();
			it100 = null;
		}
		
		eventMapper = null;
		stateMapper = null;
		
		labels = null;
		
		activeZones = null;
		activePartitions = null;
		
		actionDefinitions = null;
		eventDefinitions = null;
		stateDefinitions = null;
	}

	@Override
	public void call(ReadCommand readCommand) {
		// FIXME Subscription comes after we call StatusRequestCommand
		stateMapper.map(readCommand);
		final Event event = eventMapper.map(readCommand);
		if (event != null) {
			onEventListener.onEvent(event);
		}
	}

	@Override
	public void run(Action action) throws ActionFailureException, ActionParameterException {
		final ActionDefinition actionDef = actionDefinitions.get(action.getAppliesToActionDefinition());
		
		if (actionDef instanceof AbstractDscActionDefinition) {
			((AbstractDscActionDefinition) actionDef).run(action);
		}
	}

	@Override
	public Collection<StateDefinition> getStateDefinitions() {
		return stateDefinitions;
	}

	@Override
	public List<State> getStates(UUID stateDefinitionUuid) {
		if (ZoneStateDefinition.INSTANCE.getUUID().equals(stateDefinitionUuid)) {
			return getZoneStates();
		} else if (PartitionStateDefinition.INSTANCE.getUUID().equals(stateDefinitionUuid)) {
			return getPartitionStates();
		} 
		return Collections.emptyList();
	}
	
	protected List<State> getZoneStates() {
		final List<State> states = new ArrayList<>();
	
		for (int i = 1; i <= 128; i++) {
			final Zone zone = zones.getZone(i);
			if (zone != null) {
				final ZoneState state = new ZoneState();
				state.setZoneNumber(i);
				state.setZoneOpen(zone.isOpen());
				state.setZoneLabel(labels.getZoneLabel(i));
				states.add(state);
			}
		}
		
		return states;
 	}
	
	protected List<State> getPartitionStates() {
		final List<State> states = new ArrayList<>();
		
		for (int i = 1; i <= 16; i++) {
			final Partition partition = partitions.getPartition(i);
			if (partition != null) {
				final PartitionState state = new PartitionState();
				state.setPartitionLabel(labels.getPartitionLabel(i));
				state.setPartitionNumber(i);
				state.setPartitionArmed(partition.isArmed());
				state.setPartitionArmedMode(partition.getArmedMode());
				states.add(state);
			}
		}
		return states;
		
	}

}
