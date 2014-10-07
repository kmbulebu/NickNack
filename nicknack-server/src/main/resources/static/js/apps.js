var nicknackApp = angular.module('nicknackApp', ['angular-hal', 'ngRoute',
		'nicknackControllers', 'newplanService', 'restService', 'staticDataService']);

nicknackApp.config([ '$routeProvider', '$httpProvider', function($routeProvider, $httpProvider) {	
	$routeProvider.when('/plans', {
		templateUrl : 'partials/plans.html',
		controller : 'PlansCtrl'
	}).when('/newPlan', {
		templateUrl : 'partials/new_plan.html',
		controller : 'NewPlanCtrl'
	}).when('/newPlan/:planUuid', {
		templateUrl : 'partials/new_plan.html',
		controller : 'NewPlanCtrl'
	}).when('/runAction', {
		templateUrl : 'partials/run_action.html',
		controller : 'RunActionCtrl'
	}).when('/liveEvents', {
		templateUrl : 'partials/events.html',
		controller : 'EventsCtrl'
	}).when('/home', {
		templateUrl : 'partials/home.html',
		controller : ''
	}).otherwise({
		redirectTo : '/home'
	});
} ]);

