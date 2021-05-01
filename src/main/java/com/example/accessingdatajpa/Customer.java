package com.example.accessingdatajpa;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Customer {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String firstName;
  private String lastName;


  @OneToMany(cascade = ALL, orphanRemoval = true)
  @JoinColumn(name="customer_id")
  private Set<Address> addresses = new HashSet<>();

}
