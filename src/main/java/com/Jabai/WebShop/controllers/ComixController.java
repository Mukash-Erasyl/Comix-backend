package com.Jabai.WebShop.controllers;

import com.Jabai.WebShop.domain.Comix;
import com.Jabai.WebShop.domain.Languages;
import com.Jabai.WebShop.service.ComixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.Jabai.WebShop.domain.Status;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Sort;


@CrossOrigin(origins = "https://comix-frontend.herokuapp.com")
@RestController
@RequestMapping("/api/comix")
public class ComixController {

    private final ComixService comixService;


    @Autowired
    public ComixController(ComixService comixService) {
        this.comixService = comixService;
    }

    @GetMapping
    public List<Comix> getAllComix() {
        return comixService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comix> getComixById(@PathVariable String id) {
        Optional<Comix> comix = comixService.findById(id);
        return comix.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<String> createComix(@RequestBody Comix comix) {
        Comix createdComix = comixService.save(comix);
        String message = String.format("Комикс с id %s был создан.", createdComix.getId());
        return ResponseEntity.ok(message);
    }


    @PutMapping("/{id}")
    public ResponseEntity<String> updateComix(@PathVariable String id, @RequestBody Comix comixDetails) {
        Optional<Comix> comixOptional = comixService.findById(id);
        if (comixOptional.isPresent()) {
            Comix comix = comixOptional.get();
            comix.setTitle(comixDetails.getTitle());
            comix.setDescription(comixDetails.getDescription());
            comix.setGenre(comixDetails.getGenre());
            comix.setYear(comixDetails.getYear());
            comix.setPublisher(comixDetails.getPublisher());
            comix.setStatus(comixDetails.getStatus());
            comix.setCoverImage(comixDetails.getCoverImage());
            comix.setTags(comixDetails.getTags());
            comix.setInTop(comixDetails.isInTop());
            comix.setImages(comixDetails.getImages());
            comix.setTranslations(comixDetails.getTranslations());
            comix.setSimilarComix(comixDetails.getSimilarComix());
            Comix updatedComix = comixService.save(comix);
            String message = String.format("Комикс с id %s был обновлен.", id);
            return ResponseEntity.ok(message);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteComix(@PathVariable String id) {
        Optional<Comix> comixOptional = comixService.findById(id);
        if (comixOptional.isPresent()) {
            comixService.deleteById(id);
            return ResponseEntity.ok("Комикс с id - " + id + " был удален");
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/search")
    public List<Comix> searchComix(@RequestParam String keyword) {
        return comixService.searchByKeyword(keyword);
    }

    @GetMapping("/page")
    public Page<Comix> getComixByPage(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "4") int pageSize) {
        // Создаем объект Pageable для запроса страницы
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("title").ascending());
        // Получаем страницу комиксов с учетом номера страницы и размера страницы
        return comixService.findComixByPage(pageable);
    }

    @PatchMapping("/{id}/views")
    public ResponseEntity<String> updateComixViews(@PathVariable String id, @RequestBody int views) {
        Optional<Comix> comixOptional = comixService.findById(id);
        if (comixOptional.isPresent()) {
            Comix comix = comixOptional.get();
            comix.setViews(views);
            comixService.save(comix);
            String message = String.format("Views for comix with id %s were updated to %d.", id, views);
            return ResponseEntity.ok(message);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @Value("${upload.path}")
    private String uploadPath;

    @PostMapping("/uploadCover")
    public String uploadCoverImage(@RequestParam("file") MultipartFile file, @RequestParam("comixId") String comixId) throws IOException {
        if (file.isEmpty()) {
            return "File is empty";
        }

        // Получаем комикс по его ID
        Optional<Comix> optionalComix = comixService.findById(comixId);
        if (optionalComix.isPresent()) {
            Comix comix = optionalComix.get();

            // Получаем название комикса и убираем пробелы
            String comixName = comix.getTitle().replaceAll("\\s+", "");

            // Создаем папку для комикса, если ее не существует
            Path comixDirectory = Paths.get(uploadPath + File.separator + comixName);
            if (!Files.exists(comixDirectory)) {
                Files.createDirectory(comixDirectory);
            }

            // Копируем файл обложки в папку комикса
            String filename = file.getOriginalFilename();
            Path path = Paths.get(comixDirectory.toString(), filename);
            Files.copy(file.getInputStream(), path);

            // Обновляем путь к обложке комикса и сохраняем комикс
            comix.setCoverImage(path.toString());
            comixService.save(comix);

            return path.toString();
        } else {
            return "Comix not found";
        }
    }

    @PostMapping("/uploadImages")
    public List<String> uploadImages(@RequestParam("files") MultipartFile[] files, @RequestParam("comixId") String comixId) throws IOException {
        List<String> paths = new ArrayList<>();

        // Получаем комикс по его ID
        Optional<Comix> optionalComix = comixService.findById(comixId);
        if (optionalComix.isPresent()) {
            Comix comix = optionalComix.get();

            // Получаем название комикса и убираем пробелы
            String comixName = comix.getTitle().replaceAll("\\s+", "");

            // Создаем папку для комикса, если ее не существует
            Path comixDirectory = Paths.get(uploadPath + File.separator + comixName);
            if (!Files.exists(comixDirectory)) {
                Files.createDirectory(comixDirectory);
            }

            // Копируем все файлы изображений в папку комикса
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    String filename = file.getOriginalFilename();
                    Path imagePath = Paths.get(comixDirectory.toString(), filename);
                    Files.copy(file.getInputStream(), imagePath);
                    paths.add(imagePath.toString());
                }
            }

            // Обновляем список путей изображений комикса и сохраняем комикс
            comix.setImages(paths);
            comixService.save(comix);
        } else {
            paths = List.of("Comix not found");
        }

        return paths;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/createComix")
    public String createComix(@RequestParam("file") MultipartFile coverImage,
                              @RequestParam("files") MultipartFile[] images,
                              @RequestParam("title") String title,
                              @RequestParam("description") String description,
                              @RequestParam("genre") List<String> genre,
                              @RequestParam("year") String year,
                              @RequestParam("publisher") String publisher,
                              @RequestParam("status") Status status,
                              @RequestParam("tags") List<String> tags,
                              @RequestParam("inTop") boolean inTop,
                              @RequestParam("translations") List<Languages> translations,
                              @RequestParam("similarComix") List<String> similarComix) throws IOException {

        // Убираем пробелы из названия комикса
        String sanitizedTitle = title.replaceAll("\\s+", "");

        // Создаем новый комикс
        Comix comix = new Comix(sanitizedTitle, description, genre, year, publisher, status, "", tags, inTop, new ArrayList<>(), translations, similarComix);

        // Создаем папку для комикса, если ее не существует
        Path comixDirectory = Paths.get(uploadPath + File.separator + sanitizedTitle);
        if (!Files.exists(comixDirectory)) {
            Files.createDirectory(comixDirectory);
        }

        // Сохраняем обложку комикса
        if (!coverImage.isEmpty()) {
            String coverFilename = coverImage.getOriginalFilename();
            Path coverPath = Paths.get(comixDirectory.toString(), coverFilename);
            Files.copy(coverImage.getInputStream(), coverPath);
            comix.setCoverImage(coverPath.toString());
        }

        // Сохраняем изображения комикса
        List<String> imagePaths = new ArrayList<>();
        for (MultipartFile image : images) {
            if (!image.isEmpty()) {
                String imageFilename = image.getOriginalFilename();
                Path imagePath = Paths.get(comixDirectory.toString(), imageFilename);
                Files.copy(image.getInputStream(), imagePath);
                imagePaths.add(imagePath.toString());
            }
        }
        comix.setImages(imagePaths);

        // Сохраняем комикс
        comixService.save(comix);

        return "Comix created successfully";
    }

}