package com.oakcity.nicknack.server.validation;

import java.text.ParseException;
import java.util.Map;
import java.util.UUID;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oakcity.nicknack.core.actions.Action;
import com.oakcity.nicknack.core.actions.Action.ParameterDefinition;
import com.oakcity.nicknack.server.services.ActionDefinitionService;

@Component
public class ActionParameterValidator implements ConstraintValidator<ValidActionParameter, Action> {

	@Autowired
	private ActionDefinitionService actionDefinitionService;
	
	@Override
    public void initialize(ValidActionParameter constraintAnnotation) {

    }

    @Override
    public boolean isValid(Action action, ConstraintValidatorContext constraintContext) {

    	final Map<UUID, String> parameters = action.getParameters();
    	if (parameters != null) {
	    	for (UUID uuid : parameters.keySet()) {
	    		if (!validateParameter(action.getAppliesToActionDefinition(), uuid, parameters.get(uuid))) {
	    			return false;
	    		}
	    	}
    	}
    	return true;
    }
    
    protected boolean validateParameter(UUID actionDefUuid, UUID uuid, String value) {
    	// Get the parameter definition
    	final ParameterDefinition def = actionDefinitionService.getParameterDefinition(actionDefUuid, uuid);
    	
    	// Definition exists.
    	if (def == null) {
    		return false;
    	}
    	
    	// Value can be parsed.
    	try {
			Object parsed = def.getUnits().parse(value);
			
			Object min = def.getUnits().getMin();
			Object max = def.getUnits().getMax();
			
			// TODO Rethink Unit class/model. This isn't making it easy to validate.
			//if (min != null && def.getUnits().compare(def.getUnits().getMin(), def.getUnits().parse(value)) >= 0) {
			//	
			//}
		} catch (ParseException e) {
			return false;
		}
    	
    	
    	
    	return true;
    }

}