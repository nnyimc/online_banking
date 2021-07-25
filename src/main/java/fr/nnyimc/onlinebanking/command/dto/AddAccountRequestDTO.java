package fr.nnyimc.onlinebanking.command.dto;

import lombok.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddAccountRequestDTO {
    private BigDecimal initialBalance;
    private String currency;
}
