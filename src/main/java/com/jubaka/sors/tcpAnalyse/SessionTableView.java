package com.jubaka.sors.tcpAnalyse;

import com.jubaka.sors.sessions.Session;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;
import java.text.SimpleDateFormat;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

import javax.swing.JButton;
import javax.swing.border.LineBorder;

import java.awt.Color;

public class SessionTableView extends JScrollPane {
	private JTable table;
	private DefaultTableModel model;

	/**
	 * Create the panel.
	 */
	public SessionTableView() {
	//	setBorder(new LineBorder(Color.RED, 5));
	//	setLayout(new BorderLayout(0, 0));
		
		table = new JTable();
		table.setFillsViewportHeight(true);
		initTable();
	//	add(table,BorderLayout.CENTER);
		setViewportView(table);
		
		

	}
	
	private void initTable() {
		model = new DefaultTableModel();
	//	model.setColumnCount(10);
		model.addColumn("Started");
		model.addColumn("Finished");
		model.addColumn("Src Host");
		model.addColumn("Src Port");
		model.addColumn("Dst Host");
		model.addColumn("Dst Port");
		model.addColumn("Src Data");
		model.addColumn("Dst Data");
		model.addColumn("HTTP");
		model.addColumn("Data catch");
		model.addColumn("CheckBox");
		
		table.setModel(model);
		
	}
	public void addSession(Session ses) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yy hh:mm:ss");
		
		String[] row = new String[10];
		row[0] = sdf.format(ses.getEstablished());
		if (ses.getClosed() == null) row[1] = "Active"; else 
			row[1] = sdf.format(ses.getClosed());
		row[2] = ses.getSrcIP().getAddr().getHostAddress();
		row[3] = ses.getSrcP().toString();
		row[4] = ses.getDstIP().getAddr().getHostAddress();
		row[5] = ses.getDstP().toString();
		row[6] = Controller.processSize(ses.getSrcDataLen(),1);
		row[7] = Controller.processSize(ses.getDstDataLen(),1);
		row[8] = Integer.toString(ses.getHTTPList().size());
		model.addRow(row);
		table.updateUI();
	}
	
	public void clearTable() {
		/*
		Integer count = model.getRowCount();
		int i =0;
		while (i < count) {
			model.removeRow(0);
			i++;
		}
		table.revalidate();
		*/
	}
	

}

class CustomTableModel extends AbstractTableModel {

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
