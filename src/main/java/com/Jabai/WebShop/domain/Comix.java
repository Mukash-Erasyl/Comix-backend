package com.Jabai.WebShop.domain;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "comix")
public class Comix {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String _id;

    private String title;
    private String description;
    @ElementCollection
    private List<String> genre;
    private String year;
    private String publisher;

    @Enumerated(EnumType.STRING)
    private Status status;

    private String coverImage;
    @ElementCollection
    private List<String> tags;
    private boolean inTop;
    @ElementCollection
    private List<String> images = new ArrayList<>();
    @ElementCollection
    private List<Languages> translations;
    @ElementCollection
    private List<String> similarComix;

    private int views = 0;

    public Comix() {
        // Пустой конструктор
    }

    public Comix(String title, String description, List<String> genre, String year, String publisher, Status status, String coverImage, List<String> tags, boolean inTop, List<String> images, List<Languages> translations, List<String> similarComix ) {
        this.title = title;
        this.description = description;
        this.genre = genre;
        this.year = year;
        this.publisher = publisher;
        this.status = status;
        this.coverImage = coverImage;
        this.tags = tags;
        this.inTop = inTop;
        this.images = images;
        this.translations = translations;
        this.similarComix = similarComix;

    }

    // Геттеры и сеттеры
    public String getId() {
        return _id;
    }

    public void setId(String id) {
        this._id = String.valueOf(id);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getGenre() {
        return genre;
    }

    public void setGenre(List<String> genre) {
        this.genre = genre;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }



    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public boolean isInTop() {
        return inTop;
    }

    public void setInTop(boolean inTop) {
        this.inTop = inTop;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public List<Languages> getTranslations() {
        return translations;
    }

    public void setTranslations(List<Languages> translations) {
        this.translations = translations;
    }

    public List<String> getSimilarComix() {
        return similarComix;
    }

    public void setSimilarComix(List<String> similarComix) {
        this.similarComix = similarComix;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }
}




