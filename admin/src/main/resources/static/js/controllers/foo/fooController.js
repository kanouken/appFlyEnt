'use strict';

/* Controllers */
// foo controller
app
		.controller(
				'FooController',
				[
						'$scope',
						'$http',
						'$state',
						'$compile',
						function($scope, $http, $state, $compile) {
							$scope.foo = {};
							$scope.test = 'add';

							$scope.preAdd = function() {
								console.log("works");
								$state.go('app.fooadd');
							};

							$scope.preEdit = function() {

								$state.go('app.fooedit');

							};
							
							$scope.deleteFoo = function(id){
								$http.delete('foo/'+id).then(function(response) {
									if (response.status == 200) {
										$state.reload();
									}
								}, function(x) {
									// alter error
								});
							};
							$scope.dtoption = {
								sAjaxSource : 'foos',
								columns : [ {
									data : 'id'
								}, {
									data : 'name'

								}, {
									data : null

								} ],
								columnDefs : [
										{
											data : "id",
											render : function(data, type, full) {
												return '<a ui-sref="app.fooedit({id:'
														+ data
														+ ' })">'
														+ data
														+ '</a>';
											},
											targets : [ 0 ],
											createdCell : function(nTd, sData,
													oData, iRow, iCol) {
												$compile(nTd)($scope);
											}

										},
										{
											render : function(data, type, full) {
												
												return '<button ng-click="deleteFoo('+data.id+')" class="btn m-b-xs w-xs btn-danger">delete</button>';
											},
											targets : [ -1],
											createdCell : function(nTd, sData,
													oData, iRow, iCol) {
												$compile(nTd)($scope);
											}
										}

								]

							}

						} ]);

app.controller('FooAddController', [ '$scope', '$http', '$state',
		function($scope, $http, $state) {
			$scope.foo = {};
			$scope.addFoo = function() {
				$http.post('foo', {
					name : $scope.foo.name
				}).then(function(response) {
					if (response.status == 200) {
						$state.go('app.foo')
					}
				}, function(x) {
					// alter error
				});
			}

		} ]);

app.controller('FooEditController', [ '$scope', '$http', '$state',
		'$stateParams', function($scope, $http, $state, $stateParams) {
			$scope.foo = {};
			$scope.queryFoo = function() {
				$http.get('foo/' + $stateParams.id, {
					name : $scope.foo.name
				}).then(function(response) {
					if (response.status == 200) {
						$scope.foo = response.data;
					}
				}, function(x) {
					// alter error
				});
			};

			$scope.queryFoo();

			$scope.editFoo = function() {

				$http.put('foo', {
					name : $scope.foo.name,
					id : $scope.foo.id
				}).then(function(response) {
					if (response.status == 200) {
						$state.go('app.foo')
					}
				}, function(x) {
					// alter error
				});

			}

		} ]);
