package com.hacka.wstats.models;


public record WaterLevel(
        long id,
        String key,
        double levelPercentage
) { }
