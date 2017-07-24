'use strict';

app.service('EventTemplateService',
    ['$localStorage', '$http', '$q', 'urls',
        function ($localStorage, $http, $q, urls) {

            var factory = {
                loadAllEventTemplates: loadAllEventTemplates,
                loadEventTemplates: loadEventTemplates,
                getAllEventTemplates: getAllEventTemplates,
                getEventTemplate: getEventTemplate,
                createEventTemplate: createEventTemplate,
                updateEventTemplate: updateEventTemplate,
                removeEventTemplate: removeEventTemplate
            };

            return factory;

            function loadAllEventTemplates() {
                console.log('Fetching all events');
                var deferred = $q.defer();
                var pageable = {
                  		 page:0, size:10
                  		};

                  		var config = {
                  		 params: pageable,
                  		 headers : {'Accept' : 'application/json'}
                  		};
                $http.get(urls.EVENT_TEMPLATE_SERVICE_API,  config)
                    .then(
                        function (response) {
                            console.log('Fetched successfully all eventTemplates');
                            $localStorage.eventTemplates = response.data.content;
                            deferred.resolve(response);
                        },
                        function (errResponse) {
                            console.error('Error while loading eventTemplates');
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }
            
            function loadEventTemplates(draw, length, search, order) {
                console.log('Fetching  events');
                var deferred = $q.defer();
                var pageable = {
                  		 page:draw, size:length
                  		};

                  		var config = {
                  		 params: pageable,
                  		 headers : {'Accept' : 'application/json'}
                  		};
                 $http.get(urls.EVENT_TEMPLATE_SERVICE_API,  config)
                    .then(
                        function (response) {
                            $localStorage.eventTemplates = response.data.content;
                               deferred.resolve(response);
                      //   return     response ;
                        },
                        function (errResponse) {
                            console.error('Error while loading eventTemplates');
                           deferred.reject(errResponse);
                    //       return   errResponse ;
                        }
                    );
            return deferred.promise;
            }

            function getAllEventTemplates(){
            	console.log('$localStorage.events'+$localStorage.events);
                return $localStorage.eventTemplates;
            }

            function getEventTemplate(id) {
                console.log('Fetching EventTemplate with id :'+id);
                var deferred = $q.defer();
                $http.get(urls.EVENT_TEMPLATE_SERVICE_API + id)
                    .then(
                        function (response) {
                            console.log('Fetched successfully EventTemplate with id :'+id);
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            console.error('Error while loading eventTemplate with id :'+id);
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

            function createEventTemplate(event) {
                console.log('Creating EventTemplate');
                var deferred = $q.defer();
                $http.post(urls.EVENT_TEMPLATE_SERVICE_API, event)
                    .then(
                        function (response) {
                        	loadEventTemplates(0,10,'',null);
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                           console.error('Error while creating EventTemplate : '+errResponse.data.errorMessage);
                           deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

            function updateEventTemplate(event, id) {
                console.log('Updating EventTemplate with id '+id);
                var deferred = $q.defer();
                $http.put(urls.EVENT_TEMPLATE_SERVICE_API + id, event)
                    .then(
                        function (response) {
                            loadEventTemplates(0,10,'',null);
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            console.error('Error while updating EventTemplate with id :'+id);
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

            function removeEventTemplate(id) {
                console.log('Removing EventTemplate with id '+id);
                var deferred = $q.defer();
                $http.delete(urls.EVENT_TEMPLATE_SERVICE_API + id)
                    .then(
                        function (response) {
                        	loadEventTemplates(0,10,'',null);
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            console.error('Error while removing EventTemplate with id :'+id);
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

        }
    ]);