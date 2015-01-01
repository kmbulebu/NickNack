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

nicknackControllers.controller('NowCtrl', ['$scope', '$route', 'WebsiteService', 'StaticDataService', 'providers', 
                                             function ($scope, $route, WebsiteService, StaticDataService, providers) {  	
    $scope.providers = providers;
    var providerUuid = $route.current.params.providerUuid;
    
    if (providerUuid) {
    	// Select provider by UUID
    	for (i = 0; i < providers.length; i++) {
    		if (providers[i].uuid === providerUuid) {
    			$scope.provider = providers[i];
    		}
    	}
    	
    	// Load states
    	$scope.provider.$get('Stateses').then(function (page) {
    		if (page.$has('Stateses')) {
    			return page.$get('Stateses');
    		} else {
    			return [];
    		}
		}, function(error) {
			return [];
		}).then(function (resource) {
			$scope.stateses = resource;
		});
    }
    
    $scope.onProviderChange = function() {
    	window.location = '#/now/' + $scope.provider.uuid;
    }
    
    $scope.getAttributeName = function(state, attributeDefinitionUuid) {
    	var attributeDefinitions = state.stateDefinition.attributeDefinitions;
    	for (i = 0; i < attributeDefinitions.length; i++) {
    		if (attributeDefinitions[i].uuid === attributeDefinitionUuid) {
    			return attributeDefinitions[i].name;
    		}
    	}
    }
      	
}]);	

nicknackControllers.controller('NewPlanCtrl', ['$scope', '$rootScope', '$routeParams', '$route', 'WebsiteService', 'StaticDataService',
function ($scope, $rootScope, $routeParams, $route, WebsiteService, StaticDataService) {
	$scope.planUuid = $routeParams.planUuid;
	$scope.formData = {};
	$scope.formData.eventAttributeFilterExpressions = [];
	$scope.formData.stateAttributeFilterExpressions = [];
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
        					StaticDataService.eventAttributeDefinitions(eventDefinitionUuid).then(function (attributeDefinitions) {
        						$scope.eventAttributeDefinitions = attributeDefinitions;
        					
	        					$scope.formData.eventAttributeFilterExpressions = eventFilters[0].attributeFilterExpressions;
	    						for (i = 0; i < $scope.formData.eventAttributeFilterExpressions.length; i++) {
	    							for (j = 0; j < $scope.eventAttributeDefinitions.length; j++) {
	    								if ($scope.eventAttributeDefinitions[j].uuid === $scope.formData.eventAttributeFilterExpressions[i].attributeDefinitionUuid) {
	    									$scope.formData.eventAttributeFilterExpressions[i].attributeDefinition = $scope.eventAttributeDefinitions[j];
	    									break;
	    								}
	    							}
	    							
	    						}
        					});
        				});
        			});
        		});
        		
        		planResource.$get('stateFilters').then(function (stateFilters){
        			if (stateFilters.$has('StateFilters')) {
	        			stateFilters.$get('StateFilters').then( function(stateFilters) {
	        				var stateDefinitionUuid = stateFilters[0].appliesToStateDefinition;
	        				$scope.stateFilterUuid = stateFilters[0].uuid;
	        				// find it.
	        				StaticDataService.stateDefinition(stateDefinitionUuid).then(function (stateDef) {
	        					$scope.stateType = stateDef;
	        					StaticDataService.stateAttributeDefinitions(stateDefinitionUuid).then(function (attributeDefinitions) {
	        						$scope.stateAttributeDefinitions = attributeDefinitions;
	        					
		        					$scope.formData.stateAttributeFilterExpressions = stateFilters[0].attributeFilterExpressions;
		    						for (i = 0; i < $scope.formData.stateAttributeFilterExpressions.length; i++) {
		    							for (j = 0; j < $scope.stateAttributeDefinitions.length; j++) {
		    								if ($scope.stateAttributeDefinitions[j].uuid === $scope.formData.stateAttributeFilterExpressions[i].attributeDefinitionUuid) {
		    									$scope.formData.stateAttributeFilterExpressions[i].attributeDefinition = $scope.stateAttributeDefinitions[j];
		    									break;
		    								}
		    							}
		    							
		    						}
	        					});
	        				});
	        			});
        			}
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
		$scope.formData.stateAttributeFilters = [];
	} 
	
	StaticDataService.eventDefinitions().then(function (eventDefinitions) {
		$scope.eventDefinitions = eventDefinitions;
	});
	
	StaticDataService.stateDefinitions().then(function (stateDefinitions) {
		$scope.stateDefinitions = stateDefinitions;
	});
	
	StaticDataService.actionDefinitions().then(function (actionDefinitions) {
		$scope.actionDefinitions = actionDefinitions;
	});
	
	$scope.updateEventAttributeDefinitions = function() {
		var json = angular.fromJson($scope.eventType);
		StaticDataService.eventAttributeDefinitions(json.uuid).then(function (attributeDefinitions) {
			$scope.eventAttributeDefinitions = attributeDefinitions;
		});
	};
	
	$scope.updateStateAttributeDefinitions = function() {
		var json = angular.fromJson($scope.stateType);
		StaticDataService.stateAttributeDefinitions(json.uuid).then(function (attributeDefinitions) {
			$scope.stateAttributeDefinitions = attributeDefinitions;
		});
	};
	
	$scope.onEventAttributeFilterTypeChange = function() {
		$scope.formData.eventAttributeFilterExpressions[this.$index].attributeDefinitionUuid = $scope.formData.eventAttributeFilterExpressions[this.$index].attributeDefinition.uuid;
		
		// Get the list of attribute values.
		var index = this.$index;
		WebsiteService.getAttributeDefinitionValues($scope.eventType.uuid, $scope.formData.eventAttributeFilterExpressions[this.$index].attributeDefinition.uuid)
			.then(function(valuesResource) {
				var valueArray = [];
				for (var key in valuesResource.content) {
					valueArray.push(valuesResource.content[key]);
				}
				$scope.formData.eventAttributeFilterExpressions[this.$index].attributeDefinition.values = valueArray;
			});
	};
	
	$scope.onStateAttributeFilterTypeChange = function() {
		$scope.formData.stateAttributeFilterExpressions[this.$index].attributeDefinitionUuid = $scope.formData.stateAttributeFilterExpressions[this.$index].attributeDefinition.uuid;
		
		// Get the list of attribute values.
		var index = this.$index;
		WebsiteService.getAttributeDefinitionValues($scope.stateType.uuid, $scope.formData.stateAttributeFilterExpressions[this.$index].attributeDefinition.uuid)
			.then(function(valuesResource) {
				var valueArray = [];
				for (var key in valuesResource.content) {
					valueArray.push(valuesResource.content[key]);
				}
				$scope.formData.stateAttributeFilterExpressions[this.$index].attributeDefinition.values = valueArray;
			});
	};

	$scope.addEventAttributeFilter = function() {
		dummyFilter = { 
             	attributeDefinitionUuid: '',
             	operator: '',
             	operand:'',
             	attributeDefinition: {values:[], units:{supportedOperators:[]}}
              };
		$scope.formData.eventAttributeFilterExpressions.push(dummyFilter);
	};
	
	$scope.addStateAttributeFilter = function() {
		dummyFilter = { 
             	attributeDefinitionUuid: '',
             	operator: '',
             	operand:'',
             	attributeDefinition: {values:[], units:{supportedOperators:[]}}
              };
		$scope.formData.stateAttributeFilterExpressions.push(dummyFilter);
	};
	
	$scope.onActionTypeChange = function() {
		var json = angular.fromJson($scope.actionType);
		StaticDataService.parameterDefinitions(json.uuid).then(function (parameterDefinitions) {
			$scope.actionParameterDefinitions = parameterDefinitions;
		});
	};
	
	$scope.deleteEventAttributeFilter = function() {
		$scope.formData.eventAttributeFilterExpressions.splice(this.$index, 1);
	};
	
	$scope.deleteStateAttributeFilter = function() {
		$scope.formData.stateAttributeFilterExpressions.splice(this.$index, 1);
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
					var eventJson = angular.fromJson($scope.eventType);
					var eventAttributeFilterExpressions = $scope.formData.eventAttributeFilterExpressions;
					
					for (var i = 0; i < eventAttributeFilterExpressions.length; i++) {
						delete eventAttributeFilterExpressions[i].attributeDefinition;
					}
					var eventFilter = { 
							appliesToEventDefinition:eventJson.uuid,
							attributeFilterExpressions:eventAttributeFilterExpressions
			              };
					newPlanResource.$post('eventFilters', null, eventFilter).then();
					
					// Step 2: Create the State Filter resource.
					if ($scope.stateType) {
						var stateJson = angular.fromJson($scope.stateType);
						var stateAttributeFilterExpressions = $scope.formData.stateAttributeFilterExpressions;
						
						for (var i = 0; i < stateAttributeFilterExpressions.length; i++) {
							delete stateAttributeFilterExpressions[i].attributeDefinition;
						}
						var stateFilter = { 
								appliesToStateDefinition:stateJson.uuid,
								attributeFilterExpressions:stateAttributeFilterExpressions
				              };
						newPlanResource.$post('stateFilters', null, stateFilter).then();
					}
					
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
				var eventJson = angular.fromJson($scope.eventType);
				
				var eventAttributeFilterExpressions = $scope.formData.eventAttributeFilterExpressions;
				
				for (var i = 0; i < eventAttributeFilterExpressions.length; i++) {
					delete eventAttributeFilterExpressions[i].attributeDefinition;
				}
				
				var eventFilter = { 
						uuid: $scope.eventFilterUuid,
						appliesToEventDefinition:eventJson.uuid,
						attributeFilterExpressions:eventAttributeFilterExpressions
		              };
				WebsiteService.updateEventFilter($scope.planUuid, eventFilter).then();
				
				// Step 2: Create the State Filter resource.
				var stateJson = angular.fromJson($scope.stateType);
				
				var stateAttributeFilterExpressions = $scope.formData.stateAttributeFilterExpressions;
				
				for (var i = 0; i < stateAttributeFilterExpressions.length; i++) {
					delete stateAttributeFilterExpressions[i].attributeDefinition;
				}
				
				var stateFilter = { 
						uuid: $scope.stateFilterUuid,
						appliesToStateDefinition:stateJson.uuid,
						attributeFilterExpressions:stateAttributeFilterExpressions
		              };
				WebsiteService.updateStateFilter($scope.planUuid, stateFilter).then();
				
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
   