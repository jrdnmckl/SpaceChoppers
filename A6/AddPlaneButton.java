import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class AddPlaneButton extends JButton implements ActionListener {
	private Framework framework;
	public AddPlaneButton (Framework framework) {
		this.framework = framework;
		setText("Add Plane");
		addActionListener(this);
	}

	public void actionPerformed (ActionEvent ae) {
		framework.radar.addPlane();
	}
}