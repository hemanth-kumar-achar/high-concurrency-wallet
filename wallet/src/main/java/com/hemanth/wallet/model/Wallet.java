package com.hemanth.wallet.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "wallets")
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String userId;

    private BigDecimal balance;

    // This field handles concurrency! 
    // If two threads try to update the same wallet at the exact same time, 
    // the database will reject the second one.
    @Version
    private Long version; 
}