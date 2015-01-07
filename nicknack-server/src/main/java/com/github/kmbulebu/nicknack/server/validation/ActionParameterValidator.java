package com.github.kmbulebu.nicknack.server.validation;

import java.util.UUID;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.kmbulebu.nicknack.core.actions.Action;
import com.github.kmbulebu.nicknack.server.services.ActionDefinitionService;
import com.github.kmbulebu.nicknack.server.services.exceptions.ActionDefinitionNotFoundException;
import com.github.kmbulebu.nicknack.server.services.exceptions.AttributeDefinitionNotFoundException;

@Component
public class ActionParameterValidator implements ConstraintValidator<ValidActionParameter, Action> {

	@Autowired
	private ActionDefinitionService actionDefinitionService;
	
	@Override
    public void initialize(ValidActionParameter constraintAnnotation) {

    }

    @Override
    public boolean isValid(Action action, ConstraintValidatorContext constraintContext) {

    	/*final Map<UUID, String> parameters = action.getAttributes();
    	if (parameters != null) {
	    	for (UUID uuid : parameters.keySet()) {
	    		try {
					if (!validateParameter(action.getAppliesToActionDefinition(), uuid, parameters.get(uuid))) {
						return false;
					}
				} catch (ActionDefinitionNotFoundException | ParameterDefinitionNotFoundException e) {
					return false;
				}
	    	}
    	}*/
    	return true;
    }
    
    protected boolean validateParameter(UUID actionDefUuid, UUID uuid, String value) throws ActionDefinitionNotFoundException, AttributeDefinitionNotFoundException {
    	/*// Get the parameter definition
    	final ParameterDefinition def = actionDefinitionService.getAttributeDefinition(actionDefUuid, uuid);
    	
    	// Definition exists.
    	if (def == null) {
    		return false;
    	}
    	
    	// Value can be parsed.
    	if (def.validate(value).size() == 0) {
    		return false;
    	}*/
    	
    	return true;
    }

}