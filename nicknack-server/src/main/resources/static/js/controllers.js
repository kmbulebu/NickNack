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
        				// find it.
        				StaticDataService.eventDefinition(eventDefinitionUuid).then(function (eventDef) {
        					$scope.eventType = eventDef;
        				});
        			});
        		});
        		planResource.$get('actions').then(function (actions){
        			actions.$get('Actions').then( function(actions) {
        				var actionDefUuid = actions[0].appliesToActionDefinition;
        				// find it.
        				StaticDataService.actionDefinition(actionDefUuid).then(function (actionDef) {
        					$scope.actionType = actionDef;
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
		$scope.actionParameterValues = {};
		$scope.formData = {};
		$scope.newPlanName = "";
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
		if (this.eventAttributeType) {
			var json = angular.fromJson(this.eventAttributeType);
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
		$scope.formData.eventAttributeFilters.splice(this.$index, 1);
	};
	
	$scope.submit = function() {
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

	};
}]);


nicknackControllers.controller('RunActionCtrl', ['$scope', 'StaticDataService', 
     function ($scope, StaticDataService) {
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
   