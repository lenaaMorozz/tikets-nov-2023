package org.psu.java.example;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.psu.java.example.application.FortunateTicketService;
import org.psu.java.example.domain.Ticket;
import org.psu.java.example.infrastructure.TicketGenerator;

import java.util.Iterator;
import java.util.stream.IntStream;

import static org.junit.Assert.*;

public class FortunateTicketServiceTest {

    private TicketGenerator ticketGenerator;
    private int maxNum;
    private FortunateTicketService service;

    @Before
    public void setUp() {
        maxNum = (int) Math.pow(10, 4);
        Iterator<Ticket> iterator = IntStream
                .range(0, maxNum)
                .mapToObj(n -> {
                    Ticket ticket = Mockito.mock(Ticket.class);
                    Mockito.when(ticket.getNumber()).thenReturn((long) n);
                    Mockito.when(ticket.getLength()).thenReturn(4);
                    Mockito.when(ticket.isFortunate()).thenReturn(true);
                    return ticket;
                }).iterator();

        ticketGenerator = Mockito.mock(TicketGenerator.class);
        Mockito.when(ticketGenerator.getTickets()).thenReturn(iterator);
        service = FortunateTicketService.getStreamInstance();
    }

    @After
    public void tearDown() {
        service = null;

    }

    @Test
    public void testCount() {
        int actual = service.count(ticketGenerator.getTickets());
        assertEquals(maxNum, actual);


    }
}