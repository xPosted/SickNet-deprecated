package com.jubaka.sors.desktop.tcpAnalyse;

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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import com.jubaka.sors.desktop.factories.ClassFactory;
import com.jubaka.sors.desktop.myTreeView.FileTreeViewer;
import com.jubaka.sors.desktop.sessions.*;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
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

import javax.swing.JTree;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeSelectionEvent;

public class DataCatchView extends JFrame {
	private  Integer id;
	private JTextField textField;
	private JTextField textField_1;
	DefaultListModel<String> ipModel = new DefaultListModel<String>();
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
	private JTextField txtaddIP;
	private JTree tree;
	private JButton btnSet;
	private JButton btnDelete;
	private JButton btnRecover;

	/**
	 * Launch the application.
	 */
	

	/**
	 * Create the application.
	 */
	public DataCatchView(Integer id) {
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
		
		JPanel panel_3 = new JPanel();
		pnlAddToList.add(panel_3);
		panel_3.setLayout(new BoxLayout(panel_3, BoxLayout.X_AXIS));
		
		JLabel lblNewLabel_2 = new JLabel("Subnet");
		panel_3.add(lblNewLabel_2);
		
		Component horizontalStrut_10 = Box.createHorizontalStrut(5);
		panel_3.add(horizontalStrut_10);
		
		JLabel lblNewLabel_3 = new JLabel("[addr]");
		panel_3.add(lblNewLabel_3);
		
		Component horizontalStrut_9 = Box.createHorizontalStrut(20);
		panel_3.add(horizontalStrut_9);
		
		JLabel lblNewLabel_5 = new JLabel("IP");
		panel_3.add(lblNewLabel_5);
		
		Component horizontalStrut_15 = Box.createHorizontalStrut(5);
		panel_3.add(horizontalStrut_15);
		
		JLabel lblNewLabel_7 = new JLabel("[addr]");
		panel_3.add(lblNewLabel_7);
		
		Component horizontalStrut_8 = Box.createHorizontalStrut(20);
		pnlAddToList.add(horizontalStrut_8);
		
		Component horizontalGlue_9 = Box.createHorizontalGlue();
		pnlAddToList.add(horizontalGlue_9);
		
		JPanel panel_7 = new JPanel();
		pnlAddToList.add(panel_7);
		panel_7.setLayout(new BoxLayout(panel_7, BoxLayout.X_AXIS));
		
		btnSet = new JButton("Set");
		btnSet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				
				checkInput1.setSelected(true);
				checkOutput1.setSelected(true);
				Component[] comps = getComponents(panel_11);
				EnableSwitch(true, comps);
				
				
				
				
			}
		});
		panel_7.add(btnSet);
		
		btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Component[] comps = getComponents(panel_11);
				EnableSwitch(false, comps);
				DataSaverInfo dsi = ClassFactory.getInstance().getDataSaverInfo(id);
				DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
				if (selectedNode.getUserObject() instanceof Subnet) {
					Subnet net = (Subnet) selectedNode.getUserObject();
					dsi.removeItem(net);
				}
				if (selectedNode.getUserObject() instanceof IPaddr) {
					IPaddr ip = (IPaddr) selectedNode.getUserObject();
					dsi.removeItem(ip.getAddr());
				}
				
			}
		});
		panel_7.add(btnDelete);

		JSeparator separator_2 = new JSeparator();
		panel.add(separator_2);

		pnlMain = new JPanel();
		pnlMain.setBorder(new CompoundBorder(new EtchedBorder(
				EtchedBorder.LOWERED, null, null), new MatteBorder(2, 1, 1, 1,
				(Color) new Color(128, 128, 128))));
		panel.add(pnlMain);
		pnlMain.setLayout(new BorderLayout(0, 0));

		JPanel panel_4 = new JPanel();
		pnlMain.add(panel_4,BorderLayout.CENTER);
		panel_4.setLayout(new BoxLayout(panel_4, BoxLayout.X_AXIS));

		JPanel panel_5 = new JPanel();
		panel_4.add(panel_5);
		panel_5.setLayout(new BoxLayout(panel_5, BoxLayout.Y_AXIS));

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

		JLabel lblNewLabel_4 = new JLabel("Catch Identifiers");
		panel_26.add(lblNewLabel_4);
		
				Component horizontalGlue_12 = Box.createHorizontalGlue();
				panel_26.add(horizontalGlue_12);
		
		JButton button = new JButton("");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refresh();
			}
		});
		button.setPreferredSize(new Dimension(25, 25));
		button.setMargin(new Insets(0, 0, 0, 0));
		button.setIcon(new ImageIcon(DataCatchView.class.getResource("/refresh_27.jpg")));
		panel_26.add(button);
		
		Component horizontalStrut_16 = Box.createHorizontalStrut(5);
		panel_26.add(horizontalStrut_16);

		JSeparator separator_1 = new JSeparator();
		panel_8.add(separator_1);

		JPanel panel_9 = new JPanel();
		panel_8.add(panel_9);
		panel_9.setLayout(new BorderLayout(0, 0));
	//	panel_9.add(ipSelectList, BorderLayout.CENTER);
		
		JScrollPane scrollPane = new JScrollPane();
		panel_9.add(scrollPane, BorderLayout.CENTER);
		
		tree = new JTree();
		tree.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent arg0) {
				if (tree.getLastSelectedPathComponent()==null) return;
				DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
				
				if (selectedNode.getUserObject() instanceof Subnet) {
					Subnet net = (Subnet) selectedNode.getUserObject();
					currentSAI = ClassFactory.getInstance().getDataSaverInfo(id).getCatchInfo(
							net);
					
				}
				if (selectedNode.getUserObject() instanceof IPaddr) {
					IPaddr addr = (IPaddr) selectedNode.getUserObject();
					currentSAI = ClassFactory.getInstance().getDataSaverInfo(id).getCatchInfo(
							addr.getAddr());
					
				}
				if (currentSAI==null) {
					Component[] comps = getComponents(panel_11);
					EnableSwitch(false, comps);
					btnSet.setEnabled(true);
					btnDelete.setEnabled(false);
					
				} else {
					Component[] comps = getComponents(panel_11);
					EnableSwitch(true, comps);
					initDataPanels();
					btnSet.setEnabled(false);
					btnDelete.setEnabled(true);
				}
				
				
			}
		});
		scrollPane.setViewportView(tree);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new EmptyBorder(3, 5, 3, 5));
		panel_9.add(panel_1, BorderLayout.SOUTH);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));
		
		txtaddIP = new JTextField();
		panel_1.add(txtaddIP);
		txtaddIP.setColumns(10);
		
		Component horizontalStrut_14 = Box.createHorizontalStrut(7);
		panel_1.add(horizontalStrut_14);
		
		JButton btnNewButton_1 = new JButton("IP add");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ClassFactory cl = ClassFactory.getInstance();
				Branch br = cl.getBranch(id);
				String ip = txtaddIP.getText();
				InetAddress ia =null;
				try {
				ia = InetAddress.getByName(ip);
				} catch (Exception ee) {
					JOptionPane.showMessageDialog(null, "IP address are invalid!");
					return;
				}
				for (Subnet net : cl.getSesionInstance(id).getAllSubnets()) {
					if  (net.inSubnet(ia)) {
						IPaddr addr = new IPaddr(cl,br, ia, net);
						net.addIPmanualy(addr);
						refresh();
						return;
					}
				}
				Subnet defaultNet =  cl.getSesionInstance(id).getNotKnownNet();
				IPaddr addr = new IPaddr(cl,br, ia, defaultNet);
				defaultNet.addIPmanualy(addr);
				refresh();
				
			}
		});
		btnNewButton_1.setMargin(new Insets(2, 3, 2, 3));
		panel_1.add(btnNewButton_1);

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
				
				DataSaverInfo dsi = ClassFactory.getInstance().getDataSaverInfo(id);
				DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
				if (selectedNode.getUserObject() instanceof Subnet) {
					Subnet net = (Subnet) selectedNode.getUserObject();
					dsi.removeItem(net);
					dsi.addSubnet(net, in, out);
				}
				if (selectedNode.getUserObject() instanceof IPaddr) {
					IPaddr ip = (IPaddr) selectedNode.getUserObject();
					dsi.removeItem(ip.getAddr());
					dsi.addItem(ip.getAddr(), in, out);
				}

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
				.getResource("/change2_1.jpg")));
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
		
		btnRecover = new JButton("recover");
		btnRecover.setEnabled(false);
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
		
		JButton btnDri = new JButton("DRI");
		btnDri.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				DataRecoverInit dri = new DataRecoverInit(id);
				dri.setVisible(true);
			}
		});
		
		JButton btnTree = new JButton("tree");
		btnTree.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				FileTreeViewer ftv = new FileTreeViewer(ClassFactory.getInstance().getSesionInstance(id).getAllSubnets());
				ftv.setVisible(true);
			}
		});
		panel_2.add(btnTree);
		panel_2.add(btnDri);
		btnRecover.setMargin(new Insets(2, 5, 2, 5));
		panel_2.add(btnRecover);
		
		Component horizontalStrut_13 = Box.createHorizontalStrut(10);
		panel_2.add(horizontalStrut_13);
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
		Set<Subnet> nets = sesApi.getAllSubnets();
		DefaultMutableTreeNode top = new DefaultMutableTreeNode("Subnets");
		for (Subnet net : nets) {
			
			DefaultMutableTreeNode child = new DefaultMutableTreeNode(net);
			for (IPaddr ip : net.getIps()) {
				child.add(new DefaultMutableTreeNode(ip));
			}
			top.add(child);
		}
		tree.setModel(new DefaultTreeModel(top));
		String path = ClassFactory.getInstance().getBranchPath(id);
		if (path != null) {
			lbldataPath.setText(path);
			btnRecover.setEnabled(true);
		}
	}
	
	private void refresh() {
		ClassFactory cl = ClassFactory.getInstance();
		DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
		DefaultMutableTreeNode top = (DefaultMutableTreeNode) model.getRoot();
		DefaultMutableTreeNode child = (DefaultMutableTreeNode) top.getFirstChild();
		Set<IPaddr> currentAddrs = new HashSet<IPaddr>();
		while (child!=null) {
			Subnet net = (Subnet) child.getUserObject();
			Integer count = child.getChildCount();
			for (Integer i=0;i<count;i++) {
				DefaultMutableTreeNode subChild = (DefaultMutableTreeNode) child.getChildAt(i);
				IPaddr ip = (IPaddr) subChild.getUserObject();
				currentAddrs.add(ip);
			}
			Set<IPaddr> allAddrs = net.getIps();
			for (IPaddr item : allAddrs) {
				if (currentAddrs.contains(item)==false) {
					child.add(new DefaultMutableTreeNode(item));
				}
			}
			child = (DefaultMutableTreeNode) top.getChildAfter(child);
		}
		model.reload();
		SwingUtilities.updateComponentTreeUI(tree);
		
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

		DefaultComboBoxModel<String> comboModel = new DefaultComboBoxModel<String>();
		for (String item : netList)
			comboModel.addElement(item);

	}
}
