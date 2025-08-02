package com.openclassrooms.dto.response;

import java.math.BigDecimal;
import java.time.Instant;

import com.openclassrooms.model.Rental;
import com.openclassrooms.util.DateUtils;

public class RentalResponse {

  private Integer id;
  private String name;
  private BigDecimal surface;
  private BigDecimal price;
  private String picture;
  private String description;
  private Integer owner_id;
  private String created_at;
  private String updated_at;

  public RentalResponse() {
  }

  public RentalResponse(Rental rental) {
    this.id = rental.getId();
    this.name = rental.getName();
    this.surface = rental.getSurface();
    this.price = rental.getPrice();
    this.picture = rental.getPicture();
    this.description = rental.getDescription();
    this.owner_id = rental.getOwnerId();
    this.created_at = DateUtils.formatDate(rental.getCreatedAt());
    this.updated_at = DateUtils.formatDate(rental.getUpdatedAt());
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public BigDecimal getSurface() {
    return surface;
  }

  public void setSurface(BigDecimal surface) {
    this.surface = surface;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public String getPicture() {
    return picture;
  }

  public void setPicture(String picture) {
    this.picture = picture;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Integer getOwner_id() {
    return owner_id;
  }

  public void setOwner_id(Integer owner_id) {
    this.owner_id = owner_id;
  }

  public String getCreated_at() {
    return created_at;
  }

  public void setCreated_at(String created_at) {
    this.created_at = created_at;
  }

  public String getUpdated_at() {
    return updated_at;
  }

  public void setUpdated_at(String updated_at) {
    this.updated_at = updated_at;
  }

}
