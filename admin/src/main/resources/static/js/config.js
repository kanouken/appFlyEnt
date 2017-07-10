
angular.module('app').factory('authInterceptor', function($rootScope,  $sessionStorage){
    return {
        request: function(config){
            config.headers = config.headers || {};
            if($sessionStorage.userInfo){
                config.headers.authorization = 'Bearer ' + $sessionStorage.userInfo.token;
            }
            return config;
        },
        responseError: function(response){
        }
    };
});
var app =  
angular.module('app')
  .config(
    [        '$controllerProvider', '$compileProvider', '$filterProvider', '$provide',
    function ($controllerProvider,   $compileProvider,   $filterProvider,   $provide) {
        
        // lazy controller, directive and service
        app.controller = $controllerProvider.register;
        app.directive  = $compileProvider.directive;
        app.filter     = $filterProvider.register;
        app.factory    = $provide.factory;
        app.service    = $provide.service;
        app.constant   = $provide.constant;
        app.value      = $provide.value;
    }
  ])
  .config(['$translateProvider', function($translateProvider){
    // Register a loader for the static files
    // So, the module will search missing translation tables under the specified urls.
    // Those urls are [prefix][langKey][suffix].
    $translateProvider.useStaticFilesLoader({
      prefix: 'l10n/',
      suffix: '.js'
    });
    // Tell the module what language to use by default
    $translateProvider.preferredLanguage('en');
    // Tell the module to store the language in the local storage
    $translateProvider.useLocalStorage();
  }])
  .config(function($httpProvider){
    $httpProvider.interceptors.push('authInterceptor');
}).
config( [  
         '$compileProvider',  
         function( $compileProvider )  
         {     
             $compileProvider.aHrefSanitizationWhitelist(/^\s*(https?|ftp|mailto|tel|file|sms|itms-services):/);  
             // Angular v1.2 之前使用 $compileProvider.urlSanitizationWhitelist(...)  
         }  
     ]);  
 
//.config(['$sessionStorageProvider', 
//  function ($sessionStorageProvider) {
//    var mySerializer = function (value) {
//      return value;
//    };
//    
//    var isJson = function(obj){
//    	var isjson = typeof(obj) == "object" && Object.prototype.toString.call(obj).toLowerCase() == "[object object]" && !obj.length; 
//    	return isjson;
//    	}
//    
//    var myDeserializer = function (value) { 
//    	return JSON.parse(value);
//    };
//    console.log($localStorageProvider);
//    $sessionStorageProvider.setSerializer(mySerializer);
//    $sessionStorageProvider.setDeserializer(myDeserializer);
//  }]);