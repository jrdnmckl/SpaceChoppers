import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class ZoomInButton extends JButton implements ActionListener {
	private Framework framework;
	public ZoomInButton (Framework framework) {
		this.framework = framework;
		setText("Zoom In");
		addActionListener(this);
	}

	public void actionPerformed (ActionEvent ae) {
		framework.radar.zoomIn();
		System.out.println("zoomLevel " + framework.radar.scale);
		System.out.println("Zoom 10X");
	}
}