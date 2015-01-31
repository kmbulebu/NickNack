angular.module('plansService', [ 'apiService' ]).factory(
		'PlansService', [ 'ApiService', '$q', function(ApiService, $q) {

			return {
				'getPlans' : function() {
					var defer = $q.defer();
					var promise = defer.promise;
					
					ApiService.getApi().then(
						function(api) {
							api.$get('Plans').then(
								function(success) {
									if (success.$has('Plans')) {
										defer.resolve(success.$get('Plans'));
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
				'createPlan' : function(plan) {
					var defer = $q.defer();
					var promise = defer.promise;
					
					ApiService.getApi().then(
						function(api) {
							api.$post('Plans', null, plan).then(
								function(success) {
									// Success
									defer.resolve(success);
								}, function(error) {
									defer.reject(error);
								}
							);
						}, function(error) {
							// Could not get api resource.
							defer.reject(error);
						}
					);
					
					return promise;
				},
				'createEventFilter' : function(planResource, eventFilter) {
					var defer = $q.defer();
					var promise = defer.promise;
					
					planResource.$post('EventFilters', null, eventFilter).then(
						function(success) {
							// Success
							defer.resolve(success);
						}, function(error) {
							defer.reject(error);
						}
					);

					return promise;
				},
				'createStateFilter' : function(planResource, stateFilter) {
					var defer = $q.defer();
					var promise = defer.promise;
					
					planResource.$post('StateFilters', null, stateFilter).then(
						function(success) {
							// Success
							defer.resolve(success);
						}, function(error) {
							defer.reject(error);
						}
					);

					return promise;
				},
				'createAction' : function(planResource, actions) {
					var defer = $q.defer();
					var promise = defer.promise;
					
					planResource.$post('Actions', null, actions).then(
						function(success) {
							// Success
							defer.resolve(success);
						}, function(error) {
							defer.reject(error);
						}
					);
					
					return promise;
				},
				'createCompletePlan' : function(plan, eventFilters, stateFilters, actions) {
					var defer = $q.defer();
					var promise = defer.promise;
					var self = this;
					
					self.createPlan(plan).then(
						function(planResource) {
							var promises = [];
							// Create event filters
							angular.forEach(eventFilters, function(eventFilter) {
								promises.push(self.createEventFilter(planResource, eventFilter));
							});
							// Create state filters
							angular.forEach(stateFilters, function(stateFilter) {
								promises.push(self.createStateFilter(planResource, stateFilter));
							});
							// Create actions
							angular.forEach(actions, function(action) {
								promises.push(self.createAction(planResource, action));
							});
							// Wait for all these posts to complete before resolving
							$q.all(promises).then(
								function(success) {
									defer.resolve(planResource);
								},
								function(error) {
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
