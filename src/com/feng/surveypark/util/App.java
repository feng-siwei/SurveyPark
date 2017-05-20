package com.feng.surveypark.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class App {
	/**
	 * @param args
	 * @throws NoSuchAlgorithmException
	 */
	public static void main(String[] args) throws NoSuchAlgorithmException {
		String str  = "123" ;
		
		StringBuffer buffer = new StringBuffer();
		char[] chars = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
		
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] data = md.digest(str.getBytes()); 
		System.out.println(data[0]);
		for (byte b : data) {
			//高四位
			buffer.append(chars[b>>4& 0x0f]);			
			//低四位
			buffer.append(chars[b & 0x0f]);
		}
		System.out.println(buffer.toString());
		
	}
}
