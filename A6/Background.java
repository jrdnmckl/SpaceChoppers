import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class Background {
        
        public static BufferedImage image;
        protected int x, y;
        protected Radar radar;
        protected Crosshair player;
        protected double scrollX, scrollY, xPositions[];

        public void initialize(Radar parent, BufferedImage image, int y) {
                this.radar = parent;
                this.player = parent.player;
                this.scrollX = parent.scrollX;
                this.scrollY = parent.scrollY;
                this.y = y;
                this.image = image;

                int numPositions = (radar.frameWidth / this.image.getWidth()) + 2;
                xPositions = new double[numPositions];

                for (int i = 0; i < xPositions.length; i++){
                        xPositions[i] = i * image.getWidth();
                }
        }

        private void update(){
                for(int i = 0; i < xPositions.length; i++){
                        xPositions[i] += scrollX;

                        if(scrollX < 0){
                                if(xPositions[i] <= -image.getWidth()){
                                        xPositions[i] = image.getWidth() * (xPositions.length - 1);
                                }
                        } else {
                                if(xPositions[i] >= image.getWidth() * (xPositions.length - 1)) {
                                        xPositions[i] = -image.getWidth();
                                }
                        }
                }
        }

        public void draw(Graphics g) {
        
                update();
                for(int i = 0; i < xPositions.length; i++){
                        g.drawImage(this.image, (int)xPositions[i], y, null);
                }

        }
        public void setX(int x) {
                this.x = x;
        }
        public int getX() {
                return this.x;
        }
        public int getY() {
                return this.y;
        }
        public int getImageWidth() {
                return image.getWidth();
        }
        public int getImageHeight() {
                return image.getHeight();
        }
}