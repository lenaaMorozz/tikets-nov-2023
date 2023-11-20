package org.psu.java.example;

import org.junit.Test;
import org.psu.java.example.domain.Ticket;
import org.psu.java.example.infrastructure.TicketImpl;

import static org.junit.Assert.*;

/**
 * tests for {@link  TicketImpl}
 */

public class TicketImplTest {

    @Test
    public void testSuccessfulCreated() {
        var length = 6;
        var number = 100500;

        var actual = new TicketImpl(length, number);

        assertNotNull("Должен быть создан объект", actual);
    }

    @Test
    public void testNegativeNumber() {
        var length = 6;
        var number = -100500;
        var expected = "Передано отрицательное число";

        try {
            new TicketImpl(length, number);
        } catch (IllegalArgumentException actual) {
            assertEquals("Должно быть выброшено искл с нужным текстом", expected, actual.getMessage());
            return;
        }
        fail("Должно быть выброшено искл");
    }

    @Test
    public void testNegativeLength() {
        var length = -6;
        var number = 100500;
        var expected = "Передано отрицательное число";

        try {
            new TicketImpl(length, number);
        } catch (IllegalArgumentException actual) {
            assertEquals("Должно быть выброшено искл с нужным текстом", expected, actual.getMessage());
            return;
        }
        fail("Должно быть выброшено искл");
    }

    @Test
    public void testLengthIncorrect() {
        var length = 8;
        var number = 999999999;
        var expected = "Не соответствует длина билета и длина номера билета";

        try {
            new TicketImpl(length, number);
        } catch (IllegalArgumentException actual) {
            assertEquals("Должно быть выброшено искл с нужным текстом", expected, actual.getMessage());
            return;
        }
        fail("Должно быть выброшено искл");
    }

    @Test
    public void testIsFortunateWhenTicketIsNotFortunate() {
        Ticket ticket = new TicketImpl(6, 100500);
        assertFalse(ticket.isFortunate());
    }

    @Test
    public void testIsFortunateWhenTicketIsFortunate() {
        Ticket ticket = new TicketImpl(6, 100100);
        assertTrue(ticket.isFortunate());
    }



}