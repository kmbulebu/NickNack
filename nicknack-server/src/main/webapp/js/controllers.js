
var newplanApp = angular.module('newplanApp', ['newplanService']);

newplanApp.controller('NewPlanCtrl', ['$scope', 'WebsiteService', 
function ($scope, WebsiteService) {
	$scope.eventDefinitions = "test";
	WebsiteService
            .load()
            .then( function( websiteResource ) {
                return websiteResource.$get('EventDefinitions');
            })
            .then( function( blogPostList )
            {
            	$scope.eventDefinitions = blogPostList;
            })
            ;
}]);