package hu.fourig.demo.model;

import hu.fourig.demo.data.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Base64;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "user_fourig")
public class User implements UserDetails {

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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(() -> "ROLE_" + role.name());
    }

    public void setPassword(String password) {
        this.password = Base64.getEncoder().encodeToString(password.getBytes());
    }

    public boolean checkPassword(String rawPassword) {
        return new String(Base64.getDecoder().decode(this.password)).equals(rawPassword);
    }
}
