package com.uniso.video;

import com.uniso.video.sdk.Client;
import com.uniso.video.sdk.ClientFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class VideoApplication {

	@Value("${api.video.sandbox}")
	private String sandboxKey;

	public static void main(String[] args) {
		SpringApplication.run(VideoApplication.class, args);
	}

	@Bean
	public Client createClient(){
		return new ClientFactory().createSandbox(sandboxKey);
	}
}
