import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class Explosion {

    public static BufferedImage image;

    protected double frameWidth;
    protected Radar radar;
    protected double frameHeight;
    protected int numberOfFrames;
    protected long frameRate;
    protected long startingFrameTime;
    protected long timeForNextFrame;
    protected int currentFrameNumber;
    protected boolean loop;
    public double x;
    public double y;
    protected int startingXOfFrameInImage;
    protected int endingXOfFrameInImage;
    public boolean active;
    protected long showDelay;
    protected long timeOfAnimationCreation;


    public Explosion(Radar parent, double frameWidth, double frameHeight, int numberOfFrames, long frameTime, boolean loop, double x, double y, long showDelay)
    {
        this.radar = parent;
        this.image = image;
        this.frameWidth = parent.getWidth();
        this.frameHeight = parent.getHeight();
        this.numberOfFrames = numberOfFrames;
        this.frameRate = frameTime;
        this.loop = loop;

        this.x = x;
        this.y = y;
        
        this.showDelay = showDelay;
        
        timeOfAnimationCreation = System.currentTimeMillis();

        startingXOfFrameInImage = 0;
        endingXOfFrameInImage = (int)frameWidth;

        startingFrameTime = System.currentTimeMillis() + showDelay;
        timeForNextFrame = startingFrameTime + this.frameRate;
        currentFrameNumber = 0;
        active = true;

        // try {
        //         image = ImageIO.read(new File("explosion_anim.png"));
        // } catch (Exception e) { System.out.println(e); }

    }
    public void Update()
    {
        if(timeForNextFrame <= System.currentTimeMillis())
        {
            // Next frame.
            currentFrameNumber++;

            // If the animation is reached the end, we restart it by seting current frame to zero. If the animation isn't loop then we set that animation isn't active.
            if(currentFrameNumber >= numberOfFrames)
            {
                currentFrameNumber = 0;

                // If the animation isn't a loop then we set that animation isn't active.
                if(loop != true){
                    active = false;
                }
            }

            // Starting and ending coordinates for cutting the current frame image out of the animation image.
            startingXOfFrameInImage = (int)(currentFrameNumber * 134);
            endingXOfFrameInImage = startingXOfFrameInImage + 134;

            // Set time for the next frame.
            startingFrameTime = System.currentTimeMillis();
            timeForNextFrame = startingFrameTime + frameRate;
        }
    }

    public void draw(Graphics g2d)
    {
        this.Update();
        
        // Checks if show delay is over.
        if(this.timeOfAnimationCreation + this.showDelay <= System.currentTimeMillis()){
                g2d.drawImage(image, (int)x, (int)y, (int)x + 134, (int)y + 134, startingXOfFrameInImage, 0, endingXOfFrameInImage, 134, null);
        }
    }
}