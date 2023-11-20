package org.psu.java.example.infrastructure;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.psu.java.example.domain.Ticket;

import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
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

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
abstract class AbstractGenerator implements TicketGenerator {
    @Getter(AccessLevel.PROTECTED)
    int ticketLength;

    protected abstract IntFunction<Ticket> toTicket();

    protected IntStream getNumbersAsStream() {
        return IntStream.rangeClosed(0, (int) Math.pow(10, ticketLength));
    }

    @Override
    public Iterator<Ticket> getTickets() {
        return getNumbersAsStream().mapToObj(toTicket()).iterator();
    }

    @Override
    public Optional<Ticket> getTicket(int number) {
        Function<Integer, Ticket> toTicket = toTicket()::apply;
        return Optional.of(number)
                .filter(n -> number >= 0 && number < Math.pow(10, ticketLength))
                .map(toTicket);
    }

}

class EightDigitsTicketGenerator extends AbstractGenerator {

    public EightDigitsTicketGenerator() {
        super(8);
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
class SixDigitsTicketGenerator extends AbstractGenerator {

    public SixDigitsTicketGenerator() {
        super(6);
    }

    @Override
    public Iterator<Ticket> getTickets() {
        return IntStream
                .rangeClosed(0, 1000000)
                .mapToObj(number -> new TicketImpl(6, number))
                .map(Ticket.class::cast)
                .iterator();
    }

    @Override
    protected IntFunction<Ticket> toTicket() {
        return number -> new TicketImpl(6, number);
    }
}

class RecordTicketGenerator extends AbstractGenerator {

    public RecordTicketGenerator() {
        super(6);
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

    public LambdaTicketGenerator() {
        super(6);
    }

    @Override
    protected IntFunction<Ticket> toTicket() {
       return number -> (SixDigitTicket) () -> number;
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


