package com.feng.surveypark.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.security.MessageDigest;
import java.util.Collection;

import com.feng.surveypark.domain.BaseEntity;


/**
 * 数据处理工具类
 *
 */
public class DataUtil {

	/**
	 * MD5加密
	 * @param src 需要加密的字符串
	 * @return	已经加密的字符串
	 */
	public static String md5 (String src){
		try {
			StringBuffer buffer = new StringBuffer();
			char[] chars = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
			
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] data = md.digest(src.getBytes()); 
			for (byte b : data) {
				//高四位
				buffer.append(chars[b>>4& 0x0f]);			
				//低四位
				buffer.append(chars[b & 0x0f]);
			}
			return buffer.toString();
		} catch (Exception e) {
			e.printStackTrace();
		} 	
		return null;
	}
	
	/**
	 * 深度复制 使用的是串行化技术,(接收传输使用的流技术加字节数组)
	 * @param src 目标对象
	 * @return 复制对象
	 */
	public static Serializable deeplyCopy(Serializable src) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(src);
			oos.close();
			baos.close();
			byte[] data = baos.toByteArray();
			ByteArrayInputStream bais = new ByteArrayInputStream(data);
			ObjectInputStream ois = new ObjectInputStream(bais);
			Serializable copy = (Serializable) ois.readObject();
			ois.close();
			bais.close();
			return copy;
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	/**
	 * 将集合 的 每项id取出,形成字符串
	 */
	public static String extractEntityIds(Collection<? extends BaseEntity> c) {
		if (!ValidateUtil.isValid(c)) {		
			return null;
		}else {
			String temp = "";
			for (BaseEntity entity : c) {
				temp = temp+entity.getId()+",";
			}
			return temp.substring(0, temp.length()-1);
		}
	}
}
