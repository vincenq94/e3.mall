package cn.itheima.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;

public class SimpleHashUtils {

	private static final String DEFAULT_ALGORITHM_NAME = "SHA-512";
	private static final int DEFAULT_HASH_ITERATIONS = 2;

	public static SimpleHashBean encryptPassword(String userName, String password) {
		return encryptPassword(DEFAULT_ALGORITHM_NAME, DEFAULT_HASH_ITERATIONS, userName, password, null);
	}

	public static SimpleHashBean encryptPassword(String userName, String password, String salt) {
		return encryptPassword(DEFAULT_ALGORITHM_NAME, DEFAULT_HASH_ITERATIONS, userName, password, salt);
	}

	public static SimpleHashBean encryptPassword(int hashIterations, String userName, String password) {
		return encryptPassword(DEFAULT_ALGORITHM_NAME, hashIterations, userName, password, null);
	}

	public static SimpleHashBean encryptPassword(String algorithmName, int hashIterations, String userName,
			String password, String salt) {
		if (StringUtils.isBlank(algorithmName)) {
			algorithmName = DEFAULT_ALGORITHM_NAME;
		}
		if (hashIterations < 0) {
			hashIterations = DEFAULT_HASH_ITERATIONS;
		}
		if (StringUtils.isBlank(userName)) {
			userName = "";
		}
		if (StringUtils.isBlank(password)) {
			password = "";
		}
		String s = StringUtils.isBlank(salt) ? new SecureRandomNumberGenerator().nextBytes().toHex() : salt;
		SimpleHash hash = new SimpleHash(algorithmName, password, userName + s, hashIterations);
		String encodedPassword = hash.toHex();
		return new SimpleHashBean(encodedPassword, s);
	}

	public static void main(String[] args) {
		SimpleHashBean bean = encryptPassword("admin", "1", "994926b473c20bb80e854905dab7af76");
		System.out.println("PASSWORD=" + bean.getPassword());
		System.out.println("SALT=" + bean.getSalt());
	}
}
