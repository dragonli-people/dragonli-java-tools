/**
 * 
 */
package org.dragonli.tools.general;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author freeangel
 *
 */
public class GeneralUtil {

	public static String byteToString(byte[] digest) 
	{
		StringBuilder buf = new StringBuilder();
		
		for (int i = 0; i < digest.length; i++) 
		{
			byte c = digest[i];
			String tempStr = Integer.toHexString(c);
			if ( c <= 0x0f ) 
			{
				buf.append("0").append(tempStr);
			}
			else
			{
				buf.append(tempStr);
			}
		}
		return buf.toString().toLowerCase();
	}
	

	public static Object assertNull(Object o) throws Exception
	{
		return assertNull(o,null);
	}
	
	public static Object assertNull(Object o,String info) throws Exception
	{
		return assertNull(o, info,true);
	}
	
	public static Object assertNull(Object o,String info,boolean throwFlag) throws Exception
	{
		if( o == null && throwFlag )
			throw new Exception( ( info == null ? "object" : info ) + " cant be null!");
		
		return o;
	}
	
	public static String assertEmpty(String o) throws Exception
	{
		return assertEmpty(o,true);
	}

	public static String assertEmpty(String o,boolean trim) throws Exception
	{
		return assertEmpty(o, trim,null);
	}
	
	public static String assertEmpty(String o,boolean trim,String info) throws Exception
	{
		return assertEmpty(o,trim, info,true);
	}
	
	public static String assertEmpty(String o,boolean trim,String info,boolean throwFlag) throws Exception
	{
		if( ( o == null || ( o = ( trim ? o.trim() : o ) ).equals("") ) && throwFlag )
			throw new Exception( ( info == null ? "string" : info ) + " cant be empty!");
		
		return o;
	}
	
	public static String readFile(String url) throws Exception
	{
		File file = new File(url);  
		InputStream in = null;  
		try 
		{  
		//	System.out.println("以字节为单位读取文件内容，一次读一个字节：");  
			in = new FileInputStream(file);  
			byte[] tempbyte = new byte[in.available()];
			in.read(tempbyte);
//			while ((tempbyte = in.read(tempbyte)) != -1) 
//			{  
//			    System.out.write(tempbyte);  
//			}  
			in.close();  
			return new String(tempbyte,"UTF-8");
		}
		catch (IOException e)
		{
			e.printStackTrace();  
			return null;  
		}  
	}
	
	public static void writeFile(String url,String str) throws IOException
	{
		byte bt[] = new byte[1024];  
		bt = str.getBytes("UTF-8");  
		FileOutputStream in = new FileOutputStream(url);  
		in.write(bt, 0, bt.length);  
		in.close();  
	}
}
