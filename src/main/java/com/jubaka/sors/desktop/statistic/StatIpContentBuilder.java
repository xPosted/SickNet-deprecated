package com.jubaka.sors.desktop.statistic;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.jubaka.sors.desktop.sessions.IPaddr;
import com.jubaka.sors.desktop.sessions.StatisticLogic;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;


public class StatIpContentBuilder implements ContentBuilder {
	public IPaddr ip;
	public StatIpContentBuilder(IPaddr ip) {
		// TODO Auto-generated constructor stub
		this.ip = ip;
	}
	
	@Override
	public TimeSeriesCollection updateGrapth(Long timeFrom, Long timeTo) {
		// TODO Auto-generated method stub
		TimeSeriesCollection tsc = new TimeSeriesCollection();
		TimeSeries ts0 = null;
		TimeSeries ts1 = null;
		
		ts0 = StatisticLogic.createIpDataInSeries(null, ip, null, null);
		ts1 = StatisticLogic.createIpDataOutSeries(null, ip, null, null);
		
		tsc.addSeries(ts0);
		tsc.addSeries(ts1);
		
		return tsc;
	}

	@Override
	public TableModel updateTableModel(Long timeFrom, Long timeTo) {
		// TODO Auto-generated method stub
		DefaultTableModel model = new DefaultTableModel();
		
		model = StatisticLogic.getIPTableModel(ip);
		
		return model;
	}
	

}
