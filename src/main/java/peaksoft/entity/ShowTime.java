package peaksoft.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "show_time")
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

public class ShowTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "start_time")
    Time startTime;
    @Column(nullable = false)
    double price;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne()
    @JoinColumn(name = "hall_id")
    private Hall hall;

    @OneToMany(mappedBy = "showTime", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Ticket> tickets = new ArrayList<>();



    @PrePersist
    public void generateTicketsForShowTime() {
//        if (this.tickets == null || this.tickets.isEmpty()) {
//            this.tickets = new ArrayList<>();
//        }
//        int seatCount = hall.getCountOfSeats();
//        int seatsPerRow = 10;
//        int rowNumber = 1;
//
//        for (int i = 1; i <= seatCount; i++) {
//            if ((i - 1) % seatsPerRow == 0 && i > 1) {
//                rowNumber++;
//            }
//            Ticket ticket = new Ticket();
//            ticket.setSeatNumber((i - 1) % seatsPerRow + 1);
//            ticket.setRowNumber(rowNumber);
//            ticket.setShowTime(this);
//            this.tickets.add(ticket);
//        }
        if (this.tickets == null || this.tickets.isEmpty()) {
            this.tickets = new ArrayList<>();
        }
        int rowCount = 12;
        int seatsPerRow = hall.getCountOfSeats() / rowCount;
        for (int i = 0; i < rowCount; i++) {
            for (int j = 1; j <= seatsPerRow; j++) {
                Ticket ticket = new Ticket();
                ticket.setRowNumber(i + 1);
                ticket.setSeatNumber(j);
                ticket.setShowTime(this);
                this.tickets.add(ticket);
            }
        }
    }
}
