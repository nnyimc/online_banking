package fr.nnyimc.onlinebanking.command.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditAccountRequestDTO {
    private String id;
    private BigDecimal amount;
    private String currency;
}
