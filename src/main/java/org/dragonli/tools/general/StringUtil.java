/**
 * 
 */
package org.dragonli.tools.general;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author freeangel
 *
 */
public class StringUtil 
{
	private static final byte[] fixStrArr = new byte[62];
	public static byte[] getFixstrarr() {
		return fixStrArr;
	}

	static{
		int k = 0;
		int[] paras = new int[]{48,10,65,26,97,26};
		for(int i = 0 ; i<paras.length;i+=2)
		{
			for(int j=0;j<paras[i+1];j++)
				fixStrArr[k++] = (byte)(paras[i]+j); //new String(new byte[]{(byte)(paras[i]+j)});
		}
	}
	
	public static String strFromFixArr(int num)
	{
		final int a = fixStrArr.length;
		Queue<Integer> queue = new LinkedList<>();
		while(num!=0)
		{
			queue.add(num%a);
			num /= a;
		}
		byte[] arr = new byte[queue.size()];
		for(int i = arr.length ; i > 0 ; i-- )
			arr[i-1] = fixStrArr[queue.poll().byteValue()];
		return new String(arr);
	}
	
	public static String substitute(String source,Object[] args)
	{
		Object[] args2 = args;
		if( args == null && args2.length == 0 )
			return source;
		for( int i = 0 ; i < args.length ; i++ )
		{
			source = source.replace( "{"+i+"}" , args[i].toString());
		}
		return source;
	}
	
	//左右补齐
	public static String strPad(String str,char repeatChar,int len,boolean left)
	{
		String tempResult = String.format("%" + len + "s","").replace(' ', repeatChar);  
		str = left ? tempResult + str : str + tempResult;
		return left ? str.substring(str.length()-len) : str.substring(0, len);  
	}
	
	//取第一个和最后一个
	public static String mbSubstr(String s,char repeatChar,int len)
	{
		return s.substring(0,1) + String.format("%" + len + "s","").replace(' ', repeatChar) + s.substring(s.length()-1);
	}
}
