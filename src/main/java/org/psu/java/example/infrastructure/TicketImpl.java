package org.psu.java.example.infrastructure;

import org.psu.java.example.domain.Ticket;
import org.psu.java.example.utils.NumberUtils;

public class TicketImpl implements Ticket {
    private final int length;
    private final long number;

    public TicketImpl(int length, long number) {
        this.length = length;
        this.number = number;
    }

    @Override
    public int getLength() {
        return length;
    }

    @Override
    public long getNumber() {
        return number;
    }
}
