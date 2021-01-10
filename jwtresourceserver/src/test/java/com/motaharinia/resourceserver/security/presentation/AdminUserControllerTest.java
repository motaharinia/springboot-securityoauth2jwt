package com.motaharinia.resourceserver.security.presentation;


import org.junit.Assert;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.test.context.ActiveProfiles;

import java.util.*;


/**
 * User: https://github.com/motaharinia<br>
 * Date: 2020-06-12<br>
 * Time: 01:05:58<br>
 * Description:<br>
 * کلاس تست ماژول ادمین
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("dev")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AdminUserControllerTest {

    @LocalServerPort
    private Integer PORT;


    @Value("${app.token.url}")
    private String TOKEN_URL;
    @Value("${app.token.client.id}")
    private String TOKEN_CLIENT_ID;
    @Value("${app.token.client.secret}")
    private String TOKEN_CLIENT_SECRET;
    @Value("${app.token.login.username}")
    private String TOKEN_LOGIN_USERNAME;
    @Value("${app.token.login.password}")
    private String TOKEN_LOGIN_PASSWORD;

    private OAuth2RestTemplate restTemplate;

    /**
     * شیی crud
     */
    private static Integer crudId = 8;
    private static String crudUsername = "eng.motahari@gmail.com";
    private static String random;


    /**
     * این متد مقادیر پیش فرض قبل از هر تست این کلاس تست را مقداردهی اولیه میکند
     */
    @BeforeEach
    void initUseCase() {
        System.out.println("----------beforeEach");

    }


    /**
     * این متد مقادیر پیش فرض را قبل از اجرای تمامی متدهای تست این کلاس مقداردهی اولیه میکند
     */
    @BeforeAll
    void beforeAll() throws Exception {
        //تنظیم زبان لوکیل پروژه روی پارسی
        Locale.setDefault(new Locale("fa", "IR"));
        //آماده سازی ابزار تست فراخوانی کننده ای پی آی ها با دریافت توکن بجای کلاینت
        ResourceOwnerPasswordResourceDetails resource = new ResourceOwnerPasswordResourceDetails();
        resource.setAccessTokenUri(TOKEN_URL);
        resource.setClientId(TOKEN_CLIENT_ID);
        resource.setClientSecret(TOKEN_CLIENT_SECRET);
        resource.setUsername(TOKEN_LOGIN_USERNAME);
        resource.setPassword(TOKEN_LOGIN_PASSWORD);
        resource.setGrantType("password");
        resource.setScope(Arrays.asList(new String[]{"role_admin", "role_user"}));
        DefaultOAuth2ClientContext clientContext = new DefaultOAuth2ClientContext();
        this.restTemplate = new OAuth2RestTemplate(resource, clientContext);
        log("------------------------ accesstoken:" + restTemplate.getAccessToken());
    }


    @Test
    @Order(1)
    public void commonTest()  {
        String uri = "http://localhost:" + PORT + "/v1/resource/common";
        String responseText = this.restTemplate.getForObject(uri, String.class);
        Assert.assertEquals("ResourceController.common", responseText);
    }

}
