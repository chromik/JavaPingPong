/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pongprojekt;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author grahg
 */

public class BallFlight
{
    private Ball ball;
    private Player player1, player2;
    
    private final long FREQUENCY = 100;
    
    private double prehvel;
    private double prevvel;
    
    public static final double PIXELS_PER_METER = 100;

    public BallFlight(Ball ball, Player p1, Player p2) {
        this.ball = ball;
        this.player1 = p1;
        this.player2 = p2;
        
        this.randomizeDirection();
    }
    
    public void randomizeDirection()
    {
        this.prehvel = Math.random() % 20 + 15;
        this.prevvel = Math.random() % 20 + 15;
        
        int rand1 = (int)(Math.random() * 2);
        int rand2 = (int)(Math.random() * 2);
        
        if (rand1 == 1) this.prehvel*= -1;
        if (rand2 == 1) this.prevvel*= -1;
    }
    
    public void step()
    {
        double delay = 0.002;
        prevvel = prevvel - delay;
        
        // odrazeni od spodu
        if(prevvel < 0 && ball.getCenterY() + ball.getRadius() > World.getInstance().getHeight())
        {
            prevvel *= -1;
        }      
        
        
        // odrazeni od vrchu
        if(prevvel > 0 && ball.getCenterY() + ball.getRadius() < 0)
        {
            prevvel *= -1;
        }  
        
        // odrazeni od praveho okraje
        if(prehvel > 0 && ball.getCenterX() + ball.getRadius() > World.getInstance().getWidth() - player2.getWidth())
        {
            if (ball.getCenterY() >= player2.getstartY() && ball.getCenterY() <=player2.getstopY()) 
                prehvel *= -1.2;
        }      
        
        
        // odrazeni od leveho okraje
        if(prehvel < 0 && ball.getCenterX() - ball.getRadius() < player1.getWidth())
        {
            if (ball.getCenterY() >= player1.getstartY() && ball.getCenterY() <=player1.getstopY()) 
                prehvel *= -1.2;
        }  
                
        if (ball.getCenterX() < 0) {
            player2.ScoreIncrease();
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(BallFlight.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.prehvel = 30;
            this.prevvel = 30;
            ball.setCenterPosition(400, 300);
            this.randomizeDirection();
        } else if (ball.getCenterX() >= World.getInstance().getWidth()) {
            player1.ScoreIncrease();
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(BallFlight.class.getName()).log(Level.SEVERE, null, ex);
            }
            ball.setCenterPosition(400, 300);
            this.randomizeDirection();
        }
            
        
        double hdist = prehvel * delay;
        double vdist = prevvel * delay;
        
        double x = (ball.getCenterX()/PIXELS_PER_METER + hdist) * PIXELS_PER_METER;
        double y = (ball.getCenterY()/PIXELS_PER_METER - vdist) * PIXELS_PER_METER;
        
        ball.setCenterPosition(x, y);
    }
    
}
