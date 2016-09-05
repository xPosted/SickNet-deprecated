package com.jubaka.sors.tcpAnalyse;

import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JSeparator;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import java.awt.Component;

import javax.swing.Box;

import java.awt.Font;

import javax.swing.border.EtchedBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.MatteBorder;

import java.awt.Color;

import javax.swing.border.BevelBorder;

import com.jubaka.sors.sessions.Session;
import com.jubaka.sors.sessions.SimpleDataSaver;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.ImageIcon;

import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.UIManager;

import java.awt.BorderLayout;

public class SesItemPanCompact extends JPanel implements Observer, Destroyable,Runnable {

	JLabel lblinitTime = new JLabel("initTime");
	JLabel lbldstIP = new JLabel("dstIP");
	JLabel lblFinTime = new JLabel("FinishTime");
	JLabel lblBCount = new JLabel("srcB/dstB");
	JLabel lblport = new JLabel("srcP/dstP");
	JButton change = new JButton("");
	JLabel lblHttp;

	private String dst;
	private String src;
	private String init;
	private String srcBHuman;
	private String dstBHuman;
	private long srcB;
	private long dstB;
	private Integer srcP;
	private Integer dstP;
	private String finish;
	private Color oldBackGround;
	private Session sesStub;
	private JLabel srcIPlbl;
	private JPanel inst;
	private static JPanel selected = null;
	private JPanel panel_1;

	/**
	 * Create the panel.
	 */
	private void buildSesParam(Session ses) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yy hh:mm:ss");

		sesStub = ses;
		dst = ses.getDstIP().getAddr().getHostAddress();
		src = ses.getSrcIP().getAddr().getHostAddress();
		init = sdf.format(ses.getEstablished());
		srcB = ses.getSrcDataLen();
		dstB = ses.getDstDataLen();
		srcP = ses.getSrcP();
		dstP = ses.getDstP();
		srcBHuman = Controller.processSize(srcB, 2);
		dstBHuman = Controller.processSize(dstB, 2);
		if (ses.getClosed() != null)
			finish = sdf.format(ses.getClosed());
		else
			finish = "Online";
		lbldstIP.setText(dst);
		srcIPlbl.setText(src);
		if (ses.getDataSaver() instanceof SimpleDataSaver)
			lblBCount.setForeground(Color.blue);
		panel_1.add(lblport);
		lblport.setText("TCP(src/dst): " + srcP + "/" + dstP);

		lblFinTime.setText(finish);
		lblinitTime.setText(init);

		Component horizontalStrut = Box.createHorizontalStrut(20);
		panel_1.add(horizontalStrut);
		panel_1.add(lblBCount);
		lblBCount.setText(srcBHuman + "/" + dstBHuman);
		if (ses.getEstablished().getTime() == 0) {
			change.setVisible(true);
		}
		if (ses.getHTTPList().size() > 0) {
			lblHttp.setVisible(true);
		}
		ses.addObserver(this);

	}

	public void mouseEnter() {

		oldBackGround = this.getBackground();
		this.setBackground(Color.black);

	}

	public void mouseExit() {
		this.setBackground(oldBackGround);
	}

	public void mouseClc(MouseEvent e) {
		if (selected != null) {
			selected.setBorder(new CompoundBorder(new EtchedBorder(
					EtchedBorder.LOWERED, null, null), new BevelBorder(
					BevelBorder.LOWERED, null, null, null, null)));
		}
		inst.setBorder(new CompoundBorder(new EtchedBorder(
				EtchedBorder.LOWERED, null, null), new MatteBorder(3, 3, 3, 3,
				(Color) new Color(163, 184, 204))));
		selected = inst;

		MainWin mainWin = MainWin.instance;
		// Integer border = mainWin.getColumnCountofSesDataPan();
		byte[] srcData = sesStub.getSrcData();
		byte[] dstData = sesStub.getDstData();

		// random number that identify every click event
		Random r = new Random();
		Integer id = r.nextInt();

		ArrayList<String> srcRes = (ArrayList<String>) Controller
				.decodeData(srcData);
		ArrayList<String> dstRes = (ArrayList<String>) Controller
				.decodeData(dstData);

		mainWin.setSrcData(srcRes, id);
		mainWin.setDstData(dstRes, id);

		Integer counter = 0;
		if (e.getClickCount() == 2) {
			SessionInfoView sessionDetails = new SessionInfoView(sesStub);
			sessionDetails.setVisible(true);
		}

	}

	public SesItemPanCompact(Session ses, boolean statical) {
		inst = this;
		if (!statical) {
			addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					mouseEnter();
				}

				@Override
				public void mouseExited(MouseEvent e) {
					mouseExit();
				}

				@Override
				public void mouseClicked(MouseEvent e) {

					mouseClc(e);

				}
			});
		}
		setPreferredSize(new Dimension(550, 35));
		setMaximumSize(new Dimension(32767, 35));
		setBorder(new CompoundBorder(new EtchedBorder(EtchedBorder.LOWERED,
				null, null), new BevelBorder(BevelBorder.LOWERED, null, null,
				null, null)));
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		final JPanel panel = new JPanel();

		add(panel);

		panel_1 = new JPanel();
		if (!statical)
			panel_1.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					mouseEnter();
				}

				@Override
				public void mouseExited(MouseEvent e) {
					mouseExit();
				}

				@Override
				public void mouseClicked(MouseEvent e) {
					mouseClc(e);
				}
			});
		panel.setLayout(new BorderLayout(0, 0));
		panel_1.setMaximumSize(new Dimension(32767, 25));
		panel.add(panel_1);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));

		Component horizontalStrut = Box.createHorizontalStrut(10);
		panel_1.add(horizontalStrut);

		lblinitTime.setFont(new Font("Dialog", Font.PLAIN, 12));
		panel_1.add(lblinitTime);

		Component horizontalStrut_2 = Box.createHorizontalStrut(15);
		panel_1.add(horizontalStrut_2);
		lblFinTime.setFont(new Font("Dialog", Font.PLAIN, 12));
		panel_1.add(lblFinTime);

		Component horizontalStrut_4 = Box.createHorizontalStrut(25);
		panel_1.add(horizontalStrut_4);

		lblFinTime.setFont(new Font("Dialog", Font.PLAIN, 12));

		srcIPlbl = new JLabel("192.168.0.1");
		srcIPlbl.setFont(new Font("DejaVu Serif Condensed", Font.BOLD, 14));

		panel_1.add(srcIPlbl);

		Component horizontalGlue = Box.createHorizontalGlue();
		panel_1.add(horizontalGlue);
		change.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				sesStub.changeSrcDst();
				buildSesParam(sesStub);
			}
		});

		change.setVisible(false);

		Component horizontalStrut_5 = Box.createHorizontalStrut(5);
		panel_1.add(horizontalStrut_5);
		change.setPreferredSize(new Dimension(30, 10));
		change.setMinimumSize(new Dimension(30, 10));
		change.setMargin(new Insets(0, 0, 0, 0));
		change.setIcon(new ImageIcon(SesItemPan.class
				.getResource("/change_19_20.png")));
		panel_1.add(change);

		Component horizontalStrut_7 = Box.createHorizontalStrut(5);
		panel_1.add(horizontalStrut_7);

		lblHttp = new JLabel("HTTP://");
		panel_1.add(lblHttp);
		lblHttp.setVisible(false);
		lblHttp.setFont(new Font("DejaVu Sans Condensed", Font.BOLD, 11));
		lblHttp.setOpaque(true);
		lblHttp.setForeground(Color.BLACK);
		lblHttp.setBackground(UIManager.getColor("List.dropLineColor"));
		lblHttp.setMaximumSize(new Dimension(58, 15));
		lblHttp.setPreferredSize(new Dimension(44, 15));

		Component horizontalGlue_2 = Box.createHorizontalGlue();
		panel_1.add(horizontalGlue_2);

		lbldstIP.setFont(new Font("DejaVu Sans Condensed", Font.BOLD, 14));
		panel_1.add(lbldstIP);

		Component horizontalStrut_1 = Box.createHorizontalStrut(25);
		panel_1.add(horizontalStrut_1);

		lblport.setFont(new Font("Dialog", Font.PLAIN, 12));

		lblBCount.setFont(new Font("Dialog", Font.PLAIN, 12));
		buildSesParam(ses);

	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(this);
		
	}

	@Override
	public void destroy() {
		sesStub.deleteObserver(this);
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		srcB = sesStub.getSrcDataLen();
		dstB = sesStub.getDstDataLen();
	
		srcBHuman = Controller.processSize(srcB, 2);
		dstBHuman = Controller.processSize(dstB, 2);
		
		lblBCount.setText(srcBHuman + "/" + dstBHuman);
		
	}
	
	
	
	
	
}
