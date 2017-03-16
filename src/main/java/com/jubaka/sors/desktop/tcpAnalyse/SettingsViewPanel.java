package com.jubaka.sors.tcpAnalyse;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;

import com.jubaka.sors.factories.ClassFactory;
import com.jubaka.sors.limfo.LoadLimits;

public class SettingsViewPanel extends JPanel {
	LoadLimits ll = ClassFactory.getInstance().getLimits();
	/**
	 * Create the panel.
	 */
	public SettingsViewPanel() {
		setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		SViewListItem separSesView = new SViewListItem();
		separSesView.lblLabel.setText("Session view");
		separSesView.btnIntegrate.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Controller.showIntegratedSessionView();
				
			}
		});
		separSesView.btnSeparate.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Controller.showSeparateSessionView();
				
			}
		});
		if (ll.isSeparSessionView()) separSesView.btnSeparate.setSelected(true);
		else separSesView.btnIntegrate.setSelected(true);
		add(separSesView);
		
		SViewListItem separDataView = new SViewListItem();
		separDataView.lblLabel.setText("Session data view mode");
		separDataView.btnIntegrate.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Controller.showIntagratedDataView();
				
			}
		});
		separDataView.btnSeparate.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Controller.showSeparateDataView();
				
			}
		});
		
		if (ll.isSeparSesDataView()) separDataView.btnSeparate.setSelected(true);
		else separDataView.btnIntegrate.setSelected(true);
		add(separDataView);
		
		TriStateViewListItem sesView = new TriStateViewListItem();
		sesView.lblLabel.setText("Session view mode");
		sesView.btnCompaq.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Controller.setSessionViewCompaq();
				
			}
		});
		sesView.btnPanel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Controller.setSessionViewPanel();
				
			}
		});
		sesView.btnTable.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Controller.setSessionViewTable();
				
			}
		});
		
		switch (ll.getSessionViewMode()) {
		case 0:
			sesView.btnPanel.setSelected(true);
			break;
		case 1:
			sesView.btnCompaq.setSelected(true);
			break;
		case 2:
			sesView.btnTable.setSelected(true);
			break;

		default:
			break;
		}
		
		add(sesView);
		

	}
}
