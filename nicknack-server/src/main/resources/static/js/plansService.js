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
				'getPlan' : function(uuid) {
					var defer = $q.defer();
					var promise = defer.promise;

					ApiService.getApi().then(
						function(api) {
							api.$get('Plan', {uuid:uuid}).then(
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
				},
				'getEventFilters' : function(planUuid) {
					var defer = $q.defer();
					var promise = defer.promise;

					this.getPlan(planUuid).then(
						function(planResource) {
							planResource.$get('EventFilters').then(
								function(success) {
									if (success.$has('EventFilters')) {
										defer.resolve(success.$get('EventFilters'));
									} else {
										defer.resolve([]);
									}
								}, function(error) {
									defer.reject(error);
								}
							);
						},
						function(error) {
							defer.reject(error);
						}
					);
								
					return promise;
				},
				'getStateFilters' : function(planUuid) {
					var defer = $q.defer();
					var promise = defer.promise;

					this.getPlan(planUuid).then(
						function(planResource) {
							planResource.$get('StateFilters').then(
								function(success) {
									if (success.$has('StateFilters')) {
										defer.resolve(success.$get('StateFilters'));
									} else {
										defer.resolve([]);
									}
								}, function(error) {
									defer.reject(error);
								}
							);
						},
						function(error) {
							defer.reject(error);
						}
					);
								
					return promise;
				},
				'getActions' : function(planUuid) {
					var defer = $q.defer();
					var promise = defer.promise;

					this.getPlan(planUuid).then(
						function(planResource) {
							planResource.$get('Actions').then(
								function(success) {
									if (success.$has('Actions')) {
										defer.resolve(success.$get('Actions'));
									} else {
										defer.resolve([]);
									}
								}, function(error) {
									defer.reject(error);
								}
							);
						},
						function(error) {
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
				'createAction' : function(planResource, action) {
					var defer = $q.defer();
					var promise = defer.promise;
					
					planResource.$post('Actions', null, action).then(
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
				},
				'updatePlan' : function(plan) {
					var defer = $q.defer();
					var promise = defer.promise;
	
					plan.$put('self', null, plan).then(
						function(success) {
							// Success
							defer.resolve(success);
						}, function(error) {
							defer.reject(error);
						}
					);
					
					return promise;
				},
				'updateEventFilter' : function(eventFilterResource, newEventFilter) {
					var defer = $q.defer();
					var promise = defer.promise;
					
					eventFilterResource.$put('self', null, newEventFilter).then(
						function(success) {
							// Success
							defer.resolve(success);
						}, function(error) {
							defer.reject(error);
						}
					);

					return promise;
				},
				'updateStateFilter' : function(stateFilterResource, newStateFilter) {
					var defer = $q.defer();
					var promise = defer.promise;
					
					stateFilterResource.$put('self', null, newStateFilter).then(
						function(success) {
							// Success
							defer.resolve(success);
						}, function(error) {
							defer.reject(error);
						}
					);

					return promise;
				},
				'updateAction' : function(actionResource, newAction) {
					var defer = $q.defer();
					var promise = defer.promise;
					
					actionResource.$put('self', null, newAction).then(
						function(success) {
							// Success
							defer.resolve(success);
						}, function(error) {
							defer.reject(error);
						}
					);
					
					return promise;
				},
				'deleteEventFilter' : function(eventFilterResource) {
					var defer = $q.defer();
					var promise = defer.promise;
					
					eventFilterResource.$del('self').then(
						function(success) {
							// Success
							defer.resolve(success);
						}, function(error) {
							defer.reject(error);
						}
					);

					return promise;
				},
				'deleteStateFilter' : function(stateFilterResource) {
					var defer = $q.defer();
					var promise = defer.promise;
					
					stateFilterResource.$del('self').then(
						function(success) {
							// Success
							defer.resolve(success);
						}, function(error) {
							defer.reject(error);
						}
					);

					return promise;
				},
				'deleteAction' : function(actionResource) {
					var defer = $q.defer();
					var promise = defer.promise;
					
					actionResource.$del('self').then(
						function(success) {
							// Success
							defer.resolve(success);
						}, function(error) {
							defer.reject(error);
						}
					);
					
					return promise;
				},
				'updateCompletePlan' : function(plan, eventFilters, stateFilters, actions, deletedEventFilters, deletedStateFilters, deletedActions) {
					var defer = $q.defer();
					var promise = defer.promise;
					var self = this;
					
					
					
					var promises = [];
					// Update Plan
					promises.push(self.updatePlan(plan));
					// Update event filters
					
					angular.forEach(eventFilters, function(entry) {
						if (entry.resource.uuid) {
							promises.push(self.updateEventFilter(entry.resource, entry.data));
						} else {
							promises.push(self.createEventFilter(plan, entry.data));
						}
					});
					// Update state filters
					angular.forEach(stateFilters, function(entry) {
						if (entry.resource.uuid) {
							promises.push(self.updateStateFilter(entry.resource, entry.data));
						} else {
							promises.push(self.createStateFilter(plan, entry.data));
						}
					});
					// Update actions
					angular.forEach(actions, function(entry) {
						if (entry.resource.uuid) {
							promises.push(self.updateAction(entry.resource, entry.data));
						} else {
							promises.push(self.createAction(plan, entry.data));
						}
					});
					// Delete event filters
					angular.forEach(deletedEventFilters, function(eventFilter) {
						promises.push(self.deleteEventFilter(eventFilter));
					});
					// Delete state filters
					angular.forEach(deletedStateFilters, function(stateFilter) {
						promises.push(self.deleteStateFilter(stateFilter));
					});
					// Delete actions
					angular.forEach(deletedActions, function(action) {
						promises.push(self.deleteAction(action));
					});
					// Create new stuff
					
					// Wait for all these actions to complete before resolving
					$q.all(promises).then(
						function(success) {
							defer.resolve(success);
						},
						function(error) {
							defer.reject(error);
						}
					);
				
					return promise;
				}
				
			};

}]);
