package org.oriented.rest.api.model.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum OrderStatus {
    NEW("Новый"),
    IN_PROGRESS("В процессе"),
    COMPLETED("Выполнен"),
    CANCELLED("Отменен");
    private final String code;
}

