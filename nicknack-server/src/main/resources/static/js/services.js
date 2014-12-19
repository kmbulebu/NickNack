angular.module('newplanService', ['angular-hal']).factory('WebsiteService', [ 'halClient', function(halClient) {

    return {
        'deletePlan' :
            function(uuid) {
                return halClient.$del('api/plans/' + uuid);
            },
         'createPlanResource' :
            function(plan) {
                return halClient.$post('api/plans/', null, plan);
            },
        'updatePlanResource' :
            function(plan) {
                return halClient.$put('api/plans/' + plan.uuid, null, plan);
            },
        'updateEventFilter' :
            function(planUuid, eventFilter) {
                return halClient.$put('api/plans/' + planUuid + "/eventFilters/" + eventFilter.uuid , null, eventFilter);
            },
        'updateAttributeFilter' :
            function(planUuid, eventFilterUuid, attributeFilter) {
                return halClient.$put('api/plans/' + planUuid + "/eventFilters/" + eventFilterUuid + "/attributeFilters/" + attributeFilter.uuid, null, attributeFilter);
            },
        'deleteAttributeFilter' :
            function(planUuid, eventFilterUuid, attributeFilterUuid) {
                return halClient.$del('api/plans/' + planUuid + "/eventFilters/" + eventFilterUuid + "/attributeFilters/" + attributeFilterUuid, null);
            },
        'updateAction' :
            function(planUuid, action) {
                return halClient.$put("api/actions/" + action.uuid , null, action);
            },
        'runActionNow' :
            function(action) {
                return halClient.$post('api/actionQueue/', null, action);
            },
        'getAttributeDefinitionValues' :
            function(eventDefinitionUuid, attributeDefinitionUuid) {
                return halClient.$get('api/eventDefinitions/' + eventDefinitionUuid + "/attributeDefinitions/" + attributeDefinitionUuid + "/values");
            }
    };
}]);

angular.module('restService', ['angular-hal']).factory('RestService', [ 'halClient', '$rootScope', function(halClient, $rootScope) {
	
	return {
    	'api' :
            function() {
    			if ($rootScope.webServiceApi == undefined) {
    				$rootScope.webServiceApi = halClient.$get('api');
    			}
    			return $rootScope.webServiceApi;
            },
        'path' :
        	function(path) {
        		return halClient.$get('api/' + path);
        }
	};
           
}]);

angular.module('staticDataService', ['angular-hal']).factory('StaticDataService', [ 'halClient', 'RestService', '$rootScope', '$q', function(halClient, RestService, $rootScope, $q, StaticDataService) {
	
	$rootScope.eventDefinitions = undefined;
	$rootScope.attributeDefinitions = {};
	$rootScope.parameterDefinitions = {};
	$rootScope.actionDefinitions = undefined;
	
	return {
		// Returns promise of an array of Event Definitions
		'eventDefinitions': function() {
			if ($rootScope.eventDefinitions === undefined) {
				return RestService.api().then(function (apiResource) {
					// Successful get
					return apiResource.$get('EventDefinitions').then(function (eventDefinitionsResource) {
							return eventDefinitionsResource.$get('EventDefinitions').then(function (eventDefinitions) {
								$rootScope.eventDefinitions = eventDefinitions;
								return $rootScope.eventDefinitions;
							});
					});
				});
			} else {
				return $q.when($rootScope.eventDefinitions);
			}
		},
		'eventDefinition': function(uuid) {
			var promise = this.eventDefinitions();
			return promise.then(function(eventDefinitions) {
				for (i = 0; i < eventDefinitions.length; i++) {
					if (eventDefinitions[i].uuid === uuid) {
						return eventDefinitions[i];
					}
				}
			});
		}
		// Returns promise of an array of Attribute Definitions for the given Event Definition.
		,'attributeDefinitions': function(eventDefUuid) {
			if (eventDefUuid in $rootScope.attributeDefinitions) {
				return $q.when($rootScope.attributeDefinitions[eventDefUuid]);
			} else {
				for (i = 0; i < $rootScope.eventDefinitions.length; i++) {
					if ($rootScope.eventDefinitions[i].uuid === eventDefUuid) {
						return $rootScope.eventDefinitions[i].$get('attributeDefinitions').then(function (attributeDefinitionsResource) {
							return attributeDefinitionsResource.$get('AttributeDefinitions')
								.then(function(attributeDefinitions) {
									$rootScope.attributeDefinitions[eventDefUuid] = attributeDefinitions;
									return $q.when($rootScope.attributeDefinitions[eventDefUuid]);
								});
						});
					}
				};
			}
		},
		// Returns promise of an array of Action Definitions
		'actionDefinitions': function() {
			if ($rootScope.actionDefinitions === undefined) {
				return RestService.api().then(function (apiResource) {
					// Successful get
					return apiResource.$get('ActionDefinitions').then(function (actionDefinitionsResource) {
							return actionDefinitionsResource.$get('ActionDefinitions').then(function (actionDefinitions) {
								$rootScope.actionDefinitions = actionDefinitions;
								return $rootScope.actionDefinitions;
							});
					});
				});
			} else {
				return $q.when($rootScope.actionDefinitions);
			}
		},
		'actionDefinition': function(uuid) {
			var promise = this.actionDefinitions();
			return promise.then(function(actionDefinitions) {
				for (i = 0; i < actionDefinitions.length; i++) {
					if (actionDefinitions[i].uuid === uuid) {
						return actionDefinitions[i];
					}
				}
			});
		}
		// Returns promise of an array of Parameter Definitions for the given Event Definition.
		,'parameterDefinitions': function(actionDefUuid) {
			if (actionDefUuid in $rootScope.parameterDefinitions) {
				return $q.when($rootScope.parameterDefinitions[actionDefUuid]);
			} else {
				for (i = 0; i < $rootScope.actionDefinitions.length; i++) {
					if ($rootScope.actionDefinitions[i].uuid === actionDefUuid) {
						return $rootScope.actionDefinitions[i].$get('parameterDefinitions').then(function (parameterDefinitionsResource) {
							if (parameterDefinitionsResource.$has('ParameterDefinitions')) {
								return parameterDefinitionsResource.$get('ParameterDefinitions')
									.then(function(parameterDefinitions) {
										$rootScope.parameterDefinitions[actionDefUuid] = parameterDefinitions;
										return $q.when($rootScope.parameterDefinitions[actionDefUuid]);
									});
							} else {
								$rootScope.parameterDefinitions[actionDefUuid] = [];
								return $q.when($rootScope.parameterDefinitions[actionDefUuid]);
							}
						});
					}
				};
			}
		},
		// Returns promise of an array of Plans
		'plans': function() {
			return RestService.api().then(function (apiResource) {
				// Successful get
				return apiResource.$get('Plans').then(function (plansResource) {
						return plansResource.$get('Plans').then(function (plans) {
							return plans;
						});
				});
			});
		},
		// Returns promise of a single plan
		'plan': function(uuid) {
			return RestService.path('plans/' + uuid).then(function (planResource) {
				// Successful get
				return planResource;
			});
		}
	};
}]);




