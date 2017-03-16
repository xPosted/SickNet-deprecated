package com.jubaka.sors.desktop.tcpAnalyse;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

public class SessionDataPanel extends JTabbedPane {
	
	public JTextArea txtrSrcData = new JTextArea();
	public JTextArea txtrDstData = new JTextArea();
	public JTextArea txtrBothData = new JTextArea();
	
	
	public SessionDataPanel() {
		super();
		initialize();
	}

	public SessionDataPanel(int tabPlacement) {
		super(tabPlacement);
		initialize();
	}

	private void initialize() {
		setMinimumSize(new Dimension(5, 0));
		setPreferredSize(new Dimension(600, 100));
		JPanel sesDataViewPan = new JPanel();
		sesDataViewPan.setMinimumSize(new Dimension(10, 0));
		add("Session Data", sesDataViewPan);

		sesDataViewPan.setLayout(new BoxLayout(sesDataViewPan, BoxLayout.Y_AXIS));

		JPanel panel_37 = new JPanel();
		panel_37.setMinimumSize(new Dimension(10, 25));
		sesDataViewPan.add(panel_37);
		panel_37.setLayout(new BoxLayout(panel_37, BoxLayout.X_AXIS));

		Component horizontalGlue_9 = Box.createHorizontalGlue();
		panel_37.add(horizontalGlue_9);

		// JLabel lblNewLabel_6 = new JLabel("New label");
		// panel_37.add(lblNewLabel_6);

		Component horizontalGlue_10 = Box.createHorizontalGlue();
		panel_37.add(horizontalGlue_10);

		JPanel panel_26 = new JPanel();
		panel_26.setMinimumSize(new Dimension(10, 0));
		sesDataViewPan.add(panel_26);
		panel_26.setLayout(new BorderLayout(0, 0));

		JTabbedPane tabbedPane_2 = new JTabbedPane(JTabbedPane.BOTTOM);
		tabbedPane_2.setMinimumSize(new Dimension(5, 0));
		panel_26.add(tabbedPane_2, BorderLayout.CENTER);

		JScrollPane scrollPane = new JScrollPane();
		txtrSrcData.setMinimumSize(new Dimension(0, 0));
		scrollPane.setViewportView(txtrSrcData);
		tabbedPane_2.addTab("Source transmitted ", null, scrollPane, null);

		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setViewportView(txtrDstData);
		tabbedPane_2.addTab("Destionation transmitted", null, scrollPane_3, null);

		JScrollPane scrollPane_4 = new JScrollPane();
		scrollPane_4.setViewportView(txtrBothData);
		tabbedPane_2.addTab("ALL", null, scrollPane_4, null);


		JPanel panel_27 = new JPanel();
		sesDataViewPan.add(panel_27);
		panel_27.setLayout(new BoxLayout(panel_27, BoxLayout.Y_AXIS));

		JSeparator separator_1 = new JSeparator();
		separator_1.setPreferredSize(new Dimension(0, 5));
		separator_1.setMaximumSize(new Dimension(32767, 5));
		panel_27.add(separator_1);

		JPanel panel_28 = new JPanel();
		panel_28.setPreferredSize(new Dimension(10, 25));
		panel_28.setMaximumSize(new Dimension(32767, 30));
		panel_27.add(panel_28);
		panel_28.setLayout(new BoxLayout(panel_28, BoxLayout.X_AXIS));

		Component horizontalGlue_8 = Box.createHorizontalGlue();
		panel_28.add(horizontalGlue_8);

		
		
	}
}
