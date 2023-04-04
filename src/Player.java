public class Player {
    public int xpos, ypos, width, height, dx, dy;
    public boolean isAlive;
    public Player(int pXpos, int pYpos, int pDx, int pDy){
        xpos = pXpos;
        ypos = pYpos;
        isAlive = true;
        width = 30;
        height = 30;
        dx = pDx;
        dy = pDy;
    }
    public void move(){
        xpos = xpos + dx;
        ypos = ypos + dy;
    }
    public void crash(int left, int right){
        if (ypos>640 || ypos<0){// ypos > 700-hieght
            dy=-dy;
        }
        if (xpos < left || xpos> right){
            dx=-dx;
        }
    }
    public void wrap(){ // when eaten special fruit
        if (xpos<100){// ypos > 700-hieght
            xpos = 900;
        }
        if (xpos>900){
            xpos = 100;
        }
    }
}
