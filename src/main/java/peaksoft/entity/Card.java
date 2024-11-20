package peaksoft.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Entity
@Table(name = "cards")
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(nullable = false)
    String number;
    @Column(nullable = false)
    Double balance;
    @Column(name = "expired_date",nullable = false)
    LocalDate expiredDate;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
