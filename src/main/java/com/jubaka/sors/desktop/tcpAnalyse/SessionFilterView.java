package com.jubaka.sors.desktop.tcpAnalyse;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.BoxLayout;

import java.awt.Component;

import javax.swing.Box;
import javax.swing.JLabel;

import java.awt.Dimension;

import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;

import java.awt.Color;

import javax.swing.border.EtchedBorder;

import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JSeparator;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SessionFilterView extends JFrame {

	private JPanel contentPane;
	private JTextField srcPortFld;
	private JTextField dstPortFld;
	private JTextField srcIpFld;
	private JTextField dstIpFld;
	private JTextField srcDaraFld;
	private JTextField dstDataFld;
	private SessionFilter sFilter;
	private SessionFilterView inst;
	private JButton fBtn;
	

	/**
	 * Create the frame.
	 */
	public SessionFilterView (SessionFilter filter, JButton filterBut) {
		inst = this;
		sFilter=filter;
		fBtn=filterBut;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 371, 331);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new CompoundBorder(new LineBorder(new Color(0, 0, 0)), new EtchedBorder(EtchedBorder.LOWERED, null, null)));
		panel_1.setMaximumSize(new Dimension(32767, 40));
		panel_1.setPreferredSize(new Dimension(10, 40));
		panel.add(panel_1);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		panel_1.add(horizontalStrut);
		
		JLabel lblNewLabel = new JLabel("TCP");
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 22));
		panel_1.add(lblNewLabel);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		panel_1.add(horizontalStrut_1);
		
		JLabel lblAnalyze = new JLabel("analyze");
		lblAnalyze.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 18));
		panel_1.add(lblAnalyze);
		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2);
		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.Y_AXIS));
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_2.add(panel_3);
		panel_3.setLayout(new BoxLayout(panel_3, BoxLayout.Y_AXIS));
		
		JPanel panel_14 = new JPanel();
		panel_14.setMaximumSize(new Dimension(32767, 30));
		panel_3.add(panel_14);
		
		Component horizontalGlue_4 = Box.createHorizontalGlue();
		panel_14.add(horizontalGlue_4);
		
		JLabel lblNewLabel_9 = new JLabel("Port filter");
		panel_14.add(lblNewLabel_9);
		
		Component horizontalGlue_5 = Box.createHorizontalGlue();
		panel_14.add(horizontalGlue_5);
		
		JPanel panel_6 = new JPanel();
		panel_3.add(panel_6);
		panel_6.setLayout(new BoxLayout(panel_6, BoxLayout.X_AXIS));
		
		Component horizontalStrut_2 = Box.createHorizontalStrut(20);
		panel_6.add(horizontalStrut_2);
		
		JLabel lblNewLabel_1 = new JLabel("SRC port");
		panel_6.add(lblNewLabel_1);
		
		Component horizontalStrut_5 = Box.createHorizontalStrut(20);
		panel_6.add(horizontalStrut_5);
		
		srcPortFld = new JTextField(sFilter.getSrcPortFilter());
		srcPortFld.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				onMouseClc(srcPortFld);
			}
		});
		srcPortFld.setBackground(Color.WHITE);
		srcPortFld.setMaximumSize(new Dimension(2147483647, 25));
		panel_6.add(srcPortFld);
		srcPortFld.setColumns(10);
		
		Component horizontalStrut_16 = Box.createHorizontalStrut(20);
		panel_6.add(horizontalStrut_16);
		
		JPanel panel_7 = new JPanel();
		panel_3.add(panel_7);
		panel_7.setLayout(new BoxLayout(panel_7, BoxLayout.X_AXIS));
		
		Component horizontalStrut_3 = Box.createHorizontalStrut(20);
		panel_7.add(horizontalStrut_3);
		
		JLabel lblNewLabel_2 = new JLabel("DST port");
		panel_7.add(lblNewLabel_2);
		
		Component horizontalStrut_4 = Box.createHorizontalStrut(20);
		panel_7.add(horizontalStrut_4);
		
		dstPortFld = new JTextField(sFilter.getDstPortFilter());
		dstPortFld.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				onMouseClc(dstPortFld);
			}
		});
		dstPortFld.setMaximumSize(new Dimension(2147483647, 25));
		panel_7.add(dstPortFld);
		dstPortFld.setColumns(10);
		
		Component horizontalStrut_17 = Box.createHorizontalStrut(20);
		panel_7.add(horizontalStrut_17);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_2.add(panel_4);
		panel_4.setLayout(new BoxLayout(panel_4, BoxLayout.Y_AXIS));
		
		JPanel panel_13 = new JPanel();
		panel_13.setMaximumSize(new Dimension(32767, 30));
		panel_4.add(panel_13);
		
		Component horizontalGlue_2 = Box.createHorizontalGlue();
		panel_13.add(horizontalGlue_2);
		
		JLabel lblNewLabel_8 = new JLabel("IP filter");
		panel_13.add(lblNewLabel_8);
		
		Component horizontalGlue_3 = Box.createHorizontalGlue();
		panel_13.add(horizontalGlue_3);
		
		JPanel panel_8 = new JPanel();
		panel_4.add(panel_8);
		panel_8.setLayout(new BoxLayout(panel_8, BoxLayout.X_AXIS));
		
		Component horizontalStrut_6 = Box.createHorizontalStrut(20);
		panel_8.add(horizontalStrut_6);
		
		JLabel lblNewLabel_3 = new JLabel("SRC ip");
		panel_8.add(lblNewLabel_3);
		
		Component horizontalStrut_9 = Box.createHorizontalStrut(20);
		panel_8.add(horizontalStrut_9);
		
		srcIpFld = new JTextField(sFilter.getSrcIpFilter());
		srcIpFld.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				onMouseClc(srcIpFld);
			}
		});
		srcIpFld.setMaximumSize(new Dimension(2147483647, 25));
		panel_8.add(srcIpFld);
		srcIpFld.setColumns(10);
		
		Component horizontalStrut_18 = Box.createHorizontalStrut(20);
		panel_8.add(horizontalStrut_18);
		
		JPanel panel_9 = new JPanel();
		panel_4.add(panel_9);
		panel_9.setLayout(new BoxLayout(panel_9, BoxLayout.X_AXIS));
		
		Component horizontalStrut_7 = Box.createHorizontalStrut(20);
		panel_9.add(horizontalStrut_7);
		
		JLabel lblNewLabel_4 = new JLabel("DST ip");
		panel_9.add(lblNewLabel_4);
		
		Component horizontalStrut_8 = Box.createHorizontalStrut(20);
		panel_9.add(horizontalStrut_8);
		
		dstIpFld = new JTextField(sFilter.getDstIpFilter());
		dstIpFld.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				onMouseClc(dstIpFld);
			}
		});
		dstIpFld.setMaximumSize(new Dimension(2147483647, 25));
		panel_9.add(dstIpFld);
		dstIpFld.setColumns(10);
		
		Component horizontalStrut_19 = Box.createHorizontalStrut(20);
		panel_9.add(horizontalStrut_19);
		
		JPanel panel_5 = new JPanel();
		panel_5.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_2.add(panel_5);
		panel_5.setLayout(new BoxLayout(panel_5, BoxLayout.Y_AXIS));
		
		JPanel panel_12 = new JPanel();
		panel_12.setMaximumSize(new Dimension(32767, 30));
		panel_5.add(panel_12);
		panel_12.setLayout(new BoxLayout(panel_12, BoxLayout.X_AXIS));
		
		Component horizontalGlue = Box.createHorizontalGlue();
		panel_12.add(horizontalGlue);
		
		JLabel lblNewLabel_7 = new JLabel("Transmitted data filter");
		panel_12.add(lblNewLabel_7);
		
		Component horizontalGlue_1 = Box.createHorizontalGlue();
		panel_12.add(horizontalGlue_1);
		
		JPanel panel_10 = new JPanel();
		panel_5.add(panel_10);
		panel_10.setLayout(new BoxLayout(panel_10, BoxLayout.X_AXIS));
		
		Component horizontalStrut_10 = Box.createHorizontalStrut(20);
		panel_10.add(horizontalStrut_10);
		
		JLabel lblNewLabel_5 = new JLabel("SRC");
		panel_10.add(lblNewLabel_5);
		
		Component horizontalStrut_11 = Box.createHorizontalStrut(20);
		panel_10.add(horizontalStrut_11);
		
		srcDaraFld = new JTextField();
		srcDaraFld.setMaximumSize(new Dimension(2147483647, 25));
		panel_10.add(srcDaraFld);
		srcDaraFld.setColumns(10);
		
		Component horizontalStrut_20 = Box.createHorizontalStrut(20);
		panel_10.add(horizontalStrut_20);
		
		JPanel panel_11 = new JPanel();
		panel_5.add(panel_11);
		panel_11.setLayout(new BoxLayout(panel_11, BoxLayout.X_AXIS));
		
		Component horizontalStrut_12 = Box.createHorizontalStrut(20);
		panel_11.add(horizontalStrut_12);
		
		JLabel lblNewLabel_6 = new JLabel("DST");
		panel_11.add(lblNewLabel_6);
		
		Component horizontalStrut_13 = Box.createHorizontalStrut(20);
		panel_11.add(horizontalStrut_13);
		
		dstDataFld = new JTextField();
		dstDataFld.setMaximumSize(new Dimension(2147483647, 25));
		panel_11.add(dstDataFld);
		dstDataFld.setColumns(10);
		
		Component horizontalStrut_21 = Box.createHorizontalStrut(20);
		panel_11.add(horizontalStrut_21);
		
		JSeparator separator = new JSeparator();
		panel_2.add(separator);
		
		Component verticalStrut = Box.createVerticalStrut(10);
		panel_2.add(verticalStrut);
		
		JPanel panel_15 = new JPanel();
		panel_2.add(panel_15);
		panel_15.setLayout(new BoxLayout(panel_15, BoxLayout.X_AXIS));
		
		Component horizontalStrut_14 = Box.createHorizontalStrut(10);
		panel_15.add(horizontalStrut_14);
		JButton btnNewButton = new JButton("discard all");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				sFilter.cleanFilter();
				srcDaraFld.setText("");
				srcIpFld.setText("");
				srcPortFld.setText("");
				
				dstDataFld.setText("");
				dstIpFld.setText("");
				dstPortFld.setText("");
				setFilteredFalse();
				inst.dispose();
				
			}
		});
		panel_15.add(btnNewButton);
		
		Component horizontalGlue_6 = Box.createHorizontalGlue();
		panel_15.add(horizontalGlue_6);
		
		JButton btnNewButton_1 = new JButton("apply");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!checkInputPort(srcPortFld)) return;
				if (!checkInputIP(srcIpFld)) return;
				if (!checkInputPort(dstPortFld)) return;
				if (!checkInputIP(dstIpFld)) return;
				
				sFilter.setSrcPortFilter(srcPortFld.getText());
				sFilter.setDstPortFilter(dstPortFld.getText());
				sFilter.setSrcIpFilter(srcIpFld.getText());
				sFilter.setDstIpFilter(dstIpFld.getText());
				setFilteredTrue();
				inst.dispose();
				
			}
		});
		panel_15.add(btnNewButton_1);
		
		Component horizontalStrut_15 = Box.createHorizontalStrut(7);
		panel_15.add(horizontalStrut_15);
		
		JButton btnNewButton_2 = new JButton("cancel");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				inst.dispose();
			}
		});
		panel_15.add(btnNewButton_2);
	}
	
	public void setFilteredTrue() {
		fBtn.setText("Filtered");
		fBtn.setIcon(new ImageIcon(MainWin.class
				.getResource("/tcpAnalyse/checkMark.png")));
	}
	public void onMouseClc(JTextField fld) {
		fld.setBackground(new Color(255,255,255));
		
	}
	
	public void setFilteredFalse()  {
		fBtn.setText("Filter");
		fBtn.setIcon(new ImageIcon());
	}
	
	private boolean checkInputPort(JTextField input) {
		String inputText = input.getText();
		inputText= inputText.trim();
		if (inputText.equals("")) { input.setText("*"); return true;}
		if (inputText.equals("*")) { return true;}
		String[] split =  input.getText().split(":");
		
		for (Integer i=0;i<split.length;i++) {
			try {
				if (split[i].equals("")) continue;
				Integer.valueOf(split[i]);
			} catch ( NumberFormatException e) {
				input.setBackground(new Color(255,175,175));
				return false;
			}
		}
		return true;
		
		
	}
	
	private boolean checkInputIP(JTextField input) {
		String inputText = input.getText();
		inputText= inputText.trim();
		if (inputText.equals("")) { input.setText("*"); return true;}
		if (inputText.equals("*")) return true;
		String[] split =  input.getText().split(":");
		
		for (Integer i=0;i<split.length;i++) {
			try {
				if (split[i].equals("")) continue;
				InetAddress.getByName(split[i]);
			} catch ( UnknownHostException e) {
				input.setBackground(new Color(255,175,175));
				return false;
			}
		}
		return true;
		
		
	}
	
}
