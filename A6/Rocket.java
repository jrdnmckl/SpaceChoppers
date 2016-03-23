import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Rocket {
    
    // Time that must pass before another rocket can be fired.
    public final static long timeBetweenNewRockets = Framework.secInNanosec / 4;
    public static long timeOfLastCreatedRocket = 0;
    
    // Damage that is made to an enemy helicopter when it is hit with a rocket.
    public static int damagePower = 75;
    
    // Rocket position
    public double x;
    public double y;
    
    // Moving speed and also direction. Rocket goes always straight, so we move it only on x coordinate.
    private double movingXspeed;
    private double movingYspeed;
    private static int rocketSpeed = 5;
    // Image of rocket. Image is loaded and set in Game class in LoadContent() method.
    public static BufferedImage rocketImg;
    protected Radar radar;


    /**
     * Set variables and objects for this class.
     */
    public void initialize(Radar parent, double x, double y, double mouseX, double mouseY)
    {
        this.x = x;
        this.y = y;
        this.radar = parent;
                
        setDirectionAndSpeed(mouseX, mouseY);
    }
    private void setDirectionAndSpeed(double mouseX, double mouseY){

            // Unit direction vector of the bullet.
        double directionVx = mouseX - this.x;
        double directionVy = mouseY - this.y;
        double lengthOfVector = Math.sqrt(directionVx * directionVx + directionVy * directionVy);
        directionVx = directionVx / lengthOfVector; // Unit vector
        directionVy = directionVy / lengthOfVector; // Unit vector
        
        // Set speed.
        this.movingXspeed = rocketSpeed * directionVx;
        this.movingYspeed = rocketSpeed * directionVy;
    }
    
    /**
     * Checks if the rocket is left the screen.
     * 
     * @return true if the rocket is left the screen, false otherwise.
     */
    public boolean hasLeftScreen()
    {
        if(x > 0 && x < radar.frameWidth && y > 0 && y < radar.frameHeight) //Rocket moves only on x coordinate so we don't need to check y coordinate.
            return false;
        else
            return true;
    }
    
    
    /**
     * Moves the rocket.
     */
    public void update()
    {
        x += movingXspeed;
        y += movingYspeed;
    }
    
    
    /**
     * Draws the rocket to the screen.
     * 
     * @param g2d Graphics2D
     */
    public void draw(Graphics g)
    {
        g.drawImage(rocketImg, (int)x, (int)y, null);
    }
}
