package com.jubaka.sors.desktop.tcpAnalyse;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class SeparateSessionView extends JFrame {

	private JPanel contentPane;
	private SessionsListView slv;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SeparateSessionView frame = new SeparateSessionView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
*/
	/**
	 * Create the frame.
	 */
	public SeparateSessionView() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		slv = new SessionsListView();
		add(slv,BorderLayout.CENTER);
	}

	public SessionsListView getSlv() {
		return slv;
	}

	public void setSlv(SessionsListView slv) {
		this.slv = slv;
	}

}
