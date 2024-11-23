package peaksoft.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import peaksoft.entity.Card;
import peaksoft.entity.ShowTime;
import peaksoft.entity.Ticket;
import peaksoft.entity.User;
import peaksoft.repo.CardRepo;
import peaksoft.repo.ShowTimeRepo;
import peaksoft.repo.TicketRepo;
import peaksoft.repo.UserRepo;
import peaksoft.services.TicketService;

import java.util.List;

@Service
@AllArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final TicketRepo ticketRepo;
    private final UserRepo userRepo;
    private final CardRepo cardRepo;
    private final ShowTimeRepo showTimeRepo;


    @Override
    public void createTicket(Long userId, Long showTimeId) {
        ticketRepo.createTicket(userId,showTimeId);
    }

    @Override
    public Ticket saveTicket(Ticket ticket) {
        return ticketRepo.saveTicket(ticket);
    }

    @Override
    public List<Ticket> getAllTickets() {
        return ticketRepo.getAllTickets();
    }

    @Override
    public Ticket getTicketById(Long id) {
        return ticketRepo.getTicketById(id);
    }

    @Override
    public List<Ticket> getTicketsByShowTimeId(Long showTimeId) {
        return ticketRepo.getTicketsByShowTimeId(showTimeId);
    }

    @Override
    public List<Ticket> getTicketsByUserId(Long userId) {
        return ticketRepo.getTicketsByUserId(userId);
    }

    @Override
    public void updateTicket(Long id, Ticket updatedTicket) {
            ticketRepo.updateTicket(id, updatedTicket);
    }

    @Override
    public void deleteTicket(Long id) {
            ticketRepo.deleteTicket(id);
    }

    @Override
    public boolean purchaseTickets(List<Long> ticketIds, User user) {
        return ticketRepo.purchaseTickets(ticketIds,user);
    }

    @Override
    public List<Ticket> getAvailableTicketsForShowTime(Long showTimeId) {
        return ticketRepo.getAvailableTicketsForShowTime(showTimeId);
    }

    @Override
    public void deleteByShowTimeId(Long showTimeId) {
        ticketRepo.deleteByShowTimeId(showTimeId);
    }

    @Override
    public List<Ticket> findByShowTimeId(Long showTimeId) {
        return ticketRepo.findShowTimeId(showTimeId);
    }

}
