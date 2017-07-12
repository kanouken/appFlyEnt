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
									data : 'customerName'
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

app.controller('AppAddController', [ '$scope', '$http', '$state','$sessionStorage',
		function($scope, $http, $state,$sessionStorage) {
			$scope.app = {};
			//get customer from crm 
			//headers: { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'}

			$scope.getCustomer = function(){
				$http.get('https://api.onestong.cn/8002/crm/customer/list').then(function(response) {
					var statusCode = response.data.statusCode;
					if(statusCode!= null && statusCode != undefined && statusCode == '200'){
						$scope.customers = response.data.data.objects;
						$(response.data.data.objects).each(function(i,k){
							console.log(k);
							$("<option value='"+ k.id+"_"+k.name +"_"+ k.szm +"'>"+k.name +"</option>").appendTo($("#customer"));
						});
						$("#customer").trigger("chosen:updated");
					}
				}, function(x) {
				});
				
			};
			$scope.getCustomer();
			$scope.addApp = function() {
				 var icon = $scope.icon;
				 var fd = new FormData();
			     fd.append('icon', icon);
			     fd.append('name',$scope.app.name);
			     fd.append('description',$scope.app.description);
			     console.log($scope.app.description);
			     fd.append('appId',$scope.app.appId);
			     fd.append('keywords',$scope.app.keywords);
			     var customer = $scope.app.customer.split("_");
			     fd.append('customerId',customer[0]);
			     fd.append('customerName',customer[1]);
			     fd.append('customerPrefix',customer[2]);
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
						$scope.app.icon = 
							$scope.app.icon.replace("/",".");
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
