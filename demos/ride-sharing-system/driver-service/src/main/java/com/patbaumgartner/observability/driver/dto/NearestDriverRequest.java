package com.patbaumgartner.observability.driver.dto;

import jakarta.validation.constraints.NotNull;

public record NearestDriverRequest(@NotNull Double latitude, @NotNull Double longitude) {
}
