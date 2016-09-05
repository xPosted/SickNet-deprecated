package com.jubaka.sors.tcpAnalyse.httpView;

import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.SwingUtilities;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.EmptyBorder;

import java.awt.Dimension;
import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;

import java.awt.Color;

import javax.swing.border.LineBorder;
import javax.swing.JSeparator;
import javax.swing.UIManager;

import com.jubaka.sors.protocol.http.HTTP;
import com.jubaka.sors.protocol.http.HTTPRequest;
import com.jubaka.sors.protocol.http.HTTPResponse;

import java.awt.Component;

import javax.swing.Box;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class HTTPPanelHead extends JPanel {
	private HTTPPanelBody body = null;

	/**
	 * Create the panel.
	 */
	public HTTPPanelHead(HTTP http, HTTPPanelBody body) {
		setMaximumSize(new Dimension(32767, 40));
		
		this.body = body;
		String type = null;
		String url = null;
		if (http instanceof HTTPRequest) {
			HTTPRequest httpreq = (HTTPRequest) http;
			type  = httpreq.getRequestMethod();
			url = httpreq.getRequestUrl();
		}
		if (http instanceof HTTPResponse) {
			HTTPResponse httpresp = (HTTPResponse) http;
			type = "Response";
			url = httpresp.getRequestUrl();
		}
			
		
		setBackground(Color.GRAY);
		setForeground(Color.GRAY);
		setPreferredSize(new Dimension(477, 40));
		setBorder(new CompoundBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null), new CompoundBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), new EmptyBorder(2, 5, 2, 5))));
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		JPanel panel = new JPanel();
		panel.setBackground(UIManager.getColor("CheckBoxMenuItem.disabledForeground"));
		panel.setMaximumSize(new Dimension(100, 32767));
		panel.setPreferredSize(new Dimension(80, 10));
		add(panel);
		
		panel.setLayout(new BorderLayout(0, 0));
		
		JLabel lblType = new JLabel(type);
		lblType.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				bodyToggle();
			}
		});
		lblType.setBackground(Color.LIGHT_GRAY);
		lblType.setMaximumSize(new Dimension(100, 15));
		lblType.setPreferredSize(new Dimension(70, 15));
		lblType.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		lblType.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblType, BorderLayout.CENTER);
		
		JSeparator separator = new JSeparator();
		separator.setMaximumSize(new Dimension(2, 32767));
		add(separator);
		
		JPanel panel_1 = new JPanel();
		add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JLabel lblUrl = new JLabel(url);
		lblUrl.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				bodyToggle();
			}
		});
		panel_1.add(lblUrl, BorderLayout.CENTER);
		lblUrl.setPreferredSize(new Dimension(0, 40));
		lblUrl.setMinimumSize(new Dimension(0, 40));
		lblUrl.setHorizontalAlignment(SwingConstants.LEFT);

	}

	private void bodyToggle() {
		SwingUtilities.invokeLater( new Runnable() {
			
			@Override
			public void run() {
				if (body.isVisible()) {
					body.setVisible(false);
				} else {
					body.setVisible(true);
				}
				body.validate();
				
			}
		});
		
	}
	
	public HTTPPanelBody getBody() {
		return body;
	}

	public void setBody(HTTPPanelBody body) {
		this.body = body;
	}
}
