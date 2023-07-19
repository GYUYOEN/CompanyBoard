package com.gue.board.service;

import com.gue.board.dto.MenuDTO;

import java.util.List;

public interface RouletteService {
    void addMenu(String menu);

    List<MenuDTO> listMenu();

    void deleteRoulette(String menu);

    boolean checkIsMenu(String menu);
}
