package peaksoft.repo;

import peaksoft.entity.Ticket;
import peaksoft.entity.User;

import java.util.List;

public interface TicketRepo {


    Ticket saveTicket(Ticket ticket);
    List<Ticket> getAllTickets();
    Ticket getTicketById(Long id);
    List<Ticket> getTicketsByShowTimeId(Long showTimeId);
    List<Ticket> getTicketsByUserId(Long userId);
    void updateTicket(Long id, Ticket updatedTicket);
    void deleteTicket(Long id);
    void purchaseTicket(Long ticketId, User user);
    void createTicket(Long userId, Long showTimeId);

}
