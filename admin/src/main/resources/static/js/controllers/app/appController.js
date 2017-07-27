'use strict';

/* Controllers */
// foo controller
app.controller(
				'AppListController',
				[
						'$scope',
						'$http',
						'$state',
						'$compile','$modal',
						function($scope, $http, $state, $compile,$modal) {
							$scope.foo = {};
							$scope.preAdd = function() {
								$state.go('app.appadd');
							};
							$scope.preEdit = function() {
								$state.go('app.appedit');
							};
							$scope.showQrCode = function(qrCode){
							     var modalInstance = $modal.open({
								        templateUrl: 'myModalContent.html',
								        controller: 'ModalInstanceCtrl',
								        size:'sm',
								        windowClass: 'app-modal-window',
								        resolve: {
								          qrCode: function () {
								            return qrCode;
								          }
								        }
								      });
								      modalInstance.result.then(function (selectedItem) {
								        $scope.selected = selectedItem;
								      }, function () {
								      });
								
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
								{data:'name'},    
								{
									data : 'customerName'
								}, {
									data : 'appId'

								}, {
									data : null

								} ],
								columnDefs : [
										{
											data : "id",
											render : function(data, type, full) {
												return '<a ui-sref="app.appedit({id:'
														+ full.id
														+ ' })">'
														+ full.name
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
											//	return '<button ng-click="deleteFoo('+data.id+')" class="btn m-b-xs btn-sm btn-info btn-addon"><i class="fa fa-trash-o"></i>刪除</button>';
												
											return	'<div class="btn-group dropdown" dropdown="">' +
										        '  <button class="btn btn-default" dropdown-toggle="" aria-haspopup="true" aria-expanded="false">操作 <span class="caret"></span></button>'+
										          '<ul class="dropdown-menu"> ' +
										           ' <li><a href="">编辑</a></li>' +
										          '  <li><a href=""  ng-click="showQrCode(\''+ data.qrCode +'\');" >显示二维码</a></li>' +
										         ' </ul>'+
										      '  </div>';
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

app.controller('AppAddController', [ '$scope', '$http', '$state','$sessionStorage','toaster',
		function($scope, $http, $state,$sessionStorage,toaster) {
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
				 toaster.pop('wait', 'info','正在处理');
				 $scope.processing = true;
				 var icon = $scope.icon;
				 var fd = new FormData();
			     fd.append('icon', icon);
			     fd.append('name',$scope.app.name);
			     console.log($scope.app.description)
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
						toaster.clear();
						$scope.processing = false;
						$state.go('app.applist');
					}
				}, function(x) {
					toaster.clear();
					$scope.processing = false;
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
app.controller('ModalInstanceCtrl', ['$scope', '$modalInstance', 'qrCode', function($scope, $modalInstance, qrCode) {
    $scope.qrCode = qrCode;
    $scope.title = '下载二维码';
//    $scope.selected = {
//      item: $scope.items[0]
//    };

//    $scope.ok = function () {
//      $modalInstance.close($scope.selected.item);
//    };
//
//    $scope.cancel = function () {
//      $modalInstance.dismiss('cancel');
//    };
  }]) ; 
		
		
		
		
		
