/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pongprojekt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author grahg
 */
public class ControlReceiver implements Runnable {
    
    ServerSocket serverSocket_ovladani;
    Socket socket_ovladani;
    
    private Player player1, player2;
    private Ball ball;
    

    
    InputStream in;
    BufferedReader ovladani_reader;
    
    public ControlReceiver(int port, Player p1, Player p2, Ball ball) throws IOException
    {
        this.player1 = p1;
        this.player2 = p2;
        this.ball = ball;
        
        serverSocket_ovladani = new ServerSocket(port);
        serverSocket_ovladani.setSoTimeout(1000000);
    }
    
    public void cekatNaKlienta() throws IOException
    {
       
        while (socket_ovladani == null) {
             socket_ovladani = serverSocket_ovladani.accept();
        }       
        in = socket_ovladani.getInputStream();
        ovladani_reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        

    }
    
    public String prijmoutData() throws IOException, ClassNotFoundException
    {
        String text = ovladani_reader.readLine();
        return text;
    }
 
    
    public void OvladaniHrace2(String s)
    {
        if ("u".equals(s)) {
            player2.moveUp = true;
        }
        else if ("d".equals(s)) {
            player2.moveDown = true;
        }
        else if ("!u".equals(s)) {
            player2.moveUp = false;
        }
        else if ("!d".equals(s)) {
            player2.moveDown = false;
        }
    }

    @Override
    public void run() {
        while(true)
        {
            
            try {
                String data = this.prijmoutData();
                OvladaniHrace2(data);
                
            } catch (IOException ex) {
                Logger.getLogger(ControlReceiver.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ControlReceiver.class.getName()).log(Level.SEVERE, null, ex);
            }
                    
            
            
            
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(ControlReceiver.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }

}
