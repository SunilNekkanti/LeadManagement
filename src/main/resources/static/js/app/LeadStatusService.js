'use strict';

app.service('LeadStatusService',
    ['$localStorage', '$http', '$q', 'urls',
        function ($localStorage, $http, $q, urls) {

            var factory = {
                loadAllLeadStatuses: loadAllLeadStatuses,
                getAllLeadStatuses: getAllLeadStatuses,
                getLeadStatus: getLeadStatus,
                createLeadStatus: createLeadStatus,
                updateLeadStatus: updateLeadStatus,
                removeLeadStatus: removeLeadStatus
            };

            return factory;

            function loadAllLeadStatuses() {
                console.log('Fetching all LeadStatuss');
                var deferred = $q.defer();
                $http.get(urls.STATUS_SERVICE_API)
                    .then(
                        function (response) {
                            console.log('Fetched successfully all LeadStatuss');
                            $localStorage.LeadStatus = response.data;
                            deferred.resolve(response);
                        },
                        function (errResponse) {
                            console.error('Error while loading LeadStatuss');
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

            function getAllLeadStatuses(){
            	console.log('$localStorage.LeadStatuss');
                return $localStorage.LeadStatus;
            }

            function getLeadStatus(id) {
                console.log('Fetching LeadStatus with id :'+id);
                var deferred = $q.defer();
                $http.get(urls.STATUS_SERVICE_API + id)
                    .then(
                        function (response) {
                            console.log('Fetched successfully LeadStatus with id :'+id);
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            console.error('Error while loading user with id :'+id);
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

            function createLeadStatus(user) {
                console.log('Creating LeadStatus');
                var deferred = $q.defer();
                $http.post(urls.STATUS_SERVICE_API, user)
                    .then(
                        function (response) {
                            loadAllLeadStatuses();
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                           console.error('Error while creating LeadStatus : '+errResponse.data.errorMessage);
                           deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

            function updateLeadStatus(user, id) {
                console.log('Updating LeadStatus with id '+id);
                var deferred = $q.defer();
                $http.put(urls.STATUS_SERVICE_API + id, user)
                    .then(
                        function (response) {
                            loadAllLeadStatuses();
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            console.error('Error while updating LeadStatus with id :'+id);
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

            function removeLeadStatus(id) {
                console.log('Removing LeadStatus with id '+id);
                var deferred = $q.defer();
                $http.delete(urls.STATUS_SERVICE_API + id)
                    .then(
                        function (response) {
                            loadAllLeadStatuses();
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            console.error('Error while removing LeadStatus with id :'+id);
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

        }
    ]);