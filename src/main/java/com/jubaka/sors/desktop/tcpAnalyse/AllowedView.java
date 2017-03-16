package tcpAnalyse;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.BoxLayout;

import java.awt.Component;

import javax.swing.Box;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JSeparator;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.DefaultListModel;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AllowedView extends JFrame {

	private JPanel contentPane;
	private final JScrollPane scrollPane = new JScrollPane();
	private JTextField textField;
	private HashSet<String> dataList;
	private JList list;
//getClass()
	/**
	 * Create the frame.
	 */
	public AllowedView(Set<String> data,String text) {
		dataList = (HashSet<String>) data;
		
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
		panel_1.setBorder(new CompoundBorder(new EtchedBorder(EtchedBorder.RAISED, null, null), new LineBorder(new Color(0, 0, 0), 1, true)));
		panel_1.setPreferredSize(new Dimension(10, 40));
		panel_1.setMinimumSize(new Dimension(10, 40));
		panel_1.setBackground(Color.WHITE);
		panel.add(panel_1);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));
		
		Component horizontalGlue = Box.createHorizontalGlue();
		panel_1.add(horizontalGlue);
		
		JLabel lblAllowedIps = new JLabel("Allowed");
		lblAllowedIps.setFont(new Font("Dialog", Font.BOLD, 18));
		panel_1.add(lblAllowedIps);
		
		Component horizontalStrut_5 = Box.createHorizontalStrut(10);
		panel_1.add(horizontalStrut_5);
		
		JLabel lblText = new JLabel(text);
		lblText.setFont(new Font("Dialog", Font.BOLD, 18));
		panel_1.add(lblText);
		
		Component horizontalGlue_1 = Box.createHorizontalGlue();
		panel_1.add(horizontalGlue_1);
		
		JSeparator separator = new JSeparator();
		separator.setMaximumSize(new Dimension(32767, 2));
		panel.add(separator);
		
		JPanel panel_4 = new JPanel();
		panel_4.setPreferredSize(new Dimension(10, 30));
		panel_4.setMinimumSize(new Dimension(10, 30));
		panel_4.setMaximumSize(new Dimension(32767, 30));
		panel.add(panel_4);
		panel_4.setLayout(new BoxLayout(panel_4, BoxLayout.X_AXIS));
		
		Component horizontalStrut = Box.createHorizontalStrut(5);
		panel_4.add(horizontalStrut);
		
		textField = new JTextField();
		panel_4.add(textField);
		textField.setColumns(10);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		panel_4.add(horizontalStrut_1);
		
		JButton btnNewButton = new JButton("ADD");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String newitem = textField.getText();
				newitem.trim();
				if ((newitem.equals("")) || (dataList.contains(newitem))) return;
				
				dataList.add(newitem);
				DefaultListModel<String> lstModel =  (DefaultListModel<String>) list.getModel();
				lstModel.addElement(newitem);
				
			}
		});
		panel_4.add(btnNewButton);
		
		Component horizontalStrut_2 = Box.createHorizontalStrut(5);
		panel_4.add(horizontalStrut_2);
		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2);
		panel_2.setLayout(new BorderLayout(0, 0));
		panel_2.add(scrollPane, BorderLayout.CENTER);
		
		list = new JList();
		DefaultListModel<String> lstModel = new DefaultListModel<String>();
		for (String line : data) {
			lstModel.addElement(line);
		}
		list.setModel(lstModel);
		scrollPane.setViewportView(list);
		
		JSeparator separator_1 = new JSeparator();
		panel.add(separator_1);
		
		JPanel panel_3 = new JPanel();
		panel_3.setPreferredSize(new Dimension(10, 30));
		panel_3.setMinimumSize(new Dimension(10, 30));
		panel.add(panel_3);
		panel_3.setLayout(new BoxLayout(panel_3, BoxLayout.X_AXIS));
		
		Component horizontalStrut_3 = Box.createHorizontalStrut(20);
		panel_3.add(horizontalStrut_3);
		
		JButton btnNewButton_1 = new JButton("Delete");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DefaultListModel<String> lstModel = (DefaultListModel<String>) list.getModel();
				String selected =  lstModel.get(list.getSelectedIndex());
				dataList.remove(selected);
				lstModel.remove(list.getSelectedIndex());
				
			}
		});
		panel_3.add(btnNewButton_1);
		
		Component horizontalGlue_2 = Box.createHorizontalGlue();
		panel_3.add(horizontalGlue_2);
		
		JButton btnNewButton_2 = new JButton("close");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				close();
			}
		});
		panel_3.add(btnNewButton_2);
		
		Component horizontalStrut_4 = Box.createHorizontalStrut(5);
		panel_3.add(horizontalStrut_4);
	}
	private void close() {
		this.dispose();
	}
}
