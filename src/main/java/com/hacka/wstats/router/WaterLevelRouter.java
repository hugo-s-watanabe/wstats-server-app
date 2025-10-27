package com.hacka.wstats.router;

import com.hacka.wstats.handler.WaterLevelHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class WaterLevelRouter {

    @Autowired
    private WaterLevelHandler waterLevelHandler;

    @Bean
    public RouterFunction<ServerResponse> waterLevelRouter() {
        return RouterFunctions.route()
                .GET("/wlevel", request -> ServerResponse.sse(this.waterLevelHandler::lifetimeWaterLevel));
    }
}
