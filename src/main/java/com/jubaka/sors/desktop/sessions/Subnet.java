package com.jubaka.sors.desktop.sessions;

import com.jubaka.sors.desktop.factories.ClassFactory;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.Inet4Address;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Observable;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.locks.ReentrantLock;

public class Subnet extends Observable implements Serializable, CustomObserver  {
	
	Set<IPaddr> ips =  Collections.synchronizedSet( new HashSet<IPaddr>());	// all captured ips				
	Set<IPaddr> liveIps =  Collections.synchronizedSet( new HashSet<IPaddr>());	// ips that are online
	protected  Map<IPaddr , HashSet<Session>> srcIPMap=  Collections.synchronizedMap( new HashMap<IPaddr, HashSet<Session>>());
	protected Map<IPaddr, HashSet<Session>> dstIPMap=  Collections.synchronizedMap(new HashMap<IPaddr, HashSet<Session>>());
	protected Map<Integer, HashSet<Session>> srcPMap=  Collections.synchronizedMap(new HashMap<Integer, HashSet<Session>>());
	protected Map<Integer, HashSet<Session>> dstPMap=  Collections.synchronizedMap(new HashMap<Integer, HashSet<Session>>());
	
	
	protected  Map<IPaddr, HashSet<Session>> srcIPMapStore=   Collections.synchronizedMap( new HashMap<IPaddr, HashSet<Session>>());
	protected Map<IPaddr, HashSet<Session>> dstIPMapStore=  Collections.synchronizedMap( new HashMap<IPaddr, HashSet<Session>>());
	protected Map<Integer, HashSet<Session>> srcPMapStore=  Collections.synchronizedMap( new HashMap<Integer, HashSet<Session>>());
	protected Map<Integer, HashSet<Session>> dstPMapStore=  Collections.synchronizedMap( new HashMap<Integer, HashSet<Session>>());
	protected Map<Long, Session> timeSesMap =  Collections.synchronizedMap( new HashMap<Long, Session>());
	//private ReentrantLock mutex = new ReentrantLock();
	
//	Timer t = new Timer();
//	TimerTask task = null;
	protected HashSet<Session> treeItem;
	protected Long dataSend = (long) 0;
	protected Long dataReceive = (long) 0;
    private static final int IP_MASK = 0x80000000;
    private static final int BYTE_MASK = 0xFF;
    private InetAddress subnet;
	private int subnetInt;
	private int subnetMask;
    private int suffix;
    public boolean selected= false;
    private Branch br;
    private ClassFactory factory = null;

    /**
     * Creates a subnet from CIDR notation. For example, the subnet
     * 192.168.0.0/24 would be created using the {@link InetAddress}  
     * 192.168.0.0 and the mask 24.
     * @param subnet The {@link InetAddress} of the subnet
     * @param mask The mask
     */
    
    public Subnet(Branch br, InetAddress subnet, int mask)  {
    	this.br=br;
    	factory = br.getFactory();
    	
        if (subnet == null) {
            throw new IllegalArgumentException("Subnet address can not be null");
        }
        if (!(subnet instanceof Inet4Address)) {
            throw new IllegalArgumentException("Only IPv4 supported");
        }

        if (mask < 0 || mask > 32) {
            throw new IllegalArgumentException("Mask has to be an integer between 0 and 32");
        }
  //      System.out.println(mask+" mask when creating subnetItem");
        this.subnet = subnet;
        this.subnetInt = toInt(subnet);
        this.suffix = mask;
      

        // binary mask for this subnet
        this.subnetMask = IP_MASK >> (mask - 1);
		ObserverTimer.getinstance().addObserver(this);
       
    }
    
    public Long getInputData(Long onTime) {
    	Long dataCount = (long) 0;
    	for (IPaddr ip : ips) {
    		dataCount += ip.getDataDown(onTime);
    	}
    	return dataCount;
    }
    
    public Long getOutputData(Long onTime) {
    	Long dataCount = (long) 0;
    	for (IPaddr ip : ips) {
    		dataCount += ip.getDataUp(onTime);
    	}
    	return dataCount;
    }
    
    public Long getInputData() {
    	return dataReceive;
    }
    
    public Long getOutptData() {
    	return dataSend;
    }
    
    public void  incrementInputData(Long b) {  //maybe not very good idea
    	dataReceive=dataReceive+b;
   // 	System.out.println("dataReceive = "+dataReceive);
    	setChanged();
		
    	
    }		// trouble
    
    public void incrementOutputData(Long b) {  //maybe not very good idea
    	dataSend=dataSend+b;
   // 	System.out.println("dataSend = "+ dataSend);
    	setChanged();
		
    	
    }

    
    public int getSubnetMask() {
  		return suffix;
  	}
    
    public int getSuffix() {
  		return suffix;
  	}

  	protected void setSubnetMask(int subnetMask) {
  		this.subnetMask = subnetMask;
  	}

  	public void addIp(IPaddr ip) {
  		
  		ips.add(ip);
  		liveIps.add(ip);
  		if (isSelected())
  				factory.getController(br.getId()).addIP(ip.getAddr().getHostAddress());
  	
  	}
  	
  	public void addIPmanualy(IPaddr ip) {
  		ips.add(ip);
  		dataReceive = dataReceive+ip.getDataDown();
  		dataSend = dataSend+ip.getDataUp();
  	}
  	
  	public void removeIP(IPaddr ip) {
  		//		System.out.println(ip.getAddr());		
  		liveIps.remove(ip);
  		if (isSelected())
  			factory.getController(br.getId()).removeIP(ip.getAddr().getHostAddress());
  	
  							
  	}
  	
  	public void removeSession(Session ses) {
  		HashSet<Session> item;
  		item = srcIPMap.get(ses.getSrcIP());
  		if (item != null) item.remove(ses);
  		item = srcIPMapStore.get(ses.getSrcIP());
  		if (item != null) item.remove(ses);
  		item = srcPMap.get(ses.getSrcP());
  		if (item != null) item.remove(ses);
  		item = srcPMapStore.get(ses.getSrcP());
  		if (item != null) item.remove(ses);
  		
  		item =dstIPMap.get(ses.getDstIP());
  		if (item != null) item.remove(ses);
  		item = dstIPMapStore.get(ses.getDstIP());
  		if (item != null) item.remove(ses);
  		item = dstPMap.get(ses.getDstP());
  		if (item != null) item.remove(ses);
  		item = dstPMapStore.get(ses.getDstP());
  		if (item != null) item.remove(ses);
  	}
  	
  	
  	
  	public HashSet<Session> getOutputStoredSes(IPaddr ip) {
  		HashSet<Session> resultSet = srcIPMapStore.get(ip);
  		if (resultSet == null) resultSet = new HashSet<Session>();
  		return (HashSet<Session>) resultSet.clone();
  		
  	}
  	
  	public HashSet<Session> getOutputActiveSes(IPaddr ip) {
  		HashSet<Session> resultSet = srcIPMap.get(ip);
  		if (resultSet == null) resultSet = new HashSet<Session>();
  		return (HashSet<Session>) resultSet.clone();
  		
  	}
  	
  	public HashSet<Session> getInputActiveSes(IPaddr ip) {
  		HashSet<Session> resultSet = dstIPMap.get(ip);
  		if (resultSet == null) resultSet = new HashSet<Session>();
  		return (HashSet<Session>) resultSet.clone();
  		
  	}
  	public HashSet<Session> getInputStoredSes(IPaddr ip) {
  		HashSet<Session> resultSet = dstIPMapStore.get(ip);
  		if (resultSet == null) resultSet = new HashSet<Session>();
  		return (HashSet<Session>) resultSet.clone();
  		
  	}
  	
  	public Integer getInputActiveSesCount() {
  		Integer count = 0;
  		for (IPaddr addr : liveIps) {
  			HashSet<Session> set = getInputActiveSes(addr);
  			if (set!=null)
  				count += set.size();
  		}
  		return count;
  	}
  	
  	public Integer getInputSesCount() {
  		Integer count = getInputActiveSesCount();
  		
  		for (IPaddr addr : ips) {
  			HashSet<Session> set = getInputStoredSes(addr);
  			if (set!=null)
  				count += set.size();
  		}
  		return count;
  		
  	}
  	
  	public Integer getOutputActiveSesCount() {
  		Integer count = 0;
  		for (IPaddr addr : liveIps) {
  			HashSet<Session> set = getOutputActiveSes(addr);
  			if (set!=null)
  				count+=set.size();
  		}
  		return count;
  	}
  	
  	public Integer getOutputSesCount() {
  		Integer count = getOutputActiveSesCount();
  		for (IPaddr addr : ips) {
  			HashSet<Session> set = getOutputStoredSes(addr);
  			if (set!=null)
  				count += set.size();
  		}
  		return count;
  	}
  	
  	
  	public Integer getLiveSesCount() {
  		Integer res=0;
  		Set<IPaddr> keys= srcIPMap.keySet();
  		for (IPaddr  item : keys) {
  			res=res+srcIPMap.get(item).size();
  			
  		}
  		return res;
  		
  	}
  	
  	public Integer getSesCount() {
  		Integer res=0;
  		Set<IPaddr> keys= srcIPMap.keySet();
  		for (IPaddr  item : keys) {
  			res=res+srcIPMap.get(item).size();
  			
  		}
  		keys= srcIPMapStore.keySet();
  		for (IPaddr  item : keys) {
  			res=res+srcIPMapStore.get(item).size();
  			
  		}
  		
  		return res;
  		
  	}
  	
  	
  	
  	public void onCloseConnection(Session s) {
  	
		srcIPMap.get(s.getSrcIP()).remove(s);
		dstIPMap.get(s.getDstIP()).remove(s);
		srcPMap.get(s.getSrcP()).remove(s);
		dstPMap.get(s.getDstP()).remove(s); 
		storing(s);
	//	System.out.println(srcIPMap.get(s.getSrcIP()).size());
		if (srcIPMap.get(s.getSrcIP()).size()==0) {
		
			removeIP(s.getSrcIP());
		}
		if (dstIPMap.get(s.getDstIP()).size()==0) {
			
			removeIP(s.getDstIP());
		}
		setChanged();
		
		
		
	}
  	
  	public void storing(Session s) {
  		
	
		treeItem = srcIPMapStore.get(s.getSrcIP());
		 if (treeItem==null) {
			 HashSet<Session> container = new HashSet<Session>();
			 srcIPMapStore.put(s.getSrcIP(), container);
			 treeItem=container;
		 }
		 treeItem.add(s);
		 
		 treeItem = dstIPMapStore.get(s.getDstIP());
		 if (treeItem==null) {
			 HashSet<Session> container = new HashSet<Session>();
			 dstIPMapStore.put(s.getDstIP(), container);
			 treeItem=container;
		 }
		 treeItem.add(s);
		 
		 treeItem = srcPMapStore.get(s.getSrcP());
		 if (treeItem==null) {
			 HashSet<Session> container = new HashSet<Session>();
			 srcPMapStore.put(s.getSrcP(), container);
			 treeItem=container;
		 }
		 treeItem.add(s);
		 
		 treeItem = dstPMapStore.get(s.getDstP());
		 if (treeItem==null) {
			 HashSet<Session> container = new HashSet<Session>();
			 dstPMapStore.put(s.getDstP(), container);
			 treeItem=container;
		 }
		 treeItem.add(s);
	}
  	
  	
  	
  	protected void save(Session s) {
  		
		treeItem = srcIPMap.get(s.getSrcIP());
		 if (treeItem==null) {
			 HashSet<Session> container = new HashSet<Session>();
			 srcIPMap.put(s.getSrcIP(), container);
			 treeItem=container;
		 }
		 treeItem.add(s);
		 
		 treeItem = dstIPMap.get(s.getDstIP());
		 if (treeItem==null) {
			 HashSet<Session> container = new HashSet<Session>();
			 dstIPMap.put(s.getDstIP(), container);
			 treeItem=container;
		 }
		 treeItem.add(s);
		 
		 treeItem = srcPMap.get(s.getSrcP());
		 if (treeItem==null) {
			 HashSet<Session> container = new HashSet<Session>();
			 srcPMap.put(s.getSrcP(), container);
			 treeItem=container;
		 }
		 treeItem.add(s);
		 
		 treeItem = dstPMap.get(s.getDstP());
		 if (treeItem==null) {
			 HashSet<Session> container = new HashSet<Session>();
			 dstPMap.put(s.getDstP(), container);
			 treeItem=container;
		 }
		 treeItem.add(s);
		 timeSesMap.put(s.getEstablished().getTime(), s);
		 setChanged();

	}
  	public Session getSessionById(Long tm) {
  		return (Session) timeSesMap.get(tm);
  		
  	}
  	
  	public void deleteIP(IPaddr item) {
  		srcIPMap.remove(item);
  		srcIPMapStore.remove(item);
  		dstIPMap.remove(item);
  		dstIPMapStore.remove(item);
  		removeIP(item);
  		ips.remove(item);
  		dataReceive = dataReceive - item.getDataDown();
  		dataSend = dataSend - item.getDataUp();
  		
  	}
  	public void clearAll() {
  		for (IPaddr ip : ips) {
  			ip.clearAll();
  		}
  		liveIps.clear();
  		ips.clear();
  		srcIPMap.clear();
  		dstIPMap.clear();
  		srcPMap.clear();
  		dstPMap.clear();
  		srcIPMapStore.clear();
  		srcPMapStore.clear();
  		dstPMapStore.clear();
  		dstIPMapStore.clear();
  	}
  	
  	public Set<IPaddr> getIps() {
  		return new HashSet<IPaddr>(ips);
  	}
  	
  	public Set<IPaddr> getLiveIps() {
  			return  new HashSet<IPaddr>(liveIps);
  	}
  	
  	public InetAddress getSubnet() {
		return subnet;
	}

    
    /** 
     * Converts an IP address into an integer
     */
    private int toInt(InetAddress inetAddress) {
        byte[] address = inetAddress.getAddress();
        int result = 0;
        for (int i = 0; i < address.length; i++) {
            result <<= 8;
            result |= address[i] & BYTE_MASK;
        }
        return result;
    }

    
    /**
     * Converts an IP address to a subnet using the provided 
     * mask
     * @param address The address to convert into a subnet
     * @return The subnet as an integer
     */
    private int toSubnet(InetAddress address) {
        return toInt(address) & subnetMask;
    }

    /**
     * Checks if the {@link InetAddress} is within this subnet
     * @param address The {@link InetAddress} to check
     * @return True if the address is within this subnet, false otherwise
     */
    public boolean inSubnet(InetAddress address) {
    	
        return toSubnet(address) == subnetInt;
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString() {
        return subnet.getHostAddress() + "/" + suffix;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Subnet)) {
            return false;
        }

        Subnet other = (Subnet) obj;

        return other.subnetInt == subnetInt && other.suffix == suffix;
    }

    public synchronized void setSelected(boolean value) {
    /*
    	if (value) {
    		task = new TimerTask() {
				
				@Override
				public void run() {
					if (mutex.isLocked()) return;
					mutex.lock();
					notifyObservers();
					mutex.unlock();
				}
			};
			t.schedule(task, 0, 200);
			
    	} else {
    		if (task!=null) task.cancel();
    	}
    	*/
    	selected=value;
    }
    
    public synchronized boolean isSelected() {
    	return selected;
    }


	public synchronized Integer getId() {
		return br.getId();
	}

	@Override
	public void customUpdate() {
		notifyObservers();
	}
}