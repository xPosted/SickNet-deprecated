package com.jubaka.sors.view.statistic;

import java.awt.BorderLayout;
import java.text.SimpleDateFormat;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.BoxLayout;
import java.awt.Dimension;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JSplitPane;

import com.jubaka.sors.factories.ClassFactory;
import com.jubaka.sors.sessions.IPaddr;
import com.jubaka.sors.sessions.SessionsAPI;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import com.jubaka.sors.sessions.Subnet;

import javax.swing.JScrollPane;
import javax.swing.JTable;

public class StatMain extends JFrame {
	
	private Integer branchId = null;
	private JPanel contentPane;
	private SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yy HH:mm:ss");
	private JTable table;
	private ContentBuilder cb;
	
	JPanel panel_2; // chartPanel
	
	
	public StatMain(IPaddr ip, Integer brId) {
		this.branchId = brId;
		StatIPViewHeader ipHeader = new StatIPViewHeader(ip);
		init(ipHeader);
		
		cb = new StatIpContentBuilder(ip);
		updateContent(null, null);
		
	}
	
	public StatMain(Subnet net,Integer brId) {
		this.branchId = brId;
		StatSubnetViewHeader header = new StatSubnetViewHeader(net);
		init(header);
		
		cb = new SubnetContentBuilder(net);
		updateContent(null, null);
		
		table.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				if (me.getClickCount()==2) {
					DefaultTableModel model = (DefaultTableModel) table.getModel();
					String ipStr = (String)model.getValueAt(table.getSelectedRow(), 0);
					IPaddr ip = IPaddr.getInstance(branchId, ipStr);
					StatMain statView = new StatMain(ip,branchId);
					statView.setVisible(true);
				}
			}
		});
	}
	
	/**
	 * @wbp.parser.constructor
	 */
	public StatMain(Integer brId) {
		final Integer id = brId;
		StatMainViewHeader header = new StatMainViewHeader(brId);
		init(header);
		
		cb = new StatGlobalContentBuilder(brId);
		updateContent(null, null);
		
		
		table.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				if (me.getClickCount()==2) {
					DefaultTableModel model = (DefaultTableModel) table.getModel();
					String netStr = (String)model.getValueAt(table.getSelectedRow(), 0);
					SessionsAPI sapi = ClassFactory.getInstance().getSesionInstance(id);
					
					Subnet net = sapi.getNetByName(netStr.split("/")[0]);
					StatMain statView = new StatMain(net,id);
					statView.setVisible(true);
				}
			}
		});
	}
	
	public void updateContent(Long timeFrom, Long timeTo) {
		
		TimeSeriesCollection tsc = cb.updateGrapth(timeFrom, timeTo);
		DefaultTableModel model = (DefaultTableModel) cb.updateTableModel(timeFrom, timeTo);
		table.setModel(model);
		
		JFreeChart chart = ChartFactory.createTimeSeriesChart("Stat Beta", "Time", "Value", tsc);
		ChartPanel chPanel = new ChartPanel(chart);
		panel_2.removeAll();
		panel_2.add(chPanel, BorderLayout.CENTER);
		
		
	}
	
	private void init(JPanel header) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 579, 411);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		if (header != null)	panel.add(header);
		TimeRangePanel trp = new TimeRangePanel(this);
		panel.add(trp);
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JSplitPane splitPane = new JSplitPane();
		
		panel_1.add(splitPane);
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		
		panel_2 = new JPanel();
		panel_2.setMinimumSize(new Dimension(10, 150));
		panel_2.setPreferredSize(new Dimension(10, 150));
		splitPane.setLeftComponent(panel_2);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		
		JPanel panel_3 = new JPanel();
		splitPane.setRightComponent(panel_3);
		panel_3.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		panel_3.add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable();
		table.setFillsViewportHeight(true);
		scrollPane.setViewportView(table);
	}
}
