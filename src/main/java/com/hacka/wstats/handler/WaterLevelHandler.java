package com.hacka.wstats.handler;


import com.hacka.wstats.models.WaterLevel;
import com.hacka.wstats.repository.WaterLevelRepository;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Component
public class WaterLevelHandler {

    @Autowired
    private WaterLevelRepository waterLevelRepository;
    
    
    private ServerSentEvent<WaterLevel> mapToServerSentEvent(WaterLevel item) {
    	return ServerSentEvent.<WaterLevel>builder()
				.id(String.format("SSE-%s-%s", item.id(), item.key()))
				.event("live-wlevel")
				.data(item)
				.build();
    }
    

    public Mono<ServerResponse> lifetimeWaterLevel(ServerRequest request) {
        return ServerResponse.ok()
    		.contentType(MediaType.TEXT_EVENT_STREAM)
    		.body(BodyInserters.fromServerSentEvents(
    				Flux.interval(Duration.ofSeconds(5))
    					.flatMap(timestamp -> waterLevelRepository.findRecents(30))
    					.map(this::mapToServerSentEvent)
    		));
    }
}
