package com.jubaka.sors.view.statistic;

import com.jubaka.sors.sessions.IPaddr;
import com.jubaka.sors.tcpAnalyse.Controller;

import javax.swing.JPanel;

import javax.swing.BoxLayout;
import javax.swing.border.EmptyBorder;

import java.awt.Component;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;

import java.awt.Color;

import javax.swing.border.LineBorder;

import java.awt.Font;
import java.awt.Dimension;

import javax.swing.JSeparator;
import javax.swing.SwingConstants;

public class StatIPViewHeader extends JPanel {
	JLabel lblNetName;
	JLabel lblData;
	JLabel lblSes;
	JLabel lblDns;
	/**
	 * Create the panel.
	 */
	public StatIPViewHeader(IPaddr ip) {
		setMinimumSize(new Dimension(10, 90));
		setPreferredSize(new Dimension(600, 90));
		setMaximumSize(new Dimension(32767, 90));
		baseInit();
		lblNetName.setText(ip.getAddr().getHostAddress());
		String dataIn = Controller.processSize(ip.getDataDown(), 2);
		String dataOut = Controller.processSize(ip.getDataUp(), 2);
		lblData.setText(dataIn+"/"+dataOut);
		lblSes.setText(ip.getInSessionCount()+"/"+ip.getOutSessionCount());
		lblDns.setText(ip.getDnsName());

	}
	private void baseInit() {
		setBorder(new CompoundBorder(new EtchedBorder(EtchedBorder.RAISED, new Color(64, 64, 64), null), new LineBorder(new Color(0, 0, 0), 1, true)));
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		JPanel panel_4 = new JPanel();
		add(panel_4);
		panel_4.setLayout(new BoxLayout(panel_4, BoxLayout.Y_AXIS));
		
		JPanel panel = new JPanel();
		panel_4.add(panel);
		panel.setMinimumSize(new Dimension(200, 10));
		panel.setPreferredSize(new Dimension(270, 10));
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		Component horizontalGlue = Box.createHorizontalGlue();
		panel.add(horizontalGlue);
		
		lblNetName = new JLabel("178.159.225.0/24");
		lblNetName.setFont(new Font("Dialog", Font.BOLD, 22));
		panel.add(lblNetName);
		
		Component horizontalGlue_1 = Box.createHorizontalGlue();
		panel.add(horizontalGlue_1);
		
		JPanel panel_6 = new JPanel();
		panel_6.setBorder(new EmptyBorder(0, 5, 0, 5));
		panel_4.add(panel_6);
		panel_6.setLayout(new BoxLayout(panel_6, BoxLayout.X_AXIS));
		
		Component horizontalGlue_6 = Box.createHorizontalGlue();
		panel_6.add(horizontalGlue_6);
		
		lblDns = new JLabel("New label");
		lblDns.setFont(new Font("Dialog", Font.BOLD, 18));
		panel_6.add(lblDns);
		
		Component horizontalGlue_7 = Box.createHorizontalGlue();
		panel_6.add(horizontalGlue_7);
		
		JSeparator separator_3 = new JSeparator();
		separator_3.setMaximumSize(new Dimension(32767, 2));
		panel_4.add(separator_3);
		
		JPanel panel_5 = new JPanel();
		panel_5.setBorder(new EmptyBorder(5, 10, 5, 10));
		panel_4.add(panel_5);
		panel_5.setLayout(new BoxLayout(panel_5, BoxLayout.X_AXIS));
		
		Component horizontalGlue_4 = Box.createHorizontalGlue();
		panel_5.add(horizontalGlue_4);
		
		JLabel lblNewLabel_1 = new JLabel("IP statistic");
		lblNewLabel_1.setFont(new Font("DejaVu Serif", Font.BOLD | Font.ITALIC, 12));
		panel_5.add(lblNewLabel_1);
		
		Component horizontalGlue_5 = Box.createHorizontalGlue();
		panel_5.add(horizontalGlue_5);
		
		JPanel panel_1 = new JPanel();
		panel_1.setMaximumSize(new Dimension(320, 32767));
		add(panel_1);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.Y_AXIS));
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new EmptyBorder(3, 7, 3, 7));
		panel_1.add(panel_2);
		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.X_AXIS));
		
		Component horizontalStrut_2 = Box.createHorizontalStrut(10);
		panel_2.add(horizontalStrut_2);
		
		JLabel lblNewLabel = new JLabel("Data IN/OUT");
		panel_2.add(lblNewLabel);
		
		Component horizontalStrut = Box.createHorizontalStrut(25);
		panel_2.add(horizontalStrut);
		
		JSeparator separator = new JSeparator();
		separator.setMaximumSize(new Dimension(3, 32767));
		separator.setOrientation(SwingConstants.VERTICAL);
		panel_2.add(separator);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		panel_2.add(horizontalStrut_1);
		
		lblData = new JLabel("New label");
		panel_2.add(lblData);
		
		Component horizontalGlue_2 = Box.createHorizontalGlue();
		panel_2.add(horizontalGlue_2);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setMaximumSize(new Dimension(32767, 2));
		panel_1.add(separator_2);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new EmptyBorder(3, 7, 3, 7));
		panel_1.add(panel_3);
		panel_3.setLayout(new BoxLayout(panel_3, BoxLayout.X_AXIS));
		
		Component horizontalStrut_3 = Box.createHorizontalStrut(10);
		panel_3.add(horizontalStrut_3);
		
		JLabel lblNewLabel_2 = new JLabel("Ses IN/OUT");
		panel_3.add(lblNewLabel_2);
		
		Component horizontalStrut_4 = Box.createHorizontalStrut(33);
		panel_3.add(horizontalStrut_4);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setMaximumSize(new Dimension(3, 32767));
		separator_1.setOrientation(SwingConstants.VERTICAL);
		panel_3.add(separator_1);
		
		Component horizontalStrut_5 = Box.createHorizontalStrut(20);
		panel_3.add(horizontalStrut_5);
		
		lblSes = new JLabel("New label");
		panel_3.add(lblSes);
		
		Component horizontalGlue_3 = Box.createHorizontalGlue();
		panel_3.add(horizontalGlue_3);
	}
}
