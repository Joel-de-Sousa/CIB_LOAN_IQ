package com.Natixis.NX.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Future;
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
    @Future(message = "The date cannot be before today")
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

    public Transfer(LocalDate transferDate, double amount) {
        this.transferDate = transferDate;
        this.amount = amount;
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
    public double calculateTransferFee(LocalDate transferDate, double amount) throws Exception {
        isDateValid(transferDate);
        long daysBetween = ChronoUnit.DAYS.between(LocalDate.now(),transferDate);
        double fee;
        if((amount > 0 && amount <= 1000) && transferDate.equals(LocalDate.now())){
            fee = ((amount * 0.03)+3);
        }
        else if((amount >= 1001 && amount <= 2000) && (daysBetween >= 1 && daysBetween <= 10)) {
            fee = ((amount * 0.09));
        }
        else if((amount > 2000) && (daysBetween >= 11 && daysBetween <= 20) ) {
            fee = ((amount * 0.082));
        }
        else if((amount > 2000) && (daysBetween >= 21 && daysBetween <= 30) ) {
            fee = ((amount * 0.069));
        }
        else if((amount > 2000) && (daysBetween >= 31 && daysBetween <= 40) ) {
            fee = ((amount * 0.047));
        }
        else if((amount > 2000) && (daysBetween >= 40) ) {
            fee = ((amount * 0.0017));
        } else {
            throw new Exception("Unsupported combination of amount and transfer date");
        }
        return Math.round(fee * 100.0) / 100.0;
    }

    /**
     * Checks if selected date is valid
     * @param transferDate
     * @throws Exception
     */
    private void isDateValid(LocalDate transferDate)throws Exception{
        if(transferDate.isBefore(LocalDate.now())) {
            throw new Exception("The date selected cannot be before today");
        }
    }





}
