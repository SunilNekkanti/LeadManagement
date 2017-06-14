'use strict';

app.controller('FacilityTypeController',
    ['FacilityTypeService', '$scope',  function( FacilityTypeService, $scope) {

    	
        var self = this;
        self.facilityType = {};
        self.facilityTypes=[];
        self.display =false;
        self.submit = submit;
        self.addFacilityType = addFacilityType;
        self.getAllFacilityTypes = getAllFacilityTypes;
        self.createFacilityType = createFacilityType;
        self.updateFacilityType = updateFacilityType;
        self.removeFacilityType = removeFacilityType;
        self.editFacilityType = editFacilityType;
        self.reset = reset;

        self.successMessage = '';
        self.errorMessage = '';
        self.done = false;

        self.onlyIntegers = /^\d+$/;
        self.onlyNumbers = /^\d+([,.]\d+)?$/;

        function submit() {
            console.log('Submitting');
            if (self.facilityType.id === undefined || self.facilityType.id === null) {
                console.log('Saving New FacilityType', self.facilityType);
                createFacilityType(self.facilityType);
            } else {
                updateFacilityType(self.facilityType, self.facilityType.id);
                console.log('FacilityType updated with id ', self.facilityType.id);
            }
        }

        function createFacilityType(facilityType) {
            console.log('About to create facilityType');
            FacilityTypeService.createFacilityType(facilityType)
                .then(
                    function (response) {
                        console.log('FacilityType created successfully');
                        self.successMessage = 'FacilityType created successfully';
                        self.errorMessage='';
                        self.done = true;
                        self.display =false;
                        self.facilityType={};
                        $scope.myForm.$setPristine();
                    },
                    function (errResponse) {
                        console.error('Error while creating FacilityType');
                        self.errorMessage = 'Error while creating FacilityType: ' + errResponse.data.errorMessage;
                        self.successMessage='';
                    }
                );
        }


        function updateFacilityType(facilityType, id){
            console.log('About to update facilityType');
            FacilityTypeService.updateFacilityType(facilityType, id)
                .then(
                    function (response){
                        console.log('FacilityType updated successfully');
                        self.successMessage='FacilityType updated successfully';
                        self.errorMessage='';
                        self.done = true;
                        self.display =false;
                        $scope.myForm.$setPristine();
                    },
                    function(errResponse){
                        console.error('Error while updating FacilityType');
                        self.errorMessage='Error while updating FacilityType '+errResponse.data;
                        self.successMessage='';
                    }
                );
        }


        function removeFacilityType(id){
            console.log('About to remove FacilityType with id '+id);
            FacilityTypeService.removeFacilityType(id)
                .then(
                    function(){
                        console.log('FacilityType '+id + ' removed successfully');
                    },
                    function(errResponse){
                        console.error('Error while removing facilityType '+id +', Error :'+errResponse.data);
                    }
                );
        }


        function getAllFacilityTypes(){
            return FacilityTypeService.getAllFacilityTypes();
        }

        function editFacilityType(id) {
            self.successMessage='';
            self.errorMessage='';
            FacilityTypeService.getFacilityType(id).then(
                function (facilityType) {
                    self.facilityType = facilityType;
                    self.display =true;
                },
                function (errResponse) {
                    console.error('Error while removing facilityType ' + id + ', Error :' + errResponse.data);
                }
            );
        }
        
        function addFacilityType() {
            self.successMessage='';
            self.errorMessage='';
            self.display =true;
        }
        
        
        function reset(){
            self.successMessage='';
            self.errorMessage='';
            self.facilityType={};
            $scope.myForm.$setPristine(); //reset Form
        }
    }


    ]);