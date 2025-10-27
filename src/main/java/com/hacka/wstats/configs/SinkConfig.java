package com.hacka.wstats.configs;

import com.hacka.wstats.models.WaterLevel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Sinks;

@Configuration
public class SinkConfig {

    @Bean
    public Sinks.Many<WaterLevel> liveSink() {
        return Sinks.many().replay().limit(1);
    }
}
