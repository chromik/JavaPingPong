/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import pongprojekt.Ball;
import pongprojekt.Player;
import pongprojekt.ControlReceiver;


/**
 *
 * @author grahg
 */
public class World2 extends JComponent implements Runnable, KeyListener
{
    private static World2 instance;
    
    private int width;
    private int height;
    volatile private Socket socket_ovladani;
    BufferedWriter ovladani_writer;
    private PositionUpdater pos_updater;
    
    volatile private Player player1;
    volatile private Player player2;
    volatile private Ball ball;
    
    
    
    public static World2 getInstance() throws IOException
    {
        if(World2.instance == null)
        {            
            int width = 800;
            int height = 600;
            World2.instance = new World2(width, height);
        }
        return World2.instance;
    }
   
    private World2(int width, int height) throws IOException 
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
    
    private void update() {

    }
    
    
    private void init(int width, int height) throws IOException
    {
        this.width = width;
        this.height = height;
        setPreferredSize(new Dimension(width, height));
        
        System.out.println("Zadejte adresu serveru: ");
        
        // Nacti ze vstupu adresu serveru
        String adress = "localhost";
        Scanner in = new Scanner(System.in);
        adress = in.nextLine();
        in.close();
        
        
        socket_ovladani = new Socket(adress, 7123);
        ovladani_writer = new BufferedWriter(new OutputStreamWriter(socket_ovladani.getOutputStream())); 
        

        
        int player_width = 5;
        int player_height = 120;
        int player_starting_y= this.height/2 - player_height/2;
        int player1_x = 0;
        int player2_x = this.width - player_width;
        
        this.player1 = new Player(player1_x, player_starting_y, player_width, player_height);
        this.player2 = new Player(player2_x, player_starting_y, player_width, player_height);
        this.ball = new Ball(this.width/2, this.height/2, 5);
        
        

        
        Thread t = new Thread(this);
        t.start();
        
        pos_updater = new PositionUpdater(adress, 7124, player1, player2, ball);
        Thread pu = new Thread(pos_updater);
        pu.start();
        
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
                 try {
                     ovladani_writer.write("d\n");
                     ovladani_writer.flush();
                 } catch (IOException ex) {
                     Logger.getLogger(World2.class.getName()).log(Level.SEVERE, null, ex);
                 }
                 break;
             
             case KeyEvent.VK_UP:
                 try {
                     ovladani_writer.write("u\n");
                     ovladani_writer.flush();
                 } catch (IOException ex) {
                     Logger.getLogger(World2.class.getName()).log(Level.SEVERE, null, ex);
                 }
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
                 try {
                     ovladani_writer.write("!d\n");
                     ovladani_writer.flush();
                 } catch (IOException ex) {
                     Logger.getLogger(World2.class.getName()).log(Level.SEVERE, null, ex);
                 }
                 break;
             
             case KeyEvent.VK_UP:
                 try {
                     ovladani_writer.write("!u\n");
                     ovladani_writer.flush();
                 } catch (IOException ex) {
                     Logger.getLogger(World2.class.getName()).log(Level.SEVERE, null, ex);
                 }
                 break;
                 
                 
             default:
                 break;
         }
    }

}

