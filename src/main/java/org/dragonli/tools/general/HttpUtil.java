package org.dragonli.tools.general;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URLDecoder;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.util.CollectionUtils;


/**
 * Created by penuel on 14-7-15.
 */
public class HttpUtil {

	private static Logger logger = Logger.getLogger(HttpUtil.class);


	public static String[] getUriParts(String uri) {
		String[] uriParts = null;
		try {
			if (StringUtils.isEmpty(uri)) {
				return new String[] {};
			} else if (uri.indexOf("?") > 0) {
				uriParts = uri.replaceAll("/", "").split("[?]");
			} else {
				uriParts = new String[] { uri.replaceAll("/", "").trim() };
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return new String[] {};
		}
		return uriParts;
	}


	@SuppressWarnings("unused")
	private static String truncateUrlPage(String strURL) {
		String strAllParam = null;
		String[] arrSplit = null;
		strURL = strURL.trim().toLowerCase();
		if (strURL.length() > 1) {
			arrSplit = strURL.split("[?]");
			if (arrSplit.length > 1) {
				if (StringUtils.isNotEmpty(arrSplit[1])) {
					strAllParam = arrSplit[1];
				}
			}
		}
		return strAllParam;
	}

	public static Map<String, Object> getUrlParams(String url) throws UnsupportedEncodingException 
	{
		int index = url.indexOf("?");
		if( index < 0 || index == url.length() -1 )
			return new HashMap<String,Object>();
		url = url.substring(index+1);
		
		return getParams(url);
	}
	

	public static Map<String, Object> getParams(String url) throws UnsupportedEncodingException {
		Map<String, Object> mapRequest = new HashMap<String, Object>();
		String[] arrSplit = null;
		if (url == null) {
			return Collections.emptyMap();
		}
		// 每个键值为一组
		arrSplit = url.split("[&]");
		String[] arrSplitEqual = null;
		for (String strSplit : arrSplit) {
			arrSplitEqual = strSplit.split("[=]");
			// 解析出键值
			if (arrSplitEqual.length > 1) {
				// 正确解析
				mapRequest.put(URLDecoder.decode(arrSplitEqual[0],"UTF-8"),URLDecoder.decode( arrSplitEqual[1],"UTF-8"));

			}
		}
		return mapRequest;
	}


    public static List<String> getLocalAddressList() {
        List<String> addrs = new ArrayList<String>(3);
        Enumeration<NetworkInterface> ns = null;
        try {
            ns = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {
            // ignored...
        }
        while (ns != null && ns.hasMoreElements()) {
            NetworkInterface n = ns.nextElement();
            Enumeration<InetAddress> is = n.getInetAddresses();
            while (is.hasMoreElements()) {
                InetAddress i = is.nextElement();
                if (!i.isLoopbackAddress() && !i.isLinkLocalAddress() && !i.isMulticastAddress()
                    && !isSpecialIp(i.getHostAddress())){
                	addrs.add(i.getHostAddress());
                } 
            }
        }
        return addrs;
    }
    

    public static String getLocalAddress() {
    	List<String> addressList = getLocalAddressList();
    	if(!CollectionUtils.isEmpty(addressList)){
    		return StringUtils.isEmpty(addressList.get(0)) ? StringUtils.EMPTY : addressList.get(0).trim();
    	}
    	return StringUtils.EMPTY;
    }

    private static boolean isSpecialIp(String ip) {
        if (ip.contains(":")) return true;
        if (ip.startsWith("127.")) return true;
        if (ip.startsWith("169.254.")) return true;
        if (ip.equals("255.255.255.255")) return true;
        return false;
    }
	
	
	public static void main(String[] args) throws UnknownHostException, SocketException {
		
		System.out.println(getLocalAddressList());
		System.out.println(getLocalAddress());
	}

}
