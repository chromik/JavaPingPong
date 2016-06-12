/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.IOException;
import javax.swing.JFrame;

/**
 *
 * @author grahg
 */
public class Client {
    private JFrame appFrame;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException 
    {
        Client client = new Client();
        client.show();
    }

    public Client() throws IOException 
    {
        appFrame = new JFrame("Ping Pong");
        appFrame.setContentPane(World2.getInstance());        
        appFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public void show()
    {        
        appFrame.pack();                        
        appFrame.setLocationRelativeTo(null);   
        appFrame.setVisible(true);
    } 
}
