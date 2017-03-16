package com.jubaka.sors.tcpAnalyse;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.BoxLayout;
import javax.swing.JSeparator;

import java.awt.Dimension;

import javax.swing.JButton;

import java.awt.Component;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JRadioButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListModel;
import javax.swing.SwingConstants;

import java.awt.GridLayout;

import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.UIManager;

import java.awt.Color;

import javax.swing.border.LineBorder;
import javax.swing.JTextField;

import java.awt.Insets;

import javax.swing.border.EtchedBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;

import com.jubaka.sors.sessions.API;
import org.jnetpcap.PcapAddr;
import org.jnetpcap.PcapIf;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.HashSet;
import java.util.List;

import javax.swing.ListSelectionModel;

import java.awt.Font;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.JScrollPane;

public class BranchCreateForm extends JFrame {

	private JPanel contentPane;
	private final JPanel panel = new JPanel();
	private final JPanel panel_1 = new JPanel();
	private final JList list = new JList();
	private final JTextArea textArea = new JTextArea();
	private JTextField textField;
	private JPanel panelLive;
	private JPanel panelFile;
	private JRadioButton radioButLive;
	private JRadioButton radioButFile;
	private List<PcapIf> ifs;
	private JFrame inst;
	private JButton btnCreate;
	public static int selectFile = 0;
	public static int selectLive = 1;
	
	
	/**
	 * Create the frame.
	 */
	public BranchCreateForm(Integer select) {
		inst = this;
		setTitle("Create branch");
		ButtonGroup bg = new ButtonGroup();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(panel_1);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.Y_AXIS));

		panelLive = new JPanel();
		panelLive.setBorder(UIManager.getBorder("Spinner.border"));
		panel_1.add(panelLive);
		panelLive.setLayout(new BoxLayout(panelLive, BoxLayout.X_AXIS));

		radioButLive = new JRadioButton("");
		radioButLive.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				HashSet<Component> compsFilePanel = Controller.getComponents(
						panelFile, null);
				HashSet<Component> compsLivePanel = Controller.getComponents(
						panelLive, null);
				Controller.EnableSwitch(false, compsFilePanel);
				Controller.EnableSwitch(true, compsLivePanel);
				radioButFile.setEnabled(true);

			}
		});
		panelLive.add(radioButLive);
		bg.add(radioButLive);

		JPanel panel_5 = new JPanel();
		panel_5.setPreferredSize(new Dimension(150, 10));
		panel_5.setBorder(new EmptyBorder(5, 5, 5, 5));
		panelLive.add(panel_5);
		panel_5.setLayout(new BoxLayout(panel_5, BoxLayout.Y_AXIS));

		JPanel panel_7 = new JPanel();
		panel_7.setMaximumSize(new Dimension(32767, 25));
		panel_7.setPreferredSize(new Dimension(10, 25));
		panel_5.add(panel_7);

		JLabel lblInterfaces = new JLabel("Interfaces");
		panel_7.add(lblInterfaces);
		lblInterfaces.setHorizontalTextPosition(SwingConstants.CENTER);
		lblInterfaces.setHorizontalAlignment(SwingConstants.CENTER);

		JPanel panel_6 = new JPanel();
		panel_6.setBorder(new LineBorder(new Color(128, 128, 128), 2, true));
		panel_5.add(panel_6);
		panel_6.setLayout(new BorderLayout(0, 0));
	//	renderer.setHorizontalAlignment(JLabel.CENTER);
		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				if (list.getValueIsAdjusting()) return;
				if ((list.getModel() instanceof DefaultListModel) == false) return;
				DefaultListModel<String> model = (DefaultListModel<String>) list
						.getModel();
				if (list.getSelectedIndex() == -1) return;
				String selectedIf = model.get(list.getSelectedIndex());
				for (PcapIf ifItem : ifs) {
					if (ifItem.getName().equals(selectedIf)) {
						StringBuilder sb = new StringBuilder();

						sb.append("Name:\t" + selectedIf+"\n");
						if (ifItem.getDescription() != null)
							sb.append("Description:\t"
									+ ifItem.getDescription()+"\n");
						if (ifItem.getAddresses() != null) {
							List<PcapAddr> addrs = ifItem.getAddresses();
							for (PcapAddr addr : addrs) {
								try {
									InetAddress inetAddr = InetAddress.getByAddress(addr.getAddr().getData());
									InetAddress netmask = InetAddress.getByAddress(addr.getNetmask().getData());
									sb.append("Address:\t"+inetAddr.getHostAddress()+"\n");
									sb.append("Netmask:\t"+netmask.getHostAddress()+"\n\n");
									
								} catch (UnknownHostException e) {
									// TODO: handle exception
								}
								
							}
							
							}
						try {
							if (ifItem.getHardwareAddress() != null) {
								byte[] mac = ifItem.getHardwareAddress();
								String oct0 = String.format("%02x", mac[0]);
								String oct1 = String.format("%02x", mac[1]);
								String oct2 = String.format("%02x", mac[2]);
								String oct3 = String.format("%02x", mac[3]);
								String oct4 = String.format("%02x", mac[4]);
								String oct5 = String.format("%02x", mac[5]);
								String macStr = oct0+":"+oct1+":"+oct2+":"+oct3+":"+oct4+":"+oct5;
									System.out.println(macStr+"\n");

								sb.append("HW address:\t"
										+ macStr);
							}
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						textArea.setText(sb.toString());
						btnCreate.setEnabled(true);
						
					}
				}
				
			}
		});
		
		list.setFont(new Font("Dialog", Font.BOLD, 14));
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		DefaultListCellRenderer renderer = (DefaultListCellRenderer) list
				.getCellRenderer();
		
			//	panel_6.add(list, BorderLayout.CENTER);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(list);
		panel_6.add(scrollPane, BorderLayout.CENTER);
		

		JPanel panel_8 = new JPanel();
		panel_8.setPreferredSize(new Dimension(250, 10));
		panel_8.setBorder(new EmptyBorder(30, 5, 5, 5));
		panelLive.add(panel_8);
		panel_8.setLayout(new BorderLayout(0, 0));

		JPanel panel_9 = new JPanel();
		panel_9.setVisible(false);
		panel_9.setBorder(new EmptyBorder(5, 5, 5, 5));
		panel_8.add(panel_9, BorderLayout.NORTH);
		panel_9.setLayout(new BoxLayout(panel_9, BoxLayout.X_AXIS));

		Component horizontalGlue_1 = Box.createHorizontalGlue();
		panel_9.add(horizontalGlue_1);

		JLabel lblNewLabel = new JLabel("Properties");
		panel_9.add(lblNewLabel);

		Component horizontalGlue_2 = Box.createHorizontalGlue();
		panel_9.add(horizontalGlue_2);

		JPanel panel_10 = new JPanel();
		panel_10.setBorder(new LineBorder(new Color(128, 128, 128), 1, true));
		panel_8.add(panel_10, BorderLayout.CENTER);
		panel_10.setLayout(new BorderLayout(0, 0));
		textArea.setBackground(new Color(255, 255, 255));
		textArea.setEditable(false);
		panel_10.add(textArea, BorderLayout.CENTER);

		JPanel panel_4 = new JPanel();
		panel_1.add(panel_4);

		panelFile = new JPanel();
		panelFile.setBorder(UIManager.getBorder("Spinner.border"));
		panel.add(panelFile);
		panelFile.setLayout(new BoxLayout(panelFile, BoxLayout.X_AXIS));

		radioButFile = new JRadioButton("");
		radioButFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				HashSet<Component> compsFilePanel = Controller.getComponents(
						panelFile, null);
				HashSet<Component> compsLivePanel = Controller.getComponents(
						panelLive, null);
				Controller.EnableSwitch(true, compsFilePanel);
				Controller.EnableSwitch(false, compsLivePanel);
				radioButLive.setEnabled(true);
			}
		});
		panelFile.add(radioButFile);
		bg.add(radioButFile);

		JPanel panel_12 = new JPanel();
		panel_12.setBorder(new EmptyBorder(7, 5, 7, 5));
		panel_12.setPreferredSize(new Dimension(10, 45));
		panel_12.setMaximumSize(new Dimension(32767, 45));
		panelFile.add(panel_12);
		panel_12.setLayout(new BoxLayout(panel_12, BoxLayout.X_AXIS));

		textField = new JTextField();
		textField.setEditable(false);
		panel_12.add(textField);
		textField.setColumns(10);

		Component horizontalStrut_1 = Box.createHorizontalStrut(15);
		panel_12.add(horizontalStrut_1);

		JButton btnNewButton_2 = new JButton("Browse");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setMultiSelectionEnabled(false);
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				fileChooser.showOpenDialog(null);
				textField.setText(fileChooser.getSelectedFile()
						.getAbsolutePath());
				btnCreate.setEnabled(true);

			}
		});
		btnNewButton_2.setMargin(new Insets(2, 5, 2, 5));
		panel_12.add(btnNewButton_2);

		JSeparator separator = new JSeparator();
		separator.setMaximumSize(new Dimension(32767, 3));
		panel.add(separator);

		JPanel panel_2 = new JPanel();
		panel_2.setMaximumSize(new Dimension(32767, 50));
		panel_2.setBorder(new EmptyBorder(5, 5, 5, 5));
		panel.add(panel_2);
		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.X_AXIS));

		Component horizontalGlue = Box.createHorizontalGlue();
		panel_2.add(horizontalGlue);

		JButton btnNewButton = new JButton("cancel");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				inst.dispose();
				
			}
		});
		panel_2.add(btnNewButton);

		Component horizontalStrut = Box.createHorizontalStrut(10);
		panel_2.add(horizontalStrut);

		btnCreate = new JButton("CREATE");
		btnCreate.setEnabled(false);
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (radioButLive.isSelected()) {
					ListModel<String> model = (ListModel<String>) list.getModel();
					String ifs = model.getElementAt(list.getSelectedIndex());
					Controller.createLocalBranch(null, ifs, null);
				}
				if (radioButFile.isSelected()) {
					String fileName = textField.getText();
					Controller.createLocalBranch(null, null, fileName);
				}
				inst.dispose();
				
			}
		});
		panel_2.add(btnCreate);

		// /
		JRadioButton selected = radioButLive;
		if (select == 0) selected = radioButFile;
		
		selected.setSelected(true);
		for (ActionListener al : selected.getActionListeners())
			al.actionPerformed(null);
		initInterfaceList();
	}

	public void initInterfaceList() {
		DefaultListModel<String> listModel = new DefaultListModel<String>();

		ifs = API.getDeviceList();
		for (PcapIf item : ifs) {
			listModel.addElement(item.getName());
		}

		list.setModel(listModel);
	}
}