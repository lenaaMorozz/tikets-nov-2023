package org.psu.java.example.application;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.psu.java.example.domain.Ticket;

import java.util.Iterator;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public interface FortunateTicketService {
    static FortunateTicketService getInstance() {
        return new FortunateTicketImpl();
    }
    static FortunateTicketService getStreamInstance() {
        return new FortunateTicketStreamImpl(EvenDecorator::new);
    }

    default int count(Iterator<Ticket> tickets) {
        var result = 0;
        while (tickets.hasNext()) {
            var ticket = tickets.next();
            if (ticket.isFortunate()) {
                result++;
            }
        }
        return result;
    };
}

class FortunateTicketImpl implements FortunateTicketService {
}

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class FortunateTicketStreamImpl implements FortunateTicketService {
    UnaryOperator<Ticket> decorate;

    public FortunateTicketStreamImpl() {
        decorate = UnaryOperator.identity();
    }

    @Override
    public int count(Iterator<Ticket> tickets) {
        Iterable<Ticket> iterable = () -> tickets;
        Stream<Ticket> ticketStream =
                StreamSupport.stream(iterable.spliterator(), false);
        return (int) ticketStream.map(decorate).filter(Ticket::isFortunate).count();

    }
}
