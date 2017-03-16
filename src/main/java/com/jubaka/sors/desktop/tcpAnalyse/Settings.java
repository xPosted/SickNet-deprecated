package com.jubaka.sors.tcpAnalyse;

import java.awt.BorderLayout;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.BoxLayout;

import java.awt.Dimension;
import java.awt.Component;
import java.awt.Color;

import javax.swing.JList;
import javax.swing.JScrollPane;

import java.awt.Font;

import javax.swing.JSeparator;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

import com.jubaka.sors.factories.ClassFactory;
import com.sun.corba.se.impl.orbutil.concurrent.Mutex;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Settings extends JFrame {
	private static Settings inst;
	private JPanel contentPane;
	private JList list = new JList();
	private JPanel changeblePanel;
	private GeneralSettingsPanel gsp = new GeneralSettingsPanel();
	private PermissionSettingsPanel psp = new PermissionSettingsPanel();
	private RemoteAccessSettings ras = new RemoteAccessSettings();
	private SettingsViewPanel svp = new SettingsViewPanel();
	public static Mutex opened=new Mutex();

	/**
	 * Launch the application.
	 */
	public static Runnable getRunnable() {
		return (new Runnable() {
			public void run() {
				try {
					Settings frame = new Settings();
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
	public Settings() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				ClassFactory.getInstance().saveConfig();
				opened.release();
			}
		});
		inst=this;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 704, 550);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

		JPanel panel_1 = new JPanel();
		panel_1.setMinimumSize(new Dimension(150, 10));
		panel_1.setMaximumSize(new Dimension(150, 32767));
		panel_1.setBackground(Color.WHITE);
		panel_1.setPreferredSize(new Dimension(150, 10));
		panel.add(panel_1);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.Y_AXIS));

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setPreferredSize(new Dimension(60, 3));
		scrollPane.setMinimumSize(new Dimension(60, 22));
		panel_1.add(scrollPane);
		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				if (arg0.getValueIsAdjusting()) return;
				SettingsListItem sli = (SettingsListItem) list.getModel()
						.getElementAt(list.getSelectedIndex());
				if (sli.toString().equals("General")) {
					changeblePanel.removeAll();
					changeblePanel.add(gsp, BorderLayout.CENTER);
					changeblePanel.updateUI();
					System.out.println("restyle");
				} 
				
				if (sli.toString().equals("Security")) {
					
					changeblePanel.removeAll();
					changeblePanel.add(psp, BorderLayout.CENTER);
					changeblePanel.updateUI();
					System.out.println("restyle");
					
				}
				
				if (sli.toString().equals("Remote access")) {
					
					changeblePanel.removeAll();
					changeblePanel.add(ras, BorderLayout.CENTER);
					changeblePanel.updateUI();
					System.out.println("restyle");
					
				}
				if (sli.toString().equals("View")) {
					
					changeblePanel.removeAll();
					changeblePanel.add(svp, BorderLayout.CENTER);
					changeblePanel.updateUI();
					System.out.println("restyle");
					
				}
			}
		});
		list.setFont(new Font("Dialog", Font.BOLD, 16));
		scrollPane.setViewportView(list);

		JPanel panel_2 = new JPanel();
		panel.add(panel_2);
		panel_2.setLayout(new BorderLayout(0, 0));
		changeblePanel = new JPanel();
		changeblePanel.setLayout(new BorderLayout());
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setViewportView(changeblePanel);
		panel_2.add(scrollPane_1, BorderLayout.CENTER);

		
//		panel_2.add(changeblePanel, BorderLayout.CENTER);

		JPanel panel_3 = new JPanel();
		panel_3.setBackground(Color.WHITE);
		panel_3.setMinimumSize(new Dimension(10, 40));
		panel_3.setPreferredSize(new Dimension(10, 40));
		contentPane.add(panel_3, BorderLayout.NORTH);
		panel_3.setLayout(new BorderLayout(0, 0));

		JSeparator separator = new JSeparator();
		separator.setPreferredSize(new Dimension(0, 4));
		panel_3.add(separator, BorderLayout.SOUTH);

		JLabel lblSors = new JLabel("Sors");
		lblSors.setFont(new Font("URW Chancery L", Font.BOLD, 25));
		lblSors.setHorizontalAlignment(SwingConstants.CENTER);
		panel_3.add(lblSors, BorderLayout.CENTER);
		init();
	}

	private void init() {
		
		try {
			opened.acquire();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		DefaultListModel<SettingsListItem> lstModel = new DefaultListModel<SettingsListItem>();
		lstModel.addElement(new SettingsListItem("General"));
		lstModel.addElement(new SettingsListItem("Security"));
		lstModel.addElement(new SettingsListItem("Remote access"));
		lstModel.addElement(new SettingsListItem("View"));
		
		list.setCellRenderer(new MyCellRenderer());
		list.setModel(lstModel);
		list.setSelectedIndex(0);
		
	}
	public void loginIncorrect() {
		JOptionPane.showMessageDialog(null, "Login incorrect...");
		ras.init_2();
	}
	public static  Settings getInst() {
		return inst;
	}
}

class MyCellRenderer implements ListCellRenderer<Object> {
	@Override
	public Component getListCellRendererComponent(JList<? extends Object> lst,
			Object arg1, int cellIndex, boolean isSelected, boolean hasFocus) {
		SettingsListItem comp = (SettingsListItem) arg1;

		if (isSelected) {
			comp.setBackground(lst.getSelectionBackground());
			comp.setForeground(lst.getSelectionForeground());
			comp.setActive();

		} else {

			comp.setBackground(lst.getBackground());
			comp.setForeground(lst.getForeground());
			comp.setInactive();

		}
		return comp;
	}

}

class myLbl extends JLabel {
	public myLbl() {
		super();
	}

	public myLbl(String text) {
		super(text);
	}

	public String toString() {
		return getText();
	}
}
