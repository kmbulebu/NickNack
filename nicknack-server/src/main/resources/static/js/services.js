angular.module('newplanService', ['angular-hal']).factory('WebsiteService', [ 'halClient', function(halClient) {

    return {
    	'loadPlans' :
            function() {
                return halClient.$get('api/plans');
            },
        'deletePlan' :
            function(uuid) {
                return halClient.$del('api/plans/' + uuid);
            },
        'loadEventDefinitions' :
            function() {
                return halClient.$get('api/eventDefinitions');
            },
         'loadAttributeDefinitions' :
                function(uuid) {
                    return halClient.$get('api/eventDefinitions/' + uuid + '/attributeDefinitions');
                },
         'loadActionDefinitions' :
                function() {
                    return halClient.$get('api/actionDefinitions/');
                },
        'loadParameterDefinitions' :
                function(uuid) {
                    return halClient.$get('api/actionDefinitions/' + uuid + '/parameterDefinitions');
                },
         'createPlanResource' :
                    function(plan) {
                        return halClient.$post('api/plans/', null, plan);
                    },
    };
}]);