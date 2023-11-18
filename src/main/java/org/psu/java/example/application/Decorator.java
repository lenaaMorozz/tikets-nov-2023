package org.psu.java.example.application;

import lombok.Data;
import lombok.experimental.Delegate;
import org.psu.java.example.domain.Ticket;

@Data
public class Decorator implements Ticket {
    @Delegate(excludes = Exclude.class)
    private Ticket ticket;
    @Override
    public boolean isFortunate() {
        return getNumber() % 10 == 6 && ticket.isFortunate();
    }

    private interface Exclude {
        boolean isFortunate();
    }

}
