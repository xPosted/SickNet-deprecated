package com.jubaka.sors.desktop.tcpAnalyse;

import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import java.awt.Dimension;

import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import java.awt.Component;

import javax.swing.Box;
import javax.swing.JButton;

import java.awt.Insets;

import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.JPasswordField;

import com.jubaka.sors.desktop.limfo.LoadInfo;
import com.jubaka.sors.desktop.remote.WebConnection;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class RemoteAccessSettings extends JPanel {
	private JTextField txtNodeName;
	private JTextArea txtAreaNodeDesc;
	private JTextField txtUser;
	private JTextField txtServerName;
	private JPasswordField txtPass;
	private JLabel lblStatus;
	private JButton btnConnect;

	/**
	 * Create the panel.
	 */
	public RemoteAccessSettings() {
		init_1();
		init_2();
	}
	
	private void init_1() {
setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JPanel panel_14 = new JPanel();
		panel_14.setMaximumSize(new Dimension(32767, 100));
		panel_14.setBorder(new CompoundBorder(new EtchedBorder(EtchedBorder.RAISED, null, null), new EmptyBorder(10, 25, 10, 25)));
		add(panel_14);
		panel_14.setLayout(new BoxLayout(panel_14, BoxLayout.X_AXIS));
		
		JLabel lblNewLabel_4 = new JLabel("status:");
		panel_14.add(lblNewLabel_4);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		panel_14.add(horizontalStrut_1);
		
		lblStatus = new JLabel("not connected");
		panel_14.add(lblStatus);
		
		Component horizontalGlue_9 = Box.createHorizontalGlue();
		panel_14.add(horizontalGlue_9);
		
		JLabel lblNewLabel_6 = new JLabel("New label");
		panel_14.add(lblNewLabel_6);
		
		JPanel panel = new JPanel();
		add(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		JPanel panel_1 = new JPanel();
		panel_1.setMaximumSize(new Dimension(150, 40));
		panel_1.setPreferredSize(new Dimension(150, 10));
		panel_1.setBorder(new EmptyBorder(2, 10, 2, 5));
		panel.add(panel_1);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));
		
		Component horizontalGlue = Box.createHorizontalGlue();
		panel_1.add(horizontalGlue);
		
		JLabel lblNewLabel = new JLabel("NodeServerEndpoint Name");
		panel_1.add(lblNewLabel);
		
		Component horizontalGlue_1 = Box.createHorizontalGlue();
		panel_1.add(horizontalGlue_1);
		
		JSeparator separator = new JSeparator();
		separator.setMaximumSize(new Dimension(3, 32767));
		separator.setOrientation(SwingConstants.VERTICAL);
		panel.add(separator);
		
		JPanel panel_2 = new JPanel();
		panel_2.setMaximumSize(new Dimension(32767, 40));
		panel_2.setPreferredSize(new Dimension(10, 40));
		panel_2.setBorder(new EmptyBorder(2, 10, 2, 5));
		panel.add(panel_2);
		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.X_AXIS));
		
		txtNodeName = new JTextField();
		panel_2.add(txtNodeName);
		txtNodeName.setColumns(10);
		
		JPanel panel_3 = new JPanel();
		add(panel_3);
		panel_3.setLayout(new BoxLayout(panel_3, BoxLayout.X_AXIS));
		
		JPanel panel_4 = new JPanel();
		panel_4.setMaximumSize(new Dimension(150, 40));
		panel_4.setPreferredSize(new Dimension(150, 10));
		panel_4.setBorder(new EmptyBorder(2, 10, 2, 5));
		panel_3.add(panel_4);
		panel_4.setLayout(new BoxLayout(panel_4, BoxLayout.X_AXIS));
		
		Component horizontalGlue_4 = Box.createHorizontalGlue();
		panel_4.add(horizontalGlue_4);
		
		JLabel lblNewLabel_1 = new JLabel("NodeServerEndpoint description");
		panel_4.add(lblNewLabel_1);
		
		Component horizontalGlue_5 = Box.createHorizontalGlue();
		panel_4.add(horizontalGlue_5);
		
		JSeparator separator_1 = new JSeparator();
		panel_3.add(separator_1);
		separator_1.setMaximumSize(new Dimension(3, 32767));
		separator_1.setOrientation(SwingConstants.VERTICAL);
		
		JPanel panel_5 = new JPanel();
		panel_5.setMaximumSize(new Dimension(32767, 100));
		panel_5.setPreferredSize(new Dimension(10, 70));
		panel_5.setBorder(new EmptyBorder(2, 10, 2, 5));
		panel_3.add(panel_5);
		panel_5.setLayout(new BoxLayout(panel_5, BoxLayout.X_AXIS));
		
		JScrollPane scrollPane = new JScrollPane();
		panel_5.add(scrollPane);
		
		txtAreaNodeDesc = new JTextArea();
		scrollPane.setViewportView(txtAreaNodeDesc);
		
		JPanel panel_6 = new JPanel();
		add(panel_6);
		panel_6.setLayout(new BoxLayout(panel_6, BoxLayout.X_AXIS));
		
		JPanel panel_7 = new JPanel();
		panel_7.setMaximumSize(new Dimension(150, 40));
		panel_7.setPreferredSize(new Dimension(150, 10));
		panel_7.setBorder(new EmptyBorder(2, 10, 2, 5));
		panel_6.add(panel_7);
		panel_7.setLayout(new BoxLayout(panel_7, BoxLayout.X_AXIS));
		
		Component horizontalGlue_2 = Box.createHorizontalGlue();
		panel_7.add(horizontalGlue_2);
		
		JLabel lblUser = new JLabel("Sors user");
		panel_7.add(lblUser);
		
		Component horizontalGlue_3 = Box.createHorizontalGlue();
		panel_7.add(horizontalGlue_3);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setMaximumSize(new Dimension(3, 32767));
		separator_2.setOrientation(SwingConstants.VERTICAL);
		panel_6.add(separator_2);
		
		JPanel panel_8 = new JPanel();
		panel_8.setMaximumSize(new Dimension(32767, 40));
		panel_8.setPreferredSize(new Dimension(10, 40));
		panel_8.setBorder(new EmptyBorder(2, 10, 2, 5));
		panel_6.add(panel_8);
		panel_8.setLayout(new BoxLayout(panel_8, BoxLayout.X_AXIS));
		
		txtUser = new JTextField();
		panel_8.add(txtUser);
		txtUser.setColumns(10);
		
		JPanel panel_9 = new JPanel();
		add(panel_9);
		panel_9.setLayout(new BoxLayout(panel_9, BoxLayout.X_AXIS));
		
		JPanel panel_10 = new JPanel();
		panel_10.setMaximumSize(new Dimension(150, 40));
		panel_10.setPreferredSize(new Dimension(150, 10));
		panel_10.setBorder(new EmptyBorder(2, 10, 2, 5));
		panel_9.add(panel_10);
		panel_10.setLayout(new BoxLayout(panel_10, BoxLayout.X_AXIS));
		
		Component horizontalGlue_6 = Box.createHorizontalGlue();
		panel_10.add(horizontalGlue_6);
		
		JLabel lblNewLabel_2 = new JLabel("Password");
		panel_10.add(lblNewLabel_2);
		
		Component horizontalGlue_7 = Box.createHorizontalGlue();
		panel_10.add(horizontalGlue_7);
		
		JSeparator separator_3 = new JSeparator();
		separator_3.setMaximumSize(new Dimension(3, 32767));
		separator_3.setOrientation(SwingConstants.VERTICAL);
		panel_9.add(separator_3);
		
		JPanel panel_11 = new JPanel();
		panel_11.setMaximumSize(new Dimension(32767, 40));
		panel_11.setPreferredSize(new Dimension(10, 40));
		panel_11.setBorder(new EmptyBorder(2, 10, 2, 5));
		panel_9.add(panel_11);
		panel_11.setLayout(new BoxLayout(panel_11, BoxLayout.X_AXIS));
		
		txtPass = new JPasswordField();
		panel_11.add(txtPass);
		
		JSeparator separator_4 = new JSeparator();
		add(separator_4);
		
		JPanel panel_12 = new JPanel();
		add(panel_12);
		panel_12.setLayout(new BoxLayout(panel_12, BoxLayout.Y_AXIS));
		
		Component verticalGlue = Box.createVerticalGlue();
		panel_12.add(verticalGlue);
		
		JPanel panel_13 = new JPanel();
		panel_13.setBorder(new CompoundBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), new EmptyBorder(2, 10, 2, 5)));
		panel_13.setPreferredSize(new Dimension(10, 40));
		panel_13.setMaximumSize(new Dimension(32767, 40));
		panel_12.add(panel_13);
		panel_13.setLayout(new BoxLayout(panel_13, BoxLayout.X_AXIS));
		
		JLabel lblNewLabel_3 = new JLabel("server name");
		panel_13.add(lblNewLabel_3);
		
		Component horizontalStrut = Box.createHorizontalStrut(5);
		panel_13.add(horizontalStrut);
		
		txtServerName = new JTextField();
		txtServerName.setMinimumSize(new Dimension(200, 19));
		txtServerName.setMaximumSize(new Dimension(250, 2147483647));
		txtServerName.setPreferredSize(new Dimension(200, 40));
		panel_13.add(txtServerName);
		txtServerName.setColumns(10);
		
		Component horizontalGlue_8 = Box.createHorizontalGlue();
		panel_13.add(horizontalGlue_8);
		
		btnConnect = new JButton("Connect");
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (WebConnection.isRun()) {
					WebConnection.getInstance().closeConnection();
					setActive(true);
				} else {
					LoadInfo.setNodeName(txtNodeName.getText());
					LoadInfo.setDesc(txtAreaNodeDesc.getText());
					WebConnection.setUserName(txtUser.getText());
					WebConnection.setPassword(new String(txtPass.getPassword()));
					WebConnection.setServerName(txtServerName.getText());
					Thread th = new Thread(WebConnection.getInstance());
					th.start();
					setActive(false);
				}
			}
		});
		btnConnect.setMargin(new Insets(2, 5, 2, 5));
		panel_13.add(btnConnect);

	}
	public void init_2() {
		txtNodeName.setText(LoadInfo.getNodeName());
		txtAreaNodeDesc.setText(LoadInfo.getDesc());
		String userName = WebConnection.getUserName();
		String password = WebConnection.getPassword();
		if (userName!=null) {
			txtUser.setText(userName);
		}
		if (password!=null) {
			txtPass.setText(password);
		}
		
		setActive(!WebConnection.isRun());
		
		
	}
	private void setActive(boolean state) {
		txtNodeName.setEditable(state);
		txtAreaNodeDesc.setEditable(state);
		txtPass.setEditable(state);
		txtServerName.setEditable(state);
		txtUser.setEditable(state);
		if (state) {
			lblStatus.setText("not connected");
			btnConnect.setText("connect");
		} else {
			lblStatus.setText("Connected to "+txtServerName.getText());
			btnConnect.setText("disconnect");
		}
	}
}
