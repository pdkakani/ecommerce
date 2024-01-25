package com.test.ecomdemo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "watch")
@Getter
@Setter
public class Watch {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private double price;

    @Column(name = "discountOnPackOf")
    private int discountOnPackOf;

    @Column(name = "discountPrice")
    private double discountPrice;

}
