package hu.fourig.demo.service.search;

import hu.fourig.demo.model.Address;
import hu.fourig.demo.model.Partner;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class PartnerSpecification {

    public static Specification<Partner> hasNameContaining(String name) {
        return (root, query, criteriaBuilder) ->
                name == null || name.isEmpty()
                        ? criteriaBuilder.conjunction()
                        : criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<Partner> hasEmailContaining(String email) {
        return (root, query, criteriaBuilder) ->
                email == null || email.isEmpty()
                        ? criteriaBuilder.conjunction()
                        : criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), "%" + email.toLowerCase() + "%");
    }

    public static Specification<Partner> hasPhoneContaining(String phone) {
        return (root, query, criteriaBuilder) ->
                phone == null || phone.isEmpty()
                        ? criteriaBuilder.conjunction()
                        : criteriaBuilder.like(criteriaBuilder.lower(root.get("phone")), "%" + phone.toLowerCase() + "%");
    }

    public static Specification<Partner> hasAddressContaining(String street, String city, String zipCode, String number) {
        return (root, query, criteriaBuilder) -> {
            Join<Partner, Address> addressJoin = root.join("addresses", JoinType.LEFT);

            Predicate predicate = criteriaBuilder.conjunction();

            if (street != null && !street.isEmpty()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.lower(addressJoin.get("street")), "%" + street.toLowerCase() + "%"));
            }
            if (city != null && !city.isEmpty()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.lower(addressJoin.get("city")), "%" + city.toLowerCase() + "%"));
            }
            if (zipCode != null && !zipCode.isEmpty()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.lower(addressJoin.get("zipCode")), "%" + zipCode.toLowerCase() + "%"));
            }
            if (number != null && !number.isEmpty()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.lower(addressJoin.get("number")), "%" + number.toLowerCase() + "%"));
            }

            return predicate;
        };
    }
}

