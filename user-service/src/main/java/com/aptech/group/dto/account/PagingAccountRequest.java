package com.aptech.group.dto.account;

public interface PagingAccountRequest {

    default int getFirst() {
        return 0;
    }

    default int getMax() {
        return 10;
    }

    default boolean getBriefRepresentation() {
        return true;
    }
}
