/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import pongprojekt.Ball;
import pongprojekt.Player;

/**
 *
 * @author grahg
 */
public class PositionUpdater implements Runnable {

    private Player p1,p2;
    private Ball ball;
    private BufferedReader r;
    private Socket socket;
    
    public PositionUpdater(String adress, int port, Player player1, Player player2, Ball ball) throws IOException
    {
        this.p1 = player1;
        this.p2 = player2;
        this.ball = ball;
        this.socket = new Socket(adress, port);
        r = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        
    }
    
    @Override
    public void run() {
        
        String text = null;
        while(true) {
            try { 
                text = r.readLine();
                 String tokens [] = text.split(",");
                int p1_y = Integer.parseInt(tokens[0]);
                int p2_y = Integer.parseInt(tokens[1]);
                double ball_x = Double.parseDouble(tokens[2]);
                double ball_y = Double.parseDouble(tokens[3]);
                
                int p1score= Integer.parseInt(tokens[4]);
                int p2score = Integer.parseInt(tokens[5]);
            
                p1.setY(p1_y);
                p1.setScore(p1score);
                
                p2.setY(p2_y);
                p2.setScore(p2score);
                
                ball.setCenterPosition(ball_x, ball_y);
            } catch (IOException ex) {
                Logger.getLogger(World2.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
}
