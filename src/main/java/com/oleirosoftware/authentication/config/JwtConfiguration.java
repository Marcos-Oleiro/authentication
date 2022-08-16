package com.oleirosoftware.authentication.config;

import java.io.IOException;
import java.io.InputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class JwtConfiguration {
    
	@Value("${app.security.jwt.keystore-location}")
	private String keyStorePath;
	
	@Value("${app.security.jwt.keystore-password}")
	private String keyStorePassword;
	
	@Value("${app.security.jwt.key-alias}")
	private String keyAlias;
	
	@Value("${app.security.jwt.private-key-passphrase}")
	private String privateKeyPassphrase;

	
	@Bean
	public KeyStore keystore(){
		
		try {
			KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
			InputStream resourceAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(keyStorePath);
			keyStore.load(resourceAsStream, keyStorePassword.toCharArray());
		} catch (Exception e) {
			//TODO: handle exception
		}
		return null;
	}


}
// keytool -genkey -noprompt \
// -alias jwtsigning  \
// -dname "CN=oleirosoftware.com, OU=FIN, O=OleiroSoftware, L=Rio Grande, S=RS, C=BR" \
// -keystore keystore.jks \
// -storepass password \
// -keypass password