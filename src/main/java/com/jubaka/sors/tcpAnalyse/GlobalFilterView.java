package com.jubaka.sors.tcpAnalyse;

import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Dimension;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.border.EmptyBorder;
import java.awt.Insets;
import javax.swing.JLabel;

public class GlobalFilterView extends JPanel {
	private JTextField textField;

	/**
	 * Create the panel.
	 */
	public GlobalFilterView() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JPanel panel = new JPanel();
		panel.setBorder(new CompoundBorder(new CompoundBorder(new EtchedBorder(EtchedBorder.RAISED, null, null), new LineBorder(new Color(0, 0, 0), 1, true)), new EmptyBorder(3, 10, 3, 10)));
		panel.setMaximumSize(new Dimension(32767, 40));
		panel.setPreferredSize(new Dimension(10, 40));
		add(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		JLabel lblNewLabel = new JLabel("New label");
		panel.add(lblNewLabel);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setPreferredSize(new Dimension(75, 24));
		panel.add(comboBox);
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		panel.add(horizontalStrut);
		
		JLabel lblNewLabel_1 = new JLabel("to");
		panel.add(lblNewLabel_1);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setPreferredSize(new Dimension(75, 24));
		panel.add(comboBox_1);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		panel.add(horizontalStrut_1);
		
		textField = new JTextField();
		textField.setMaximumSize(new Dimension(10, 2147483647));
		textField.setPreferredSize(new Dimension(50, 19));
		panel.add(textField);
		textField.setColumns(10);
		
		Component horizontalStrut_2 = Box.createHorizontalStrut(20);
		panel.add(horizontalStrut_2);
		
		JButton btnNewButton = new JButton("add filter");
		btnNewButton.setMargin(new Insets(2, 10, 2, 10));
		panel.add(btnNewButton);
		
		JPanel panel_1 = new JPanel();
		add(panel_1);

	}
}
