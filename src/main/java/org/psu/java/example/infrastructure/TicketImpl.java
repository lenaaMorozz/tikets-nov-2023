package org.psu.java.example.infrastructure;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.psu.java.example.domain.Ticket;
import org.psu.java.example.utils.NumberUtils;

@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TicketImpl implements Ticket {
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    int length;
    long number;
}
