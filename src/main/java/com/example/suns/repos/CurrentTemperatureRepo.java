package com.example.suns.repos;

import com.example.suns.domain.Current_temperature;
import org.springframework.data.repository.CrudRepository;

public interface CurrentTemperatureRepo extends CrudRepository<Current_temperature, Long> {
}
