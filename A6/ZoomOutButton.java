import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class ZoomOutButton extends JButton implements ActionListener {
	private Framework framework  ;
	public ZoomOutButton (Framework framework) {
		this.framework = framework;
		setText("Zoom Out");
		addActionListener(this);
	}

	public void actionPerformed (ActionEvent ae) {
		framework.radar.zoomOut();
		System.out.println("zoomLevel " + framework.radar.scale);
		System.out.println("Zoom Out 10X");
	}
}