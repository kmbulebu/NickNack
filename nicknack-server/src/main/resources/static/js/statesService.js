angular.module('statesService', [ 'apiService' ]).factory(
		'StatesService', [ 'ApiService', '$q', function(ApiService, $q) {

			return {
				'getStateDefinitions' : function() {
					var defer = $q.defer();
					var promise = defer.promise;
					
					ApiService.getApi().then(
						function(state) {
							state.$get('StateDefinitions').then(
								function(success) {
									if (success.$has('StateDefinitions')) {
										defer.resolve(success.$get('StateDefinitions'));
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
				'getStateDefinitionsByProvider' : function(provider) {
					var defer = $q.defer();
					var promise = defer.promise;
					
					provider.$get('StateDefinitions').then(
						function(success) {
							if (success.$has('StateDefinitions')) {
								defer.resolve(success.$get('StateDefinitions'));
							} else {
								defer.resolve([]);
							}
						}, function(error) {
							defer.reject(error);
						}
					);
					
					return promise;
				},
				'getStateDefinition' : function(uuid) {
					var defer = $q.defer();
					var promise = defer.promise;

					ApiService.getApi().then(function(api) {
						api.$get('StateDefinition', {uuid:uuid}).then(function(success) {
							defer.resolve(success);
						}, function(error) {
							defer.reject(error);
						});
					}, function(error) {
						defer.reject(error);
					});

					return promise;
				},
				'getAttributeDefinitions' : function(stateDefinition) {
					var defer = $q.defer();
					var promise = defer.promise;
					
					stateDefinition.$get('AttributeDefinitions').then(
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
