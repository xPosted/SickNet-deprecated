package com.jubaka.sors.desktop.statistic;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.jubaka.sors.desktop.sessions.StatisticLogic;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import com.jubaka.sors.desktop.sessions.Subnet;

public class SubnetContentBuilder implements ContentBuilder {
	Subnet net;
	
	public SubnetContentBuilder(Subnet net) {
		this.net = net;
	}

	@Override
	public TimeSeriesCollection updateGrapth(Long timeFrom, Long timeTo) {
		// TODO Auto-generated method stub
		TimeSeriesCollection tsc = new TimeSeriesCollection();
		TimeSeries ts0 = null;
		TimeSeries ts1 = null;
		
		ts0 = StatisticLogic.createNetworkDataInSeries(null,net, timeFrom, timeTo);
		ts1 = StatisticLogic.createNetworkDataOutSeries(null,net, timeFrom, timeTo);
		
		tsc.addSeries(ts0);
		tsc.addSeries(ts1);
		
		return tsc;
	}

	@Override
	public TableModel updateTableModel(Long timeFrom, Long timeTo) {
		// TODO Auto-generated method stub
		DefaultTableModel model = new DefaultTableModel();
		model = StatisticLogic.getNetTableModel(net);
		
		return model;
	}

}
