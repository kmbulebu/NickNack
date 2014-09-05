var nicknackControllers = angular.module('nicknackControllers', []);

nicknackControllers.controller('PlansCtrl', ['$scope', 'WebsiteService', 
                                       function ($scope, WebsiteService) {
	
	$scope.plans = {};
	
	$scope.refresh = function() {
		WebsiteService
	    .loadPlans()
	    .then( function( websiteResource ) {
	    	if (websiteResource.$has('Plans')) 
	    		return websiteResource.$get('Plans');
	    })
	    .then( function( plansList )
	    {
	    	$scope.plans = plansList;
	    });
	};
	
	$scope.refresh();
	
	$scope.deletePlan = function(planUuid) {
		WebsiteService.deletePlan(planUuid).then(function () {
			$scope.refresh();
		});
		
	};
	
}]);	

nicknackControllers.controller('NewPlanCtrl', ['$scope', 'WebsiteService', 
function ($scope, WebsiteService) {
	$scope.actionParameterValues = {};
	$scope.formData = {};
	$scope.newPlanName = 'New Plan';
	$scope.formData.eventAttributeFilters = [
	                 	                    { 
	                 	                    	appliesToAttributeDefinition: '',
	                 	                    	operator: '',
	                 	                    	operand:''
	                	                      }
	                	                  ];
	
	WebsiteService
            .loadEventDefinitions()
            .then( function( websiteResource ) {
                return websiteResource.$get('EventDefinitions');
            })
            .then( function( eventDefinitionList )
            {
            	$scope.eventDefinitions = eventDefinitionList;
            })
            ;
	WebsiteService
	    .loadActionDefinitions()
	    .then( function( websiteResource ) {
	        return websiteResource.$get('ActionDefinitions');
	    })
	    .then( function( actionDefinitionList )
	    {
	    	$scope.actionDefinitions = actionDefinitionList;
	    })
	    ;
	$scope.updateAttributeDefinitions = function() {
		var json = angular.fromJson($scope.eventType);
		WebsiteService
        .loadAttributeDefinitions(json.uuid)
        .then( function(websiteResource) {
                return websiteResource.$get('AttributeDefinitions');
            })
        .then(function( attributeDefinitionList )
        {
        	$scope.eventAttributeDefinitions = attributeDefinitionList;
         })
        ;
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
		WebsiteService
        .loadParameterDefinitions(json.uuid)
        .then( function(websiteResource) {
                return websiteResource.$get('ParameterDefinitions');
            })
        .then(function( parameterDefinitionList )
        {
        	$scope.actionParameterDefinitions = parameterDefinitionList;
         })
        ;
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


nicknackControllers.controller('RunActionCtrl', ['$scope', 'WebsiteService', 
   function ($scope, WebsiteService) {
   	$scope.actionParameterValues = {};
   	$scope.formData = {};
   
   	WebsiteService
   	    .loadActionDefinitions()
   	    .then( function( websiteResource ) {
   	        return websiteResource.$get('ActionDefinitions');
   	    })
   	    .then( function( actionDefinitionList )
   	    {
   	    	$scope.actionDefinitions = actionDefinitionList;
   	    })
   	    ;
   	
   	$scope.onActionTypeChange = function() {
   		var json = angular.fromJson($scope.actionType);
   		WebsiteService
           .loadParameterDefinitions(json.uuid)
           .then( function(websiteResource) {
                   return websiteResource.$get('ParameterDefinitions');
               })
           .then(function( parameterDefinitionList )
           {
           	$scope.actionParameterDefinitions = parameterDefinitionList;
            })
           ;
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
   