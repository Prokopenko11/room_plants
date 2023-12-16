package com.example.suns;


import com.example.suns.domain.Current_temperature;
import com.example.suns.domain.Last_week_temperature;
import com.example.suns.domain.Today_temperature;
import com.example.suns.repos.CurrentTemperatureRepo;
import com.example.suns.repos.LastWeekTemperatureRepo;
import com.example.suns.repos.TodayTemperatureRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class PlantsController {

    @Autowired
    private CurrentTemperatureRepo currentTemperatureRepo;

    @Autowired
    private LastWeekTemperatureRepo lastWeekTemperatureRepo;

    @Autowired
    private TodayTemperatureRepo todayTemperatureRepo;

    @GetMapping
    public String main(Map<String, Object> model) {
        Map<Integer, String> months = new HashMap<>();
        months.put(1, "Янв");
        months.put(2, "Фев");
        months.put(3, "Мар");
        months.put(4, "Апр");
        months.put(5, "Май");
        months.put(6, "Июн");
        months.put(7, "Июл");
        months.put(8, "Авг");
        months.put(9, "Сен");
        months.put(10, "Окт");
        months.put(11, "Ноя");
        months.put(12, "Дек");

        Map<Integer, String> days_of_week = new HashMap<>();
        days_of_week.put(1, "Пн");
        days_of_week.put(2, "Вт");
        days_of_week.put(3, "Ср");
        days_of_week.put(4, "Чт");
        days_of_week.put(5, "Пт");
        days_of_week.put(6, "Сб");
        days_of_week.put(7, "Вс");

        Iterable<Current_temperature> current = currentTemperatureRepo.findAll();
        Iterable<Last_week_temperature> lastWeek = lastWeekTemperatureRepo.findAll();

        List<Last_week_temperature> lastWeekArray = new ArrayList<>();
        for (Last_week_temperature lastWeekTemperature : lastWeek) {
            String day_of_week = days_of_week.get(Integer.parseInt(lastWeekTemperature.getDay_of_week()));
            lastWeekTemperature.setDay_of_week(day_of_week);
            lastWeekArray.add(lastWeekTemperature);
        }

        Iterable<Today_temperature> todayTemperatures = todayTemperatureRepo.findAllByOrderByIdAsc();
        List<Today_temperature> todayArray = new ArrayList<>();
        int id_count = 0;
        for (Today_temperature todayTemperature : todayTemperatures) {
            if (todayTemperature.getTemperature() != null) {
                todayTemperature.setTemperature_site(String.valueOf(todayTemperature.getTemperature()));
            }
            todayArray.add(todayTemperature);
            id_count++;
        }

        for (Current_temperature currentTemperature : current) {
            String time = String.valueOf(currentTemperature.getTimestamp_()).substring(11, 16);
            String month = months.get(Integer.parseInt(String.valueOf(currentTemperature.getTimestamp_())
                    .substring(5, 7)));
            String day_of_month = String.valueOf(currentTemperature.getTimestamp_()).substring(8, 10);
            currentTemperature.setTime(time);
            currentTemperature.setMonth(month);
            currentTemperature.setDay_of_month(day_of_month);
        }

        model.put("current", current);

        model.put("today0", todayArray.get(0));
        model.put("today1", todayArray.get(1));
        model.put("today2", todayArray.get(2));
        model.put("today3", todayArray.get(3));
        model.put("today4", todayArray.get(4));
        model.put("today5", todayArray.get(5));
        model.put("today6", todayArray.get(6));
        model.put("today7", todayArray.get(7));
        model.put("today8", todayArray.get(8));
        model.put("today9", todayArray.get(9));
        model.put("today10", todayArray.get(10));
        model.put("today11", todayArray.get(11));
        model.put("today12", todayArray.get(12));
        model.put("today13", todayArray.get(13));
        model.put("today14", todayArray.get(14));
        model.put("today15", todayArray.get(15));
        model.put("today16", todayArray.get(16));
        model.put("today17", todayArray.get(17));
        model.put("today18", todayArray.get(18));
        model.put("today19", todayArray.get(19));
        model.put("today20", todayArray.get(20));
        model.put("today21", todayArray.get(21));
        model.put("today22", todayArray.get(22));
        model.put("today23", todayArray.get(23));

        model.put("lastWeek1", lastWeekArray.get(0));
        model.put("lastWeek2", lastWeekArray.get(1));
        model.put("lastWeek3", lastWeekArray.get(2));
        model.put("lastWeek4", lastWeekArray.get(3));
        model.put("lastWeek5", lastWeekArray.get(4));
        model.put("lastWeek6", lastWeekArray.get(5));
        model.put("lastWeek7", lastWeekArray.get(6));

        return "main";
    }



}
