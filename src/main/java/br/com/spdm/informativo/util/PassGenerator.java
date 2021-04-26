package br.com.spdm.informativo.util;

import java.security.MessageDigest;

import org.apache.tomcat.util.codec.binary.Base64;

public class PassGenerator {

	public static void main(String[] args) {
		System.out.println(new PassGenerator().generate("admin"));
	}
	
	public String generate(String senha){
		try {
			byte[] digest = MessageDigest.getInstance("sha-256").digest(senha.getBytes());
			return Base64.encodeBase64String(digest);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
