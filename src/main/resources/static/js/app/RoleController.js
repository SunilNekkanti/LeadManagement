'use strict';

app.controller('RoleController',
    ['RoleService', '$scope',  function( RoleService, $scope) {

    	
        var self = this;
        self.role = {};
        self.roles=[];
        self.display =false;
        self.submit = submit;
        self.addRole = addRole;
        self.getAllRoles = getAllRoles;
        self.createRole = createRole;
        self.updateRole = updateRole;
        self.removeRole = removeRole;
        self.editRole = editRole;
        self.reset = reset;

        self.successMessage = '';
        self.errorMessage = '';
        self.done = false;

        self.onlyIntegers = /^\d+$/;
        self.onlyNumbers = /^\d+([,.]\d+)?$/;

        function submit() {
            console.log('Submitting');
            if (self.role.id === undefined || self.role.id === null) {
                console.log('Saving New Role', self.role);
                createRole(self.role);
            } else {
                updateRole(self.role, self.role.id);
                console.log('Role updated with id ', self.role.id);
            }
        }

        function createRole(role) {
            console.log('About to create role');
            RoleService.createRole(role)
                .then(
                    function (response) {
                        console.log('Role created successfully');
                        self.successMessage = 'Role created successfully';
                        self.errorMessage='';
                        self.done = true;
                        self.display =false;
                        self.role={};
                        $scope.myForm.$setPristine();
                    },
                    function (errResponse) {
                        console.error('Error while creating Role');
                        self.errorMessage = 'Error while creating Role: ' + errResponse.data.errorMessage;
                        self.successMessage='';
                    }
                );
        }


        function updateRole(role, id){
            console.log('About to update role');
            RoleService.updateRole(role, id)
                .then(
                    function (response){
                        console.log('Role updated successfully');
                        self.successMessage='Role updated successfully';
                        self.errorMessage='';
                        self.done = true;
                        self.display =false;
                        $scope.myForm.$setPristine();
                    },
                    function(errResponse){
                        console.error('Error while updating Role');
                        self.errorMessage='Error while updating Role '+errResponse.data;
                        self.successMessage='';
                    }
                );
        }


        function removeRole(id){
            console.log('About to remove Role with id '+id);
            RoleService.removeRole(id)
                .then(
                    function(){
                        console.log('Role '+id + ' removed successfully');
                    },
                    function(errResponse){
                        console.error('Error while removing role '+id +', Error :'+errResponse.data);
                    }
                );
        }


        function getAllRoles(){
            return RoleService.getAllRoles();
        }

        function editRole(id) {
            self.successMessage='';
            self.errorMessage='';
            RoleService.getRole(id).then(
                function (role) {
                    self.role = role;
                    self.display =true;
                },
                function (errResponse) {
                    console.error('Error while removing role ' + id + ', Error :' + errResponse.data);
                }
            );
        }
        
        function addRole() {
            self.successMessage='';
            self.errorMessage='';
            self.display =true;
        }
        
        
        function reset(){
            self.successMessage='';
            self.errorMessage='';
            self.role={};
            $scope.myForm.$setPristine(); //reset Form
        }
    }


    ]);