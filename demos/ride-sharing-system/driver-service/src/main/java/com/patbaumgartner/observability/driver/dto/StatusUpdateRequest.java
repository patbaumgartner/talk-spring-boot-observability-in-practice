package com.patbaumgartner.observability.driver.dto;

import jakarta.validation.constraints.NotNull;

public record StatusUpdateRequest(@NotNull String status) {
}
