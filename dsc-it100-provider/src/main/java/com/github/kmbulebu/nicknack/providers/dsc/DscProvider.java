package com.github.kmbulebu.nicknack.providers.dsc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.configuration.Configuration;
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
import com.github.kmbulebu.nicknack.core.states.State;
import com.github.kmbulebu.nicknack.core.states.StateDefinition;
import com.github.kmbulebu.nicknack.providers.dsc.actions.CommandOutputActionDefinition;
import com.github.kmbulebu.nicknack.providers.dsc.actions.DscActionDefinition;
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
import com.github.kmbulebu.nicknack.providers.dsc.internal.Zone;
import com.github.kmbulebu.nicknack.providers.dsc.internal.Zones;
import com.github.kmbulebu.nicknack.providers.dsc.states.ZoneState;
import com.github.kmbulebu.nicknack.providers.dsc.states.ZoneStateDefinition;

public class DscProvider implements Provider, Action1<ReadCommand> {
	
	private static final Logger LOG = LogManager.getLogger();
	
	public static final UUID PROVIDER_UUID = UUID.fromString("873b5fe3-b69d-4c80-a10e-9c04c0673776");
	
	//private Configuration configuration;
	
	private final List<EventDefinition> eventDefinitions;
	private final Map<UUID, ActionDefinition> actionDefinitions;
	private final List<StateDefinition> stateDefinitions;
	private OnEventListener onEventListener = null;
	private CommandToEventMapper eventMapper;
	private CommandToStateMapper stateMapper;
	
	private Zones zones;
	private Labels labels;
	
	public DscProvider() {
		this.eventDefinitions = new ArrayList<>(6);
		this.eventDefinitions.add(ZoneOpenCloseEventDefinition.INSTANCE);
		this.eventDefinitions.add(PartitionArmedEventDefinition.INSTANCE);
		this.eventDefinitions.add(PartitionInAlarmEventDefinition.INSTANCE);
		this.eventDefinitions.add(PartitionDisarmedEventDefinition.INSTANCE);
		this.eventDefinitions.add(EntryDelayInProgressEventDefinition.INSTANCE);
		this.eventDefinitions.add(ExitDelayInProgressEventDefinition.INSTANCE);
		
		this.stateDefinitions = new ArrayList<>(1);
		this.stateDefinitions.add(ZoneStateDefinition.INSTANCE);
		
		this.actionDefinitions = new HashMap<>();
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
		return "Nick Nack";
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
		return actionDefinitions.values();
	}

	@Override
	public Map<String, String> getAttributeDefinitionValues(UUID eventDefinitionUuid, UUID attributeDefinitionUuid) {
		return Collections.emptyMap();
	}

	@Override
	public void init(Configuration configuration, OnEventListener onEventListener) {
		//this.configuration = configuration;
		
		final String host = configuration.getString("host");
		final int port = configuration.getInt("port");
		
		final IT100 it100 = new IT100(new ConfigurationBuilder().withRemoteSocket(host, port).build());
		
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
		stateMapper = new CommandToStateMapper(zones);
		writeObservable.onNext(new StatusRequestCommand());
		
		// Labels gives us friendly names to our zones.
		labels = new Labels(readObservable, writeObservable);
		eventMapper = new CommandToEventMapper(labels);
		
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
		
		if (actionDef instanceof DscActionDefinition) {
			((DscActionDefinition) actionDef).run(action);
		}
	}

	@Override
	public Collection<StateDefinition> getStateDefinitions() {
		return stateDefinitions;
	}

	@Override
	public List<State> getStates(UUID stateDefinitionUuid) {
		if (ZoneStateDefinition.INSTANCE.getUUID().equals(stateDefinitionUuid)) {
			return getZoneOpenStates();
		}
		return Collections.emptyList();
	}
	
	protected List<State> getZoneOpenStates() {
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

}
