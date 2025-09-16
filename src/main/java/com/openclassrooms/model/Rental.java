package com.openclassrooms.model;

import java.math.BigDecimal;
import java.time.Instant;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Rental entity for property rentals
 */
@Entity
@Table(name = "RENTALS")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Rental {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String name;

  private BigDecimal surface;

  private BigDecimal price;

  private String picture;

  private String description;

  @Column(name = "owner_id", nullable = false)
  private Integer ownerId;

  @CreationTimestamp
  @Column(name = "created_at", updatable = false)
  private Instant createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at")
  private Instant updatedAt;

  @ManyToOne(fetch = FetchType.LAZY)
  // insertable/updatable = false to prevent Hibernate to write this field
  @JoinColumn(name = "owner_id", nullable = false, insertable = false, updatable = false)
  private AppUser owner;

}
