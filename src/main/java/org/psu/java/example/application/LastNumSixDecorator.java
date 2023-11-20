package org.psu.java.example.application;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.psu.java.example.domain.Ticket;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class LastNumSixDecorator implements Ticket {
    Ticket ticket;

    @Override
    public boolean isFortunate() {
        return ticket.getNumber() % 10 == 6 && ticket.isFortunate();
    }

    @Override
    public int getLength() {
        return ticket.getLength();
    }

    @Override
    public long getNumber() {
        return ticket.getNumber();
    }
}
