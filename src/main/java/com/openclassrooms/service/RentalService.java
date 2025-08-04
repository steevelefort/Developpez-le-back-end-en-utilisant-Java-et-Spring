package com.openclassrooms.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.openclassrooms.dto.request.RentalRequest;
import com.openclassrooms.dto.request.RentalUpdateRequest;
import com.openclassrooms.model.Rental;
import com.openclassrooms.repository.RentalRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class RentalService {

  @Autowired
  private RentalRepository rentalRepository;

  @Value("${app.upload-dir}")
  private String uploadDir;

  @Value("${app.base-url}")
  private String baseUrl;

  /**
   * Return a rental by id if it exists
   *
   * @param id the rental id
   * @return Rental
   * @throws Exception if not found
   */
  public Rental getRental(final Integer id) {
    Rental rental = rentalRepository.findById(id).orElseThrow(() -> new RuntimeException("Location introuvable"));
    return rental;
    // return new RentalResponse(rental);
  }

  /**
   * Return all existing rentals
   *
   * @return Iterable<Rental>
   */
  public Iterable<Rental> getRentals() {
    return rentalRepository.findAll();
  }

  /**
   * Create a new rental in database
   *
   * @param request a DTO object with the rental’s data
   * @param picture the uploaded rental image
   * @param userId the current authenticated user id
   * @return boolean true in case of success
   * @throws Exception if the picture can’t be saved
   */
  public boolean createRental(RentalRequest request, MultipartFile picture, Integer userId) throws Exception {

    Rental rental = new Rental();
    rental.setName(request.getName());
    rental.setDescription(request.getDescription());
    rental.setSurface(request.getSurface());
    rental.setPrice(request.getPrice());
    rental.setOwnerId(userId);
    rental.setPicture("");
    rental.setCreatedAt(Instant.now());
    rental.setUpdatedAt(Instant.now());

    Rental savedRental = rentalRepository.save(rental);

    String path = saveImageAndGetPath(savedRental.getId(), picture);

    savedRental.setPicture(path);
    rentalRepository.save(savedRental);

    return true;
  }

  /**
   * Update an existing rental in database
   *
   * @param request a DTO object with the provided new data
   * @param userId the ID of current authenticated user
   * @param rentalId the ID of the target rental
   * @return boolean if success
   * @throws Exception if the given rental doesn’t exist
   */
  public boolean updateRental(RentalUpdateRequest request, Integer userId, Integer rentalId)
      throws Exception {

    Rental rental = rentalRepository.findById(rentalId)
        .orElseThrow(() -> new EntityNotFoundException("Logement introuvable"));

    // Only the owner can modify a rental
    if (!rental.getOwnerId().equals(userId)) {
      throw new SecurityException("Forbidden");
    }

    if (request.getName() != null) {
      rental.setName(request.getName());
    }
    if (request.getDescription() != null) {
      rental.setDescription(request.getDescription());
    }
    if (request.getSurface() != null) {
      rental.setSurface(request.getSurface());
    }
    if (request.getPrice() != null) {
      rental.setPrice(request.getPrice());
    }

    rental.setUpdatedAt(Instant.now());

    rentalRepository.save(rental);

    return true;
  }

  /**
   * Save an uploaded image, and return the related url
   *
   * @param rentalId the database ID of the associated rental
   * @param picture the multipart uploaded image
   * @return String the public url
   * @throws Exception if the upload target directory can’t be found or write
   */
  private String saveImageAndGetPath(Integer rentalId, MultipartFile picture) throws Exception {
    String extension = "." + StringUtils.getFilenameExtension(picture.getOriginalFilename());
    Path imagePath = Paths.get(uploadDir, rentalId + extension);
    Files.createDirectories(Paths.get(uploadDir)); // Can throw an IOException
    picture.transferTo(imagePath);

    return baseUrl + "/images/" + rentalId + extension;
  }
}
