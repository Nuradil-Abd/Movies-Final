package peaksoft.services.impl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import peaksoft.entity.Hall;
import peaksoft.entity.ShowTime;
import peaksoft.entity.Ticket;
import peaksoft.repo.HallRepo;
import peaksoft.services.HallService;

import java.util.List;

@Service
@AllArgsConstructor
public class HallServiceImpl implements HallService {

    private final HallRepo hallRepo;


    public List<Hall> getAllHalls() {
        return hallRepo.findAll();
    }

    public Hall findHallById(Long id) {
        return hallRepo.findById(id);
    }

    public Hall findByName(String name) {
        return hallRepo.findByName(name);
    }

    public List<Hall> getHallsWithShowTimesForMovie(Long movieId,Long cinemaId) {
        return hallRepo.getHallsWithShowTimesForMovie(movieId,cinemaId);
    }

    public Hall saveHall(Hall hall) {
        return hallRepo.save(hall);
    }


    @Transactional
    public void deleteHallById(Long id) {
        hallRepo.deleteById(id);
    }

    @Override
    public List<Hall> findByCinemaId(Long cinemaId) {
        return hallRepo.findByCinemaId(cinemaId);
    }

    @Override
    public Hall createHall(Hall hall) {

        Hall savedHall = hallRepo.save(hall);

        generateTicketsForShowTimes(savedHall);

        return savedHall;
    }

    @Override
    public Hall updateHall(Long hallId, Hall updatedHall) {
        Hall existingHall = hallRepo.findById(hallId);

        existingHall.setName(updatedHall.getName());
        existingHall.setCountOfSeats(updatedHall.getCountOfSeats());


        return hallRepo.save(existingHall);
    }

    @Override
    public List<Hall> getHallsForCinemaAndMovie(Long id, Long movieId) {
        return hallRepo.getHallsForCinemaAndMovie(id, movieId);
    }


    @Transactional
    public void generateTicketsForShowTimes(Hall hall) {

        for (ShowTime showTime : hall.getShowTimes()) {
            int seatCount = hall.getCountOfSeats();
            for (int i = 1; i <= seatCount; i++) {
                Ticket ticket = new Ticket();
                ticket.setSeatNumber(i);
                ticket.setShowTime(showTime);
                showTime.getTickets().add(ticket);
            }
        }
    }
}
