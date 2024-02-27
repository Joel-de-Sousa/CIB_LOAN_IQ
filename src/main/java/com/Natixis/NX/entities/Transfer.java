package com.Natixis.NX.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "TRANSFER")
@Table(name = "TRANSFER")
public class Transfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false,length = 3)
    private Long id;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate transferDate;
    @Max(value = 1000000, message = "Max amount possible: 1.000.000")
    @Min(value = 1, message = "Min amount possible: 1")
    private double amount;
    private double bankFee;

    /**
     * Constructor to set the bank fee to asked parameters
     * @param transferDate
     * @param amount
     * @throws Exception
     */

    public Transfer(LocalDate transferDate, double amount) throws Exception {
        this.transferDate = transferDate;
        this.amount = amount;
        this.bankFee = calculateTransferFee(transferDate, amount);
    }

    /**
     * Para cada transação, uma taxa de transferencia deve ser cobrada da seguinte forma:
     * Taxa A (valor da transferencia entre 0€ e 1000€)
     * - Data do Agendamento igual Data Atual - 3% do valor da transação + 3€
     * Taxa B (valor da transferencia entre 1001€ e 2000€)
     * - Data de agendamento entre 1 e 10 dias da data atual - 9%
     * Taxa C (valor da transferencia maior que 2000€)
     * - Data de agendamento entre 11 e 20 dias da data atual - 8.2% do valor da transação
     * - Data de agendamento entre 21 e 30 dias da data atual - 6.9% do valor da transação
     * - Data de agendamento entre 31 e 40 dias da data atual - 4.7% do valor da transação
     * - Data de agendamento maior que 40 dias da data atual - 1.7% do valor da transação
     */
    private double calculateTransferFee(LocalDate transferDate, double amount) throws Exception {
        long daysBetween = ChronoUnit.DAYS.between(LocalDate.now(),transferDate);

        if((amount > 0 && amount <= 1000) && transferDate.equals(LocalDate.now())){
            return ((amount * 0.03)+3);
        }
        else if((amount > 0 && amount <= 1000) && (daysBetween >= 1 && daysBetween <= 10)) {
            return ((amount * 0.09));
        }
        else if((amount > 1001 && amount <= 2000) && (daysBetween >= 1 && daysBetween <= 10) ) {
            return ((amount * 0.09));
        }
        throw new Exception("Unsupported combination of amount and transfer date");
    }




}
