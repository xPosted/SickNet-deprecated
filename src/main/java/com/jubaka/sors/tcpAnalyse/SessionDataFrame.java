package com.jubaka.sors.tcpAnalyse;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

public class SessionDataFrame extends JFrame {

	private JPanel contentPane;
	public JTextArea txtrSrcData;
	public JTextArea txtrDstData;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SessionDataFrame frame = new SessionDataFrame();
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
	public SessionDataFrame() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		SessionDataPanel sdp = new SessionDataPanel();
		contentPane.add(sdp, BorderLayout.CENTER);
		this.txtrDstData = sdp.txtrDstData;
		this.txtrSrcData = sdp.txtrSrcData;
	}

}
