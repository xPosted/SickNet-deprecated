package com.jubaka.sors.view.statistic;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.jubaka.sors.sessions.StatisticLogic;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;


public class StatGlobalContentBuilder implements ContentBuilder {
	private Integer brId;
	public StatGlobalContentBuilder(Integer brId) {
		// TODO Auto-generated constructor stub
		this.brId = brId;
	}

	@Override
	public TimeSeriesCollection updateGrapth(Long timeFrom, Long timeTo) {
		// TODO Auto-generated method stub
		
		TimeSeriesCollection tsc = new TimeSeriesCollection();
		TimeSeries ts0 = null;
		TimeSeries ts1 = null;
		
		ts0 = StatisticLogic.createDataInSeries(brId, timeFrom, timeTo);
		ts1 = StatisticLogic.createDataOutSeries(brId,timeFrom, timeTo);
		
		tsc.addSeries(ts0);
		tsc.addSeries(ts1);
		
		return tsc;
		
	}

	@Override
	public TableModel updateTableModel(Long timeFrom, Long timeTo) {
		// TODO Auto-generated method stub
		DefaultTableModel model = new DefaultTableModel();
		model = StatisticLogic.getBaseTableModel(brId);
		return model;
	}

}
