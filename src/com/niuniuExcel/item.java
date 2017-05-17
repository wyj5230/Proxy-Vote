package com.niuniuExcel;

/**
 * Created by 20160816 on 17-Feb-2017.
 */
public class item {

    private int value;

    private String disc;

    private String column;

    public item(int value, String disc, String column) {
        this.value = value;
        this.disc = disc;
        this.column = column;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }


    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getDisc() {
        return disc;
    }

    public void setDisc(String disc) {
        this.disc = disc;
    }


}
