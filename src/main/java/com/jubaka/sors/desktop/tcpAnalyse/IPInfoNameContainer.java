package com.jubaka.sors.desktop.tcpAnalyse;

import javax.swing.JPanel;
import javax.swing.BoxLayout;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JLabel;
import java.awt.Dimension;
import javax.swing.border.EmptyBorder;

public class IPInfoNameContainer extends JPanel {
	//private String name = "";
	/**
	 * Create the panel.
	 */
	public IPInfoNameContainer(String name) {
		setBorder(new EmptyBorder(0, 0, 0, 5));
		setPreferredSize(new Dimension(160, 25));
		setMinimumSize(new Dimension(150, 25));
		setMaximumSize(new Dimension(200, 35));
		
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		Component horizontalGlue = Box.createHorizontalGlue();
		add(horizontalGlue);
		
		JLabel lblName = new JLabel(name);
		add(lblName);
	

	}
}
