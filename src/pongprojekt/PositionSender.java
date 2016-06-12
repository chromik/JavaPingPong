/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pongprojekt;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author grahg
 */
public class PositionSender implements Runnable  {
    
    volatile ServerSocket serverSocket_pozice;
    volatile Socket socket_pozice;
    
    OutputStream out;
    BufferedWriter pozice_writer;
    
    Player p1, p2;
    Ball ball;
    
    public PositionSender(int port, Player p1, Player p2, Ball ball) throws UnsupportedEncodingException, IOException
    {   
        this.p1 = p1;
        this.p2 = p2;
        this.ball = ball;
        
        
        serverSocket_pozice = new ServerSocket(port);
        serverSocket_pozice.setSoTimeout(1000000);
        System.out.println(serverSocket_pozice.getInetAddress());
    }
    
    void cekatNaKlienta() throws UnsupportedEncodingException, IOException
    {
        while (socket_pozice == null) {
             socket_pozice = serverSocket_pozice.accept();
        }
        System.out.println(socket_pozice.getLocalAddress().getHostAddress());
        
        out = socket_pozice.getOutputStream();
        pozice_writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));

    }

    public void odeslatData(String text) throws IOException
    {
        pozice_writer.write(text + '\n');
        pozice_writer.flush();
    }
    
    
    @Override
    public void run() {
        while (true) {
            String pozice = p1.getstartY() + "," + p2.getstartY() + "," + ball.getCenterX() + "," + ball.getCenterY();
            pozice += "," + p1.getScore() + "," + p2.getScore();
            try {
                pozice_writer.write(pozice + "\n");
                pozice_writer.flush();
            } catch (IOException ex) {
                Logger.getLogger(ControlReceiver.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
    }
    
    
}
