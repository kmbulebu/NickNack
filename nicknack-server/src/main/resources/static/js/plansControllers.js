var plansControllers = angular.module('plansControllers', ['eventsService','statesService','plansService']);

plansControllers.controller('PlansCtrl', ['$scope', '$route', 'PlansService', 'plans', 
                                             function ($scope, $route, PlansService, plans) {
      	
      	$scope.plans = plans;
      	
      	$scope.deletePlan = function(plan) {
      		plan.$del('self').then(function () {
      			$route.reload();
      		});
      		
      	};
      	
      }]);

plansControllers.controller('PlanCtrl', ['$scope', '$route', 'EventsService', 'StatesService', 'ActionsService', 'PlansService', 'providers', 'plan', 'eventFilters', 'stateFilters', 'actions', 
    function ($scope, $route, EventsService, StatesService, ActionsService, PlansService, providers, plan, eventFilters, stateFilters, actions) {
	
	// Plan
	$scope.plan = plan;
	
	// Events
	$scope.providers = providers;
	
	$scope.eventFilters = eventFilters;
	
	$scope.onEventChange = function(eventFilter) {
		EventsService.getAttributeDefinitions(eventFilter.eventDefinition).then(
			function(success) {
				eventFilter.attributeDefinitions = success;
				
				// If attribute filter expressions are defined, find the right attributeDefinition object to mark as selected.
				angular.forEach(eventFilter.attributeFilterExpressions, function(attributeFilterExpression) {
					if (attributeFilterExpression.attributeDefinitionUuid) {
						for (i = 0; i < eventFilter.attributeDefinitions.length; i++) {
							if (eventFilter.attributeDefinitions[i].uuid === attributeFilterExpression.attributeDefinitionUuid) {
								attributeFilterExpression.attributeDefinition = eventFilter.attributeDefinitions[i];
								break;
							}
						}
					}
				});
			}
		);
	};
	
	$scope.onProviderChangeUpdateEvents = function(eventFilter) {
		EventsService.getEventDefinitionsByProvider(eventFilter.provider).then(
			function(success) {
				eventFilter.eventDefinitions = success;
				
				// If an eventFilter is selected, find the object to mark it as selected
				if (eventFilter.appliesToEventDefinition) {
					for (i = 0; i < eventFilter.eventDefinitions.length; i++) {
						if (eventFilter.eventDefinitions[i].uuid === eventFilter.appliesToEventDefinition) {
							eventFilter.eventDefinition = eventFilter.eventDefinitions[i];
							$scope.onEventChange(eventFilter);
							break;
						}
					}
				}
			}
		);
	}
		
	
	$scope.addEventFilter = function() {
		$scope.eventFilters.push({
			attributeFilterExpressions:[]
		});
	};	
	
	$scope.addAttributeFilterExpression = function(attributeFilterExpressions) {
		attributeFilterExpressions.push({});
	};
	
	$scope.deleteItem = function(array, index) {
		array.splice(index, 1);
	};
	
	angular.forEach($scope.eventFilters, function(eventFilter) {
		// If a provider is selected, find the object to mark it as selected
		if (eventFilter.appliesToProvider) {
			for (i = 0; i < $scope.providers.length; i++) {
				if ($scope.providers[i].uuid === eventFilter.appliesToProvider) {
					eventFilter.provider = $scope.providers[i];
					$scope.onProviderChangeUpdateEvents(eventFilter);
					break;
				}
			}
		}
	});
	
	// States
	$scope.stateFilters = stateFilters;
	
	$scope.onStateChange = function(stateFilter) {
		StatesService.getAttributeDefinitions(stateFilter.stateDefinition).then(
			function(success) {
				stateFilter.attributeDefinitions = success;
				
				// If attribute filter expressions are defined, find the right attributeDefinition object to mark as selected.
				angular.forEach(stateFilter.attributeFilterExpressions, function(attributeFilterExpression) {
					if (attributeFilterExpression.attributeDefinitionUuid) {
						for (i = 0; i < stateFilter.attributeDefinitions.length; i++) {
							if (stateFilter.attributeDefinitions[i].uuid === attributeFilterExpression.attributeDefinitionUuid) {
								attributeFilterExpression.attributeDefinition = stateFilter.attributeDefinitions[i];
								break;
							}
						}
					}
				});
			}
		);
	};
	
	$scope.onProviderChangeUpdateStates = function(stateFilter) {
		StatesService.getStateDefinitionsByProvider(stateFilter.provider).then(
			function(success) {
				stateFilter.stateDefinitions = success;
				
				// If an stateFilter is selected, find the object to mark it as selected
				if (stateFilter.appliesToStateDefinition) {
					for (i = 0; i < stateFilter.stateDefinitions.length; i++) {
						if (stateFilter.stateDefinitions[i].uuid === stateFilter.appliesToStateDefinition) {
							stateFilter.stateDefinition = stateFilter.stateDefinitions[i];
							$scope.onStateChange(stateFilter);
							break;
						}
					}
				}
			}
		);
	}
		
	$scope.addStateFilter = function() {
		$scope.stateFilters.push({
			attributeFilterExpressions:[]
		});
	};
	
	angular.forEach($scope.stateFilters, function(stateFilter) {
		// If a provider is selected, find the object to mark it as selected
		if (stateFilter.appliesToProvider) {
			for (i = 0; i < $scope.providers.length; i++) {
				if ($scope.providers[i].uuid === stateFilter.appliesToProvider) {
					stateFilter.provider = $scope.providers[i];
					$scope.onProviderChangeUpdateStates(stateFilter);
					break;
				}
			}
		}
	});
	
	// Actions
	$scope.actions = actions;
	
	$scope.onActionChange = function(action) {
		ActionsService.getAttributeDefinitions(action.actionDefinition).then(
			function(success) {
				action.attributeDefinitions = success;
				
				angular.forEach(action.attributeDefinitions, function(attributeDefinition) {
					attributeDefinition.value = action.attributes[attributeDefinition.uuid];
				});
			}
		);
	};
	
	$scope.onProviderChangeUpdateActions = function(action) {
		ActionsService.getActionDefinitionsByProvider(action.provider).then(
			function(success) {
				action.actionDefinitions = success;
				
				// If an action is selected, find the object to mark it as selected
				if (action.appliesToActionDefinition) {
					for (i = 0; i < action.actionDefinitions.length; i++) {
						if (action.actionDefinitions[i].uuid === action.appliesToActionDefinition) {
							action.actionDefinition = action.actionDefinitions[i];
							$scope.onActionChange(action);
							break;
						}
					}
				}
			}
		);
	}
	
	$scope.addAction = function() {
		$scope.actions.push({

		});
	}; 
	
	angular.forEach($scope.actions, function(action) {
		// If a provider is selected, find the object to mark it as selected
		if (action.appliesToProvider) {
			for (i = 0; i < $scope.providers.length; i++) {
				if ($scope.providers[i].uuid === action.appliesToProvider) {
					action.provider = $scope.providers[i];
					$scope.onProviderChangeUpdateActions(action);
					break;
				}
			}
		}
	});
	
	$scope.save = function() {
		 // Need to build our objects suitable for the web services.
		var plan = $scope.plan;
		
		// Build eventFilters
		var eventFilters = [];
		angular.forEach($scope.eventFilters, function(eventFilter) {
			var attributeFilterExpressions = [];
			angular.forEach(eventFilter.attributeFilterExpressions, function(scopeFilterExpression) {
				attributeFilterExpressions.push({
					attributeDefinitionUuid: scopeFilterExpression.attributeDefinition.uuid,
	             	operator: scopeFilterExpression.operator,
	             	operand: [scopeFilterExpression.operand]
				});
			});
			eventFilters.push({ 
				appliesToEventDefinition:eventFilter.eventDefinition.uuid,
				appliesToProvider:eventFilter.provider.uuid,
				attributeFilterExpressions:attributeFilterExpressions
	        });
		});
		// Build stateFilters
		var stateFilters = [];
		angular.forEach($scope.stateFilters, function(stateFilter) {
			var attributeFilterExpressions = [];
			angular.forEach(stateFilter.attributeFilterExpressions, function(scopeFilterExpression) {
				attributeFilterExpressions.push({
					attributeDefinitionUuid: scopeFilterExpression.attributeDefinition.uuid,
	             	operator: scopeFilterExpression.operator,
	             	operand: [scopeFilterExpression.operand]
				});
			});
			stateFilters.push({ 
				appliesToStateDefinition:stateFilter.stateDefinition.uuid,
				appliesToProvider:stateFilter.provider.uuid,
				attributeFilterExpressions:attributeFilterExpressions
	        });
		});
		// Build actions
		var actions = [];
		angular.forEach($scope.actions, function(action) {
			var parameters = {};
			angular.forEach(action.attributeDefinitions, function(attributeDefinition) {
				parameters[attributeDefinition.uuid] = attributeDefinition.value;
			});
			actions.push({ 
				appliesToActionDefinition:action.actionDefinition.uuid,
				appliesToProvider:action.provider.uuid,
				parameters:parameters
	        });
		});
		
		//Post to web service
		if (plan.uuid) {
			// Update existing
		} else {
			// Create new
			PlansService.createCompletePlan(plan, eventFilters, stateFilters, actions).then(
				function(success) {
					
				},
				function(error) {
					
				}
			);
		}
	}
		
}]);