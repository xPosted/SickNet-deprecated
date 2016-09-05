package com.jubaka.sors.tcpAnalyse;

import com.jubaka.sors.factories.ClassFactory;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.BoxLayout;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.Component;

import javax.swing.Box;
import javax.swing.border.EtchedBorder;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.Dimension;

import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.net.UnknownHostException;

public class NetAdd extends JFrame {
	protected final Integer id;
	private JPanel contentPane;
	private final JPanel panel_1 = new JPanel();
	private JTextField textField;
	static NetAdd frame;
	/**
	 * Launch the application.
	 */
	

	/**
	 * Create the frame.
	 */
	public NetAdd(Integer id) {
		this.id=id;
		setTitle("Nets controll panel");
		frame = this;
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setBounds(100, 100, 283, 97);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.add(panel_1);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));
		
		Component horizontalGlue_1 = Box.createHorizontalGlue();
		panel_1.add(horizontalGlue_1);
		
		JLabel lblAddNewNetwork = new JLabel("Add new Network");
		lblAddNewNetwork.setFont(new Font("Liberation Mono", Font.BOLD, 16));
		panel_1.add(lblAddNewNetwork);
		
		Component horizontalGlue = Box.createHorizontalGlue();
		panel_1.add(horizontalGlue);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_2.setMaximumSize(new Dimension(32767, 30));
		panel.add(panel_2);
		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.X_AXIS));
		
		textField = new JTextField();
		textField.setMaximumSize(new Dimension(2147483647, 25));
		panel_2.add(textField);
		textField.setColumns(10);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		panel_2.add(horizontalStrut_1);
		
		JButton btnNewButton = new JButton("ADD");
		btnNewButton.addActionListener(new Action(id));
		panel_2.add(btnNewButton);
	}
	class 	Action implements ActionListener {
		private Integer id;
		
		public Action(Integer id) {
			this.id=id;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			String addr = textField.getText();
			String[] buf =addr.split("/");
			if (buf.length==2) {
				addr=buf[0];
				System.out.println(buf[1]);
				Integer mask = Integer.parseInt(buf[1]);
				System.out.println(addr+"/"+mask);
				try {
					ClassFactory.getInstance().getController(id).addNet(addr, mask);
					frame.dispose();
					
				} catch (UnknownHostException ee) {
					ee.printStackTrace();
				}
				
			} else JOptionPane.showMessageDialog(null, "Invalid net address");
			
		}
		
	}

}
