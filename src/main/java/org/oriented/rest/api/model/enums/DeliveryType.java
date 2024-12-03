package org.oriented.rest.api.model.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum DeliveryType {
    STANDARD("Стандартный"),
    EXPRESS("Экспресс");

    private final String code;
}
