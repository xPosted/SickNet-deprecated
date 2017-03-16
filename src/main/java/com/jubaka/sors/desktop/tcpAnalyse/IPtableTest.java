package com.jubaka.sors.desktop.tcpAnalyse;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.BoxLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class IPtableTest extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTable table_1;
	private JPanel panel_1;
	private JButton btnNewButton;
	private JButton btnNewButton_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					IPtableTest frame = new IPtableTest();
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
	public IPtableTest() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 613, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		JScrollPane scrollPane = new JScrollPane();
		panel.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		panel.add(scrollPane_1);
		
		table_1 = new JTable();
		scrollPane_1.setViewportView(table_1);
		
		panel_1 = new JPanel();
		panel.add(panel_1);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.Y_AXIS));
		
		btnNewButton = new JButton("New button");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				model.addRow(new String[]{"123",".","345",".","453",".","234"});
			}
		});
		panel_1.add(btnNewButton);
		
		btnNewButton_1 = new JButton("New button");
		panel_1.add(btnNewButton_1);
		initTables();
	}
	
	public void initTables() {
		DefaultTableModel model = new DefaultTableModel();
		model.setColumnCount(7);
		model.addRow(new String[]{"1",".","2",".","3",".","4"});
		model.addRow(new String[]{"192",".","168",".","0",".","12"});
		model.addRow(new String[]{"123",".","345",".","453",".","234"});
		model.addRow(new String[]{"1",".","2",".","3",".","4"});
		table.setModel(model);
		table.setFillsViewportHeight(true);
		table.setShowGrid(false);
		
		DefaultTableModel model2 = new DefaultTableModel();
		model2.addRow(new String[]{"123","345","453","234"});
		model2.addRow(new String[]{"1","2","3","4"});
		model2.addRow(new String[]{"192","168","0","12"});
		model2.addRow(new String[]{"1","2","3","4"});
		table_1.setModel(model2);
		table_1.setFillsViewportHeight(true);
		
		
		DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
		cellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		cellRenderer.setVerticalAlignment(SwingConstants.CENTER);
		
		
		TableColumnModel tcm = table.getColumnModel();
		tcm.getColumn(0).setCellRenderer(cellRenderer);
		tcm.getColumn(1).setCellRenderer(cellRenderer);
		tcm.getColumn(2).setCellRenderer(cellRenderer);
		tcm.getColumn(3).setCellRenderer(cellRenderer);
		tcm.getColumn(4).setCellRenderer(cellRenderer);
		tcm.getColumn(5).setCellRenderer(cellRenderer);
		tcm.getColumn(6).setCellRenderer(cellRenderer);
		
		
		tcm.getColumn(1).setMaxWidth(2);
		tcm.getColumn(1).setMinWidth(2);
		tcm.getColumn(1).setPreferredWidth(2);
		
		tcm.getColumn(3).setMaxWidth(2);
		tcm.getColumn(3).setMinWidth(2);
		tcm.getColumn(3).setPreferredWidth(2);
		
		tcm.getColumn(5).setMaxWidth(2);
		tcm.getColumn(5).setMinWidth(2);
		tcm.getColumn(5).setPreferredWidth(2);
		
	}
}
