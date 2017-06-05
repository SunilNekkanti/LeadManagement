'use strict';

app.service('LeadLanguageService',
    ['$localStorage', '$http', '$q', 'urls',
        function ($localStorage, $http, $q, urls) {

            var factory = {
                loadAllLeadLanguages: loadAllLeadLanguages,
                getAllLeadLanguages: getAllLeadLanguages,
                getLeadLanguage: getLeadLanguage,
                createLeadLanguage: createLeadLanguage,
                updateLeadLanguage: updateLeadLanguage,
                removeLeadLanguage: removeLeadLanguage
            };

            return factory;

            function loadAllLeadLanguages() {
                console.log('Fetching all LeadLanguages');
                var deferred = $q.defer();
                $http.get(urls.LANGUAGE_SERVICE_API)
                    .then(
                        function (response) {
                            console.log('Fetched successfully all LeadLanguages');
                            $localStorage.LeadLanguages = response.data;
                            deferred.resolve(response);
                        },
                        function (errResponse) {
                            console.error('Error while loading LeadLanguages');
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

            function getAllLeadLanguages(){
            	console.log('$localStorage.LeadLanguages'+$localStorage.LeadLanguages);
                return $localStorage.LeadLanguages;
            }

            function getLeadLanguage(id) {
                console.log('Fetching LeadLanguage with id :'+id);
                var deferred = $q.defer();
                $http.get(urls.LANGUAGE_SERVICE_API + id)
                    .then(
                        function (response) {
                            console.log('Fetched successfully LeadLanguage with id :'+id);
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            console.error('Error while loading user with id :'+id);
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

            function createLeadLanguage(user) {
                console.log('Creating LeadLanguage');
                var deferred = $q.defer();
                $http.post(urls.LANGUAGE_SERVICE_API, user)
                    .then(
                        function (response) {
                            loadAllLeadLanguages();
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                           console.error('Error while creating LeadLanguage : '+errResponse.data.errorMessage);
                           deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

            function updateLeadLanguage(user, id) {
                console.log('Updating LeadLanguage with id '+id);
                var deferred = $q.defer();
                $http.put(urls.LANGUAGE_SERVICE_API + id, user)
                    .then(
                        function (response) {
                            loadAllLeadLanguages();
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            console.error('Error while updating LeadLanguage with id :'+id);
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

            function removeLeadLanguage(id) {
                console.log('Removing LeadLanguage with id '+id);
                var deferred = $q.defer();
                $http.delete(urls.LANGUAGE_SERVICE_API + id)
                    .then(
                        function (response) {
                            loadAllLeadLanguages();
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            console.error('Error while removing LeadLanguage with id :'+id);
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

        }
    ]);