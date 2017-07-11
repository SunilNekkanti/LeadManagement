'use strict';
   
    app.service('FileUploadService', ['$http', '$q', 'urls',function ($http, $q, urls) {

        this.uploadFileToUrl = function (file, lead) {
            //FormData, object of key/value pair for form fields and values
            var fileFormData = new FormData();
            fileFormData.append('file', file);
          //  fileFormData.append('lead',  JSON.stringify(lead));
             
            var deffered = $q.defer();
             		
            $http.post(urls.FILE_UPLOADER, fileFormData, {
                transformRequest: angular.identity,
                headers: {'Content-Type': undefined}

            }).success(function (response) {
                deffered.resolve(response);

            }).error(function (response) {
                deffered.reject(response);
            });

            return deffered.promise;
        }
   }
    ]);