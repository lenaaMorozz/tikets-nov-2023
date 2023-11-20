package org.psu.java.example.infrastructure;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.psu.java.example.domain.Ticket;
import org.psu.java.example.utils.NumberUtils;

@Getter
@ToString
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TicketImpl implements Ticket {
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    int length;
    long number;

    public TicketImpl(int length, long number) {
        if (number < 0 || length < 0) {
            throw new IllegalArgumentException("Передано отрицательное число");
        }

        if (number > Math.pow(10, length) - 1) {
            throw new IllegalArgumentException("Не соответствует длина билета и длина номера билета");
        }
        this.length = length;
        this.number = number;
    }
}
