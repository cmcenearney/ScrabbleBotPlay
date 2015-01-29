//App
var ScrabbleBot = {
  Models: {},
  Collections: {},
  Views: {}
};


// Models
ScrabbleBot.Models.Player = Backbone.Model.extend();

ScrabbleBot.Models.BoardSpace = Backbone.Model.extend();

ScrabbleBot.Models.Board = Backbone.Model.extend();

ScrabbleBot.Models.Game = Backbone.Model.extend({
  urlRoot: "game"
});


//Views
ScrabbleBot.Views.ScoresView = Backbone.View.extend({
  id: "scoreboard",
  render: function(){
    header = "<p><b>Scores</b></p>";
    this.$el.append(header);
    tbl = document.createElement("table");
    for (var i=this.model.get('players').length-1; i >= 0; i--){
      player = this.model.get('players')[i];
      tr = document.createElement("tr");
      td = document.createElement("td");
      td.innerHTML =  player.name;
      tr.appendChild(td);
      score = document.createElement("td");
      score.innerHTML = player.score;
      tr.appendChild(score);
      tbl.appendChild(tr)
    }
    this.$el.append(tbl);
    this.$el.html();
  }
});

ScrabbleBot.Views.CurrentPlayerView = Backbone.View.extend({
  initialize: function(){
    this.currentPlayer = this.model.get('players')[1];
    this.tiles = this.currentPlayer.tiles;
  },
  id: "player_move",
  events: {
    "click #shuffle_btn": "shuffleTiles",
    "click #move_btn": "submitMove",
    "keypress #move_input": "handleKeyPress"
  },

  handleKeyPress: function(e){
    if ( e.which == 13 )
      {
        this.submitMove();
      }
  },

  submitMove: function(){
    moveString = $('#move_input').val();
    if (!isValidMoveInput(moveString)){
        invalidMoveAlert();
        return;
    }
    proposedMove = this.parseMoveInput(moveString);
    appGame.get("moves").push(proposedMove);
    model = this.model;
    $.ajax({
        url: "game",
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(appGame),
        processData: false,
        dataType: 'json'
    }).done(function( data, status, xhr ) {
           //console.log(xhr.responseJSON.board);
           appGame = new ScrabbleBot.Models.Game({
               board:  xhr.responseJSON.board,
               moves: xhr.responseJSON.moves,
               players: xhr.responseJSON.players
               });
           renderViews(appGame);
      });
  },

    parseMoveInput: function(moveInput){
        tokens = moveInput.split(",");
        row = tokens[0].toUpperCase().charCodeAt(0) - 65;
        col = tokens[1] - 1;
        direction = "ACROSS";
        if (tokens[2] == "^"){
            direction = "DOWN";
        }
        word = tokens[3].toUpperCase();
        move = {};
        move.word = word; move.row = row; move.column = col; move.direction = direction;
        return move;
    },

  shuffleTiles: function(){
    tiles = this.tiles;
    for (var i=tiles.length-1; i > 0; i--){
      j = Math.floor((Math.random()*i)+0);
      tmp = tiles[j];
      tiles[j] = tiles[i];
      tiles[i] = tmp;
    }
    this.render();
  },

  render: function(){
    tiles = this.tiles;
    var rendered = "<h5>" + (this.currentPlayer.name) + ", it's your move:</h5>\n<table id='tile_rack'><tr>";
    for (var i=0; i < tiles.length; i++){
      rendered += "<td>" + tiles[i].character + "<sub>" + tiles[i].points + "</sub></td>";
    }
    rendered += "</tr></table>";
    rendered += "<button id='shuffle_btn'>Shuffle tiles</button>";
    rendered += "<input id='move_input'><button id='move_btn'>Submit move</button>";
    this.$el.html(rendered);
  }
});

ScrabbleBot.Views.BotPlayerView = Backbone.View.extend({
  initialize: function(){
    this.currentPlayer = this.model.get('players')[0];
    this.tiles = this.currentPlayer.tiles;
  },
  id: "bot_tiles",

  render: function(){
    tiles = this.tiles;
    var rendered = "<h5>Bot tiles:</h5>\n<table id='tile_rack'><tr>";
    for (var i=0; i < tiles.length; i++){
      rendered += "<td>" + tiles[i].character + "<sub>" + tiles[i].points + "</sub></td>";
    }
    rendered += "</tr></table>";
    this.$el.html(rendered);
  }
});

ScrabbleBot.Views.BoardView = Backbone.View.extend({
  id: "board",
  tagName: "table",
  render: function(){
    n = this.model.spaces.length;
    //guide row
    tr = document.createElement("tr");
    td = document.createElement("td");
    td.className = "guide corner";
    tr.appendChild(td);
    for (var i=1; i <= n; i++){
      td = document.createElement("td");
      td.className = "guide";
      td.innerHTML = i;
      tr.appendChild(td);
    }
    this.$el.append(tr)
    for (var i=0; i < n; i++) {
      tr = document.createElement("tr");
      td = document.createElement("td");
      td.className = "guide";
      td.innerHTML = String.fromCharCode(i + 65);
      tr.appendChild(td)
      for (var j=0; j < n; j++) {
        space = this.model.spaces[i][j];
        td = document.createElement("td");
        td.className = space.type;
        if (space.occupied) {
          td.innerHTML = space.value;
          td.className += (" occupied");
        }
        tr.appendChild(td);
      }
      this.$el.append(tr)
    }
    this.$el.html();
  }
});

//spaghetti
var appGame = new Object();

var  moveRegEx = /[A-Oa-o]{1},[0-9]{1,2},[>|^],\w{1,15}/;

function isValidMoveInput(str){
    if (str.match(moveRegEx) != null){
        return true;
    }
    return false;
};

function invalidMoveAlert(){
    alert("Moves must be of the format {row},{column},[^|>],{word}");
};

function startNewGame(){
  $.ajax({
    url: "game/new",
    type: 'GET',
    contentType: 'application/json',
    processData: false,
    dataType: 'json'
  }).done(function( data, status, xhr ) {
    //console.log(xhr.responseJSON.board);
    appGame = new ScrabbleBot.Models.Game({
        board:  xhr.responseJSON.board,
        moves: xhr.responseJSON.moves,
        players: xhr.responseJSON.players
        });
    renderViews(appGame);
  });
};

function showHelpInfo(){
  var helpText = "For now, moves must be entered as \n" +
      "row,column,^|>,word\n" +
      "where '^' or '>' indicates the direction of the move.\n" +
      "Example:\n" +
          "\th,7,>,move\n" +
      "\n" +
      "Dark blue = triple letter score\n" +
      "Light blue = double letter score\n" +
      "Red = triple word score\n" +
      "Pink = double word score";
  alert(helpText);
};

function renderViews(game) {

  var moves =  appGame.get("moves")
  lastMove = moves[moves.length-1]
  if (lastMove != null && lastMove.errorMessage != null ){
    alert(lastMove.errorMessage);
  };

  currentPlayer = game.get('players')[game.get('currentTurn')];
  board = game.get('board');

  scores = new ScrabbleBot.Views.ScoresView({ model: game });
  boardView = new ScrabbleBot.Views.BoardView({ model: board });
  currentPlayerView = new ScrabbleBot.Views.CurrentPlayerView({ model: game });
  botPlayerView = new ScrabbleBot.Views.BotPlayerView({ model: game });

  scores.render();
  currentPlayerView.render();
  botPlayerView.render();
  boardView.render();

  $('#bot_tiles').replaceWith(botPlayerView.el);
  $('#player_move').replaceWith(currentPlayerView.el);
  $('#board').replaceWith(boardView.el);
  $('#scoreboard').replaceWith(scores.el);
  $("#move_input").focus();

};