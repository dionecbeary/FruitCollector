import java.awt.*;
public class Player {
    public int xpos, ypos, width, height, dx, dy;
    public boolean isAlive;
    public Rectangle rec;
    public Player(int pXpos, int pYpos, int pDx, int pDy){
        xpos = pXpos;
        ypos = pYpos;
        isAlive = true;
        width = 30;
        height = 30;
        dx = pDx;
        dy = pDy;
        rec = new Rectangle(xpos, ypos, (int)width, height);
    }
    public void move(){
        xpos = xpos + dx;
        ypos = ypos + dy;
        rec = new Rectangle(xpos, ypos, (int)width, height);
    }
    public void crash(int left, int right){
        if (ypos>640 || ypos<0){// ypos > 700-hieght
            dy=-dy;
        }
        if (xpos < left || xpos> right){
            dx=-dx;
        }
    }
    public void wrap(){
        if (xpos<100){// ypos > 700-hieght
            xpos = 900-30;
        }
        if (xpos > 900-30){
            xpos = 100;
        }
        if (ypos < 0){
            ypos = 700-30;
        }
        if (ypos > 700-30){
            ypos = 0;
        }
    }
}
