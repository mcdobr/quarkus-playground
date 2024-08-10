package me.mcdobr.customer;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

/**
 * Active record pattern is a bit strange (first impression).
 */
@Entity
public class Customer extends PanacheEntity {
    public String name;
}
