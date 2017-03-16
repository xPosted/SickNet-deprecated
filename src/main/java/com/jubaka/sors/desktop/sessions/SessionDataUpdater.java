package com.jubaka.sors.sessions;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.swing.JTextArea;


public class SessionDataUpdater implements Runnable {

	private Integer curId;
	/**
	 * static field is neded for adding content of the
	 */
	private static Integer id;
	private ArrayList<String> data;
	JTextArea dataView;
	Thread thisTH;

	public SessionDataUpdater(JTextArea dataView, Integer curID,
			ArrayList<String> data) {
		this.curId = curID;
		id = curID;
		this.data = data;
		this.dataView = dataView;
	}

	public void run() {

		dataView.setText("");
		if (data == null)
			return;
		for (String item : data) {
			if (curId == id) {
				try {
					item = new String(item.getBytes(), "UTF-8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				dataView.append(item + "\n");
			} else {
				return;
			}
		}

	}

}

