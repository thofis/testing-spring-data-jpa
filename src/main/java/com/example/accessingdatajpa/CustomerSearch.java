package com.example.accessingdatajpa;

import lombok.*;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;
import org.hibernate.annotations.Synchronize;

import javax.persistence.*;

@Entity
@Immutable
//@Subselect(
//  "select c.id id, first_name, last_name, street, city " +
//  "from customer c, address a " +
//  "where c.id = a.customer_id"
//)
//@Synchronize({"customer", "address"})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "v_customer_search")
public class CustomerSearch {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "address_id")
  private Long addressId;
  @Column(name="customer_id")
  private Long customerId;
  private String firstName;
  private String lastName;
  private String street;
  private String city;

}
