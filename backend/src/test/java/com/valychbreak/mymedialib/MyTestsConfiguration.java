package com.valychbreak.mymedialib;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class MyTestsConfiguration {
    static {
        //for localhost testing only
        javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(
                new javax.net.ssl.HostnameVerifier() {

                    public boolean verify(String hostname,
                                          javax.net.ssl.SSLSession sslSession) {
                        if (hostname.equals("localhost")) {
                            return true;
                        }
                        return false;
                    }
                });
    }

    /*@Bean
    @Primary
    public TestRestTemplate testRestTemplate() {
        return new TestRestTemplate(TestRestTemplate.HttpClientOption.ENABLE_REDIRECTS, TestRestTemplate.HttpClientOption.ENABLE_COOKIES);
    }*/
}