(function(){
'use strict';
var app = angular.module('my-app');

app.service('StatusReportService',
    ['$localStorage', '$http', '$q', 'urls',
        function ($localStorage, $http, $q, urls) {

            var factory = {
                loadStatusReport: loadStatusReport
            };

            return factory;

            function loadStatusReport(page, length, search , order, statuses, roles, userName, events, startDate, endDate) {
                console.log('Fetching all StatusReports');
                var deferred = $q.defer();
                var pageable = {
                     page:page,length:length, search:search, order:order, statusIds:statuses, roleIds:roles, userName:userName,eventIds:events, startDate:startDate,endDate:endDate,reportType:'Detailed'
                      };

                      var config = {
                       params: pageable,
                       headers : {'Accept' : 'application/json'}
                      };
            return    $http.get(urls.STATUS_REPORT_SERVICE_API,  config)
                    .then(
                        function (response) {
                            console.log('Fetched successfully   StatusReport');
                           return response;
                        },
                        function (errResponse) {
                            console.error('Error while loading StatusReport');
                          return errResponse;
                        }
                    );
            }

        }
    ]);
})();
