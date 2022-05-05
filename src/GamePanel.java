



import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener{

    static final int SCREEN_WIDTH = 1280;
    static final int SCREEN_HEIGHT = 720;
    static final int UNIT_SIZE =50;
    static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
    static final int DELAY = 175;
    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];

    int bodyParts = 6;
    int applesEaten;
    int appleX;
    int appleY;
    char direction = 'R';
    boolean welcome= false;
    boolean running = false;
    Timer timer;
    Random random;
    JButton play;
    JButton Exit;
    ImageIcon backgrond_welcome = new ImageIcon("snake3.jpg");
    ImageIcon backgrond_Game = new ImageIcon("background.jpg");

    ImageIcon apple= new ImageIcon("apple.png");
    ImageIcon GoldApple= new ImageIcon("goldapple.png");


    GamePanel(){
//        x[0]=10;
//        y[0]=10;
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
//        startGame();


    }
    public void Welcome(Graphics g){

//        g.setColor(Color.red);
//        g.setFont( new Font("Ink Free",Font.BOLD, 75));
//        FontMetrics metrics2 = getFontMetrics(g.getFont());
//        g.drawString("Welcome press enter" , (SCREEN_WIDTH - metrics2.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2);

        play = new JButton("PLAY");
        play.setBounds(470, SCREEN_HEIGHT/2, 300, 50);

        this.add(play);
        play.addActionListener((event) -> {
            ResetGame();

        });
        Exit = new JButton("Exit");
        Exit.setBounds(470, (SCREEN_HEIGHT/2)+100, 300, 50);

        this.add(Exit);
        Exit.addActionListener((event) -> {
            System.exit(0);

        });



//   startGame();
    }
    public void startGame() {
        welcome=true;
        newApple();
        running = true;

        timer = new Timer(DELAY,this);
        timer.start();
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g) {
        if(!welcome){
            Welcome(g);
            g.drawImage(backgrond_welcome.getImage(),0,0,null);

        }

        if(running) {
            g.drawImage(backgrond_Game.getImage(),0,0,null);
            g.setColor(new Color(139,69,2));
           g.fillRect(0,0,50,720);
           g.fillRect(0,0,1280,50);
            g.fillRect(1230,0,50,720);
           g.fillRect(0,670,1280,50);
//            g.drawImage(apple.getImage(),appleX,appleY,null);


          if(applesEaten%5==0 && applesEaten!=0) {
//              g.setColor(new Color(255,215,0));
//              g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
              g.drawImage(GoldApple.getImage(),appleX,appleY,null);
          }else {
//              g.setColor(Color.RED);
//              g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
              g.drawImage(apple.getImage(),appleX,appleY,null);
          }

//            g.setColor(Color.red);
//            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            for(int i = 0; i< bodyParts;i++) {
                if(i == 0) {
                    g.setColor(Color.green);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
                else {
                    g.setColor(new Color(45,180,0));
//                    g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            g.setColor(Color.red);
            g.setFont( new Font("Ink Free",Font.BOLD, 40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: "+applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: "+applesEaten))/2, g.getFont().getSize());

            if((applesEaten-3)% 5 ==0 &&  applesEaten!=3 ){
                g.setColor(new Color(255,215,0));
                g.setFont( new Font("Ink Free",Font.BOLD, 40));
                g.drawString("Bonus!!", ((SCREEN_WIDTH - metrics.stringWidth("Score: "+applesEaten))/2)+200, g.getFont().getSize());
            }
        }
        if(!running&& welcome){
            gameOver(g);
        }

    }
    public void newApple(){
        appleX = random.nextInt((int)((SCREEN_WIDTH)/UNIT_SIZE))*UNIT_SIZE;
        appleY = random.nextInt((int)((SCREEN_HEIGHT)/UNIT_SIZE))*UNIT_SIZE;
    }
    public void move(){
        for(int i = bodyParts;i>0;i--) {
            x[i] = x[i-1];
            y[i] = y[i-1];
        }

        switch(direction) {
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
        }

    }
    public void checkApple() {
        if((x[0] == appleX) && (y[0] == appleY)) {
            bodyParts++;
            if(applesEaten%5==0&& applesEaten!=0)
            applesEaten+=3;
            else {
                applesEaten++;
            }
            newApple();
        }
    }
    public void checkCollisions() {
        //checks if head collides with body
        for(int i = bodyParts;i>0;i--) {
            if((x[0] == x[i])&& (y[0] == y[i])) {
                running = false;
            }
        }
        //check if head touches left border
        if(x[0] < 0) {
            running = false;
        }
        //check if head touches right border
        if(x[0] > SCREEN_WIDTH) {
            running = false;
        }
        //check if head touches top border
        if(y[0] < 0) {
            running = false;
        }
        //check if head touches bottom border
        if(y[0] > SCREEN_HEIGHT) {
            running = false;
        }

        if(!running) {
            timer.stop();
        }
    }
    public void gameOver(Graphics g) {
        //Score
        g.setColor(Color.red);
        g.setFont( new Font("Ink Free",Font.BOLD, 40));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Score: "+applesEaten, (SCREEN_WIDTH - metrics1.stringWidth("Score: "+applesEaten))/2, g.getFont().getSize());
        //Game Over text
        g.setColor(Color.red);
        g.setFont( new Font("Ink Free",Font.BOLD, 75));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Game Over" , (SCREEN_WIDTH - metrics2.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2);
        g.drawString("Press Enter To Restart",(SCREEN_WIDTH - metrics2.stringWidth("Press Enter To Restart"))/2, (SCREEN_HEIGHT/2)+70);



    }
    @Override
    public void actionPerformed(ActionEvent e) {

        if(running) {
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (direction != 'R') {
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction != 'L') {
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (direction != 'D') {
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (direction != 'U') {
                        direction = 'D';
                    }
                    break;
            }

            if(e.getKeyCode()== KeyEvent.VK_ENTER){
                if(!running){

                 ResetGame();


                }

            }

        }
    }
    public void ResetGame(){
        Exit.setVisible(false);
        play.setVisible(false);
        welcome=false;
        applesEaten=0;
        running=true;

        bodyParts = 6;
        direction = 'R';

        for (int i=0; i<x.length; i++){
            x[i]=0;
        }
        for (int i=0; i<y.length; i++){
            y[i]=0;
        }

        repaint();
        startGame();
    }
}
