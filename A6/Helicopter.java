/*******************************************************************************
*                                                                              *
*                                                                              *
*                                                                              *
*                                                                              *
*                                                                              *
*                                                                              *
*                                                                              *
*******************************************************************************/
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.Random;
import java.awt.image.BufferedImage;


public class Helicopter extends Aircraft {

        protected double x, y, w, h, scale, scrollX, scrollY, velX, velY;
        protected boolean clicked;
        protected Radar parent;
        protected String coordinates;
        protected Graphics2D heli;
        public static final long timeBetweenNewEnemiesInit = Framework.secInNanosec * 3;
        public static BufferedImage image;
        public static long timeBetweenNewEnemies = timeBetweenNewEnemiesInit;
        public static long timeOfLastCreatedEnemy = 0;

        public void initialize(Radar parent, int x, int y)
        {
        this.health = 100;
        // super(parent);
        // this.image
        this.posX = 0;
        this.posY = 0;
        this.w = 0;
        this.h = 0;
        this.velX = randomVel();
        this.velY = randomVel();
        this.parent = parent;
        this.clicked = false;
        this.scale = parent.scale;
        this.scrollX = parent.scrollX;
        this.scrollY = parent.scrollY;
        this.health = 100;
        // Sets enemy position.
        this.x = x;
        this.y = y;
        
        }
        public void draw(Graphics g) {
                Graphics2D heli = (Graphics2D) g;
                heli.setColor(Color.RED);
                if (x <= 0){
                        velX = -velX;
                } else if (x + 40 > parent.background.getImageWidth() / 2) {
                        velX = -velX;
                }if (y <= 0) {
                        velY = -velY;
                } else if (y + 40 > parent.background.getImageHeight() / 2) {
                        velY = -velY;
                }
                x += velX;
                y += velY;
                posX = x;
                posY = y;
                w = 40;
                h = 40;
                Rectangle2D rectangle = new Rectangle2D.Double(x * scale, y * scale, 40, 40);
                heli.translate(parent.getWidth() * scale, parent.getHeight() * scale);
                heli.translate(-parent.getWidth() * scale, -parent.getHeight() * scale);
                heli.draw(rectangle);
                heli.drawImage(image, (int)x, (int)y, null);
                if (this.clicked == true){
                        heli.fill(rectangle);
                        coordinates = "Heli at: " + (int)x + " " + (int)y;
                        heli.drawString(coordinates, (int)(x * scale), (int)(y * scale));
                        heli.drawString("Health = " + health, (int)(x +40 * scale), (int)(y + 40* scale));

                } else {
                        heli.draw(rectangle);
                }
        }
        public void restartEnemy(){
            timeBetweenNewEnemies = timeBetweenNewEnemiesInit;
            timeOfLastCreatedEnemy = 0;
            velX = randomVel();
            velY = randomVel();
        }
    
        public double posX(){
            return posX;
        }
        public double posY(){
            return posY;
        }
        public double w(){
            return w;
        }
        public double h(){
            return h;
        }
        public double randomPos(){
                Random random = new Random();
                double num = (double) (Math.random() * (100));
                return num;
        }
        public void setVel(){
            velX = randomVel();
            velY = randomVel();
        }
        public double randomVel(){
                Random random = new Random();
                double velocity = (double) (Math.random() * (4) - 2);
                return velocity;
        }
        public boolean pickCorrelation(int mouseX, int mouseY) {
                double planeSize = 40 * parent.scale;
                double scaledX = x * parent.scale;
                double scaledY = y * parent.scale;
                        if(scaledX < mouseX && scaledX + planeSize >= mouseX &&
                                scaledY < mouseY && scaledY + planeSize >= mouseY) {
                                clicked = true;
                                return clicked;
                        } else {
                                clicked = false;
                                return clicked;
                        }
        }
}