package com.jubaka.sors.desktop.tcpAnalyse;

import java.awt.EventQueue;

import javax.swing.JFrame;

import java.awt.BorderLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.SwingUtilities;

import java.awt.Component;

import javax.swing.Box;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import java.awt.Dimension;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.JTextArea;
import javax.swing.JList;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JScrollPane;
import javax.swing.border.EtchedBorder;

import java.awt.Font;

import javax.swing.border.MatteBorder;

import java.awt.Color;

import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;

import com.jubaka.sors.desktop.factories.ClassFactory;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.UnknownHostException;

import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.JTabbedPane;
import javax.swing.ScrollPaneConstants;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Insets;

import javax.swing.SwingConstants;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JCheckBoxMenuItem;

import com.jubaka.sors.desktop.limfo.LoadLimits;
import com.jubaka.sors.desktop.sessions.*;
import com.jubaka.sors.desktop.statistic.StatMain;

public class MainWin implements Observer {
	static int compCount = 0; // tmp
	/**
	 * Controller instance for current branch
	 */
	protected Controller mainControl;
	public static MainWin instance;
	private ClassFactory factory;

	private JFrame frame;
	private JTextField textField;
	private JSplitPane mainSplitPane;
	private JCheckBox chckDumpPath;
	private String dumpPath=null;
//	private JPanel stateControlPanel;
	private StateControlPanel stateControlTest;
	private JMenuItem mntmStart;
	public SessionsListView sessionView;
//	private JSplitPane splitPane_1;
	private JPanel ipPanel;
	private JPanel splitParent;
	private JPanel panel;
	private JPanel panel_1;
	private SeparateSessionView ssv=null;
	private SessionDataFrame sdf = null;
	
	
	private JMenuItem mntmInfo;
	private JMenuItem mntmStatistic;
	private JMenuItem mntmDataCapture;
	private JMenuItem mntmAddNetwork;
	private JMenuItem mntmDelete;
	/**
	 * 
	 * Panel where <NetViewItemPanel> instances are displayed
	 */
	public JPanel subnetView = new JPanel();
	
	
	
	/**
	 * LoadInfo info panel for current <Subnet> item
	 */
	private NetInfoPanel netInfo = new NetInfoPanel();
	/**
	 * container for available NetViewItemPanel instances
	 */
	HashSet<NetViewItemPanel> netPanSet = new HashSet<NetViewItemPanel>();
	JTextArea txtrSrcData = null;
	JTextArea txtrDstData = null;
	JTextArea txtrBothData = null;
	//JList liveIPlist = new JList();
	JTextArea dumpArea;
	JLabel lblDate;
	JLabel lblData;
	JLabel lblSes;
	JLabel lblInOut;

	String selectedIP = null;
	JLabel lblNewLabel_4;
	JLabel lblNewLabel_5;
	// HashSet<Session> actSes = new HashSet<Session>();
	// HashSet<Session> savedSes = new HashSet<Session>();
	// private boolean dumping = false;
	public Subnet notKnownNet = null;

	private Subnet currentNet;

	public Integer id = null;
	private JTextField textField_1;
	private JComboBox comboSearchResult;
	private DefaultComboBoxModel<String> comboModel = new DefaultComboBoxModel<String>();
	boolean letIPclick = true;
	public SessionFilter sFilter = new SessionFilter();


	protected JComboBox comboIPsort;
	JTable tbl_0;
	JTable tbl_1;
	/**
	 * @wbp.nonvisual location=753,-37
	 */
	private final JLabel label = new JLabel("New label");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClassFactory factory = ClassFactory.getInstance();
			
					if (factory.getLimits()==null) return;
					MainWin window = new MainWin();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * Create the application.
	 */
	public MainWin() {
		
		instance = this;
		factory = ClassFactory.getInstance();
		initialize();
		ClassFactory factory = ClassFactory.getInstance();
		LoadLimits ll = factory.getLimits();
		if (ll.isSeparSessionView()) 
			showSeparateSesView();
		else 
			showIntegratedSesView();
		if (ll.isSeparSesDataView())
			showSeparateSessionDataWindow();
		else 
			showIntegratedSessionDataWindow();
	
		setId(null,false);

	}
	public void showIntegratedSesView() {
		splitParent.removeAll();
		splitParent.revalidate();
		System.out.println(SwingUtilities.isEventDispatchThread());
		if (ssv != null)	
				ssv.dispose();
		sessionView = new SessionsListView();
		JSplitPane split = new JSplitPane();
		split.setLeftComponent(ipPanel);
		split.setRightComponent(sessionView);  
		splitParent.add(split,BorderLayout.CENTER);
		splitParent.revalidate();
	}
	
	public void showSeparateSesView() {
		ssv = new SeparateSessionView();
		sessionView = ssv.getSlv();
		ssv.setVisible(true);
		splitParent.removeAll();
		splitParent.revalidate();
		splitParent.add(ipPanel, BorderLayout.CENTER);
		splitParent.revalidate();
	
	}
	
	
	public synchronized Integer getId() {
		return id;
	}

	public void setId(Integer id,boolean showMsg) {
		deleteIPObserver();
		clearIPInfoView();
		//Enabling componennts
		if (id == null) {
			clearSessioListView();
			netPanSet.clear();
			subnetView.removeAll();
			subnetView.revalidate();
			netInfo.reset();
			mntmStart.setEnabled(false);
			HashSet<Component> comps = getComponents(panel_1, null);
			EnableSwitch(false, comps);
			onBranchStateChanged(null);
			return;
		}
		mainControl = ClassFactory.getInstance().getController(id);
		
		
		if (this.id != null) {
			Controller cntr = ClassFactory.getInstance().getController(this.id);
			cntr.changeCurrentNetView(null);
			netInfo.setSubnet(null);
			
		}
		
		this.id = id;
		updateNetView();		// init net list with new items

		Branch newBr = factory.getBranch(id);

		boolean branchActiveFlag = newBr.isActive();
		if (branchActiveFlag) {
		
			mntmStart.setText("Stop");
		} else {
			
			mntmStart.setText("Start");
		}
		// Enabling State control panel
		HashSet<Component> comps = getComponents(panel_1, null);
		EnableSwitch(true, comps);
		
		sessionView.btnCustomize.setEnabled(true);
		mntmStart.setEnabled(true);
		stateControlTest.setBranch(newBr);
		
		
		if (showMsg) {
			BranchInfoView msg = new BranchInfoView(id);
			msg.setVisible(true);
		}
		
	
	}
	
	public   void EnableSwitch(boolean enable, HashSet<Component> comps) {
		for (Component item : comps) {
			item.setEnabled(enable);
		}

	}

	public  HashSet<Component> getComponents(Component container,
			HashSet<Component> list) {

		if (list == null)
			list = new HashSet<Component>();

		try {
			for (Component contItem : ((java.awt.Container) container)
					.getComponents()) {
				list.add(contItem);
				getComponents(contItem, list);
			}

		} catch (ClassCastException e) {

		}

		return list;
	}

	/**
	 * Refresh Subnet view when new subnet is added
	 */
	public void updateNetView() {
		ClassFactory factory = ClassFactory.getInstance();
		SessionsAPI sesApi = factory.getSesionInstance(id);
		Controller cntr = factory.getController(id);

		cntr.changeCurrentNetView(null);
		netPanSet.clear();
		for (Subnet net : sesApi.getAllSubnets()) {
			netPanSet.add(new NetViewItemPanel(net));
		}
		subnetView.removeAll();
		for (NetViewItemPanel netView : netPanSet) {
			subnetView.add(netView);

		}
		Component verticalGlue = Box.createVerticalGlue();
		subnetView.add(verticalGlue);
		SwingUtilities.updateComponentTreeUI(frame);
	}

	/**
	 * Handler for click event on distinct session view</br> Displays Src
	 * transmitted text data
	 * 
	 * @param text
	 *            - text data that was captured during session
	 * @param id
	 *            - random number that identify every click event
	 */
	public void setSrcData(ArrayList<String> text, Integer id) {
		if (txtrSrcData == null) return;
		SessionDataUpdater dataUpdater = new SessionDataUpdater(txtrSrcData,
				id, text);
		Thread th = new Thread(dataUpdater);

		th.start();

	}

	public void setAllData(ArrayList<String> text, Integer id) {
		if (txtrBothData == null) return;
		SessionDataUpdater dataUpdater = new SessionDataUpdater(txtrBothData,
				id, text);
		Thread th = new Thread(dataUpdater);

		th.start();

	}

	/**
	 * Handler for click event on distinct session view</br> Displays Dst
	 * transmitted text data
	 * 
	 * @param text
	 *            - text data that was captured during session
	 * @param id
	 *            - random number that identify every click event
	 */
	public void setDstData(ArrayList<String> text, Integer id) {
		if (txtrDstData == null) return;
		SessionDataUpdater dataUpdater = new SessionDataUpdater(txtrDstData,
				id, text);
		Thread th = new Thread(dataUpdater);

		th.start();
	}

	/**
	 * Change selected subnet, deleting observer on ip
	 * 
	 * @param net
	 *            - Subnet item that is newly selected
	 */
	public void setNetActive(Subnet net) {
		
		deleteIPObserver();
		currentNet = net;
		netInfo.setSubnet(net);

	}

	private void initialize() {
		JPanel testPanel = new JPanel(new BorderLayout());
		testPanel.add(new JLabel("Hello world"), BorderLayout.CENTER);

		frame = new JFrame();
		frame.setBounds(100, 100, 1128, 575);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setTitle("TCPanalyse");
		panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout());

		JPanel panel_8 = new JPanel();
		panel_8.setPreferredSize(new Dimension(10, 35));
		panel_8.setBorder(new MatteBorder(3, 1, 3, 1, (Color) Color.GRAY));
		panel.add(panel_8,BorderLayout.PAGE_START);
		panel_8.setLayout(new BoxLayout(panel_8, BoxLayout.X_AXIS));

		stateControlTest = new StateControlPanel();								// initializing stateControlPanel
		panel_8.add(stateControlTest);

		panel_1 = new JPanel();
		panel_1.setMinimumSize(new Dimension(10, 200));
		panel_1.setPreferredSize(new Dimension(100, 400));
		
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.Y_AXIS));

		JSplitPane splitPane = new JSplitPane();
		splitPane.setPreferredSize(new Dimension(242, 1400));
		panel_1.add(splitPane);

		JPanel panel_3 = new JPanel();
		panel_3.setPreferredSize(new Dimension(250, 200));
		panel_3.setMinimumSize(new Dimension(40, 10));
		splitPane.setLeftComponent(panel_3);
		panel_3.setLayout(new BoxLayout(panel_3, BoxLayout.Y_AXIS));
		panel_3.add(netInfo);

		JPanel panel_24 = new JPanel();
		panel_24.setPreferredSize(new Dimension(10, 150));
		panel_24.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null,
				null, null));
		panel_24.setLayout(new BoxLayout(panel_24, BoxLayout.Y_AXIS));

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBorder(new EmptyBorder(0, 0, 0, 0));
		panel_24.add(scrollPane_1);

		subnetView.setBorder(null);
		scrollPane_1.setViewportView(subnetView);
		subnetView.setLayout(new BoxLayout(subnetView, BoxLayout.Y_AXIS));

		JPanel panel_7 = new JPanel();
		panel_7.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_7.setMaximumSize(new Dimension(32767, 25));
		panel_24.add(panel_7);

		JButton btnNewButton = new JButton("Add NET");
		btnNewButton.setMargin(new Insets(0, 14, 0, 14));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				NetAdd addnet = new NetAdd(id);
				
				addnet.setVisible(true);

			}
		});
		panel_7.setLayout(new BorderLayout(0, 0));
		panel_7.add(btnNewButton);
		panel_3.add(panel_24);

		JPanel panel_4 = new JPanel();
		splitPane.setRightComponent(panel_4);
		panel_4.setLayout(new BorderLayout(0, 0));

		splitParent = new JPanel();
		splitParent.setBorder(new MatteBorder(3, 1, 3, 1, (Color) Color.GRAY));
		panel_4.add(splitParent, BorderLayout.CENTER);
		splitParent.setLayout(new BorderLayout(0, 0));

	//	splitPane_1 = new JSplitPane();
	//	splitParent.add(splitPane_1,BorderLayout.CENTER);

		ipPanel = new JPanel();
		//splitPane_1.setLeftComponent(ipPanel);
		ipPanel.setLayout(new BoxLayout(ipPanel, BoxLayout.Y_AXIS));

		JPanel panel_15 = new JPanel();
		ipPanel.add(panel_15);
		panel_15.setLayout(new BoxLayout(panel_15, BoxLayout.Y_AXIS));

		Component verticalStrut_3 = Box.createVerticalStrut(5);
		panel_15.add(verticalStrut_3);

		JPanel panel_23 = new JPanel();
		panel_15.add(panel_23);
		panel_23.setLayout(new BoxLayout(panel_23, BoxLayout.X_AXIS));

		Component horizontalStrut_3 = Box.createHorizontalStrut(10);
		panel_23.add(horizontalStrut_3);

		lblNewLabel_4 = new JLabel("0.0.0.0");
		panel_23.add(lblNewLabel_4);
		//panel_23.add(new IPInfoNameContainer("0.0.0.0"));
		

		Component horizontalGlue_7 = Box.createHorizontalGlue();
		panel_23.add(horizontalGlue_7);

		lblNewLabel_5 = new JLabel("DNS name");
		panel_23.add(lblNewLabel_5);

		Component horizontalStrut_4 = Box.createHorizontalStrut(10);
		panel_23.add(horizontalStrut_4);

		Component verticalStrut_4 = Box.createVerticalStrut(5);
		panel_15.add(verticalStrut_4);

		JPanel panel_38 = new JPanel();
		panel_15.add(panel_38);
		panel_38.setLayout(new BoxLayout(panel_38, BoxLayout.X_AXIS));

		Component horizontalStrut_17 = Box.createHorizontalStrut(10);
		panel_38.add(horizontalStrut_17);

		JLabel lblActivated = new JLabel("activated");
		panel_38.add(lblActivated);

		Component horizontalGlue_11 = Box.createHorizontalGlue();
		panel_38.add(horizontalGlue_11);

		lblDate = new JLabel("Date");
		panel_38.add(lblDate);

		Component horizontalStrut_18 = Box.createHorizontalStrut(10);
		panel_38.add(horizontalStrut_18);

		Component verticalStrut = Box.createVerticalStrut(5);
		ipPanel.add(verticalStrut);

		JPanel panel_39 = new JPanel();
		ipPanel.add(panel_39);
		panel_39.setLayout(new BoxLayout(panel_39, BoxLayout.X_AXIS));

		Component horizontalStrut_19 = Box.createHorizontalStrut(10);
		panel_39.add(horizontalStrut_19);

		JLabel lblDataUpdown = new JLabel("data up/down (kB)");
		panel_39.add(lblDataUpdown);

		Component horizontalGlue_13 = Box.createHorizontalGlue();
		panel_39.add(horizontalGlue_13);

		lblData = new JLabel("0/0");
		panel_39.add(lblData);

		Component horizontalStrut_24 = Box.createHorizontalStrut(10);
		panel_39.add(horizontalStrut_24);

		Component verticalStrut_9 = Box.createVerticalStrut(5);
		ipPanel.add(verticalStrut_9);

		JPanel panel_40 = new JPanel();
		ipPanel.add(panel_40);
		panel_40.setLayout(new BoxLayout(panel_40, BoxLayout.X_AXIS));

		Component horizontalStrut_20 = Box.createHorizontalStrut(10);
		panel_40.add(horizontalStrut_20);

		JLabel lblNewLabel_10 = new JLabel("ses (act/svd)");
		panel_40.add(lblNewLabel_10);

		Component horizontalGlue_14 = Box.createHorizontalGlue();
		panel_40.add(horizontalGlue_14);

		lblSes = new JLabel("0/0");
		panel_40.add(lblSes);

		Component horizontalStrut_21 = Box.createHorizontalStrut(10);
		panel_40.add(horizontalStrut_21);

		Component verticalStrut_10 = Box.createVerticalStrut(5);
		ipPanel.add(verticalStrut_10);

		JPanel panel_41 = new JPanel();
		ipPanel.add(panel_41);
		panel_41.setLayout(new BoxLayout(panel_41, BoxLayout.X_AXIS));

		Component horizontalStrut_22 = Box.createHorizontalStrut(10);
		panel_41.add(horizontalStrut_22);

		JLabel lblNewLabel_12 = new JLabel("conn-s (in/out)");
		panel_41.add(lblNewLabel_12);

		Component horizontalGlue_15 = Box.createHorizontalGlue();
		panel_41.add(horizontalGlue_15);

		lblInOut = new JLabel("0/0");
		panel_41.add(lblInOut);

		Component horizontalStrut_23 = Box.createHorizontalStrut(10);
		panel_41.add(horizontalStrut_23);

		Component verticalStrut_11 = Box.createVerticalStrut(5);
		ipPanel.add(verticalStrut_11);

		JPanel panel_16 = new JPanel();
		panel_16.setPreferredSize(new Dimension(300, 10));
		ipPanel.add(panel_16);
		panel_16.setLayout(new BoxLayout(panel_16, BoxLayout.X_AXIS));

		JPanel panel_17 = new JPanel();
		panel_17.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0,
				0)));
		panel_16.add(panel_17);
		panel_17.setLayout(new BoxLayout(panel_17, BoxLayout.Y_AXIS));

		JPanel panel_19 = new JPanel();
		panel_17.add(panel_19);
		panel_19.setLayout(new BoxLayout(panel_19, BoxLayout.X_AXIS));

		Component horizontalGlue_6 = Box.createHorizontalGlue();
		panel_19.add(horizontalGlue_6);

		JLabel lblNewLabel_3 = new JLabel("Real time");
		panel_19.add(lblNewLabel_3);

		Component horizontalGlue_5 = Box.createHorizontalGlue();
		panel_19.add(horizontalGlue_5);

		Component verticalStrut_1 = Box.createVerticalStrut(5);
		panel_17.add(verticalStrut_1);

		JPanel panel_20 = new JPanel();
		panel_17.add(panel_20);
		panel_20.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane_5 = new JScrollPane();
		scrollPane_5.setMinimumSize(new Dimension(20, 20));
		panel_20.add(scrollPane_5, BorderLayout.CENTER);
		
		tbl_0 = new JTable();
		initTable(tbl_0);
		tbl_0.addMouseListener(new MouseAdapter() {
			 public void mousePressed(MouseEvent e){
				
				 if (e.getClickCount()==2) {
					 MyTableModel model = (MyTableModel) tbl_0.getModel();
			            String ip = model.getIPbyRow(tbl_0.getSelectedRow());
			            SessionsAPI sesApi = ClassFactory.getInstance().getSesionInstance(id);
			            IPaddr addr = sesApi.getIpInstance(ip);
			            if (addr!=null) {
			            	
			            }
				 }
			 }
		});
		tbl_0.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
	        public void valueChanged(ListSelectionEvent event) {
	        	if (event.getValueIsAdjusting()) return;
	            MyTableModel model = (MyTableModel) tbl_0.getModel();
	            String ip = model.getIPbyRow(tbl_0.getSelectedRow());
	        	
	            ipEventHandler ipHandler = new ipEventHandler(ip);
	            SwingUtilities.invokeLater(ipHandler);
	        }
	    });
		
		scrollPane_5.setViewportView(tbl_0);

		Component horizontalStrut_2 = Box.createHorizontalStrut(5);
		panel_16.add(horizontalStrut_2);

		JPanel panel_18 = new JPanel();
		panel_18.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0,
				0)));
		panel_16.add(panel_18);
		panel_18.setLayout(new BoxLayout(panel_18, BoxLayout.Y_AXIS));

		JPanel panel_21 = new JPanel();
		panel_18.add(panel_21);
		panel_21.setLayout(new BoxLayout(panel_21, BoxLayout.X_AXIS));

		Component horizontalGlue_3 = Box.createHorizontalGlue();
		panel_21.add(horizontalGlue_3);

		JLabel lblNewLabel_2 = new JLabel("History");
		panel_21.add(lblNewLabel_2);

		Component horizontalGlue_4 = Box.createHorizontalGlue();
		panel_21.add(horizontalGlue_4);

		Component verticalStrut_2 = Box.createVerticalStrut(5);
		panel_18.add(verticalStrut_2);

		JPanel panel_22 = new JPanel();
		panel_18.add(panel_22);
		panel_22.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane_4 = new JScrollPane();
		scrollPane_4.setMinimumSize(new Dimension(20, 20));
		panel_22.add(scrollPane_4, BorderLayout.CENTER);
		
		tbl_1 = new JTable();
		initTable(tbl_1);
		tbl_1.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
	        public void valueChanged(ListSelectionEvent event) {
	            MyTableModel model = (MyTableModel) tbl_1.getModel();
	            String ip = model.getIPbyRow(tbl_1.getSelectedRow());
	        	
	            ipEventHandler ipHandler = new ipEventHandler(ip);
	            SwingUtilities.invokeLater(ipHandler);
	        }
	    });
		scrollPane_4.setViewportView(tbl_1);

		Component verticalGlue_1 = Box.createVerticalGlue();
		panel_16.add(verticalGlue_1);

		Component verticalStrut_8 = Box.createVerticalStrut(5);
		ipPanel.add(verticalStrut_8);

		JPanel panel_48 = new JPanel();
		ipPanel.add(panel_48);
		panel_48.setLayout(new BoxLayout(panel_48, BoxLayout.X_AXIS));

		JPanel panel_47 = new JPanel();
		panel_48.add(panel_47);
		panel_47.setLayout(new BoxLayout(panel_47, BoxLayout.Y_AXIS));

		textField_1 = new JTextField();
		textField_1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				Runnable run = new Runnable() {
					
					@Override
					public void run() {
						letIPclick = false;
						comboModel.removeAllElements();
						String text = textField_1.getText();
						String ip;

						Set<IPaddr> ips = currentNet.getIps();
						for (IPaddr addr : ips) {
							ip = addr.getAddr().getHostAddress();
							if (ip.startsWith(text))
								comboModel.addElement(ip);
						}
						comboSearchResult.showPopup();
						
					}
				};
				SwingUtilities.invokeLater(run);
				

			}
		});
		textField_1.setToolTipText("type to search...");
		textField_1.setMaximumSize(new Dimension(2147483647, 20));
		panel_47.add(textField_1);
		textField_1.setColumns(10);

		comboSearchResult = new JComboBox();
		comboSearchResult.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
		//		liveIPlist.clearSelection();
		//		ipLst.clearSelection();

				String select = (String) comboModel.getSelectedItem();
				System.out.println("select from combo " + select);
				if (select == null)
					return;
				if (letIPclick) {
					SwingUtilities.invokeLater(new ipEventHandler(select));
					textField_1.setText(select);
				}
				letIPclick = true;

			}
		});
		comboSearchResult.setModel(comboModel);
		comboSearchResult.setPreferredSize(new Dimension(32, 1));
		panel_47.add(comboSearchResult);

		Component horizontalStrut_28 = Box.createHorizontalStrut(10);
		panel_48.add(horizontalStrut_28);

		JLabel lblSortBy_1 = new JLabel("Sort by:");
		panel_48.add(lblSortBy_1);

		comboIPsort = new JComboBox();
		comboIPsort.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Controller cntr = ClassFactory.getInstance().getController(id);
				cntr.updateIpLists(currentNet);
			}
		});
		DefaultComboBoxModel<String> comboModelIPsort = new DefaultComboBoxModel<String>();
		comboModelIPsort.addElement("data up");
		comboModelIPsort.addElement("data down");
		comboModelIPsort.addElement("Active time");
		comboIPsort.setModel(comboModelIPsort);
		comboIPsort.setPreferredSize(new Dimension(100, 24));
		panel_48.add(comboIPsort);

		JPanel panel_25 = new JPanel();
		panel_25.setLayout(new BoxLayout(panel_25, BoxLayout.Y_AXIS));

		JPanel panel_30 = new JPanel();
		panel_25.add(panel_30);
		panel_30.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_30.setMaximumSize(new Dimension(32767, 25));
		panel_30.setLayout(new BoxLayout(panel_30, BoxLayout.X_AXIS));

		Component horizontalStrut_5 = Box.createHorizontalStrut(20);
		panel_30.add(horizontalStrut_5);

		JLabel lblTcp_1 = new JLabel("TCP");
		lblTcp_1.setFont(new Font("Dialog", Font.BOLD, 20));
		panel_30.add(lblTcp_1);

		JLabel lblNewLabel_7 = new JLabel("dump");
		lblNewLabel_7.setFont(new Font("Dialog", Font.BOLD, 18));
		panel_30.add(lblNewLabel_7);

		Component horizontalStrut_9 = Box.createHorizontalStrut(15);
		panel_30.add(horizontalStrut_9);

		final JButton btnNewButton_1 = new JButton("START");
		btnNewButton_1.setMargin(new Insets(2, 5, 2, 5));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (btnNewButton_1.getText().equals("START")) {
					String exp = textField.getText().trim();
					if (exp.equals("")) exp=null;
			//		mainControl.setDump(exp,lblEth.getText(),dumpPath,dumpArea);   // 
					btnNewButton_1.setText("STOP");
				} else {

					mainControl.stopDump();
					btnNewButton_1.setText("START");
				}
			}
		});
		btnNewButton_1.setMaximumSize(new Dimension(100, 25));
		btnNewButton_1.setPreferredSize(new Dimension(100, 25));
		btnNewButton_1.setMinimumSize(new Dimension(100, 25));
		panel_30.add(btnNewButton_1);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		panel_30.add(horizontalStrut_1);
		
		JPanel panel_9 = new JPanel();
		panel_30.add(panel_9);
		panel_9.setLayout(new BoxLayout(panel_9, BoxLayout.X_AXIS));
		
		chckDumpPath = new JCheckBox("");
		chckDumpPath.setEnabled(false);
		chckDumpPath.setFocusable(false);
		panel_9.add(chckDumpPath);
		
		JButton btnNewButton_3 = new JButton("save to");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser chooser = new JFileChooser();
				chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				chooser.showSaveDialog(null);
				dumpPath = chooser.getSelectedFile().getAbsolutePath();
				if (dumpPath!=null)
					chckDumpPath.setSelected(true);
					
				
			}
		});
		btnNewButton_3.setMargin(new Insets(2, 2, 2, 2));
		panel_9.add(btnNewButton_3);

		Component horizontalStrut_8 = Box.createHorizontalStrut(80);
		panel_30.add(horizontalStrut_8);

		JPanel panel_32 = new JPanel();
		panel_30.add(panel_32);
		panel_32.setLayout(new BoxLayout(panel_32, BoxLayout.X_AXIS));

		Component horizontalStrut_6 = Box.createHorizontalStrut(10);
		panel_32.add(horizontalStrut_6);

		JLabel lblNewLabel_8 = new JLabel("expression:");
		panel_32.add(lblNewLabel_8);

		Component horizontalStrut_7 = Box.createHorizontalStrut(15);
		panel_32.add(horizontalStrut_7);

		textField = new JTextField();
		panel_32.add(textField);
		textField.setColumns(10);

		JPanel panel_31 = new JPanel();
		panel_31.setMinimumSize(new Dimension(10, 0));
		panel_25.add(panel_31);
		panel_31.setPreferredSize(new Dimension(10, 50));
		panel_31.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_31.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setMinimumSize(new Dimension(0, 0));

		scrollPane_2.setFocusCycleRoot(true);
		scrollPane_2.setAutoscrolls(true);
		scrollPane_2
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane_2
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		panel_31.add(scrollPane_2, BorderLayout.CENTER);

		dumpArea = new JTextArea();
		dumpArea.setMinimumSize(new Dimension(0, 0));
		scrollPane_2.setViewportView(dumpArea);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnCapture = new JMenu("Branch");
		menuBar.add(mnCapture);
		
		mntmInfo = new JMenuItem("Info");
		mnCapture.add(mntmInfo);
		
		mntmStatistic = new JMenuItem("Statistic");
		mntmStatistic.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				StatMain frame = new StatMain(id);
				frame.setVisible(true);
			}
		});
		mnCapture.add(mntmStatistic);
		
		mntmDataCapture = new JMenuItem("Data capture");
		mntmDataCapture.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DataCatchView dc = new DataCatchView(id);
				dc.setVisible(true);
			}
		});
		mnCapture.add(mntmDataCapture);
		
		mntmAddNetwork = new JMenuItem("Add network");
		mntmAddNetwork.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NetAdd addnet = new NetAdd(id);
				addnet.setVisible(true);
			}
		});
		mnCapture.add(mntmAddNetwork);
		
		JSeparator separator_3 = new JSeparator();
		mnCapture.add(separator_3);
		
		mntmDelete = new JMenuItem("Delete");
		mnCapture.add(mntmDelete);
		
		JMenu mnBranch = new JMenu("Capture");
		menuBar.add(mnBranch);
		
		JMenuItem mntmCreateLive = new JMenuItem("Create live");
		mntmCreateLive.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				BranchCreateForm bcf = new BranchCreateForm(BranchCreateForm.selectLive);
				bcf.setVisible(true);
			}
		});
		mnBranch.add(mntmCreateLive);
		
		JMenuItem mntmOpenFile = new JMenuItem("Open file");
		mntmOpenFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				BranchCreateForm bcf = new BranchCreateForm(BranchCreateForm.selectFile);
				bcf.setVisible(true);
			}
		});
		mnBranch.add(mntmOpenFile);
		
		JSeparator separator_4 = new JSeparator();
		mnBranch.add(separator_4);
		
		mntmStart = new JMenuItem("Start");
		mntmStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				
			}
		});
		mnBranch.add(mntmStart);
		
		JMenuItem mntmBranches = new JMenuItem("Branches");
		mntmBranches.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ThirdPartyView thirdParty = new ThirdPartyView();
				thirdParty.setVisible(true);
			}
		});
		mnBranch.add(mntmBranches);
		
		JMenuItem mntmSettings = new JMenuItem("Settings");
		mntmSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Settings ss = new Settings();
				ss.setVisible(true);
			}
		});
		mnBranch.add(mntmSettings);
		
		JMenu mnView = new JMenu("View");
		menuBar.add(mnView);
		
		JCheckBoxMenuItem chckbxmntmSessionData = new JCheckBoxMenuItem("Session data");
		mnView.add(chckbxmntmSessionData);
		
		JMenuItem mntmSessionFilter = new JMenuItem("Session filter");
		mnView.add(mntmSessionFilter);
		
		JMenu mnSortSessionsBy = new JMenu("Sort sessions by");
		mnView.add(mnSortSessionsBy);
		
		JMenuItem mntmSessionViewSettings = new JMenuItem("Session view settings");
		mnView.add(mntmSessionViewSettings);
		
		JMenu mnNewMenu = new JMenu("Separate view");
		mnView.add(mnNewMenu);
		
		JMenu mnCommunity = new JMenu("Community");
		menuBar.add(mnCommunity);
		
		JMenuItem mntmConnectToServer = new JMenuItem("Connect to server");
		mnCommunity.add(mntmConnectToServer);
		
		JMenuItem mntmSecurity = new JMenuItem("Security");
		mnCommunity.add(mntmSecurity);
		
		JMenuItem mntmNodeInfo = new JMenuItem("NodeServerEndpoint info");
		mnCommunity.add(mntmNodeInfo);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		JMenuItem mntmAbout = new JMenuItem("About");
		mnHelp.add(mntmAbout);
		
		JMenuItem mntmHowTo = new JMenuItem("How to");
		mnHelp.add(mntmHowTo);
		
		JMenuItem mntmPossibilities = new JMenuItem("Possibilities");
		mnHelp.add(mntmPossibilities);
		
		Component horizontalGlue_12 = Box.createHorizontalGlue();
		menuBar.add(horizontalGlue_12);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		menuBar.add(lblNewLabel);

		DefaultTableModel model = new DefaultTableModel();
		model.addColumn(new JButton("Hello"));

	}
	
	public void showIntegratedSessionDataWindow() {
		mainSplitPane = new JSplitPane();
		mainSplitPane.setPreferredSize(new Dimension(242, 300));
		mainSplitPane.setAlignmentX(Component.CENTER_ALIGNMENT);
		mainSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		SessionDataPanel sdp = new SessionDataPanel(JTabbedPane.TOP);
		mainSplitPane.setLeftComponent(panel_1);
		mainSplitPane.setRightComponent(sdp);
		panel.remove(panel_1);
		panel.add(mainSplitPane, BorderLayout.CENTER);
		panel.revalidate();
		txtrSrcData = sdp.txtrSrcData;
		txtrDstData = sdp.txtrDstData;
		txtrBothData = sdp.txtrBothData;
		if (sdf != null)
			sdf.dispose();

	}
	
	public void showSeparateSessionDataWindow() {
		if (mainSplitPane != null )
			panel.remove(mainSplitPane);
		panel.add(panel_1, BorderLayout.CENTER);
		sdf = new SessionDataFrame();
		sdf.setVisible(true);
		
		txtrSrcData = sdf.txtrSrcData;
		txtrDstData = sdf.txtrDstData;
		txtrBothData = sdf.txtrBothData;
		
		panel.revalidate();
		
	}
	
/*	public void hideSessionDataWindow() {
		if (mainSplitPane != null )
			panel.remove(mainSplitPane);
		panel.add(panel_1, BorderLayout.CENTER);
		panel.revalidate();
		txtrSrcData = null;
		txtrDstData = null;
	}
	*/
	
	public void onBranchStateChanged(Branch br) {
		if (br==null) {
			mntmInfo.setEnabled(false);
			mntmDataCapture.setEnabled(false);
			mntmDelete.setEnabled(false);
			mntmAddNetwork.setEnabled(false);
			mntmStart.setEnabled(false);
			mntmStatistic.setEnabled(false);
			return;
		}
		if (br.getId() == id) {
			switch (br.getState()) {
			case 0:
				mntmInfo.setEnabled(true);
				mntmDataCapture.setEnabled(true);
				mntmDelete.setEnabled(true);
				mntmAddNetwork.setEnabled(true);
				mntmStart.setEnabled(true);
				mntmStatistic.setEnabled(true);
				break;
			case 1:
				mntmInfo.setEnabled(true);
				mntmDataCapture.setEnabled(true);
				mntmDelete.setEnabled(true);
				mntmAddNetwork.setEnabled(true);
				mntmStart.setEnabled(true);
				mntmStatistic.setEnabled(true);
				break;
			case 2:
				mntmInfo.setEnabled(true);
				mntmDataCapture.setEnabled(true);
				mntmDelete.setEnabled(true);
				mntmAddNetwork.setEnabled(true);
				mntmStart.setEnabled(true);
				mntmStatistic.setEnabled(true);
				break;
			case 3:
				mntmInfo.setEnabled(true);
				mntmDataCapture.setEnabled(true);
				mntmDelete.setEnabled(true);
				mntmAddNetwork.setEnabled(true);
				mntmStart.setEnabled(false);
				mntmStatistic.setEnabled(true);
				break;

			default:
				break;
			}
		}
			
		stateControlTest.onBranchStateChanged(br);
	}
	
	// depracated, moved to controller
	public void startCapture_old(Branch curBr) {
	/*	curBr.startCapture(null);
		btnStart.setText("Stop");
		HashSet<Component> comps = getComponents(panel_1,
				null);
		EnableSwitch(true, comps);
	*/
	}
	

	public void initTable(JTable tbl) {
		tbl.setFillsViewportHeight(true);
		tbl.setShowGrid(false);
		
		MyTableModel model = new MyTableModel();
		model.setColumnCount(7);
		
		DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
		cellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		cellRenderer.setVerticalAlignment(SwingConstants.CENTER);
		tbl.setModel(model);
		tbl.setTableHeader(null);
		TableColumnModel tcm = tbl.getColumnModel();
		tcm.getColumn(0).setCellRenderer(cellRenderer);
		tcm.getColumn(1).setCellRenderer(cellRenderer);
		tcm.getColumn(2).setCellRenderer(cellRenderer);
		tcm.getColumn(3).setCellRenderer(cellRenderer);
		tcm.getColumn(4).setCellRenderer(cellRenderer);
		tcm.getColumn(5).setCellRenderer(cellRenderer);
		tcm.getColumn(6).setCellRenderer(cellRenderer);
		
		
		tcm.getColumn(1).setMaxWidth(2);
		tcm.getColumn(1).setMinWidth(2);
		tcm.getColumn(1).setPreferredWidth(2);
		
		tcm.getColumn(3).setMaxWidth(2);
		tcm.getColumn(3).setMinWidth(2);
		tcm.getColumn(3).setPreferredWidth(2);
		
		tcm.getColumn(5).setMaxWidth(2);
		tcm.getColumn(5).setMinWidth(2);
		tcm.getColumn(5).setPreferredWidth(2);
	}

	/**
	 * Display date when ip captured
	 * 
	 * @param data
	 *            - date & time when session start
	 */
	public void setActivated(String data) {
		lblDate.setText(data);
	}

	/**
	 * Display data counters values according to ip
	 * 
	 * @param up
	 *            - upload data
	 * @param down
	 *            - download data
	 */
	public void setDataCount(String up, String down) {
		lblData.setText(up + "/" + down);
	}

	/**
	 * Display session counters according to ip
	 * 
	 * @param act
	 *            - currently active session count
	 * @param svd
	 *            - all session count
	 */
	public void setSesCount(Integer act, Integer svd) {
		lblSes.setText(act + "/" + svd);
	}

	/**
	 * Display in/out session counters
	 * 
	 * @param in
	 *            - input session count
	 * @param out
	 *            - output session count
	 */
	public void setConnectionsCount(Integer in, Integer out) {
		lblInOut.setText(in + "/" + out);
	}

	public void printDumpLine(Object pack) {
/*
		if (pack instanceof TCPPacket)
			dumpArea.append(((TCPPacket) pack).toString() + "\n");
		if (pack instanceof UDPPacket)
			dumpArea.append(((UDPPacket) pack).toString() + "\n");
		if (pack instanceof ICMPPacket)
			dumpArea.append(((ICMPPacket) pack).toString() + "\n");
		if (pack instanceof ARPPacket)
			dumpArea.append(((ARPPacket) pack).toString() + "\n");
	*/
	}

	/**
	 * Sort input sessions
	 * 
	 * @param buf
	 *            - container with Session objects
	 * @return Sorted map
	 */
	public TreeMap<Long, Set<Session>> prepareTree(HashSet<Session> buf) {
		TreeMap<Long, Set<Session>> res = new TreeMap<Long, Set<Session>>();
		if (buf == null)
			return res;
		for (Session ses : buf) {
			long key = getSortValue(ses);
			if (res.containsKey(key)) {
				res.get(key).add(ses);
			} else {
				Set<Session> set = new HashSet<Session>();
				set.add(ses);
				res.put(key, set);
			}

		}
		return res;

	}

	/**
	 * Retreive sort parametr from session object
	 * 
	 * @param ses
	 *            - input session
	 * @return sort key
	 */
	public Long getSortValue(Session ses) {
		long result;
		switch (sessionView.comboSort.getSelectedIndex()) {
		case 0:
			result = ses.getDstDataLen();
			break;

		case 1:
			result = ses.getSrcDataLen();
			break;
		case 2:
			result = (long) ses.getDstP();
			break;
		case 3:
			result = (long) ses.getSrcP();
			break;

		default:
			result = ses.getDstDataLen();
			break;
		}
		return result;

	}
	
	public void clearIPInfoView() {
		lblNewLabel_4.setText("0.0.0.0");
		lblNewLabel_5.setText("DNS name");

		lblInOut.setText("0/0");
				
		lblSes.setText("0/0");
		DateFormat df = DateFormat.getInstance();
		// setSesCount(activeSes.size(), savedSes.size());
		setActivated(df.format(new Date(0)));
		setDataCount("0", "0");
	}
	
	public void deleteIPObserver(){
		if (selectedIP != null) {
			SessionsAPI sesApi = factory.getSesionInstance(currentNet.getId());
			IPaddr addr1 = sesApi.getIpInstance(selectedIP);
			addr1.deleteObserver(this);
			selectedIP = null;
			System.out.println(addr1.getAddr().getHostAddress()
					+ " delete observer");
		}

	}
	
	public void clearSessioListView() {
		sessionView.clearView();
	}

	/**
	 * Handle click event on ipaddress view</br> Build session tabs, add
	 * observer
	 * 
	 * @param ip
	 *            - ipaddress that was clicked
	 */
	public void onIPclick(String ip) {
		clearSessioListView();
		

		try {
			deleteIPObserver();
			if (ip == null) return;
			HashSet<Session> savedSes = new HashSet<Session>();
			HashSet<Session> activeSes = new HashSet<Session>();
			if (sessionView.btnInput.isSelected()) {
				activeSes = mainControl.getInputActiveSes(ip);
				savedSes = mainControl.getInputStoredSes(ip);
			}
			if (sessionView.btnOutput.isSelected()) {
				activeSes = mainControl.getOutputActiveSes(ip);
				savedSes = mainControl.getOutputStoredSes(ip);
			}

			buildActTab(prepareTree(activeSes));
			buildSvdTab(prepareTree(savedSes));
		//	System.out.println(sessionView.comboSort.getSelectedIndex());

			SessionsAPI sesApi = factory.getSesionInstance(currentNet.getId());
			IPaddr addr = sesApi.getIpInstance(ip);
			if (addr == null) {
				System.out.println("IPaddr was not found " + ip);
				return;
			}
			addr.addObserver(this);
			selectedIP = ip;
			buildIPInfo(addr);

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void buildIPInfo(IPaddr addr) {
		lblNewLabel_4.setText(addr.toString());
		lblNewLabel_5.setText(addr.getDnsName());
		lblInOut.setText(addr.getInSessionCount() + "/"
				+ addr.getOutSessionCount());
		lblSes.setText(addr.getActiveSesCount() + "/"
				+ addr.getSavedSesCount());

		DateFormat df = DateFormat.getInstance();
		// setSesCount(activeSes.size(), savedSes.size());
		setActivated(df.format(addr.getActivated()));
		String dataUpHumanSize = Controller.processSize(addr.getDataUp(), 2);
		String dataDownHumanSize = Controller.processSize(addr.getDataDown(), 2);
		setDataCount(dataUpHumanSize,dataDownHumanSize);
	}
	
	/**
	 * Build active sessions tab
	 * 
	 * @param tree
	 *            - input session map
	 */
	public void buildActTab(TreeMap<Long, Set<Session>> tree) {
		sessionView.activeTabRemoveAll();
	//	sessionView.panelTabAct.revalidate();
		sessionView.activeTabRevalidate();
		if (tree == null)
			return;
		Set<Long> keys = tree.keySet();
		for (long key : keys) {
			for (Session ses : tree.get(key)) {
				if (!sFilter.checkSession(ses))
					continue;
			//	SesItemPan sesPan = new SesItemPan(ses,false);
			//	ses.setView(sesPan);
				sessionView.addActiveSession(ses, false);
			}

		}
		Component glue = Box.createVerticalGlue();
	//	sessionView.panelTabAct.add(glue);
		sessionView.activeTabUpdateUI();

	}

	/**
	 * Build saved session tab
	 * 
	 * @param tree
	 *            - input session map
	 */
	public void buildSvdTab(TreeMap<Long, Set<Session>> tree) {
		sessionView.savedTabRemoveAll();
	//	sessionView.panelTabSvd.revalidate();
		sessionView.savedTabRevalidate();
		if (tree == null)
			return;
		Set<Long> keys = tree.keySet();
		for (long key : keys) {
			for (Session ses : tree.get(key)) {
				if (!sFilter.checkSession(ses))
					continue;
			//	SesItemPan sesPan = new SesItemPan(ses,false);
			//	ses.setView(sesPan);
			//	sessionView.panelTabSvd.add(sesPan);
				sessionView.addSavedSession(ses, false);
			}

		}
		Component glue = Box.createVerticalGlue();
	//	sessionView.panelTabSvd.add(glue);
		sessionView.savedTabUpdateUI();
		
	}

	public void addSession(Session ses) {
		if (!sFilter.checkSession(ses)) return;
	//	SesItemPan sesPan = new SesItemPan(ses,false);
	//	ses.setView(sesPan);
		if (ses.getClosed() == null ) {
			sessionView.addActiveSession(ses, false);
			sessionView.activeTabRevalidate();
		} else {
			sessionView.addSavedSession(ses, false);
			sessionView.savedTabRevalidate();
		}
		
	}
	@Override
	/**
	 * Updates info about ip during capture 
	 */
	public void update(Observable arg0, Object arg1) {
		if (arg0 instanceof IPaddr) {
			Session ses = null;
			if (arg1 instanceof Session)
				ses=(Session) arg1;
			updateIPInfo uO = new updateIPInfo(arg0,ses);
			SwingUtilities.invokeLater(uO);

		}

	}
	
	
	
	class updateIPInfo implements Runnable {
		private Observable arg0;
		private Session ses = null;
		public updateIPInfo(Observable arg0, Session ses) {
			// TODO Auto-generated constructor stub
			this.arg0 = arg0;
			this.ses = ses;
		}
		public void run() {
			IPaddr ip = (IPaddr) arg0;
			lblInOut.setText(ip.getInSessionCount() + "/"
					+ ip.getOutSessionCount());
			lblSes.setText(ip.getActiveSesCount() + "/"
					+ ip.getSavedSesCount());
			String dataUpHumanSize = Controller.processSize(ip.getDataUp(), 2);
			String dataDownHumanSize = Controller.processSize(ip.getDataDown(), 2);
			setDataCount(dataUpHumanSize,dataDownHumanSize);
			if (ses != null ) {
				if ((ses.getClosed() != null) & ses.getView() !=null) ses.getView().setVisible(false);
			boolean isIn = ip.isIn(ses);
				if (sessionView.btnInput.isSelected() & (isIn)) {
						addSession(ses);
				}
				if (sessionView.btnOutput.isSelected() & (!isIn)) {
						addSession(ses);
				}
			}
		}
	}

	class ipEventHandler implements Runnable {

		String ip;

		public ipEventHandler(String ip) {
			this.ip = ip;

		}

		public ipEventHandler(JList<String> list) {

			DefaultListModel<String> lModel = (DefaultListModel<String>) list
					.getModel();
			if (list.getSelectedIndex() == -1)
				return;
			ip = lModel.get(list.getSelectedIndex());
		}

		public void run() {
			
			onIPclick(ip);
			
		//	GetHostName bind = new GetHostName(ip, lblNewLabel_5);
		//	bind.resolve();
		}

	}

	public JFrame getFrame() {
		return frame;
	}
	public void setFrame(JFrame frame) {
		this.frame = frame;
	}
}



class MyTableModel extends DefaultTableModel {
	private HashSet<String> elems = new HashSet<String>();
	public MyTableModel() {
		super();
	}
	
	public void addIP(String ip) {
		if (ip==null) return;
		ip = ip.trim();
		String[] ipSplit = ip.split("\\.");
		if (ipSplit.length!=4) return;
		addRow(new String[] { ipSplit[0],".",ipSplit[1],".",ipSplit[2],".",ipSplit[3]});
		elems.add(ip);
	}
	public boolean exist(String ip) {
		if (elems.contains(ip.trim())) return true;
		return false;
	}
	
	public void removeIP(String ip) {
		for (Integer i =0;i< getRowCount();i++) {
			
				String[] splitIP = ip.split("\\.");
				if (splitIP[0].equals(this.getValueAt(i, 0)) & (splitIP[1].equals(this.getValueAt(i, 2))) & (splitIP[2].equals(this.getValueAt(i, 4))) & (splitIP[3].equals(this.getValueAt(i, 6)))) {
					this.removeRow(i);
					elems.remove(ip);
				}
			
		}
	}
	public void removeAll() {
		for (Integer i =0;i< elems.size();i++) {
			this.removeRow(0);
		}
		elems.clear();
	}
	public String getIPbyRow(Integer row) {
		if (row<0) return null;
		String ip = (String) this.getValueAt(row, 0)+"."+ (String) this.getValueAt(row, 2) +"."+ (String) this.getValueAt(row, 4)+"."+ (String) this.getValueAt(row, 6);
		return ip;
	}

	
}

