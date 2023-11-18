package org.psu.java.example.infrastructure;

import org.psu.java.example.domain.Ticket;

import java.util.Iterator;
import java.util.Optional;
import java.util.function.IntFunction;
import java.util.stream.IntStream;

public interface TicketGenerator {
    static TicketGenerator getInstance(GeneratorType type) {
        return switch (type) {
            case SIX -> new RecordTicketGenerator();
            case EIGHT -> new EightDigitsTicketGenerator();
            default -> throw new IllegalArgumentException();
        };
    }
    Iterator<Ticket> getTickets();
    Optional<Ticket> getTicket(int number);
}
abstract class AbstractGenerator implements TicketGenerator {

    protected abstract IntFunction<Ticket> toTicket();

    protected abstract IntStream getNumbersAsStream();

    @Override
    public Iterator<Ticket> getTickets() {
        return getNumbersAsStream().mapToObj(toTicket()).iterator();
    }

    @Override
    public Optional<Ticket> getTicket(int number) {
        return Optional.empty();
    }
}

class EightDigitsTicketGenerator extends AbstractGenerator {

    @Override
    public Optional<Ticket> getTicket(int number) {
        return Optional.empty();
    }

    @Override
    protected IntFunction<Ticket> toTicket() {
        return number -> (TheTicket) () -> number;
    }

    @Override
    protected IntStream getNumbersAsStream() {
        return IntStream.range(0, 100_000_000);
    }

    interface TheTicket extends Ticket {
        @Override
        default int getLength() {
            return 8;
        }
    }
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
        return Optional
                .of(number)
                .filter(n -> number < 0 || number >= 1000000)
                .map(n -> new TicketImpl(6, n));
    }
}

class RecordTicketGenerator extends AbstractGenerator {

    @Override
    public Optional<Ticket> getTicket(int number) {
        if (number < 0 || number >= 1000000) {
            return Optional.empty();
        }
        return Optional.of(new TicketRecordImpl(6, number));
    }

    @Override
    protected IntFunction<Ticket> toTicket() {
        return number -> new TicketRecordImpl(6, number);
    }

    @Override
    protected IntStream getNumbersAsStream() {
        return IntStream.rangeClosed(0, 1000000);
    }
}

class LambdaTicketGenerator extends AbstractGenerator {

    @Override
    protected IntFunction<Ticket> toTicket() {
       return number -> (SixDigitTicket) () -> number;
    }

    @Override
    protected IntStream getNumbersAsStream() {
        return IntStream.rangeClosed(0, 1000000);
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


