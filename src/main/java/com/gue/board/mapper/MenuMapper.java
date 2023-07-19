package com.gue.board.mapper;

import com.gue.board.dto.MenuDTO;
import com.gue.board.entity.Menu;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MenuMapper extends GenericMapper<MenuDTO, Menu> {
}
