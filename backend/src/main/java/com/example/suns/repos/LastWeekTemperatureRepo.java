package com.example.suns.repos;

import com.example.suns.domain.Last_week_temperature;
import org.springframework.data.repository.CrudRepository;

public interface LastWeekTemperatureRepo extends CrudRepository<Last_week_temperature, Long> {
}
