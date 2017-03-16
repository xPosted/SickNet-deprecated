package com.jubaka.sors.desktop.tcpAnalyse;

import javax.swing.JPanel;

import java.awt.BorderLayout;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import java.awt.Dimension;
import java.awt.Component;

import javax.swing.Box;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import java.awt.Font;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import com.jubaka.sors.desktop.sessions.Subnet;

public class NetInfoPanel extends JPanel implements Observer {

	/**
	 * Create the panel.
	 */

	private Subnet net = null;
	private JLabel lblNetAddr = new JLabel("0.0.0.0/0");
	private JLabel lblSA = new JLabel("Ses/Addr");
	private JLabel lblUD = new JLabel("Up/Down (mB)");
	private JLabel lblActSA = new JLabel("Active Ses/Addr");

	JLabel lblActSAdata = new JLabel("0/0");
	JLabel lblSAdata = new JLabel("0/0");
	JLabel lblUDdata = new JLabel("0/0");

	public NetInfoPanel() {
		setMinimumSize(new Dimension(10, 140));
		setPreferredSize(new Dimension(350, 145));
		setMaximumSize(new Dimension(32767, 145));
		setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null,
				null));
		add(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_1.setPreferredSize(new Dimension(10, 30));
		panel_1.setMinimumSize(new Dimension(10, 20));
		panel.add(panel_1);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));

		Component horizontalGlue = Box.createHorizontalGlue();
		panel_1.add(horizontalGlue);

		lblNetAddr.setFont(new Font("Dialog", Font.BOLD, 16));
		panel_1.add(lblNetAddr);

		Component horizontalGlue_1 = Box.createHorizontalGlue();
		panel_1.add(horizontalGlue_1);

		JSeparator separator_8 = new JSeparator();
		panel.add(separator_8);

		JPanel panel_2 = new JPanel();
		panel.add(panel_2);
		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.X_AXIS));

		JPanel panel_3 = new JPanel();
		panel_3.setMaximumSize(new Dimension(150, 32767));
		panel_3.setPreferredSize(new Dimension(150, 10));
		panel_2.add(panel_3);

		lblSA.setFont(new Font("Dialog", Font.PLAIN, 12));
		panel_3.add(lblSA);

		JSeparator separator = new JSeparator();
		separator.setMaximumSize(new Dimension(2, 32767));
		separator.setOrientation(SwingConstants.VERTICAL);
		panel_2.add(separator);

		JPanel panel_4 = new JPanel();
		panel_2.add(panel_4);

		lblSAdata.setFont(new Font("Dialog", Font.BOLD, 14));
		panel_4.add(lblSAdata);

		JSeparator separator_7 = new JSeparator();
		panel.add(separator_7);

		JPanel panel_5 = new JPanel();
		panel.add(panel_5);
		panel_5.setLayout(new BoxLayout(panel_5, BoxLayout.X_AXIS));

		JPanel panel_8 = new JPanel();
		panel_8.setMaximumSize(new Dimension(150, 32767));
		panel_8.setPreferredSize(new Dimension(150, 10));
		panel_5.add(panel_8);

		lblActSA.setFont(new Font("Dialog", Font.PLAIN, 12));
		panel_8.add(lblActSA);

		JSeparator separator_1 = new JSeparator();
		separator_1.setOrientation(SwingConstants.VERTICAL);
		separator_1.setMaximumSize(new Dimension(2, 32767));
		panel_5.add(separator_1);

		JPanel panel_9 = new JPanel();
		panel_5.add(panel_9);

		lblActSAdata.setFont(new Font("Dialog", Font.BOLD, 14));
		panel_9.add(lblActSAdata);

		JSeparator separator_4 = new JSeparator();
		panel.add(separator_4);

		JPanel panel_6 = new JPanel();
		panel.add(panel_6);
		panel_6.setLayout(new BoxLayout(panel_6, BoxLayout.X_AXIS));

		JPanel panel_10 = new JPanel();
		panel_10.setPreferredSize(new Dimension(150, 10));
		panel_10.setMaximumSize(new Dimension(150, 32767));
		panel_6.add(panel_10);

		panel_10.add(lblUD);
		lblUD.setFont(new Font("Dialog", Font.PLAIN, 12));

		JSeparator separator_3 = new JSeparator();
		separator_3.setOrientation(SwingConstants.VERTICAL);
		separator_3.setMaximumSize(new Dimension(2, 32767));
		panel_6.add(separator_3);

		JPanel panel_11 = new JPanel();
		panel_6.add(panel_11);

		lblUDdata.setFont(new Font("Dialog", Font.BOLD, 14));
		panel_11.add(lblUDdata);

		JSeparator separator_6 = new JSeparator();
		panel.add(separator_6);

		JPanel panel_7 = new JPanel();
		panel.add(panel_7);
		panel_7.setLayout(new BoxLayout(panel_7, BoxLayout.X_AXIS));

		JSeparator separator_2 = new JSeparator();
		separator_2.setOrientation(SwingConstants.VERTICAL);
		separator_2.setMaximumSize(new Dimension(2, 32767));
		panel_7.add(separator_2);

		JSeparator separator_5 = new JSeparator();
		panel.add(separator_5);

	}

	public void setSubnet(Subnet net) {
		if (this.net != null)	this.net.deleteObserver(this);
		if (net==null) {
			lblNetAddr.setText("0.0.0.0");
			this.net = null;
		} else {
			this.net = net;
			this.net.addObserver(this);
			lblNetAddr.setText(net.getSubnet().getHostAddress() + "/"
					+ net.getSuffix());
		}
		updateInfo();
	

	}
	
	public void reset() {
		if (this.net != null) {
			net.deleteObserver(this);
		}
		net = null;
		lblNetAddr.setText("0.0.0.0/0");
		lblSA.setText("Ses/Addr");
		lblUD.setText("Up/Down (mB)");
		lblActSA.setText("Active Ses/Addr");

		lblActSAdata.setText("0/0");
		lblSAdata.setText("0/0");
		lblUDdata.setText("0/0");
		
	}

	public void updateInfo() {
		Integer liveSes = 0;
		Integer liveAddr = 0;
		Integer AddrCount = 0;
		Integer SesCount = 0;
		Long up = (long) 0;
		Long down = (long) 0;

		
		if (net!=null) {
			liveSes = net.getLiveSesCount();
			liveAddr = net.getLiveIps().size();
			AddrCount = net.getIps().size();
			SesCount = net.getSesCount();
			down = net.getInputData();
			up = net.getOutptData();
			
		}
		String dataDownHumanSize = Controller.processSize(down,2); // down in stored in 
		String dataUpHumanSize = Controller.processSize(up,2);
		lblSAdata.setText(SesCount + "/" + AddrCount);
		lblActSAdata.setText(liveSes + "/" + liveAddr);
		lblUDdata.setText(dataUpHumanSize + "/" + dataDownHumanSize);
		
		/*
		 * MainWin mainWin = MainWin.instance; DefaultListModel<String> model =
		 * new DefaultListModel<String>(); for (InetAddress item :
		 * net.getLiveIps()) { model.addElement(item.getHostAddress()); }
		 * mainWin.liveIPlist.setModel(model); DefaultListModel<String> model2 =
		 * new DefaultListModel<String>(); for (InetAddress item : net.getIps())
		 * model2.addElement(item.getHostAddress());
		 * mainWin.ipLst.setModel(model2);
		 */

	}

	@Override
	public void update(Observable arg0, Object arg1) {
		Runnable run = new Runnable() {
			
			@Override
			public void run() {
				updateInfo();
				
			}
		};
			SwingUtilities.invokeLater(run);
		

	}

}
