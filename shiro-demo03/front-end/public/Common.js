/**
 * Created by yicj on 2019/6/8.
 */
function Common(){}
Common.saveToken = function(token){
    sessionStorage.setItem("x-access-token",token) ;
}
Common.getToken = function(token){
   return sessionStorage.getItem("x-access-token") ;
}