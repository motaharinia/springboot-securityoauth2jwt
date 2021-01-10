## Spring Boot Security JWT

### Spring Security:
Spring Security is a powerful and highly customizable authentication and access-control framework. It is the de-facto standard for securing Spring-based applications.
Spring Security is a framework that focuses on providing both authentication and authorization to Java applications. Like all Spring projects, the real power of Spring Security is found in how easily it can be extended to meet custom requirements

### Spring Security Features:
- Comprehensive and extensible support for both Authentication and Authorization
- Protection against attacks like session fixation, clickjacking, cross site request forgery, etc
- Servlet API integration
- Optional integration with Spring Web MVC

### OAuth 2.0:
OAuth 2.0 is the industry-standard protocol for authorization. OAuth 2.0 focuses on client developer simplicity while providing specific authorization flows for web applications, desktop applications, mobile phones, and living room devices. This specification and its extensions are being developed within the IETF OAuth Working Group.

### JWS(JSON Web Signature) and JWT(JSON Web Token):
JWS is a specification created by the IETF that describes different cryptographic mechanisms to verify the integrity of data, namely the data in a JSON Web Token (JWT). It defines a JSON structure that contains the necessary information to do so.

### JWE(JSON Web Encryption):
In the first case, the JWT is represented as a JWS. While if it's encrypted, the JWT will be encoded in a JSON Web Encryption (JWE) structure.

### JWK(JSON Web Key):
The most common scenario when working with OAuth is having just signed JWTs. This is because we don't usually need to “hide” information but simply verify the integrity of the data.
Of course, whether we're handling signed or encrypted JWTs, we need formal guidelines to be able to transmit public keys efficiently.
This is the purpose of JWK, a JSON structure that represents a cryptographic key, defined also by the IETF.
Many Authentication providers offer a “JWK Set” endpoint, also defined in the specifications. With it, other applications can find information on public keys to process JWTs.
For instance, a Resource Server uses the kid (Key Id) field present in the JWT to find the correct key in the JWK set.

further references:
- https://www.youtube.com/watch?v=wxebTn_a930
- https://www.youtube.com/watch?v=fTAXXw-pKH8
- https://github.com/talk2amareswaran/spring-boot2-oauth2-auth-server-jwt-mysql
- https://www.baeldung.com/spring-security-oauth2-jws-jwk
- https://tools.ietf.org/html/rfc7515
- https://tools.ietf.org/html/rfc7517
- https://medium.com/swlh/stateless-jwt-authentication-with-spring-boot-a-better-approach-1f5dbae6c30f
- https://docs.oracle.com/javase/6/docs/technotes/tools/solaris/keytool.html

### Project Descriptions:
please see application.properties files in resources folder and select a active profile "dev" or "com" to run project. you can check test methods too.

steps:
- for the first time, download openssl and run these command in its parent folder
- generate keystore:  
```keytool -genkeypair -alias authentication-server -keyalg RSA -keypass Hami123456 -keystore auth-server.jks -storepass Hami123456 -validity 180 -keysize 2048 -dname "CN=Hami,OU=DevTeam,O=HamiSystem,L=HM,C=IR"```
- migrate to pkcs12:  
  ```keytool -importkeystore -srckeystore auth-server.jks -destkeystore auth-server.jks -deststoretype pkcs12```
- check file and public key:  
  ```keytool -list -rfc --keystore auth-server.jks | "openssl-0.9.8k_X64\bin\openssl.exe" x509 -inform pem -pubkey```
- copy auth-server.jks file in resource/static/security and build the project
- run jwtauthorizationserver

### IntellliJ IDEA Configurations:
- IntelijIDEA: Help -> Edit Custom Vm Options -> add these two line:
    - -Dfile.encoding=UTF-8
    - -Dconsole.encoding=UTF-8
- IntelijIDEA: File -> Settings -> Editor -> File Encodings-> Project Encoding: form "System default" to UTF-8. May be it affected somehow.
- IntelijIDEA: File -> Settings -> Editor -> General -> Code Completion -> check "show the documentation popup in 500 ms"
- IntelijIDEA: File -> Settings -> Editor -> General -> Auto Import -> check "Optimize imports on the fly (for current project)"
- IntelijIDEA: File -> Settings -> Editor -> Color Scheme -> Color Scheme Font -> Scheme: Default -> uncheck "Show only monospaced fonts" and set font to "Tahoma"
- IntelijIDEA: Run -> Edit Configuration -> Spring Boot -> XXXApplication -> Configuration -> Environment -> VM Options: -Dspring.profiles.active=dev
- IntelijIDEA: Run -> Edit Configuration -> Spring Boot -> XXXApplication -> Code Coverage -> Fix the package in include box

<hr/>
<a href="mailto:eng.motahari@gmail.com?"><img src="https://img.shields.io/badge/gmail-%23DD0031.svg?&style=for-the-badge&logo=gmail&logoColor=white"/></a>

