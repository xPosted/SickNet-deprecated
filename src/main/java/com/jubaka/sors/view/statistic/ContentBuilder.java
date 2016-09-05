package com.jubaka.sors.view.statistic;

import javax.swing.table.TableModel;

import org.jfree.data.time.TimeSeriesCollection;

public interface ContentBuilder {
	public TimeSeriesCollection updateGrapth(Long timeFrom, Long timeTo);
	public TableModel updateTableModel(Long timeFrom, Long timeTo);
}
