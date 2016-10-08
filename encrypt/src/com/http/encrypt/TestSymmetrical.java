/**
 * 
 */
package com.http.encrypt;

import java.io.IOException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * @Description: �ԳƼ��ܵ�ʹ�� 
 **/
public class TestSymmetrical {

	/*******DES�����㷨*********/

	/**
	 * ÿ�����ɵ�key������ͬ,������ɵ�key��Ҫ��������,�����´��޷�����
	 * @return
	 * @throws Exception
	 */
	public static String genKeyDES() throws Exception{
		KeyGenerator keyGen = KeyGenerator.getInstance("DES");
		keyGen.init(56);
        SecretKey key = keyGen.generateKey(); 
        String base64Str = byte2base64(key.getEncoded());
        return base64Str;
	}
	
	public static SecretKey loadKeyDES(String base64Key) throws Exception{
		byte[] bytes = base642byte(base64Key);
		SecretKey key = new SecretKeySpec(bytes, "DES");
		return key;
	}
	
	public static byte[] encryptDES(byte[] source,SecretKey key)throws Exception{
		Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] bytes = cipher.doFinal(source);
        return bytes;
	}
	
	public static byte[] decryptDES(byte[] source,SecretKey key) throws Exception{
		Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] bytes = cipher.doFinal(source);
        return bytes;
	}
	
	/****AES�㷨����****/
	//Ĭ��Ϊ128,AES�㷨Ҳ֧�� 192 �� 256,����jdk�����������ڼ���������ڵĿ���,���ʹ��256λ����Կ,����Ҫ���������ߺ�˾�����Ƶ��ļ�,����취���Կ��Բ���http://stackoverflow.com/questions/6481627/java-security-illegal-key-size-or-default-parameters
	public static String genKeyAES() throws Exception{
		KeyGenerator keyGen = KeyGenerator.getInstance("AES");
		keyGen.init(128);
        SecretKey key = keyGen.generateKey(); 
        String base64Str = byte2base64(key.getEncoded());
        return base64Str;
	}
	
	public static SecretKey loadKeyAES(String base64Key) throws Exception{
		byte[] bytes = base642byte(base64Key);
		SecretKey key = new SecretKeySpec(bytes, "AES");
		return key;
	}
	
	public static byte[] encryptAES(byte[] source,SecretKey key)throws Exception{
		Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] bytes = cipher.doFinal(source);
        return bytes;
	}
	
	public static byte[] decryptAES(byte[] source,SecretKey key) throws Exception{
		Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] bytes = cipher.doFinal(source);
        return bytes;
	}
	
	
	private static String byte2base64(byte[] bytes){
		BASE64Encoder base64Encoder = new BASE64Encoder();
		return base64Encoder.encode(bytes);
	}
	private static byte[] base642byte(String base64) throws IOException{
		BASE64Decoder base64Decoder = new BASE64Decoder();
		return base64Decoder.decodeBuffer(base64);
	}
	
	public static void main(String[] args) throws Exception {
		String a = "hello,i am chenkangxian,good night!";
		//DES����
		String desKeyStr = genKeyDES();
		System.out.println(desKeyStr);
		SecretKey desKey = loadKeyDES(desKeyStr);
		byte[]desEnBytes = encryptDES(a.getBytes(),desKey);
		System.out.println(byte2base64(desEnBytes));
		byte[] desDeBytes = decryptDES(desEnBytes,desKey);
		System.out.println(new String(desDeBytes));
		
		System.out.println("======================================");
		
		//AES����
		String aesKeyStr = genKeyAES();
		System.out.println(aesKeyStr);
		SecretKey aesKey = loadKeyAES(aesKeyStr);
		byte[] aesEnBytes = encryptAES(a.getBytes(),aesKey);
		System.out.println(byte2base64(aesEnBytes));
		byte[] aesDeBytes = decryptAES(aesEnBytes,aesKey);
		System.out.println(new String(aesDeBytes));
	}

}
