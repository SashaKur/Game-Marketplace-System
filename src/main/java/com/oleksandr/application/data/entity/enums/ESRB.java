package com.oleksandr.application.data.entity.enums;

public enum ESRB {
    E("Everyone"),
    T("Teen"),
    A("Adults only"),
    RP("Rating pending");

    public final String label;

    ESRB(String label) {
        this.label = label;
    }
}
