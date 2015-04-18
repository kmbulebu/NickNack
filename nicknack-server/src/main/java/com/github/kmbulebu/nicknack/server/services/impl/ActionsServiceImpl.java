package com.github.kmbulebu.nicknack.server.services.impl;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

import javax.inject.Inject;

import org.springframework.data.neo4j.fieldaccess.DynamicPropertiesContainer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.kmbulebu.nicknack.server.dbmodel.ActionEntity;
import com.github.kmbulebu.nicknack.server.dbmodel.ActionRepository;
import com.github.kmbulebu.nicknack.server.restmodel.Action;
import com.github.kmbulebu.nicknack.server.services.ActionsService;

@Service
public class ActionsServiceImpl implements ActionsService {
	
	@Inject
	private ActionRepository actionRepository;

	@Override
	@Transactional
	public void saveAction(Action action) {
		ActionEntity actionEntity = null;
		
		if (action.getId() != null) {
			actionEntity = actionRepository.findOne(action.getId());
		}
		
		if (actionEntity == null) {
			actionEntity = new ActionEntity();
		}
		
		actionEntity.setName(action.getName());
		actionEntity.setActionDefinitionUuid(action.getUuid());
		final DynamicPropertiesContainer attributes = new DynamicPropertiesContainer();
		
		for (UUID attributeUuid : action.getAttributes().keySet()) {
			attributes.setProperty(attributeUuid.toString(), action.getAttributes().get(attributeUuid));
		}
		actionEntity.setAttributes(attributes);
		
		actionRepository.save(actionEntity);
	}

	@Override
	@Transactional(readOnly = true)
	public Action getAction(Long id) {
		final ActionEntity entity = actionRepository.findOne(id);
		
		if (entity == null) {
			return null;
		}
		
		final Action action = mapEntity(entity);
		
		/*for (AttributeEntity attributeEntity : entity.getDescribedBy()) {
			// Look up definition
			final AttributeDefinition<?, ?> definition = coreProviderService.getNickNackProviderService().getAttributeDefinition(attributeEntity.getAttributeDefinitionUuid());
			//attributeMapper.
			
			
			if (definition != null) {
				final AttributeImpl attribute = new AttributeImpl(null);
				action.getAttributes().add(attribute);
			}
		}*/
		
		return action;
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Action> getAllActions() {
		final List<Action> actions = new LinkedList<>();
		
		actionRepository.findAll().forEach(new Consumer<ActionEntity>() {

			@Override
			public void accept(ActionEntity entity) {
				actions.add(mapEntity(entity));
			}
		});
		
		return actions;
	}
	
	private Action mapEntity(ActionEntity entity) {
		Action action = new Action();
		action.setId(entity.getId());
		action.setName(entity.getName());
		action.setUuid(entity.getActionDefinitionUuid());
		
		final Map<UUID, String[]> attributes = new HashMap<>();
		final Map<String, Object> entityAttributes = entity.getAttributes().asMap();
		for (String uuidStr : entityAttributes.keySet()) {
			Object obj = entityAttributes.get(uuidStr);
			final UUID uuid = UUID.fromString(uuidStr);
			String[] values = (String[]) obj;
			attributes.put(uuid, values);
		}
		action.setAttributes(attributes);
		return action;
	}

	@Override
	public void deleteAction(Long id) {
		actionRepository.delete(id);
	}

}
