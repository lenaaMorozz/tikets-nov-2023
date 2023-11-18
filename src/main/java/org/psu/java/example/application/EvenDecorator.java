package org.psu.java.example.application;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.psu.java.example.domain.Ticket;

/**
 * Добавляет проверку чётности номера
 */
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EvenDecorator implements Ticket {
    Ticket wrapee;

    @Override
    public boolean isFortunate() {
        return wrapee.getNumber() % 2 == 0 && wrapee.isFortunate();
    }

    @Override
    public int getLength() {
        return wrapee.getLength();
    }

    @Override
    public long getNumber() {
        return wrapee.getNumber();
    }
}
