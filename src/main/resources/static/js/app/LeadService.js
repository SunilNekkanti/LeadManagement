'use strict';

app.service('LeadService',
    ['$localStorage', '$http', '$q', 'urls',
        function ($localStorage, $http, $q, urls) {

            var factory = {
                loadAllLeads: loadAllLeads,
                loadLeads: loadLeads,
                getAllLeads: getAllLeads,
                getLead: getLead,
                createLead: createLead,
                updateLead: updateLead,
                removeLead: removeLead
            };

            return factory;

            function loadAllLeads() {
                console.log('Fetching all leads');
                var deferred = $q.defer();
                var pageable = {
                  		 page:0, size:10
                  		};

                  		var config = {
                  		 params: pageable,
                  		 headers : {'Accept' : 'application/json'}
                  		};
                $http.get(urls.LEAD_SERVICE_API,  config)
                    .then(
                        function (response) {
                            console.log('Fetched successfully all leads');
                            $localStorage.leads = response.data.content;
                            deferred.resolve(response.data.content);
                        },
                        function (errResponse) {
                            console.error('Error while loading leads');
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }
            
            function loadLeads(draw, length, search, order) {
                console.log('Fetching  leads');
                var pageable = {
                  		 page:draw, size:length
                  		};

                  		var config = {
                  		 params: pageable,
                  		 headers : {'Accept' : 'application/json'}
                  		};
            return     $http.get(urls.LEAD_SERVICE_API,  config)
                    .then(
                        function (response) {
                            console.log('Fetched successfully  leads');
                         return     response ;
                        },
                        function (errResponse) {
                            console.error('Error while loading leads');
                            return   errResponse ;
                        }
                    );
            }

            function getAllLeads(){
            	console.log('$localStorage.leads'+$localStorage.leads);
                return $localStorage.leads;
            }

            function getLead(id) {
                console.log('Fetching Lead with id :'+id);
                var deferred = $q.defer();
                $http.get(urls.LEAD_SERVICE_API + id)
                    .then(
                        function (response) {
                            console.log('Fetched successfully Lead with id :'+id);
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            console.error('Error while loading lead with id :'+id);
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

            function createLead(lead) {
                console.log('Creating Lead');
                var deferred = $q.defer();
                $http.post(urls.LEAD_SERVICE_API, lead)
                    .then(
                        function (response) {
                            loadAllLeads();
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                           console.error('Error while creating Lead : '+errResponse.data.errorMessage);
                           deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

            function updateLead(lead, id) {
                console.log('Updating Lead with id '+id);
                var deferred = $q.defer();
                $http.put(urls.LEAD_SERVICE_API + id, lead)
                    .then(
                        function (response) {
                            loadAllLeads();
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            console.error('Error while updating Lead with id :'+id);
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

            function removeLead(id) {
                console.log('Removing Lead with id '+id);
                var deferred = $q.defer();
                $http.delete(urls.LEAD_SERVICE_API + id)
                    .then(
                        function (response) {
                            loadAllLeads();
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            console.error('Error while removing Lead with id :'+id);
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

        }
    ]);