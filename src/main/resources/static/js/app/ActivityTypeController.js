'use strict';

app.controller('ActivityTypeController',
    ['ActivityTypeService', '$scope',  function( ActivityTypeService, $scope) {

    	
        var self = this;
        self.activityType = {};
        self.activityTypes=[];
        self.display =false;
        self.submit = submit;
        self.addActivityType = addActivityType;
        self.getAllActivityTypes = getAllActivityTypes;
        self.createActivityType = createActivityType;
        self.updateActivityType = updateActivityType;
        self.removeActivityType = removeActivityType;
        self.editActivityType = editActivityType;
        self.reset = reset;

        self.successMessage = '';
        self.errorMessage = '';
        self.done = false;

        self.onlyIntegers = /^\d+$/;
        self.onlyNumbers = /^\d+([,.]\d+)?$/;

        function submit() {
            console.log('Submitting');
            if (self.activityType.id === undefined || self.activityType.id === null) {
                console.log('Saving New ActivityType', self.activityType);
                createActivityType(self.activityType);
            } else {
                updateActivityType(self.activityType, self.activityType.id);
                console.log('ActivityType updated with id ', self.activityType.id);
            }
        }

        function createActivityType(activityType) {
            console.log('About to create activityType');
            ActivityTypeService.createActivityType(activityType)
                .then(
                    function (response) {
                        console.log('ActivityType created successfully');
                        self.successMessage = 'ActivityType created successfully';
                        self.errorMessage='';
                        self.done = true;
                        self.display =false;
                        self.activityType={};
                        $scope.myForm.$setPristine();
                    },
                    function (errResponse) {
                        console.error('Error while creating ActivityType');
                        self.errorMessage = 'Error while creating ActivityType: ' + errResponse.data.errorMessage;
                        self.successMessage='';
                    }
                );
        }


        function updateActivityType(activityType, id){
            console.log('About to update activityType');
            ActivityTypeService.updateActivityType(activityType, id)
                .then(
                    function (response){
                        console.log('ActivityType updated successfully');
                        self.successMessage='ActivityType updated successfully';
                        self.errorMessage='';
                        self.done = true;
                        self.display =false;
                        $scope.myForm.$setPristine();
                    },
                    function(errResponse){
                        console.error('Error while updating ActivityType');
                        self.errorMessage='Error while updating ActivityType '+errResponse.data;
                        self.successMessage='';
                    }
                );
        }


        function removeActivityType(id){
            console.log('About to remove ActivityType with id '+id);
            ActivityTypeService.removeActivityType(id)
                .then(
                    function(){
                        console.log('ActivityType '+id + ' removed successfully');
                    },
                    function(errResponse){
                        console.error('Error while removing activityType '+id +', Error :'+errResponse.data);
                    }
                );
        }


        function getAllActivityTypes(){
            return ActivityTypeService.getAllActivityTypes();
        }

        function editActivityType(id) {
            self.successMessage='';
            self.errorMessage='';
            ActivityTypeService.getActivityType(id).then(
                function (activityType) {
                    self.activityType = activityType;
                    self.display =true;
                },
                function (errResponse) {
                    console.error('Error while removing activityType ' + id + ', Error :' + errResponse.data);
                }
            );
        }
        
        function addActivityType() {
            self.successMessage='';
            self.errorMessage='';
            self.display =true;
        }
        
        
        function reset(){
            self.successMessage='';
            self.errorMessage='';
            self.activityType={};
            $scope.myForm.$setPristine(); //reset Form
        }
    }


    ]);