import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.geom.Arc2D.Double;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.File;

public class Radar extends JPanel {
	    protected Aircraft aircraft;
        protected ArrayList<Aircraft> fleet;
        protected ArrayList<Bullet> bullets;
        protected ArrayList<Explosion> explosions;
        protected ArrayList<Rocket> rockets;
        protected Explosion explosion;
        protected boolean pressed;
        protected Background background;
        protected BufferedImage space, animation, bulletImg, explosionImg, rocketImg, heliImg, planeImg;
    	protected Crosshair player;
    	protected double x, y, scale;
    	protected Graphics g;
    	protected Helicopter heli;
    	protected int frameWidth, frameHeight, mouseX, mouseY, numAircraft, 
                                            numExplosions, numPlanes, numHelis;
        protected Main main;                                                     
    	protected Plane plane;
    	protected Framework parent;
    	protected Random random;

        public double accuracy, numberOfShots, numberOfHits, scrollX, scrollY;
        public int destroyedEnemies;
        protected Font font;

    	public Radar (Framework parent) {
            super();
            setBackground(Color.BLACK);
            this.parent = parent;
            this.main = parent.parent;
            this.frameWidth = main.getWidth();
            this.frameHeight = main.getHeight();
            Framework.gameState = Framework.GameState.GAME_CONTENT_LOADING;
            Thread threadforInitGame = new Thread(){
                @Override
                public void run() {
                    initialize();
                    loadContent();
                    Framework.gameState = Framework.GameState.MAIN_MENU;
            }
        };
        threadforInitGame.start();
        }
        public void initialize(){
            x = 0;
            y = 0;
            scrollX = 0;
            scrollY = 0;
            scale = 1;
            fleet = new ArrayList<Aircraft>();
            bullets = new ArrayList<Bullet>();
            rockets = new ArrayList<Rocket>();
            explosions = new ArrayList<Explosion>();
            player = new Crosshair(this);
            aircraft = null;
            numAircraft = fleet.size();
            numExplosions = explosions.size();
            numPlanes = 0;
            numHelis = 0;
            numberOfHits = 0;
            numberOfShots = 0;
            random = new Random();
            font = new Font("monospaced", Font.BOLD, 18);
            background = new Background();
        }
        public void loadContent() {
                try {
                        space = ImageIO.read(new File("space.png"));
                        Bullet.image = ImageIO.read(new File("bullet.png"));
                        Explosion.image = ImageIO.read(new File("explosion_anim.png"));
                        Rocket.rocketImg = ImageIO.read(new File("rocket.png"));
                        Crosshair.image = ImageIO.read(new File("heli.png"));
                        Helicopter.image = ImageIO.read(new File("copter.png"));
                } catch (IOException e) { 
                    Logger.getLogger(Radar.class.getName()).log(Level.SEVERE, null, e);
                 }
                background.initialize(this, space, 0);
        }
    	public void draw(Graphics g, double mouseX, double mouseY, long gameTime) {
            Graphics2D radar = (Graphics2D) g;
            Graphics2D window = (Graphics2D) g;
            if(scrollX >= getWidth()){
                scrollX = getWidth();
            }else if(scrollX <= 0){
                scrollX = 0;
            }
            if(scrollY >= getHeight()){
                scrollX = getHeight();
            } else if (scrollY <= 0){
                scrollY = 0;
            }
            background.draw(g);
    		radar.setColor(Color.GREEN);

    		//outer circle
    		radar.draw(new Arc2D.Double(x, y, frameWidth, frameHeight, 0, 360, 0));
    		//quadrant lines
    		radar.draw(new Line2D.Double(frameWidth / 2, 0, frameWidth / 2, frameHeight));
    		radar.draw(new Line2D.Double(0, frameHeight / 2, frameWidth, frameHeight / 2));
            radar.scale(scale, scale);

            if(isPlayerAlive()){
                player.draw(g);
            }

            g.setFont(font);
            g.setColor(Color.WHITE);
            
            g.drawString(formatTime(gameTime), frameWidth / 2 - 45, 21);
            g.drawString("DESTROYED: " + destroyedEnemies, 10, 25);
            g.drawString("ROCKETS: "   + player.numberOfRockets, 10, 50);
            g.drawString("AMMO: "      + player.numberOfAmmo, 10, 75);

            for (int i = 0; i < fleet.size(); i++) {    
                fleet.get(i).draw(g);
            }
            for(int i = 0; i < explosions.size(); i++) {
                explosions.get(i).draw(g);
            }
            for(int i = 0; i < bullets.size(); i++) {
                bullets.get(i).draw(g);
            }
            for(int i = 0; i < rockets.size(); i++) {
                rockets.get(i).draw(g);
            }
            updateGame(gameTime, mouseX, mouseY);
    	}
            /**
     * Draws some game statistics when game is over. */
    public void drawStatistic(Graphics g, long gameTime){
        g.drawString("Time: " + formatTime(gameTime),                frameWidth/2 - 50, frameHeight/3 + 80);
        g.drawString("Rockets left: "      + player.numberOfRockets, frameWidth/2 - 55, frameHeight/3 + 105);
        g.drawString("Ammo left: "         + player.numberOfAmmo,    frameWidth/2 - 55, frameHeight/3 + 125);
        g.drawString("Destroyed enemies: " + destroyedEnemies,       frameWidth/2 - 65, frameHeight/3 + 150);
        g.drawString("Accuracy: "          + accuracy + " %",        frameWidth/2 - 70, frameHeight/3 + 170);
        g.setFont(font);
        g.drawString("Statistics: ",                                 frameWidth/2 - 75, frameHeight/3 + 60);
    }
        private boolean isPlayerAlive() {
                if(player.health <= 0) {
                    return false;
                }
                return true;
        }
    	public void changeSpeed() {
    		
    		for(int i = 0; i < numAircraft; i++){
                    fleet.get(i).setVel();
                    fleet.get(i).setVel();
    		}
    	}
    	public void addPlane() {
    		fleet.add(new Plane());
    		numAircraft++;
    		numPlanes++;
    		this.add(fleet.get(numAircraft - 1));
    		fleet.get(numAircraft - 1).draw(g);
    	}
    	public void addHeli() {
            this.heli = new Helicopter();
            heli.initialize(this, 0, 100);
    		fleet.add(heli);
    		numAircraft++;
    		numHelis++;
    		this.add(fleet.get(numAircraft - 1));
            fleet.get(numAircraft - 1).draw(g);
    	}
    	public void addCrosshair() {
    		this.player = new Crosshair(this);
    		this.add(this.player);
    		this.player.setFocusable(true);
    		this.player.draw(g);
    	}
    	public void clearFleet() {
    		for(int i = 0; i < numAircraft; i++) {
                fleet.remove(i);
                numAircraft--;
    		}
    		numAircraft = 0;
    		numPlanes = 0;
    		numHelis = 0;
    	}
    	public void zoomIn(){
    		scale = scale * 1.1;
    		for(int i = 0; i < numAircraft; i++){
    			fleet.get(i).draw(g);
    		}
    	}
    	public void zoomOut() {
    		scale = scale / 1.1;
    		for(int i = 0; i < numAircraft; i++) {
    			fleet.get(i).draw(g);
    		}
    	}
                /**
        * Restart game - reset some variables.
        */
        public void restartGame()
        {
            player.reset(frameWidth / 4, frameHeight / 4);
            for(int i = 0; i < numAircraft; i++){
                this.aircraft = fleet.get(i); 
                aircraft.restartEnemy();
            }
            
            Bullet.timeOfLastCreatedBullet = 0;
            Rocket.timeOfLastCreatedRocket = 0;

            // Empty all the lists.
            fleet.clear();
            bullets.clear();
            rockets.clear();
            explosions.clear();

            // Statistics
            destroyedEnemies = 0;
            accuracy = 0;
            numberOfHits = 0;
            numberOfShots = 0;
         }

        public void updateGame(long gameTime, double mouseX, double mouseY) {
        /* Player */
        // When player is destroyed and all explosions are finished showing we change game status.
        if( !isPlayerAlive() && explosions.isEmpty() ){
            Framework.gameState = Framework.GameState.GAMEOVER;
            return; // If player is destroyed, we don't need to do thing below.
        }
        // When a player is out of rockets and machine gun bullets, and all lists 
        // of bullets, rockets and explosions are empyt(end showing) we finish the game.
        if(player.numberOfAmmo <= 0 && 
           player.numberOfRockets <= 0 && 
           bullets.isEmpty() && 
           rockets.isEmpty() && 
           explosions.isEmpty())
        {
            Framework.gameState = Framework.GameState.GAMEOVER;
            return;
        }
        accuracy = getAccuracy();
        updateBullets();
        updateRockets(); 
        createEnemy(gameTime);
        // System.out.println("Game Time : " + gameTime / 1000000000);
        // System.out.println("System Time : " + System.nanoTime());

        updateCollisions();
        updateEnemies();
        updateExplosions();
        }
    private void createEnemy(long gameTime)
    {
        if(gameTime - Aircraft.timeOfLastCreatedEnemy >= Aircraft.timeBetweenNewEnemies)
        {
            int randomNum = random.nextInt(2);
            int xCoordinate;
            Aircraft enemy;
            if(randomNum % 2 == 0){
                enemy = new Plane();
                xCoordinate = frameWidth - 50;
            } else {
                enemy = new Helicopter();
                xCoordinate = 10;
            }
            int yCoordinate = random.nextInt(frameHeight - 40);
            enemy.initialize(this, xCoordinate, yCoordinate);
            fleet.add(enemy);
            numAircraft++;
            // Sets new time for last created enemy.
            Aircraft.timeOfLastCreatedEnemy = gameTime;
        }
    }
        private void updateCollisions() {
            if(numAircraft > 1) {
                for (int i = 0; i < fleet.size(); i++) {
                        Aircraft plane1 = fleet.get(i);
                        Rectangle2D craft1 = new Rectangle2D.Double(plane1.posX(), plane1.posY(), plane1.w(), plane1.h());
                        for (int j = (numAircraft - 1); j > i; j--){
                            Aircraft plane2 = fleet.get(j);
                            Rectangle2D craft2 = new Rectangle2D.Double(plane2.posX(), plane2.posY(), plane2.w(), plane2.h());
                            if(craft1.intersects(craft2)) {
                                Explosion explosion = new Explosion(this, (int)(134 * scale), (int)(134 * scale), 12, 45, false, plane1.posX() * scale, plane1.posY() * scale, 100);
                                Explosion explosion2 = new Explosion(this, (int)(134 * scale), (int)(134 * scale), 12, 45, false, plane2.posX() * scale, plane2.posY() * scale, 100);
                                explosions.add(explosion);
                                explosions.add(explosion2);
                                numExplosions += 2;
                                fleet.remove(j);
                                fleet.remove(i);
                                numAircraft -= 2;
                                parent.repaint();
                                break;
                            }
                        }
                }
            }
	   }
        public void updateBullets(){
                for(int i = 0; i < bullets.size(); i++){
                        Bullet b = bullets.get(i);

                        if(b.hasLeftScreen()){
                                bullets.remove(i);
                                continue;
                        }
                        Rectangle2D bulletRect = new Rectangle2D.Double((int)b.xCoordinate, (int)b.yCoordinate, b.image.getWidth(), b.image.getHeight());
                        for(int j = 0; j < fleet.size(); j++){
                                Aircraft craft = fleet.get(j);
                                Rectangle2D enemyRect = new Rectangle2D.Double(craft.posX(), craft.posY(), craft.w(), craft.h());
                                if(bulletRect.intersects(enemyRect)) {
                                        Explosion explosion = new Explosion(this, (int)(134 * scale), (int)(134 * scale), 12, 45, false, craft.posX() * scale, craft.posY() * scale - random.nextInt(40), 100);
                                        explosions.add(explosion);
                                        numExplosions += 1;
                                        numberOfHits++;
                                        fleet.get(j).health -= Bullet.damagePower;
                                        bullets.remove(i);
                                        parent.repaint();
                                        break;
                                }
                        }
                }
        }
        public void updateEnemies()
        {
            for(int i = 0; i < fleet.size(); i++){
                Aircraft enemy = fleet.get(i);

                Rectangle2D playerRect = new Rectangle2D.Double(player.x, player.y, 40, 40);
                Rectangle2D enemyRect = new Rectangle2D.Double(enemy.posX(), enemy.posY(), enemy.w(), enemy.h());
                if(playerRect.intersects(enemyRect)){
                    player.health = 0;
                    fleet.remove(i);
                    numAircraft--;
                    for(int expNum = 0; expNum < 3; expNum++){
                        Explosion explosion = new Explosion(this, (int)(134 * scale), (int)(134 * scale), 12, 45, false, player.x * scale + expNum * 60, player.y * scale - random.nextInt(40), expNum * 100 + random.nextInt(100));
                        explosions.add(explosion);
                    }
                    for(int expNum = 0; expNum < 3; expNum++){
                        Explosion explosion = new Explosion(this, (int)(134 * scale), (int)(134 * scale), 12, 45, false, enemy.posX() * scale + expNum * 60, enemy.posY() * scale - random.nextInt(40), expNum * 100 + random.nextInt(100));
                        explosions.add(explosion);
                    }
                    break;
                }

                if(enemy.health <= 0){
                    Explosion explosion = new Explosion(this, (int)(134 * scale), (int)(134 * scale), 12, 45, false, enemy.posX() * scale, enemy.posY() * scale - random.nextInt(40), 100);
                    explosions.add(explosion);

                    destroyedEnemies++;

                    fleet.remove(i);
                    numAircraft--;
                    continue;
                }
            }
        }

        public void updateRockets() {
            for(int i = 0; i < rockets.size(); i++)
            {
                Rocket rocket = rockets.get(i);                
                rocket.update();

                if(rocket.hasLeftScreen())
                {
                    rockets.remove(i);
                    continue;
                }
                
                Rectangle2D rocketRect = new Rectangle2D.Double(rocket.x, rocket.y, rocket.rocketImg.getWidth(), rocket.rocketImg.getHeight());
                for(int j = 0; j < fleet.size(); j++) {

                    Aircraft enemy = fleet.get(j);
                    Rectangle2D enemyRect = new Rectangle2D.Double(enemy.posX(), enemy.posY(), 40, 40);

                    if(rocketRect.intersects(enemyRect)) {
                        Explosion explosion = new Explosion(this, (int)(134 * scale), (int)(134 * scale), 12, 45, false, enemy.posX() * scale, enemy.posY() * scale - random.nextInt(40), 100);
                        explosions.add(explosion);
                        numExplosions += 1;
                        numberOfHits++;
                        rockets.remove(i);
                        enemy.health -= Rocket.damagePower;
                        break;
                    }
                }
            }
        }

        public void updateExplosions() {
                for(int i = 0; i < explosions.size(); i++) {
                    // If the animation is over we remove it from the list.
                    if(!explosions.get(i).active){
                                explosions.remove(i);
                                numExplosions--;
                        }

                }
        }
    /**
     * Format given time into 00:00 format.
     */
    private static String formatTime(long time){
            // Time in seconds.

            int sec = (int)(time / Framework.secInNanosec);

            // Time in minutes and seconds.
            int min = sec / 60;
            sec = sec - (min * 60);

            String minString, secString;

            if(min <= 9)
                minString = "0" + Integer.toString(min);
            else
                minString = "" + Integer.toString(min);

            if(sec <= 9)
                secString = "0" + Integer.toString(sec);
            else
                secString = "" + Integer.toString(sec);

            return minString + ":" + secString;
    }   

        public int getAccuracy(){
            
            double accuracyRate;
            if(numberOfShots == 0 || numberOfHits == 0){
                accuracyRate = 0;
                return (int)accuracyRate;
            } else {
                accuracyRate = (numberOfHits / numberOfShots);
                accuracyRate *= 100;
                return (int)accuracyRate;
            }
        }
        public int getMouseX(){
            return mouseX;
        }
        public int getMouseY(){
            return mouseY;
        }
}

