package org.psu.java.example;

import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.psu.java.example.application.FortunateTicketService;
import org.psu.java.example.domain.Ticket;
import org.psu.java.example.infrastructure.TicketGenerator;
import org.psu.java.example.infrastructure.TicketImpl;
import org.psu.java.example.infrastructure.TicketRecordImpl;

import java.util.Iterator;
import java.util.stream.IntStream;

import static org.junit.Assert.*;

public class FortunateTicketServiceTest {

    private TicketGenerator ticketGenerator;
    private int maxNum;
    private FortunateTicketService service;
    private Ticket ticket;

    @Before
    public void setUp() {
        maxNum = (int) Math.pow(10, 4);
        ticket = Mockito.spy(new TicketImpl(4, maxNum - 1));
        Mockito.when(ticket.isFortunate()).thenReturn(true);

        Iterator<Ticket> iterator = IntStream
                .range(0, maxNum)
                .mapToObj(n -> ticket)
                .iterator();

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
        Mockito.verify(ticket, Mockito.atLeast(maxNum)).isFortunate();

    }
}