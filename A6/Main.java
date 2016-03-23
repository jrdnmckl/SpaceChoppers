import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.KeyListener;
import java.awt.geom.*;

public class Main extends JFrame {

        public static void main(String args[]) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new Main();
                }
            });
        }
        public Main() {

                setTitle("Space Choppers"); 
                setLocationRelativeTo(null);
                setSize(1000,800);

                Container content = getContentPane();
                content.setLayout(new BorderLayout());
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                Framework framework = new Framework(this);
                SpeedButton speedButton = new SpeedButton(framework);
                ClearButton clearButton = new ClearButton(framework);
                AddPlaneButton addPlane = new AddPlaneButton(framework);
                AddHeliButton addHeli = new AddHeliButton(framework);
                ZoomInButton zoomIn = new ZoomInButton(framework);
                ZoomOutButton zoomOut = new ZoomOutButton(framework);
                JPanel controls = new JPanel();
                controls.add(speedButton);
                // controls.add(clearButton);
                // controls.add(addPlane);
                // controls.add(addHeli);
                // controls.add(zoomIn);
                // controls.add(zoomOut);
                controls.setLayout(new FlowLayout());
                controls.setBackground(Color.BLACK);
                content.add(framework, BorderLayout.CENTER);
                content.add(controls, BorderLayout.SOUTH);
                setVisible(true); 
        }
}