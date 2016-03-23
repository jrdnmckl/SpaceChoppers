import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class AddHeliButton extends JButton implements ActionListener {
	private Framework framework;
	public AddHeliButton (Framework framework) {
		this.framework = framework;
		setText("Add Helicopter");
		addActionListener(this);
	}

	public void actionPerformed (ActionEvent ae) {
		framework.radar.addHeli();
	}
}