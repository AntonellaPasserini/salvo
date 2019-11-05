let resData;
//let user= resData.current_user;
download();
function download(){

fetch( "/api/games").then(function (response) {

  return  response.json();
}).then(function (data) {


resData = data;
makingList(data);

});

};
var list;

function makingList(info){
document.getElementById("game-list").innerHTML="";
if(info.current_user==null){
list =   info.games_list.map(function (e){
       if (e.gamePlayers.length >1 ){
        return "<li>"+e.id+","+e.created+","+e.gamePlayers[0].player.email+","+e.gamePlayers[1].player.email+"</li>";
       }else {
      return "<li>"+e.id+","+e.created+","+e.gamePlayers[0].player.email+"</li>";

             }
 })


}else{
list =   info.games_list.map(function (e){
       if (e.gamePlayers.length >1 ){
        return "<li>"+e.id+","+e.created+","+e.gamePlayers[0].player.email+","+e.gamePlayers[1].player.email+"</li>";
       }else {
      return "<li>"+e.id+","+e.created+","+e.gamePlayers[0].player.email+"<button >"+"Enter Game"+"</button></li>";

             }
 })}


 list.forEach(function(e){document.getElementById("game-list").innerHTML += e})


 }


 $( document ).ready(function() {


  var  logOut = document.getElementById("logoutForm");
   logOut.style.display="none";

 });


 $(function() {
 $("#signUp").on("click", signUp);

   });
 function signUp() {
 var email= $("#userame").val();
 var password=  $("#passwd").val();
    $.post("/api/players", {  userName: email , pwd: password }).done(function() { console.log("sing up!");
     download();
     })
    $("#output").text(email);
    document.getElementById("logoutForm").style.display = "block";
    document.getElementById("login_form").style.display = "none";


 }
  $(function() {
 $("#login").on("click", login);

   });
 function login() {
 var email= $("#userame").val();
 var password=  $("#passwd").val();
     $.post("/api/login", {  userName: email , pwd: password }).done(function() { console.log("logged in!");
       download();
       $("#output").text(email);
           document.getElementById("logoutForm").style.display = "block";
            document.getElementById("login_form").style.display = "none";
      })



 }
 $(function() {
 $("#logout").on("click", logout);

   });
 function logout() {
 var email= $("#userame").val();
 var password=  $("#passwd").val();
     $.post("/api/logout").done(function() { console.log("logged out!");
     download();
     })
     $("#output").text("");
    document.getElementById("logoutForm").style.display = "none";
     document.getElementById("login_form").style.display = "block";


 }

/*


function makingListForLogin(info){

list =   info.map(function (e){
       if (e.gamePlayers.length >1 ){
        return "<li>"+e.id+","+e.created+","+e.gamePlayers[0].player.email+","+e.gamePlayers[1].player.email+"</li>";
       }else {
      return "<li>"+e.id+","+e.created+","+e.gamePlayers[0].player.email+"<button >"+"Enter Game"+"</button></li>";

             }
 })

 list.forEach(function(e){document.getElementById("game-list").innerHTML += e})



}*/

