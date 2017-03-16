package com.jubaka.sors.desktop.tcpAnalyse;

import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import java.awt.Dimension;
import java.awt.BorderLayout;

import javax.swing.JLabel;

import java.awt.Component;

import javax.swing.Box;
import javax.swing.border.EmptyBorder;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

import javax.swing.border.LineBorder;

import com.jubaka.sors.desktop.factories.ClassFactory;
import com.jubaka.sors.desktop.limfo.LoadLimits;

import java.awt.Color;


import javax.swing.JComboBox;

public class GeneralSettingsPanel extends JPanel {
	
	ClassFactory factory = ClassFactory.getInstance();
	private JCheckBox chckbxLiveOnly;
	private JCheckBox chckbxRemoteOnly;
	private JCheckBox chckbxBoth;
	private JComboBox comboHomeSize;
	
	private JLabel lblhome;
	private JTextField txtfldHomeRemMax;

	/**
	 * Create the panel.
	 */
	public GeneralSettingsPanel() {
		setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(10, 250));
		add(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		JPanel panel_1 = new JPanel();
		panel_1.setMinimumSize(new Dimension(140, 10));
		panel_1.setPreferredSize(new Dimension(140, 10));
		panel_1.setBorder(new EmptyBorder(10, 10, 0, 10));
		panel.add(panel_1);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.Y_AXIS));
		
		JLabel lblNewLabel = new JLabel("NodeServerEndpoint status");
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 16));
		panel_1.add(lblNewLabel);
		
		Component verticalGlue = Box.createVerticalGlue();
		panel_1.add(verticalGlue);
		
		JSeparator separator = new JSeparator();
		separator.setMaximumSize(new Dimension(4, 32767));
		separator.setOrientation(SwingConstants.VERTICAL);
		panel.add(separator);
		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2);
		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.Y_AXIS));
		
		JPanel panel_3 = new JPanel();
		panel_3.setMaximumSize(new Dimension(32767, 200));
		panel_3.setPreferredSize(new Dimension(10, 200));
		panel_3.setBorder(new EmptyBorder(10, 5, 5, 5));
		panel_2.add(panel_3);
		panel_3.setLayout(new BoxLayout(panel_3, BoxLayout.Y_AXIS));
		
		JPanel panel_7 = new JPanel();
		panel_3.add(panel_7);
		panel_7.setLayout(new BoxLayout(panel_7, BoxLayout.X_AXIS));
			ButtonGroup bg = new ButtonGroup();
		chckbxLiveOnly = new JCheckBox("Live only");
		chckbxLiveOnly.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ClassFactory.getInstance().getLimits().setStatus(-1);
			}
		});
		bg.add(chckbxLiveOnly);
		panel_7.add(chckbxLiveOnly);
		
		Component horizontalGlue = Box.createHorizontalGlue();
		panel_7.add(horizontalGlue);
		
		JPanel panel_6 = new JPanel();
		panel_6.setBorder(new EmptyBorder(10, 25, 10, 10));
		panel_3.add(panel_6);
		panel_6.setLayout(new BorderLayout(0, 0));
		
		JTextArea txtrInsertTextHere = new JTextArea();
		txtrInsertTextHere.setEditable(false);
		txtrInsertTextHere.setBackground(UIManager.getColor("MenuItem.background"));
		txtrInsertTextHere.setLineWrap(true);
		txtrInsertTextHere.setText("Deny all remote requests to this node. Only local processing operations will be permited.");
		panel_6.add(txtrInsertTextHere, BorderLayout.CENTER);
		
		JPanel panel_4 = new JPanel();
		panel_4.setPreferredSize(new Dimension(10, 200));
		panel_4.setBorder(new EmptyBorder(10, 5, 5, 5));
		panel_2.add(panel_4);
		panel_4.setLayout(new BoxLayout(panel_4, BoxLayout.Y_AXIS));
		
		JPanel panel_8 = new JPanel();
		panel_4.add(panel_8);
		panel_8.setLayout(new BoxLayout(panel_8, BoxLayout.X_AXIS));
		
		chckbxRemoteOnly = new JCheckBox("Remote only");
		chckbxRemoteOnly.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ClassFactory.getInstance().getLimits().setStatus(0);
			}
		});
		bg.add(chckbxRemoteOnly);
		panel_8.add(chckbxRemoteOnly);
		
		Component horizontalGlue_1 = Box.createHorizontalGlue();
		panel_8.add(horizontalGlue_1);
		
		JPanel panel_9 = new JPanel();
		panel_9.setBorder(new EmptyBorder(10, 25, 10, 10));
		panel_4.add(panel_9);
		panel_9.setLayout(new BorderLayout(0, 0));
		
		JTextArea txtrDenyLiveCapture = new JTextArea();
		txtrDenyLiveCapture.setEditable(false);
		txtrDenyLiveCapture.setLineWrap(true);
		txtrDenyLiveCapture.setText("Deny live capture stream processing. Only remote requests, or local dump processing will be permited.  ");
		txtrDenyLiveCapture.setBackground(UIManager.getColor("MenuItem.background"));
		panel_9.add(txtrDenyLiveCapture, BorderLayout.CENTER);
		
		JPanel panel_5 = new JPanel();
		panel_5.setPreferredSize(new Dimension(10, 200));
		panel_5.setBorder(new EmptyBorder(10, 5, 5, 5));
		panel_2.add(panel_5);
		panel_5.setLayout(new BoxLayout(panel_5, BoxLayout.Y_AXIS));
		
		JPanel panel_10 = new JPanel();
		panel_5.add(panel_10);
		panel_10.setLayout(new BoxLayout(panel_10, BoxLayout.X_AXIS));
		
		chckbxBoth = new JCheckBox("Both");
		chckbxBoth.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ClassFactory.getInstance().getLimits().setStatus(1);
			}
		});
		bg.add(chckbxBoth);
		panel_10.add(chckbxBoth);
		
		Component horizontalGlue_2 = Box.createHorizontalGlue();
		panel_10.add(horizontalGlue_2);
		
		JPanel panel_11 = new JPanel();
		panel_11.setBorder(new EmptyBorder(10, 25, 10, 10));
		panel_5.add(panel_11);
		panel_11.setLayout(new BorderLayout(0, 0));
		
		JTextArea txtrAllKindOf = new JTextArea();
		txtrAllKindOf.setEditable(false);
		txtrAllKindOf.setText("All kind of request will be permited.");
		txtrAllKindOf.setLineWrap(true);
		txtrAllKindOf.setBackground(UIManager.getColor("MenuItem.background"));
		panel_11.add(txtrAllKindOf, BorderLayout.CENTER);
		
		JPanel panel_12 = new JPanel();
		panel_12.setPreferredSize(new Dimension(10, 50));
		add(panel_12);
		panel_12.setLayout(new BoxLayout(panel_12, BoxLayout.X_AXIS));
		
		JPanel panel_13 = new JPanel();
		panel_13.setMinimumSize(new Dimension(140, 10));
		panel_13.setBorder(new EmptyBorder(10, 10, 10, 10));
		panel_13.setPreferredSize(new Dimension(140, 10));
		panel_12.add(panel_13);
		panel_13.setLayout(new BoxLayout(panel_13, BoxLayout.Y_AXIS));
		
		JLabel lblHomeFolder = new JLabel("Home folder");
		lblHomeFolder.setFont(new Font("Dialog", Font.BOLD, 16));
		panel_13.add(lblHomeFolder);
		
		Component verticalGlue_1 = Box.createVerticalGlue();
		panel_13.add(verticalGlue_1);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setPreferredSize(new Dimension(4, 2));
		separator_1.setMaximumSize(new Dimension(4, 32767));
		separator_1.setOrientation(SwingConstants.VERTICAL);
		panel_12.add(separator_1);
		
		JPanel panel_14 = new JPanel();
		panel_14.setBorder(new EmptyBorder(10, 10, 10, 10));
		panel_12.add(panel_14);
		panel_14.setLayout(new BoxLayout(panel_14, BoxLayout.X_AXIS));
		
		 lblhome = new JLabel("not set");
		 lblhome.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 14));
		panel_14.add(lblhome);
		
		Component horizontalGlue_3 = Box.createHorizontalGlue();
		panel_14.add(horizontalGlue_3);
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		panel_14.add(horizontalStrut);
		
		JButton btnNewButton = new JButton("edit");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser chooser = new JFileChooser();
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				chooser.showOpenDialog(null);
				String newHome = chooser.getSelectedFile().getAbsolutePath();
				if ((newHome!=null)) {
					ClassFactory.getInstance().getLimits().setHome(newHome);
					lblhome.setText(newHome);
				}
			}
		});
		panel_14.add(btnNewButton);
		
		JPanel panel_15 = new JPanel();
		panel_15.setMaximumSize(new Dimension(32767, 50));
		panel_15.setBorder(null);
		panel_15.setPreferredSize(new Dimension(10, 50));
		add(panel_15);
		panel_15.setLayout(new BoxLayout(panel_15, BoxLayout.X_AXIS));
		
		JPanel panel_16 = new JPanel();
		panel_16.setBorder(new EmptyBorder(7, 7, 7, 7));
		panel_16.setMinimumSize(new Dimension(140, 30));
		panel_16.setPreferredSize(new Dimension(140, 30));
		panel_15.add(panel_16);
		panel_16.setLayout(new BoxLayout(panel_16, BoxLayout.Y_AXIS));
		
		JPanel panel_18 = new JPanel();
		panel_16.add(panel_18);
		panel_18.setLayout(new BoxLayout(panel_18, BoxLayout.X_AXIS));
		
		JLabel lblNewLabel_1 = new JLabel("Home remote");
		panel_18.add(lblNewLabel_1);
		
		JPanel panel_19 = new JPanel();
		panel_16.add(panel_19);
		panel_19.setLayout(new BoxLayout(panel_19, BoxLayout.X_AXIS));
		
		JLabel lblNewLabel_2 = new JLabel("maxSize");
		panel_19.add(lblNewLabel_2);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setOrientation(SwingConstants.VERTICAL);
		panel_15.add(separator_2);
		
		JPanel panel_17 = new JPanel();
		panel_17.setBorder(new EmptyBorder(10, 10, 10, 10));
		panel_15.add(panel_17);
		panel_17.setLayout(new BoxLayout(panel_17, BoxLayout.X_AXIS));
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		panel_17.add(horizontalStrut_1);
		
		txtfldHomeRemMax = new JTextField();
		txtfldHomeRemMax.setHorizontalAlignment(SwingConstants.CENTER);
		panel_17.add(txtfldHomeRemMax);
		txtfldHomeRemMax.setColumns(10);
		
		Component horizontalStrut_2 = Box.createHorizontalStrut(20);
		horizontalStrut_2.setPreferredSize(new Dimension(10, 0));
		panel_17.add(horizontalStrut_2);
		
		comboHomeSize = new JComboBox();
		comboHomeSize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		comboHomeSize.setPreferredSize(new Dimension(70, 24));
		panel_17.add(comboHomeSize);
		
		Component horizontalGlue_4 = Box.createHorizontalGlue();
		panel_17.add(horizontalGlue_4);

		
		init(); // loading values
	}
	
	private void init() {
		LoadLimits ll = factory.getLimits();
		Integer status = ll.getStatus();
		String home = ll.getHome();
		if (status==-1) {
			chckbxLiveOnly.setSelected(true);
		}
		if (status==0) {
			chckbxRemoteOnly.setSelected(true);
		}
		if (status==1) {
			chckbxBoth.setSelected(true);
		}
		if (home!=null) {
			DefaultComboBoxModel<String> homeSizeModel = new DefaultComboBoxModel<String>();
			homeSizeModel.addElement("b");
			homeSizeModel.addElement("kb");
			homeSizeModel.addElement("mb");
			homeSizeModel.addElement("gb");
			comboHomeSize.setModel(homeSizeModel);
			lblhome.setText(home);
			Double sizeView = Controller.processSize(ll.getHomeRemoteMaxSize(), homeSizeModel);
			
			txtfldHomeRemMax.setText( sizeView.toString());
		}
	}
}
