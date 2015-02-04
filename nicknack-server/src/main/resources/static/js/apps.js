var nicknackApp = angular.module('nicknackApp', ['angular-hal', 'ngRoute', 'mgo-angular-wizard',
		'nicknackControllers', 'actionsControllers', 'plansControllers', 'plansService', 'restService', 'providersService', 'eventsService', 'statesService', 'staticDataService', 'websiteService', 'actionsService']);

nicknackApp.config([ '$routeProvider', '$httpProvider', function($routeProvider, $httpProvider) {	
	$routeProvider.when('/liveEvents', {
		templateUrl : 'partials/events.html',
		controller : 'EventsCtrl'
	}).when('/now/:providerUuid?', {
		templateUrl : 'partials/now.html',
		controller : 'NowCtrl',
		resolve: {
			providers: function(ProvidersService) {
				return ProvidersService.getProviders();
			}
		}
	}).when('/home', {
		templateUrl : 'partials/home.html',
		controller : ''
	}).when('/plans', {
		templateUrl : 'partials/plans.html',
		controller : 'PlansCtrl',
		resolve: {
			plans: function(PlansService) {
				return PlansService.getPlans();
			}
		}
	}).when('/actionBookmarks', {
		templateUrl : 'partials/actions.html',
		controller : 'ActionBookmarksCtrl',
		resolve: {
			actions: function($route, RestService) {
				return RestService
					.api()
					.then(function (apiResource) {
						return apiResource.$get('Actions');
				}).then(function (resource) {
					if (resource.$has('Actions')) {
						return resource.$get('Actions');
					} else {
						return [];
					}
				});
			}
		}
	}).when('/actionBookmarks/:actionUuid', {
		templateUrl : 'partials/action.html',
		controller : 'ActionBookmarkCtrl',
		resolve: {
			action: function($route, WebsiteService) {
					return WebsiteService
						.getActionBookmark($route.current.params.actionUuid);
				},
			actionDefinitions: function($route, StaticDataService) {
					return StaticDataService.actionDefinitions();
				}
			}
	}).when('/newActionBookmark/:actionDefinitionUuid?', {
		templateUrl : 'partials/action.html',
		controller : 'ActionBookmarkCtrl',
		resolve: {
			action: function() {
					var newAction = {
							parameters: {}
					}
					return newAction;
			},
			actionDefinitions: function($route, ActionsService) {
					return ActionsService.getActionDefinitions();
			},
			attributeDefinitions: function($route, ActionsService) {
				if ($route.current.params.actionDefinitionUuid) {
					return ActionsService.getAttributeDefinitions($route.current.params.actionDefinitionUuid)
				} else {
					return [];
				}
			}
		}
	}).when('/providers', {
		templateUrl : 'partials/providers.html',
		controller : 'ProvidersCtrl',
		resolve: {
			providers: function(ProvidersService) {
				return ProvidersService.getProviders();
			}
		}
	}).when('/providers/:uuid/settings', {
		templateUrl : 'partials/providerSettings.html',
		controller : 'ProviderSettingsCtrl',
		resolve: {
			provider: function($route, ProvidersService) {
				return ProvidersService.getProvider($route.current.params.uuid);
			},
			providerSettingDefinitions: function($route, ProvidersService) {
				return ProvidersService.getProviderSettingDefinitions($route.current.params.uuid);
			},
			providerSettings: function($route, ProvidersService) {
				return ProvidersService.getProviderSettings($route.current.params.uuid);
			},
		}
	}).when('/plan/:uuid?', {
		templateUrl : 'partials/plan_wizard.html',
		controller : 'PlanCtrl',
		resolve: {
			providers: function(ProvidersService) {
				return ProvidersService.getProviders();
			},
			plan: function($route, PlansService) {
				if ($route.current.params.uuid) {
					return PlansService.getPlan($route.current.params.uuid);
				} else {
					return {};
				}
			},
			eventFilters: function($route, PlansService) {
				if ($route.current.params.uuid) {
					return PlansService.getEventFilters($route.current.params.uuid);
				} else {
					return [{
							attributeFilterExpressions:[]
						}];
				}
			},
			stateFilters: function($route, PlansService) {
				if ($route.current.params.uuid) {
					return PlansService.getStateFilters($route.current.params.uuid);
				} else {
					return [];
				}
			},
			actions: function($route, PlansService) {
				if ($route.current.params.uuid) {
					return PlansService.getActions($route.current.params.uuid);
				} else {
					return [{}];
				}
			}
			
		}
	})
	.otherwise({
		redirectTo : '/home'
	});
} ]);

