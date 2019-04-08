package reversi;

import java.awt.*;

public class HumanPlayer extends ReversiPlayer {

    public HumanPlayer(int mark) {
        super(mark);
    }

    @Override
    public boolean isUserPlayer() {
        return true;
    }

    @Override
    public String playerName() {
        return "User" ;
    }

    @Override
    public Point play(int[][] board) {
        return null;
    }

    @Override
    public void requestMove(Object o) {

    }

    @Override
    public void endGame() {

    }

}
