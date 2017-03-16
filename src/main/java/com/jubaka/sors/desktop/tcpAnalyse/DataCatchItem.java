package com.jubaka.sors.desktop.tcpAnalyse;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.SoftBevelBorder;

public class DataCatchItem extends JPanel {

	/**
	 * Create the panel.
	 */
	public DataCatchItem(boolean input) {
		JPanel panel_6 = new JPanel();
		panel_6.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_6.setLayout(new BoxLayout(panel_6, BoxLayout.Y_AXIS));
		
		JPanel panel_15 = new JPanel();
		panel_15.setBorder(new SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, null, null, null, null));
		panel_6.add(panel_15);
		panel_15.setLayout(new BoxLayout(panel_15, BoxLayout.X_AXIS));
		
		Component horizontalGlue_1 = Box.createHorizontalGlue();
		panel_15.add(horizontalGlue_1);
		JLabel lblInputConnections=null;
		JLabel lblNewLabel = new JLabel("New label");
		
		if (input) {
			lblNewLabel.setText("Input Connections");
		} else {
			lblNewLabel.setText("Output Connections");
		}
		panel_15.add(lblInputConnections);
		
		
		panel_15.add(lblNewLabel);
		
		Component horizontalGlue_2 = Box.createHorizontalGlue();
		panel_15.add(horizontalGlue_2);
		
		JPanel panel_10 = new JPanel();
		panel_6.add(panel_10);
		panel_10.setLayout(new BoxLayout(panel_10, BoxLayout.Y_AXIS));
		
		JPanel panel_12 = new JPanel();
		panel_12.setMaximumSize(new Dimension(32767, 40));
		panel_10.add(panel_12);
		panel_12.setLayout(new BoxLayout(panel_12, BoxLayout.X_AXIS));
		
		Component horizontalStrut_3 = Box.createHorizontalStrut(20);
		panel_12.add(horizontalStrut_3);
		
		
		JCheckBox chckbxNewCheckBox = new JCheckBox("New check box");
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(chckbxNewCheckBox);
		panel_12.add(chckbxNewCheckBox);
		
		JPanel panel_13 = new JPanel();
		panel_10.add(panel_13);
		panel_13.setLayout(new BoxLayout(panel_13, BoxLayout.Y_AXIS));
		
		JPanel panel = new JPanel();
		panel.setMaximumSize(new Dimension(32767, 25));
		panel.setPreferredSize(new Dimension(10, 25));
		panel_13.add(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		panel.add(horizontalStrut);
		
		JCheckBox chckbxNewCheckBox_1 = new JCheckBox("New check box");
		buttonGroup.add(chckbxNewCheckBox_1);
		panel.add(chckbxNewCheckBox_1);
		
		Component horizontalGlue_3 = Box.createHorizontalGlue();
		panel.add(horizontalGlue_3);
		
		JPanel panel_14 = new JPanel();
		panel_14.setPreferredSize(new Dimension(10, 25));
		panel_14.setMaximumSize(new Dimension(32767, 35));
		panel_13.add(panel_14);
		panel_14.setLayout(new BoxLayout(panel_14, BoxLayout.X_AXIS));
		
		Component horizontalStrut_2 = Box.createHorizontalStrut(20);
		panel_14.add(horizontalStrut_2);
		JTextField textField;
		textField = new JTextField();
		panel_14.add(textField);
		textField.setColumns(10);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_2.setMaximumSize(new Dimension(32767, 35));
		
		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.X_AXIS));
		
		Component horizontalGlue = Box.createHorizontalGlue();
		panel_2.add(horizontalGlue);

	}

}
