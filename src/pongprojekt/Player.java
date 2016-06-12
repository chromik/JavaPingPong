/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pongprojekt;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author grahg
 */



public class Player implements IPaintable {
    private int x;
    volatile private int y;
    private int height;
    private int width;
    private Color color;
    volatile public boolean moveUp, moveDown;
    private final int step = 4;
    
    int score;
    
    public Player(int x, int y, int width, int height)
    {
       // System.out.println(World.getInstance().getWidth());
        this.width = width;
        this.height = height;
        this.x =x;
        this.y = y;
        this.color = Color.BLACK;
        this.score = 0;
        
    }
    
    public int getstartY()
    {
        return this.y;
    }
    
    public int getstopY()
    {
        return this.y + this.height;
    }
    
    public int getWidth()
    {
        return this.width;
    }
    
    public void setY(int y)
    {
        this.y = y;
    }
    
    public void moveUp() 
    { 
        if (this.y - step > 0){
            this.y-= step;
        }
    }
    
    public void moveDown()
    {
        if (this.y<World.getInstance().getHeight() - this.height + this.step) {
            this.y+= step;
        }
    }
    
    
    @Override
    public void paint(Graphics g) 
    {
        g.setColor(this.color);
        g.fillRect(this.x, this.y, this.width, this.height);
        g.setColor(this.color);
        g.drawRect(this.x, this.y, this.width, this.height);
    }
    
    public void update()
    {
        if (moveUp == true)
            this.moveUp();
        if (moveDown == true)
            this.moveDown();
    }
    
    public int getScore()
    {
        return this.score;
    }
    
    public int ScoreIncrease()
    {
        return ++this.score;
    }
    
    public void setScore(int score)
    {
        this.score = score;
    }
}
