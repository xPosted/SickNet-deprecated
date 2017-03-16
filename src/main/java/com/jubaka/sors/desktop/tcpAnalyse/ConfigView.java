package com.jubaka.sors.desktop.tcpAnalyse;
/*
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.BoxLayout;
import javax.swing.JLayeredPane;

import java.awt.Color;

import javax.swing.JLabel;

import java.awt.Component;

import javax.swing.Box;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.JSeparator;
import javax.swing.JToggleButton;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JSpinner;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JRadioButton;
import javax.swing.border.LineBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;

import factories.ClassFactory;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import limfo.LoadLimits;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class ConfigView extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JLabel homeLbl;
	private JRadioButton rdbtnLiveOnly;
	private JRadioButton rdbtnRemoteOnly;
	private JRadioButton rdbtnBoth;
	private ButtonGroup rdbtnGroup = new ButtonGroup();
	private JSpinner spDumpSize;
	private JSpinner spHomeMax;
	private ClassFactory factory = ClassFactory.getInstance();
	private JComboBox comboDumpSize;
	private JComboBox comboHomeMax;
	private JToggleButton btnHomeSwitcher;
	private JToggleButton btnDumpSwitcher;
	private boolean homeFlag=false;
	private boolean dumpFlag=false;

	public ConfigView() {
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 580, 455);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2);
		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.Y_AXIS));
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new CompoundBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), new LineBorder(new Color(0, 0, 0))));
		panel_3.setMaximumSize(new Dimension(32767, 50));
		panel_3.setMinimumSize(new Dimension(10, 50));
		panel_3.setPreferredSize(new Dimension(10, 50));
		panel_3.setBackground(Color.WHITE);
		panel_2.add(panel_3);
		panel_3.setLayout(new BoxLayout(panel_3, BoxLayout.X_AXIS));
		
		Component horizontalGlue = Box.createHorizontalGlue();
		panel_3.add(horizontalGlue);
		
		JLabel lblNewLabel = new JLabel("Processing Properties");
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 16));
		panel_3.add(lblNewLabel);
		
		Component horizontalGlue_1 = Box.createHorizontalGlue();
		panel_3.add(horizontalGlue_1);
		
		JPanel panel_14 = new JPanel();
		panel_14.setPreferredSize(new Dimension(10, 50));
		panel_14.setMinimumSize(new Dimension(10, 50));
		panel_14.setMaximumSize(new Dimension(32767, 50));
		panel_14.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel_2.add(panel_14);
		panel_14.setLayout(new BoxLayout(panel_14, BoxLayout.X_AXIS));
		
		JPanel panel_15 = new JPanel();
		panel_14.add(panel_15);
		panel_15.setLayout(new BoxLayout(panel_15, BoxLayout.X_AXIS));
		
		Component horizontalGlue_12 = Box.createHorizontalGlue();
		panel_15.add(horizontalGlue_12);
		
		rdbtnLiveOnly = new JRadioButton("Live only");
		rdbtnLiveOnly.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				factory.getLimits().setStatus(-1);
			}
		});
		rdbtnGroup.add(rdbtnLiveOnly);
		
		panel_15.add(rdbtnLiveOnly);
		
		Component horizontalGlue_13 = Box.createHorizontalGlue();
		panel_15.add(horizontalGlue_13);
		
		JSeparator separator_3 = new JSeparator();
		separator_3.setMaximumSize(new Dimension(5, 32767));
		separator_3.setOrientation(SwingConstants.VERTICAL);
		panel_14.add(separator_3);
		
		JPanel panel_16 = new JPanel();
		panel_14.add(panel_16);
		panel_16.setLayout(new BoxLayout(panel_16, BoxLayout.X_AXIS));
		
		Component horizontalGlue_14 = Box.createHorizontalGlue();
		panel_16.add(horizontalGlue_14);
		
		rdbtnRemoteOnly = new JRadioButton("Remote only");
		rdbtnRemoteOnly.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				factory.getLimits().setStatus(0);
			}
		});
		rdbtnGroup.add(rdbtnRemoteOnly);
		panel_16.add(rdbtnRemoteOnly);
		
		Component horizontalGlue_15 = Box.createHorizontalGlue();
		panel_16.add(horizontalGlue_15);
		
		JSeparator separator_4 = new JSeparator();
		separator_4.setMaximumSize(new Dimension(5, 32767));
		separator_4.setOrientation(SwingConstants.VERTICAL);
		panel_14.add(separator_4);
		
		JPanel panel_17 = new JPanel();
		panel_14.add(panel_17);
		panel_17.setLayout(new BoxLayout(panel_17, BoxLayout.X_AXIS));
		
		Component horizontalGlue_16 = Box.createHorizontalGlue();
		panel_17.add(horizontalGlue_16);
		
		rdbtnBoth = new JRadioButton("Both");
		rdbtnBoth.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				factory.getLimits().setStatus(1);
			}
		});
		rdbtnGroup.add(rdbtnBoth);
		panel_17.add(rdbtnBoth);
		
		Component horizontalGlue_17 = Box.createHorizontalGlue();
		panel_17.add(horizontalGlue_17);
		
		JPanel panel_4 = new JPanel();
		panel_4.setMinimumSize(new Dimension(10, 35));
		panel_4.setPreferredSize(new Dimension(0, 35));
		panel_4.setBorder(new LineBorder(Color.GRAY, 1, true));
		panel_4.setMaximumSize(new Dimension(32767, 35));
		panel_2.add(panel_4);
		panel_4.setLayout(new BoxLayout(panel_4, BoxLayout.X_AXIS));
		
		Component horizontalGlue_4 = Box.createHorizontalGlue();
		panel_4.add(horizontalGlue_4);
		
		JPanel panel_6 = new JPanel();
		panel_6.setMinimumSize(new Dimension(220, 10));
		panel_6.setMaximumSize(new Dimension(400, 32767));
		panel_6.setPreferredSize(new Dimension(400, 10));
		panel_4.add(panel_6);
		panel_6.setLayout(new BoxLayout(panel_6, BoxLayout.X_AXIS));
		
		Component horizontalGlue_5 = Box.createHorizontalGlue();
		panel_6.add(horizontalGlue_5);
		
		JLabel lblNewLabel_1 = new JLabel("home folder");
		lblNewLabel_1.setFont(new Font("Dialog", Font.BOLD, 14));
		panel_6.add(lblNewLabel_1);
		
		Component horizontalGlue_3 = Box.createHorizontalGlue();
		panel_6.add(horizontalGlue_3);
		
		JSeparator separator = new JSeparator();
		separator.setMaximumSize(new Dimension(5, 32767));
		separator.setSize(new Dimension(5, 2));
		separator.setPreferredSize(new Dimension(4, 2));
		separator.setOrientation(SwingConstants.VERTICAL);
		panel_4.add(separator);
		
		JPanel panel_7 = new JPanel();
		panel_7.setPreferredSize(new Dimension(32767, 30));
		panel_4.add(panel_7);
		panel_7.setLayout(new BoxLayout(panel_7, BoxLayout.X_AXIS));
		
		Component horizontalStrut_2 = Box.createHorizontalStrut(20);
		panel_7.add(horizontalStrut_2);
		
		homeLbl = new JLabel("not set");
		homeLbl.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 12));
		panel_7.add(homeLbl);
		
		Component horizontalGlue_2 = Box.createHorizontalGlue();
		panel_7.add(horizontalGlue_2);
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		panel_7.add(horizontalStrut);
		
		JPanel panel_8 = new JPanel();
		panel_8.setPreferredSize(new Dimension(65, 65));
		panel_8.setMaximumSize(new Dimension(30, 32767));
		panel_7.add(panel_8);
		panel_8.setLayout(new BoxLayout(panel_8, BoxLayout.Y_AXIS));
		
		Component verticalStrut_1 = Box.createVerticalStrut(5);
		verticalStrut_1.setPreferredSize(new Dimension(0, 7));
		panel_8.add(verticalStrut_1);
		
		JButton btnNewButton = new JButton("Edit");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser chooser = new JFileChooser();
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				chooser.showSaveDialog(null);
				homeLbl.setText(chooser.getSelectedFile().getPath());
				factory.setHome(chooser.getSelectedFile().getPath());
			}
		});
		panel_8.add(btnNewButton);
		
		Component verticalStrut = Box.createVerticalStrut(2);
		panel_8.add(verticalStrut);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(5);
		panel_7.add(horizontalStrut_1);
		
					// sizepanel 1
		JPanel panel_5 = new JPanel();
		panel_5.setMinimumSize(new Dimension(10, 35));
		panel_5.setBorder(new LineBorder(Color.GRAY, 1, true));
		panel_5.setPreferredSize(new Dimension(0, 35));
		panel_5.setMaximumSize(new Dimension(32767, 35));
		panel_5.setLayout(new BoxLayout(panel_5, BoxLayout.X_AXIS));
		
		JPanel panel_9 = new JPanel();
		panel_9.setPreferredSize(new Dimension(220, 10));
		panel_9.setMaximumSize(new Dimension(220, 32767));
		panel_9.setMinimumSize(new Dimension(220, 10));
		panel_5.add(panel_9);
		panel_9.setLayout(new BoxLayout(panel_9, BoxLayout.X_AXIS));
		
		Component horizontalGlue_6 = Box.createHorizontalGlue();
		panel_9.add(horizontalGlue_6);
		
		JPanel panel55 = new JPanel();
		panel_9.add(panel55);
		panel55.setLayout(new BoxLayout(panel55, BoxLayout.Y_AXIS));
		
		Component verticalGlue_2 = Box.createVerticalGlue();
		panel55.add(verticalGlue_2);
		
		JLabel lblNewLabel_3 = new JLabel("home size limit");
		panel55.add(lblNewLabel_3);
		lblNewLabel_3.setFont(new Font("Dialog", Font.BOLD, 14));
		
		Component verticalGlue_1 = Box.createVerticalGlue();
		panel55.add(verticalGlue_1);
		
		Component horizontalGlue_7 = Box.createHorizontalGlue();
		panel_9.add(horizontalGlue_7);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setMaximumSize(new Dimension(5, 32767));
		separator_1.setOrientation(SwingConstants.VERTICAL);
		panel_5.add(separator_1);
		
		JPanel panel_10 = new JPanel();
		panel_5.add(panel_10);
		panel_10.setLayout(new BoxLayout(panel_10, BoxLayout.X_AXIS));
		
		Component horizontalStrut_3 = Box.createHorizontalStrut(20);
		panel_10.add(horizontalStrut_3);
		
		spHomeMax = new JSpinner();
		spHomeMax.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				double i= (double)spHomeMax.getValue();
				DefaultComboBoxModel<String> comboModel = (DefaultComboBoxModel<String>)comboHomeMax.getModel();
				String pow =(String) comboModel.getSelectedItem();
				if (pow.equals("kb")) i=i*Math.pow(2, 10);
				if (pow.equals("mb")) i=i*Math.pow(2, 20);
				if (pow.equals("gb")) i=i*Math.pow(2, 30);
				factory.getLimits().setHomeRemoteMaxSize(i);
			}
		});
		SpinnerNumberModel spNumModel = new SpinnerNumberModel(0, 0, Double.MAX_VALUE, 1);
		
		spHomeMax.setModel(spNumModel);
		spHomeMax.setFont(new Font("Dialog", Font.BOLD, 14));
		spHomeMax.setMaximumSize(new Dimension(32767, 30));
		spHomeMax.setSize(new Dimension(0, 30));
		spHomeMax.setPreferredSize(new Dimension(28, 30));
		panel_10.add(spHomeMax);
		
		Component horizontalStrut_4 = Box.createHorizontalStrut(5);
		panel_10.add(horizontalStrut_4);
		
		comboHomeMax = new JComboBox();
		comboHomeMax.setModel(new DefaultComboBoxModel(new String[] {"gb", "mb", "kb", "b"}));
		comboHomeMax.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				double i = ClassFactory.getInstance().getLimits().getHomeMax();
				DefaultComboBoxModel<String> comboModel = (DefaultComboBoxModel<String>)comboHomeMax.getModel();
				String pow =(String) comboModel.getSelectedItem();
				if (pow.equals("kb")) i=i*Math.pow(2, 10);
				if (pow.equals("mb")) i=i*Math.pow(2, 20);
				if (pow.equals("gb")) i=i*Math.pow(2, 30);
				spHomeMax.setValue(i);
			}
		});
		comboHomeMax.setToolTipText("\n");
		comboHomeMax.setPreferredSize(new Dimension(60, 24));
		comboHomeMax.setMaximumSize(new Dimension(60, 30));
		panel_10.add(comboHomeMax);
		
		Component horizontalGlue_8 = Box.createHorizontalGlue();
		panel_10.add(horizontalGlue_8);
		
		JPanel panel_1 = new JPanel();
		panel_10.add(panel_1);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.Y_AXIS));
		
		btnHomeSwitcher = new JToggleButton("Disabled");
		btnHomeSwitcher.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				LoadLimits lim = factory.getLimits();
				if (homeFlag) {
					homeFlag=false;
					btnHomeSwitcher.setText("Disabled");
					spHomeMax.setEnabled(false);
					comboHomeMax.setEnabled(false);
					lim.setHomeRemoteMaxSize(-1);
					
				} else {
					homeFlag=true;
					btnHomeSwitcher.setText("Enabled");
					spHomeMax.setEnabled(true);
					comboHomeMax.setEnabled(true);
					lim.setHomeRemoteMaxSize((double) spHomeMax.getValue());
					
				}
			}
		});
		panel_1.add(btnHomeSwitcher);
		
					// sizepanel 1
		panel_2.add(panel_5);
		
					// sizepanel 2
		JPanel panel_56 = new JPanel();
		panel_56.setMinimumSize(new Dimension(10, 35));
		panel_56.setBorder(new LineBorder(Color.GRAY, 1, true));
		panel_56.setPreferredSize(new Dimension(0, 35));
		panel_56.setMaximumSize(new Dimension(32767, 35));
		panel_56.setLayout(new BoxLayout(panel_56, BoxLayout.X_AXIS));
		
		JPanel panel_91 = new JPanel();
		panel_91.setMinimumSize(new Dimension(220, 10));
		panel_91.setMaximumSize(new Dimension(220, 32767));
		panel_91.setPreferredSize(new Dimension(220, 10));
		panel_56.add(panel_91);
		panel_91.setLayout(new BoxLayout(panel_91, BoxLayout.X_AXIS));
		
		Component horizontalGlue_61 = Box.createHorizontalGlue();
		panel_91.add(horizontalGlue_61);
		
		JPanel panel551 = new JPanel();
		panel_91.add(panel551);
		panel551.setLayout(new BoxLayout(panel551, BoxLayout.Y_AXIS));
		
		Component verticalGlue_21 = Box.createVerticalGlue();
		panel551.add(verticalGlue_21);
		
		JLabel lblNewLabel_31 = new JLabel("dump size limit");
		lblNewLabel_31.setFont(new Font("Dialog", Font.BOLD, 14));
		panel551.add(lblNewLabel_31);
		lblNewLabel_3.setFont(new Font("Dialog", Font.BOLD, 14));
		
		Component verticalGlue_11 = Box.createVerticalGlue();
		panel551.add(verticalGlue_11);
		
		Component horizontalGlue_71 = Box.createHorizontalGlue();
		panel_91.add(horizontalGlue_71);
		
		JSeparator separator_11 = new JSeparator();
		separator_11.setMaximumSize(new Dimension(5, 32767));
		separator_11.setOrientation(SwingConstants.VERTICAL);
		panel_56.add(separator_11);
		
		JPanel panel_101 = new JPanel();
		panel_56.add(panel_101);
		panel_101.setLayout(new BoxLayout(panel_101, BoxLayout.X_AXIS));
		
		Component horizontalStrut_31 = Box.createHorizontalStrut(20);
		panel_101.add(horizontalStrut_31);
		
		spDumpSize = new JSpinner();
		spDumpSize.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				double i= (double)spDumpSize.getModel().getValue();
				DefaultComboBoxModel<String> comboModel = (DefaultComboBoxModel<String>)comboDumpSize.getModel();
				String pow =(String) comboModel.getSelectedItem();
				if (pow.equals("kb")) i=i*Math.pow(2, 10);
				if (pow.equals("mb")) i=i*Math.pow(2, 20);
				if (pow.equals("gb")) i=i*Math.pow(2, 30);
				factory.getLimits().setMaxDumpSize(i);
			}
		});
		SpinnerNumberModel spDSmodel = new SpinnerNumberModel(0, 0, Double.MAX_VALUE, 1);
		spDumpSize.setModel(spDSmodel);
		spDumpSize.setFont(new Font("Dialog", Font.BOLD, 14));
		spDumpSize.setMaximumSize(new Dimension(32767, 30));
		spDumpSize.setSize(new Dimension(0, 30));
		spDumpSize.setPreferredSize(new Dimension(28, 30));
		panel_101.add(spDumpSize);
		
		Component horizontalStrut_41 = Box.createHorizontalStrut(5);
		panel_101.add(horizontalStrut_41);
		
		comboDumpSize = new JComboBox();
		comboDumpSize.setModel(new DefaultComboBoxModel(new String[] {"gb", "mb", "kb", "b"}));
		comboDumpSize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				double i = ClassFactory.getInstance().getLimits().getDumpMax();
				DefaultComboBoxModel<String> comboModel = (DefaultComboBoxModel<String>)comboDumpSize.getModel();
			
				String pow =(String) comboModel.getSelectedItem();
				if (pow.equals("kb")) i=i*Math.pow(2, 10);
				if (pow.equals("mb")) i=i*Math.pow(2, 20);
				if (pow.equals("gb")) i=i*Math.pow(2, 30);
				spDumpSize.setValue(i);
			}
		});
		comboDumpSize.setToolTipText("gb\nmb\nkb\nb");
		comboDumpSize.setPreferredSize(new Dimension(60, 24));
		comboDumpSize.setMaximumSize(new Dimension(60, 30));
		panel_101.add(comboDumpSize);
		
		Component horizontalGlue_81 = Box.createHorizontalGlue();
		panel_101.add(horizontalGlue_81);
		
		JPanel panel_20 = new JPanel();
		panel_101.add(panel_20);
		panel_20.setLayout(new BoxLayout(panel_20, BoxLayout.X_AXIS));
		
		btnDumpSwitcher = new JToggleButton("Disabled");
		btnDumpSwitcher.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoadLimits lim = factory.getLimits();
				if (dumpFlag) {
					dumpFlag=false;
					btnDumpSwitcher.setText("Disabled");
					spDumpSize .setEnabled(false);
					comboDumpSize.setEnabled(false);
					lim.setMaxDumpSize(-1);
					
				} else {
					dumpFlag=true;
					btnDumpSwitcher.setText("Enabled");
					spDumpSize.setEnabled(true);
					comboDumpSize.setEnabled(true);
					lim.setMaxDumpSize((double) spDumpSize.getValue());
					
				}
				
			}
		});
		panel_20.add(btnDumpSwitcher);
					// sizepanel 2
		panel_2.add(panel_56);
					// ip panel 1
		JPanel panel_11 = new JPanel();
		panel_11.setMinimumSize(new Dimension(10, 35));
		panel_11.setBorder(new LineBorder(Color.GRAY, 1, true));
		panel_11.setPreferredSize(new Dimension(0, 35));
		panel_11.setMaximumSize(new Dimension(32767, 35));
		panel_2.add(panel_11);
		panel_11.setLayout(new BoxLayout(panel_11, BoxLayout.X_AXIS));
		
		JPanel panel_12 = new JPanel();
		panel_12.setPreferredSize(new Dimension(220, 10));
		panel_12.setMinimumSize(new Dimension(220, 10));
		panel_12.setMaximumSize(new Dimension(220, 32767));
		panel_11.add(panel_12);
		panel_12.setLayout(new BoxLayout(panel_12, BoxLayout.X_AXIS));
		
		Component horizontalGlue_9 = Box.createHorizontalGlue();
		panel_12.add(horizontalGlue_9);
		
		JLabel lblNewLabel_4 = new JLabel("allowed ip");
		lblNewLabel_4.setFont(new Font("Dialog", Font.BOLD, 14));
		panel_12.add(lblNewLabel_4);
		
		Component horizontalGlue_10 = Box.createHorizontalGlue();
		panel_12.add(horizontalGlue_10);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setMaximumSize(new Dimension(5, 32767));
		separator_2.setPreferredSize(new Dimension(5, 2));
		separator_2.setOrientation(SwingConstants.VERTICAL);
		panel_11.add(separator_2);
		
		JPanel panel_13 = new JPanel();
		panel_11.add(panel_13);
		panel_13.setLayout(new BoxLayout(panel_13, BoxLayout.X_AXIS));
		
		Component horizontalStrut_5 = Box.createHorizontalStrut(20);
		panel_13.add(horizontalStrut_5);
		
		textField = new JTextField();
		textField.setPreferredSize(new Dimension(170, 19));
		textField.setMaximumSize(new Dimension(170, 30));
		panel_13.add(textField);
		textField.setColumns(10);
		
		Component horizontalStrut_6 = Box.createHorizontalStrut(5);
		panel_13.add(horizontalStrut_6);
		
		JButton btnNewButton_1 = new JButton("ADD");
		btnNewButton_1.setOpaque(false);
		btnNewButton_1.setBorderPainted(false);
		panel_13.add(btnNewButton_1);
		
		Component horizontalStrut_7 = Box.createHorizontalStrut(20);
		panel_13.add(horizontalStrut_7);
		
		Component horizontalGlue_11 = Box.createHorizontalGlue();
		panel_13.add(horizontalGlue_11);
		
		JButton btnNewButton_2 = new JButton("view all");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoadLimits ll = factory.getLimits();
				AllowedView allowedv = new AllowedView(ll.getAllowedIP(), "IPs");
				allowedv.setVisible(true);
				
			}
		});
		panel_13.add(btnNewButton_2);
		
		Component horizontalStrut_8 = Box.createHorizontalStrut(5);
		panel_13.add(horizontalStrut_8);
		
					// ip panel 1
					// user panle 2
		JPanel userPan  = new JPanel();
		userPan.setMinimumSize(new Dimension(10, 35));
		userPan.setBorder(new LineBorder(Color.GRAY, 1, true));
		userPan.setPreferredSize(new Dimension(0, 35));
		userPan.setMaximumSize(new Dimension(32767, 35));
		panel_2.add(userPan);
		userPan.setLayout(new BoxLayout(userPan, BoxLayout.X_AXIS));
		
		JPanel panel_121 = new JPanel();
		panel_121.setMinimumSize(new Dimension(220, 10));
		panel_121.setMaximumSize(new Dimension(220, 32767));
		panel_121.setPreferredSize(new Dimension(220, 10));
		userPan.add(panel_121);
		panel_121.setLayout(new BoxLayout(panel_121, BoxLayout.X_AXIS));
		
		Component horizontalGlue_91 = Box.createHorizontalGlue();
		panel_121.add(horizontalGlue_91);
		
		JLabel lblNewLabel_41 = new JLabel("allowed users");
		lblNewLabel_41.setFont(new Font("Dialog", Font.BOLD, 14));
		panel_121.add(lblNewLabel_41);
		
		Component horizontalGlue_101 = Box.createHorizontalGlue();
		panel_121.add(horizontalGlue_101);
		
		JSeparator separator_21 = new JSeparator();
		separator_21.setMaximumSize(new Dimension(5, 32767));
		separator_21.setPreferredSize(new Dimension(5, 2));
		separator_21.setOrientation(SwingConstants.VERTICAL);
		userPan.add(separator_21);
		
		JPanel panel_131 = new JPanel();
		userPan.add(panel_131);
		panel_131.setLayout(new BoxLayout(panel_131, BoxLayout.X_AXIS));
		
		Component horizontalStrut_51 = Box.createHorizontalStrut(20);
		panel_131.add(horizontalStrut_51);
		
		textField = new JTextField();
		textField.setPreferredSize(new Dimension(170, 19));
		textField.setMaximumSize(new Dimension(170, 30));
		panel_131.add(textField);
		textField.setColumns(10);
		
		Component horizontalStrut_61 = Box.createHorizontalStrut(5);
		panel_131.add(horizontalStrut_61);
		
		JButton btnNewButton_11 = new JButton("ADD");
		btnNewButton_11.setOpaque(false);
		btnNewButton_11.setBorderPainted(false);
		panel_131.add(btnNewButton_11);
		
		Component horizontalStrut_71 = Box.createHorizontalStrut(20);
		panel_131.add(horizontalStrut_71);
		
		Component horizontalGlue_111 = Box.createHorizontalGlue();
		panel_131.add(horizontalGlue_111);
		
		JButton btnNewButton_21 = new JButton("view all");
		btnNewButton_21.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoadLimits ll = factory.getLimits();
				AllowedView allowedv = new AllowedView(ll.getAllowedUsers(), "Users");
				allowedv.setVisible(true);
			}
		});
		panel_131.add(btnNewButton_21);
		
		Component horizontalStrut_81 = Box.createHorizontalStrut(5);
		panel_131.add(horizontalStrut_81);
		
					// ip panel 2
		
		Component verticalGlue = Box.createVerticalGlue();
		panel_2.add(verticalGlue);
		
		JSeparator separator_5 = new JSeparator();
		separator_5.setMaximumSize(new Dimension(32767, 2));
		panel_2.add(separator_5);
		
		JPanel panel_18 = new JPanel();
		panel_18.setPreferredSize(new Dimension(10, 35));
		panel_18.setMinimumSize(new Dimension(10, 35));
		panel_18.setMaximumSize(new Dimension(32767, 35));
		panel_2.add(panel_18);
		panel_18.setLayout(new BoxLayout(panel_18, BoxLayout.X_AXIS));
		
		Component horizontalStrut_9 = Box.createHorizontalStrut(10);
		panel_18.add(horizontalStrut_9);
		
		JLabel lblusrlocaletc = new JLabel("/usr/local/etc/sors/sors.cfg");
		lblusrlocaletc.setFont(new Font("Dialog", Font.ITALIC, 12));
		panel_18.add(lblusrlocaletc);
		
		Component horizontalGlue_18 = Box.createHorizontalGlue();
		panel_18.add(horizontalGlue_18);
		
		JPanel panel_19 = new JPanel();
		panel_19.setMaximumSize(new Dimension(60, 32767));
		panel_18.add(panel_19);
		panel_19.setLayout(new BoxLayout(panel_19, BoxLayout.Y_AXIS));
		
		Component verticalGlue_3 = Box.createVerticalGlue();
		panel_19.add(verticalGlue_3);
		
		JButton btnNewButton_3 = new JButton("Save");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ClassFactory.getInstance().saveConfig();
				
			}
		});
		panel_19.add(btnNewButton_3);
		
		Component verticalGlue_4 = Box.createVerticalGlue();
		panel_19.add(verticalGlue_4);
		
		Component horizontalStrut_10 = Box.createHorizontalStrut(5);
		panel_18.add(horizontalStrut_10);
		
		
		initValues();
	}
	private void initValues() {
		factory = ClassFactory.getInstance();
		LoadLimits lim =  factory.getLimits();
		Integer st;
		
		String home = factory.getHome();
	if (home==null) homeLbl.setText("not set"); else 
		homeLbl.setText(home);
	st = lim.getStatus();
	switch (st) {
	case -1:
			rdbtnLiveOnly.setSelected(true);
		break;
	case 0:
			rdbtnRemoteOnly.setSelected(true);
		break;
	case 1:
			rdbtnBoth.setSelected(true);
	break;
	default:
		
		break;
	}
		
		if (lim.getHomeMax()==-1) {
			spHomeMax.setEnabled(false);
			comboHomeMax.setEnabled(false);
			
		} else {
			SpinnerNumberModel snm =(SpinnerNumberModel) spHomeMax.getModel();
			snm.setValue(lim.getHomeMax());
			btnHomeSwitcher.setText("Enabled");
			btnHomeSwitcher.setSelected(true);
			homeFlag=true;
		}
		
		if (lim.getDumpMax()==-1) {
			spDumpSize.setEnabled(false);
			comboDumpSize.setEnabled(false);
		} else {
			SpinnerNumberModel snm = (SpinnerNumberModel) spDumpSize.getModel();
			snm.setValue(lim.getDumpMax());
			btnDumpSwitcher.setText("Enabled");
			btnDumpSwitcher.setSelected(true);
			dumpFlag=true;
			
		}
	
	
	}
}
*/