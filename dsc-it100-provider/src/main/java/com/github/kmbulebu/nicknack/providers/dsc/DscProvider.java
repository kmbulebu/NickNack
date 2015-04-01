package com.github.kmbulebu.nicknack.providers.dsc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
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
import com.github.kmbulebu.nicknack.core.attributes.AttributeCollection;
import com.github.kmbulebu.nicknack.core.events.Event;
import com.github.kmbulebu.nicknack.core.providers.BadConfigurationException;
import com.github.kmbulebu.nicknack.core.providers.BaseProvider;
import com.github.kmbulebu.nicknack.core.providers.OnEventListener;
import com.github.kmbulebu.nicknack.core.providers.ProviderFailureException;
import com.github.kmbulebu.nicknack.core.states.State;
import com.github.kmbulebu.nicknack.core.states.StateDefinition;
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

public class DscProvider extends BaseProvider implements Action1<ReadCommand> {
	
	private static final Logger LOG = LogManager.getLogger();
	
	public static final UUID PROVIDER_UUID = UUID.fromString("873b5fe3-b69d-4c80-a10e-9c04c0673776");
	
	private OnEventListener onEventListener = null;
	private CommandToEventMapper eventMapper;
	private CommandToStateMapper stateMapper;
	
	private Zones zones;
	private Partitions partitions;
	private Labels labels;
	
	private IT100 it100;
	
	private Set<Integer> activeZones = null;
	private Set<Integer> activePartitions = null;
	
	private PublishSubject<WriteCommand> writeObservable = null;
	
	public DscProvider() {
		super(PROVIDER_UUID, 
				"DSC Security System", 
				"NickNack", 
				1, 
				new ActivePartitionsSettingDefinition(),
				new ActiveZonesSettingDefinition(),
				new HostSettingDefinition(),
				new PortSettingDefinition());
	}

	@Override
	public void init(AttributeCollection settings, OnEventListener onEventListener) throws BadConfigurationException, ProviderFailureException {
		super.init(settings, onEventListener);
		addEventDefinition(ZoneOpenCloseEventDefinition.INSTANCE);
		addEventDefinition(PartitionArmedEventDefinition.INSTANCE);
		addEventDefinition(PartitionInAlarmEventDefinition.INSTANCE);
		addEventDefinition(PartitionDisarmedEventDefinition.INSTANCE);
		addEventDefinition(EntryDelayInProgressEventDefinition.INSTANCE);
		addEventDefinition(ExitDelayInProgressEventDefinition.INSTANCE);
		
		addStateDefinition(ZoneStateDefinition.INSTANCE);
		addStateDefinition(PartitionStateDefinition.INSTANCE);
		
		addActionDefinition(new CommandOutputActionDefinition());
		addActionDefinition(new PartitionArmAwayActionDefinition());
		addActionDefinition(new PartitionArmNoEntryDelayActionDefinition());
		addActionDefinition(new PartitionArmStayActionDefinition());
		addActionDefinition(new PartitionArmWithCodeActionDefinition());
		addActionDefinition(new PartitionDisarmActionDefinition());
		
		String host;
		Integer port;
		Integer[] activeZonesSetting;
		Integer[] activePartitionsSetting;
		
		try {
			host = (String) settings.getAttributes().get(HostSettingDefinition.DEF_UUID);
			port = (Integer) settings.getAttributes().get(PortSettingDefinition.DEF_UUID);

			activeZonesSetting = (Integer[]) settings.getAttributes().get(ActiveZonesSettingDefinition.DEF_UUID);
			activePartitionsSetting = (Integer[]) settings.getAttributes().get(ActivePartitionsSettingDefinition.DEF_UUID);

		} catch (ClassCastException e) {
			throw new BadConfigurationException("Unexpected configuration problem.", e);
		}
		
		this.activePartitions = new HashSet<>();
		for (Integer activePartition : activePartitionsSetting) {
			this.activePartitions.add(activePartition);
		}
		
		this.activeZones = new HashSet<>();
		for (Integer activeZone : activeZonesSetting) {
			this.activeZones.add(activeZone);
		}

		it100 = new IT100(new ConfigurationBuilder().withRemoteSocket(host, port).build());
		
		// Start communicating with IT-100.
		try {
			it100.connect();
		} catch (Exception e) {
			throw new ProviderFailureException("Could not connect to DSC IT-100: " + e.getMessage(), e);
		}
		
		final Observable<ReadCommand> readObservable = it100.getReadObservable();
		writeObservable = it100.getWriteObservable();
		
		// Begin tracking zone state and request the initial state.
		zones = new Zones();
		partitions = new Partitions();
		stateMapper = new CommandToStateMapper(zones, partitions, activeZones, activePartitions);
		writeObservable.onNext(new StatusRequestCommand());
		
		// Labels gives us friendly names to our zones.
		labels = new Labels(readObservable, writeObservable);
		eventMapper = new CommandToEventMapper(labels, activeZones, activePartitions);
		
		this.onEventListener = onEventListener;
	
		// TODO Delay a reasonable amount of time before creating events.
		readObservable.ofType(ReadCommand.class).subscribe(this);
	}
	
	public void sendCommand(WriteCommand command) {
		if (writeObservable != null) {
			writeObservable.onNext(command);
		}
	}
	
	@Override
	public void shutdown() throws Exception {
		super.shutdown();
		onEventListener = null;
		
		it100.disconnect();
		it100 = null;
		
		eventMapper = null;
		stateMapper = null;
		
		labels = null;
		
		activeZones = null;
		activePartitions = null;
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
	protected List<State> getStates(StateDefinition stateDefinition) {
		final UUID stateDefinitionUuid = stateDefinition.getUUID();
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
	
	public List<String> getPartitionLabels() {
		final List<String> partLabels = new ArrayList<>();
		for (Integer partNum : activePartitions) {
			if (partNum != null) {
				String label = labels.getPartitionLabel(partNum);
				if (label != null) {
					partLabels.add(label);
				}
			}
		}
		return Collections.unmodifiableList(partLabels);
	}
	
	public List<String> getZoneLabels() {
		final List<String> zoneLabels = new ArrayList<>();
		for (Integer zoneNum : activeZones) {
			if (zoneNum != null) {
				String label = labels.getZoneLabel(zoneNum);
				if (label != null) {
					zoneLabels.add(label);
				}
			}
		}
		return Collections.unmodifiableList(zoneLabels);
	}
	
	public List<Integer> getZoneNumbers() {
		return new ArrayList<Integer>(activeZones);
	}
	
	public List<Integer> getPartitionNumbers() {
		return new ArrayList<Integer>(activePartitions);
	}


}
