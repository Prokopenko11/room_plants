package com.example.suns.repos;

import com.example.suns.domain.Today_temperature;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TodayTemperatureRepo extends CrudRepository<Today_temperature, Long> {
    List<Today_temperature> findAllByOrderByIdAsc();
}
