package com.hemanth.wallet.dto;

import lombok.Data;
import java.math.BigDecimal;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Data
public class TransferRequest {
    @NotNull
    private String fromUser;
    @NotNull
    private String toUser;
    @Min(1)
    private BigDecimal amount;
}