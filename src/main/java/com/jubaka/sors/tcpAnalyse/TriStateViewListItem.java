package com.jubaka.sors.tcpAnalyse;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.Component;

import javax.swing.Box;
import javax.swing.JCheckBox;
import javax.swing.JToggleButton;

import java.awt.Insets;

import javax.swing.border.CompoundBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;

import java.awt.Dimension;

public class TriStateViewListItem extends JPanel {
	public JToggleButton btnPanel;
	public JToggleButton btnCompaq;
	public JToggleButton btnTable;
	public JLabel lblLabel;
	public ButtonGroup bg = new ButtonGroup();
	/**
	 * Create the panel.
	 */
	public TriStateViewListItem() {
		setPreferredSize(new Dimension(388, 40));
		setMaximumSize(new Dimension(32767, 40));
		setBorder(new CompoundBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null), new EmptyBorder(3, 0, 3, 0)));
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		Component horizontalGlue = Box.createHorizontalGlue();
		add(horizontalGlue);
		
		lblLabel = new JLabel("Label");
		lblLabel.setFont(new Font("Dialog", Font.BOLD, 12));
		add(lblLabel);
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		add(horizontalStrut);
		
		btnPanel = new JToggleButton("panel");
		btnPanel.setSelected(true);
		btnPanel.setFont(new Font("DejaVu Serif", Font.PLAIN, 12));
		btnPanel.setMargin(new Insets(2, 3, 2, 3));
		add(btnPanel);
		
		btnCompaq = new JToggleButton("compaq panel");
		btnCompaq.setFont(new Font("DejaVu Serif", Font.PLAIN, 12));
		btnCompaq.setMargin(new Insets(2, 3, 2, 3));
		add(btnCompaq);
		
		btnTable = new JToggleButton("table");
		btnTable.setFont(new Font("DejaVu Serif", Font.PLAIN, 12));
		btnTable.setMargin(new Insets(2, 3, 2, 3));
		add(btnTable);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(15);
		add(horizontalStrut_1);
		bg.add(btnPanel);
		bg.add(btnCompaq);
		bg.add(btnTable);

	}
	

}
