package com.openclassrooms.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.openclassrooms.dto.request.RentalRequest;
import com.openclassrooms.dto.request.RentalUpdateRequest;
import com.openclassrooms.dto.response.RentalListResponse;
import com.openclassrooms.dto.response.RentalResponse;
import com.openclassrooms.mapper.RentalReponseMapper;
import com.openclassrooms.mapper.RentalRequestMapper;
import com.openclassrooms.model.Rental;
import com.openclassrooms.repository.RentalRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class RentalService {

  @Autowired
  private RentalRepository rentalRepository;

  @Autowired
  RentalReponseMapper rentalReponseMapper;

  @Autowired
  RentalRequestMapper rentalRequestMapper;

  @Value("${app.upload-dir}")
  private String uploadDir;

  @Value("${app.base-url}")
  private String baseUrl;

  /**
   * Return a rental by id if it exists
   *
   * @param id the rental id
   * @return RentalResponse
   * @throws org.springframework.web.server.ResponseStatusException if not found
   */
  public RentalResponse getRental(final Integer id) {
    Rental rental = rentalRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "error"));
    RentalResponse rentalResponse = rentalReponseMapper.toResponse(rental);
    return rentalResponse;
  }

  /**
   * Return all existing rentals
   *
   * @return RentalListResponse with all existing rentals
   */
  public RentalListResponse getRentals() {
    Iterable<Rental> rentals = rentalRepository.findAll();
    List<RentalResponse> rentalResponses = rentalReponseMapper.toResponseList(rentals);
    return new RentalListResponse(rentalResponses);
  }

  /**
   * Create a new rental in database
   *
   * @param request a DTO object with the rental’s data
   * @param picture the uploaded rental image
   * @param userId  the current authenticated user id
   * @return void
   * @throws org.springframework.web.server.ResponseStatusException if the picture can’t be saved
   */
  @Transactional // Rollback if an exception occurs
  public void createRental(RentalRequest request, MultipartFile picture, Integer userId) {
    Rental rental = rentalRequestMapper.toEntity(request);
    rental.setOwnerId(userId);
    Rental savedRental = rentalRepository.save(rental);

    String path;
    try {
      path = saveImageAndGetPath(savedRental.getId(), picture);
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "error");
    }

    savedRental.setPicture(path);
    rentalRepository.save(savedRental);
  }

  /**
   * Update an existing rental in database
   *
   * @param request  a DTO object with the provided new data
   * @param userId   the ID of current authenticated user
   * @param rentalId the ID of the target rental
   * @return void
   * @throws org.springframework.web.server.ResponseStatusException if unauthorized or data is invalid
   */
  public void updateRental(RentalUpdateRequest request, Integer userId, Integer rentalId) {

    Rental rental = rentalRepository.findById(rentalId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Logement introuvable"));

    // Only the owner can modify a rental
    if (!rental.getOwnerId().equals(userId)) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, "error");
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

    rentalRepository.save(rental);
  }

  /**
   * Save an uploaded image, and return the related url
   *
   * @param rentalId the database ID of the associated rental
   * @param picture  the multipart uploaded image
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
