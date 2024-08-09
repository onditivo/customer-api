package com.esg.exercise.customer.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

/**
 * Map the CSV rows to an entity and support serialisation to JSON string
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity(name = "customer")
@Getter
@Setter
@ToString
public class Customer {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "customer_ref", nullable = false, unique = true)
    @NotBlank(message = "Customer reference must not be empty")
    @JsonProperty("customer_ref") String customerRef;

    @Column(name = "customer_name")
    @JsonProperty("customer_name") String customerName;

    @Column(name = "address_line_1")
    @JsonProperty("address_line_1") String addressLine1;

    @Column(name = "address_line_2")
    @JsonProperty("address_line_2") String addressLine2;

    @Column(name = "town")
    @JsonProperty("town") String town;

    @Column(name = "county")
    @JsonProperty("county") String county;

    @Column(name = "country")
    @JsonProperty("country") String country;

    @Column(name = "postcode")
    @JsonProperty("postcode") String postcode;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Customer customer = (Customer) o;
        return getCustomerRef() != null && Objects.equals(getCustomerRef(), customer.getCustomerRef());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}

