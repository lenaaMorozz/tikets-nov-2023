package org.psu.java.example.utils;

import java.util.Collection;

/**
 * Утилитные методы для работы с числами
 */
public class NumberUtils {
    /**
     * Возвращает сумму цифр переданного числа
     * @param number число
     * @return сумма цифр
     */
    public static int digitsSum(final long number) {
        var result = 0;
        for(var tmp = number; tmp > 0; tmp /= 10) {
            var digit = tmp % 10;
            result += digit;
        }
        return result;
    }

    public static <T> void print(final Iterable<T> objects) {
        var iterator = objects.iterator();
        while(iterator.hasNext()) {
            var item = iterator.next();
            System.out.println(item);
        }
    }
}
