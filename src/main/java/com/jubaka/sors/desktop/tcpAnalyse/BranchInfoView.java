package com.jubaka.sors.tcpAnalyse;

import com.jubaka.sors.factories.ClassFactory;
import com.jubaka.sors.sessions.Branch;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.BoxLayout;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.Font;
import java.awt.Dimension;

import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JSeparator;
import javax.swing.JTable;

import java.awt.Component;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.Box;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JSpinner;

public class BranchInfoView extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private Integer brID;
	private BranchInfoView ins;
	private JTextArea descArea;


	/**
	 * Create the frame.
	 */
	public BranchInfoView(Integer brID) {
		this.brID=brID;
		ins = this;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 400);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new CompoundBorder(new LineBorder(new Color(64, 64, 64), 1, true), new EtchedBorder(EtchedBorder.RAISED, null, null)));
		panel_1.setMaximumSize(new Dimension(32767, 40));
		panel_1.setBackground(Color.WHITE);
		panel.add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JLabel lblSors = new JLabel("Sors");
		lblSors.setFont(new Font("URW Chancery L", Font.BOLD, 30));
		lblSors.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(lblSors, BorderLayout.CENTER);
		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2);
		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.Y_AXIS));
		
		JPanel panel_3 = new JPanel();
		panel_3.setPreferredSize(new Dimension(10, 40));
		panel_3.setMaximumSize(new Dimension(32767, 40));
		panel_2.add(panel_3);
		panel_3.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel = new JLabel("Current branch info:");
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 15));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		panel_3.add(lblNewLabel, BorderLayout.CENTER);
		
		JSeparator separator = new JSeparator();
		separator.setMaximumSize(new Dimension(32767, 5));
		panel_2.add(separator);
		
		Component verticalStrut = Box.createVerticalStrut(9);
		panel_2.add(verticalStrut);
		
		JPanel panel_4 = new JPanel();
		panel_2.add(panel_4);
		panel_4.setLayout(new BorderLayout(0, 0));
		
		table = new JTable();
	//	panel_4.add(table, BorderLayout.CENTER);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(table);
		panel_4.add(scrollPane, BorderLayout.CENTER);
		
		JPanel panel_5 = new JPanel();
		panel_2.add(panel_5);
		panel_5.setLayout(new BorderLayout(0, 0));
		JLabel lblDesc = new JLabel("Branch description:");
		lblDesc.setBorder(new EmptyBorder(0, 10, 0, 10));
		panel_5.add(lblDesc,BorderLayout.WEST);
		JScrollPane scrollPane_1 = new JScrollPane();
		panel_5.add(scrollPane_1,BorderLayout.CENTER);
		scrollPane_1.setMinimumSize(new Dimension(22, 50));
		scrollPane_1.setPreferredSize(new Dimension(3, 50));
		descArea = new JTextArea();
		descArea.setPreferredSize(new Dimension(0, 50));
		descArea.setMinimumSize(new Dimension(0, 50));
		scrollPane_1.setViewportView(descArea);
		
		Component verticalStrut_1 = Box.createVerticalStrut(20);
		verticalStrut_1.setPreferredSize(new Dimension(0, 10));
		verticalStrut_1.setMaximumSize(new Dimension(32767, 10));
		panel_2.add(verticalStrut_1);
		
		JPanel panel_6 = new JPanel();
		panel_2.add(panel_6);
		panel_6.setLayout(new BoxLayout(panel_6, BoxLayout.X_AXIS));
		
		Component horizontalGlue = Box.createHorizontalGlue();
		panel_6.add(horizontalGlue);
		
		JButton btnNewButton = new JButton("OK");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ins.dispose();
			}
		});
		panel_6.add(btnNewButton);
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		horizontalStrut.setPreferredSize(new Dimension(10, 0));
		panel_6.add(horizontalStrut);
		initTable();
	}
	
	public void initTable() {
		String[] row = new String[2];
		Branch curBr = ClassFactory.getInstance().getBranch(brID);
		String brName = curBr.getName();
		String iface = curBr.getIface();
		String fName = curBr.getFileName();
		int state = curBr.getState();
		String user = curBr.getUserName();
		Date started = curBr.getTime();
		String desc = curBr.getDesc();
		
		DefaultTableModel infoModel = new DefaultTableModel();
		infoModel.addColumn("Name");
		infoModel.addColumn("Value");
		row[0] = "Branch name";
		row[1] = brName;
		infoModel.addRow(row);
		
		if (iface==null) {
			row[0]="File name";
			row[1]=fName;
		}
		else { 
			row[0]="Interface";
			row[1]=iface;
		}
		infoModel.addRow(row);
		row[0]="State";
		switch (state) {
		case 0:
			row[1]="Created, not started yet";
			break;
		case 1:
			row[1]="Processing";
			break;
		case 2:
			row[1]="Paused";
			break;
		case 3:
			row[1]="Stoped";
			break;
		default:
			row[1]="Error, state undefined";
			break;
		}
		
		infoModel.addRow(row);
		
		row[0]="User";
		row[1]=user;
		infoModel.addRow(row);
		
		SimpleDateFormat sdf  = new SimpleDateFormat("dd.mm hh:mm:ss");
		row[0]="Created";
		row[1]=sdf.format(started);
		infoModel.addRow(row);
		
		row[0]="ID";
		row[1]=brID.toString();		
		table.setModel(infoModel);
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
		descArea.setText(desc);
	}
}
