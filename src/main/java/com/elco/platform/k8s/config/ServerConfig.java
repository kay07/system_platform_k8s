package com.elco.platform.k8s.config;

import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.util.ClientBuilder;
import io.kubernetes.client.util.credentials.AccessTokenAuthentication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServerConfig {
    @Value("${k8s.url}")
    private String addr;
    @Value("${k8s.token}")
    private String token;
    @Bean
    public ApiClient getConnection() {
        ApiClient   apiClient = new ClientBuilder().
                setBasePath(addr).setVerifyingSsl(false).
                setAuthentication(new AccessTokenAuthentication(token)).build();
        io.kubernetes.client.openapi.Configuration.setDefaultApiClient(apiClient);
        return apiClient;
    }
}
