package org.dragonli.tools.general;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Set;

public class GroupUtil {

	public static void initGroup(String ipAndGroup,Set<Integer> groups)
	{
		try {
			String ip = InetAddress.getLocalHost().getHostAddress();
			groups.clear();
			String[] arr1 = ipAndGroup.split(";");
			for( String s1:arr1 )
			{
				String[] arr2 = s1.split(":");
//				if( !ip.equals(arr2[0]) )
//					continue;
				String[] arr3 = arr2[1].split(",");
				for( String s2:arr3 )
					groups.add(Integer.parseInt(s2));
			}
		} catch (UnknownHostException e) {}
	}
	
}
