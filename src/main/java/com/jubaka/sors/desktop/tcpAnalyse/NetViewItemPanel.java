package com.jubaka.sors.tcpAnalyse;

import com.jubaka.sors.factories.ClassFactory;
import com.jubaka.sors.sessions.Subnet;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.SwingUtilities;

import java.awt.Component;

import javax.swing.Box;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.border.MatteBorder;
import javax.swing.UIManager;

public class NetViewItemPanel extends JPanel {
	private Integer id;

	JLabel lblNetAddr = new JLabel("192.168.145.0/24");
	JPanel panel = new JPanel();
	NetViewItemPanel inst = this;
	Subnet net;
/*	InetAddress addr;
	Integer mask;
	Integer sesCount;
	Integer hostCount;
	Long upLoad;
	Long download;

*/
	/**
	 * Create the panel.
	 */
	public NetViewItemPanel(Subnet netItem) {
		this.id = netItem.getId();

		setPreferredSize(new Dimension(200, 30));

		// addMouseListener(new mouseAction());

		setMaximumSize(new Dimension(32767, 30));
		setMinimumSize(new Dimension(10, 55));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		panel.setBackground(UIManager.getColor("Button.background"));

		panel.setBorder(new CompoundBorder(new EtchedBorder(
				EtchedBorder.LOWERED, null, null), new EmptyBorder(0, 0, 0, 0)));
		panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				CompoundBorder border = new CompoundBorder(new EtchedBorder(
						EtchedBorder.LOWERED), new MatteBorder(1, 1, 2, 1,
						(Color) new Color(163, 184, 204)));

				panel.setBorder(border);

			}

			@Override
			public void mouseExited(MouseEvent e) {
				CompoundBorder border = new CompoundBorder(new EtchedBorder(
						EtchedBorder.LOWERED), new EmptyBorder(0, 0, 0, 0));

				panel.setBorder(border);

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				
				try {
					Runnable run = new Runnable() {
						
						@Override
						public void run() {
							if (net.isSelected()==true) return;
							Controller control;
							control = ClassFactory.getInstance().getController(id);

							control.changeCurrentNetView(inst);
							MainWin.instance.setNetActive(net);
							
						}
					};
					SwingUtilities.invokeLater(run);
					

				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		add(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

		Component verticalGlue = Box.createVerticalGlue();
		panel.add(verticalGlue);

		Component horizontalGlue_2 = Box.createHorizontalGlue();
		panel.add(horizontalGlue_2);

		Component horizontalStrut = Box.createHorizontalStrut(20);
		panel.add(horizontalStrut);

		panel.add(lblNetAddr);

		Component horizontalStrut_3 = Box.createHorizontalStrut(20);
		panel.add(horizontalStrut_3);

		Component horizontalGlue_1 = Box.createHorizontalGlue();
		panel.add(horizontalGlue_1);
		try {
			setSubnet(netItem);
			lblNetAddr.setText(net.getSubnet().getHostAddress()+"/"+net.getSubnetMask());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void setSubnet(Subnet net) {
		this.net = net;

	}


}
