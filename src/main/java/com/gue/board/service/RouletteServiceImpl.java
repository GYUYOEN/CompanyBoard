package com.gue.board.service;

import com.gue.board.dto.MenuDTO;
import com.gue.board.entity.Menu;
import com.gue.board.mapper.MenuMapper;
import com.gue.board.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RouletteServiceImpl implements RouletteService {

    private final MenuRepository menuRepository;
    private final MenuMapper menuMapper;

    @Override
    public boolean checkIsMenu(String menu) {
        Menu entityMenu = menuRepository.findByMenuName(menu);
        if(entityMenu != null) {
            return false;
        }
        return true;
    }

    @Override
    public void addMenu(String menu) {
        Menu entityMenu = Menu.builder().menuName(menu).build();
        menuRepository.save(entityMenu);
    }

    @Override
    public List<MenuDTO> listMenu() {
        List<Menu> menuEntity = menuRepository.findAll();

        return menuMapper.toDTOList(menuEntity);
    }

    @Override
    @Transactional
    public void deleteRoulette(String menu) {
        menuRepository.deleteByMenuName(menu);
    }
}
