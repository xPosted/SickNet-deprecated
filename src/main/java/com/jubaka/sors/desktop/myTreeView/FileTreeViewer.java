package com.jubaka.sors.desktop.myTreeView;

import com.jubaka.sors.desktop.sessions.IPaddr;
import com.jubaka.sors.desktop.sessions.Session;
import com.jubaka.sors.desktop.sessions.Subnet;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.io.File;
import java.util.HashSet;
import java.util.Set;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;



public class FileTreeViewer  extends JPanel {

private static final long serialVersionUID = 1L;
public static final ImageIcon ICON_COMPUTER =  new ImageIcon("");
public static final ImageIcon ICON_DISK =  new ImageIcon("defaults1.png");
public static final ImageIcon ICON_FOLDER =   new ImageIcon("fol_orig.png");
public static final ImageIcon ICON_EXPANDEDFOLDER =  new ImageIcon("folder_open.png");

protected JTree  m_tree;
protected DefaultTreeModel m_model;

AddCheckBoxToTree AddCh = new AddCheckBoxToTree();

private AddCheckBoxToTree.CheckTreeManager checkTreeManager;


protected TreePath m_clickedPath;

public FileTreeViewer(Set<Subnet> nets)
{
    setSize(400, 300);
    setLayout(new BorderLayout());
    DefaultMutableTreeNode top = new DefaultMutableTreeNode(
            new IconData(ICON_COMPUTER, null, "Computer"));

    DefaultMutableTreeNode node;
    File[] roots = File.listRoots();
    for (Subnet net : nets)
    {
        node = new DefaultMutableTreeNode(new IconData(ICON_DISK, null,net ));
        top.add(node);
        node.add(new DefaultMutableTreeNode( new Boolean(true) ));
    }

    m_model = new DefaultTreeModel(top);

    m_tree = new JTree(m_model);
    /*{
        public String getToolTipText(MouseEvent ev) 
        {
            if(ev == null)
                return null;
            TreePath path = m_tree.getPathForLocation(ev.getX(), 
                    ev.getY());
            if (path != null)
            {
                FileNode fnode = getFileNode(getTreeNode(path));
                if (fnode==null)
                    return null;
                File f = fnode.getFile();
                return (f==null ? null : f.getPath());
            }
            return null;
        }
    };
*/
    ToolTipManager.sharedInstance().registerComponent(m_tree);

    m_tree.putClientProperty("JTree.lineStyle", "Angled");

    TreeCellRenderer renderer = new IconCellRenderer();
    m_tree.setCellRenderer(renderer);

    m_tree.addTreeExpansionListener(new  DirExpansionListener());

    m_tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION); 
    m_tree.setShowsRootHandles(true); 
    m_tree.setEditable(false);


    checkTreeManager = AddCh.new CheckTreeManager(m_tree, null);



    JScrollPane s = new JScrollPane();
    s.setViewportView(m_tree);
    add(s, BorderLayout.CENTER);
    
    JButton btnShowSelected = new JButton("show selected");
    btnShowSelected.addActionListener(new ActionListener() {
    	public void actionPerformed(ActionEvent arg0) {
    		
    		DefaultMutableTreeNode root = (DefaultMutableTreeNode)  m_model.getRoot();
    		
    		Integer netCount = root.getChildCount();
    		for (Integer i=0; i<netCount; i++) {
    			DefaultMutableTreeNode someNetwork = (DefaultMutableTreeNode) root.getChildAt(i);
    			Object objNet =  someNetwork.getUserObject();
    			
    			if (objNet instanceof IconData) {				// is network selected, if true go to next subnet
    				IconData iDataNetwork = (IconData) objNet;
    				if (iDataNetwork.isNodeChecked()) {
    					System.out.println(iDataNetwork.getObject());
    					continue;
    				}
    			}
    			Integer ipsCount = someNetwork.getChildCount();
    			for (Integer j=0; j<ipsCount;j++) {
    				DefaultMutableTreeNode ipNode = (DefaultMutableTreeNode) someNetwork.getChildAt(j);
    				Object obj = ipNode.getUserObject();
    				if (obj instanceof IconData) {				// is ip selected
    				IconData iData = (IconData) ipNode.getUserObject();
    				if (iData.isNodeChecked()) {
    					System.out.println(iData.getObject());
    				}
    				}
    				
    			}
    		}
    	}
    });
    add(btnShowSelected, BorderLayout.SOUTH);
}

public Set<Session> getSelectedItems() {
	
	DefaultMutableTreeNode root = (DefaultMutableTreeNode)  m_model.getRoot();
	HashSet<Session> selectedItems = new HashSet<Session>();
	Integer netCount = root.getChildCount();
	
	
	for (Integer i=0; i<netCount; i++) {
		DefaultMutableTreeNode someNetwork = (DefaultMutableTreeNode) root.getChildAt(i);
		Object objNet =  someNetwork.getUserObject();
		
		
		if (objNet instanceof IconData) {				// is network selected, if true go to next subnet
			IconData iDataNetwork = (IconData) objNet;
			if (iDataNetwork.isNodeChecked()) {
				Object subnetObj = iDataNetwork.getObject();
				if (subnetObj instanceof Subnet) {
					Subnet net = (Subnet) subnetObj;
					fillSessionSet(net, selectedItems);
				}
				System.out.println(iDataNetwork.getObject());
				
				continue;
			}
		}
		Integer ipsCount = someNetwork.getChildCount();
		for (Integer j=0; j<ipsCount;j++) {
			DefaultMutableTreeNode ipNode = (DefaultMutableTreeNode) someNetwork.getChildAt(j);
			Object obj = ipNode.getUserObject();
			if (obj instanceof IconData) {				// is ip selected
			IconData iData = (IconData) ipNode.getUserObject();
			if (iData.isNodeChecked()) {
				Object ipObj = iData.getObject();
				if (ipObj instanceof IPaddr) {
					IPaddr addr = (IPaddr) ipObj;
					fillSessionSet(addr, selectedItems);
				}
				System.out.println(iData.getObject());
			}
			}
			
		}
	}
	return selectedItems;
}

private void fillSessionSet(IPaddr addr, Set<Session> set) {
	set.addAll(addr.getInputActiveSessions());
	set.addAll(addr.getInputStoredSessions());
	set.addAll(addr.getOutputActiveSessions());
	set.addAll(addr.getOutputStoredSessions());
	
}


private void fillSessionSet(Subnet net, Set<Session> set) { 
	for (IPaddr ip : net.getIps()) {
		fillSessionSet(ip, set);
	}
}

DefaultMutableTreeNode getTreeNode(TreePath path)
{
    return (DefaultMutableTreeNode)(path.getLastPathComponent());
}

private Subnet getSubnetFromTreeNode(DefaultMutableTreeNode node)
{
    if (node == null)
        return null;
    Object obj = node.getUserObject();
    if (obj instanceof IconData)
        obj = ((IconData)obj).getObject();
    if (obj instanceof Subnet)
        return (Subnet)obj;
    else
        return null;
}

public AddCheckBoxToTree.CheckTreeManager getCheckTreeManager() {
    return checkTreeManager;
}

// Make sure expansion is threaded and updating the tree model
// only occurs within the event dispatching thread.
class DirExpansionListener implements TreeExpansionListener
{
    public void treeExpanded(TreeExpansionEvent event)
    {
        final DefaultMutableTreeNode node = getTreeNode(
                event.getPath());
        final Subnet net = getSubnetFromTreeNode(node);
        Thread runner = new Thread() 
        {
            public void run() 
            {
                if (net != null) 
                {	
                	DefaultMutableTreeNode flag = (DefaultMutableTreeNode)node.getFirstChild();
                    if (flag==null)    // No flag
                        return;
                    Object obj = flag.getUserObject();
                    if (!(obj instanceof Boolean))
                        return;      // Already expanded
                	node.removeAllChildren();
                	for (IPaddr addr : net.getIps()) {
                		DefaultMutableTreeNode ipSubNode = new DefaultMutableTreeNode(new IconData(ICON_DISK, null,addr ));
                		node.add(ipSubNode);
                	}
                    Runnable runnable = new Runnable() 
                    {
                        public void run() 
                        {
                            m_model.reload(node);
                        }
                    };
                    SwingUtilities.invokeLater(runnable);
                }
            }
        };
        runner.start();
    }

    public void treeCollapsed(TreeExpansionEvent event) {}
}

}

class IconCellRenderer extends JLabel implements TreeCellRenderer{
protected Color m_textSelectionColor;
protected Color m_textNonSelectionColor;
protected Color m_bkSelectionColor;
protected Color m_bkNonSelectionColor;
protected Color m_borderSelectionColor;

protected boolean m_selected;

public IconCellRenderer()
{
    super();
    m_textSelectionColor = UIManager.getColor(
            "Tree.selectionForeground");
    m_textNonSelectionColor = UIManager.getColor(
            "Tree.textForeground");
    m_bkSelectionColor = UIManager.getColor(
            "Tree.selectionBackground");
    m_bkNonSelectionColor = UIManager.getColor(
            "Tree.textBackground");
    m_borderSelectionColor = UIManager.getColor(
            "Tree.selectionBorderColor");
    setOpaque(false);
}

public Component getTreeCellRendererComponent(JTree tree, 
        Object value, boolean sel, boolean expanded, boolean leaf, 
        int row, boolean hasFocus) 

{
    DefaultMutableTreeNode node = 
            (DefaultMutableTreeNode)value;
    Object obj = node.getUserObject();
    setText(obj.toString());

    if (obj instanceof Boolean)
        setText("Retrieving data...");

    if (obj instanceof IconData)
    {
        IconData idata = (IconData)obj;
        if (expanded)
            setIcon(idata.getExpandedIcon());
        else
            setIcon(idata.getIcon());
    }
    else
        setIcon(null);

    setFont(tree.getFont());
    setForeground(sel ? m_textSelectionColor : 
        m_textNonSelectionColor);
    setBackground(sel ? m_bkSelectionColor : 
        m_bkNonSelectionColor);
    m_selected = sel;
    return this;
}

public void paintComponent(Graphics g) 
{
    Color bColor = getBackground();
    Icon icon = getIcon();

    g.setColor(bColor);
    int offset = 0;
    if(icon != null && getText() != null) 
        offset = (icon.getIconWidth() + getIconTextGap());
    g.fillRect(offset, 0, getWidth() - 1 - offset,
            getHeight() - 1);

    if (m_selected) 
    {
        g.setColor(m_borderSelectionColor);
        g.drawRect(offset, 0, getWidth()-1-offset, getHeight()-1);
    }

    super.paintComponent(g);
}


}

class IconData {
protected Icon   m_icon;
protected Icon   m_expandedIcon;
protected Object m_data;
protected boolean nodeChecked = false;

public IconData(Icon icon, Object data)
{
    m_icon = icon;
    m_expandedIcon = null;
    m_data = data;
}

public IconData(Icon icon, Icon expandedIcon, Object data)
{
    m_icon = icon;
    m_expandedIcon = expandedIcon;
    m_data = data;
}

public void setNodeChecked(boolean value) {
	
	nodeChecked = value;
}

public boolean isNodeChecked() {
	return nodeChecked;
}

public Icon getIcon() 
{ 
    return m_icon;
}

public Icon getExpandedIcon() 
{ 
    return m_expandedIcon!=null ? m_expandedIcon : m_icon;
}

public Object getObject() 
{ 
    return m_data;
}

public String toString() 
{ 
    return m_data.toString();
}
}


/*
class FileNode {
protected File m_file;

public FileNode(File file)
{
    m_file = file;
}

public File getFile() 
{ 
    return m_file;
}

public String toString() 
{ 
    return m_file.getName().length() > 0 ? m_file.getName() : 
        m_file.getPath();
}

public boolean expand(DefaultMutableTreeNode parent){
    DefaultMutableTreeNode flag = (DefaultMutableTreeNode)parent.getFirstChild();
    if (flag==null)    // No flag
        return false;
    Object obj = flag.getUserObject();
    if (!(obj instanceof Boolean))
        return false;      // Already expanded

    parent.removeAllChildren();  // Remove Flag

    File[] files = listFiles();
    if (files == null)
        return true;

    Vector<FileNode> v = new Vector<FileNode>();

    for (int k=0; k<files.length; k++){
        File f = files[k];
        if (!(f.isDirectory()))
            continue;

        FileNode newNode = new FileNode(f);

        boolean isAdded = false;
        for (int i=0; i<v.size(); i++)
        {
            FileNode nd = (FileNode)v.elementAt(i);
            if (newNode.compareTo(nd) < 0)
            {
                v.insertElementAt(newNode, i);
                isAdded = true;
                break;
            }
        }
        if (!isAdded)
            v.addElement(newNode);
    }

    for (int i=0; i<v.size(); i++){
        FileNode nd = (FileNode)v.elementAt(i);
        IconData idata = new IconData(FileTreeViewer.ICON_FOLDER, FileTreeViewer.ICON_EXPANDEDFOLDER, nd);
        DefaultMutableTreeNode node = new 
                DefaultMutableTreeNode(idata);
        parent.add(node);

        if (nd.hasSubDirs())
            node.add(new DefaultMutableTreeNode( 
                    new Boolean(true) ));
    }

    return true;
}

public boolean hasSubDirs(){
    File[] files = listFiles();
    if (files == null)
        return false;
    for (int k=0; k<files.length; k++)
    {
        if (files[k].isDirectory())
            return true;
    }
    return false;
}

public int compareTo(FileNode toCompare){ 
    return  m_file.getName().compareToIgnoreCase(
            toCompare.m_file.getName() ); 
}

protected File[] listFiles(){
    if (!m_file.isDirectory())
        return null;
    try
    {
        return m_file.listFiles();
    }
    catch (Exception ex)
    {
        JOptionPane.showMessageDialog(null, "Error reading directory "+m_file.getAbsolutePath(),"Warning", JOptionPane.WARNING_MESSAGE);
        return null;
    }
}
}
*/