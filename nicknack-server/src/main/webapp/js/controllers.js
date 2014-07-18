
var newplanApp = angular.module('newplanApp', ['newplanService']);

newplanApp.controller('NewPlanCtrl', ['$scope', 'WebsiteService', 
function ($scope, WebsiteService) {
	$scope.eventDefinitions = "test";
	WebsiteService
            .loadEventDefinitions()
            .then( function( websiteResource ) {
                return websiteResource.$get('EventDefinitions');
            })
            .then( function( eventDefinitionList )
            {
            	$scope.eventDefinitions = eventDefinitionList;
            })
            ;
	$scope.updateAttributeDefinitions = function() {
		WebsiteService
        .loadAttributeDefinitions($scope.eventType)
        .then( function(websiteResource) {
                return websiteResource.$get('AttributeDefinitions');
            })
        .then(function( attributeDefinitionList )
        {
        	$scope.eventAttributeDefinitions = attributeDefinitionList;
        })
        ;
	};
}]);