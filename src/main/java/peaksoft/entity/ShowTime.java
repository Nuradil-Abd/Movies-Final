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
        if (this.tickets == null || this.tickets.isEmpty()) {
            this.tickets = new ArrayList<>();
        }
        int seatCount = hall.getCountOfSeats();
        for (int i = 1; i <= seatCount; i++) {
            Ticket ticket = new Ticket();
            ticket.setSeatNumber(i);
            ticket.setShowTime(this);
            this.tickets.add(ticket);
        }
    }
}
