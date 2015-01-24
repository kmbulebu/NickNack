package com.github.kmbulebu.nicknack.core.attributes.filters;

import java.util.Collection;

/**
 * A collection of attribute filters. 
 *
 */
public interface AttributeFilterExpressionCollection {
	
	/**
	 * Collection of AttributeFilterExpressions. All must match
	 * for the collection to be considered a match.
	 *
	 * @return Collection of AttributeFilterExpressions 
	 */
	public Collection<AttributeFilterExpression<?>> getAttributeFilterExpressions();

}
