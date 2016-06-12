/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pongprojekt;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;

/**
 *
 * @author bura
 */
public class World extends JComponent implements Runnable, KeyListener
{
    private static World instance;
    
    private int width;
    private int height;
    
    public static World getInstance()
    {
        if(World.instance == null)
        {            
            int width = 800;
            int height = 600;
            World.instance = new World(width, height);
        }
        return World.instance;
    }
    
    private Player player1;
    private Player player2;
    private Ball ball;
    private ControlReceiver cont_receiver;
    private PositionSender pos_sender;
    private BallFlight ball_flight;
    
    private World(int width, int height) 
    {
       this.setFocusable(true);
       this.addKeyListener(this);
       init(width, height);
    }

    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    public int getHeight() {
        return this.height;
    }       
    
    private void update() throws InterruptedException {
        ball_flight.step();
        ball.update();
        player1.update();
        player2.update();
    }
    
    private void init(int width, int height)
    {
        this.width = width;
        this.height = height;
        setPreferredSize(new Dimension(width, height));

        
        
        int player_width = 5;
        int player_height = 120;
        int player_starting_y= this.height/2 - player_height/2;
        int player1_x = 0;
        int player2_x = this.width - player_width;
        
        this.player1 = new Player(player1_x, player_starting_y, player_width, player_height);
        this.player2 = new Player(player2_x, player_starting_y, player_width, player_height);
        this.ball = new Ball(this.width/2, this.height/2, 5);
        this.ball_flight = new BallFlight(ball, player1, player2);
        
        
        
        int port = 7123;
        
        // spusteni cont_receiveru ke cteni ovladani
        try {
            this.cont_receiver = new ControlReceiver(port, player1, player2, ball);
        } catch (IOException ex) {
            Logger.getLogger(World.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // spusteni cont_receiveru k posilani pozic
        int port2 = port + 1;
        try {
            pos_sender = new PositionSender(port2, player1, player2, ball);
        } catch (IOException ex) {
            Logger.getLogger(World.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("Server spusten!");
        System.out.println("Na portu " + port + " spusteno ovladani hrace2");
        System.out.println("Na portu " + (port+1) + " spusteno zasilani pozic objektu");
        
        System.out.println("Cekam na pripojeni klienta...");
        
        // cekam na pripojeni k ovladacimu cont_receiveru
        try {
            cont_receiver.cekatNaKlienta();
        } catch (IOException ex) {
            Logger.getLogger(World.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // cekam na pripojeni k pozicnimu cont_receiver
        try {
            pos_sender.cekatNaKlienta();
        } catch (IOException ex) {
            Logger.getLogger(World.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        System.out.println("Klient pripojen. Spoustim hru");
        
        
        Thread t = new Thread(this);
        t.start();
        
        Thread s = new Thread(cont_receiver);
        s.start();
        
        // COMENTED
        Thread p = new Thread(pos_sender);
        p.start();
        

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);        
        ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);        
        
        if(player1 != null)
        {
           player1.paint(g);
        }
        if(player2 != null)
        {
            player2.paint(g);
        }
        if(ball != null)
        {
            ball.paint(g);
        }
        g.drawString(player1.getScore() + ":" + player2.getScore() , 50, 40);
       
    }

    @Override
    public void run() {
        long start;
        long elapsed;
        long wait;
        long FPS = 60;
        long targetTime = 1000 / FPS;

        
        while (true) {
            
            start = System.nanoTime();
          
            
            try {
                this.update();
            } catch (InterruptedException ex) {
                Logger.getLogger(World.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.repaint();
            
            elapsed = System.nanoTime() - start;
            wait = targetTime - elapsed / 1000000;
            if(wait <0) 
                wait = 1000;
            
            try{
                Thread.sleep(wait);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyPressed(KeyEvent e) {
         switch(e.getKeyCode())
                {
                    case KeyEvent.VK_DOWN:
                        player1.moveDown = true;
                        break;
                    case KeyEvent.VK_UP:
                        player1.moveUp = true;
                        break;
                    case KeyEvent.VK_ENTER:
                        break;
                    case KeyEvent.VK_ESCAPE:
                        break;   
                    default:
                        break;
                }
    }

    @Override
    public void keyReleased(KeyEvent e) {
         switch(e.getKeyCode())
                {
                    case KeyEvent.VK_DOWN:
                        player1.moveDown = false;
                        break;
                    case KeyEvent.VK_UP:
                        player1.moveUp = false;
                        break;
                    case KeyEvent.VK_ENTER:
                        //  start();
                        break;
                    case KeyEvent.VK_ESCAPE:
                        //stop();
                        break;   
                    default:
                        break;
                }
    }
}

