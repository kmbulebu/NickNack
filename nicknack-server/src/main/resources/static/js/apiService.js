angular.module('apiService', ['angular-hal']).factory('ApiService', [ 'halClient', '$q', function(halClient, $q) {
    
    return {
        'getApi' :
            function() {
                var apiDefer = $q.defer();
                var apiPromise = apiDefer.promise;
                
                halClient.$get('api/', null).then(
            		function(success) {
            			apiDefer.resolve(success);
            		}, function(error) {
            			apiDefer.reject(error);
            		}
                );
                
                return apiPromise;
            }
    };
           
}]);
