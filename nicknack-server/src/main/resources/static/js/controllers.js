var nicknackControllers = angular.module('nicknackControllers', ['staticDataService']);

nicknackControllers.controller('PlansCtrl', ['$scope', '$route', 'WebsiteService', 'StaticDataService', 'plans', 
                                       function ($scope, $route, WebsiteService, StaticDataService, plans) {
	
	$scope.plans = plans;
	
	$scope.deletePlan = function(planUuid) {
		WebsiteService.deletePlan(planUuid).then(function () {
			$route.reload();
		});
		
	};
	
}]);	

nicknackControllers.controller('NewPlanCtrl', ['$scope', '$rootScope', '$routeParams', '$route', 'WebsiteService', 'StaticDataService',
function ($scope, $rootScope, $routeParams, $route, WebsiteService, StaticDataService) {
	$scope.planUuid = $routeParams.planUuid;
	$scope.formData = {};
	$scope.formData.attributeFilterExpressions = [];
	$scope.actionParameterValues = [];
	if ($scope.planUuid !== undefined) {
		// Look up plan.
		StaticDataService.plan($scope.planUuid)
        .then( function( planResource )
        {
        	if (planResource) {
        		$scope.planUuid = planResource.uuid;
        		$scope.newPlanName = planResource.name;
        		planResource.$get('eventFilters').then(function (eventFilters){
        			eventFilters.$get('EventFilters').then( function(eventFilters) {
        				var eventDefinitionUuid = eventFilters[0].appliesToEventDefinition;
        				$scope.eventFilterUuid = eventFilters[0].uuid;
        				// find it.
        				StaticDataService.eventDefinition(eventDefinitionUuid).then(function (eventDef) {
        					$scope.eventType = eventDef;
        					StaticDataService.attributeDefinitions(eventDefinitionUuid).then(function (attributeDefinitions) {
        						$scope.eventAttributeDefinitions = attributeDefinitions;
        					
	        					$scope.formData.attributeFilterExpressions = eventFilters[0].attributeFilterExpressions;
	    						for (i = 0; i < $scope.formData.attributeFilterExpressions.length; i++) {
	    							for (j = 0; j < $scope.eventAttributeDefinitions.length; j++) {
	    								if ($scope.eventAttributeDefinitions[j].uuid === $scope.formData.attributeFilterExpressions[i].attributeDefinitionUuid) {
	    									$scope.formData.attributeFilterExpressions[i].attributeDefinition = $scope.eventAttributeDefinitions[j];
	    									break;
	    								}
	    							}
	    							
	    						}
        					});
        				});
        			});
        		});
        		planResource.$get('Actions').then(function (actions){
        			actions.$get('Actions').then( function(actions) {
        				var actionDefUuid = actions[0].appliesToActionDefinition;
        				$scope.actionUuid = actions[0].uuid;
        				// find it.
        				StaticDataService.actionDefinition(actionDefUuid).then(function (actionDef) {
        					$scope.actionType = actionDef;
        					StaticDataService.parameterDefinitions(actionDefUuid).then(function (parameterDefinitions) {
        						$scope.actionParameterDefinitions = parameterDefinitions;
        						for (i = 0; i < $scope.actionParameterDefinitions.length; i++) {
        							$scope.actionParameterValues[i] = actions[0].parameters[$scope.actionParameterDefinitions[i].uuid];
        						}
        					});
        				});
        			});
        		});
        	} else {
        		$scope.planUuid = undefined;
        	}
        })
        ;
	}
	
	if ($scope.planUuid == undefined) {
		// Start empty for new plan.
		$scope.newPlanName = "";
		// Get rid of this in favor of using an array of each field. Then build this at submit time. 
		$scope.formData.eventAttributeFilters = [
	        { 
	        	appliesToAttributeDefinition: '',
	        	operator: '',
	        	operand:''
	        }];
	} 
	
	StaticDataService.eventDefinitions().then(function (eventDefinitions) {
		$scope.eventDefinitions = eventDefinitions;
	});
	
	StaticDataService.actionDefinitions().then(function (actionDefinitions) {
		$scope.actionDefinitions = actionDefinitions;
	});
	
	$scope.updateAttributeDefinitions = function() {
		var json = angular.fromJson($scope.eventType);
		StaticDataService.attributeDefinitions(json.uuid).then(function (attributeDefinitions) {
			$scope.eventAttributeDefinitions = attributeDefinitions;
		});
	};
	
	$scope.onAttributeFilterTypeChange = function() {
		$scope.formData.attributeFilterExpressions[this.$index].attributeDefinitionUuid = $scope.formData.attributeFilterExpressions[this.$index].attributeDefinition.uuid;
		
		// Get the list of attribute values.
		var index = this.$index;
		WebsiteService.getAttributeDefinitionValues($scope.eventType.uuid, $scope.formData.attributeFilterExpressions[this.$index].attributeDefinition.uuid)
			.then(function(valuesResource) {
				var valueArray = [];
				for (var key in valuesResource.content) {
					valueArray.push(valuesResource.content[key]);
				}
				$scope.formData.attributeFilterExpressions[this.$index].attributeDefinition.values = valueArray;
			});
	};
	
	$scope.addEventAttributeFilter = function() {
		dummyFilter = { 
             	attributeDefinitionUuid: '',
             	operator: '',
             	operand:'',
             	attributeDefinition: {values:[], units:{supportedOperators:[]}}
              };
		$scope.formData.attributeFilterExpressions.push(dummyFilter);
	};
	
	$scope.onActionTypeChange = function() {
		var json = angular.fromJson($scope.actionType);
		StaticDataService.parameterDefinitions(json.uuid).then(function (parameterDefinitions) {
			$scope.actionParameterDefinitions = parameterDefinitions;
		});
	};
	
	$scope.deleteEventAttributeFilter = function() {
		$scope.formData.attributeFilterExpressions.splice(this.$index, 1);
	};
	
	$scope.submit = function() {
		
		if ($scope.planUuid === undefined) {
			// Step 1: Create the plan resource.
			var plan = { 
	             	name:$scope.newPlanName
	              };
			
			WebsiteService
				.createPlanResource(plan).then(function (newPlanResource) {
					// Step 2: Create the Event Filter resource.
					var json = angular.fromJson($scope.eventType);
					var attributeFilterExpressions = $scope.formData.attributeFilterExpressions;
					
					for (var i = 0; i < attributeFilterExpressions.length; i++) {
						delete attributeFilterExpressions[i].attributeDefinition;
					}
					var eventFilter = { 
							appliesToEventDefinition:json.uuid,
							attributeFilterExpressions:attributeFilterExpressions
			              };
					newPlanResource.$post('eventFilters', null, eventFilter).then();
					
					// Step 4: Create the Action resource with parameter values.
					var json = angular.fromJson($scope.actionType);
					var actionParameters = {};
					
					if ($scope.actionParameterDefinitions) {
						for (var i = 0; i < $scope.actionParameterDefinitions.length; i++) {
							actionParameters[$scope.actionParameterDefinitions[i].uuid] = $scope.actionParameterValues[i];
						}
					} 
					 
					var action = {
						appliesToActionDefinition:json.uuid,
						parameters:actionParameters
					};
					
					newPlanResource.$post('Actions', null, action);
					
					
				}).then( function() {
					// This is redirecting before the posts finish, causing them to be cancelled.
					window.location = "#/plans";
					//$route.updateParams("/plans");
				});
		} else {
			var plan = { 
	             	name:$scope.newPlanName,
	             	uuid:$scope.planUuid
	              };
			WebsiteService
			.updatePlanResource(plan).then(function (newPlanResource) {
				// Step 2: Create the Event Filter resource.
				var json = angular.fromJson($scope.eventType);
				
				var attributeFilterExpressions = $scope.formData.attributeFilterExpressions;
				
				for (var i = 0; i < attributeFilterExpressions.length; i++) {
					delete attributeFilterExpressions[i].attributeDefinition;
				}
				
				var eventFilter = { 
						uuid: $scope.eventFilterUuid,
						appliesToEventDefinition:json.uuid,
						attributeFilterExpressions:attributeFilterExpressions
		              };
				WebsiteService.updateEventFilter($scope.planUuid, eventFilter).then();
				
				var json = angular.fromJson($scope.actionType);
				var actionParameters = {};
				
				if ($scope.actionParameterDefinitions) {
					for (var i = 0; i < $scope.actionParameterDefinitions.length; i++) {
						actionParameters[$scope.actionParameterDefinitions[i].uuid] = $scope.actionParameterValues[i];
					}
				}
				
				var action = {
					uuid: $scope.actionUuid,
					appliesToActionDefinition:json.uuid,
					parameters:actionParameters
				};
				
				WebsiteService.updateAction($scope.planUuid, action);
			}).then( function() {
				// This is redirecting before the posts finish, causing them to be cancelled.
				window.location = "#/plans";
				//$route.updateParams("/plans");
			});
		}

	};
}]);

nicknackControllers.controller('ActionBookmarksCtrl', ['$scope', '$route', 'StaticDataService', 'WebsiteService', 'actions', 
    function ($scope, $route, StaticDataService, WebsiteService, actions) {
	
	$scope.actions = actions;
	
	$scope.runNow = function(actionUuid) {
		WebsiteService.runActionBookmark(actionUuid).then( function() {
			// Do something
		});
   	};
	
	
}]);

nicknackControllers.controller('ActionBookmarkCtrl', ['$scope', 'StaticDataService', 'WebsiteService', 'action', 'actionDefinitions',
                                                 function ($scope, StaticDataService, WebsiteService, action, actionDefinitions) {
	
	// Bind to the view
	$scope.action = action;
	$scope.actionDefinitions = actionDefinitions;
	
	// When an action definition is selected, update the parameter list and set the uuid
	$scope.onActionTypeChange = function() {
		$scope.action.appliesToActionDefinition = $scope.selectedActionDefinition.uuid;
		
   		var json = angular.fromJson($scope.selectedActionDefinition);
   		StaticDataService.parameterDefinitions(json.uuid).then(function (parameterDefinitions) {
			$scope.actionParameterDefinitions = parameterDefinitions;
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
		WebsiteService.runActionNow($scope.action).then( function() {
			// Do something
		});
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

   
nicknackControllers.controller('EventsCtrl', ['$scope', 'WebsiteService', 
                                                  function ($scope, WebsiteService) {
	
       
       $scope.events = [];

       var handleCallback = function (msg) {
           $scope.$apply(function () {
               var json = JSON.parse(msg.data);
               var attributes = [];
               for (var i = 0; i < json.eventDefinition.attributeDefinitions.length; i++) {
            	   var value = json.attributes[json.eventDefinition.attributeDefinitions[i].uuid];
            	   attributes.push(
            			   {
            				   "uuid":json.eventDefinition.attributeDefinitions[i].uuid,
            				   "name":json.eventDefinition.attributeDefinitions[i].name,
            				   "value":value
            			   }
            	   );
               }
               $scope.events.unshift({
            	  "eventName":json.eventDefinition.name,
            	  "created":json.created,
            	  "attributes":attributes
               });
           });
       };
	   
	   var source = new EventSource('/api/eventsStream');
       source.addEventListener('message', handleCallback, false);
   }]);
   