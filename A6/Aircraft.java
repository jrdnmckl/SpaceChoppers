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


public abstract class Aircraft extends JComponent{

        protected double x, y, w, h, posX, posY, velX, velY;
        protected Radar parent;
        protected int frameRate;
        protected Random random;
        protected boolean clicked;
        protected double scale; 
        public int health;
        public static final long timeBetweenNewEnemiesInit = Framework.secInNanosec * 3;
        public static long timeBetweenNewEnemies = timeBetweenNewEnemiesInit;
        public static long timeOfLastCreatedEnemy = 0;

        public void initialize(Radar parent, int x, int y)
        {
        health = 100;
        
        }
        public void restartEnemy(){}
        public void draw(Graphics g) {}
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
        public void setVel(){
        }
        public double randomVel(){
            return 1;
        }
        public boolean pickCorrelation(int mouseX, int mouseY) {
            return false;
        }
}

