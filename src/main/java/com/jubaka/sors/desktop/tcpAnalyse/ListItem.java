package com.jubaka.sors.desktop.tcpAnalyse;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.JSeparator;
import java.awt.Dimension;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.Color;
import javax.swing.border.LineBorder;

public class ListItem extends JPanel {

	/**
	 * Create the panel.
	 */
	public ListItem() {
		setMaximumSize(new Dimension(32767, 50));
		setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JPanel panel = new JPanel();
		add(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		Component verticalGlue = Box.createVerticalGlue();
		panel.add(verticalGlue);
		
		Component horizontalStrut = Box.createHorizontalStrut(10);
		panel.add(horizontalStrut);
		
		JLabel lblNewLabel = new JLabel("192.168.145.1");
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 12));
		panel.add(lblNewLabel);
		
		Component horizontalGlue = Box.createHorizontalGlue();
		panel.add(horizontalGlue);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(ListItem.class.getResource("/tcpAnalyse/arrow.jpg")));
		panel.add(lblNewLabel_2);
		
		Component horizontalGlue_1 = Box.createHorizontalGlue();
		panel.add(horizontalGlue_1);
		
		JLabel lblNewLabel_1 = new JLabel("192.168.145.2");
		lblNewLabel_1.setFont(new Font("Dialog", Font.BOLD, 12));
		panel.add(lblNewLabel_1);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(10);
		panel.add(horizontalStrut_1);
		
		JSeparator separator = new JSeparator();
		separator.setPreferredSize(new Dimension(0, 1));
		add(separator);
		
		JPanel panel_1 = new JPanel();
		add(panel_1);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));
		
		Component horizontalStrut_2 = Box.createHorizontalStrut(20);
		panel_1.add(horizontalStrut_2);
		
		Component verticalGlue_1 = Box.createVerticalGlue();
		panel_1.add(verticalGlue_1);
		
		JLabel lblUp = new JLabel("08:00 up");
		lblUp.setFont(new Font("Dialog", Font.PLAIN, 12));
		panel_1.add(lblUp);
		
		Component horizontalGlue_2 = Box.createHorizontalGlue();
		panel_1.add(horizontalGlue_2);
		
		JLabel lblmbmb = new JLabel("12mb/699mb");
		lblmbmb.setFont(new Font("Dialog", Font.PLAIN, 12));
		panel_1.add(lblmbmb);
		
		Component horizontalGlue_3 = Box.createHorizontalGlue();
		panel_1.add(horizontalGlue_3);
		
		JLabel lblPort = new JLabel("port: 80");
		lblPort.setFont(new Font("Dialog", Font.PLAIN, 12));
		panel_1.add(lblPort);
		
		Component horizontalStrut_3 = Box.createHorizontalStrut(20);
		panel_1.add(horizontalStrut_3);

	}

}
