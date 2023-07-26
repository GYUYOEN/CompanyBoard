package com.gue.board.common;

public class Data {

    public static class Item {
        public int itemNo;
        public String itemName;
        public int itemPrice;
        public int itemStatus; //구매 가능 여부 (1이면 살수있고 0이면 조건 만족해야 살수있음)
        public int condition; //구매 불가능일시 해당아이템 필요한 조건 (업적테이블 index)
        public String conditionText;
        public String textExplanation;
    }
}
