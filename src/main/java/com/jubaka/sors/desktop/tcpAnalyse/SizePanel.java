package com.jubaka.sors.desktop.tcpAnalyse;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.SwingConstants;

public class SizePanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public SizePanel() {
		this.setPreferredSize(new Dimension(486, 40));
		this.setMaximumSize(new Dimension(32767, 40));
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		JPanel panel_9 = new JPanel();
		this.add(panel_9);
		panel_9.setLayout(new BoxLayout(panel_9, BoxLayout.X_AXIS));
		
		Component horizontalGlue_6 = Box.createHorizontalGlue();
		panel_9.add(horizontalGlue_6);
		
		JPanel panel = new JPanel();
		panel_9.add(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		Component verticalGlue = Box.createVerticalGlue();
		panel.add(verticalGlue);
		
		JLabel lblNewLabel_3 = new JLabel("New label");
		panel.add(lblNewLabel_3);
		lblNewLabel_3.setFont(new Font("Dialog", Font.BOLD, 14));
		
		Component verticalGlue_1 = Box.createVerticalGlue();
		panel.add(verticalGlue_1);
		
		Component horizontalGlue_7 = Box.createHorizontalGlue();
		panel_9.add(horizontalGlue_7);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setMaximumSize(new Dimension(5, 32767));
		separator_1.setOrientation(SwingConstants.VERTICAL);
		this.add(separator_1);
		
		JPanel panel_10 = new JPanel();
		this.add(panel_10);
		panel_10.setLayout(new BoxLayout(panel_10, BoxLayout.X_AXIS));
		
		Component horizontalStrut_3 = Box.createHorizontalStrut(20);
		panel_10.add(horizontalStrut_3);
		
		JSpinner spinner = new JSpinner();
		spinner.setFont(new Font("Dialog", Font.BOLD, 14));
		spinner.setMaximumSize(new Dimension(32767, 30));
		spinner.setSize(new Dimension(0, 30));
		spinner.setPreferredSize(new Dimension(28, 30));
		panel_10.add(spinner);
		
		Component horizontalStrut_4 = Box.createHorizontalStrut(5);
		panel_10.add(horizontalStrut_4);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setPreferredSize(new Dimension(60, 24));
		comboBox.setMaximumSize(new Dimension(60, 30));
		panel_10.add(comboBox);
		
		Component horizontalGlue_8 = Box.createHorizontalGlue();
		panel_10.add(horizontalGlue_8);
	}
}
