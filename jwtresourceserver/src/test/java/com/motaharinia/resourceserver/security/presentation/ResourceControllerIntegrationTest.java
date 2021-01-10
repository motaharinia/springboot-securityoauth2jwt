package com.motaharinia.resourceserver.security.presentation;



import org.junit.Test;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.test.context.ActiveProfiles;


import java.util.Arrays;
import java.util.Locale;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("dev")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ResourceControllerIntegrationTest {

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

//    private final String RESOURCE_ENDPOINT = "/common";
//    @Autowired
//    private MockMvc mockMvc;

    /**
     * این متد مقادیر پیش فرض را قبل از اجرای هر متد تست این کلاس مقداردهی اولیه میکند
     */
    @BeforeEach
    void beforeEach() {
        System.out.println("----------beforeEach");
//        //تنظیم زبان لوکیل پروژه روی پارسی
//        Locale.setDefault(new Locale("fa", "IR"));
//        //آماده سازی ابزار تست فراخوانی کننده ای پی آی ها با دریافت توکن بجای کلاینت
//        ResourceOwnerPasswordResourceDetails resource = new ResourceOwnerPasswordResourceDetails();
//        resource.setAccessTokenUri(TOKEN_URL);
//        resource.setClientId(TOKEN_CLIENT_ID);
//        resource.setClientSecret(TOKEN_CLIENT_SECRET);
//        resource.setUsername(TOKEN_LOGIN_USERNAME);
//        resource.setPassword(TOKEN_LOGIN_PASSWORD);
//        resource.setGrantType("password");
//        resource.setScope(Arrays.asList(new String[]{"role_admin", "role_user"}));
//        DefaultOAuth2ClientContext clientContext = new DefaultOAuth2ClientContext();
//        this.restTemplate = new OAuth2RestTemplate(resource, clientContext);
//        System.out.println("------------------------ accesstoken:" + restTemplate.getAccessToken());
    }

//    /**
//     * این متد مقادیر پیش فرض را قبل از اجرای تمامی متدهای تست این کلاس مقداردهی اولیه میکند
//     */
//    @BeforeAll
//    void beforeAll() throws Exception {
//        System.out.println("----------beforeAll");
//
//    }

    @Test
    @Order(1)
    public void commonTest() throws Exception {
        System.out.println("----------commonTest");
        String uri = "http://localhost:" + PORT + "/v1/resource/common";
//        String responseText = this.restTemplate.getForObject("http://localhost:" + PORT + "/v1/resource/common", String.class);
//        Assert.assertEquals("ResourceController.common", responseText);
    }


//    @Test
//    public void givenAccessToken_whenGetUserResource_thenSuccess() throws Exception {
//        System.out.println("-------------------------TOKEN_URL:" + TOKEN_URL);
//        String accessToken = obtainAccessToken();
//        System.out.println("-------------------------accessToken:" + accessToken);
//
//
//        // Access resources using access token
//        this.mockMvc.perform(get(RESOURCE_ENDPOINT).header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(1L))
//                .andExpect(jsonPath("$.name", isA(String.class)));
//    }
//
//    private String obtainAccessToken() {
//        // get access token
//        Response response = RestAssured.given()
//                .auth().preemptive().basic(TOKEN_CLIENT_ID, TOKEN_CLIENT_SECRET)
//                .contentType("application/x-www-form-urlencoded").log().all()
//                .formParam("grant_type", "password")
//                .formParam("username", TOKEN_LOGIN_USERNAME)
//                .formParam("password", TOKEN_LOGIN_PASSWORD)
//                .when()
//                .post(TOKEN_URL);
//
//        System.out.println("-------------------------response:" + response);
//        System.out.println("-------------------------response.getBody().asString():" + response.getBody().asString());
//        return response.jsonPath().getString("access_token");
//
////        JSONObject jsonObject = new JSONObject(response.getBody().asString());
////        String accessToken = jsonObject.get("access_token").toString();
////        String tokenType = jsonObject.get("token_type").toString();
////        log.info("Oauth Token with type " + tokenType + "   " + accessToken);
////
////
////        // get access token
////        Map<String, String> params = new HashMap<String, String>();
////        params.put("grant_type", "client_credentials");
////        params.put("scope", "read");
////        Response response = RestAssured.given()
////                .auth()
////                .basic("hami-client", "hami-secret")
////                .formParams(params)
////                .post(TOKEN_URL);
////        return response.jsonPath()
////                .getString("access_token");
//    }
}