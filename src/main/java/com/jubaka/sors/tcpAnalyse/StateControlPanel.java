package com.jubaka.sors.tcpAnalyse;

import java.awt.Component;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import com.jubaka.sors.factories.ClassFactory;
import com.jubaka.sors.sessions.Branch;


import java.awt.Dimension;

import javax.swing.border.LineBorder;

import java.awt.Color;

import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;

public class StateControlPanel extends JPanel {
	
	private JLabel lblSrcType;
	private JLabel lblDS;
	private JButton btnStart;
	private Branch currentBranch;
	private JLabel lblStatus;
	private JButton btnStop;
	private Controller control;
	private JButton btnInfo;
	private JButton btnDel;
	
	
	/**
	 * Create the panel.
	 */
	public StateControlPanel() {
		setMaximumSize(new Dimension(32767, 45));
		setBorder(new EmptyBorder(5, 5, 5, 5));
		
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(5);
		add(horizontalStrut_1);
		
		lblStatus = new JLabel("NULL");
		lblStatus.setPreferredSize(new Dimension(70, 35));
		lblStatus.setMinimumSize(new Dimension(70, 35));
		lblStatus.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 14));
		lblStatus.setHorizontalAlignment(SwingConstants.CENTER);
		lblStatus.setBorder(new CompoundBorder(new LineBorder(new Color(99, 130, 191), 1, true), new EtchedBorder(EtchedBorder.LOWERED, null, null)));
		lblStatus.setMaximumSize(new Dimension(90, 35));
		add(lblStatus);
		
		Component horizontalStrut_2 = Box.createHorizontalStrut(20);
		add(horizontalStrut_2);
		
		lblSrcType = new JLabel("Data source:");
		add(lblSrcType);
		
		Component horizontalStrut = Box.createHorizontalStrut(10);
		add(horizontalStrut);
		
		lblDS = new JLabel("n/a");
		lblDS.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 16));
		add(lblDS);
		
		Component horizontalStrut_10 = Box.createHorizontalStrut(20);
		add(horizontalStrut_10);
				
				Component horizontalGlue = Box.createHorizontalGlue();
				add(horizontalGlue);
		
				btnStart = new JButton("start");
				btnStart.setEnabled(false);
				add(btnStart);
				btnStart.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Runnable run = new Runnable() {
							
							@Override
							public void run() {
								
								if (currentBranch.getState() == 1) {
									control.pauseBranch(currentBranch);
								} else {
									control.startProcessing(currentBranch);
									
								}
								
							}
						};
						SwingUtilities.invokeLater(run);
						

					}
				});
				btnStart.setMargin(new Insets(2, 2, 2, 2));
				btnStart.setFont(new Font("Dialog", Font.BOLD, 12));
				
						Component horizontalStrut_33 = Box.createHorizontalStrut(4);
						add(horizontalStrut_33);
								
								btnStop = new JButton("stop");
								btnStop.setFont(new Font("Dialog", Font.BOLD, 12));
								btnStop.setEnabled(false);
								btnStop.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent arg0) {
										
										if (currentBranch.getState() == 1) 
											control.stopBranch(currentBranch);
									}
								});
								btnStop.setMargin(new Insets(2, 3, 2, 3));
								add(btnStop);
								
								Component horizontalStrut_3 = Box.createHorizontalStrut(4);
								add(horizontalStrut_3);
						
								btnInfo = new JButton("info");
								btnInfo.setEnabled(false);
								add(btnInfo);
								btnInfo.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										BranchInfoView info = new BranchInfoView(currentBranch.getId());
										info.setVisible(true);
									}
								});
								btnInfo.setFont(new Font("Dialog", Font.BOLD, 12));
								btnInfo.setMargin(new Insets(2, 2, 2, 2));
								
										Component horizontalStrut_34 = Box.createHorizontalStrut(4);
										add(horizontalStrut_34);
										
												btnDel = new JButton("del");
												btnDel.setEnabled(false);
												add(btnDel);
												btnDel.addActionListener(new ActionListener() {
													public void actionPerformed(ActionEvent arg0) {
														int res = JOptionPane.showConfirmDialog(null,"Delete current branch and all corresponding data in home folder?");
														if (res == 0) {
															ClassFactory.getInstance().deleteBranch(currentBranch.getId());
															Controller.resetView();
														}
														//0 yes
														//1 no
														//2 cancel
													}
												});
												btnDel.setFont(new Font("Dialog", Font.BOLD, 12));
												btnDel.setMargin(new Insets(2, 2, 2, 2));
												
												Component horizontalStrut_4 = Box.createHorizontalStrut(10);
												add(horizontalStrut_4);

	}
	
	public void onBranchStateChanged(Branch br) {
		
		if (br == null) {
			btnStart.setEnabled(false);
			btnStop.setEnabled(false);
			btnInfo.setEnabled(false);
			btnDel.setEnabled(false);
			lblStatus.setText("NULL");
			btnStart.setText("Start");
			return;
		}
		if (br == currentBranch) {
			switch (currentBranch.getState()) {
			case 0:
				btnStart.setEnabled(true);
				btnStop.setEnabled(false);
				btnInfo.setEnabled(true);
				btnDel.setEnabled(true);
				lblStatus.setText("Created");
				btnStart.setText("Start");
				break;
			case 1:
				btnStart.setEnabled(true);
				btnStop.setEnabled(true);
				btnInfo.setEnabled(true);
				btnDel.setEnabled(true);
				lblStatus.setText("Live");
				btnStart.setText("Pause");
				
				break;
			case 2:
				btnStart.setEnabled(true);
				btnStop.setEnabled(true);
				btnInfo.setEnabled(true);
				btnDel.setEnabled(true);
				lblStatus.setText("Paused");
				btnStart.setText("Start");
				break;
			case 3:
				btnStart.setEnabled(false);
				btnStop.setEnabled(false);
				btnInfo.setEnabled(true);
				btnDel.setEnabled(true);
				lblStatus.setText("Stoped");
				btnStart.setText("Start");
				break;
			default:
				
				break;
			}
		}
	}
	
	public void setBranch(Branch br) {
		currentBranch =  br;
		control = ClassFactory.getInstance().getController(br.getId());
		String ds = br.getIface();
		if (ds == null) ds = br.getFileName();
		lblDS.setText(ds);
		onBranchStateChanged(br);
	}
}
