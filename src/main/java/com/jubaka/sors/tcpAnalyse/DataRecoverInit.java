package com.jubaka.sors.tcpAnalyse;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.Renderer;
import javax.swing.SwingConstants;

import java.awt.Dimension;

import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellRenderer;

import com.jubaka.sors.factories.ClassFactory;
import com.jubaka.sors.myTreeView.FileTreeViewer;

import java.awt.Color;

import javax.swing.border.EtchedBorder;

import java.awt.Font;

import javax.swing.JSeparator;

import java.awt.Component;

import javax.swing.Box;
import javax.swing.JButton;

import java.awt.Insets;
import java.awt.SystemColor;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import javax.swing.UIManager;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JTable;

import com.jubaka.sors.sessions.Branch;
import com.jubaka.sors.sessions.Session;
import com.jubaka.sors.sessions.SessionsAPI;
import com.jubaka.sors.sessions.Subnet;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.JSplitPane;

public class DataRecoverInit extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private FileTreeViewer addrsTree;
	private Integer branchId;
	private JPanel panelChangableParent;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DataRecoverInit frame = new DataRecoverInit(0);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public DataRecoverInit(Integer brId) {
		branchId = brId;
		initialise(brId);
		initTable(brId);
		initTree(brId);
		setOneSideView();
	
	}
	
	public void initialise(Integer brId) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 410);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		
		JPanel panel = new JPanel();
		panel.setBorder(new CompoundBorder(new LineBorder(new Color(0, 0, 0), 1, true), new EtchedBorder(EtchedBorder.RAISED, null, null)));
		panel.setMaximumSize(new Dimension(32767, 40));
		panel.setPreferredSize(new Dimension(10, 40));
		contentPane.add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		JLabel lblSors = new JLabel("Sors");
		lblSors.setFont(new Font("URW Chancery L", Font.BOLD, 24));
		lblSors.setHorizontalTextPosition(SwingConstants.CENTER);
		lblSors.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblSors, BorderLayout.CENTER);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.Y_AXIS));
		
		JPanel panel_2 = new JPanel();
		panel_1.add(panel_2);
		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.X_AXIS));
		
		Component horizontalGlue_1 = Box.createHorizontalGlue();
		panel_2.add(horizontalGlue_1);
		
		JLabel lblNewLabel = new JLabel("Data recover tool");
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 14));
		panel_2.add(lblNewLabel);
		
		Component horizontalGlue = Box.createHorizontalGlue();
		panel_2.add(horizontalGlue);
		
		JSeparator separator = new JSeparator();
		separator.setMaximumSize(new Dimension(32767, 3));
		panel_1.add(separator);
		
		JPanel panel_4 = new JPanel();
		contentPane.add(panel_4);
		panel_4.setLayout(new BoxLayout(panel_4, BoxLayout.Y_AXIS));
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBorder(new CompoundBorder(new LineBorder(new Color(100, 149, 237), 1, true), new EmptyBorder(2, 2, 7, 2)));
		scrollPane_1.setMaximumSize(new Dimension(32767, 150));
		scrollPane_1.setMinimumSize(new Dimension(22, 120));
		scrollPane_1.setPreferredSize(new Dimension(3, 140));
		panel_4.add(scrollPane_1);
		
				Set<Subnet> subnets =  ClassFactory.getInstance().getSesionInstance(brId).getAllSubnets();
				
				panelChangableParent = new JPanel();
				panelChangableParent.setLayout(new BorderLayout());
				panel_4.add(panelChangableParent);
				addrsTree = new FileTreeViewer(subnets);
		
		table = new JTable();
		scrollPane_1.setViewportView(table);
		table.setFillsViewportHeight(true);
		
		Component verticalGlue = Box.createVerticalGlue();
		contentPane.add(verticalGlue);
		
		JPanel panel_3 = new JPanel();
		panel_3.setMaximumSize(new Dimension(32767, 50));
		panel_3.setPreferredSize(new Dimension(10, 50));
		panel_3.setBorder(new EmptyBorder(10, 10, 10, 10));
		contentPane.add(panel_3);
		panel_3.setLayout(new BoxLayout(panel_3, BoxLayout.X_AXIS));
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		panel_3.add(horizontalStrut);
		
		JButton btnNewButton_1 = new JButton("RECOVER");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SessionsAPI sapi = ClassFactory.getInstance().getSesionInstance(branchId);
				Set<Session> selectedSessions = addrsTree.getSelectedItems();
				sapi.dataRecovering(selectedSessions);
			}
		});
		btnNewButton_1.setBackground(new Color(60, 179, 113));
		btnNewButton_1.setMargin(new Insets(5, 5, 5, 5));
		panel_3.add(btnNewButton_1);
		
		Component horizontalGlue_2 = Box.createHorizontalGlue();
		panel_3.add(horizontalGlue_2);
		
		JButton btnNewButton = new JButton("cancel");
		btnNewButton.setMargin(new Insets(2, 6, 2, 6));
		panel_3.add(btnNewButton);
		
	}
	
	private void setOneSideView() {
		panelChangableParent.removeAll();
		panelChangableParent.add(addrsTree, BorderLayout.CENTER);
		panelChangableParent.revalidate();
		panelChangableParent.updateUI();
	}
	
	private void setTwoSideView() {
		panelChangableParent.removeAll();
		
		SessionTableView stv = new SessionTableView();
		JSplitPane split = new JSplitPane();
		split.setLeftComponent(addrsTree);
		split.setRightComponent(stv);
		panelChangableParent.add(split, BorderLayout.CENTER);
		panelChangableParent.revalidate();
		panelChangableParent.updateUI();
	}
	
	public void initTable(Integer brId) {
		Branch curBr = ClassFactory.getInstance().getBranch(brId);
		String iface = curBr.getIface();
		String fName = curBr.getFileName();
		int state = curBr.getState();
		String user = curBr.getUserName();
		Date started = curBr.getTime();
		Long rawDataSize = curBr.getCapturedDataSize();
		
		DefaultTableModel model = new DefaultTableModel();
		model.setColumnCount(2);
		
		String[] row = new String[2];
		
		row[0]="Branch Name";
		row[1]=curBr.getName();
		model.addRow(row);
		if (iface==null) {
			row[0]="File name";
			row[1]=fName;
		}
		else { 
			row[0]="Interface";
			row[1]=iface;
		}
		model.addRow(row);
		
		
		row[0]="State";
		switch (state) {
		case 0:	row[1]="Active";
				break;
		case 1: row[1]="Analizing";
				break;
		case 2: row[1]="Paused";
				break;
		case 3: row[1]="Stoped";
				break;
		}
			
		model.addRow(row);
		
		row[0]="User";
		row[1]=user;
		model.addRow(row);
		
		SimpleDateFormat sdf  = new SimpleDateFormat("dd.mm hh:mm:ss");
		row[0]="Created";
		row[1]=sdf.format(started);
		model.addRow(row);
		
		row[0]="ID";
		row[1]=brId.toString();	
		model.addRow(row);
		
		
		table.setModel(model);
		table.setTableHeader(null);
		DefaultTableCellRenderer valueCellRenderer = new DefaultTableCellRenderer() {
		    Font font = new Font("Dialog", Font.ITALIC, 15);
		    Border padding = BorderFactory.createEmptyBorder(10, 10, 10, 10);
		    @Override
		    public Component getTableCellRendererComponent(JTable table,
		            Object value, boolean isSelected, boolean hasFocus,
		            int row, int column) {
		        super.getTableCellRendererComponent(table, value, isSelected, hasFocus,
		                row, column);
		        setFont(font);
		        setBorder(padding);
		        setHorizontalAlignment(JLabel.CENTER);
		        return this;
		    }

		};
		
		DefaultTableCellRenderer nameCellRenderer = new DefaultTableCellRenderer() {
		    Font font = new Font("Dialog",Font.BOLD | Font.PLAIN, 15);
		    Border padding = BorderFactory.createEmptyBorder(10, 10, 10, 10);
		    @Override
		    public Component getTableCellRendererComponent(JTable table,
		            Object value, boolean isSelected, boolean hasFocus,
		            int row, int column) {
		        super.getTableCellRendererComponent(table, value, isSelected, hasFocus,
		                row, column);
		        setFont(font);
		        setBorder(padding);
		        setHorizontalAlignment(JLabel.RIGHT);
		        return this;
		    }

		};
		table.getColumnModel().getColumn(0).setCellRenderer(nameCellRenderer);
		table.getColumnModel().getColumn(1).setCellRenderer(valueCellRenderer);
		

	}
	
	private void initTree(Integer brId) {
		CustomMutableTreeNode root = new CustomMutableTreeNode(new DRTreeItemPanel());
		for (int i=0; i<10;i++) {
			CustomMutableTreeNode child = new CustomMutableTreeNode(new DRTreeItemPanel());
			root.add(child);
			
		}
		DefaultTreeModel dtm = new DefaultTreeModel(root);
	}
	
	
	
	
	
}

class CustomMutableTreeNode extends DefaultMutableTreeNode {
	public CustomMutableTreeNode(Object userObj) {
		super(userObj);
	}
	public Object getUserObject() {
		return userObject;
	} 
}

class CustomTreeCellRenderer implements TreeCellRenderer {
	JCheckBox ch = new JCheckBox("Niga");

	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value,
			boolean selected, boolean expanded, boolean leaf, int row,
			boolean hasFocus) {
		
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
		
		ch.setEnabled(true);
		return (Component)ch;
	}
	
}
