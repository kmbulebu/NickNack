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

nicknackControllers.controller('ProvidersCtrl', ['$scope', '$route', 'WebsiteService', 'StaticDataService', 'providers', 
                                             function ($scope, $route, WebsiteService, StaticDataService, providers) {
      	
	$scope.providers = providers;

      	
}]);


nicknackControllers.controller('ProviderSettingsCtrl', ['$scope', '$route', 'provider', 'providerSettingDefinitions', 'providerSettings',
                                             function ($scope, $route, provider, providerSettingDefinitions, providerSettings) {
      	
	$scope.provider = provider;
	$scope.settingDefinitions = providerSettingDefinitions;
	$scope.settings = providerSettings;
	
	$scope.save = function() {
		$scope.settings.$post('self', null, $scope.settings).then(
			function(success) {
				window.location = '#/providers/';
			}
		); 
	}
}]);

nicknackControllers.directive('settingInput', [function() {
	return {
		scope: {},
		restrict: 'AE',
		replace: 'true',
		template: '<input type="{{inputType}}"/>',
		link: function(scope, elem, attrs) {
			 var definition = angular.fromJson(attrs.definition);
			 var settingType = definition.settingType;
			 if (settingType.name === 'hostname') {
				 scope.inputType = 'text';
				 
			 } else {
			 	 scope.inputType = settingType.name;
			 	
			 }
			 elem.attr('required', definition.required);
			 //elem.attr('placeholder', definition.name);
			 if (settingType.maximumLength) {
				 elem.attr('maxLength', settingType.maximumLength);
			 }
			 if (settingType.regexPattern) {
				 elem.attr('pattern', settingType.regexPattern);
			 }
			 if (settingType.min) {
		 		 elem.attr('min', settingType.min);
		 	 }
		 	if (settingType.max) {
		 		 elem.attr('max', settingType.max);
		 	 }
		 	if (settingType.step) {
		 		 elem.attr('step', settingType.step);
		 	 }
		 	// TODO Add datalist for valueList
		}
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
	$scope.stateEnabled = false;
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
        				$scope.stateEnabled = true;
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
        							$scope.actionParameterValues[i] = actions[0].attributes[$scope.actionParameterDefinitions[i].uuid];
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
	
	$scope.enableState = function() {
		$scope.stateEnabled = true;
	};
	
	$scope.disableState = function() {
		$scope.stateEnabled = false;
	}
	
	$scope.updateEventAttributeDefinitions = function(eventType) {
		StaticDataService.eventAttributeDefinitions(eventType.uuid).then(function (attributeDefinitions) {
			$scope.eventAttributeDefinitions = attributeDefinitions;
		});
	};
	
	$scope.updateStateAttributeDefinitions = function(stateType) {
		StaticDataService.stateAttributeDefinitions(stateType.uuid).then(function (attributeDefinitions) {
			$scope.stateAttributeDefinitions = attributeDefinitions;
		});
	};
	
	$scope.onEventAttributeFilterTypeChange = function(eventAttributeFilterExpression, eventDefinition, attributeDefinition) {
		eventAttributeFilterExpression.attributeDefinitionUuid = attributeDefinition.uuid;
		// Get the list of attribute values.
		WebsiteService.getEventAttributeDefinitionValues(eventDefinition.uuid, attributeDefinition.uuid)
			.then(function(valuesResource) {
				var valueArray = [];
				for (var key in valuesResource.content) {
					valueArray.push(valuesResource.content[key]);
				}
				attributeDefinition.values = valueArray;
			});
	};
	
	
	$scope.onStateAttributeFilterTypeChange = function(stateAttributeFilterExpression, stateDefinition, attributeDefinition) {
		stateAttributeFilterExpression.attributeDefinitionUuid = attributeDefinition.uuid;
		// Get the list of attribute values.
		WebsiteService.getStateAttributeDefinitionValues(stateDefinition.uuid, attributeDefinition.uuid)
			.then(function(valuesResource) {
				var valueArray = [];
				for (var key in valuesResource.content) {
					valueArray.push(valuesResource.content[key]);
				}
				attributeDefinition.values = valueArray;
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
						eventAttributeFilterExpressions[i].attributeDefinitionUuid = eventAttributeFilterExpressions[i].attributeDefinition.uuid;
						delete eventAttributeFilterExpressions[i].attributeDefinition;
					}
					var eventFilter = { 
							appliesToEventDefinition:eventJson.uuid,
							attributeFilterExpressions:eventAttributeFilterExpressions
			              };
					newPlanResource.$post('eventFilters', null, eventFilter).then();
					
					// Step 2: Create the State Filter resource.
					if ($scope.stateType && $scope.stateEnabled) {
						var stateJson = angular.fromJson($scope.stateType);
						var stateAttributeFilterExpressions = $scope.formData.stateAttributeFilterExpressions;
						
						for (var i = 0; i < stateAttributeFilterExpressions.length; i++) {
							stateAttributeFilterExpressions[i].attributeDefinitionUuid = stateAttributeFilterExpressions[i].attributeDefinition.uuid;
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
						attributes:actionParameters
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
					eventAttributeFilterExpressions[i].attributeDefinitionUuid = eventAttributeFilterExpressions[i].attributeDefinition.uuid;
					delete eventAttributeFilterExpressions[i].attributeDefinition;
				}
				
				var eventFilter = { 
						uuid: $scope.eventFilterUuid,
						appliesToEventDefinition:eventJson.uuid,
						attributeFilterExpressions:eventAttributeFilterExpressions
		              };
				WebsiteService.updateEventFilter($scope.planUuid, eventFilter).then();
				
				// Step 2: Create the State Filter resource.
				if ($scope.stateEnabled && $scope.stateType) {
					var stateJson = angular.fromJson($scope.stateType);
					
					var stateAttributeFilterExpressions = $scope.formData.stateAttributeFilterExpressions;
					
					for (var i = 0; i < stateAttributeFilterExpressions.length; i++) {
						stateAttributeFilterExpressions[i].attributeDefinitionUuid = stateAttributeFilterExpressions[i].attributeDefinition.uuid;
						delete stateAttributeFilterExpressions[i].attributeDefinition;
					}
					
					var stateFilter = { 
							uuid: $scope.stateFilterUuid,
							appliesToStateDefinition:stateJson.uuid,
							attributeFilterExpressions:stateAttributeFilterExpressions
			              };
					WebsiteService.updateStateFilter($scope.planUuid, stateFilter).then();
				} else {
					WebsiteService.deleteStateFilter($scope.planUuid, $scope.stateFilterUuid).then();
				}
				
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
					attributes:actionParameters
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
   
