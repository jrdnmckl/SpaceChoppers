import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ClearButton extends JButton implements ActionListener {
	private Framework framework;
	public ClearButton (Framework framework) {
		this.framework = framework;
		setText("Clear Radar");
		addActionListener(this);
	}

	public void actionPerformed (ActionEvent ae) {
		framework.radar.clearFleet();
		System.out.println("Radar cleared");
	}
}