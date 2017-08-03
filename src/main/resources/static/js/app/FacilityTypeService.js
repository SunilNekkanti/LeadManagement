'use strict';

app.service('FacilityTypeService',
    ['$localStorage', '$http', '$q', 'urls',
        function ($localStorage, $http, $q, urls) {

            var factory = {
                loadAllFacilityTypes: loadAllFacilityTypes,
                getAllFacilityTypes: getAllFacilityTypes,
                getFacilityType: getFacilityType,
                createFacilityType: createFacilityType,
                updateFacilityType: updateFacilityType,
                removeFacilityType: removeFacilityType
            };

            return factory;

            function loadAllFacilityTypes() {
                console.log('Fetching all facilityTypes');
                var deferred = $q.defer();
                $http.get(urls.FACILITYTYPE_SERVICE_API)
                    .then(
                        function (response) {
                            console.log('Fetched successfully all facilityTypes');
                            $localStorage.facilityTypes = response.data;
                            deferred.resolve(response);
                        },
                        function (errResponse) {
                            console.error('Error while loading facilityTypes');
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

            function getAllFacilityTypes(){
            	console.log('$localStorage.facilityTypes'+$localStorage.facilityTypes);
                return $localStorage.facilityTypes;
            }

            function getFacilityType(id) {
                console.log('Fetching FacilityType with id :'+id);
                var deferred = $q.defer();
                $http.get(urls.FACILITYTYPE_SERVICE_API + id)
                    .then(
                        function (response) {
                            console.log('Fetched successfully FacilityType with id :'+id);
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            console.error('Error while loading user with id :'+id);
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

            function createFacilityType(user) {
                console.log('Creating FacilityType');
                var deferred = $q.defer();
                $http.post(urls.FACILITYTYPE_SERVICE_API, user)
                    .then(
                        function (response) {
                            loadAllFacilityTypes();
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                           console.error('Error while creating FacilityType : '+errResponse.data.errorMessage);
                           deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

            function updateFacilityType(user, id) {
                console.log('Updating FacilityType with id '+id);
                var deferred = $q.defer();
                $http.put(urls.FACILITYTYPE_SERVICE_API + id, user)
                    .then(
                        function (response) {
                            loadAllFacilityTypes();
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            console.error('Error while updating FacilityType with id :'+id);
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

            function removeFacilityType(id) {
                console.log('Removing FacilityType with id '+id);
                var deferred = $q.defer();
                $http.delete(urls.FACILITYTYPE_SERVICE_API + id)
                    .then(
                        function (response) {
                            loadAllFacilityTypes();
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            console.error('Error while removing FacilityType with id :'+id);
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

        }
    ]);