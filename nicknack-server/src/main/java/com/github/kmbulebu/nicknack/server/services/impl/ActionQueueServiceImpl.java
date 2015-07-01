package com.github.kmbulebu.nicknack.server.services.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.github.kmbulebu.nicknack.core.actions.ActionAttributeException;
import com.github.kmbulebu.nicknack.core.actions.ActionFailureException;
import com.github.kmbulebu.nicknack.core.attributes.AttributeDefinition;
import com.github.kmbulebu.nicknack.core.attributes.AttributeValueParser;
import com.github.kmbulebu.nicknack.core.attributes.AttributeValueParser.InvalidValueException;
import com.github.kmbulebu.nicknack.core.providers.ProviderService;
import com.github.kmbulebu.nicknack.core.valuetypes.ValueType.ParseException;
import com.github.kmbulebu.nicknack.server.restmodel.Action;
import com.github.kmbulebu.nicknack.server.services.ActionQueueService;
import com.github.kmbulebu.nicknack.server.services.ActionsService;
import com.github.kmbulebu.nicknack.server.services.CoreProviderServiceWrapper;

@Service
public class ActionQueueServiceImpl implements ActionQueueService {
	
	@Inject
	private CoreProviderServiceWrapper coreProviderServiceWrapper;
	
	@Inject
	private ActionsService actionsService;
	
	private AttributeValueParser valueParser = new AttributeValueParser();
	
	private ProviderService getProviderService() {
		return coreProviderServiceWrapper.getNickNackProviderService();
	}

	@Override
	public void enqueueAction(Long actionId) {
		final Action restAction = actionsService.getAction(actionId);
		
		if (restAction != null) {
			enqueueAction(restAction);
		}
		
	}

	@Override
	public void enqueueAction(Action action) {
		final com.github.kmbulebu.nicknack.core.actions.Action coreAction = mapAction(action);
		try {
			coreProviderServiceWrapper.getNickNackProviderService().run(coreAction);
		} catch (ActionFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ActionAttributeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private com.github.kmbulebu.nicknack.core.actions.Action mapAction(Action restAction) {
		final UUID actionDefinitionUuid = restAction.getUuid();
		
		
		
		final Map<UUID, Object> coreAttributes = new HashMap<>();
		for (UUID attributeUuid : restAction.getAttributes().keySet()) {
			
			final AttributeDefinition<?, ?> attributeDefinition = getProviderService().getAttributeDefinition(attributeUuid);
			
			if (attributeDefinition != null) {
				final String[] valueStrs = restAction.getAttributes().get(attributeUuid);
				if (attributeDefinition.isMultiValue()) {
					try {	
						coreAttributes.put(attributeUuid, valueParser.toObjects(attributeDefinition, valueStrs));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvalidValueException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else if (valueStrs.length == 1) {
					try {	
						coreAttributes.put(attributeUuid, valueParser.toObject(attributeDefinition, valueStrs[0]));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvalidValueException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				
			}
		}
 		final com.github.kmbulebu.nicknack.core.actions.Action coreAction = new com.github.kmbulebu.nicknack.core.actions.Action() {
			
			@Override
			public Map<UUID, ?> getAttributes() {
				return coreAttributes;
			}
			
			@Override
			public UUID getAppliesToActionDefinition() {
				return actionDefinitionUuid;
			}
		};
		
		return coreAction;
	}


}
