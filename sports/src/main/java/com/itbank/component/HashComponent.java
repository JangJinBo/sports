package com.itbank.component;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class HashComponent {

	Random ran = new Random();

	public String getRandomSalt() {
		String salt = "";
		String sample = "abcdefghijklmnopqrstuvwxyz0123456789!@#$%^&ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		for (int i = 0; i < 8; i++) {
			salt += sample.charAt(ran.nextInt(sample.length()));
		}
		return salt;
	}

	public String getHash(String source, String salt) {
		String hash = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			md.update(source.getBytes());
			md.update(salt.getBytes());
			hash = String.format("%0128X", new BigInteger(1, md.digest()));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return hash;
	}

}
