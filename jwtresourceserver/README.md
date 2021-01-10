## Spring Boot Profile Properties

### Profiles:
Spring Profiles provide a way to segregate parts of your application configuration and make it only available in certain environments
Spring boot allows to add profile-specific properties files by using application-{myProfileName}.properties pattern.

### Active Profile:
To load a specific profile property file we can use command line option -Dspring.profiles.active=myProfileName.
The default file application.properties can still be used by not using any -Dspring.profile.active option or by using -Dspring.profiles.active=default. The default properties file can be named as application-default.properties as well.
The properties specified in default application.properties are overridden by the profile-specific file properties.

### reading properties by @Value:
add resource tag to pom.xml inside <build> tag for reading properties by @Value
```
       <resources>
            <resource>
                <directory>src/main/resources</directory>
                <filtering>true</filtering>
                <includes>
                    <include>**/*.properties</include>
                </includes>
            </resource>
        </resources>
```

further references:     
- https://docs.spring.io/spring-boot/docs/1.1.x/reference/html/boot-features-profiles.html
- https://www.logicbig.com/tutorials/spring-framework/spring-boot/profile-specific-properties.html
- https://stackoverflow.com/questions/41244585/how-to-set-spring-active-profile-environment-variable-in-intellij/46345476
- https://stackoverflow.com/questions/39738901/how-do-i-activate-a-spring-boot-profile-when-running-from-intellij


### Project Descriptions:
run project and view 
- url in "http://localhost:8085/" 
- random integer in "http://localhost:8085/randomInt" 

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

