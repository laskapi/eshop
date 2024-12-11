package com.laskapi.eshop.productservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Category {

    @Builder
    public Category(String name, long parent_id) {
        this.name = name;
        this.parent_id = parent_id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(unique=true)
    private String name;

    @Column(nullable = false)
    private long parent_id;
}
