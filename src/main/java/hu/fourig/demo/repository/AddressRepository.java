package hu.fourig.demo.repository;

import hu.fourig.demo.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    @Query(value = """
        SELECT a.id, a.street, a.city, a.zip_code, a.number,
               p.id, p.name, p.email, p.phone
        FROM Address a
        JOIN PARTNER_ADDRESS pa ON a.id = pa.ADDRESS_ID
        JOIN Partner p ON p.id = pa.PARTNER_ID
        WHERE (:street IS NULL OR LOWER(a.street) LIKE LOWER(CONCAT('%', :street, '%')))
        AND (:city IS NULL OR LOWER(a.city) LIKE LOWER(CONCAT('%', :city, '%')))
        AND (:zipCode IS NULL OR a.zip_code = :zipCode)
        AND (:number IS NULL OR a.number LIKE CONCAT('%', :number, '%'))
    """, nativeQuery = true)
    List<Object[]> searchAddressesWithPartners(
            @Param("street") String street,
            @Param("city") String city,
            @Param("zipCode") String zipCode,
            @Param("number") String number
    );
}
