'use strict';

app.service('BrokerageService',
    ['$localStorage', '$http', '$q', 'urls',
        function ($localStorage, $http, $q, urls) {

            var factory = {
                loadAllBrokerages: loadAllBrokerages,
                getAllBrokerages: getAllBrokerages,
                getBrokerage: getBrokerage,
                createBrokerage: createBrokerage,
                updateBrokerage: updateBrokerage,
                removeBrokerage: removeBrokerage
            };

            return factory;

            function loadAllBrokerages() {
                console.log('Fetching all brokerages');
                var deferred = $q.defer();
                $http.get(urls.BROKERAGE_SERVICE_API)
                    .then(
                        function (response) {
                            console.log('Fetched successfully all brokerages');
                            $localStorage.brokerages = response.data;
                            deferred.resolve(response);
                        },
                        function (errResponse) {
                            console.error('Error while loading brokerages');
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

            function getAllBrokerages(){
            	console.log('$localStorage.brokerages'+$localStorage.brokerages);
                return $localStorage.brokerages;
            }

            function getBrokerage(id) {
                console.log('Fetching Brokerage with id :'+id);
                var deferred = $q.defer();
                $http.get(urls.BROKERAGE_SERVICE_API + id)
                    .then(
                        function (response) {
                            console.log('Fetched successfully Brokerage with id :'+id);
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            console.error('Error while loading user with id :'+id);
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

            function createBrokerage(user) {
                console.log('Creating Brokerage');
                var deferred = $q.defer();
                $http.post(urls.BROKERAGE_SERVICE_API, user)
                    .then(
                        function (response) {
                            loadAllBrokerages();
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                           console.error('Error while creating Brokerage : '+errResponse.data.errorMessage);
                           deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

            function updateBrokerage(user, id) {
                console.log('Updating Brokerage with id '+id);
                var deferred = $q.defer();
                $http.put(urls.BROKERAGE_SERVICE_API + id, user)
                    .then(
                        function (response) {
                            loadAllBrokerages();
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            console.error('Error while updating Brokerage with id :'+id);
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

            function removeBrokerage(id) {
                console.log('Removing Brokerage with id '+id);
                var deferred = $q.defer();
                $http.delete(urls.BROKERAGE_SERVICE_API + id)
                    .then(
                        function (response) {
                            loadAllBrokerages();
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            console.error('Error while removing Brokerage with id :'+id);
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

        }
    ]);