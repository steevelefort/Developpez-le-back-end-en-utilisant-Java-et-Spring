package com.openclassrooms.service;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.openclassrooms.dto.request.RentalRequest;
import com.openclassrooms.dto.request.RentalUpdateRequest;
import com.openclassrooms.dto.response.RentalResponse;
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

  public RentalResponse getRental(final Integer id) {
    Rental rental = rentalRepository.findById(id).orElseThrow(() -> new RuntimeException("Location introuvable"));
    return new RentalResponse(rental);
  }

  public Iterable<Rental> getRentals() {
    return rentalRepository.findAll();
  }

  public void deleteRental(final Integer id) {
    rentalRepository.deleteById(id);
  }

  // public Rental saveRental(final Rental rental) {
  public Rental saveRental(final Rental rental) {
    return rentalRepository.save(rental);
  }

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

    // String extension = "." +
    // StringUtils.getFilenameExtension(picture.getOriginalFilename());
    // Path imagePath = Paths.get(uploadDir, savedRental.getId() + extension);
    // Files.createDirectories(Paths.get(uploadDir)); // Can throw an IOException
    // picture.transferTo(imagePath);
    String path = saveImageAndGetPath(savedRental.getId(), picture);

    // savedRental.setPicture(baseUrl + "/images/"+
    // savedRental.getId().toString()+extension);
    savedRental.setPicture(path);
    rentalRepository.save(savedRental);

    return true;
  }

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

    // if (!picture.isEmpty()) {
    //   // Delete the previous picture before saving the new one
    //   String previousFilename = getFileNameFromUrl(rental.getPicture());
    //   Path previousImagePath = Paths.get(uploadDir, previousFilename);
    //   Files.deleteIfExists(previousImagePath);
    //
    //   // Upload the new picture
    //   String path = saveImageAndGetPath(rental.getId(), picture);
    //   rental.setPicture(path);
    // }

    rentalRepository.save(rental);

    return true;
  }

  private String saveImageAndGetPath(Integer rentalId, MultipartFile picture) throws Exception {
    String extension = "." + StringUtils.getFilenameExtension(picture.getOriginalFilename());
    Path imagePath = Paths.get(uploadDir, rentalId + extension);
    Files.createDirectories(Paths.get(uploadDir)); // Can throw an IOException
    picture.transferTo(imagePath);

    return baseUrl + "/images/" + rentalId + extension;
  }

  // private String getFileNameFromUrl(String url) {
  //   URI uri = URI.create(url);
  //   return Paths.get(uri.getPath()).getFileName().toString();
  // }

}
