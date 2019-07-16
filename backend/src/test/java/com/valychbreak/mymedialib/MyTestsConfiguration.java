package com.valychbreak.mymedialib;

import org.springframework.context.annotation.Configuration;

@Configuration
public class MyTestsConfiguration {
    // For https (SSL). Fixes some problem with tests
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
}
