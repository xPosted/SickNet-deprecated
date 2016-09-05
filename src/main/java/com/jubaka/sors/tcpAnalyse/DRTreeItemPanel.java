package com.jubaka.sors.tcpAnalyse;

import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DRTreeItemPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public DRTreeItemPanel() {
		setLayout(new BorderLayout(0, 0));
		
		JLabel lblLabel = new JLabel("Label");
		add(lblLabel, BorderLayout.CENTER);
		
		JCheckBox checkBox = new JCheckBox("");
		checkBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("perfomed");
			}
		});
		add(checkBox, BorderLayout.WEST);

	}

}
