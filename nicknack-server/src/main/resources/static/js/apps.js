var nicknackApp = angular.module('nicknackApp', [ 'ngRoute',
		'nicknackControllers', 'newplanService' ]);

nicknackApp.config([ '$routeProvider', function($routeProvider) {
	$routeProvider.when('/plans', {
		templateUrl : 'partials/plans.html',
		controller : 'PlansCtrl'
	}).when('/newPlan', {
		templateUrl : 'partials/new_plan.html',
		controller : 'NewPlanCtrl'
	}).when('/runAction', {
		templateUrl : 'partials/run_action.html',
		controller : 'RunActionCtrl'
	}).otherwise({
		redirectTo : '/plans'
	});
} ]);