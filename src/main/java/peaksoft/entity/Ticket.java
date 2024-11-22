package peaksoft.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "tickets")
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "seat_number", nullable = false)
    int seatNumber;

    @Column(name = "row_number")
    private Integer rowNumber;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne()
    @JoinColumn(name = "show_time_id", nullable = false)
    private ShowTime showTime;



    @Column(nullable = false)
    private boolean isPurchased = false; // Статус билета

    @PrePersist
    public void prePersist() {
        if (isPurchased) {
            throw new IllegalStateException("Ticket is already purchased!");
        }
    }
}
