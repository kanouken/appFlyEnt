'use strict';

/* Controllers */
// foo controller
app.controller(
				'AppListController',
				[
						'$scope',
						'$http',
						'$state',
						'$compile',
						function($scope, $http, $state, $compile) {
							$scope.foo = {};
							$scope.preAdd = function() {
								$state.go('app.appadd');
							};
							$scope.preEdit = function() {
								$state.go('app.appedit');
							};
							
							$scope.deleteFoo = function(id){
								$http.delete('app/'+id).then(function(response) {
									if (response.status == 200) {
										$state.reload();
									}
								}, function(x) {
								});
							};
							$scope.dtoption = {
								sAjaxSource : 'apps',
								columns : [ 
								{data:'id'},    
								{
									data : 'appId'
								}, {
									data : 'name'

								}, {
									data : null

								} ],
								columnDefs : [
										{
											data : "id",
											render : function(data, type, full) {
												return '<a ui-sref="app.appedit({id:'
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
												
												return '<button ng-click="deleteFoo('+data.id+')" class="btn m-b-xs btn-sm btn-warning btn-addon">删除</button>';
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

app.controller('AppAddController', [ '$scope', '$http', '$state',
		function($scope, $http, $state) {
			$scope.app = {};
			
			$scope.addApp = function() {
				 var icon = $scope.icon;
				 var fd = new FormData();
			     fd.append('icon', icon);
			     fd.append('name',$scope.app.name);
			     fd.append('description',$scope.app.description);
			     console.log($scope.app.description);
			     fd.append('appId',$scope.app.appId);
			     fd.append('keywords',$scope.app.keywords);
				$http.post('app' ,fd, {
					 transformRequest: angular.identity,
			         headers: {'Content-Type': undefined,}
				}).then(function(response) {
					if (response.status == 200) {
						$state.go('app.applist');
					}
				}, function(x) {
					// alter error
				});
			};
			
}]);

app.controller('AppEditController', [ '$scope', '$http', '$state',
		'$stateParams', function($scope, $http, $state, $stateParams) {
			$scope.app = {};
			$scope.queryApp = function() {
				$http.get('app/' + $stateParams.id).then(function(response) {
					if (response.status == 200) {
						$scope.app = response.data;
						console.log(response.data);
					}
				}, function(x) {
					// alter error
				});
			};

			$scope.preAddVersion=function(){
				$state.go('app.versionadd',{appId:$scope.app.id,appName:$scope.app.name});
				
			};
			
			$scope.preUpdateVersion = function(plat){
				$state.go('app.versionupdate',{appId:$scope.app.id,plat:plat});
			};
			$scope.queryApp();

			$scope.editApp = function() {

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
