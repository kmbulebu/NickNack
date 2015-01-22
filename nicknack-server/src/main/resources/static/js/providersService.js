angular.module('providersService', [ 'apiService' ]).factory(
		'ProvidersService', [ 'ApiService', '$q', function(ApiService, $q) {

			return {
				'getProviders' : function() {
					var defer = $q.defer();
					var promise = defer.promise;

					ApiService.getApi().then(function(api) {
						api.$get('Providers').then(function(success) {
							if (success.$has('Providers')) {
								defer.resolve(success.$get('Providers'));
							} else {
								defer.resolve([]);
							}
						}, function(error) {
							defer.reject(error);
						});
					}, function(error) {
						defer.reject(error);
					});

					return promise;
				},
				'getProvider' : function(uuid) {
					var defer = $q.defer();
					var promise = defer.promise;

					ApiService.getApi().then(function(api) {
						api.$get('Provider', {uuid:uuid}).then(function(success) {
							defer.resolve(success);
						}, function(error) {
							defer.reject(error);
						});
					}, function(error) {
						defer.reject(error);
					});

					return promise;
				},
				'getProviderSettingDefinitions' : function(uuid) {
					var defer = $q.defer();
					var promise = defer.promise;
					
					this.getProvider(uuid).then(
						function(provider) {
							provider.$get('ProviderSettingDefinitions').then(
								function(success) {
									if (success.settingDefinitions) {
										defer.resolve(success.settingDefinitions);
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
				'getProviderSettings' : function(uuid) {
					var defer = $q.defer();
					var promise = defer.promise;
					
					this.getProvider(uuid).then(
						function(provider) {
							provider.$get('ProviderSettings').then(
								function(success) {
									defer.resolve(success);
								}, function(error) {
									defer.reject(error);
								}
							);
						}, function(error) {
							defer.reject(error);
						}
					);
					
					return promise;
				}
			};

}]);
