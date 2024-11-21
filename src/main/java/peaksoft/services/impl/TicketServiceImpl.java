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
//    public boolean purchaseTickets(List<Long> ticketIds, User user) {
//        // Получаем список билетов по их id
//        List<Ticket> tickets = ticketRepository.findAllById(ticketIds);
//
//        // Получаем id сеанса из первого билета
//        Long showTimeId = tickets.get(0).getShowTime().getId();
//        ShowTime showTime = showTimeRepo.findById(showTimeId)
//
//        double totalPrice = tickets.stream()
//                .filter(ticket -> !ticket.isPurchased())  // Считаем только доступные билеты
//                .mapToDouble(ticket -> showTime.getPrice())
//                .sum();
//
//
//        Card card = user.getCard();
//        if (card == null || card.getBalance() < totalPrice) {
//            return false;
//        }
//
//
//        card.setBalance(card.getBalance() - totalPrice);
//        cardRepo.save(card);
//
//        for (Ticket ticket : tickets) {
//            if (!ticket.isPurchased()) {
//                ticket.setPurchased(true);
//                ticket.setUser(user);
//                ticketRepo.saveTicket(ticket);
//            }
//        }
//
//        return true;  // Успешная покупка
//    }
}
