
package peaksoft.entity;

import java.util.ArrayList;
import java.util.List;

public class TicketGenerator {

    public static List<Ticket> generateTicketsForShowTime(ShowTime showTime) {
        List<Ticket> tickets = new ArrayList<>();
        int seatCount = showTime.getHall().getCountOfSeats();
        for (int i = 1; i <= seatCount; i++) {
            Ticket ticket = new Ticket();
            ticket.setSeatNumber(i);  
            ticket.setShowTime(showTime);
            tickets.add(ticket);
        }
        return tickets;
    }
}