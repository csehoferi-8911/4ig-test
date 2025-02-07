package hu.fourig.demo.repository;

import hu.fourig.demo.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByCityContainingIgnoreCase(String city);
}
