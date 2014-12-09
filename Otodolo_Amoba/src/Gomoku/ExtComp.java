package Gomoku;

import javax.swing.JButton;


public class ExtComp extends JButton{
    int X;
    int Y;
    
    public ExtComp() {
    
    }
    
    public void setX(int x){
        this.X = x;
    }
    
    public void setY(int y){
        this.Y = y;
    }
    
    public int getX(){
        return X;
    }
    
    public int getY(){
        return Y;
    }
    
}
