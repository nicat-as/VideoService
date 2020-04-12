package com.uniso.video.configuration;

import com.uniso.video.sdk.Client;
import com.uniso.video.sdk.ClientFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SdkConf {

    @Value("${api.video.sandbox}")
    private String sandboxKey;

    @Bean
    public Client createClient() {
        return new ClientFactory().createSandbox(sandboxKey);
    }
}
