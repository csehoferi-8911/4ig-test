package hu.fourig.demo.model;

import hu.fourig.demo.data.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Base64;

@Entity
@Getter
@Setter
@Table(name = "user_fourig")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_fourig_seq")
    @SequenceGenerator(name = "user_fourig_seq", sequenceName = "user_fourig_seq", allocationSize = 1)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    public void setPassword(String password) {
        this.password = Base64.getEncoder().encodeToString(password.getBytes());
    }

}
