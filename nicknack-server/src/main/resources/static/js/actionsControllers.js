var actionsControllers = angular.module('actionsControllers', ['staticDataService']);

actionsControllers.controller('ActionBookmarksCtrl', ['$scope', '$route', 'StaticDataService', 'WebsiteService', 'actions', 
    function ($scope, $route, StaticDataService, WebsiteService, actions) {
	
	$scope.actions = actions;
	
	$scope.runNow = function(actionUuid) {
		WebsiteService.runActionBookmark(actionUuid).then( function() {
			// Do something
		});
   	};
	
	
}]);

actionsControllers.controller('ActionBookmarkCtrl', ['$scope', 'ActionsService', 'WebsiteService', 'action', 'actionDefinitions', 'attributeDefinitions',
                                                 function ($scope, ActionsService, WebsiteService, action, actionDefinitions, attributeDefinitions) {
	
	// Bind to the view
	$scope.action = action;
	$scope.actionDefinitions = actionDefinitions;
	$scope.attributeDefinitions = [];
	
	// When an action definition is selected, update the parameter list and set the uuid
	$scope.onActionDefinitionChange = function(actionDefinition) {
		$scope.action.appliesToActionDefinition = actionDefinition.uuid;
		
		ActionsService.getAttributeDefinitions(actionDefinition).then(
			function (success) {
				$scope.attributeDefinitions = success;
			});
   	};
	
	// If an action definition is selected, find the object to mark it as selected
	if ($scope.action.appliesToActionDefinition) {
		for (i = 0; i < $scope.actionDefinitions.length; i++) {
			if ($scope.actionDefinitions[i].uuid === $scope.action.appliesToActionDefinition) {
				$scope.selectedActionDefinition = $scope.actionDefinitions[i];
				$scope.onActionTypeChange();
				break;
			}
		}
	}
	
	$scope.deleteAction = function(actionUuid) {
		WebsiteService.deleteActionBookmark(actionUuid).then(function () {
			$route.reload();
		});
		
	};
	
	$scope.runNow = function() {
		WebsiteService.runActionNow($scope.action);
   	};
	
   	$scope.save = function() {
   		if ($scope.action.uuid) {
	   		WebsiteService.updateActionBookmark($scope.action.uuid, $scope.action).then( function() {
				window.location = '#/actionBookmarks';
			});
   		} else {
   			WebsiteService.saveActionBookmark($scope.action).then( function() {
				window.location = '#/actionBookmarks';
			});
   		}
   	};
}]);

actionsControllers.directive('attributeInput', [function() {
	return {
		scope: {
			definition: '='
		},
		restrict: 'AE',
		replace: 'true',
		template: '<input type="{{inputType}}"/>',
		link: function(scope, elem, attrs) {
			scope.$watch('definition', function(value) {
			 if (angular.isDefined(value)) {
				 var valueType = value.valueType;
				 
				 if (valueType.name === 'hostname') {
					 scope.inputType = 'text'; 
				 } else {
				 	 scope.inputType = valueType.name;
				 }
				 
				 
				 if (angular.isDefined(valueType.maximumLength)) {
					 elem.attr('maxLength', valueType.maximumLength);
				 }
				 if (angular.isDefined(valueType.regexPattern)) {
					 elem.attr('pattern', valueType.regexPattern);
				 }
				 if (angular.isDefined(valueType.min)) {
			 		 elem.attr('min', valueType.min);
			 	 }
			 	if (angular.isDefined(valueType.max)) {
			 		 elem.attr('max', valueType.max);
			 	 }
			 	if (angular.isDefined(valueType.step)) {
			 		 elem.attr('step', valueType.step);
			 	 }
			 }
			});
		}
	};
}]);

actionsControllers.directive('attributeExpressionInput', [function() {
	return {
		scope: {
			definition: '=',
			operator: '='
		},
		restrict: 'AE',
		replace: 'true',
		template: '<input type="{{inputType}}"/>',
		link: function(scope, elem, attrs) {
			scope.$watch('operator', function(value) {
				if (angular.isDefined(value)) {
					if (value === 'IN' || value === 'NOT_IN') {
						elem.prop('multiple', true);
					}
				}
			});
			scope.$watch('definition', function(value) {
			 if (angular.isDefined(value)) {
				 var valueType = value.valueType;
				 
				 if (valueType.name === 'hostname') {
					 scope.inputType = 'text'; 
				 } else {
				 	 scope.inputType = valueType.name;
				 }
				 
				 
				 if (angular.isDefined(valueType.maximumLength)) {
					 elem.attr('maxLength', valueType.maximumLength);
				 }
				 if (angular.isDefined(valueType.regexPattern)) {
					 elem.attr('pattern', valueType.regexPattern);
				 }
				 if (angular.isDefined(valueType.min)) {
			 		 elem.attr('min', valueType.min);
			 	 }
			 	if (angular.isDefined(valueType.max)) {
			 		 elem.attr('max', valueType.max);
			 	 }
			 	if (angular.isDefined(valueType.step)) {
			 		 elem.attr('step', valueType.step);
			 	 }
			 }
			});
		}
	};
}]);

   