package com.hacka.wstats.handler;


import com.hacka.wstats.repository.WaterLevelRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;


@Component
public class WaterLevelHandler {

    @Autowired
    private WaterLevelRepository waterLevelRepository;

    public Mono<ServerResponse> lifetimeWaterLevel(ServerRequest request) {
        return ServerResponse.ok()
    		.contentType(MediaType.TEXT_EVENT_STREAM)
    		.body(waterLevelRepository.findAll().collectList(), List.class);
    }
}
