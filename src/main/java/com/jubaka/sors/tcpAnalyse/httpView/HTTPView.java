package com.jubaka.sors.tcpAnalyse.httpView;

import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellRenderer;

import com.jubaka.sors.protocol.http.HTTP;
import com.jubaka.sors.sessions.Session;
import javax.swing.BoxLayout;


public class HTTPView extends JPanel {

	/**
	 * Create the panel.
	 */
	public HTTPView(Session ses) {
		setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);
		JPanel panel = new JPanel();
		
		for (HTTP http : ses.getHTTPList()) {
			HTTPPanelBody pBody = new HTTPPanelBody(http);
			HTTPPanelHead pHead = new HTTPPanelHead(http,pBody);
			
			panel.add(pHead);
			panel.add(pBody);
		
		}
		
		
		scrollPane.setViewportView(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
	}
}


