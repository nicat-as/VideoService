package com.uniso.video;

import com.uniso.video.sdk.Client;
import com.uniso.video.sdk.ClientFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class VideoApplication {

    @Value("${api.video.sandbox}")
    private String sandboxKey;

    public static void main(String[] args) {
        SpringApplication.run(VideoApplication.class, args);
    }

    @Bean
    public Client createClient() {
        return new ClientFactory().createSandbox(sandboxKey);
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }
}
