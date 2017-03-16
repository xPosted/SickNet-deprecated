package com.jubaka.sors.tcpAnalyse;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;

import com.jubaka.sors.factories.ClassFactory;
import com.jubaka.sors.sessions.SesCaptureInfo;
import com.jubaka.sors.sessions.SessionsAPI;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.JSeparator;
import javax.swing.border.MatteBorder;

import java.awt.Color;

import javax.swing.border.CompoundBorder;

import java.awt.Font;

import javax.swing.ImageIcon;

import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

import javax.swing.JScrollPane;

import java.awt.Insets;

public class DataCatch extends JFrame {
	private  Integer id;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	JList netSelectList = new JList();
	JList ipSelectList = new JList();
	DefaultListModel<String> ipModel = new DefaultListModel<String>();
	JComboBox comboNetSelect = new JComboBox();
	static JLabel lbldataPath = new JLabel("Home directory is't set");
	SesCaptureInfo currentSAI = null;
	JCheckBox checkInput1 = new JCheckBox("any port");
	JCheckBox checkInput2 = new JCheckBox("only for ports");
	JCheckBox checkOutput1 = new JCheckBox("any port");
	JCheckBox checkOutput2 = new JCheckBox("only for ports");
	private JCheckBox chckbxNewCheckBox;
	JPanel panel_11;
	private SessionsAPI sesApi;
	private ButtonGroup groupInput;
	private ButtonGroup groupOutput;
	private JPanel pnlMain;
	private JPanel pnlAddToList;

	/**
	 * Launch the application.
	 */
	

	/**
	 * Create the application.
	 */
	public DataCatch(Integer id) {
		this.id=id;
		sesApi= ClassFactory.getInstance().getSesionInstance(id);
		initialize();
		myInit();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		setBounds(100, 100, 600, 330);
		setLocationRelativeTo(null);
		setTitle("Session data capture customizing");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel panel = new JPanel();
		panel.setEnabled(false);
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		pnlAddToList = new JPanel();
		pnlAddToList.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		pnlAddToList.setMaximumSize(new Dimension(32767, 40));
		panel.add(pnlAddToList);
		pnlAddToList.setLayout(new BoxLayout(pnlAddToList, BoxLayout.X_AXIS));

		Component horizontalStrut_8 = Box.createHorizontalStrut(20);
		pnlAddToList.add(horizontalStrut_8);

		JLabel lblNewLabel_2 = new JLabel("Network");
		pnlAddToList.add(lblNewLabel_2);

		comboNetSelect.setPreferredSize(new Dimension(150, 24));
		comboNetSelect.setSize(new Dimension(20, 0));
		comboNetSelect.setMinimumSize(new Dimension(40, 24));
		pnlAddToList.add(comboNetSelect);

		Component horizontalStrut_9 = Box.createHorizontalStrut(20);
		pnlAddToList.add(horizontalStrut_9);

		JLabel lblIp = new JLabel("ip");
		pnlAddToList.add(lblIp);

		textField_2 = new JTextField();
		textField_2.setPreferredSize(new Dimension(20, 19));
		pnlAddToList.add(textField_2);
		textField_2.setColumns(10);

		Component horizontalStrut_10 = Box.createHorizontalStrut(20);
		pnlAddToList.add(horizontalStrut_10);

		JButton btnNewButton_3 = new JButton("add to list");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String ip = textField_2.getText();
				String net = (String) comboNetSelect.getSelectedItem();
				try {
					boolean res = sesApi.addIP(net, ip);
					if (res == false) {
						lbldataPath
								.setText("IP address can't be added to net - "
										+ net);
						JOptionPane.showMessageDialog(null,
								"IP address can't be added to network " + net);
					}
					netSelectList.clearSelection();
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		pnlAddToList.add(btnNewButton_3);

		JSeparator separator_2 = new JSeparator();
		panel.add(separator_2);

		pnlMain = new JPanel();
		pnlMain.setBorder(new CompoundBorder(new EtchedBorder(
				EtchedBorder.LOWERED, null, null), new MatteBorder(2, 1, 1, 1,
				(Color) new Color(128, 128, 128))));
		panel.add(pnlMain);
		pnlMain.setLayout(new BoxLayout(pnlMain, BoxLayout.X_AXIS));

		JPanel panel_24 = new JPanel();
		panel_24.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		pnlMain.add(panel_24);
		panel_24.setLayout(new BoxLayout(panel_24, BoxLayout.Y_AXIS));

		JPanel panel_25 = new JPanel();
		panel_24.add(panel_25);
		panel_25.setLayout(new BoxLayout(panel_25, BoxLayout.X_AXIS));

		Component horizontalGlue_9 = Box.createHorizontalGlue();
		panel_25.add(horizontalGlue_9);

		JLabel lblNewLabel_3 = new JLabel("Networks");
		panel_25.add(lblNewLabel_3);

		Component horizontalGlue_10 = Box.createHorizontalGlue();
		panel_25.add(horizontalGlue_10);

		JSeparator separator = new JSeparator();
		separator.setMaximumSize(new Dimension(32767, 2));
		panel_24.add(separator);

		JPanel panel_3 = new JPanel();
		panel_24.add(panel_3);
		panel_3.setPreferredSize(new Dimension(150, 10));
		panel_3.setMinimumSize(new Dimension(150, 10));
		panel_3.setMaximumSize(new Dimension(250, 32767));
		panel_3.setLayout(new BorderLayout(0, 0));
		netSelectList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				DefaultListModel<String> netModel = (DefaultListModel<String>) netSelectList
						.getModel();
				Integer index = netSelectList.getSelectedIndex();
				String net = netModel.get(index);
				try {
					Set<String> ips = sesApi.getIPlist(net);
					ipModel.clear();
					for (String item : ips) {
						ipModel.addElement(item);
					}

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});

		panel_3.add(netSelectList);

		JPanel panel_4 = new JPanel();
		pnlMain.add(panel_4);
		panel_4.setLayout(new BoxLayout(panel_4, BoxLayout.X_AXIS));

		JPanel panel_5 = new JPanel();
		panel_4.add(panel_5);
		panel_5.setLayout(new BoxLayout(panel_5, BoxLayout.Y_AXIS));

		JPanel panel_7 = new JPanel();
		panel_7.setMaximumSize(new Dimension(200, 35));
		panel_5.add(panel_7);
		panel_7.setLayout(new BoxLayout(panel_7, BoxLayout.X_AXIS));

		JPanel panel_8 = new JPanel();
		panel_8.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_8.setMaximumSize(new Dimension(200, 32767));
		panel_8.setPreferredSize(new Dimension(200, 10));
		panel_5.add(panel_8);
		panel_8.setLayout(new BoxLayout(panel_8, BoxLayout.Y_AXIS));

		Component verticalStrut = Box.createVerticalStrut(10);
		panel_8.add(verticalStrut);

		JPanel panel_26 = new JPanel();
		panel_8.add(panel_26);
		panel_26.setLayout(new BoxLayout(panel_26, BoxLayout.X_AXIS));

		Component horizontalGlue_11 = Box.createHorizontalGlue();
		panel_26.add(horizontalGlue_11);

		JLabel lblNewLabel_4 = new JLabel("IP adresses");
		panel_26.add(lblNewLabel_4);

		Component horizontalGlue_12 = Box.createHorizontalGlue();
		panel_26.add(horizontalGlue_12);

		JSeparator separator_1 = new JSeparator();
		panel_8.add(separator_1);

		JPanel panel_9 = new JPanel();
		panel_8.add(panel_9);
		panel_9.setLayout(new BorderLayout(0, 0));
		ipSelectList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				DefaultListModel<String> model = (DefaultListModel<String>) ipSelectList
						.getModel();
				String ip = model.get(ipSelectList.getSelectedIndex());
				try {
					InetAddress ipAddr = InetAddress.getByName(ip);
					currentSAI = ClassFactory.getInstance().getDataSaverInfo(id).getCatchInfo(
							ipAddr);
					initDataPanels();
					Component[] comps = getComponents(panel_11);
					EnableSwitch(true, comps);

				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});

		ipSelectList.setModel(ipModel);
	//	panel_9.add(ipSelectList, BorderLayout.CENTER);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(ipSelectList);
		panel_9.add(scrollPane, BorderLayout.CENTER);

		panel_11 = new JPanel();
		panel_4.add(panel_11);
		panel_11.setLayout(new BoxLayout(panel_11, BoxLayout.Y_AXIS));

		JPanel panel_6 = new JPanel();
		panel_6.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_11.add(panel_6);
		panel_6.setLayout(new BoxLayout(panel_6, BoxLayout.Y_AXIS));

		JPanel panel_12 = new JPanel();
		panel_12.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null,
				null, null));
		panel_6.add(panel_12);
		panel_12.setLayout(new BoxLayout(panel_12, BoxLayout.X_AXIS));

		Component horizontalGlue_1 = Box.createHorizontalGlue();
		panel_12.add(horizontalGlue_1);

		JLabel lblNewLabel = new JLabel("Incoming Sessions");
		panel_12.add(lblNewLabel);

		Component horizontalGlue_2 = Box.createHorizontalGlue();
		panel_12.add(horizontalGlue_2);

		JPanel panel_13 = new JPanel();
		panel_13.setMaximumSize(new Dimension(32767, 35));
		panel_6.add(panel_13);
		panel_13.setLayout(new BoxLayout(panel_13, BoxLayout.X_AXIS));

		Component horizontalStrut_2 = Box.createHorizontalStrut(20);
		panel_13.add(horizontalStrut_2);
		groupInput = new ButtonGroup();

		checkInput1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textField.setEnabled(false);
			}
		});
		groupInput.add(checkInput1);
		panel_13.add(checkInput1);

		Component horizontalGlue_6 = Box.createHorizontalGlue();
		panel_13.add(horizontalGlue_6);

		JPanel panel_14 = new JPanel();
		panel_6.add(panel_14);
		panel_14.setLayout(new BoxLayout(panel_14, BoxLayout.Y_AXIS));

		JPanel panel_15 = new JPanel();
		panel_15.setMaximumSize(new Dimension(32767, 35));
		panel_14.add(panel_15);
		panel_15.setLayout(new BoxLayout(panel_15, BoxLayout.X_AXIS));

		Component horizontalStrut_3 = Box.createHorizontalStrut(20);
		panel_15.add(horizontalStrut_3);

		textField = new JTextField();

		checkInput2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textField.setEnabled(true);
			}
		});
		groupInput.add(checkInput2);
		panel_15.add(checkInput2);

		Component horizontalGlue_8 = Box.createHorizontalGlue();
		panel_15.add(horizontalGlue_8);

		JPanel panel_16 = new JPanel();
		panel_16.setMaximumSize(new Dimension(32767, 25));
		panel_16.setPreferredSize(new Dimension(10, 25));
		panel_14.add(panel_16);
		panel_16.setLayout(new BoxLayout(panel_16, BoxLayout.X_AXIS));

		Component horizontalStrut_4 = Box.createHorizontalStrut(35);
		panel_16.add(horizontalStrut_4);

		panel_16.add(textField);
		textField.setColumns(10);

		JPanel panel_10 = new JPanel();
		panel_10.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_11.add(panel_10);
		panel_10.setLayout(new BoxLayout(panel_10, BoxLayout.Y_AXIS));

		JPanel panel_17 = new JPanel();
		panel_17.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null,
				null, null));
		panel_10.add(panel_17);
		panel_17.setLayout(new BoxLayout(panel_17, BoxLayout.X_AXIS));

		Component horizontalGlue_3 = Box.createHorizontalGlue();
		panel_17.add(horizontalGlue_3);

		JLabel lblNewLabel_1 = new JLabel("Output Sessions");
		panel_17.add(lblNewLabel_1);

		Component horizontalGlue_4 = Box.createHorizontalGlue();
		panel_17.add(horizontalGlue_4);

		JPanel panel_18 = new JPanel();
		panel_18.setMaximumSize(new Dimension(32767, 35));
		panel_10.add(panel_18);
		panel_18.setLayout(new BoxLayout(panel_18, BoxLayout.X_AXIS));

		Component horizontalStrut_5 = Box.createHorizontalStrut(20);
		panel_18.add(horizontalStrut_5);
		 groupOutput = new ButtonGroup();

		checkOutput1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textField_1.setEnabled(false);
			}
		});
		groupOutput.add(checkOutput1);
		panel_18.add(checkOutput1);

		Component horizontalGlue_7 = Box.createHorizontalGlue();
		panel_18.add(horizontalGlue_7);

		JPanel panel_19 = new JPanel();
		panel_10.add(panel_19);
		panel_19.setLayout(new BoxLayout(panel_19, BoxLayout.Y_AXIS));

		JPanel panel_20 = new JPanel();
		panel_20.setMaximumSize(new Dimension(32767, 35));
		panel_19.add(panel_20);
		panel_20.setLayout(new BoxLayout(panel_20, BoxLayout.X_AXIS));

		Component horizontalStrut_6 = Box.createHorizontalStrut(20);
		panel_20.add(horizontalStrut_6);

		checkOutput2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textField_1.setEnabled(true);
			}
		});
		groupOutput.add(checkOutput2);
		panel_20.add(checkOutput2);

		Component horizontalGlue_5 = Box.createHorizontalGlue();
		panel_20.add(horizontalGlue_5);

		JPanel panel_21 = new JPanel();
		panel_21.setPreferredSize(new Dimension(10, 25));
		panel_21.setMaximumSize(new Dimension(32767, 30));
		panel_19.add(panel_21);
		panel_21.setLayout(new BoxLayout(panel_21, BoxLayout.X_AXIS));

		Component horizontalStrut_7 = Box.createHorizontalStrut(35);
		panel_21.add(horizontalStrut_7);

		textField_1 = new JTextField();
		panel_21.add(textField_1);
		textField_1.setColumns(10);

		Component verticalGlue = Box.createVerticalGlue();
		panel_11.add(verticalGlue);

		JPanel panel_22 = new JPanel();
		panel_22.setMaximumSize(new Dimension(32767, 35));
		panel_11.add(panel_22);
		panel_22.setLayout(new BorderLayout(0, 0));

		JButton btnNewButton_2 = new JButton("save changes");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DefaultListModel<String> model = (DefaultListModel<String>) ipSelectList
						.getModel();
				String ip = model.get(ipSelectList.getSelectedIndex());
				InetAddress ipAddr = null;
				try {
					ipAddr = InetAddress.getByName(ip);
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String in="";
				String out="";
				if (checkInput1.isSelected())
					in = "-1";
				if (checkInput2.isSelected())
					in = textField.getText();
				
				if (checkOutput1.isSelected())
					out = "-1";
				if (checkOutput2.isSelected())
					out = textField_1.getText();
				
				ClassFactory.getInstance().getDataSaverInfo(id).addItem(ipAddr, in, out);

			}
		});
		panel_22.add(btnNewButton_2, BorderLayout.CENTER);

		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_2.setMaximumSize(new Dimension(32767, 35));
		panel.add(panel_2);
		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.X_AXIS));

		Component horizontalStrut_11 = Box.createHorizontalStrut(10);
		panel_2.add(horizontalStrut_11);

		JButton btnNewButton = new JButton("");
		btnNewButton.setMinimumSize(new Dimension(40, 10));
		btnNewButton.setMaximumSize(new Dimension(45, 25));
		btnNewButton.setIcon(new ImageIcon(DataCatch.class
				.getResource("/tcpAnalyse/change2_1.jpg")));
		btnNewButton.setFont(new Font("Dialog", Font.PLAIN, 10));
		btnNewButton.setPreferredSize(new Dimension(40, 25));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
					
					Thread th = new Thread(new Runnable() {
						
						@Override
						public void run() {
							try {
							Settings set = new Settings();
							set.setVisible(true);
							
							Settings.opened.acquire();
							Settings.opened.release();
				
							myInit();
							} catch (Exception e) {
								e.printStackTrace();
							}
							
						}
					});
					th.start();
					
			}
		});
		
		JButton btnRecover = new JButton("recover");
		btnRecover.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Thread th = new Thread(new Runnable() {
					
					@Override
					public void run() {
						ClassFactory.getInstance().getSesionInstance(id).dataRecovering();
						
					}
				});
				th.start();
				
				
			}
		});
		btnRecover.setMargin(new Insets(2, 5, 2, 5));
		panel_2.add(btnRecover);
		
		Component horizontalStrut_13 = Box.createHorizontalStrut(10);
		panel_2.add(horizontalStrut_13);
		
		JButton btnNewButton_1 = new JButton("New button");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				
			}
		});
		panel_2.add(btnNewButton_1);
		panel_2.add(btnNewButton);

		Component horizontalStrut_12 = Box.createHorizontalStrut(5);
		panel_2.add(horizontalStrut_12);

		JLabel lblNewLabel_6 = new JLabel("data path:");
		lblNewLabel_6.setFont(new Font("Dialog", Font.PLAIN, 12));
		panel_2.add(lblNewLabel_6);
		lbldataPath.setToolTipText("Path where session data store");

		panel_2.add(lbldataPath);

		Component horizontalGlue = Box.createHorizontalGlue();
		panel_2.add(horizontalGlue);

		Component horizontalStrut = Box.createHorizontalStrut(15);
		panel_2.add(horizontalStrut);
		
		chckbxNewCheckBox = new JCheckBox("catch ALL sessions");
		chckbxNewCheckBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
			
				if (chckbxNewCheckBox.isSelected()) {
					ClassFactory.getInstance().getDataSaverInfo(id).catchAll();
					
				} else ClassFactory.getInstance().getDataSaverInfo(id).catchSelcted();
			}
		});
		if (ClassFactory.getInstance().getDataSaverInfo(id).isCatchAll()) {
			chckbxNewCheckBox.setSelected(true);
		}
		panel_2.add(chckbxNewCheckBox);

		Component horizontalStrut_1 = Box.createHorizontalStrut(10);
		panel_2.add(horizontalStrut_1);
		


	}
	
	public void myInit() {
		String dataPath = ClassFactory.getInstance().getRawDataPath(id);
		if (dataPath==null) {
			Component[] comps = getComponents(pnlMain);
			EnableSwitch(false, comps);
			comps = getComponents(pnlAddToList);
			EnableSwitch(false, comps);
			chckbxNewCheckBox.setEnabled(false);
			JOptionPane.showMessageDialog(null, "Home directory is't available, set home directory at first!");
		
		} else {
			lbldataPath.setText(dataPath);
			Component[] comps = getComponents(pnlMain);
			EnableSwitch(true, comps);
			comps = getComponents(pnlAddToList);
			EnableSwitch(true, comps);
			chckbxNewCheckBox.setEnabled(true);
		}
		initNetList();
	}
	
	private void EnableSwitch(boolean enable, Component[] comps) {
		for (Component item : comps) {
			item.setEnabled(enable);
		}

	}

	private Component[] getComponents(Component container) {
		ArrayList<Component> list = null;

		try {

			list = new ArrayList<Component>(
					Arrays.asList(((java.awt.Container) container)
							.getComponents()));
			for (int index = 0; index < list.size(); index++) {
				for (Component currentComponent : getComponents(list.get(index))) {
					list.add(currentComponent);
				}
			}
		} catch (ClassCastException e) {
			list = new ArrayList<Component>();
		}

		return list.toArray(new Component[list.size()]);
	}

	public void initDataPanels() {
		groupInput.clearSelection();
		groupOutput.clearSelection();
		
		textField.setText("");
		textField_1.setText("");
		
		if (currentSAI == null) {
			System.out.println("sai = null");
			return;
					
		}
			
		HashSet<Integer> inPorts = currentSAI.getInPorts();
		HashSet<Integer> outPorts = currentSAI.getOutPorts();
		System.out.println("in_out = "+inPorts+" " +outPorts);
		if (inPorts.size()!=0) {
			if (inPorts.contains(-1))
				checkInput1.setSelected(true);
			else {
				String res = "";
				for (Integer item : inPorts) {
					res = res + item + ",";
				}
				checkInput2.setSelected(true);
				textField.setText(res);
			}
		}
		if (outPorts.size()!=0) {
			if (outPorts.contains(-1))
				checkOutput1.setSelected(true);
			else {
				String res = "";
				for (Integer item : outPorts) {
					res = res + item + ",";
				}
				checkOutput2.setSelected(true);
				textField_1.setText(res);
			}
		}
	}

	public void initNetList() {

		List<String> netList = sesApi.getNetList();
		DefaultListModel<String> netModel = new DefaultListModel<String>();
		for (String item : netList) {
			netModel.addElement(item);
		}
		netSelectList.setModel(netModel);

		DefaultComboBoxModel<String> comboModel = new DefaultComboBoxModel<String>();
		for (String item : netList)
			comboModel.addElement(item);
		comboNetSelect.setModel(comboModel);

	}

}
