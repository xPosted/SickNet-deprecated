package com.jubaka.sors.desktop.unitTests;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.ButtonGroup;
import javax.swing.JScrollPane;
import javax.swing.JTree;

import com.jubaka.sors.desktop.tcpAnalyse.SettingsListItem;

public class TreeTest extends JFrame {

	private JPanel contentPane;
	private ButtonGroup bg= new ButtonGroup();
	private final JScrollPane scrollPane = new JScrollPane();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TreeTest frame = new TreeTest();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TreeTest() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		MyNode top = new MyNode(new JPanel());
		MyNode node = new MyNode (new SettingsListItem("General"));
		MyNode node2 = new MyNode(new SettingsListItem("Security"));
		node.add(node2);
		top.add(node);
		DefaultTreeModel model = new DefaultTreeModel(top);
		JTree tree = new JTree(model);
		tree.setCellRenderer(new MyRenderer());
		tree.setRootVisible(false);
		scrollPane.setViewportView(tree);
		
		
	}
}

class MyNode extends DefaultMutableTreeNode {
	
	public MyNode(Object userobject) {
		super(userobject);
	}
	public Object getUserObject() {
		return userObject;
	}
}

class MyRenderer implements TreeCellRenderer {

	@Override
	public Component getTreeCellRendererComponent(JTree arg0, Object arg1,
			boolean arg2, boolean arg3, boolean arg4, int arg5, boolean arg6) {
		DefaultMutableTreeNode  node = (DefaultMutableTreeNode) arg1;
	//	if (node.getUserObject() instanceof Component)
			System.out.println("comp " +node.getUserObject());
		return (Component)node.getUserObject();
	//return new SettingsListItem("Security");
	}
	
}
class test {
	public String toString() {
		return "Hello niga";
	}
}