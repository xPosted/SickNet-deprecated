package com.jubaka.sors.tcpAnalyse;

import java.awt.BorderLayout;
import javax.swing.JPanel;

import com.jubaka.sors.sessions.Session;
import com.jubaka.sors.sessions.StatisticLogic;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import javax.swing.JLabel;
import java.awt.Font;

public class SessionChartPanel extends JPanel {

	private JPanel contentPane;
	private JPanel panel = new JPanel();

	/**
	 * @wbp.parser.constructor
	 */
	public SessionChartPanel(Session ses) {
		setLayout(new BorderLayout());
		
		panel.setLayout(new BorderLayout(0, 0));
		TimeSeriesCollection tsc = new TimeSeriesCollection();
		TimeSeries ts0 = StatisticLogic.createSessionSrcTS(ses);
		TimeSeries ts1 = StatisticLogic.createSessionDstTS(ses);
		
		tsc.addSeries(ts1);
		tsc.addSeries(ts0);
		
		
		JFreeChart chart = ChartFactory.createTimeSeriesChart("Session Chart", "Time", "Value", tsc);
		
		JLabel lblHelloNiga = new JLabel("Hello niga");
		lblHelloNiga.setFont(new Font("Dialog", Font.BOLD, 28));
		panel.add(lblHelloNiga, BorderLayout.NORTH);
		ChartPanel chPanel = new ChartPanel(chart);
		panel.add(chPanel, BorderLayout.CENTER);
		add(panel, BorderLayout.CENTER);
	}
	
	
	
	

}
