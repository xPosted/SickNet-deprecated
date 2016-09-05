package com.jubaka.sors.tcpAnalyse;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Dimension;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.JButton;

public class SessionSearch extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	/**
	 * @wbp.nonvisual location=84,347
	 */
	private final Component verticalStrut_2 = Box.createVerticalStrut(20);

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SessionSearch frame = new SessionSearch();
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
	public SessionSearch() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 350, 307);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));
		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2);
		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.Y_AXIS));
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_3.setPreferredSize(new Dimension(10, 30));
		panel_3.setMaximumSize(new Dimension(32767, 45));
		panel_2.add(panel_3);
		panel_3.setLayout(new BoxLayout(panel_3, BoxLayout.X_AXIS));
		
		JPanel panel_6 = new JPanel();
		panel_3.add(panel_6);
		panel_6.setLayout(new BoxLayout(panel_6, BoxLayout.X_AXIS));
		
		Component horizontalStrut_3 = Box.createHorizontalStrut(10);
		horizontalStrut_3.setMaximumSize(new Dimension(10, 30));
		panel_6.add(horizontalStrut_3);
		
		JLabel lblNewLabel = new JLabel("Src port");
		panel_6.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setMinimumSize(new Dimension(4, 5));
		textField.setPreferredSize(new Dimension(20, 20));
		textField.setMaximumSize(new Dimension(60, 25));
		panel_6.add(textField);
		textField.setColumns(10);
		
		Component horizontalStrut = Box.createHorizontalStrut(10);
		panel_3.add(horizontalStrut);
		
		JPanel panel_7 = new JPanel();
		panel_3.add(panel_7);
		panel_7.setLayout(new BoxLayout(panel_7, BoxLayout.X_AXIS));
		
		JLabel lblNewLabel_1 = new JLabel("Dst port");
		panel_7.add(lblNewLabel_1);
		
		textField_1 = new JTextField();
		textField_1.setHorizontalAlignment(SwingConstants.CENTER);
		textField_1.setMaximumSize(new Dimension(60, 25));
		panel_7.add(textField_1);
		textField_1.setColumns(10);
		
		Component verticalStrut_3 = Box.createVerticalStrut(10);
		panel_2.add(verticalStrut_3);
		
		JPanel panel_5 = new JPanel();
		panel_5.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_2.add(panel_5);
		panel_5.setLayout(new BoxLayout(panel_5, BoxLayout.X_AXIS));
		
		Component horizontalStrut_4 = Box.createHorizontalStrut(10);
		horizontalStrut_4.setMaximumSize(new Dimension(20, 30));
		panel_5.add(horizontalStrut_4);
		
		JLabel lblNewLabel_2 = new JLabel("Src address");
		panel_5.add(lblNewLabel_2);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		horizontalStrut_1.setMaximumSize(new Dimension(20, 30));
		panel_5.add(horizontalStrut_1);
		
		textField_2 = new JTextField();
		textField_2.setHorizontalAlignment(SwingConstants.CENTER);
		textField_2.setPreferredSize(new Dimension(280, 25));
		textField_2.setMinimumSize(new Dimension(80, 25));
		textField_2.setMaximumSize(new Dimension(300, 25));
		panel_5.add(textField_2);
		textField_2.setColumns(10);
		
		Component verticalStrut = Box.createVerticalStrut(10);
		panel_2.add(verticalStrut);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_2.add(panel_4);
		panel_4.setLayout(new BoxLayout(panel_4, BoxLayout.X_AXIS));
		
		Component horizontalStrut_5 = Box.createHorizontalStrut(10);
		horizontalStrut_5.setMaximumSize(new Dimension(20, 30));
		panel_4.add(horizontalStrut_5);
		
		JLabel lblDstAddress = new JLabel("Dst address");
		panel_4.add(lblDstAddress);
		
		Component horizontalStrut_2 = Box.createHorizontalStrut(20);
		horizontalStrut_2.setMaximumSize(new Dimension(20, 30));
		panel_4.add(horizontalStrut_2);
		
		textField_3 = new JTextField();
		textField_3.setHorizontalAlignment(SwingConstants.CENTER);
		textField_3.setMinimumSize(new Dimension(200, 25));
		textField_3.setPreferredSize(new Dimension(200, 25));
		textField_3.setMaximumSize(new Dimension(300, 25));
		panel_4.add(textField_3);
		textField_3.setColumns(10);
		
		Component verticalStrut_1 = Box.createVerticalStrut(20);
		panel.add(verticalStrut_1);
		
		JPanel panel_8 = new JPanel();
		panel.add(panel_8);
		panel_8.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_9 = new JPanel();
		panel_8.add(panel_9, BorderLayout.SOUTH);
		panel_9.setLayout(new BoxLayout(panel_9, BoxLayout.X_AXIS));
		
		Component horizontalGlue = Box.createHorizontalGlue();
		panel_9.add(horizontalGlue);
		
		JButton btnNewButton = new JButton("cancel");
		panel_9.add(btnNewButton);
		
		Component horizontalStrut_6 = Box.createHorizontalStrut(20);
		panel_9.add(horizontalStrut_6);
		
		JButton btnNewButton_1 = new JButton("search");
		panel_9.add(btnNewButton_1);
		
		Component horizontalStrut_7 = Box.createHorizontalStrut(10);
		panel_9.add(horizontalStrut_7);
	}
}
