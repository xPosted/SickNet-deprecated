package com.jubaka.sors.desktop.tcpAnalyse;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import java.awt.Dimension;
import java.awt.BorderLayout;

import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JTextField;

import java.awt.Component;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JSpinner;
import javax.swing.JComboBox;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;


import com.jubaka.sors.beans.SecPolicy;
import com.jubaka.sors.desktop.factories.ClassFactory;
import com.jubaka.sors.desktop.limfo.LoadLimits;


import java.awt.Color;
import java.util.Collection;
import java.util.HashSet;


import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.JToggleButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Insets;

import javax.swing.ButtonGroup;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.SpinnerNumberModel;

public class PermissionSettingsPanel extends JPanel {
	private JTextField textField;
	private DefaultListModel<String> defListModel = new DefaultListModel<String>(); 
	private ClassFactory factory = ClassFactory.getInstance();
	private JList list;
	private JToggleButton tglDumpUpOFF;
	private JToggleButton tglDumpOn;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private final ButtonGroup buttonGroup_1 = new ButtonGroup();
	private final ButtonGroup buttonGroup_2 = new ButtonGroup();
	private final ButtonGroup buttonGroup_4 = new ButtonGroup();
	private JPanel dataPanel;
	private JToggleButton tglEnabled;
	private  JPanel panelDataView;
	private JSpinner spinHomeSizeLim;
	private JSpinner spinDumpSizeLim;
	private JSpinner spinDumpCountLim;
	private JToggleButton tglViewLiveOn;
	private JToggleButton tglViewLiveOff;
	private JToggleButton tglViewThirdOn;
	private JToggleButton tglViewThirdOff;
	private JToggleButton tglLiveCapOn;
	private JToggleButton tglLiveCapOff;
	private JLabel lblName;
	private JComboBox comboHomeSFormat;
	private JComboBox comboDumpSFormat;
	private DefaultComboBoxModel<String> homesFormatModel;
	private DefaultComboBoxModel<String> dumpsFormatModel;
	private String curHomeSF=null;
	private String curDumpSF=null;
	private SecPolicy currentSecPol=null;
	private final ButtonGroup buttonGroup_3 = new ButtonGroup();
	private JToggleButton tglUsers;
	private JToggleButton tglIPs;
	
	

	/**
	 * Create the panel.
	 */
	public PermissionSettingsPanel() {
		setPreferredSize(new Dimension(527, 467));
		setMinimumSize(new Dimension(10, 450));
		setBorder(new CompoundBorder(new LineBorder(new Color(0, 0, 0), 1, true), new EmptyBorder(0, 0, 15, 0)));
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(90, 10));
		add(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		JPanel panel_2 = new JPanel();
		panel_2.setMaximumSize(new Dimension(32767, 40));
		panel_2.setPreferredSize(new Dimension(10, 40));
		panel_2.setBorder(new EmptyBorder(5, 10, 5, 2));
		panel.add(panel_2);
		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.X_AXIS));
		
		textField = new JTextField();
		panel_2.add(textField);
		textField.setColumns(10);
		
		Component horizontalStrut = Box.createHorizontalStrut(5);
		panel_2.add(horizontalStrut);
		
		JButton btnNewButton = new JButton("add");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SecPolicy newSecPol = new SecPolicy(textField.getText());
				
				LoadLimits ll = ClassFactory.getInstance().getLimits();
				if (tglUsers.isSelected()) {ll.addUserPolicy(newSecPol); buildUsersPolicies();}
				if (tglIPs.isSelected()) {ll.addIpPolicy(newSecPol); buildIPPolicies();}
			}
		});
		btnNewButton.setMargin(new Insets(2, 5, 2, 5));
		panel_2.add(btnNewButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(new EmptyBorder(0, 10, 0, 0));
		panel.add(scrollPane);
		
		list = new JList();
		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				if (arg0.getValueIsAdjusting()) return;
				if (list.getSelectedIndex()==-1) return;
				
				currentSecPol=null;
				 String selectedText = defListModel.get(list.getSelectedIndex());
				 LoadLimits ll = factory.getLimits();
				 SecPolicy secPol;
				 if (tglIPs.isSelected())
					 secPol = ll.getPolicyByIP(selectedText); 
				 else 
					 secPol = ll.getPolicyByUser(selectedText);
				 if (secPol==null) return;
				 lblName.setText(secPol.getMyName());
				 double homeLim = Controller.processSize(secPol.getHomeMax(), homesFormatModel);
				 double dumpLim = Controller.processSize(secPol.getDumpSize(), dumpsFormatModel);
				 curDumpSF=(String)dumpsFormatModel.getSelectedItem();
				 curHomeSF=(String)homesFormatModel.getSelectedItem();
				 
				 spinHomeSizeLim.setValue(homeLim );
				 spinDumpSizeLim.setValue(dumpLim);
				 spinDumpCountLim.setValue(secPol.getDumpCountLim());
				 
				 if (secPol.isDenyLoad()) {tglDumpOn.setSelected(false); tglDumpUpOFF.setSelected(true);} else 
				 		{tglDumpOn.setSelected(true); tglDumpUpOFF.setSelected(false);}
				 
				 if (secPol.isDenyViewLive()) {tglViewLiveOn.setSelected(false); tglViewLiveOff.setSelected(true);} else 
				 		{tglViewLiveOn.setSelected(true); tglViewLiveOff.setSelected(false);}
				 	
				 if (secPol.isDenyViewThird()) {tglViewThirdOn.setSelected(false); tglViewThirdOff.setSelected(true);} else 
				 		{tglViewThirdOn.setSelected(true); tglViewThirdOff.setSelected(false);}
				 
				 if (secPol.isDenyLiveCap()) {tglLiveCapOn.setSelected(false); tglLiveCapOff.setSelected(true);} else 
			 		{tglLiveCapOn.setSelected(true); tglLiveCapOff.setSelected(false);}
				 
					HashSet<Component> comps = Controller.getComponents(panelDataView, null);
					Controller.EnableSwitch(true, comps);
					currentSecPol=secPol;
				 
			}
		});
		list.setFont(new Font("Dialog", Font.BOLD, 15));
		list.setModel(defListModel);
		scrollPane.setViewportView(list);
		
		JPanel panel_1 = new JPanel();
		panel_1.setMaximumSize(new Dimension(32767, 25));
		panel_1.setMinimumSize(new Dimension(90, 10));
		panel_1.setPreferredSize(new Dimension(90, 25));
		panel.add(panel_1);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));
		
		JPanel panel_5 = new JPanel();
		panel_1.add(panel_5);
		panel_5.setLayout(new BorderLayout(0, 0));
		
		tglUsers = new JToggleButton("Users");
		tglUsers.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent arg0) {
				System.out.println("Hello niga");
			}
		});
		tglUsers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buildUsersPolicies();
				
			}
		});
		buttonGroup_3.add(tglUsers);
		panel_5.add(tglUsers, BorderLayout.CENTER);
		
		JPanel panel_31 = new JPanel();
		panel_1.add(panel_31);
		panel_31.setLayout(new BorderLayout(0, 0));
		
		tglIPs = new JToggleButton("IP's");
		tglIPs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buildIPPolicies();
			}
		});
		buttonGroup_3.add(tglIPs);
		panel_31.add(tglIPs, BorderLayout.CENTER);
		
		JSeparator separator = new JSeparator();
		separator.setMaximumSize(new Dimension(2, 32767));
		separator.setOrientation(SwingConstants.VERTICAL);
		add(separator);
		
		 panelDataView = new JPanel();
		 panelDataView.setBorder(new EmptyBorder(0, 0, 10, 10));
		 panelDataView.setPreferredSize(new Dimension(250, 10));
		add(panelDataView);
		panelDataView.setLayout(new BoxLayout(panelDataView, BoxLayout.Y_AXIS));
		
		JPanel panel_3 = new JPanel();
		panelDataView.add(panel_3);
		panel_3.setLayout(new BoxLayout(panel_3, BoxLayout.X_AXIS));
		
		JPanel panel_6 = new JPanel();
		panel_6.setMinimumSize(new Dimension(10, 50));
		panel_6.setMaximumSize(new Dimension(32767, 50));
		panel_6.setPreferredSize(new Dimension(10, 50));
		panel_3.add(panel_6);
		panel_6.setLayout(new BorderLayout(0, 0));
		
		lblName = new JLabel("UserName");
		lblName.setFont(new Font("Dialog", Font.BOLD, 16));
		lblName.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_6.add(lblName);
		
		JPanel panel_30 = new JPanel();
		panel_30.setPreferredSize(new Dimension(100, 10));
		panel_30.setMaximumSize(new Dimension(60, 32767));
		panel_30.setBorder(new EmptyBorder(10, 10, 10, 10));
		panel_6.add(panel_30, BorderLayout.WEST);
		panel_30.setLayout(new BorderLayout(0, 0));
		
		tglEnabled = new JToggleButton("Enabled");
		tglEnabled.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (tglEnabled.isSelected()) {
				tglEnabled.setText("Disabled");
				HashSet<Component> comps = Controller.getComponents(dataPanel, null);
				Controller.EnableSwitch(false, comps);
				} else {
					tglEnabled.setText("Enabled");
					
					HashSet<Component> comps = Controller.getComponents(dataPanel, null);
					Controller.EnableSwitch(true, comps);
					
				}
			}
		});
		tglEnabled.setBackground(Color.WHITE);
		tglEnabled.setMargin(new Insets(2, 3, 2, 3));
		panel_30.add(tglEnabled, BorderLayout.CENTER);
		
		JPanel panel_4 = new JPanel();
		panelDataView.add(panel_4);
		panel_4.setLayout(new BoxLayout(panel_4, BoxLayout.Y_AXIS));
		
		JPanel panel_9 = new JPanel();
		panel_4.add(panel_9);
		panel_9.setLayout(new BoxLayout(panel_9, BoxLayout.X_AXIS));
		
		Component horizontalGlue_1 = Box.createHorizontalGlue();
		panel_9.add(horizontalGlue_1);
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_9.add(lblNewLabel_1);
		
		Component verticalStrut_2 = Box.createVerticalStrut(20);
		verticalStrut_2.setMaximumSize(new Dimension(32767, 5));
		panel_4.add(verticalStrut_2);
		
		JPanel panel_10 = new JPanel();
		panel_4.add(panel_10);
		panel_10.setLayout(new BoxLayout(panel_10, BoxLayout.X_AXIS));
		
		Component horizontalGlue = Box.createHorizontalGlue();
		panel_10.add(horizontalGlue);
		
		JLabel lblNewLabel_2 = new JLabel("New label");
		panel_10.add(lblNewLabel_2);
		
		Component verticalStrut_1 = Box.createVerticalStrut(20);
		verticalStrut_1.setMaximumSize(new Dimension(32767, 5));
		panel_4.add(verticalStrut_1);
		
		JPanel panel_7 = new JPanel();
		panel_4.add(panel_7);
		panel_7.setLayout(new BoxLayout(panel_7, BoxLayout.X_AXIS));
		
		Component horizontalGlue_2 = Box.createHorizontalGlue();
		panel_7.add(horizontalGlue_2);
		
		dataPanel = new JPanel();
		panelDataView.add(dataPanel);
		dataPanel.setLayout(new BoxLayout(dataPanel, BoxLayout.Y_AXIS));
		
		JSeparator separator_5 = new JSeparator();
		separator_5.setMaximumSize(new Dimension(32767, 2));
		dataPanel.add(separator_5);
		
		JPanel panel_8 = new JPanel();
		panel_8.setMaximumSize(new Dimension(32767, 40));
		panel_8.setPreferredSize(new Dimension(10, 40));
		dataPanel.add(panel_8);
		panel_8.setLayout(new BoxLayout(panel_8, BoxLayout.X_AXIS));
		
		JPanel panel_13 = new JPanel();
		panel_13.setPreferredSize(new Dimension(100, 25));
		panel_8.add(panel_13);
		panel_13.setLayout(new BoxLayout(panel_13, BoxLayout.X_AXIS));
		
		Component horizontalGlue_3 = Box.createHorizontalGlue();
		panel_13.add(horizontalGlue_3);
		
		JLabel lblHomeSizeLimit = new JLabel("home size limit");
		lblHomeSizeLimit.setHorizontalAlignment(SwingConstants.CENTER);
		panel_13.add(lblHomeSizeLimit);
		
		Component horizontalGlue_4 = Box.createHorizontalGlue();
		panel_13.add(horizontalGlue_4);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setMaximumSize(new Dimension(2, 32767));
		separator_1.setOrientation(SwingConstants.VERTICAL);
		panel_8.add(separator_1);
		
		JPanel panel_14 = new JPanel();
		panel_14.setMaximumSize(new Dimension(350, 32767));
		panel_14.setPreferredSize(new Dimension(200, 25));
		panel_14.setBorder(new EmptyBorder(5, 15, 5, 10));
		panel_8.add(panel_14);
		panel_14.setLayout(new BoxLayout(panel_14, BoxLayout.X_AXIS));
		
		spinHomeSizeLim = new JSpinner();
		spinHomeSizeLim.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				System.out.println("stateCH "+spinHomeSizeLim.getValue());
				if (currentSecPol!=null) {
					double newHSize = Controller.processSize((Double)spinHomeSizeLim.getValue(), curHomeSF, "b");
					currentSecPol.setHomeMax(newHSize);
				}
			}
		});
		
		spinHomeSizeLim.setModel(new SpinnerNumberModel(new Double(0), null, null, new Double(1)));
		spinHomeSizeLim.setFont(new Font("Dialog", Font.BOLD, 14));
		spinHomeSizeLim.setPreferredSize(new Dimension(50, 20));
		panel_14.add(spinHomeSizeLim);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		horizontalStrut_1.setPreferredSize(new Dimension(5, 0));
		horizontalStrut_1.setMaximumSize(new Dimension(5, 32767));
		panel_14.add(horizontalStrut_1);
		
		comboHomeSFormat = new JComboBox();
		comboHomeSFormat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (curHomeSF==null) return;
				String selObj = (String) homesFormatModel.getSelectedItem();
				double curVal = (Double) spinHomeSizeLim.getValue();
		//		System.out.println("val: "+curVal+"formatFrom: "+curHomeSF+" formatto: "+selObj);
				spinHomeSizeLim.setValue(Controller.processSize(curVal, curHomeSF, selObj));
				curHomeSF=selObj;
				
			}
		});
		comboHomeSFormat.setPreferredSize(new Dimension(20, 24));
		panel_14.add(comboHomeSFormat);
		
		JPanel panel_11 = new JPanel();
		panel_11.setPreferredSize(new Dimension(10, 40));
		panel_11.setMaximumSize(new Dimension(32767, 40));
		dataPanel.add(panel_11);
		panel_11.setLayout(new BoxLayout(panel_11, BoxLayout.X_AXIS));
		
		JPanel panel_15 = new JPanel();
		panel_15.setPreferredSize(new Dimension(100, 10));
		panel_11.add(panel_15);
		panel_15.setLayout(new BoxLayout(panel_15, BoxLayout.X_AXIS));
		
		Component horizontalGlue_6 = Box.createHorizontalGlue();
		panel_15.add(horizontalGlue_6);
		
		JLabel lblNewLabel_3 = new JLabel("dump size limit");
		panel_15.add(lblNewLabel_3);
		
		Component horizontalGlue_5 = Box.createHorizontalGlue();
		panel_15.add(horizontalGlue_5);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setOrientation(SwingConstants.VERTICAL);
		separator_2.setMaximumSize(new Dimension(2, 32767));
		panel_11.add(separator_2);
		
		JPanel panel_16 = new JPanel();
		panel_16.setMaximumSize(new Dimension(350, 32767));
		panel_16.setPreferredSize(new Dimension(200, 10));
		panel_16.setBorder(new EmptyBorder(5, 15, 5, 10));
		panel_11.add(panel_16);
		panel_16.setLayout(new BoxLayout(panel_16, BoxLayout.X_AXIS));
		
		spinDumpSizeLim = new JSpinner();
		spinDumpSizeLim.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
			}
		});
		spinDumpSizeLim.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if (currentSecPol!=null) {
					double newDSize = Controller.processSize((Double)spinDumpSizeLim.getValue(), curDumpSF,"b");
					currentSecPol.setDumpSize(newDSize);
					}
			}
		});
		spinDumpSizeLim.setModel(new SpinnerNumberModel(new Double(0), null, null, new Double(1)));
		spinDumpSizeLim.setFont(new Font("Dialog", Font.BOLD, 14));
		spinDumpSizeLim.setPreferredSize(new Dimension(50, 20));
		panel_16.add(spinDumpSizeLim);
		
		Component horizontalStrut_2 = Box.createHorizontalStrut(5);
		panel_16.add(horizontalStrut_2);
		
		comboDumpSFormat = new JComboBox();
		comboDumpSFormat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (curDumpSF==null) return;
				String selObj = (String) dumpsFormatModel.getSelectedItem();
				double curVal = (Double) spinDumpSizeLim.getValue();
		//		System.out.println("val: "+curVal+"formatFrom: "+curDumpSF+" formatto: "+selObj);
				spinDumpSizeLim.setValue(Controller.processSize(curVal, curDumpSF, selObj));
				curDumpSF=selObj;
				
			}
		});
		comboDumpSFormat.setPreferredSize(new Dimension(20, 24));
		panel_16.add(comboDumpSFormat);
		
		JPanel panel_12 = new JPanel();
		panel_12.setMaximumSize(new Dimension(32767, 40));
		panel_12.setPreferredSize(new Dimension(10, 40));
		dataPanel.add(panel_12);
		panel_12.setLayout(new BoxLayout(panel_12, BoxLayout.X_AXIS));
		
		JPanel panel_17 = new JPanel();
		panel_17.setPreferredSize(new Dimension(100, 10));
		panel_12.add(panel_17);
		panel_17.setLayout(new BoxLayout(panel_17, BoxLayout.X_AXIS));
		
		Component horizontalGlue_7 = Box.createHorizontalGlue();
		panel_17.add(horizontalGlue_7);
		
		JLabel lblNewLabel_4 = new JLabel("dump count limit");
		panel_17.add(lblNewLabel_4);
		
		Component horizontalGlue_8 = Box.createHorizontalGlue();
		panel_17.add(horizontalGlue_8);
		
		JSeparator separator_3 = new JSeparator();
		separator_3.setMaximumSize(new Dimension(2, 32767));
		separator_3.setOrientation(SwingConstants.VERTICAL);
		panel_12.add(separator_3);
		
		JPanel panel_18 = new JPanel();
		panel_18.setMinimumSize(new Dimension(350, 10));
		panel_18.setPreferredSize(new Dimension(200, 10));
		panel_18.setBorder(new EmptyBorder(5, 15, 5, 10));
		panel_12.add(panel_18);
		panel_18.setLayout(new BoxLayout(panel_18, BoxLayout.X_AXIS));
		
		spinDumpCountLim = new JSpinner();
		spinDumpCountLim.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if (currentSecPol!=null)
					currentSecPol.setDumpCountLim((Integer)spinDumpCountLim.getValue());
			}
		});
		spinDumpCountLim.setFont(new Font("Dialog", Font.BOLD, 14));
		spinDumpCountLim.setMaximumSize(new Dimension(400, 32767));
		spinDumpCountLim.setPreferredSize(new Dimension(20, 20));
		panel_18.add(spinDumpCountLim);
		
		JSeparator separator_4 = new JSeparator();
		separator_4.setMaximumSize(new Dimension(32767, 2));
		dataPanel.add(separator_4);
		
		JPanel panel_20 = new JPanel();
		dataPanel.add(panel_20);
		panel_20.setLayout(new BoxLayout(panel_20, BoxLayout.Y_AXIS));
		
		JPanel panel_21 = new JPanel();
		panel_20.add(panel_21);
		panel_21.setLayout(new BoxLayout(panel_21, BoxLayout.X_AXIS));
		
		JPanel panel_24 = new JPanel();
		panel_24.setBorder(new EmptyBorder(10, 10, 10, 10));
		panel_21.add(panel_24);
		panel_24.setLayout(new BoxLayout(panel_24, BoxLayout.X_AXIS));
		
		Component horizontalGlue_10 = Box.createHorizontalGlue();
		panel_24.add(horizontalGlue_10);
		
		JLabel lblPermitDumpUpload = new JLabel("Permit dump upload");
		panel_24.add(lblPermitDumpUpload);
		
		JPanel panel_25 = new JPanel();
		panel_25.setBorder(new EmptyBorder(10, 10, 10, 10));
		panel_21.add(panel_25);
		panel_25.setLayout(new BoxLayout(panel_25, BoxLayout.X_AXIS));
		
		tglDumpOn= new JToggleButton("ON");
		tglDumpOn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (currentSecPol!=null)
				currentSecPol.setDenyLoad(false);
			}
		});
		buttonGroup_1.add(tglDumpOn);
		tglDumpOn.setSelected(true);
		panel_25.add(tglDumpOn);
		
		 tglDumpUpOFF = new JToggleButton("OFF");
		 tglDumpUpOFF.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
		 		if (currentSecPol!=null)
		 		currentSecPol.setDenyLoad(true);
		 	}
		 });
		 buttonGroup_1.add(tglDumpUpOFF);
		panel_25.add(tglDumpUpOFF);
		
		JPanel panel_22 = new JPanel();
		panel_20.add(panel_22);
		panel_22.setLayout(new BoxLayout(panel_22, BoxLayout.X_AXIS));
		
		JPanel panel_26 = new JPanel();
		panel_26.setBorder(new EmptyBorder(10, 10, 10, 10));
		panel_22.add(panel_26);
		panel_26.setLayout(new BoxLayout(panel_26, BoxLayout.X_AXIS));
		
		Component horizontalGlue_11 = Box.createHorizontalGlue();
		panel_26.add(horizontalGlue_11);
		
		JLabel lblNewLabel_5 = new JLabel("Permit view live");
		panel_26.add(lblNewLabel_5);
		
		JPanel panel_27 = new JPanel();
		panel_27.setBorder(new EmptyBorder(10, 10, 10, 10));
		panel_22.add(panel_27);
		panel_27.setLayout(new BoxLayout(panel_27, BoxLayout.X_AXIS));
		
		tglViewLiveOn = new JToggleButton("ON");
		tglViewLiveOn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (currentSecPol!=null)
				currentSecPol.setDenyViewLive(false);
			}
		});
		tglViewLiveOn.setSelected(true);
		buttonGroup.add(tglViewLiveOn);
		panel_27.add(tglViewLiveOn);
		
		tglViewLiveOff = new JToggleButton("OFF");
		tglViewLiveOff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (currentSecPol!=null)
				currentSecPol.setDenyViewLive(true);
			}
		});
		buttonGroup.add(tglViewLiveOff);
		panel_27.add(tglViewLiveOff);
		
		JPanel panel_23 = new JPanel();
		panel_20.add(panel_23);
		panel_23.setLayout(new BoxLayout(panel_23, BoxLayout.X_AXIS));
		
		JPanel panel_28 = new JPanel();
		panel_28.setBorder(new EmptyBorder(10, 10, 10, 10));
		panel_23.add(panel_28);
		panel_28.setLayout(new BoxLayout(panel_28, BoxLayout.X_AXIS));
		
		Component horizontalGlue_12 = Box.createHorizontalGlue();
		panel_28.add(horizontalGlue_12);
		
		JLabel lblNewLabel_6 = new JLabel("Permit view third");
		panel_28.add(lblNewLabel_6);
		
		JPanel panel_29 = new JPanel();
		panel_29.setBorder(new EmptyBorder(10, 10, 10, 10));
		panel_23.add(panel_29);
		panel_29.setLayout(new BoxLayout(panel_29, BoxLayout.X_AXIS));
		
		tglViewThirdOn = new JToggleButton("ON");
		tglViewThirdOn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (currentSecPol!=null)
				currentSecPol.setDenyViewThird(false);
			}
		});
		tglViewThirdOn.setSelected(true);
		buttonGroup_2.add(tglViewThirdOn);
		panel_29.add(tglViewThirdOn);
		
		tglViewThirdOff = new JToggleButton("OFF");
		tglViewThirdOff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (currentSecPol!=null)
				currentSecPol.setDenyViewThird(true);
			}
		});
		buttonGroup_2.add(tglViewThirdOff);
		panel_29.add(tglViewThirdOff);
		
		JPanel panel_32 = new JPanel();
		panel_20.add(panel_32);
		panel_32.setLayout(new BoxLayout(panel_32, BoxLayout.X_AXIS));
		
		JPanel panel_33 = new JPanel();
		panel_33.setBorder(new EmptyBorder(10, 10, 10, 10));
		panel_32.add(panel_33);
		panel_33.setLayout(new BoxLayout(panel_33, BoxLayout.X_AXIS));
		
		Component horizontalGlue_13 = Box.createHorizontalGlue();
		panel_33.add(horizontalGlue_13);
		
		JLabel lblPermit = new JLabel("Permit Live capture");
		panel_33.add(lblPermit);
		
		JPanel panel_34 = new JPanel();
		panel_34.setBorder(new EmptyBorder(10, 10, 10, 10));
		panel_32.add(panel_34);
		panel_34.setLayout(new BoxLayout(panel_34, BoxLayout.X_AXIS));
		
		tglLiveCapOn = new JToggleButton(" ON");
		tglLiveCapOn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (currentSecPol!=null)
					currentSecPol.setDenyLiveCap(false);
			}
		});
		tglLiveCapOn.setSelected(true);
		buttonGroup_4.add(tglLiveCapOn);
		panel_34.add(tglLiveCapOn);
		
		tglLiveCapOff = new JToggleButton("OFF");
		tglLiveCapOff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (currentSecPol!=null)
					currentSecPol.setDenyLiveCap(true);
			}
		});
		buttonGroup_4.add(tglLiveCapOff);
		panel_34.add(tglLiveCapOff);
		
		Component verticalGlue = Box.createVerticalGlue();
		dataPanel.add(verticalGlue);
		
		JPanel panel_19 = new JPanel();
		dataPanel.add(panel_19);
		panel_19.setLayout(new BoxLayout(panel_19, BoxLayout.X_AXIS));
		
		Component horizontalGlue_9 = Box.createHorizontalGlue();
		panel_19.add(horizontalGlue_9);
		
		JButton btnSave = new JButton("save");
		panel_19.add(btnSave);
		
		
		init();  //load values
		
	}
	
	private void init() {
		homesFormatModel = new DefaultComboBoxModel<String>();
			homesFormatModel.addElement("kb");
			homesFormatModel.addElement("mb");
			homesFormatModel.addElement("gb");
		dumpsFormatModel = new DefaultComboBoxModel<String>();
			dumpsFormatModel.addElement("kb");
			dumpsFormatModel.addElement("mb");
			dumpsFormatModel.addElement("gb");
		
		comboDumpSFormat.setModel(dumpsFormatModel);
		comboHomeSFormat.setModel(homesFormatModel);
			
		tglUsers.setSelected(true);
		buildUsersPolicies();
		
	}
	private void buildUsersPolicies() {
		defListModel.clear();
		LoadLimits ll= factory.getLimits();
		Collection<SecPolicy> usersSec = ll.getUserPolicy().values();
		for (SecPolicy secPol : usersSec) {
			defListModel.addElement(secPol.getMyName());
		}
		
		HashSet<Component> comps = Controller.getComponents(panelDataView, null);
		Controller.EnableSwitch(false, comps);
	}
	private void buildIPPolicies() {
		defListModel.clear();
		LoadLimits ll= factory.getLimits();
		Collection<SecPolicy> ipsSec = ll.getIpPolicy().values();
		for (SecPolicy secPol : ipsSec) {
			defListModel.addElement(secPol.getMyName());
		}
		
		HashSet<Component> comps = Controller.getComponents(panelDataView, null);
		Controller.EnableSwitch(false, comps);
	}
}
