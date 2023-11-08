package org.psu.java.example.infrastructure;

import org.psu.java.example.domain.Ticket;

import java.util.Iterator;
import java.util.Optional;
import java.util.stream.IntStream;

public interface TicketGenerator {
    static TicketGenerator getInstance(GeneratorType type) {
        return switch (type) {
            case SIX -> new RecordTicketGenerator();
            default -> throw new IllegalArgumentException();
        };
    }
    Iterator<Ticket> getTickets();
    Optional<Ticket> getTicket(int number);

}

class SixDigitsTicketGenerator implements TicketGenerator {

    @Override
    public Iterator<Ticket> getTickets() {
        return IntStream
                .rangeClosed(0, 1000000)
                .mapToObj(number -> new TicketImpl(6, number))
                .map(Ticket.class::cast)
                .iterator();
    }

    @Override
    public Optional<Ticket> getTicket(final int number) {
        if (number < 0 || number >= 1000000) {
            return Optional.empty();
        }
        return Optional.of(new TicketImpl(6, number));
    }
}

class RecordTicketGenerator implements TicketGenerator {

    @Override
    public Iterator<Ticket> getTickets() {
        return IntStream
                .rangeClosed(0, 1000000)
                .mapToObj(number -> new TicketRecordImpl(6, number))
                .map(Ticket.class::cast)
                .iterator();

    }

    @Override
    public Optional<Ticket> getTicket(int number) {
        if (number < 0 || number >= 1000000) {
            return Optional.empty();
        }
        return Optional.of(new TicketRecordImpl(6, number));
    }
}

class LambdaTicketGenerator implements TicketGenerator {

    @Override
    public Iterator<Ticket> getTickets() {
        return IntStream
                .rangeClosed(0, 1000000)
                .mapToObj(number -> (SixDigitTicket) () -> number)
                .map(Ticket.class::cast)
                .iterator();
    }

    @Override
    public Optional<Ticket> getTicket(int number) {
        if (number < 0 || number >= 1000000) {
            return Optional.empty();
        }
        return Optional.of((SixDigitTicket) () -> number);
    }

    interface SixDigitTicket extends Ticket {
        @Override
        default int getLength() {
            return 6;
        }
    }
}


