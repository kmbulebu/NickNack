var nicknackControllers = angular.module('nicknackControllers', ['staticDataService']);

nicknackControllers.controller('PlansCtrl', ['$scope', 'WebsiteService', 'StaticDataService', 
                                       function ($scope, WebsiteService, StaticDataService) {
	
	$scope.plans = {};
	
	$scope.refresh = function() {
		StaticDataService.plans().then(function (plans) {
			$scope.plans = plans;
		});
	};
	
	$scope.refresh();
	
	$scope.deletePlan = function(planUuid) {
		WebsiteService.deletePlan(planUuid).then(function () {
			$scope.refresh();
		});
		
	};
	
}]);	

nicknackControllers.controller('NewPlanCtrl', ['$scope', '$rootScope', '$routeParams', 'WebsiteService', 'StaticDataService',
function ($scope, $rootScope, $routeParams, WebsiteService, StaticDataService) {
	$scope.planUuid = $routeParams.planUuid;
	$scope.formData = {};
	$scope.formData.eventAttributeFilters = [];
	$scope.eventAttributeType = [];
	$scope.actionParameterValues = [];
	$scope.formData.deletedAttributeFilters = [];
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
        					});
	        				eventFilters[0].$get('attributeFilters').then(function(attFiltersResource) {
	        					attFiltersResource.$get('AttributeFilters').then(function(attFilters) {
	        						for (i = 0; i < attFilters.length; i++) {
	        							$scope.formData.eventAttributeFilters.push({ 
	        								uuid: attFilters[i].uuid,
	             	                    	appliesToAttributeDefinition: attFilters[i].appliesToAttributeDefinition,
	             	                    	operator: attFilters[i].operator,
	             	                    	operand: attFilters[i].operand
	            	                    });
	        							for (j = 0; j < $scope.eventAttributeDefinitions.length; j++) {
	        								if ($scope.eventAttributeDefinitions[j].uuid === attFilters[i].appliesToAttributeDefinition) {
	        									$scope.eventAttributeType[i]= $scope.eventAttributeDefinitions[j];
	        									$scope.eventAttributeOperators = $scope.eventAttributeType[i].units.supportedOperators;
	        									break;
	        								}
	        							}
	        							
	        						}
	        					});
	        				});
        				});
        			});
        		});
        		planResource.$get('actions').then(function (actions){
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
		if (this.eventAttributeType[this.$index]) {
			var json = angular.fromJson(this.eventAttributeType[this.$index]);
			$scope.formData.eventAttributeFilters[this.$index].appliesToAttributeDefinition = json.uuid;
			$scope.eventAttributeOperators = json.units.supportedOperators;
		}
	};
	
	$scope.addEventAttributeFilter = function() {
		$scope.formData.eventAttributeFilters.push({ 
	                 	                    	appliesToAttributeDefinition: '',
	                 	                    	operator: '',
	                 	                    	operand:''
	                	                      });
	};
	
	$scope.onActionTypeChange = function() {
		var json = angular.fromJson($scope.actionType);
		StaticDataService.parameterDefinitions(json.uuid).then(function (parameterDefinitions) {
			$scope.actionParameterDefinitions = parameterDefinitions;
		});
	};
	
	$scope.deleteEventAttributeFilter = function() {
		if ($scope.formData.eventAttributeFilters[this.$index].uuid) {
			$scope.formData.deletedAttributeFilters.push($scope.formData.eventAttributeFilters[this.$index].uuid);
		}
		$scope.formData.eventAttributeFilters.splice(this.$index, 1);
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
					var eventFilter = { 
							appliesToEventDefinition:json.uuid
			              };
					newPlanResource.$post('eventFilters', null, eventFilter).then(function (newEventResource) {
						// Step 3: Create each of the Attribute Filter resources.
						$scope.formData.eventAttributeFilters.forEach(function(entry) {
							newEventResource.$post('attributeFilters', null, entry);
						});
						
					});
					
					// Step 4: Create the Action resource with parameter values.
					var json = angular.fromJson($scope.actionType);
					var actionParameters = {};
					
					for (var i = 0; i < $scope.actionParameterDefinitions.length; i++) {
						actionParameters[$scope.actionParameterDefinitions[i].uuid] = $scope.actionParameterValues[i];
					}
					
					var action = {
						appliesToActionDefinition:json.uuid,
						parameters:actionParameters
					};
					
					newPlanResource.$post('actions', null, action);
					
					
				}).then( function() {
					// This is redirecting before the posts finish, causing them to be cancelled.
					//window.location = "plans.html";
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
				var eventFilter = { 
						uuid: $scope.eventFilterUuid,
						appliesToEventDefinition:json.uuid
		              };
				WebsiteService.updateEventFilter($scope.planUuid, eventFilter).then(function (newEventResource) {
					$scope.formData.eventAttributeFilters.forEach(function(entry) {
						if (entry.uuid) {
							WebsiteService.updateAttributeFilter($scope.planUuid, eventFilter.uuid, entry);
						} else {
							newEventResource.$post('attributeFilters', null, entry);
						}
						
					});
					
					$scope.formData.deletedAttributeFilters.forEach(function(attributeFilterUuid) {
						WebsiteService.deleteAttributeFilter($scope.planUuid, eventFilter.uuid, attributeFilterUuid);
					});
					
				});
				
				var json = angular.fromJson($scope.actionType);
				var actionParameters = {};
				
				for (var i = 0; i < $scope.actionParameterDefinitions.length; i++) {
					actionParameters[$scope.actionParameterDefinitions[i].uuid] = $scope.actionParameterValues[i];
				}
				
				var action = {
					uuid: $scope.actionUuid,
					appliesToActionDefinition:json.uuid,
					parameters:actionParameters
				};
				
				WebsiteService.updateAction($scope.planUuid, action);
			}).then( function() {
				// This is redirecting before the posts finish, causing them to be cancelled.
				//window.location = "plans.html";
			});
		}

	};
}]);


nicknackControllers.controller('RunActionCtrl', ['$scope', 'StaticDataService', 'WebsiteService', 
     function ($scope, StaticDataService, WebsiteService) {
     	$scope.actionParameterValues = {};
     	$scope.formData = {};
     
     	StaticDataService.actionDefinitions().then(function (actionDefinitions) {
     		$scope.actionDefinitions = actionDefinitions;
     	});
   
   	$scope.onActionTypeChange = function() {
   		var json = angular.fromJson($scope.actionType);
   		StaticDataService.parameterDefinitions(json.uuid).then(function (parameterDefinitions) {
			$scope.actionParameterDefinitions = parameterDefinitions;
		});
   	};
   	
   	$scope.submit = function() {
		var json = angular.fromJson($scope.actionType);
		var actionParameters = {};
		
		for (var i = 0; i < $scope.actionParameterDefinitions.length; i++) {
			actionParameters[$scope.actionParameterDefinitions[i].uuid] = $scope.actionParameterValues[i];
		}
		
		var action = {
			appliesToActionDefinition:json.uuid,
			parameters:actionParameters
		};
		
		WebsiteService.runActionNow(action).then( function() {
			// Do something
		});

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
   