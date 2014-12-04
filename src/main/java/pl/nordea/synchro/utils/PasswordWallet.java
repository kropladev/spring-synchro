package pl.nordea.synchro.utils;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

public  final class PasswordWallet {
	private  static final String ENCRYPTION_PWD = "bpmadmin";
	private static StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
	static {
		encryptor.setPassword(ENCRYPTION_PWD);
	}
	public static String encrypt(String value) {
		System.out.println("PasswordWallet encrypt:"+value);
		return encryptor.encrypt(value);
	}
	
	public static String decrypt(String value) {
		System.out.println("PasswordWallet decrypt:"+value);
		return encryptor.decrypt(value);
	}
	
}
