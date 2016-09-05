package com.jubaka.sors.tcpAnalyse.httpView;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.SystemColor;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.SoftBevelBorder;



public class PanelBodyItem extends JPanel {

	/**
	 * Create the panel.
	 */
	public PanelBodyItem(String header,String value) {
		setPreferredSize(new Dimension(430, 41));
		
		setMaximumSize(new Dimension(32767, 41));
		setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new EmptyBorder(0, 0, 0, 5));
		panel_3.setPreferredSize(new Dimension(120, 40));
		panel_3.setMaximumSize(new Dimension(120, 40));
		add(panel_3);
		panel_3.setLayout(new BorderLayout(0, 0));
		
		JLabel lblUserAgentHeader = new JLabel(header);
		lblUserAgentHeader.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_3.add(lblUserAgentHeader, BorderLayout.CENTER);
		
		JSeparator separator = new JSeparator();
		separator.setMaximumSize(new Dimension(2, 32767));
		separator.setOrientation(SwingConstants.VERTICAL);
		add(separator);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new EmptyBorder(0, 5, 0, 0));
		panel_4.setBackground(SystemColor.text);
		panel_4.setMaximumSize(new Dimension(32767, 40));
		add(panel_4);
		panel_4.setLayout(new BorderLayout(0, 0));
		
		JLabel lblUserAgentValue = new JLabel(value);
		lblUserAgentValue.setBackground(SystemColor.text);
		panel_4.add(lblUserAgentValue, BorderLayout.CENTER);
	}

}
