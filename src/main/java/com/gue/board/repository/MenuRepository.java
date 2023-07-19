package com.gue.board.repository;


import com.gue.board.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Integer> {
    void deleteByMenuName(String menuName);

    Menu findByMenuName(String menuName);
}
