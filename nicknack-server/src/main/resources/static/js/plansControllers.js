var plansControllers = angular.module('plansControllers', ['eventsService','statesService']);

plansControllers.controller('PlanCtrl', ['$scope', '$route', 'EventsService', 'StatesService', 'ActionsService', 'providers',
    function ($scope, $route, EventsService, StatesService, ActionsService, providers, eventDefinitions, actionDefinitions) {
	
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
			//attributeFilterExpressions:[]
		});
	};
	
	$scope.save = function() {
		 
	}
		
}]);