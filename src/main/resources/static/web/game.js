/*
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
       if (e.gamePlayers.length > 1){
        return "<li>"+e.id+","+e.created+","+e.gamePlayers[0].player.email+","+e.gamePlayers[1].player.email+"</li>";
       }else {
      return "<li>"+e.id+","+e.created+","+e.gamePlayers[0].player.email+"</li>";

             }
 })

 list.forEach(function(e){document.getElementById("l").innerHTML += e})


 }




*/

$(function() {
    loadData();
});

function getParameterByName(name) {
    var match = RegExp('[?&]' + name + '=([^&]*)').exec(window.location.search);
    return match && decodeURIComponent(match[1].replace(/\+/g, ' '));
};

function loadData(){
    $.get('/api/game_view/'+getParameterByName('gp'))
        .done(function(data) {
            console.log(data)
            let playerInfo;
            if (data.gamePlayers.length>1){
                if(data.gamePlayers.id == getParameterByName('gp'))
                    playerInfo = [data.gamePlayers[0].player.email,data.gamePlayers[1].player.email];
                else
                    playerInfo = [data.gamePlayers[1].player.email,data.gamePlayers[0].player.email];

                $('#playerInfo').text(playerInfo[0] + '(you) vs ' + playerInfo[1]);

            } else{
                    playerInfo = [data.gamePlayers[0].player.email, "waiting for another player"];
                    $('#playerInfo').text(playerInfo[0] + '(you) vs  '+ playerInfo[1]);
                   }

      data.ships.forEach(function (shipPiece) {
        shipPiece.shipLocations.forEach(function (shipLocation) {
          let turnHitted = isHit(shipLocation,data.salvoes,playerInfo[0].id)
          if(turnHitted >0){
            $('#B_' + shipLocation).addClass('ship-piece-hited');
            $('#B_' + shipLocation).text(turnHitted);
          }
          else
            $('#B_' + shipLocation).addClass('ship-piece');
        });
      });

            data.ships.forEach(function(shipPiece){
                shipPiece.shipLocations.forEach(function(shipLocation){
                    $('#'+shipLocation).addClass('ship-piece');
                })
            });
               data.salvoes.forEach(function (salvo) {
                    console.log(salvo);
                    if (playerInfo[0].id === salvo.player) {
                      salvo.locations.forEach(function (location) {
                        $('#S_' + location).addClass('Salvo');
                      });
                    } else {
                      salvo.locations.forEach(function (location) {
                        $('#_' + location).addClass('Salvo');
                      });
                    }
                  });
        })
        .fail(function( jqXHR, textStatus ) {
          alert( "Failed: " + textStatus );
        });
 function isHit(shipLocation,salvoes,playerId) {
     var hit = 0;
     salvoes.forEach(function (salvo) {
       if(salvo.player != playerId)
         salvo.locations.forEach(function (location) {
           if(shipLocation === location)
             hit = salvo.turn;
         });
     });
     return hit;
     }

     }

     function getParameterByName(name) {
          var match = RegExp('[?&]' + name + '=([^&]*)').exec(window.location.search);
          return match && decodeURIComponent(match[1].replace(/\+/g, ' '));
      };
function placeSalvo(){
 $.post("/api/games" ).done(function(data) {
         //location.href   =   "/web/game.html?gp="+data.gpid;
         location.href =  "/web/battleship-gridStack/gridAddSalvoes.html?gp="+getParameterByName('gp');
      })
     }