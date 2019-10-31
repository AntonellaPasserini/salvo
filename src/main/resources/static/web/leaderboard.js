$(function() {
    loadData()
});



function updateViewLBoard(data) {
  var htmlList = data.map(function (Score) {
      return  '<tr><td>' + Score.player + '</td>'
              + '<td>' + Score.score.totalScore + '</td>'
              + '<td>' + Score.score.win + '</td>'
              + '<td>' + Score.score.losses + '</td>'
              + '<td>' + Score.score.tie + '</td></tr>';
  }).join('');
  document.getElementById("leader-list").innerHTML = htmlList;
}

function loadData() {

  $.get("/api/leaderboard")
    .done(function(data) {
        console.log(data)
      updateViewLBoard(data);
    })
    .fail(function( jqXHR, textStatus ) {
      alert( "Failed: " + textStatus );
    });
}


