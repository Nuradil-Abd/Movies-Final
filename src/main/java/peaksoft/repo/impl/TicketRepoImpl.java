package peaksoft.repo.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;
import peaksoft.entity.*;
import peaksoft.repo.TicketRepo;

import java.util.List;

@Repository
@Transactional

public class TicketRepoImpl implements TicketRepo {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Ticket saveTicket(Ticket ticket) {
        if (ticket.getId() == null) {
            entityManager.persist(ticket);
            return ticket;
        } else {
            return entityManager.merge(ticket);
        }
    }

    @Override
    public List<Ticket> getAllTickets() {
        return entityManager.createQuery("from Ticket", Ticket.class).getResultList();
    }

    @Override
    public Ticket getTicketById(Long id) {
        return entityManager.find(Ticket.class, id);
    }

    @Override
    public List<Ticket> getTicketsByShowTimeId(Long showTimeId) {
        return entityManager.createQuery(
                        "select t from Ticket t where t.showTime.id = :showTimeId", Ticket.class)
                .setParameter("showTimeId", showTimeId)
                .getResultList();
    }

    @Override
    public List<Ticket> getTicketsByUserId(Long userId) {

        return entityManager.createQuery(
                        "select t from Ticket t where t.user.id = :userId and t.isPurchased ", Ticket.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    @Override
    public void updateTicket(Long id, Ticket updatedTicket) {
        Ticket ticket = getTicketById(id);
        if (ticket != null) {
            ticket.setSeatNumber(updatedTicket.getSeatNumber());
            ticket.setUser(updatedTicket.getUser());
            ticket.setShowTime(updatedTicket.getShowTime());
            entityManager.merge(ticket);
        }
        }

    @Override
    public void deleteTicket(Long id) {
        Ticket ticket = getTicketById(id);
        if (ticket != null) {
            entityManager.remove(ticket);
        }

    }

    public boolean purchaseTickets(List<Long> ticketIds, User user) {

        List<Ticket> tickets = entityManager.createQuery("SELECT t FROM Ticket t WHERE t.id IN :ticketIds", Ticket.class)
                .setParameter("ticketIds", ticketIds)
                .getResultList();

        if (tickets.isEmpty()) {
            return false;
        }

        ShowTime showTime = tickets.get(0).getShowTime();
        double totalPrice = 0;

        for (Ticket ticket : tickets) {
            if (!ticket.isPurchased()) {
                totalPrice += showTime.getPrice();
            }
        }

        Card card = user.getCard();
        if (card == null || card.getBalance() < totalPrice) {
            return false;
        }

        card.setBalance(card.getBalance() - totalPrice);
        entityManager.merge(card);

        boolean allPurchased = true;
        for (Ticket ticket : tickets) {
            if (!ticket.isPurchased()) {
                ticket.setUser(user);
                ticket.setPurchased(true);
                entityManager.merge(ticket);
            } else {
                allPurchased = false;
            }
        }
        return allPurchased;
    }


    public List<Ticket> getAvailableTicketsForShowTime(Long showTimeId) {
        return entityManager.createQuery(
                        "SELECT t FROM Ticket t WHERE t.showTime.id = :showTimeId AND t.isPurchased = false", Ticket.class)
                .setParameter("showTimeId", showTimeId)
                .getResultList();
    }

    @Override
    public void deleteByShowTimeId(Long showTimeId) {
        String hql = "DELETE FROM Ticket t WHERE t.showTime.id = :showTimeId";
        entityManager.createQuery(hql)
                .setParameter("showTimeId", showTimeId)
                .executeUpdate();
    }

    @Override
    public List<Ticket> findShowTimeId(Long showTimeId) {
        String jpql = "SELECT t FROM Ticket t WHERE t.showTime.id = :showTimeId";
        return entityManager.createQuery(jpql, Ticket.class)
                .setParameter("showTimeId", showTimeId)
                .getResultList();
    }


    @Override
    public void createTicket(Long userId, Long showTimeId) {

        User user = entityManager.find(User.class, userId);
        ShowTime showTime = entityManager.find(ShowTime.class, showTimeId);

        if (user == null) {
            throw new IllegalArgumentException("User with ID " + userId + " not found");
        }
        if (showTime == null) {
            throw new IllegalArgumentException("ShowTime with ID " + showTimeId + " not found");
        }
        Hall hall = showTime.getHall();
        List<Ticket> availableTickets = showTime.getTickets();

        if (availableTickets.size() >= hall.getCountOfSeats()) {
            throw new IllegalStateException("No available seats in the hall");
        }
        Ticket ticket = new Ticket();
        ticket.setUser(user);
        ticket.setShowTime(showTime);

        entityManager.persist(ticket);
    }
}



