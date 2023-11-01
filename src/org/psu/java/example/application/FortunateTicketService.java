package org.psu.java.example.application;

import org.psu.java.example.domain.Ticket;

import java.util.Iterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public interface FortunateTicketService {
    static FortunateTicketService getInstance() {
        return new FortunateTicketImpl();
    }
    static FortunateTicketService getStreamInstance() {
        return new FortunateTicketStreamImpl();
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

class FortunateTicketStreamImpl implements FortunateTicketService {
    @Override
    public int count(Iterator<Ticket> tickets) {
        Iterable<Ticket> iterable = () -> tickets;
        Stream<Ticket> ticketStream =
                StreamSupport.stream(iterable.spliterator(), false);
        return (int) ticketStream.filter(Ticket::isFortunate).count();

    }
}
