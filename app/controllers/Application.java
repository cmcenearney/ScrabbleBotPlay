package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.GameModel;
import models.Player;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import scrabblebot.core.Board;
import scrabblebot.core.Direction;
import scrabblebot.core.Move;
import scrabblebot.core.Tile;
import views.html.index;

import java.util.ArrayList;
import java.util.List;

public class Application extends Controller {

    public static Result index() {
        return ok(index.render("Your new application is ready."));
    }

    public static Result newGame() {
        return ok(Json.toJson(new GameModel()));
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result gameControl() {
        JsonNode node = request().body().asJson();
        ObjectNode result = Json.newObject();
        //board
        ArrayNode spaces = (ArrayNode)node.get("board").get("spaces");
        Board b = new Board();
        for (int i = 0; i < spaces.size(); i++){
            ArrayNode row = (ArrayNode) spaces.get(i);
            for (int j = 0; j < row.size(); j++){
                if (!row.get(j).get("value").isNull()) {
                    Character c = (Character) row.get(j).get("value").textValue().toCharArray()[0];
                    b.getSpace(i, j).setValue(c);
                }
            }
        }
        //players
        List<Player> players = new ArrayList<Player>();
        ArrayNode playersJSON = (ArrayNode)node.get("players");
        for (int i = 0; i < playersJSON.size(); i++){
            JsonNode player = playersJSON.get(i);
            String name = player.get("name").asText();
            List<Tile> tiles = new ArrayList<>();
            ArrayNode tilesJSON = (ArrayNode)player.get("tiles");
            for (int t =0; t < tilesJSON.size(); t++){
                JsonNode tileJson = tilesJSON.get(t);
                Character c = (Character) tileJson.get("character").asText().toCharArray()[0];
                int points = tileJson.get("points").asInt();
                Tile tile = new Tile(c, points);
                tiles.add(tile);
            }
            Player p = new Player(name,tiles);
            players.add(p);
        }
        //moves
        List<Move> moves = new ArrayList<>();
        ArrayNode movesJSON = (ArrayNode)node.get("moves");
        for (int i = 0; i < movesJSON.size(); i++){
            JsonNode move = movesJSON.get(i);
            int row = move.get("row").asInt();
            int col = move.get("column").asInt();
            Direction dir = Direction.getFromString(move.get("direction").asText());
            String word = move.get("word").asText();
            List<Tile> tiles = new ArrayList<>();
            if (i == movesJSON.size()-1){
                tiles = players.get(players.size()-1).getTiles();
            }
            Move m = new Move(b,row, col, dir, word, tiles);
            moves.add(m);
        }
        GameModel game = new GameModel(moves,b,players);
        game.processClientInput();
        System.out.println(game.toString());
        return ok(Json.toJson(game));
    }




}