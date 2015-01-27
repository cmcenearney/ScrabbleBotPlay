package models;

import scrabblebot.bot.RowBot;
import scrabblebot.core.Board;
import scrabblebot.core.Move;
import scrabblebot.core.TileBag;

import java.util.ArrayList;
import java.util.List;


public class GameModel {

    public static final int NUM_TILES = 7;

    public List<Move> moves;
    public Board board;
    public List<Player> players;
    private TileBag tileBag;

    public GameModel() {
        this.moves = new ArrayList<>();
        this.players = new ArrayList<>();
        this.board = new Board();
        tileBag = new TileBag();
        initPlayersHumanVBot();
    }

    public GameModel(List<Move> moves, Board board, List<Player> players) {
        this.moves = moves;
        this.board = board;
        this.players = players;
        tileBag = generateTileFromBoardAndPlayers();
    }

    void initPlayersHumanVBot(){
        Player bot = new Player("Bot");
        fillPlayersTiles(bot);
        Player human = new Player("Human");
        fillPlayersTiles(human);
        players.add(bot);
        players.add(human);
    }

    TileBag generateTileFromBoardAndPlayers(){
        TileBag bag = new TileBag();
        board.getSpaces().stream().flatMap(row -> row.stream())
                .forEach(s -> bag.removeByChar(s.getValue()));
        players.stream().flatMap(p -> p.getTiles().stream())
                .forEach(t -> bag.removeByChar(t.getCharacter()));
        return bag;
    }

    void fillPlayersTiles(Player p){
        while (p.getTiles().size() < NUM_TILES){
            p.getTiles().add(tileBag.randomDraw());
        }
    }

    public void processClientInput(){
        Move humanMove = moves.get(moves.size()-1);
        humanMove.process();
        if (humanMove.getErrorMessage() != null) {
            return;
        }
        humanMove.scoreMove();
        Player human = players.stream().filter(p -> p.getName().equals("Human")).findFirst().orElse(players.get(1));
        int score = human.getScore();
        score += humanMove.getScore();
        human.setScore(score);
        System.out.println(humanMove.getTiles());
        human.setTiles(humanMove.getTiles());
        fillPlayersTiles(human);
        this.board = humanMove.getBoard();
        Player bot = players.stream().filter(p -> p.getName().equals("Bot")).findFirst().orElse(players.get(0));
        RowBot rowBot = new RowBot(board, bot.getTiles());
        Move botMove = rowBot.getHighestScoringMove();
        botMove.process();
        botMove.scoreMove();
        int botScore = bot.getScore();
        botScore += botMove.getScore();
        bot.setScore(botScore);
        bot.setTiles(botMove.getTiles());
        this.moves.add(botMove);
        this.board = botMove.getBoard();
        return;
    }

}
