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
import java.lang.Math.*;
import java.awt.image.BufferedImage;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;

public class Crosshair extends JComponent {
        public static double x, y, velX, velY, stoppingX, stoppingY;
        protected Radar radar;
        protected int healthInit = 100;
        protected int numberOfRocketsInit = 80;
        protected int numberOfAmmoInit = 1000;
        public int numberOfAmmo;
        public int numberOfRockets;
        public int health;
        public boolean stopped;
        public static BufferedImage image;

        public Crosshair(Radar parent) {
                
                this.health = healthInit;
                this.numberOfAmmo = numberOfAmmoInit;
                this.x = 500;
                this.y = 400;
                this.velX = 0;
                this.velY = 0;
                this.stoppingX = -.1;
                this.stoppingY = -.1;
                this.setFocusable(true);
                this.setFocusTraversalKeysEnabled(true);
                this.requestFocusInWindow(true);
                this.radar = parent;
                this.numberOfRockets = numberOfRocketsInit;
        }

        /**
        * Resets the player.
        */
        public void reset(int xCoordinate, int yCoordinate) {
                this.health = healthInit;

                this.numberOfRockets = numberOfRocketsInit;
                this.numberOfAmmo = numberOfAmmoInit;

                this.x = xCoordinate;
                this.y = yCoordinate;

                this.velX = 0;
                this.velY = 0;
                this.stopped = true;
        }
        public void draw(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setColor(Color.GREEN);
                if (x <= 0){
                        x = 0;
                }
                if (x + 40 > radar.background.getImageWidth() / 2) {
                        x = radar.background.getImageWidth() / 2 - 40;
                }
                if (y <= 0) {
                        y = 0;
                }
                if (y + 40 >= radar.background.getImageHeight() / 2) {
                        y = radar.background.getImageHeight() / 2 - 40;
                }

                if(velX > 0){
                        flip(image);
                        System.out.println("should be flipping...");
                        velX += stoppingX;
                        if(velX <= 0)
                                velX = 0;
                }
                if(velX < 0){
                        g2.drawImage(image, -(int)x, (int)y, null);
                        System.out.println("should be flipping...");
                        velX -= stoppingX;
                        if(velX >= 0)
                                velX = 0;
                }
                if(velY > 0){
                        velY += stoppingY;
                        if (velY <= 0)
                                velY = 0;
                } 
                if(velY < 0){
                        velY -= stoppingY;
                        if (velY >= 0)
                                velY = 0;
                }
                x += velX;
                y += velY;
                radar.scrollX += velX;
                radar.scrollY += velY;
                g2.drawImage(image, (int)x, (int)y, null);
        }
        public void up() {
                velY = -5;
        }
        public void down() {
                velY = 5;
        }
        public void left() {
                velX = -5;
        }
        public void right() {
                flip(image);
                velX = 5;
        }       


        private static BufferedImage flip (BufferedImage image){
                System.out.println("in flip!");
                AffineTransform tx = AffineTransform.getScaleInstance(1, -1);
                tx.translate(0, -image.getHeight(null));
                AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
                image = op.filter(image, null);

                return image;

        }
}
