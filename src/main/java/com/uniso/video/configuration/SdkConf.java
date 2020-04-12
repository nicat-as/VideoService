package com.uniso.video.configuration;

import com.uniso.video.sdk.Client;
import com.uniso.video.sdk.ClientFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

@Configuration
public class SdkConf {

    @Value("${api.video.sandbox}")
    private String sandboxKey;

    @Bean
    public Client createClient() {
        return new ClientFactory().createSandbox(sandboxKey);
    }

    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor(){
        return new MethodValidationPostProcessor();
    }
}
