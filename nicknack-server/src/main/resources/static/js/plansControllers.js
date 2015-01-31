var plansControllers = angular.module('plansControllers', ['eventsService','statesService','plansService']);

plansControllers.controller('PlanCtrl', ['$scope', '$route', 'EventsService', 'StatesService', 'ActionsService', 'PlansService', 'providers',
    function ($scope, $route, EventsService, StatesService, ActionsService, PlansService, providers, eventDefinitions, actionDefinitions) {
	
	// Plan
	$scope.plan = {};
	
	// Events
	$scope.providers = providers;
	$scope.eventFilters = [{
		attributeFilterExpressions:[]
	}];
	
	$scope.onProviderChangeUpdateEvents = function(eventFilter) {
		EventsService.getEventDefinitionsByProvider(eventFilter.provider).then(
			function(success) {
				eventFilter.eventDefinitions = success;
			}
		);
	}
		
	$scope.onEventChange = function(eventFilter) {
		EventsService.getAttributeDefinitions(eventFilter.eventDefinition).then(
			function(success) {
				eventFilter.attributeDefinitions = success;
			}
		);
	};
	
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
	
	// States
	$scope.stateFilters = [];
	
	$scope.onProviderChangeUpdateStates = function(stateFilter) {
		StatesService.getStateDefinitionsByProvider(stateFilter.provider).then(
			function(success) {
				stateFilter.stateDefinitions = success;
			}
		);
	}
		
	$scope.onStateChange = function(stateFilter) {
		StatesService.getAttributeDefinitions(stateFilter.stateDefinition).then(
			function(success) {
				stateFilter.attributeDefinitions = success;
			}
		);
	};
	
	$scope.addStateFilter = function() {
		$scope.stateFilters.push({
			attributeFilterExpressions:[]
		});
	};
	
	// Actions
	$scope.actions = [{}];
	
	$scope.onProviderChangeUpdateActions = function(action) {
		ActionsService.getActionDefinitionsByProvider(action.provider).then(
			function(success) {
				action.actionDefinitions = success;
			}
		);
	}
		
	$scope.onActionChange = function(action) {
		ActionsService.getAttributeDefinitions(action.actionDefinition).then(
			function(success) {
				action.attributeDefinitions = success;
			}
		);
	};
	
	$scope.addAction = function() {
		$scope.actions.push({

		});
	}; 
	
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
				parameters:parameters
	        });
		});
		
		// Post to web service
		PlansService.createCompletePlan(plan, eventFilters, stateFilters, actions).then(
			function(success) {
				
			},
			function(error) {
				
			}
		);
	}
		
}]);