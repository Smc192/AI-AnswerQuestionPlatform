package com.luots.AIDaTi.config;

import com.zhipu.oapi.ClientV4;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "ai")
@Data
public class AiConfig {
    /**
     * apikey: 从开放平台获取
     */
    private String apikey;

    @Bean
    public ClientV4 getClientV4() {
        return new ClientV4.Builder(apikey).build();
    }
}
