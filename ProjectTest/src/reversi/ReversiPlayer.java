package reversi;

import java.awt.*;

public abstract class ReversiPlayer {

    protected int myMark;
    public ReversiPlayer(int mark){
        myMark = mark;
    }

    abstract public boolean isUserPlayer();

    abstract public String playerName();

    abstract public Point play(int[][] board);


    public abstract void requestMove(Object o);

    public abstract void endGame();
}
