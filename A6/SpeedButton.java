import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import java.util.Random;

public class SpeedButton extends JButton implements ActionListener {
	private Framework framework;
	public SpeedButton (Framework framework) {
		this.framework = framework;
		setText("Change Speed");
		addActionListener(this);
	}

	public void actionPerformed(ActionEvent ae) {
		framework.radar.changeSpeed();
	}
}