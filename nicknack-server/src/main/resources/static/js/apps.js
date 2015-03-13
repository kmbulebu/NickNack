var nicknackApp = angular.module('nicknackApp', ['angular-hal', 'ngRoute',
		'nicknackControllers', 'newplanService', 'restService', 'staticDataService']);

nicknackApp.config([ '$routeProvider', '$httpProvider', function($routeProvider, $httpProvider) {	
	$routeProvider.when('/newPlan', {
		templateUrl : 'partials/plan.html',
		controller : 'NewPlanCtrl'
	}).when('/plans/:planUuid', {
		templateUrl : 'partials/plan.html',
		controller : 'NewPlanCtrl'
	}).when('/liveEvents', {
		templateUrl : 'partials/events.html',
		controller : 'EventsCtrl'
	}).when('/now/:providerUuid?', {
		templateUrl : 'partials/now.html',
		controller : 'NowCtrl',
		resolve: {
			providers: function($route, RestService) {
				return RestService
					.api()
					.then(function (apiResource) {
						return apiResource.$get('Providers');
				}).then(function (resource) {
					if (resource.$has('Providers')) {
						return resource.$get('Providers');
					} else {
						return [];
					}
				});
			}
		}
	}).when('/home', {
		templateUrl : 'partials/home.html',
		controller : ''
	}).when('/plans', {
		templateUrl : 'partials/plans.html',
		controller : 'PlansCtrl',
		resolve: {
			plans: function($route, RestService) {
				return RestService
					.api()
					.then(function (apiResource) {
						return apiResource.$get('Plans');
				}).then(function (resource) {
					if (resource.$has('Plans')) {
						return resource.$get('Plans');
					} else {
						return [];
					}
				});
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
	}).when('/newActionBookmark', {
		templateUrl : 'partials/action.html',
		controller : 'ActionBookmarkCtrl',
		resolve: {
			action: function() {
					var newAction = {
							parameters: {}
					}
					return newAction;
				},
			actionDefinitions: function($route, StaticDataService) {
					return StaticDataService.actionDefinitions();
				}
			}
	}).otherwise({
		redirectTo : '/home'
	});
} ]);

