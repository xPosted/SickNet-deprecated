package com.jubaka.sors.desktop.tcpAnalyse;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.BoxLayout;

import java.awt.Dimension;

import javax.swing.JLabel;

import java.awt.Component;

import javax.swing.Box;

import java.awt.Font;

import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.border.EtchedBorder;

import com.jubaka.sors.desktop.sessions.Session;
import com.jubaka.sors.desktop.sessions.SessionDataUpdater;
import com.jubaka.sors.desktop.tcpAnalyse.httpView.HTTPView;

import javax.swing.JTabbedPane;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SessionInfoView extends JFrame {

	private JPanel contentPane;
	private JTextArea txtAreaDstData;
	private JTextArea txtAreaSrcData;
	private Session sesStub;
	private Integer brId;
	private JPanel panelContainer;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
				//	SessionInfoView frame = new SessionInfoView();
				//	frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public SessionInfoView(Session ses) {
		sesStub = ses;
		this.brId = MainWin.instance.getId();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.LIGHT_GRAY);
		panel_1.setBorder(new CompoundBorder(new LineBorder(new Color(0, 0, 0), 1, true), new EtchedBorder(EtchedBorder.LOWERED, null, null)));
		panel_1.setMaximumSize(new Dimension(32767, 30));
		panel_1.setMinimumSize(new Dimension(15, 30));
		panel_1.setPreferredSize(new Dimension(0, 30));
		
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));
		
		Component horizontalGlue = Box.createHorizontalGlue();
		panel_1.add(horizontalGlue);
		
		JLabel lblSessionInfo = new JLabel("Session info");
		lblSessionInfo.setFont(new Font("DejaVu Serif", Font.BOLD, 18));
		panel_1.add(lblSessionInfo);
		
		Component horizontalGlue_1 = Box.createHorizontalGlue();
		panel_1.add(horizontalGlue_1);
		panel.add(panel_1);
		ButtonGroup radioGroup = new ButtonGroup();
		JRadioButton rdbtnHttp = new JRadioButton("HTTP");
		rdbtnHttp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("http");
				setHTTPMode();
				
			}
		});
		radioGroup.add(rdbtnHttp);
		panel_1.add(rdbtnHttp);
		
		JRadioButton rdbtnRaw = new JRadioButton("RAW");
		rdbtnRaw.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("raw");
				setRawMode();
			}
		});

		rdbtnRaw.setSelected(true);
		panel_1.add(rdbtnRaw);
		radioGroup.add(rdbtnRaw);
		
		JRadioButton rdbtnChart = new JRadioButton("Chart");
		rdbtnChart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setChartMode();
			}
		});
		radioGroup.add(rdbtnChart);
		panel_1.add(rdbtnChart);
		SesItemPan mainInfo = new SesItemPan(ses,true);
		panel.add(mainInfo);
		
			panelContainer = new JPanel();
			panel.add(panelContainer);
			panelContainer.setLayout(new BorderLayout(0, 0));
			setRawMode(); // by default
		//	setHTTPMode();
	}
	
	private void initRawData() {
		ArrayList<String> srcdata = (ArrayList<String>) Controller.decodeData(sesStub.getSrcData());
		ArrayList<String> dstdata = (ArrayList<String>) Controller.decodeData(sesStub.getDstData());
		SessionDataUpdater dstSdu = new SessionDataUpdater(txtAreaDstData, brId, dstdata);
		SessionDataUpdater srcSdu = new SessionDataUpdater(txtAreaSrcData, brId, srcdata);
		Thread th = new Thread(dstSdu);
		Thread ht = new Thread(srcSdu);
		th.start();
		ht.start();
	}
	
	private void setRawMode() {
		panelContainer.removeAll();
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.BOTTOM);
		panelContainer.add(tabbedPane);
		JPanel srcDataPanel = new JPanel();
		JPanel dstDataPanel = new JPanel();
		tabbedPane.addTab("Source", srcDataPanel);
		srcDataPanel.setLayout(new BorderLayout(0, 0));
		
		txtAreaSrcData = new JTextArea();
		srcDataPanel.add(txtAreaSrcData, BorderLayout.CENTER);
		tabbedPane.addTab("Destination", dstDataPanel);
		dstDataPanel.setLayout(new BorderLayout(0, 0));
		
		txtAreaDstData = new JTextArea();
		dstDataPanel.add(txtAreaDstData, BorderLayout.CENTER);
		initRawData();
		panelContainer.revalidate();
	}
	
	private void setHTTPMode() {
		panelContainer.removeAll();
	//	HTTPTreeView httpTree = new HTTPTreeView(sesStub);
		HTTPView httpTree = new HTTPView(sesStub);
		panelContainer.add(httpTree, BorderLayout.CENTER);
		panelContainer.revalidate();
	}
	private void setChartMode() {
		panelContainer.removeAll();
			SessionChartPanel scp = new SessionChartPanel(sesStub);
			panelContainer.add(scp, BorderLayout.CENTER);
			panelContainer.revalidate();
	}

}
