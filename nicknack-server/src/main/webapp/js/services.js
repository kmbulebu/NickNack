angular.module('newplanService', ['angular-hal']).factory('WebsiteService', [ 'halClient', function(halClient) {

    return {
        'load' :
            function() {
                return halClient.$get('api/eventDefinitions');
            },
    };
}]);
