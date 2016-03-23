import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import java.io.File;
import java.text.DecimalFormat;

public class Framework extends JPanel implements MouseListener, MouseMotionListener {
    
    public static int frameWidth;
    public static int frameHeight;
    public static final long secInNanosec = 1000000000L;
    private final int GAME_FPS = 60;
    /* Pause between updates. It is in nanoseconds.*/
    private final long GAME_UPDATE_PERIOD = secInNanosec / GAME_FPS;
    public static final long milisecInNanosec = 1000000L;
    public static enum GameState{STARTING, VISUALIZING, GAME_CONTENT_LOADING, MAIN_MENU, OPTIONS, PLAYING, GAMEOVER, DESTROYED}
    public static GameState gameState;
    protected long gameTime, lastTime, endTime;
    public Main parent;
    protected Radar radar;
    protected Font font;
    protected double mouseX, mouseY, seconds;
    protected Graphics g;
    protected BufferedImage menuBorderImg;
        public Framework (Main parent)
    {
        super();
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.parent = parent;
        setPreferredSize(new Dimension(1000,800));
        setBackground(Color.BLACK);
        gameState = GameState.VISUALIZING;
        font = new Font("monospaced", Font.BOLD, 18);
        radar = new Radar(this);
        this.setLayout(new FlowLayout());
        this.add(radar, BorderLayout.CENTER);
        this.mouseX = radar.getMouseX();
        this.mouseY = radar.getMouseY();
        this.frameWidth = parent.getWidth();
        this.frameHeight = parent.getHeight();
        InputMap imap = getInputMap(WHEN_IN_FOCUSED_WINDOW);
        ActionMap amap = getActionMap();

        imap.put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, false), "W Pressed");
        imap.put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, true), "W Released");
        imap.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, false), "S Pressed");
        imap.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, true), "S Released");
        imap.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, false), "D Pressed");
        imap.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, true), "D Released");
        imap.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, false), "A Pressed");
        imap.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, true), "A Released");
        imap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false), "ESC Pressed");
        imap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false), "ENTER Pressed");
        
        amap.put("ENTER Pressed", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameState = GameState.PLAYING;
            }
        });
        amap.put("W Pressed", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                radar.player.up();
            }
        });
        amap.put("S Pressed", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                radar.player.down();
            }
        });
        amap.put("D Pressed", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                radar.player.right();
            }
        });
        amap.put("A Pressed", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                radar.player.left();
            }
        });
        amap.put("W Released", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
        amap.put("S Released", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
            }
        });
        amap.put("D Released", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
        amap.put("A Released", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
        amap.put("ESC Pressed", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });


        //We start game in new thread.
        Thread gameThread = new Thread() {
            @Override
            public void run(){
                GameLoop();
            }
        };
        gameThread.start();
    }

    private void initialize() 
    {
        font = new Font("monospaced", Font.BOLD, 28);
    }

    private void loadContent()
    {
        try {
            menuBorderImg = ImageIO.read(new File("menu_border.png"));
        } catch (IOException ex) {
            Logger.getLogger(Framework.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
/**
     * In specific intervals of time (GAME_UPDATE_PERIOD) the game/logic is updated and then the game is drawn on the screen.
     */
    private void GameLoop()
    {
        // This two variables are used in VISUALIZING state of the game. We used them to wait some time so that we get correct frame/window resolution.
        long visualizingTime = 0, lastVisualizingTime = System.nanoTime();
        
        // This variables are used for calculating the time that defines for how long we should put threat to sleep to meet the GAME_FPS.
        long beginTime, timeTaken, timeLeft;
        
        while(true)
        {
            beginTime = System.nanoTime();
            
            switch (gameState)
            {

                case GAMEOVER:
                    //...
                break;
                case MAIN_MENU:
                    //...
                break;
                case OPTIONS:
                    //...
                break;
                case GAME_CONTENT_LOADING:
                    //...
                break;
                case STARTING:
                    // Sets variables and objects.
                       initialize();
                    // Load files - images, sounds, ...
                       loadContent();

                    // When all things that are called above finished, we change game status to main menu.
                    gameState = GameState.MAIN_MENU;
                break;
                case PLAYING:
                    gameTime += System.nanoTime() - lastTime;
                    seconds = ((double) gameTime / 1000000000);

                    radar.updateGame(gameTime, mouseX, mouseY);

                    lastTime = System.nanoTime();


                break;
                case VISUALIZING:
                    //wait one second for the window/frame to be set to its correct size. 
                    if(parent.getWidth() > 1 && visualizingTime > secInNanosec)
                    {
                        frameWidth = parent.getWidth();
                        frameHeight = parent.getHeight();
                        // When we get size of frame we change status of game.
                        gameState = GameState.STARTING;
                    }
                    else
                    {
                        visualizingTime += System.nanoTime() - lastVisualizingTime;
                        lastVisualizingTime = System.nanoTime();
                    }
                break;
            }
            repaint();            
            // Here we calculate the time that defines for how long we should put threat to sleep to meet the GAME_FPS.
            timeTaken = System.nanoTime() - beginTime;
            timeLeft = (GAME_UPDATE_PERIOD - timeTaken) / milisecInNanosec; // In milliseconds
            // If the time is less than 10 milliseconds, then we will put thread to sleep for 10 millisecond so that some other thread can do some work.
            if (timeLeft < 10) 
                timeLeft = 10; //set a minimum
            try {
                 //Provides the necessary delay and also yields control so that other thread can do work.
                 Thread.sleep(timeLeft);
            } catch (InterruptedException ex) { }
        }
    }
    
    public void paintComponent(Graphics g)
    {
        switch (gameState)
        {
            case PLAYING:
                radar.draw(g, mouseX, mouseY, gameTime);
            break;
            case GAMEOVER:
                drawMenuBackground(g);
                g.setColor(Color.WHITE);
                g.drawString("Press ENTER to restart or ESC to exit.", frameWidth / 2 - 113, frameHeight / 4 + 30);
                radar.drawStatistic(g, gameTime);
                g.setFont(font);
                g.drawString("GAME OVER", frameWidth / 2 - 90, frameHeight / 4);
            break;
            case MAIN_MENU:
                drawMenuBackground(g);
                g.setColor(Color.WHITE);
                g.drawString("Use w, a, s, and d OR the arrow keys to move the helicopter.", frameWidth / 2 - 117, frameHeight / 2 - 30);
                g.drawString("Use left mouse button to fire bullets and right mouse button to fire rockets.", frameWidth / 2 - 180, frameHeight / 2);
                g.drawString("Press any key to start the game or ESC to exit.", frameWidth / 2 - 114, frameHeight / 2 + 30);
            break;
            case OPTIONS:
                //...
            break;
            case GAME_CONTENT_LOADING:
                g.setColor(Color.WHITE);
                g.drawString("GAME is LOADING", frameWidth / 2 - 50, frameHeight / 2);
            break;
        }
    }

    private void newGame()
    {
        // We set gameTime to zero and lastTime to current time for later calculations.
        gameTime = 0;
        lastTime = System.nanoTime();
        
        radar = new Radar(this);
    }
       /**
     *  Restart game - reset game time and call RestartGame() method of game object to reset some variables.
     */
    private void restartGame()
    {
        // We set gameTime to 0 and lastTime to current time for later calculations.
        gameTime = 0;
        lastTime = System.nanoTime();

        radar.restartGame();        
        // We change game status so that the game can start.
        gameState = GameState.PLAYING;
    }
        private void drawMenuBackground(Graphics g){
        g.drawImage(menuBorderImg,  0, 0, frameWidth, frameHeight, null);
        g.setColor(Color.WHITE);
        g.drawString("SpaceChopper", 7, frameHeight - 5);
    }
    public void mousePressed(MouseEvent e){}
    
    public void mouseReleased(MouseEvent e){}
    
    public void mouseClicked(MouseEvent mouse) { 
                this.mouseX = mouse.getX();
                this.mouseY = mouse.getY();
                if (mouse.getButton() == 3){
                    radar.player.numberOfRockets--;
                    radar.numberOfShots++;
                    Rocket r = new Rocket();
                    r.initialize(radar, radar.player.x, radar.player.y, mouseX, mouseY);
                    radar.rockets.add(r);
                }
                if(mouse.getButton() == 1){
                    radar.numberOfShots++;
                    for (int i = 0; i < radar.numAircraft; i++) {
                        if(radar.fleet.get(i).pickCorrelation((int)mouseX, (int)mouseY) == true) {
                             radar.aircraft = radar.fleet.get(i);
                        }
                }
                radar.player.numberOfAmmo--;
                Bullet b = new Bullet(radar, radar.player.x, radar.player.y, mouseX, mouseY);
                radar.bullets.add(b);
            }
    }
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseMoved(MouseEvent e){}
    public void mouseDragged(MouseEvent e){}
}