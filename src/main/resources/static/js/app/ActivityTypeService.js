'use strict';

app.service('ActivityTypeService',
    ['$localStorage', '$http', '$q', 'urls',
        function ($localStorage, $http, $q, urls) {

            var factory = {
                loadAllActivityTypes: loadAllActivityTypes,
                getAllActivityTypes: getAllActivityTypes,
                getActivityType: getActivityType,
                createActivityType: createActivityType,
                updateActivityType: updateActivityType,
                removeActivityType: removeActivityType
            };

            return factory;

            function loadAllActivityTypes() {
                console.log('Fetching all activityTypes');
                var deferred = $q.defer();
                $http.get(urls.ACTIVITYTYPE_SERVICE_API)
                    .then(
                        function (response) {
                            console.log('Fetched successfully all activityTypes');
                            $localStorage.activityTypes = response.data;
                            deferred.resolve(response);
                        },
                        function (errResponse) {
                            console.error('Error while loading activityTypes');
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

            function getAllActivityTypes(){
            	console.log('$localStorage.activityTypes'+$localStorage.activityTypes);
                return $localStorage.activityTypes;
            }

            function getActivityType(id) {
                console.log('Fetching ActivityType with id :'+id);
                var deferred = $q.defer();
                $http.get(urls.ACTIVITYTYPE_SERVICE_API + id)
                    .then(
                        function (response) {
                            console.log('Fetched successfully ActivityType with id :'+id);
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            console.error('Error while loading user with id :'+id);
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

            function createActivityType(user) {
                console.log('Creating ActivityType');
                var deferred = $q.defer();
                $http.post(urls.ACTIVITYTYPE_SERVICE_API, user)
                    .then(
                        function (response) {
                            loadAllActivityTypes();
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                           console.error('Error while creating ActivityType : '+errResponse.data.errorMessage);
                           deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

            function updateActivityType(user, id) {
                console.log('Updating ActivityType with id '+id);
                var deferred = $q.defer();
                $http.put(urls.ACTIVITYTYPE_SERVICE_API + id, user)
                    .then(
                        function (response) {
                            loadAllActivityTypes();
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            console.error('Error while updating ActivityType with id :'+id);
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

            function removeActivityType(id) {
                console.log('Removing ActivityType with id '+id);
                var deferred = $q.defer();
                $http.delete(urls.ACTIVITYTYPE_SERVICE_API + id)
                    .then(
                        function (response) {
                            loadAllActivityTypes();
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            console.error('Error while removing ActivityType with id :'+id);
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

        }
    ]);