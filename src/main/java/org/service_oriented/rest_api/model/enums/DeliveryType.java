package org.service_oriented.rest_api.model.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum DeliveryType {
    STANDARD("Стандартный"),
    EXPRESS("Экспресс");

    private final String code;
}
