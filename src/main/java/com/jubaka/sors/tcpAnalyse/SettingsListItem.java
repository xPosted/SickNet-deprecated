package com.jubaka.sors.tcpAnalyse;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Dimension;
import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.border.EmptyBorder;

public class SettingsListItem extends JPanel {
	private  JPanel panel_1 = new JPanel();
	private  JPanel panel = new JPanel();
	private  JLabel lblMaintext;
	JLabel lblIcon;
	private final JPanel panel_2 = new JPanel();

	/**
	 * Create the panel.
	 */
	public SettingsListItem(String text) {
		panel_2.setMinimumSize(new Dimension(30, 10));
		panel_2.setPreferredSize(new Dimension(30, 10));
		panel_2.setLayout(new BorderLayout(0, 0));
		panel_2.setBackground(new Color(0,255,0,0));
		
		setBorder(new EmptyBorder(2, 2, 2, 2));
		setBackground(new Color(192, 192, 192));
		setMinimumSize(new Dimension(10, 40));
		setPreferredSize(new Dimension(120, 40));
		setLayout(new BorderLayout(0, 0));
		panel_1.setBackground(new Color(0,255,0,0));
		add(panel_2, BorderLayout.EAST);
		panel_2.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		lblIcon = new JLabel("");
		lblIcon.setIcon(new ImageIcon(this.getClass().getResource("/black_right_pointer_30.png")));
		panel_1.add(lblIcon, BorderLayout.CENTER);
		panel.setBorder(new EmptyBorder(2, 2, 2, 2));
		panel.setBackground(new Color(0,255,0,0));
		add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		lblMaintext=new JLabel(text);
		lblMaintext.setHorizontalTextPosition(SwingConstants.CENTER);
		lblMaintext.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblMaintext, BorderLayout.CENTER);
		
		

	}
	public String toString() {
		return lblMaintext.getText();
	}
	public void setActive() {
		lblIcon.setVisible(true);
	}
	public void setInactive() {
		lblIcon.setVisible(false);
	}
}
