package hu.fourig.demo.model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Partner implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "partner_seq")
    @SequenceGenerator(name = "partner_seq", sequenceName = "partner_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String email;
    private String phone;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(
            name = "PARTNER_ADDRESS",
            joinColumns = @JoinColumn(name = "PARTNER_ID", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "ADDRESS_ID", nullable = false))
    private List<Address> addresses = new ArrayList<>();
}
