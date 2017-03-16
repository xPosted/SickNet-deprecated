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

import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JSeparator;
import javax.swing.JTextArea;

import java.awt.Font;

import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;

import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import com.jubaka.sors.desktop.factories.ClassFactory;
import com.jubaka.sors.desktop.sessions.Branch;
import com.jubaka.sors.desktop.sessions.SessionsAPI;

import javax.swing.JScrollPane;

import java.io.File;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTree;
import javax.swing.JToggleButton;
import javax.swing.JSplitPane;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeSelectionEvent;

import java.awt.Insets;

import javax.swing.SwingConstants;

public class ThirdPartyView extends JFrame implements Observer {

	private JPanel contentPane;
	private JTable table;
	private DefaultTableModel tModel;
	private JButton alowRemote;
	private boolean alowRemoteFlag = false;
	private JLabel lblselObjName;
	private JButton btnBan;
	private boolean isBan=false;
	private JLabel totalSize;
	private JLabel totalReq;
	private JPanel panel_9;
	private JPanel panel_2;
	private JLabel lblPath;
	private Component horizontalGlue_16;
	private JTree tree;
	private JLabel lblFileName;
	private JToggleButton tglUser;
	private JToggleButton tglIP;
	private Object selectedObj;
	private JLabel lblObjType;
	private JLabel lblSubnetCountVal;
	private JLabel lblIPCountVal;
	private JLabel lblSessionsCountVal;
	private JLabel lblDataCaptureVal;
	private JLabel lblHomeUsedVal;


	public ThirdPartyView() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				
				deleteObserver();
			}
		});
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 770, 648);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		JPanel panel_1 = new JPanel();
		panel_1.setPreferredSize(new Dimension(10, 40));
		panel_1.setBorder(new CompoundBorder(new LineBorder(new Color(0, 0, 0), 1, true), new EtchedBorder(EtchedBorder.LOWERED, null, null)));
		panel_1.setMaximumSize(new Dimension(32767, 50));
		panel.add(panel_1);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));
		
		Component horizontalGlue_12 = Box.createHorizontalGlue();
		panel_1.add(horizontalGlue_12);
		
		JLabel lblNewLabel = new JLabel("Third-party");
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 24));
		panel_1.add(lblNewLabel);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		panel_1.add(horizontalStrut_1);
		
		JLabel lblNewLabel_1 = new JLabel("dump processing");
		lblNewLabel_1.setFont(new Font("Dialog", Font.BOLD, 18));
		panel_1.add(lblNewLabel_1);
		
		Component horizontalGlue_13 = Box.createHorizontalGlue();
		panel_1.add(horizontalGlue_13);
		
		JSeparator separator = new JSeparator();
		separator.setPreferredSize(new Dimension(0, 4));
		panel.add(separator);
		
		Component verticalStrut = Box.createVerticalStrut(20);
		panel.add(verticalStrut);
		
		JPanel panel_19 = new JPanel();
		panel.add(panel_19);
		panel_19.setLayout(new BoxLayout(panel_19, BoxLayout.X_AXIS));
		
		Component horizontalStrut_7 = Box.createHorizontalStrut(10);
		panel_19.add(horizontalStrut_7);
		
		JPanel panel_18 = new JPanel();
		panel_19.add(panel_18);
		panel_18.setLayout(new BoxLayout(panel_18, BoxLayout.Y_AXIS));
		
		panel_2 = new JPanel();
		panel_2.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_18.add(panel_2);
		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.X_AXIS));
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(null);
		panel_3.setPreferredSize(new Dimension(150, 10));
		panel_3.setMaximumSize(new Dimension(230, 32767));
				//panel_2.add(panel_3);
		panel_3.setLayout(new BoxLayout(panel_3, BoxLayout.Y_AXIS));
		
		JPanel panel_5 = new JPanel();
		panel_3.add(panel_5);
		panel_5.setLayout(new BoxLayout(panel_5, BoxLayout.X_AXIS));
		
		Component horizontalGlue = Box.createHorizontalGlue();
		panel_5.add(horizontalGlue);
		
		JLabel lblNewLabel_2 = new JLabel("WEB server IP");
		panel_5.add(lblNewLabel_2);
		
		Component horizontalGlue_1 = Box.createHorizontalGlue();
		panel_5.add(horizontalGlue_1);
		
		JPanel panel_6 = new JPanel();
		panel_6.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_3.add(panel_6);
		panel_6.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane_1 = new JScrollPane();
		panel_6.add(scrollPane_1, BorderLayout.CENTER);
		
		tree = new JTree();
		tree.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent arg0) {
				DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
				if (selectedNode == null) return;
				selectedObj = selectedNode.getUserObject();
				TModelBuilder modelBuilder = new TModelBuilder(selectedObj, table.getModel());
				modelBuilder.build();
				buildInfoAbout(selectedObj);
				 
					 
				
			}
		});
		scrollPane_1.setViewportView(tree);
		
		JPanel panel_21 = new JPanel();
		panel_21.setMaximumSize(new Dimension(32767, 25));
		panel_21.setPreferredSize(new Dimension(10, 25));
		panel_21.setMinimumSize(new Dimension(10, 25));
		panel_6.add(panel_21, BorderLayout.SOUTH);
		panel_21.setLayout(new BoxLayout(panel_21, BoxLayout.X_AXIS));
		
		tglIP = new JToggleButton("IP");
		tglIP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tree.getSelectionModel().clearSelection();
				tglUser.setSelected(false);
				buildByIP();
				
			}
		});
		tglIP.setSelected(true);
		tglIP.setMinimumSize(new Dimension(70, 25));
		tglIP.setMaximumSize(new Dimension(400, 25));
		tglIP.setPreferredSize(new Dimension(70, 25));
		panel_21.add(tglIP);
		
		tglUser = new JToggleButton("USER");
		tglUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tree.getSelectionModel().clearSelection();
				tglIP.setSelected(false);
				buildByUser();
			}
		});
		tglUser.setMaximumSize(new Dimension(400, 25));
		panel_21.add(tglUser);
		
		JSeparator separator_1 = new JSeparator();
		panel_2.add(separator_1);
		
		JPanel panel_4 = new JPanel();
				//panel_2.add(panel_4);
		panel_4.setLayout(new BoxLayout(panel_4, BoxLayout.Y_AXIS));
		
		Component verticalStrut_7 = Box.createVerticalStrut(4);
		panel_4.add(verticalStrut_7);
		
		JPanel panel_7 = new JPanel();
		panel_4.add(panel_7);
		panel_7.setLayout(new BoxLayout(panel_7, BoxLayout.X_AXIS));
		
		Component horizontalStrut_3 = Box.createHorizontalStrut(20);
		panel_7.add(horizontalStrut_3);
		
		JLabel lblNewLabel_3 = new JLabel("->");
		lblNewLabel_3.setFont(new Font("Dialog", Font.BOLD, 14));
		panel_7.add(lblNewLabel_3);
		
		Component horizontalStrut_4 = Box.createHorizontalStrut(20);
		panel_7.add(horizontalStrut_4);
		
		lblFileName = new JLabel("fileName");
		lblFileName.setFont(new Font("Dialog", Font.BOLD, 14));
		panel_7.add(lblFileName);
		
		Component horizontalGlue_2 = Box.createHorizontalGlue();
		panel_7.add(horizontalGlue_2);
		
		JButton btnNewButton = new JButton("view");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				System.out.println("user selcted brach " + tModel.getValueAt(table.getSelectedRow(), 2));
				
				Integer br_id = Integer.valueOf(tModel.getValueAt(table.getSelectedRow(), 2).toString());
				MainWin.instance.setId(br_id,true);
				MainWin.instance.updateNetView();
				
			}
		});
		panel_7.add(btnNewButton);
		
		Component horizontalStrut_5 = Box.createHorizontalStrut(10);
		panel_7.add(horizontalStrut_5);
		
		JButton btnNewButton_1 = new JButton("remove");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Integer row = table.getSelectedRow();
				Integer br_id = Integer.valueOf((String) tModel.getValueAt(row,1));
				ClassFactory.getInstance().removeFromMem(br_id);
				tModel.removeRow(table.getSelectedRow());
				System.out.println("deleting  branch "+br_id);
			}
		});
		panel_7.add(btnNewButton_1);
		
		Component horizontalStrut_9 = Box.createHorizontalStrut(10);
		panel_7.add(horizontalStrut_9);
		
		Component verticalStrut_4 = Box.createVerticalStrut(8);
		panel_4.add(verticalStrut_4);
		
		JPanel panel_8 = new JPanel();
		panel_8.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_4.add(panel_8);
		panel_8.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				String fileName = (String) tModel.getValueAt(table.getSelectedRow(), 1);
				lblFileName.setText(fileName);
				
			}
		});
		panel_8.add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setLeftComponent(panel_3);
		splitPane.setRightComponent(panel_4);
		
		Component verticalStrut_12 = Box.createVerticalStrut(5);
		panel_4.add(verticalStrut_12);
		
		JPanel panel_24 = new JPanel();
		panel_24.setPreferredSize(new Dimension(10, 150));
		panel_4.add(panel_24);
		panel_24.setLayout(new BoxLayout(panel_24, BoxLayout.X_AXIS));
		
		JPanel panel_25 = new JPanel();
		panel_25.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_24.add(panel_25);
		panel_25.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_27 = new JPanel();
		panel_27.setPreferredSize(new Dimension(160, 10));
		panel_25.add(panel_27, BorderLayout.WEST);
		panel_27.setLayout(new BoxLayout(panel_27, BoxLayout.Y_AXIS));
		
		JPanel panel_29 = new JPanel();
		panel_29.setMaximumSize(new Dimension(32767, 25));
		panel_29.setPreferredSize(new Dimension(10, 25));
		
		panel_29.setLayout(new BoxLayout(panel_29, BoxLayout.X_AXIS));
		
		Component horizontalGlue_9 = Box.createHorizontalGlue();
		panel_29.add(horizontalGlue_9);
		
		JLabel lblNewLabel_4 = new JLabel("Subnet count");
		lblNewLabel_4.setFont(new Font("Dialog", Font.BOLD, 12));
		panel_29.add(lblNewLabel_4);
		
		Component horizontalStrut_2 = Box.createHorizontalStrut(20);
		panel_29.add(horizontalStrut_2);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setMaximumSize(new Dimension(2, 32767));
		separator_2.setPreferredSize(new Dimension(2, 2));
		separator_2.setOrientation(SwingConstants.VERTICAL);
		panel_29.add(separator_2);
		
		panel_27.add(panel_29);
		JSeparator separ0 = new JSeparator(SwingConstants.HORIZONTAL);
		separ0.setMaximumSize(new Dimension(32767, 2));
		panel_27.add(separ0);
		JPanel panel_30 = new JPanel();
		panel_30.setMaximumSize(new Dimension(32767, 25));
		panel_30.setPreferredSize(new Dimension(10, 25));
		
		panel_30.setLayout(new BoxLayout(panel_30, BoxLayout.X_AXIS));
		
		Component horizontalGlue_17 = Box.createHorizontalGlue();
		panel_30.add(horizontalGlue_17);
		
		JLabel lblNewLabel_5 = new JLabel("IPs count");
		lblNewLabel_5.setFont(new Font("Dialog", Font.BOLD, 12));
		panel_30.add(lblNewLabel_5);
		
		Component horizontalStrut_10 = Box.createHorizontalStrut(20);
		panel_30.add(horizontalStrut_10);
		
		JSeparator separator_3 = new JSeparator();
		separator_3.setMaximumSize(new Dimension(2, 32767));
		separator_3.setPreferredSize(new Dimension(2, 2));
		separator_3.setOrientation(SwingConstants.VERTICAL);
		panel_30.add(separator_3);
		
		panel_27.add(panel_30);
		JSeparator separ1 = new JSeparator(SwingConstants.HORIZONTAL);
		separ1.setMaximumSize(new Dimension(32767, 2));
		panel_27.add(separ1);
			/////
		
		JPanel panel_301 = new JPanel();
		panel_301.setMaximumSize(new Dimension(32767, 25));
		panel_301.setPreferredSize(new Dimension(10, 25));
		
		panel_301.setLayout(new BoxLayout(panel_301, BoxLayout.X_AXIS));
		
		Component horizontalGlue_171 = Box.createHorizontalGlue();
		panel_301.add(horizontalGlue_171);
		
		JLabel lblNewLabel_51 = new JLabel("Sessions count");
		lblNewLabel_51.setFont(new Font("Dialog", Font.BOLD, 12));
		panel_301.add(lblNewLabel_51);
		
		Component horizontalStrut_101 = Box.createHorizontalStrut(20);
		panel_301.add(horizontalStrut_101);
		
		JSeparator separator_31 = new JSeparator();
		separator_31.setMaximumSize(new Dimension(2, 32767));
		separator_31.setPreferredSize(new Dimension(2, 2));
		separator_31.setOrientation(SwingConstants.VERTICAL);
		panel_301.add(separator_31);
		
		panel_27.add(panel_301);
		JSeparator separ11 = new JSeparator(SwingConstants.HORIZONTAL);
		separ11.setMaximumSize(new Dimension(32767, 2));
		panel_27.add(separ11);
		
			/////
		
		////
		
		JPanel panel_302 = new JPanel();
		panel_302.setMaximumSize(new Dimension(32767, 25));
		panel_302.setPreferredSize(new Dimension(10, 25));
		
		panel_302.setLayout(new BoxLayout(panel_302, BoxLayout.X_AXIS));
		
		Component horizontalGlue_172 = Box.createHorizontalGlue();
		panel_302.add(horizontalGlue_172);
		
		JLabel lblNewLabel_52 = new JLabel("Data capture");
		lblNewLabel_52.setFont(new Font("Dialog", Font.BOLD, 12));
		panel_302.add(lblNewLabel_52);
		
		Component horizontalStrut_102 = Box.createHorizontalStrut(20);
		panel_302.add(horizontalStrut_102);
		
		JSeparator separator_32 = new JSeparator();
		separator_32.setMaximumSize(new Dimension(2, 32767));
		separator_32.setPreferredSize(new Dimension(2, 2));
		separator_32.setOrientation(SwingConstants.VERTICAL);
		panel_302.add(separator_32);
		
		panel_27.add(panel_302);
		JSeparator separ12 = new JSeparator(SwingConstants.HORIZONTAL);
		separ12.setMaximumSize(new Dimension(32767, 2));
		panel_27.add(separ12);
		
		////
		
		////
		
		JPanel panel_303 = new JPanel();
		panel_303.setMaximumSize(new Dimension(32767, 25));
		panel_303.setPreferredSize(new Dimension(10, 25));
		
		panel_303.setLayout(new BoxLayout(panel_303, BoxLayout.X_AXIS));
		
		Component horizontalGlue_173 = Box.createHorizontalGlue();
		panel_303.add(horizontalGlue_173);
		
		JLabel lblNewLabel_53 = new JLabel("Home used");
		lblNewLabel_53.setFont(new Font("Dialog", Font.BOLD, 12));
		panel_303.add(lblNewLabel_53);
		
		Component horizontalStrut_103 = Box.createHorizontalStrut(20);
		panel_303.add(horizontalStrut_103);
		
		JSeparator separator_33 = new JSeparator();
		separator_33.setMaximumSize(new Dimension(2, 32767));
		separator_33.setPreferredSize(new Dimension(2, 2));
		separator_33.setOrientation(SwingConstants.VERTICAL);
		panel_303.add(separator_33);
		
		panel_27.add(panel_303);
		JSeparator separ13 = new JSeparator(SwingConstants.HORIZONTAL);
		separ13.setMaximumSize(new Dimension(32767, 2));
		panel_27.add(separ13);
		
		////
		
		JPanel panel_28 = new JPanel();
		panel_25.add(panel_28, BorderLayout.CENTER);
		panel_28.setLayout(new BoxLayout(panel_28, BoxLayout.Y_AXIS));
		
		JPanel panel_31 = new JPanel();
		panel_31.setMaximumSize(new Dimension(32767, 25));
		panel_31.setPreferredSize(new Dimension(10, 25));
		
		panel_31.setLayout(new BoxLayout(panel_31, BoxLayout.X_AXIS));
		
		Component horizontalGlue_18 = Box.createHorizontalGlue();
		panel_31.add(horizontalGlue_18);
		
		lblSubnetCountVal = new JLabel("-");
		lblSubnetCountVal.setFont(new Font("Dialog", Font.ITALIC, 13));
		panel_31.add(lblSubnetCountVal);
		
		Component horizontalGlue_19 = Box.createHorizontalGlue();
		panel_31.add(horizontalGlue_19);
		panel_28.add(panel_31);
		JSeparator separ2 = new JSeparator(SwingConstants.HORIZONTAL);
		separ2.setMaximumSize(new Dimension(32767, 2));
		panel_28.add(separ2);
		
		JPanel panel_32 = new JPanel();
		panel_32.setMaximumSize(new Dimension(32767, 25));
		panel_32.setPreferredSize(new Dimension(10, 25));
		
		panel_32.setLayout(new BoxLayout(panel_32, BoxLayout.X_AXIS));
		
		Component horizontalGlue_20 = Box.createHorizontalGlue();
		panel_32.add(horizontalGlue_20);
		
		lblIPCountVal = new JLabel("-");
		lblIPCountVal.setFont(new Font("Dialog", Font.ITALIC, 13));
		panel_32.add(lblIPCountVal);
		
		Component horizontalGlue_21 = Box.createHorizontalGlue();
		panel_32.add(horizontalGlue_21);
		panel_28.add(panel_32);
		JSeparator separ3 = new JSeparator(SwingConstants.HORIZONTAL);
		separ3.setMaximumSize(new Dimension(32767, 2));
		panel_28.add(separ3);
		
		///
		
		JPanel panel_321 = new JPanel();
		panel_321.setMaximumSize(new Dimension(32767, 25));
		panel_321.setPreferredSize(new Dimension(10, 25));
		
		panel_321.setLayout(new BoxLayout(panel_321, BoxLayout.X_AXIS));
		
		Component horizontalGlue_201 = Box.createHorizontalGlue();
		panel_321.add(horizontalGlue_201);
		
		lblSessionsCountVal = new JLabel("-");
		lblSessionsCountVal.setFont(new Font("Dialog", Font.ITALIC, 13));
		panel_321.add(lblSessionsCountVal);
		
		Component horizontalGlue_211 = Box.createHorizontalGlue();
		panel_321.add(horizontalGlue_211);
		panel_28.add(panel_321);
		JSeparator separ31 = new JSeparator(SwingConstants.HORIZONTAL);
		separ31.setMaximumSize(new Dimension(32767, 2));
		panel_28.add(separ31);
		
		///
		
		///
		
				JPanel panel_323 = new JPanel();
				panel_323.setMaximumSize(new Dimension(32767, 25));
				panel_323.setPreferredSize(new Dimension(10, 25));
				
				panel_323.setLayout(new BoxLayout(panel_323, BoxLayout.X_AXIS));
				
				Component horizontalGlue_203 = Box.createHorizontalGlue();
				panel_323.add(horizontalGlue_203);
				
				lblDataCaptureVal = new JLabel("-");
				lblDataCaptureVal.setFont(new Font("Dialog", Font.ITALIC, 13));
				panel_323.add(lblDataCaptureVal);
				
				Component horizontalGlue_213 = Box.createHorizontalGlue();
				panel_323.add(horizontalGlue_213);
				panel_28.add(panel_323);
				JSeparator separ33 = new JSeparator(SwingConstants.HORIZONTAL);
				separ33.setMaximumSize(new Dimension(32767, 2));
				panel_28.add(separ33);
				
				///
				
		
		///
		
		JPanel panel_322 = new JPanel();
		panel_322.setMaximumSize(new Dimension(32767, 25));
		panel_322.setPreferredSize(new Dimension(10, 25));
		
		panel_322.setLayout(new BoxLayout(panel_322, BoxLayout.X_AXIS));
		
		Component horizontalGlue_202 = Box.createHorizontalGlue();
		panel_322.add(horizontalGlue_202);
		
		lblHomeUsedVal = new JLabel("-");
		lblHomeUsedVal.setFont(new Font("Dialog", Font.ITALIC, 13));
		panel_322.add(lblHomeUsedVal);
		
		Component horizontalGlue_212 = Box.createHorizontalGlue();
		panel_322.add(horizontalGlue_212);
		panel_28.add(panel_322);
		JSeparator separ32 = new JSeparator(SwingConstants.HORIZONTAL);
		separ32.setMaximumSize(new Dimension(32767, 2));
		panel_28.add(separ32);
		
		///
		
		
		JPanel panel_26 = new JPanel();
		panel_26.setBorder(new CompoundBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null), new EmptyBorder(3, 5, 3, 5)));
		panel_24.add(panel_26);
		panel_26.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane_2 = new JScrollPane();
		JTextArea textAreaBranchDesc = new JTextArea();
		scrollPane_2.setViewportView(textAreaBranchDesc);
		panel_26.add(scrollPane_2, BorderLayout.CENTER);
		
		JPanel panel_33 = new JPanel();
		panel_26.add(panel_33, BorderLayout.NORTH);
		panel_33.setLayout(new BoxLayout(panel_33, BoxLayout.X_AXIS));
		
		JLabel lblNewLabel_9 = new JLabel("New label");
		panel_33.add(lblNewLabel_9);
		panel_2.add(splitPane);
		
		Component verticalStrut_1 = Box.createVerticalStrut(15);
		panel_18.add(verticalStrut_1);
		
		panel_9 = new JPanel();
		panel_9.setBorder(new CompoundBorder(new LineBorder(new Color(0, 0, 0), 1, true), new EmptyBorder(5, 5, 5, 5)));
		panel_18.add(panel_9);
		panel_9.setLayout(new BoxLayout(panel_9, BoxLayout.X_AXIS));
		
		JPanel panel_10 = new JPanel();
		panel_10.setMaximumSize(new Dimension(280, 32767));
		panel_10.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_10.setPreferredSize(new Dimension(230, 150));
		panel_10.setMinimumSize(new Dimension(350, 80));
		panel_9.add(panel_10);
		panel_10.setLayout(new BoxLayout(panel_10, BoxLayout.Y_AXIS));
		
		JPanel panel_12 = new JPanel();
		panel_10.add(panel_12);
		panel_12.setLayout(new BoxLayout(panel_12, BoxLayout.X_AXIS));
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		panel_12.add(horizontalStrut);
		
		lblObjType = new JLabel("User:");
		panel_12.add(lblObjType);
		
		Component horizontalGlue_3 = Box.createHorizontalGlue();
		panel_12.add(horizontalGlue_3);
		
		lblselObjName = new JLabel("0.0.0.0");
		lblselObjName.setFont(new Font("Dialog", Font.BOLD, 22));
		panel_12.add(lblselObjName);
		
		Component horizontalGlue_4 = Box.createHorizontalGlue();
		panel_12.add(horizontalGlue_4);
		
		Component verticalStrut_5 = Box.createVerticalStrut(4);
		panel_10.add(verticalStrut_5);
		
		JPanel panel_13 = new JPanel();
		panel_10.add(panel_13);
		panel_13.setLayout(new BoxLayout(panel_13, BoxLayout.X_AXIS));
		
		Component horizontalGlue_5 = Box.createHorizontalGlue();
		panel_13.add(horizontalGlue_5);
		
		btnBan = new JButton("Deny loading dumps");
		btnBan.setFont(new Font("Dialog", Font.PLAIN, 12));
		btnBan.setMargin(new Insets(2, 7, 2, 7));
		btnBan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ClassFactory factory = ClassFactory.getInstance();
				String ip = lblselObjName.getText();
				
			}
		});
		
		btnBan.setMinimumSize(new Dimension(185, 25));
		btnBan.setPreferredSize(new Dimension(185, 25));
		btnBan.setMaximumSize(new Dimension(185, 25));
		panel_13.add(btnBan);
		
		Component horizontalGlue_6 = Box.createHorizontalGlue();
		panel_13.add(horizontalGlue_6);
		
		Component verticalStrut_6 = Box.createVerticalStrut(4);
		panel_10.add(verticalStrut_6);
		
		JPanel panel_14 = new JPanel();
		panel_10.add(panel_14);
		panel_14.setLayout(new BoxLayout(panel_14, BoxLayout.X_AXIS));
		
		Component horizontalGlue_7 = Box.createHorizontalGlue();
		panel_14.add(horizontalGlue_7);
		
		JButton btnNewButton_3 = new JButton("Deny view Live");
		btnNewButton_3.setFont(new Font("Dialog", Font.PLAIN, 12));
		btnNewButton_3.setPreferredSize(new Dimension(185, 25));
		btnNewButton_3.setMinimumSize(new Dimension(185, 25));
		btnNewButton_3.setMaximumSize(new Dimension(185, 25));
		btnNewButton_3.setMargin(new Insets(2, 7, 2, 7));
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ClassFactory factory = ClassFactory.getInstance();
				factory.removeFromMem(lblselObjName.getText());
	//				lstModel.removeElement(lblselIP.getText());
	//			cleanTable();
			}
		});
		panel_14.add(btnNewButton_3);
		
		Component horizontalGlue_8 = Box.createHorizontalGlue();
		panel_14.add(horizontalGlue_8);
		
		Component verticalStrut_11 = Box.createVerticalStrut(4);
		panel_10.add(verticalStrut_11);
		
		JPanel panel_22 = new JPanel();
		panel_10.add(panel_22);
		panel_22.setLayout(new BoxLayout(panel_22, BoxLayout.X_AXIS));
		
		Component horizontalGlue_14 = Box.createHorizontalGlue();
		panel_22.add(horizontalGlue_14);
		
		JButton btnSdfgsdf = new JButton("Deny view third");
		btnSdfgsdf.setFont(new Font("Dialog", Font.PLAIN, 12));
		btnSdfgsdf.setPreferredSize(new Dimension(185, 25));
		btnSdfgsdf.setMinimumSize(new Dimension(185, 25));
		btnSdfgsdf.setMaximumSize(new Dimension(185, 25));
		btnSdfgsdf.setMargin(new Insets(2, 7, 2, 7));
		panel_22.add(btnSdfgsdf);
		
		Component horizontalGlue_15 = Box.createHorizontalGlue();
		panel_22.add(horizontalGlue_15);
		
		Component horizontalStrut_6 = Box.createHorizontalStrut(5);
		panel_9.add(horizontalStrut_6);
		
		JPanel panel_11 = new JPanel();
		panel_11.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_9.add(panel_11);
		panel_11.setLayout(new BoxLayout(panel_11, BoxLayout.Y_AXIS));
		
		JPanel panel_15 = new JPanel();
		panel_11.add(panel_15);
		panel_15.setLayout(new BoxLayout(panel_15, BoxLayout.X_AXIS));
		
		Component verticalStrut_2 = Box.createVerticalStrut(5);
		panel_11.add(verticalStrut_2);
		
		JPanel panel_16 = new JPanel();
		panel_11.add(panel_16);
		panel_16.setLayout(new BoxLayout(panel_16, BoxLayout.X_AXIS));
		
		Component horizontalStrut_11 = Box.createHorizontalStrut(20);
		panel_16.add(horizontalStrut_11);
		
		JLabel lblNewLabel_8 = new JLabel("Total upload size:");
		lblNewLabel_8.setFont(new Font("Dialog", Font.BOLD, 14));
		panel_16.add(lblNewLabel_8);
		
		Component horizontalGlue_10 = Box.createHorizontalGlue();
		panel_16.add(horizontalGlue_10);
		
		totalSize = new JLabel("New label");
		totalSize.setFont(new Font("Dialog", Font.BOLD, 14));
		panel_16.add(totalSize);
		
		Component horizontalStrut_12 = Box.createHorizontalStrut(20);
		panel_16.add(horizontalStrut_12);
		
		Component verticalStrut_3 = Box.createVerticalStrut(5);
		panel_11.add(verticalStrut_3);
		
		JPanel panel_17 = new JPanel();
		panel_11.add(panel_17);
		panel_17.setLayout(new BoxLayout(panel_17, BoxLayout.X_AXIS));
		
		Component horizontalStrut_14 = Box.createHorizontalStrut(20);
		panel_17.add(horizontalStrut_14);
		
		JLabel lblNewLabel_10 = new JLabel("Total requests:");
		lblNewLabel_10.setFont(new Font("Dialog", Font.BOLD, 14));
		panel_17.add(lblNewLabel_10);
		
		Component horizontalGlue_11 = Box.createHorizontalGlue();
		panel_17.add(horizontalGlue_11);
		
		totalReq = new JLabel("New label");
		totalReq.setFont(new Font("Dialog", Font.BOLD, 14));
		panel_17.add(totalReq);
		
		Component horizontalStrut_13 = Box.createHorizontalStrut(20);
		panel_17.add(horizontalStrut_13);
		
		Component verticalGlue = Box.createVerticalGlue();
		panel_11.add(verticalGlue);
		
		Component verticalStrut_8 = Box.createVerticalStrut(10);
		panel_18.add(verticalStrut_8);
		
		JPanel panel_20 = new JPanel();
		panel_20.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_18.add(panel_20);
		panel_20.setLayout(new BoxLayout(panel_20, BoxLayout.Y_AXIS));
		
		Component verticalStrut_10 = Box.createVerticalStrut(5);
		panel_20.add(verticalStrut_10);
		
		JPanel panel_23 = new JPanel();
		panel_20.add(panel_23);
		panel_23.setLayout(new BoxLayout(panel_23, BoxLayout.X_AXIS));
		
		Component horizontalStrut_16 = Box.createHorizontalStrut(20);
		panel_23.add(horizontalStrut_16);
		
		Component horizontalStrut_15 = Box.createHorizontalStrut(5);
		panel_23.add(horizontalStrut_15);
		
		alowRemote = new JButton("Settings");
		alowRemote.setMaximumSize(new Dimension(200, 25));
		alowRemote.setIcon(null);
		alowRemote.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Settings ss = new Settings();
				ss.setVisible(true);
			}
		});
		panel_23.add(alowRemote);
		
		Component horizontalStrut_18 = Box.createHorizontalStrut(20);
		panel_23.add(horizontalStrut_18);
		
		lblPath = new JLabel("[home not set]");
		lblPath.setFont(new Font("Dialog", Font.ITALIC, 14));
		panel_23.add(lblPath);
		
		horizontalGlue_16 = Box.createHorizontalGlue();
		panel_23.add(horizontalGlue_16);
		
		JButton btnNewButton_6 = new JButton("close");
		btnNewButton_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				deleteObserver();
				dispose();
			}
		});
		panel_23.add(btnNewButton_6);
		
		Component horizontalStrut_17 = Box.createHorizontalStrut(10);
		panel_23.add(horizontalStrut_17);
		
		Component verticalStrut_9 = Box.createVerticalStrut(5);
		panel_20.add(verticalStrut_9);
		
		Component horizontalStrut_8 = Box.createHorizontalStrut(10);
		panel_19.add(horizontalStrut_8);
		
		myInitCode();
	}
	public void myInitCode() {
		TModelBuilder modelBuilder = new TModelBuilder(null, null);
		tModel=modelBuilder.init();
		table.setModel(tModel);
		table.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				String brIdStr = (String)model.getValueAt(table.getSelectedRow(), 1);
				Integer brId = Integer.decode(brIdStr);
				Branch br  = ClassFactory.getInstance().getBranch(brId);
				if (br != null) {
					SessionsAPI sesApi = ClassFactory.getInstance().getSesionInstance(brId);
					lblSubnetCountVal.setText(sesApi.getSubnetCount().toString());
					lblIPCountVal.setText(sesApi.getIPsCount().toString());
					lblSessionsCountVal.setText(sesApi.getSessionsCount().toString());
					if (br.isDataCapture()) lblDataCaptureVal.setText("Yes"); else 
						lblDataCaptureVal.setText("No");
					String homeUsed = Controller.processSize(br.getHomeUsed(), 2);
					lblHomeUsedVal.setText(homeUsed);
					
					
				}
			}
		});
		table.setFont(new Font("Dialog", Font.BOLD, 13));
		
		buildByIP();
		 ClassFactory.getInstance().addObserver(this);
	}
	private void buildInfoAbout(Object obj) {
		if (obj instanceof User) {
			User user = (User) obj;
			if (user.getIP()!=null)
				lblObjType.setText("IP/User:");
			else
				lblObjType.setText("User:");
			lblselObjName.setText(user.toString());
		}
		if (obj instanceof Ip) {
			Ip ip = (Ip) obj;
			if (ip.getUser()!=null)
				lblObjType.setText("User/IP:");
			else
				lblObjType.setText("IP:");
			lblselObjName.setText(ip.toString());
			
		}
		
	}
	
	private void buildByUser() {
		ClassFactory factory = ClassFactory.getInstance();
		Collection<Branch> branches =  factory.getBranches();
		DefaultMutableTreeNode top = new DefaultMutableTreeNode("Users");
		HashSet<String> users = new HashSet<String>();
		users.add("allUsers");
		for (Branch item : branches) {
			users.add(item.getUserName());
			
		}
		for (String user : users) {
			DefaultMutableTreeNode child = new DefaultMutableTreeNode(new User(user,null));
			HashSet<String> ips = new HashSet<String>();
			for (Branch b : branches) {
				if (b.getUserName().equals(user) || user.equals("allUsers")) {
					ips.add(b.getWebIP());
				}
			}
			for (String ip : ips) {
				DefaultMutableTreeNode subChild = new DefaultMutableTreeNode(new Ip(ip, user));
				child.add(subChild);
			}
			top.add(child);
			
		}
		DefaultTreeModel model = new DefaultTreeModel(top);
		tree.setModel(model);
		
	
	}
	
	
	private void buildByIP() {
		ClassFactory factory = ClassFactory.getInstance();
		Collection<Branch> branches =  factory.getBranches();
		DefaultMutableTreeNode top = new DefaultMutableTreeNode("IPs");
		HashSet<String> ips = new HashSet<String>();
		ips.add("*.*.*.*");
		for (Branch item : branches) {
			ips.add(item.getWebIP());
			
		}
		for (String ip : ips) {
			
			DefaultMutableTreeNode child = new DefaultMutableTreeNode(new Ip(ip,null));
			HashSet<String> users = new HashSet<String>();
			for (Branch b : branches) {
				if (b.getWebIP().equals(ip) || ip.equals("*.*.*.*")) {
					users.add(b.getUserName());
				}
			}
			for (String user : users) {
				DefaultMutableTreeNode subChild = new DefaultMutableTreeNode(new User(user, ip));
				child.add(subChild);
			}
			top.add(child);
			
		}
		DefaultTreeModel model = new DefaultTreeModel(top);
		tree.setModel(model);
		
	
	}
	
	public void deleteObserver() {
		ClassFactory.getInstance().deleteObserver(this);
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
	
	
	@Override
	public void update(Observable arg0, Object arg1) {
		if ((arg0 instanceof ClassFactory)&& (arg1 instanceof Branch)) {
			Branch br = (Branch) arg1;
			if (selectedObj instanceof Ip) {
			
				Ip ip = (Ip)selectedObj;
				if (!br.getWebIP().equals(ip.toString())) 
					if  (!ip.toString().equals("*.*.*.*")) return;
				if (ip.getUser()!=null)
					if (!br.getUserName().equals(ip.getUser()) || !ip.getUser().equals("allUsers") ) return;
				
			} 
			if (selectedObj instanceof User) {
				
					User user = (User) selectedObj;
					if (!br.getUserName().equals(user.toString())) 
						if  (user.toString().equals("allUsers")) return;
					if (user.getIP()!=null)
						if (!br.getWebIP().equals(user.getIP()) || user.getIP().equals("*.*.*.*")) return;

			}

				TModelBuilder modelBuilder = new TModelBuilder(null, table.getModel());
				modelBuilder.processBranch(br);
			
			}
		}
	}

class TModelBuilder {
	 static ArrayList<String> cols = new ArrayList<String>();
	User user=null;
	Ip ip=null;
	DefaultTableModel tmodel;
	private Integer rowCounter=0;
	
	
	public TModelBuilder(Object userSelectedObj, TableModel tmodel) {
		if (userSelectedObj instanceof User) 
			user = (User) userSelectedObj;
		if (userSelectedObj instanceof Ip)
			ip=(Ip) userSelectedObj;
		this.tmodel =(DefaultTableModel) tmodel;
	}
	
	public void cleanTable() {
		
		Integer count =  tmodel.getRowCount();
		for (Integer pos=0; pos<count; pos++ ) {
			tmodel.removeRow(0);
		}
	
	}
public DefaultTableModel  init() {
		tmodel = new DefaultTableModel();
		cols.add("id");
		cols.add("file_iface");
		cols.add("brID");
		cols.add("created");
		cols.add("state");
		cols.add("desc");
		cols.add("data_cap");
		cols.add("subnet_count");
		cols.add("ips_count");
		cols.add("ses_count");
		cols.add("file_size");
		cols.add("home_space_used");
		
		
	tmodel = new DefaultTableModel();
	tmodel.addColumn("id");
	tmodel.addColumn("brID");
	tmodel.addColumn("FileName/iface");
	tmodel.addColumn("created");
	tmodel.addColumn("state");
//	tmodel.addColumn("desc");
//	tmodel.addColumn("data capture");
//	tmodel.addColumn("subnet cnt");
//	tmodel.addColumn("ips cnt");
//	tmodel.addColumn("ses cnt");
//	tmodel.addColumn("file size");
//	tmodel.addColumn("home used");
		return tmodel;
		
		
	}
	public void processBranch(Branch br) {
		String data[] = new String[tmodel.getColumnCount()];
		
		
		for (int i=0; i<tmodel.getColumnCount(); i++) {
			String col = tmodel.getColumnName(i);
			if (col.equals("id")) {
				data[i]=rowCounter.toString();
			
			}
			if (col.equals("FileName/iface")) {
				if (br.getFileName()!=null)
					data[i]=br.getFileName();
				if (br.getIface()!=null) 
					data[i]=br.getIface();
				
			}
			if (col.equals("brID")) {
				data[i]=br.getId().toString();
	
			}
			if (col.equals("created")) {
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM hh:mm:ss");
				data[i]=sdf.format(br.getTime());
			
			}
			if (col.equals("state")) {
				if (br.getState()==0) data[i]="Created";
				if (br.getState()==1) data[i]="Started";
				if (br.getState()==2) data[i]="Paused";
				if (br.getState()==3) data[i]="Stoped";
	
			}
			if (col.equals("desc")) {
				data[i] = br.getDesc();
	
			}
			if (col.equals("data capture")) {
				if (br.isDataCapture()) {
					Long len = br.getCapturedDataSize();
					data[i]=Controller.processSize(len,2);
				
				} else data[i]="No";
			}
			if (col.equals("subnet cnt")) {
				Integer id = br.getId();
				SessionsAPI sesApi = ClassFactory.getInstance().getSesionInstance(id);
				Integer netCount = sesApi.getSubnetCount();
				data[i]=netCount.toString();
				
			}
			if (col.equals("ips cnt")) {
				
				Integer id = br.getId();
				SessionsAPI sesApi = ClassFactory.getInstance().getSesionInstance(id);
				Integer ips_count = sesApi.getIPsCount();
				data[i]=ips_count.toString();
			
			}
			if (col.equals("ses cnt")) {
				
				Integer id = br.getId();
				SessionsAPI sesApi = ClassFactory.getInstance().getSesionInstance(id);
				Integer ses_count = sesApi.getSessionsCount();
				data[i]=ses_count.toString();
			
			}
			if (col.equals("file size")) {
				if (br.getFileName()!=null) {
					File dump = new File(br.getFileName());
					data[i]=Controller.processSize( dump.length(),2);
				} else 
					data[i]="[-1]";
			
			}
			if (col.equals("home used")) {
				String sizeStr = Controller.processSize(br.getHomeUsed(),2);	
				data[i]=sizeStr;
			}
		}
		tmodel.addRow(data);
		
	}
	public void build() {
		cleanTable();
		
		
		
		if (user !=null) {
			 
				Collection<Branch> branches =  ClassFactory.getInstance().getBranches();
				Integer id = 0;
				for (Branch item : branches) {
					
					if ((user.toString().equals("allUsers")) || (item.getUserName().equals(user.toString()))) {
						if (user.getIP()==null || user.getIP().equals(item.getWebIP()) || user.getIP().equals("*.*.*.*")) {
							processBranch(item);
						
						}
					}
				}
			 
			 
		 } 
		if (ip != null) {
			 
			 	
			Collection<Branch> branches =  ClassFactory.getInstance().getBranches();
				Integer id = 0;
				for (Branch item : branches) {
					
					if ((ip.toString().equals("*.*.*.*")) || (item.getWebIP().equals(ip.toString()))) {
						if (ip.getUser()==null || ip.getUser().equals(item.getUserName()) || ip.getUser().equals("allUsers")) {
							processBranch(item);
						}
					}
				}
		 }
	}
	
}

class User {
	String ip=null;
	String name;
	public User(String name, String ip) {
		this.name= name;
		this.ip=ip;
	}
	public String getIP() {
		return ip;
	}
	public String toString() {
		return name;
	}
}

class Ip {
	String ip;
	String name=null;
	public Ip(String ip,String name) {
		this.name = name;
		this.ip = ip;
	}
	public String getUser() {
		return name;
	}
	public String toString() {
		return ip;
	}
}
