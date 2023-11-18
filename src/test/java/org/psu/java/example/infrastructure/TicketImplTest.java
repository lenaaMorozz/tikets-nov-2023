package org.psu.java.example.infrastructure;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Тесты для {@link TicketImpl}
 */
public class TicketImplTest {

    /**
     * Проверяет успешное создание объекта
     */
    @Test
    public void testSuccessfullyCreated() {
        // given
        var length = 6;
        var number = 100_500;

        // when
        var actual = new TicketImpl(length, number);

        // then
        assertNotNull("Должен быть создан объект", actual);
    }

    /**
     * Проверяет, что при отрицательном номере происходит сбой.
     */
    @Test
    public void testNegativeNumber() {
        // given
        var length = 6;
        var number = -100_500;
        var expected = String.format("Передан %d < 0", number);

        // when
        try {
            new TicketImpl(length, number);
        } catch (IllegalArgumentException actual) {
            // then
            assertEquals("Должно быть выброшено исключение с нужным текстом", expected, actual.getMessage());
            return;
        }
        fail("Должно быть выброшено исключение!");
    }

    /**
     * Можем создать билет с нечётной длиной
     */
    @Test
    public void testLengthIsOdd() {
        // given
        var length = 7;
        var number = 100_500;

        // when
        var actual = new TicketImpl(length, number);

        // then
        assertNotNull("Должен быть создан объект", actual);
    }

    /**
     * Номер билета не может быть больше максимально возможного.
     */
    @Test
    public void testNumberGreaterThenMaxNumber() {
        // given
        var length = 4;
        var number = 100_500;
        int maxTicketNumber = (int) Math.pow(10, length) - 1;
        var expected = String.format("%d > %d", number, maxTicketNumber);

        // when
        try {
            new TicketImpl(length, number);
        } catch (IllegalArgumentException actual) {
            // then
            assertEquals("Должно быть выброшено исключение с нужным текстом", expected, actual.getMessage());
            return;
        }
        fail("Должно быть выброшено исключение!");
    }

    /**
     * Если сумма первых трёх цифр совпадает с суммой последних трёх цифр, то билет счастливый
     */
    @Test
    public void testTicketIsFortunate() {
        // given
        var length = 6;
        var number = 1001;

        // when
        var actual = new TicketImpl(length, number);

        // then
        assertTrue("Билет является счастливым", actual.isFortunate());
    }

    /**
     * Если сумма первых трёх цифр не совпадает с суммой последних трёх цифр, то билет не счастливый
     */
    @Test
    public void testTicketIsInFortunate() {
        // given
        var length = 6;
        var number = 1002;

        // when
        var actual = new TicketImpl(length, number);

        // then
        assertFalse("Билет не является счастливым", actual.isFortunate());
    }

    /**
     * Если длина билета - нечётное число, то счастливых билетов нет.
     */
    @Test
    public void testTicketIsInFortunateWhenLengthIsOdd() {
        // given
        var length = 7;
        var number = 1001;

        // when
        var actual = new TicketImpl(length, number);

        // then
        assertFalse("Билет не является счастливым", actual.isFortunate());
    }

}