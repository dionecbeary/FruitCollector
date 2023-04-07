import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class BasicGameApp implements Runnable, KeyListener, MouseListener, MouseMotionListener {
    final int WIDTH = 1000;
    final int HEIGHT = 700;
    public JFrame frame;
    public int d1, d2, score1, score2, fruitCount;
    public boolean started;
    public Canvas canvas;
    public JPanel panel;
    public BufferStrategy bufferStrategy;
    public Player P1, P2;
    public Image greenLPic, greenRPic, purpleLPic, purpleRPic;
    public Image Pic1, Pic2;
    public Image arenaPic, startPic;
    public Image[] FruitPics = {Toolkit.getDefaultToolkit().getImage("Fruit1.png"), Toolkit.getDefaultToolkit().getImage("Fruit2.png"), Toolkit.getDefaultToolkit().getImage("Fruit3.png"), Toolkit.getDefaultToolkit().getImage("Fruit4.png"), Toolkit.getDefaultToolkit().getImage("Fruit5.png")};
    public Fruit[] Fruits;

    public static void main(String[] args) {
        BasicGameApp ex = new BasicGameApp();   //creates a new instance of the game
        new Thread(ex).start();                 //creates a threads & starts up the code in the run( ) method
    }

    public BasicGameApp() {
        setUpGraphics();
        greenLPic = Toolkit.getDefaultToolkit().getImage("greenL.png");
        greenRPic = Toolkit.getDefaultToolkit().getImage("greenR.png");
        purpleLPic = Toolkit.getDefaultToolkit().getImage("purpleL.png");
        purpleRPic = Toolkit.getDefaultToolkit().getImage("purpleR.png");
        startPic = Toolkit.getDefaultToolkit().getImage("Start.png");
        Pic1 = greenRPic;
        Pic2 = purpleLPic;
        arenaPic = Toolkit.getDefaultToolkit().getImage("arena.jpeg");
        P1 = new Player(25, 325, 0, 0);
        P2 = new Player(915, 325, 0, 0);
        d1 = 2;
        d2 = 2;
        score1 = 0;
        score2 = 0;
        fruitCount = 0;
        started = false;
        Fruits = new Fruit[5];
        for (int i = 0; i<Fruits.length; i++){
            Fruits[i] = new Fruit((int)(Math.random()*760)+100, (int)(Math.random()*660), 0, 0, i);
            fruitCount += 1;
        }
    }

    public void run() {
        //for the moment we will loop things forever.
        while (true) {
            if (started == true){
                moveThings();//move all the game objects

            }
            render();  // paint the graphics
//            pause(10); // sleep for 10 ms
        }

    }

    public void moveThings() {
        //calls the move( ) code in the objects
        P1.crash(0,840);
        P1.move();
        P2.crash(100, 940);
        P2.move();
        intersections();
    }
    public void intersections(){
        for (int i = 0; i<Fruits.length; i++){
            if ((Fruits[i].rec.intersects(P1.rec)&&(P1.isCrashing == false) )||( Fruits[i].rec.intersects(P2.rec)&&(P2.isCrashing == false))){
                if (Fruits[i].rec.intersects(P1.rec)){
//               System.out.println("point to P1");
                    score1 +=1;
                    P1.isCrashing = true;
                }
                if (Fruits[i].rec.intersects(P2.rec)){
//                System.out.println("point to P2");
                    score2 += 1;
                    P2.isCrashing = true;
                }
                Fruits[i].isAlive = false;
                fruitCount -= 1;
                System.out.println(fruitCount);
                if (fruitCount == 2 ){
                    int n = (int)(Math.random()*5);
                    Fruits[n] = new Fruit((int)(Math.random()*760)+100, (int)(Math.random()*660), 0, 0, n);
                    fruitCount +=1;
                }
            }
            if (Fruits[i].rec.intersects(P1.rec) == false && Fruits[i].rec.intersects(P2.rec) == false){
                P1.isCrashing = false;
                P2.isCrashing = false;
            }
        }
    }

    public void pause(int time) {
        //sleep
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {

        }
    }

    private void setUpGraphics() {
        frame = new JFrame("Application Template");   //Create the program window or frame.  Names it.

        panel = (JPanel) frame.getContentPane();  //sets up a JPanel which is what goes in the frame
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));  //sizes the JPanel
        panel.setLayout(null);   //set the layout

        // creates a canvas which is a blank rectangular area of the screen onto which the application can draw
        // and trap input events (Mouse and Keyboard events)
        canvas = new Canvas();
        canvas.addKeyListener(this);
        canvas.addMouseListener(this);
        canvas.addMouseMotionListener(this);
        canvas.setBounds(0, 0, WIDTH, HEIGHT);
        canvas.setIgnoreRepaint(true);

        panel.add(canvas);  // adds the canvas to the panel.
//        canvas.addMouseListener(this);
//        canvas.addMouseMotionListener(this);

        // frame operations
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //makes the frame close and exit nicely
        frame.pack();  //adjusts the frame and its contents so the sizes are at their default or larger
        frame.setResizable(false);   //makes it so the frame cannot be resized
        frame.setVisible(true);      //IMPORTANT!!!  if the frame is not set to visible it will not appear on the screen!

        // sets up things so the screen displays images nicely.
        canvas.createBufferStrategy(2);
        bufferStrategy = canvas.getBufferStrategy();
        canvas.requestFocus();
        System.out.println("DONE graphic setup");

    }

    private void render() {
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
        g.clearRect(0, 0, WIDTH, HEIGHT);

        //code here
        g.drawImage(arenaPic, 100, 0, 800, 700, null);
        g.drawImage(Toolkit.getDefaultToolkit().getImage("green.png"), 0, 0, 100, 700, null);
        g.drawImage(Toolkit.getDefaultToolkit().getImage("purple.png"), 900, 0, 100, 700, null);
        g.drawImage(Pic1, P1.xpos, P1.ypos, 60, 50, null);
        g.drawImage(Pic2, P2.xpos, P2.ypos, 60, 50, null);
        if (started == false){
            g.drawImage(startPic, 400, 300, 200, 100, null);
        } else if (started == true){
            for (int i = 0; i < Fruits.length; i++){
                if (Fruits[i].isAlive == true){
                    g.drawImage(FruitPics[Fruits[(i+1)%5].image], Fruits[i].xpos, Fruits[i].ypos, 40,40,null);
//            g.draw(new Rectangle(Fruits[i].xpos, Fruits[i].ypos, 40,40));
                }
            }
        }

        g.dispose();
        bufferStrategy.show();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        //System.out.print(code);
        int c = Math.abs(code);
        // Player 1:
        if (code == 87){
            P1.dy = -d1;
        }
        if (code == 83){
            P1.dy = d1;
        }
        if (code == 65){
            P1.dx = -d1;
        }
        if (code == 68){
            P1.dx = d1;
        }
        if (code == 81){
            System.out.println("Q");
        }

        // Player 2:
        if (code == 38){
            P2.dy = -d2;
        }
        if (code == 40){
            P2.dy = d2;
        }
        if (code == 37){
            P2.dx = -d2;
        }
        if (code == 39){
            P2.dx = d2;
        }
        if (code == 16){
            System.out.println("Shift");
        }

        if(P1.dx>0){
            Pic1 = greenRPic;
        }else if(P1.dx<0){
            Pic1 = greenLPic;
        }
        if(P2.dx>0){
            Pic2 = purpleRPic;
        }else if(P2.dx<0){
            Pic2 = purpleLPic;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        P1.dx = 0;
        P1.dy = 0;
        P2.dx = 0;
        P2.dy = 0;

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getX() > 400 && e.getX() <600 && e.getY() >300 && e.getY() <400){
            started = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
//        System.out.println(e.getX() + e.getY());
        if (started == false && e.getX() > 400 && e.getX() <600 && e.getY() >300 && e.getY() <400){
            startPic = Toolkit.getDefaultToolkit().getImage("Start copy.png");
        }else{
            startPic = Toolkit.getDefaultToolkit().getImage("Start.png");
        }
    }
}
//    @Override
//    public void run() {
//
//    }
//}
