package com.gue.board.controller;

import com.gue.board.dto.MenuDTO;
import com.gue.board.service.RouletteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping(value = "roulette")
public class RouletteController {

    private final RouletteService rouletteService;

    @GetMapping()
    public String roulette(Model model) {
        List<MenuDTO> menuDTO = rouletteService.listMenu();

        model.addAttribute("menu", menuDTO);

        LocalDate date = LocalDate.now();
        DayOfWeek dayOfWeek = date.getDayOfWeek();

        int dayOfWeekNumber = dayOfWeek.getValue();

        if(dayOfWeekNumber == 5) {
            return "friday.html";
        }

        return "roulette.html";
    }

    @PostMapping(value = "/add")
    @ResponseBody
    public ResponseEntity<String> addRoulette(@RequestParam(value = "menu") String menu) {
        log.info(menu);
        boolean isMenu = rouletteService.checkIsMenu(menu);

        if(!isMenu) {
            return new ResponseEntity<>("fail", HttpStatus.OK);
        }

        rouletteService.addMenu(menu);

        return new ResponseEntity<>(menu, HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete")
    @ResponseBody
    public ResponseEntity<String> deleteRoulette(@RequestParam(value = "menu") String menu) {
        log.info(menu);
        rouletteService.deleteRoulette(menu);
        return new ResponseEntity<>(menu, HttpStatus.OK);
    }


}
