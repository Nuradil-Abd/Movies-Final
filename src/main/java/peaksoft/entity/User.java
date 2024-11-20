package peaksoft.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import peaksoft.enums.Role;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(unique = true, nullable = false)
    String email;
    @Column(nullable = false)
    String password;
    @Column(name = "phone_number",nullable = false)
    String phoneNumber;
    @Enumerated(EnumType.STRING)
    Role role = Role.USER;
    @Column(name = "registration_date")
    LocalDate registrationDate;
    @Column(nullable = false)
    String name;

    @OneToOne( cascade = CascadeType.ALL)
    private Card card;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Ticket> tickets;



    @PrePersist
    public void prePersist() {
        registrationDate = LocalDate.now();
    }

    public User(String email, String password, String phoneNumber,  LocalDate registrationDate, String name) {
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.registrationDate = registrationDate;
        this.name = name;
    }

}
