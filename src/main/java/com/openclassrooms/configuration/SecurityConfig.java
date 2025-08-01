package com.openclassrooms.configuration;

import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Value("${app.jwt.private-key}")
  private String jwtPrivateKey;

  @Value("${app.jwt.public-key}")
  public String jwtPublicKey;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
        .csrf(csrf -> csrf.disable());

    return http.build();
  }

  @Bean
  public JwtEncoder jwtEncoder() {
    try {
      RSAPublicKey publicKey = parsePublicKey(jwtPublicKey);
      RSAPrivateKey privateKey = parsePrivateKey(jwtPrivateKey);

      JWK jwk = new RSAKey.Builder(publicKey)
          .privateKey(privateKey)
          .build();

      JWKSource<SecurityContext> jwkSource = new ImmutableJWKSet<>(new JWKSet(jwk));

      return new NimbusJwtEncoder(jwkSource);

    } catch (Exception e) {
      throw new RuntimeException("Error with JWT configuration: " + e.getMessage(), e);
    }
  }

  /**
   * Obtain the binary key data from a PEM string
   *
   * @param pemKey a PEM-formated key
   * @return byte[] the decoded key data
   */
  private byte[] keyDataFromPEM(String pemKey) {
    String regex = "(-+[A-Z ]+-+)|\\s";
    String base64Key = pemKey.replaceAll(regex, "");
    return Base64.getDecoder().decode(base64Key);
  }

  /**
   * Get a RSAPublicKey object from a PEM string
   *
   * @param pemKey a PEM-formated RSA public key string
   * @return RSAPublicKey
   * @throws Exception if the PEM key is invalid
   */
  private RSAPublicKey parsePublicKey(String pemKey) throws Exception {
    byte[] keyBytes = keyDataFromPEM(pemKey);
    X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
    return (RSAPublicKey) keyFactory.generatePublic(keySpec);
  }

  /**
   * Get a RSAPrivateKey object from a PEM string
   *
   * @param pemKey a PEM-formated RSA public key string
   * @return RSAPublicKey
   * @throws Exception if the PEM key is invalid
   */
  private RSAPrivateKey parsePrivateKey(String pemKey) throws Exception {
    byte[] keyBytes = keyDataFromPEM(pemKey);
    PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
    return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
  }

}
