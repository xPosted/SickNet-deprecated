package com.jubaka.sors.desktop.statistic;

import javax.swing.JPanel;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JFormattedTextField;
import java.awt.Dimension;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.SwingConstants;
import java.awt.Component;
import javax.swing.Box;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TimeRangePanel extends JPanel implements ActionListener {
	private JFormattedTextField ftfFrom;
	private JFormattedTextField ftfTo;
	private SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yy HH:mm:ss");	
	private StatMain statInst;
	/**
	 * Create the panel.
	 */
	public TimeRangePanel(StatMain statInst) {
		this.statInst = statInst;
		setBorder(new CompoundBorder(new LineBorder(new Color(128, 128, 128), 1, true), new EmptyBorder(5, 8, 5, 8)));
		setMaximumSize(new Dimension(32767, 40));
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		JLabel lblNewLabel = new JLabel("Time range:");
		add(lblNewLabel);
		MaskFormatter mask = null;
		try {
			mask = new  MaskFormatter("##.##.## ##:##:##");
		}catch (ParseException pe) {
			
		}
		mask.setPlaceholderCharacter('_'); 
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		add(horizontalStrut_1);
		
		JLabel lblNewLabel_1 = new JLabel("from");
		lblNewLabel_1.setFont(new Font("Dialog", Font.ITALIC, 12));
		add(lblNewLabel_1);
		ftfFrom = new JFormattedTextField(mask);
		ftfFrom.setHorizontalAlignment(SwingConstants.CENTER);
		ftfFrom.setPreferredSize(new Dimension(140, 19));
		ftfFrom.setMaximumSize(new Dimension(140, 2147483647));
		
		add(ftfFrom);
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		add(horizontalStrut);
		
		JLabel lblTo = new JLabel("to");
		lblTo.setFont(new Font("Dialog", Font.ITALIC, 12));
		add(lblTo);
		
		ftfTo = new JFormattedTextField(mask);
		ftfTo.setHorizontalAlignment(SwingConstants.CENTER);
		ftfTo.setPreferredSize(new Dimension(140, 19));
		ftfTo.setMaximumSize(new Dimension(140, 2147483647));
		add(ftfTo);
		
		Component horizontalStrut_2 = Box.createHorizontalStrut(20);
		add(horizontalStrut_2);
		
		JButton btnNewButton = new JButton("update");
		btnNewButton.addActionListener(this);
		btnNewButton.setMargin(new Insets(2, 5, 2, 5));
		add(btnNewButton);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String fromStr  = ftfFrom.getText();
		String toStr = ftfTo.getText();
		try {
			Date from = sdf.parse(fromStr);
			Date to = sdf.parse(toStr);
			statInst.updateContent(from.getTime(), to.getTime());
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	
	

}
