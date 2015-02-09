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

actionsControllers.controller('ActionBookmarkCtrl', ['$scope', '$location', '$routeParams', 'ActionsService', 'WebsiteService', 'providers', 'action', 'actionDefinitions', 'attributeDefinitions',
                                                 function ($scope, $location, $routeParams, ActionsService, WebsiteService, providers, action, actionDefinitions, attributeDefinitions) {
	
	// Bind to the view
	$scope.providers = providers;
	$scope.description = action.description;
	$scope.attributes = action.attributes;
	$scope.action = action;
	$scope.actionDefinitions = actionDefinitions;
	$scope.attributeDefinitions = attributeDefinitions;
	
	$scope.onProviderChange = function(provider) {
		$location.path('/newActionBookmark/provider/' + provider.uuid + '/actionDefinition//action/');
	}
	
	$scope.onActionDefinitionChange = function(provider, actionDefinition) {
		$location.path('/newActionBookmark/provider/' + provider.uuid + '/actionDefinition/' + actionDefinition.uuid + '/action/');
	}
	
	if ($routeParams.provider) {
		for (i = 0; i < providers.length; i++) {
			if (providers[i].uuid === $routeParams.provider) {
				$scope.provider = providers[i];
				break;
			}
		}
	}
	
	if ($routeParams.actionDefinitionUuid) {
		for (i = 0; i < providers.length; i++) {
			if (actionDefinitions[i].uuid === $routeParams.actionDefinitionUuid) {
				$scope.actionDefinition = actionDefinitions[i];
				break;
			}
		}
	}
	
	
	$scope.deleteAction = function(actionUuid) {
		WebsiteService.deleteActionBookmark(actionUuid).then(function () {
			window.location = '#/actionBookmarks';
		});
		
	};
	
	buildAction = function() {
		return {
			appliesToProvider: $scope.provider.uuid,
			appliesToActionDefinition: $scope.actionDefinition.uuid,
			attributes: $scope.attributes,
			description: $scope.description,
			uuid: $scope.action.uuid
		};
	}
	
	$scope.runNow = function() {
		WebsiteService.runActionNow(buildAction());
   	};
	
   	$scope.save = function() {
   		if (action.uuid) {
	   		WebsiteService.updateActionBookmark(action.uuid, buildAction()).then( function() {
				window.location = '#/actionBookmarks';
			});
   		} else {
   			WebsiteService.saveActionBookmark(buildAction()).then( function() {
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

   