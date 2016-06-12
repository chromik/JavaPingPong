/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pongprojekt;

import javax.swing.JFrame;

/**
 *
 * @author grahg
 */
public class PongProjekt {

      private JFrame appFrame;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        PongProjekt pong = new PongProjekt();
        pong.show();
    }

    public PongProjekt() 
    {
        appFrame = new JFrame("Ping Pong");
        appFrame.setContentPane(World.getInstance());        
        appFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public void show()
    {        
        appFrame.pack();                        
        appFrame.setLocationRelativeTo(null);   
        appFrame.setVisible(true);
    } 
    
}
