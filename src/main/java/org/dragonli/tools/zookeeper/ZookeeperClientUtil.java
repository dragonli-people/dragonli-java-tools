package org.dragonli.tools.zookeeper;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;

import java.io.IOException;
import java.util.List;

public class ZookeeperClientUtil implements Watcher {  
	
	private volatile boolean connected = false;
	private volatile long lastConnectTime = 0;
	
    public long getLastConnectTime() {
		return lastConnectTime;
	}

	private volatile ZooKeeper zk;  
    //zookeeper地址  
    private String servers;  
    //链接超时时间  
    private int sessionTimeout = 40000000;  
      
    public ZooKeeper getAliveZk() {  
        ZooKeeper aliveZk = zk;  
        if (aliveZk != null && aliveZk.getState().isAlive()) {  
            return aliveZk;  
        } else {  
            zkReconnect();  
            return zk;  
        }  
    }  
    public synchronized void zkReconnect() {  
        close();  
        try {  
            connect();  
        } catch (IOException e) {  
        	e.printStackTrace();
        }  
    }  
    public synchronized void close() {  
        if (zk != null) {  
            try {  
                zk.close();  
            } catch (InterruptedException e) {  
            }  
            zk = null;  
        }  
    }  
    private synchronized void connect() throws IOException {  
    	
        if (zk == null && servers != null  && !servers.trim().equals("") )  
            zk = new ZooKeeper(servers, sessionTimeout, this);
    }  
    
    public String getTextData(String path) throws Exception
    {
    	byte[] data = null;
    	if(this.getAliveZk().exists(path, false)==null)
    		return null;
		data = this.getAliveZk().getData(path, false, null);
		return data == null ? null : new String(data,"UTF-8");
    }
    public boolean setTextData(String path,String s) throws Exception
    {
    	return setTextData(path,s,null,null);//if sure the path had existsed!
    }
    public boolean setTextData(String path,String s,List<ACL> ids,CreateMode mode) throws Exception
    {
    	byte [] data = s.getBytes("UTF-8");
    
    	if( zk != null && !connected )
    		return false;
    	if( this.getAliveZk().exists(path,false) != null )
    		this.getAliveZk().setData(path, data, -1);
    	else
    		this.getAliveZk().create(path, data, ids, mode);
    	return true;
    }
    public void createIfNotExist(String path,List<ACL> ids) throws Exception
    {
    	String p = "/";
    	String[] paths = path.split("/");
    	for(int i = 1 ; i<paths.length;i++)
    	{
    		p += paths[i];
    		if( this.getAliveZk().exists(p,false) == null )
        		this.getAliveZk().create(p, null, ids, CreateMode.PERSISTENT);
    		p += "/";
    	}
    }
    
    /*
    public String getData(String path) {  
        String result = null;  
        try {  
            byte [] data = getAliveZk().getData(path, Boolean.TRUE,null);  
            if(null != data){  
                result = new String(data, "UTF-8");  
            }  
        } catch (KeeperException e) {  
        } catch (InterruptedException e) {  
        } catch (UnsupportedEncodingException e) {  
        }  
        return result ;  
    }  
    public List<String> getChildren(){  
        List<String> data = null;  
        try {  
            data = getAliveZk().getChildren(mainPath, Boolean.TRUE);  
        } catch (KeeperException e) {  
        } catch (InterruptedException e) {  
        }  
        return data;  
    }  
    */
    public void setSessionTimeout(int sessionTimeout) {  
        this.sessionTimeout = sessionTimeout;  
    }  
 
    public void setServers(String servers) {  
        this.servers = servers;  
    }
    
	@Override
	public void process(WatchedEvent event) {
		// TODO Auto-generated method stub
		System.out.println("event.getState():"+event.getState());
		if( event.getState() == KeeperState.Disconnected )
			connected = false;
		else if( event.getState() == KeeperState.SyncConnected )
		{
			connected = true;
			lastConnectTime = System.currentTimeMillis();			
		}
		else if( event.getState() == KeeperState.Expired )
		{
			this.zk = null;
			try {
				this.connect();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
//		System.out.println("event.getType():"+event.getType());
//		System.out.println("event.getState() == KeeperState.Disconnected : " + ( event.getState() == KeeperState.Disconnected ) );
//		System.out.println("event.getState() == KeeperState.SyncConnected : " + ( event.getState() == KeeperState.SyncConnected ) );
	}  
}  
