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
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.Random;
import java.awt.image.BufferedImage;


public class Plane extends Aircraft {

        protected boolean clicked;
        public double x, y, w, h, scale, scrollX, scrollY, velX, velY;
        protected Graphics2D circ;
        protected Radar parent;
        protected String coordinates;
        public static final long timeBetweenNewEnemiesInit = Framework.secInNanosec * 3;
        public static long timeBetweenNewEnemies = timeBetweenNewEnemiesInit;
        public static long timeOfLastCreatedEnemy = 0;
        public static BufferedImage image;

        public void initialize(Radar parent, int x, int y) {
        // super(parent);
        this.posX = 0;
        this.posY = 0;
        this.w = 0;
        this.h = 0;
        this.velX = randomVel();
        this.velY = randomVel();
        this.parent = parent;
        this.scale = parent.scale;
        this.scrollX = parent.scrollX;
        this.scrollY = parent.scrollY;
        this.clicked = false;
        this.health = 100;
        // Sets enemy position.
        this.x = x;
        this.y = y;
        }
        public void draw(Graphics g) {
                Graphics2D circ = (Graphics2D) g;
                circ.setColor(Color.WHITE);
                if (x < 0){
                        velX = -velX;
                } else if (x + 40 > parent.background.getImageWidth() / 2) {
                        velX = -velX;
                }if (y < 0) {
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
                Rectangle2D circle = new Rectangle2D.Double(x * scale, y * scale, 40, 40);
                circ.translate(parent.getWidth() / scale, parent.getHeight() / scale );
                circ.translate(-parent.getWidth() / scale, -parent.getHeight() / scale);
                circ.draw(circle);
                circ.drawImage(image, (int)x , (int)y, null);

                if (this.clicked == true) {
                        circ.fill(circle);
                        coordinates = "Plane at: " + (int)x  + " " + (int)y;
                        circ.drawString(coordinates, (int)(x * scale), (int)(y * scale));
                        circ.drawString("Health = " + health, (int)(x + 40 * scale), (int)(y + 40 * scale));
                } else {
                        circ.draw(circle);
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
                double scaledX = x * parent.scale;
                double scaledY = y * parent.scale;
                double planeSize = 40 * parent.scale;
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

