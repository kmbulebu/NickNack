package com.oakcity.nicknack.providers.dsc;

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

import com.oakcity.dsc.it100.ConfigurationBuilder;
import com.oakcity.dsc.it100.IT100;
import com.oakcity.dsc.it100.Labels;
import com.oakcity.dsc.it100.commands.read.ReadCommand;
import com.oakcity.dsc.it100.commands.write.WriteCommand;
import com.oakcity.nicknack.core.actions.Action;
import com.oakcity.nicknack.core.actions.ActionDefinition;
import com.oakcity.nicknack.core.actions.ActionFailureException;
import com.oakcity.nicknack.core.actions.ActionParameterException;
import com.oakcity.nicknack.core.events.Event;
import com.oakcity.nicknack.core.events.EventDefinition;
import com.oakcity.nicknack.core.providers.OnEventListener;
import com.oakcity.nicknack.core.providers.Provider;
import com.oakcity.nicknack.providers.dsc.actions.CommandOutputActionDefinition;
import com.oakcity.nicknack.providers.dsc.actions.DscActionDefinition;
import com.oakcity.nicknack.providers.dsc.actions.PartitionArmAwayActionDefinition;
import com.oakcity.nicknack.providers.dsc.actions.PartitionArmNoEntryDelayActionDefinition;
import com.oakcity.nicknack.providers.dsc.actions.PartitionArmStayActionDefinition;
import com.oakcity.nicknack.providers.dsc.actions.PartitionArmWithCodeActionDefinition;
import com.oakcity.nicknack.providers.dsc.actions.PartitionDisarmActionDefinition;
import com.oakcity.nicknack.providers.dsc.events.EntryDelayInProgressEventDefinition;
import com.oakcity.nicknack.providers.dsc.events.ExitDelayInProgressEventDefinition;
import com.oakcity.nicknack.providers.dsc.events.PartitionArmedEventDefinition;
import com.oakcity.nicknack.providers.dsc.events.PartitionDisarmedEventDefinition;
import com.oakcity.nicknack.providers.dsc.events.PartitionInAlarmEventDefinition;
import com.oakcity.nicknack.providers.dsc.events.ZoneOpenCloseEventDefinition;

public class DscProvider implements Provider, Action1<ReadCommand> {
	
	private static final Logger LOG = LogManager.getLogger();
	
	public static final UUID PROVIDER_UUID = UUID.fromString("873b5fe3-b69d-4c80-a10e-9c04c0673776");
	
	//private Configuration configuration;
	
	private final List<EventDefinition> eventDefinitions;
	private final Map<UUID, ActionDefinition> actionDefinitions;
	private OnEventListener onEventListener = null;
	private CommandToEventMapper eventMapper;
	private Labels labels;
	
	public DscProvider() {
		this.eventDefinitions = new ArrayList<>(6);
		this.eventDefinitions.add(ZoneOpenCloseEventDefinition.INSTANCE);
		this.eventDefinitions.add(PartitionArmedEventDefinition.INSTANCE);
		this.eventDefinitions.add(PartitionInAlarmEventDefinition.INSTANCE);
		this.eventDefinitions.add(PartitionDisarmedEventDefinition.INSTANCE);
		this.eventDefinitions.add(EntryDelayInProgressEventDefinition.INSTANCE);
		this.eventDefinitions.add(ExitDelayInProgressEventDefinition.INSTANCE);
		
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
		
		readObservable.ofType(ReadCommand.class).subscribe(this);

	}

	@Override
	public void call(ReadCommand readCommand) {
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

}