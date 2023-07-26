package com.gue.board.handler;

import com.gue.board.service.ExcelServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

// 스프링 시작시점에서 프로그램을 동작할 수 있도록 하는 handler
@RequiredArgsConstructor
@Component
public class ContextRefreshedEventHandler implements ApplicationListener<ContextRefreshedEvent> {

    private final ExcelServiceImpl excelService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        try {
            excelService.loadAll();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
