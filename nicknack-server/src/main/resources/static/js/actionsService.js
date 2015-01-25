angular.module('actionsService', [ 'apiService' ]).factory(
		'ActionsService', [ 'ApiService', '$q', function(ApiService, $q) {

			return {
				'getActionDefinitions' : function() {
					var defer = $q.defer();
					var promise = defer.promise;
					
					ApiService.getApi().then(
						function(action) {
							action.$get('ActionDefinitions').then(
								function(success) {
									if (success.$has('ActionDefinitions')) {
										defer.resolve(success.$get('ActionDefinitions'));
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
				'getActionDefinition' : function(uuid) {
					var defer = $q.defer();
					var promise = defer.promise;

					ApiService.getApi().then(function(api) {
						api.$get('ActionDefinition', {uuid:uuid}).then(function(success) {
							defer.resolve(success);
						}, function(error) {
							defer.reject(error);
						});
					}, function(error) {
						defer.reject(error);
					});

					return promise;
				},
				'getAttributeDefinitions' : function(uuid) {
					var defer = $q.defer();
					var promise = defer.promise;
					
					this.getActionDefinition(uuid).then(
						function(success) {
							success.$get('AttributeDefinitions').then(
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
						}, function(error) {
							defer.reject(error);
						});
					return promise;
				}
			};

}]);
