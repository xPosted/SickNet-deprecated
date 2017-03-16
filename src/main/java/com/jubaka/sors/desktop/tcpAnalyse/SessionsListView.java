package com.jubaka.sors.tcpAnalyse;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;

import com.jubaka.sors.factories.ClassFactory;
import com.jubaka.sors.limfo.LoadLimits;
import com.jubaka.sors.sessions.Session;

public class SessionsListView extends JPanel {
	protected JToggleButton btnInput;
	protected JToggleButton btnOutput;
	private JPanel panelTabAct = new JPanel();
	private JPanel panelTabSvd = new JPanel();
	private SessionTableView tableTabAct;
	private SessionTableView tableTabSvd;
	private JButton btnFilter;
	protected JComboBox comboSort;
	protected JComboBox comboIPsort;
	protected JButton btnCustomize;
	private JScrollPane scrollPanelAct;
	private JScrollPane scrollPanelSvd;
	private boolean compaq = false;
	private boolean tableView = false;
	private JTabbedPane tabbedPane;
	private JPanel panelParentAct;
	private JPanel panelParentSvd;
	private LoadLimits ll = ClassFactory.getInstance().getLimits();
	private Set<Destroyable> currentActiveSessions = new HashSet<Destroyable>();
	private Set<Destroyable> currentSavedSessions = new HashSet<Destroyable>();

	/**
	 * Create the panel.
	 */
	public SessionsListView() {
		switch (ll.getSessionViewMode()) {
		case 0:
			tableView = false;
			compaq = false;
			break;
		case 1:
			tableView = false;
			compaq = true;
			break;
		case 2:
			tableView = true;
			compaq = false;
			break;

		default:
			break;
		}
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		JPanel panel_42 = new JPanel();
		panel_42.setPreferredSize(new Dimension(10, 35));
		panel_42.setMinimumSize(new Dimension(10, 25));
		panel_42.setMaximumSize(new Dimension(32767, 40));
		add(panel_42);
		panel_42.setLayout(new BoxLayout(panel_42, BoxLayout.X_AXIS));

		JPanel panel_43 = new JPanel();
		panel_43.setPreferredSize(new Dimension(50, 30));
		panel_43.setMinimumSize(new Dimension(10, 25));
		panel_42.add(panel_43);
		panel_43.setLayout(new BorderLayout(0, 0));

		btnInput = new JToggleButton("INPUT");
		btnInput.setPreferredSize(new Dimension(90, 25));
		btnInput.addActionListener(new BtnInputAction());
		panel_43.add(btnInput, BorderLayout.CENTER);

		JPanel panel_44 = new JPanel();
		panel_44.setPreferredSize(new Dimension(50, 30));
		panel_42.add(panel_44);
		panel_44.setLayout(new BorderLayout(0, 0));

		btnOutput = new JToggleButton("OUTPUT");
		btnOutput.addActionListener(new BtnOutputAction());
		panel_44.add(btnOutput, BorderLayout.CENTER);

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		add(tabbedPane);
		
		 panelParentAct = new JPanel();
		 panelParentAct.setLayout(new BorderLayout());
		 panelParentSvd = new JPanel();
		 panelParentSvd.setLayout(new BorderLayout());
		
		scrollPanelAct = new JScrollPane();
		scrollPanelSvd = new JScrollPane();
		scrollPanelAct.setViewportView(panelTabAct);
		scrollPanelSvd.setViewportView(panelTabSvd);

	//	panelParentAct.add(scrollPanelAct,BorderLayout.CENTER);
	//	panelParentSvd.add(scrollPanelSvd,BorderLayout.CENTER);
		
	//	tabbedPane.addTab("Active", null, scrollAct, null);
		tabbedPane.addTab("Active", null, panelParentAct, null);
		panelTabAct.setLayout(new BoxLayout(panelTabAct, BoxLayout.Y_AXIS));

	//	tabbedPane.addTab("Saved", null, scrollPanelSvd, null);
		tabbedPane.addTab("Saved", null, panelParentSvd, null);
		panelTabSvd.setLayout(new BoxLayout(panelTabSvd, BoxLayout.Y_AXIS));

		JPanel panel_29 = new JPanel();
		panel_29.setMaximumSize(new Dimension(32767, 20));
		panel_29.setPreferredSize(new Dimension(10, 20));
		add(panel_29);
		panel_29.setLayout(new BoxLayout(panel_29, BoxLayout.X_AXIS));

		JPanel panel_45 = new JPanel();
		panel_45.setMaximumSize(new Dimension(100, 32767));
		panel_29.add(panel_45);
		panel_45.setLayout(new BorderLayout(0, 0));

		btnFilter = new JButton("FIltered");
		btnFilter.setMaximumSize(new Dimension(100, 25));
		btnFilter.setPreferredSize(new Dimension(100, 25));
		btnFilter.setMinimumSize(new Dimension(100, 25));
		btnFilter.addActionListener(new BtnFilterAction());
		panel_45.add(btnFilter, BorderLayout.CENTER);

		Component horizontalStrut_26 = Box.createHorizontalStrut(10);
		panel_29.add(horizontalStrut_26);

		JPanel panel_46 = new JPanel();
		panel_29.add(panel_46);
		panel_46.setLayout(new BorderLayout(0, 0));

		btnCustomize = new JButton("customise");
		panel_46.add(btnCustomize, BorderLayout.CENTER);

		Component horizontalStrut_27 = Box.createHorizontalStrut(20);
		panel_29.add(horizontalStrut_27);

		JLabel lblSortBy = new JLabel("Sort by:");
		panel_29.add(lblSortBy);

		comboSort = new JComboBox();
		comboSort.addActionListener(new ComboSortAction());
		DefaultComboBoxModel<String> comboModelSort = new DefaultComboBoxModel<String>();
		comboModelSort.addElement("dst data");
		comboModelSort.addElement("src data");
		comboModelSort.addElement("dst port");
		comboModelSort.addElement("src port");
		comboSort.setModel(comboModelSort);

		comboSort.setPreferredSize(new Dimension(100, 24));
		panel_29.add(comboSort);
		btnCustomize.addActionListener(new BtnCustomizeAction());
		if (tableView) 
			setTableModeOn();
		else 
			setListModeOn();
	}

	public void setTableModeOn() {
		tableView = true;
		tableTabAct = new SessionTableView();
		tableTabSvd = new SessionTableView();
	
		/*scrollAct.removeAll();
		scrollAct.setViewportView(tableTabAct);
		scrollSvd.removeAll();
		scrollSvd.setViewportView(tableTabSvd);
		scrollAct.revalidate();
		scrollSvd.revalidate();
		scrollAct.updateUI();
		scrollSvd.updateUI();
*/
		activeTabRemoveAll();
		panelParentAct.removeAll();
		panelParentAct.add(tableTabAct,BorderLayout.CENTER);
		panelParentAct.revalidate();
		
		savedTabRemoveAll();
		panelParentSvd.removeAll();
		panelParentSvd.add(tableTabSvd,BorderLayout.CENTER);
		panelParentSvd.revalidate();
		
	}

	public void setListModeOn() {
		tableView = false;

	/*	scrollAct.removeAll();
		scrollAct.setViewportView(panelTabAct);
		scrollSvd.removeAll();
		scrollSvd.setViewportView(panelTabSvd);
		scrollAct.revalidate();
		scrollSvd.revalidate();
	 */
		panelParentAct.removeAll();
		panelParentAct.add(scrollPanelAct,BorderLayout.CENTER);
		panelParentAct.revalidate();
		
		panelParentSvd.removeAll();
		panelParentSvd.add(scrollPanelSvd,BorderLayout.CENTER);
		panelParentSvd.revalidate();
		
	}
	

	public void activeTabRemoveAll() {
		if (tableView) {
			tableTabAct.clearTable();
		} else {
			for (Destroyable d : currentActiveSessions) {
				d.destroy();
			}
			panelTabAct.removeAll();
		}
			
	}

	public void savedTabRemoveAll() {
		if (tableView) {
			tableTabSvd.clearTable();
		} else {
			for (Destroyable d : currentSavedSessions) {
				d.destroy();
			}
			panelTabSvd.removeAll();
		}
			
	}

	public void activeTabUpdateUI() {
		if (tableView) {
			tableTabAct.updateUI();
		} else
			panelTabAct.updateUI();
	}
	public void savedTabUpdateUI() {
		if (tableView) {
			tableTabSvd.updateUI();
		} else
			panelTabSvd.updateUI();
	}

	public void savedTabRevalidate() {
		if (tableView) {
			tableTabSvd.revalidate();
		} else
			panelTabSvd.revalidate();
	}

	public void activeTabRevalidate() {
		panelTabAct.revalidate();
	}

	public void clearView() {
		if (tableView) {
			tableTabAct.clearTable();
			tableTabSvd.clearTable();
		} else {
			activeTabRemoveAll();
			panelTabAct.revalidate();

			savedTabRemoveAll();
			panelTabSvd.revalidate();
		}

	}

	public void addActiveSession(Session ses, boolean statical) {
		if (tableView) {
			tableTabAct.addSession(ses);
		} else {

			JPanel sesPan;
			if (compaq) {
				sesPan = new SesItemPanCompact(ses, statical);
			} else {
				sesPan = new SesItemPan(ses, statical);
			}
			ses.setView(sesPan);
			panelTabAct.add(sesPan);
			currentActiveSessions.add((Destroyable)sesPan);
		}
		
	}

	public void addSavedSession(Session ses, boolean statical) {
		if (tableView) {
			tableTabSvd.addSession(ses);
		} else {
			JPanel sesPan;
			if (compaq) {
				sesPan = new SesItemPanCompact(ses, statical);
			} else {
				sesPan = new SesItemPan(ses, statical);
			}
			ses.setView(sesPan);
			panelTabSvd.add(sesPan);
			currentSavedSessions.add((Destroyable)sesPan);
		}
		

	}

	class BtnInputAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			Runnable run = new Runnable() {

				@Override
				public void run() {
					MainWin inst = MainWin.instance;
					if (btnOutput.isSelected())
						btnOutput.setSelected(false);
					inst.onIPclick(inst.selectedIP);

				}
			};
			SwingUtilities.invokeLater(run);
		}

	}

	class BtnOutputAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			Runnable run = new Runnable() {

				@Override
				public void run() {
					MainWin inst = MainWin.instance;
					if (btnInput.isSelected())
						btnInput.setSelected(false);
					inst.onIPclick(inst.selectedIP);

				}
			};
			SwingUtilities.invokeLater(run);
		}

	}

	class BtnFilterAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			EventQueue.invokeLater(new Runnable() {

				@Override
				public void run() {
					MainWin inst = MainWin.instance;
					SessionFilterView filterView = new SessionFilterView(
							inst.sFilter, btnFilter);
					filterView
							.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					filterView.setVisible(true);

				}
			});

		}

	}

	class ComboSortAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			MainWin inst = MainWin.instance;
			inst.onIPclick(inst.selectedIP);
		}

	}

	class BtnCustomizeAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			MainWin inst = MainWin.instance;
			DataCatchView dc = new DataCatchView(inst.id);
			// DataCatch dc = new DataCatch(id);
			dc.setVisible(true);
		}

	}

	public boolean isCompaq() {
		return compaq;
	}

	public void setCompaq(boolean compaq) {
		this.compaq = compaq;
	}

}
