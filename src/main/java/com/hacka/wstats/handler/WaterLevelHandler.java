package com.hacka.wstats.handler;

import com.hacka.wstats.configs.SinkConfig;
import com.hacka.wstats.models.WaterLevel;
import com.hacka.wstats.repository.WaterLevelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.time.Duration;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class WaterLevelHandler {

    @Autowired
    private WaterLevelRepository waterLevelRepository;

    @Autowired
    private Sinks.Many<WaterLevel> liveSink;

    public Flux<WaterLevel> lifetimeWaterLevel(ServerRequest request) {
        return Flux.interval(Duration.ofSeconds(5))
                .flatMap(t -> waterLevelRepository.findAll())
                .doOnNext(liveSink::tryEmitNext);
    }
}
