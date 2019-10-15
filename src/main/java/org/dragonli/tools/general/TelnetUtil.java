package org.dragonli.tools.general;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

import org.apache.log4j.Logger;

public class TelnetUtil {
	
//	public static void main(String[] args) throws Exception
//	{
//		TelnetUtil.startTelnet(new HashMap<String, ITelnetCommandHandler>(), 7070);
//	}
	
	public static Logger logger = Logger.getLogger(TelnetUtil.class);

	public static void startTelnet(Map<String,ITelnetCommandHandler> serviceTelnetCommandDic,int telnetPort)
	{
		new Thread(new Runnable() {
			
			@SuppressWarnings("resource")
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Thread.currentThread().setName("TelnetUtil-server-thread");
				ServerSocket server = null;
				try {
					server = new ServerSocket(telnetPort);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					logger.error(e1);
					return;
				} 
				int id = 0;
				while(true)
				{
					Socket client;
					try {
						client = server.accept();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						logger.error(e1);
						continue;
					}
					id++;
					final int iid = id;
					logger.info("TelnetUtil-client-tick|"+System.currentTimeMillis());
					new Thread(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							Thread.currentThread().setName("TelnetUtil-client-thread-"+iid);
							BufferedReader in;
							try {
								in = new BufferedReader(new InputStreamReader(client.getInputStream()));
							
								PrintStream out = new PrintStream(client.getOutputStream()); //向客户端响应数据
								boolean f = true;
								String tmp = null;
								while(!client.isClosed() && f)
								{
									try
									{
										tmp = in.readLine();
									}catch(Exception ee){
										break;
									}
									logger.info("TelnetUtil-cmd-tick|"+System.currentTimeMillis()+"|"+tmp);

									if(tmp == null)
										break;
									if(client.isClosed())
										break;
									if( "".equals( tmp = tmp.trim() ))
										continue;
									if("quit".equals(tmp))
										break;
									
									tmp = tmp.replaceAll("\\s+", " ");
									String[] arr1 = tmp.split(" ");
									String[] arr2 = new String[ arr1.length - 1 ];
									if( arr1.length > 1 )
										System.arraycopy(arr1, 1, arr2, 0, arr1.length-1);
									ITelnetCommandHandler command = serviceTelnetCommandDic.get(arr1[0]);
									if( command == null )
									{
										out.println("err command: " + tmp);
										continue;
									}
									
									String code;
									try {
										code = command.command(arr2);
										out.println("done command["+tmp+"]:" + code);
									} catch (Exception e) {
										// TODO Auto-generated catch block
										out.println("excption command["+tmp+"]:" + e.getMessage());
//										e.printStackTrace();
									}
									
								}
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}  //获取客户端输入的数据
							try {
								client.close();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							logger.info("TelnetUtil-cmd-close|"+System.currentTimeMillis());
						}
					}).start();
				}
			}
		}).start();
		
	}
}
