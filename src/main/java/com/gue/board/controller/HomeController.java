package com.gue.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

@Controller
public class HomeController {

    @GetMapping(value = "/index")
    public String index() {
        return "index.html";
    }

    @GetMapping(value = "/ladder")
    public String ladder() {
        return "ladder.html";
    }

    @GetMapping(value = "/board")
    public String board() throws IOException {
//        WeatherData weatherData = new WeatherData();
//        weatherData.lookUpWeather();

        return "board.html";
    }
}
