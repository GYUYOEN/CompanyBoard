package com.gue.board.common;

public class Types {

    public static enum ExcelNumber {
        TEST("item_table", 0);

        private String name;
        private int value;
        private ExcelNumber(String name, int value) {
            this.name = name;
            this.value = value;
        }

        public String getFileName() {
            return this.name + ".csv";
        }
    }
}
