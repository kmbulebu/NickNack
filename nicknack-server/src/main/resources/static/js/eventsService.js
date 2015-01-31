angular.module('eventsService', [ 'apiService' ]).factory(
		'EventsService', [ 'ApiService', '$q', function(ApiService, $q) {

			return {
				'getEventDefinitions' : function() {
					var defer = $q.defer();
					var promise = defer.promise;
					
					ApiService.getApi().then(
						function(event) {
							event.$get('EventDefinitions').then(
								function(success) {
									if (success.$has('EventDefinitions')) {
										defer.resolve(success.$get('EventDefinitions'));
									} else {
										defer.resolve([]);
									}
								}, function(error) {
									defer.reject(error);
								}
							);
						}, function(error) {
							defer.reject(error);
						}
					);
					
					return promise;
				},
				'getEventDefinitionsByProvider' : function(provider) {
					var defer = $q.defer();
					var promise = defer.promise;
					
					provider.$get('EventDefinitions').then(
						function(success) {
							if (success.$has('EventDefinitions')) {
								defer.resolve(success.$get('EventDefinitions'));
							} else {
								defer.resolve([]);
							}
						}, function(error) {
							defer.reject(error);
						}
					);
					
					return promise;
				},
				'getEventDefinition' : function(uuid) {
					var defer = $q.defer();
					var promise = defer.promise;

					ApiService.getApi().then(function(api) {
						api.$get('EventDefinition', {uuid:uuid}).then(function(success) {
							defer.resolve(success);
						}, function(error) {
							defer.reject(error);
						});
					}, function(error) {
						defer.reject(error);
					});

					return promise;
				},
				'getAttributeDefinitions' : function(eventDefinition) {
					var defer = $q.defer();
					var promise = defer.promise;
					
					eventDefinition.$get('AttributeDefinitions').then(
						function(success) {
							if (success.$has('AttributeDefinitions')) {
								defer.resolve(success.$get('AttributeDefinitions'));
							} else {
								defer.resolve([]);
							}
						}, function(error) {
							defer.reject(error);
						}
					);
	
					return promise;
				}
			};

}]);
