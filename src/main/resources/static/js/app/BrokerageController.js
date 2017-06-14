'use strict';

app.controller('BrokerageController',
    ['BrokerageService', '$scope',  function( BrokerageService, $scope) {

    	
        var self = this;
        self.brokerage = {};
        self.brokerages=[];
        self.display =false;
        self.submit = submit;
        self.addBrokerage = addBrokerage;
        self.getAllBrokerages = getAllBrokerages;
        self.createBrokerage = createBrokerage;
        self.updateBrokerage = updateBrokerage;
        self.removeBrokerage = removeBrokerage;
        self.editBrokerage = editBrokerage;
        self.reset = reset;

        self.successMessage = '';
        self.errorMessage = '';
        self.done = false;

        self.onlyIntegers = /^\d+$/;
        self.onlyNumbers = /^\d+([,.]\d+)?$/;

        function submit() {
            console.log('Submitting');
            if (self.brokerage.id === undefined || self.brokerage.id === null) {
                console.log('Saving New Brokerage', self.brokerage);
                createBrokerage(self.brokerage);
            } else {
                updateBrokerage(self.brokerage, self.brokerage.id);
                console.log('Brokerage updated with id ', self.brokerage.id);
            }
        }

        function createBrokerage(brokerage) {
            console.log('About to create brokerage');
            BrokerageService.createBrokerage(brokerage)
                .then(
                    function (response) {
                        console.log('Brokerage created successfully');
                        self.successMessage = 'Brokerage created successfully';
                        self.errorMessage='';
                        self.done = true;
                        self.display =false;
                        self.brokerage={};
                        $scope.myForm.$setPristine();
                    },
                    function (errResponse) {
                        console.error('Error while creating Brokerage');
                        self.errorMessage = 'Error while creating Brokerage: ' + errResponse.data.errorMessage;
                        self.successMessage='';
                    }
                );
        }


        function updateBrokerage(brokerage, id){
            console.log('About to update brokerage');
            BrokerageService.updateBrokerage(brokerage, id)
                .then(
                    function (response){
                        console.log('Brokerage updated successfully');
                        self.successMessage='Brokerage updated successfully';
                        self.errorMessage='';
                        self.done = true;
                        self.display =false;
                        $scope.myForm.$setPristine();
                    },
                    function(errResponse){
                        console.error('Error while updating Brokerage');
                        self.errorMessage='Error while updating Brokerage '+errResponse.data;
                        self.successMessage='';
                    }
                );
        }


        function removeBrokerage(id){
            console.log('About to remove Brokerage with id '+id);
            BrokerageService.removeBrokerage(id)
                .then(
                    function(){
                        console.log('Brokerage '+id + ' removed successfully');
                    },
                    function(errResponse){
                        console.error('Error while removing brokerage '+id +', Error :'+errResponse.data);
                    }
                );
        }


        function getAllBrokerages(){
            return BrokerageService.getAllBrokerages();
        }

        function editBrokerage(id) {
            self.successMessage='';
            self.errorMessage='';
            BrokerageService.getBrokerage(id).then(
                function (brokerage) {
                    self.brokerage = brokerage;
                    self.display =true;
                },
                function (errResponse) {
                    console.error('Error while removing brokerage ' + id + ', Error :' + errResponse.data);
                }
            );
        }
        
        function addBrokerage() {
            self.successMessage='';
            self.errorMessage='';
            self.display =true;
        }
        
        
        function reset(){
            self.successMessage='';
            self.errorMessage='';
            self.brokerage={};
            $scope.myForm.$setPristine(); //reset Form
        }
    }


    ]);