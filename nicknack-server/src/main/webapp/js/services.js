angular.module('newplanService', ['angular-hal']).factory('WebsiteService', [ 'halClient', function(halClient) {

    return {
        'loadEventDefinitions' :
            function() {
                return halClient.$get('api/eventDefinitions');
            },
         'loadAttributeDefinitions' :
                function(uuid) {
                    return halClient.$get('api/eventDefinitions/' + uuid + "/attributeDefinitions");
                },
    };
}]);
