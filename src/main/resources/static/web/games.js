let resData;
//let user= resData.current_user;
download();
function download(){

fetch( "/api/games").then(function (response) {

  return  response.json();
}).then(function (data) {

    if(data.current_user    !=   "Guest"){
       document.getElementById("logoutForm").style.display = "block";
       document.getElementById("login_form").style.display = "none";
        $("#output").text(data.current_user.player);
        resData = data;
        makingList(data);
    }else{
           document.getElementById("logoutForm").style.display = "none";
           document.getElementById("login_form").style.display = "block";
            $("#output").text(data.current_user);
            resData = data;
            makingList(data);
    }



});

};
var list;

function makingList(info){
document.getElementById("game-list").innerHTML="";
if(info.current_user== "Guest"){
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
            if(e.gamePlayers[0].player == info.current_user|| e.gamePlayers[1].player.email == info.current_user.player){
            return "<li>"+e.id+","+e.created+","+e.gamePlayers[0].player.email+","+e.gamePlayers[1].player.email+"<button onclick='enterGame("+e.gamePlayers[0].id+")' >"+"Entrar al Game"+"</button></li>";
            }else{
                return"<li>"+e.id+","+e.created+","+e.gamePlayers[0].player.email+","+e.gamePlayers[1].player.email+"</li>";
            }
       }else {
      return "<li>"+e.id+","+e.created+","+e.gamePlayers[0].player.email+"<button onclick='joinGame("+e.id+")'  >"+"Unirme al Game"+"</button></li>";

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
    $.post("/api/players", {  userName: email , pwd: password })
            .done(function() {
                console.log("sing up!");
                document.getElementById("logoutForm").style.display = "block";
                document.getElementById("login_form").style.display = "none";
                $("#output").text(email);
                login();
            })

 }
  $(function() {
 $("#login").on("click", login);

   });
 function login() {
 var email= $("#userame").val();
 var password=  $("#passwd").val();
     $.post("/api/login", {  userName: email , pwd: password }).done(function() {
        console.log("logged in!");
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
 function createGame() {
   $.post("/api/games" ).done(function(data) {
         //location.href   =   "/web/game.html?gp="+data.gpid;
         location.href =  "/web/battleship-gridStack/grid.html?gp="+data.gpid;
      })
     }

function enterGame(gpid) {
  $.get("/api/game_view/"+gpid ).done(function() {
        location.href   =   "/web/game.html?gp="+gpid;
     })
    }
 function joinGame(gameId) {
 console.log("sirve?")
   $.post("/api/games/"+gameId+"/players" ).done(function(data) {
         console.log("join in!");
        // location.href  =   "/web/game.html?gp="+data.gpid;
         location.href =  "/web/battleship-gridStack/grid.html?gp="+data.gpid;

      })
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

