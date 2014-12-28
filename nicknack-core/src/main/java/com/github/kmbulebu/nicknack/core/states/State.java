package com.github.kmbulebu.nicknack.core.states;

import com.github.kmbulebu.nicknack.core.attributes.AttributeCollection;



/**
 * An event.
 * @author Kevin Bulebush
 *
 */
public interface State extends AttributeCollection {

	public StateDefinition getStateDefinition();
	
}
