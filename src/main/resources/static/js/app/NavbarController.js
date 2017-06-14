'use strict';

app
		.controller(
				'NavbarController',
				[
						'$scope',
						'$compile',
						'$filter',
						'DTOptionsBuilder',
						'DTColumnBuilder',
						function( $scope, $compile, $filter) {

							var self = this;
							self.users = [];
							self.display = false;
	} 
]);