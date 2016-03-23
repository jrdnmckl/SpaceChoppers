// import java.util.ArrayList;
// import java.util.Random;
// import java.awt.*;
// import java.awt.image.BufferedImage;
// import java.io.IOException;
// import javax.imageio.ImageIO;

// public class Game {        
//         protected Font font;
//         // protected Crosshair player;
//         protected int runAwayEnemies, destroyedEnemies;

//         protected Random random;

//         // We will use this for seting mouse position.
//         protected Robot robot;

//         // Player - helicopter that is managed by player.
//         protected Crosshair player;
//         protected Graphics g;
//         // // Enemy helicopters.
//         // protected ArrayList<Aircraft> enemies = new ArrayList<Aircraft>();

//         // // Explosions
//         // protected ArrayList<Explosion> explosionsList;
//         // protected BufferedImage explosionAnimImg;

//         // // List of all the machine gun bullets.
//         // protected ArrayList<Bullet> bulletsList;

//         // // List of all the rockets.
//         // protected ArrayList<Rocket> rocketsList;
//         // // List of all the rockets smoke.
//         // protected ArrayList<RocketSmoke> rocketSmokeList;

//         // Image for the sky color.
//         // protected BufferedImage skyColorImg;

//         // // Images for white spot on the sky.
//         // protected BufferedImage cloudLayer1Img;
//         // protected BufferedImage cloudLayer2Img;
//         // // Images for mountains and ground.
//         // protected BufferedImage mountainsImg;
//         // protected BufferedImage groundImg;

//         // // Objects of moving images.
//         // protected Background cloudLayer1Moving;
//         // protected Background cloudLayer2Moving;
//         // protected Background mountainsMoving;
//         // protected Background groundMoving;
//         public Radar radar;

        // public Game() {
        //         Framework.gameState = Framework.GameState.GAME_CONTENT_LOADING;
        
        //         Thread threadForInitGame = new Thread() {
        //                 @Override
        //                 public void run(){
        //                         // Sets variables and objects for the game.
        //                         initialize();
        //                         // Load game files (images, sounds, ...)
        //                         loadContent();
                        
        //                         Framework.gameState = Framework.GameState.PLAYING;
        //                 }
        //         };
        //         threadForInitGame.start();
        // }

//         private boolean isPlayerAlive() {
//                 if(player.health <= 0)
//                     return false;
                
//                 return true;
//         }

//         private void initialize() {
//         random = new Random();
        
//         try {
//             robot = new Robot();
//         } catch (AWTException ex) {
//             Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
//         }
        
//         // player = new PlayerHelicopter(Framework.frameWidth / 4, Framework.frameHeight / 4);
        
//         // enemies = new ArrayList<Aircraft>();
        
//         // explosionsList = new ArrayList<Explosion>();
        
//         // bulletsList = new ArrayList<Bullet>();
        
//         // rocketsList = new ArrayList<Rocket>();
//         // rocketSmokeList = new ArrayList<RocketSmoke>();
        
//         // Moving images.
//         // cloudLayer1Moving = new Background();
//         // cloudLayer2Moving = new Background();
//         // mountainsMoving = new Background();
//         // groundMoving = new Background();
        
//         font = new Font("monospaced", Font.BOLD, 18);
        
//         runAwayEnemies = 0;
//         destroyedEnemies = 0;
//         }

//     /**
//      * Load game files (images).
//      */
//     private void loadContent()
//     {
//         // try 
//         // {
//         //     // Images of environment
//         //     URL skyColorImgUrl = this.getClass().getResource("/helicopterbattle/resources/images/sky_color.jpg");
//         //     skyColorImg = ImageIO.read(skyColorImgUrl);
//         //     URL cloudLayer1ImgUrl = this.getClass().getResource("/helicopterbattle/resources/images/cloud_layer_1.png");
//         //     cloudLayer1Img = ImageIO.read(cloudLayer1ImgUrl);
//         //     URL cloudLayer2ImgUrl = this.getClass().getResource("/helicopterbattle/resources/images/cloud_layer_2.png");
//         //     cloudLayer2Img = ImageIO.read(cloudLayer2ImgUrl);
//         //     URL mountainsImgUrl = this.getClass().getResource("/helicopterbattle/resources/images/mountains.png");
//         //     mountainsImg = ImageIO.read(mountainsImgUrl);
//         //     URL groundImgUrl = this.getClass().getResource("/helicopterbattle/resources/images/ground.png");
//         //     groundImg = ImageIO.read(groundImgUrl);
            
//         //     // Load images for enemy helicopter
//         //     URL helicopterBodyImgUrl = this.getClass().getResource("/helicopterbattle/resources/images/2_helicopter_body.png");
//         //     EnemyHelicopter.helicopterBodyImg = ImageIO.read(helicopterBodyImgUrl);
//         //     URL helicopterFrontPropellerAnimImgUrl = this.getClass().getResource("/helicopterbattle/resources/images/2_front_propeller_anim.png");
//         //     EnemyHelicopter.helicopterFrontPropellerAnimImg = ImageIO.read(helicopterFrontPropellerAnimImgUrl);
//         //     URL helicopterRearPropellerAnimImgUrl = this.getClass().getResource("/helicopterbattle/resources/images/2_rear_propeller_anim.png");
//         //     EnemyHelicopter.helicopterRearPropellerAnimImg = ImageIO.read(helicopterRearPropellerAnimImgUrl);
            
//         //     // Images of rocket and its smoke.
//         //     URL rocketImgUrl = this.getClass().getResource("/helicopterbattle/resources/images/rocket.png");
//         //     Rocket.rocketImg = ImageIO.read(rocketImgUrl);
//         //     URL rocketSmokeImgUrl = this.getClass().getResource("/helicopterbattle/resources/images/rocket_smoke.png");
//         //     RocketSmoke.smokeImg = ImageIO.read(rocketSmokeImgUrl);
            
//         //     // Imege of explosion animation.
//         //     URL explosionAnimImgUrl = this.getClass().getResource("/helicopterbattle/resources/images/explosion_anim.png");
//         //     explosionAnimImg = ImageIO.read(explosionAnimImgUrl);
            
//         //     // Helicopter machine gun bullet.
//         //     URL bulletImgUrl = this.getClass().getResource("/helicopterbattle/resources/images/bullet.png");
//         //     Bullet.bulletImg = ImageIO.read(bulletImgUrl);
//         // } 
//         // catch (IOException ex) 
//         // {
//         //     Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
//         // }
        
//         // Now that we have images we initialize moving images.
//         // cloudLayer1Moving.initialize(cloudLayer1Img, -6, 0);
//         // cloudLayer2Moving.initialize(cloudLayer2Img, -2, 0);
//         // mountainsMoving.initialize(mountainsImg, -1, Framework.frameHeight - groundImg.getHeight() - mountainsImg.getHeight() + 40);
//         // groundMoving.initialize(groundImg, -1.2, Framework.frameHeight - groundImg.getHeight());
//     }


//         /**
//         * Restart game - reset some variables.
//         */
//         public void restartGame()
//         {
//                 player.reset(Framework.frameWidth / 4, Framework.frameHeight / 4);

//                 Enemies.restartEnemy();

//                 Bullet.timeOfLastCreatedBullet = 0;
//                 Rocket.timeOfLastCreatedRocket = 0;

//                 // Empty all the lists.
//                 fleet.clear();
//                 bullets.clear();
//                 rockets.clear();
//                 // rocketSmoke.clear();
//                 explosions.clear();

//                 // Statistics
//                 runAwayEnemies = 0;
//                 destroyedEnemies = 0;
//         }

//         /**
//      * Update game logic.
//      * 
//      * @param gameTime The elapsed game time in nanoseconds.
//      * @param mousePosition current mouse position.
//      */
//         public void updateGame(long gameTime, Point mousePosition) {
//         /* Player */
//         // When player is destroyed and all explosions are finished showing we change game status.
//         if( !isPlayerAlive() && explosions.isEmpty() ){
//             Framework.gameState = Framework.GameState.GAMEOVER;
//             return; // If player is destroyed, we don't need to do thing below.
//         }
//         // When a player is out of rockets and machine gun bullets, and all lists 
//         // of bullets, rockets and explosions are empyt(end showing) we finish the game.
//         if(player.numberOfAmmo <= 0 && 
//            player.numberOfRockets <= 0 && 
//            bullets.isEmpty() && 
//            // rockets.isEmpty() && 
//            explosions.isEmpty())
//         {
//             Framework.gameState = Framework.GameState.GAMEOVER;
//             return;
//         }
//         // If player is alive we update him.
//         if(isPlayerAlive()){
//             isPlayerShooting(gameTime, mousePosition);
//             didPlayerFiredRocket(gameTime);
//             // player.isMoving();
//             player.draw(g);
//         }
        
//         /* Mouse */
//         limitMousePosition(mousePosition);
        
//         /* Bullets */
//         updateBullets();
        
//         /* Rockets */
//         updateRockets(gameTime); // It also checks for collisions (if any of the rockets hit any of the enemy helicopter).
//         updateRocketSmoke(gameTime);
        
//         /* Enemies */
//         createEnemyHelicopter(gameTime);
//         updateEnemies();
        
//         /* Explosions */
//         updateExplosions();
//         }
            
//         public void draw(Graphics g, double mouseX, double mouseY, long gameTime){

//                 if(isPlayerAlive()){
//                         player.draw(g);
//                 }
//                 g.setFont(font);
//                 g.setColor(Color.darkGray);
                
//                 g.drawString(formatTime(gameTime), Framework.frameWidth/2 - 45, 21);
//                 g.drawString("DESTROYED: " + destroyedEnemies, 10, 21);
//                 g.drawString("RUNAWAY: "   + runAwayEnemies,   10, 41);
//                 g.drawString("ROCKETS: "   + player.numberOfRockets, 10, 81);
//                 g.drawString("AMMO: "      + player.numberOfAmmo, 10, 101);
//         }

//             /**
//         * Draws some game statistics when game is over.
//         */
//         public void drawStatistic(Graphics g, long gameTime){
//                 g.drawString("Time: " + formatTime(gameTime),                Framework.frameWidth/2 - 50, Framework.frameHeight/3 + 80);
//                 g.drawString("Rockets left: "      + player.numberOfRockets, Framework.frameWidth/2 - 55, Framework.frameHeight/3 + 105);
//                 g.drawString("Ammo left: "         + player.numberOfAmmo,    Framework.frameWidth/2 - 55, Framework.frameHeight/3 + 125);
//                 g.drawString("Destroyed enemies: " + destroyedEnemies,       Framework.frameWidth/2 - 65, Framework.frameHeight/3 + 150);
//                 g.drawString("Runaway enemies: "   + runAwayEnemies,         Framework.frameWidth/2 - 65, Framework.frameHeight/3 + 170);
//                 g.setFont(font);
//                 g.drawString("Statistics: ",                                 Framework.frameWidth/2 - 75, Framework.frameHeight/3 + 60);
//         }
    
//     /**
//      * Format given time into 00:00 format.
//      */
//     private static String formatTime(long time){
//             // Given time in seconds.
//             int sec = (int)(time / Framework.milisecInNanosec / 1000);

//             // Given time in minutes and seconds.
//             int min = sec / 60;
//             sec = sec - (min * 60);

//             String minString, secString;

//             if(min <= 9)
//                 minString = "0" + Integer.toString(min);
//             else
//                 minString = "" + Integer.toString(min);

//             if(sec <= 9)
//                 secString = "0" + Integer.toString(sec);
//             else
//                 secString = "" + Integer.toString(sec);

//             return minString + ":" + secString;
//     }
    

//     /**
//      * Check if player is alive. If not, set game over status.
//      */
//         private boolean isPlayerAlive() {
//                 if(player.health <= 0){
//                     return false;
//                 }
//                 return true;
//         }
//     /**
//      * Checks if the player is shooting with the machine gun and creates bullets if he shooting.
//      * 
//      * @param gameTime Game time.
//      */
//     private void isPlayerShooting(long gameTime, double mouseX, double mouseY)
//     {
//         if(player.isShooting(gameTime))
//         {
//             Bullet.timeOfLastCreatedBullet = gameTime;
//             player.numberOfAmmo--;
            
//             Bullet b = new Bullet(radar, player.x, player.y, mouseX, mouseY);
//             radar.bullets.add(b);
//         }
//     }
    
//     /**
//      * Checks if the player is fired the rocket and creates it if he did.
//      * It also checks if player can fire the rocket.
//      */
//         private void didPlayerFiredRocket(long gameTime)
//         {
//                 if(player.isFiredRocket(gameTime)) {
//                     // Rocket.timeOfLastCreatedRocket = gameTime;
//                     player.numberOfRockets--;
                    
//                     // // Rocket r = new Rocket();
//                     // r.Initialize(player.rocketHolderXcoordinate, player.rocketHolderYcoordinate);
//                     // rockets.add(r);
//                 }
//         }
    

// }