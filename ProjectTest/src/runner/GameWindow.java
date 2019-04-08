package runner;

import application.ApplicationHandler;
import javafx.application.Application;
import javafx.stage.Stage;
import reversi.GamePanel;

import javax.swing.*;

public class GameWindow extends JFrame {

    public GameWindow(){
        GamePanel gp = new GamePanel();
        this.add(gp);
        this.setTitle("Reversi");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        //this.setSize(500,500);

    }

    public static void main(String[] args) {
        new GameWindow();
    }


}
