package com.mesh.group.test.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransferMoneyRequest {
    @NotNull(message = "UserToId can't be null")
    private Long userToId;
    @NotNull(message = "Transfer value can't be null")
    private BigDecimal value;
}
