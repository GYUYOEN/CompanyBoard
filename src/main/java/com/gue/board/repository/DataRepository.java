package com.gue.board.repository;

import com.gue.board.common.Data.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ConcurrentHashMap;

@Repository
public class DataRepository {

    ConcurrentHashMap<Integer, Item> itemMap = new ConcurrentHashMap<>();

    @Value("${path.data}")
    private String pathData;

    public String getPathData() {
        return this.pathData;
    }

    public void delAllTest() {
        itemMap.clear();
    }

    public void addItem(Item info) {
        itemMap.put(info.itemNo, info);
    }
}
