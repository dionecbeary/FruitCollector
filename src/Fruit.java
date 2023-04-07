import java.awt.*;
public class Fruit {
    public int xpos, ypos, width, height, dx, dy, image;
    public boolean isAlive;
    public Rectangle rec;
    public Fruit(int pXpos, int pYpos, int pDx, int pDy, int img ){
        xpos = pXpos;
        ypos = pYpos;
        isAlive = true;
        width = 30;
        height = 30;
        dx = pDx;
        dy = pDy;
        image = img;
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
    public void move(){
        xpos = xpos + dx;
        ypos = ypos + dy;
        rec = new Rectangle(xpos, ypos, (int)width, height);
    }
}
