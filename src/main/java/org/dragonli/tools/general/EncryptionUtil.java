package org.dragonli.tools.general;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.security.MessageDigest;

public class EncryptionUtil {


	protected static String digestStr(String source,String encode,String type) throws Exception{
		MessageDigest digest = MessageDigest.getInstance(type);
		digest.update(source.getBytes(encode));
		byte[] messageDigest = digest.digest();
		return byteArrayToHexStr(messageDigest);
	}


	public static String sha1(String str) throws Exception
	{
		return sha1(str,null);
	}


	public static String sha1(String str,Integer len) throws Exception{
		String result = digestStr(str,"UTF-8","SHA");
		return toShortCryptoCode(result,len);
	}


	public static String md5(String str) throws Exception{
		return md5(str,null);
	}


	public static String md5(String str,Integer len) throws Exception{
		String result = digestStr(str,"UTF-8","MD5");
		return toShortCryptoCode(result,len);
	}



	public static String byteArrayToHexStr(byte[] arr)
	{
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < arr.length; i++) {
			String shaHex = Integer.toHexString(arr[i] & 0xFF);
			if (shaHex.length() < 2) {
				hexString.append(0);
			}
			hexString.append(shaHex);
		}
		return hexString.toString().toUpperCase();
	}


	public static String toShortCryptoCode(String sourceCode,Integer shortCodeLength){
		if(shortCodeLength == null || shortCodeLength >= sourceCode.length() )return sourceCode;
		if(shortCodeLength <= 0 )return "";
		int bitLen = shortCodeLength<<2;
		BigInteger tailBase = BigInteger.valueOf( ~((-1L)<<(bitLen)) );//2^(len*4)-1
		BigInteger bi = new BigInteger(sourceCode,16);
		long resultNum = 0;//xor 0 还是自己
		while ( bi.compareTo(BigInteger.ZERO) != 0 ){
			resultNum = bi.and(tailBase).longValue() ^ resultNum;
			bi = bi.shiftRight(bitLen);
		}
		return String.format("%0"+shortCodeLength+"x",resultNum);
	}


	public static String subtraction(String str1, String str2,int scale) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("减法 : "+str1+" : "+str2+" : "+scale);
		BigDecimal amount1 = new BigDecimal(str1);
		BigDecimal amount2 = new BigDecimal(str2);
		BigDecimal amount = amount1.subtract(amount2).setScale(scale,BigDecimal.ROUND_DOWN);
		return toFix(amount.toPlainString());
	}


	public static String multiplication(String str1, String str2,int scale) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("乘法 : "+str1+" : "+str2+" : "+scale);
		BigDecimal amount1 = new BigDecimal(str1);
		BigDecimal amount2 = new BigDecimal(str2);
		BigDecimal amount = amount1.multiply(amount2).setScale(scale,BigDecimal.ROUND_DOWN);
		return toFix(amount.toPlainString());
	}


	public static String division(String str1, String str2,int scale) throws Exception {
		System.out.println("除法 : "+str1+" : "+str2+" : "+scale);
		BigDecimal amount1 = new BigDecimal(str1);
		BigDecimal amount2 = new BigDecimal(str2);
		BigDecimal amount = amount1.divide(amount2,scale, RoundingMode.DOWN);
		return toFix(amount.toPlainString());
	}


	public static String addition(String str1, String str2,int scale) throws Exception {
		System.out.println("加法 : "+str1+" : "+str2+" : "+scale);
		BigDecimal amount1 = new BigDecimal(str1);
		BigDecimal amount2 = new BigDecimal(str2);
		BigDecimal amount = amount1.add(amount2).setScale(scale,BigDecimal.ROUND_DOWN);
		return toFix(amount.toPlainString());
	}

	public static String toFix(String str){
		int index = 0;
		str = ( index = str.indexOf(".") ) < 0 ? str :
				str.substring(0,index+2)+str.substring(index+2).replaceAll("0+$","");
		return str;
	}

}
