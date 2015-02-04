var nicknackControllers = angular.module('nicknackControllers', ['staticDataService']);

nicknackControllers.controller('ProvidersCtrl', ['$scope', '$route', 'providers', 
                                             function ($scope, $route, providers) {
      	
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

nicknackControllers.controller('NowCtrl', ['$scope', '$route', 'providers', 
                                             function ($scope, $route, providers) {  	
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

nicknackControllers.controller('EventsCtrl', ['$scope', 
                                                  function ($scope) {
       
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
   
