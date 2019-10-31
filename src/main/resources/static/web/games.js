
let resData;

fetch( "/api/games").then(function (response) {

  return  response.json();
}).then(function (data) {


resData = data;
makingList(data);

});
var list;

function makingList(info){

 list =   info.map(function (e){
       if (e.gamePlayers.length >1 ){
        return "<li>"+e.id+","+e.created+","+e.gamePlayers[0].player.email+","+e.gamePlayers[1].player.email+"</li>";
       }else {
      return "<li>"+e.id+","+e.created+","+e.gamePlayers[0].player.email+"</li>";

             }
 })

 list.forEach(function(e){document.getElementById("game-list").innerHTML += e})


 }




