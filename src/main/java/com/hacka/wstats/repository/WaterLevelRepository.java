package com.hacka.wstats.repository;

import com.hacka.wstats.models.WaterLevel;
import io.r2dbc.spi.Row;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public class WaterLevelRepository {

    private final DatabaseClient databaseClient;


    public WaterLevelRepository(DatabaseClient databaseClient) {
        this.databaseClient = databaseClient;
    }


    public Flux<WaterLevel> findAll() {
        return databaseClient.sql("SELECT * FROM water_level")
                .map((row, rowMetadata) -> parse(row))
                .all();
    }


    private WaterLevel parse(Row row) {
        Long id = row.get("id", Long.class);
        String key = row.get("key", String.class);
        Double levelPercentage = row.get("levelPercentage", Double.class);

        return new WaterLevel(id, key, levelPercentage);
    }
}
