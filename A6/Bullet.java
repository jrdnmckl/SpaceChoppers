import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

/**
 * Helicopter machine gun bullet.
 */

public class Bullet {
    
    // For creating new bullets.
    public final static long timeBetweenNewBullets = 1000000;
    public static long timeOfLastCreatedBullet = 0;
    
    // Damage that is made to an enemy helicopter when it is hit with a bullet.
    public static int damagePower = 20;
    
    // Position of the bullet on the screen. Must be of type double because movingXspeed and movingYspeed will not be a whole number.
    public double xCoordinate;
    public double yCoordinate;
    
    // Moving speed and direction.
    private static int bulletSpeed = 20;
    private double movingXspeed;
    private double movingYspeed;
    protected Radar radar;
    // Images of helicopter machine gun bullet. Image is loaded and set in Game class in LoadContent() method.
    public static BufferedImage image;
    
    
    /**
     * Creates new machine gun bullet.
     */
    public Bullet(Radar parent, double xCoordinate, double yCoordinate, double mouseX, double mouseY)
    {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.radar = parent;
        
        setDirectionAndSpeed(mouseX, mouseY);
    }
    
    private void setDirectionAndSpeed(double mouseX, double mouseY)
    {
        // Unit direction vector of the bullet.
        double directionVx = mouseX - this.xCoordinate;
        double directionVy = mouseY - this.yCoordinate;
        double lengthOfVector = Math.sqrt(directionVx * directionVx + directionVy * directionVy);
        directionVx = directionVx / lengthOfVector; // Unit vector
        directionVy = directionVy / lengthOfVector; // Unit vector
        
        // Set speed.
        this.movingXspeed = bulletSpeed * directionVx;
        this.movingYspeed = bulletSpeed * directionVy;
    }
    public boolean hasLeftScreen()
    {
        if(xCoordinate > 0 && xCoordinate < radar.frameWidth &&
           yCoordinate > 0 && yCoordinate < radar.frameHeight)
            return false;
        else
            return true;
    }
    public void draw(Graphics g)
    {
        xCoordinate += movingXspeed;
        yCoordinate += movingYspeed;
        g.drawImage(image, (int)xCoordinate, (int)yCoordinate, null);
    }
}
