/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pongprojekt;

import java.awt.Color;
import java.awt.Graphics;


public class Ball implements IPaintable
{
    volatile private double centerX;
    volatile private double centerY;
    private double radius;

    public Ball(double centerX, double centerY, double radius) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.radius = radius;
    }

    @Override
    public void paint(Graphics g) 
    {
        g.setColor(Color.blue);
        g.fillOval((int)(centerX - radius), (int)(centerY - radius), (int)(2 * radius), (int)(2* radius));
        g.setColor(Color.black);
        g.drawOval((int)(centerX - radius), (int)(centerY - radius), (int)(2 * radius), (int)(2* radius));
    }
    
    public boolean contains(double x, double y)
    {
        double pomX = x - centerX;
        double pomY = y - centerY;
        return pomX * pomX + pomY * pomY <= radius * radius;
    }
    
    public void setCenterPosition(double centerX, double centerY)
    {
        this.centerX = centerX;
        this.centerY = centerY;
    }
        
    public double getCenterX()
    {
        return centerX;
    }
    
    public double getCenterY()
    {
        return centerY;
    }

    public double getRadius() {
        return radius;
    }
    
    public void update() {
        
    }
}
