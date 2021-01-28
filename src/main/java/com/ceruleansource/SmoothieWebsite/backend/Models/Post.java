package com.ceruleansource.SmoothieWebsite.backend.Models;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Length(min = 1, max = 32)
    private String title;

    private String description;

    private LocalDateTime dateTime;

    private String postImageName;

    @Lob
    @Basic
    @Nullable
    private byte[] postImage;

    @OneToOne
    @MapsId
    private Smoothie smoothie;

    public Post(){
    }

    public Post(String title, String description, LocalDateTime dateTime, byte[] postImage, Smoothie smoothie) {
        this.title = title;
        this.description = description;
        this.dateTime = dateTime;
        this.postImage = postImage;
        this.smoothie = smoothie;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    /**
     *
     * @return - The date in the following format: "Month dayOfMonth, year"
     * Example: January 27, 2020
     */
    public String getDateString(){
        return dateTime.getMonth().name().substring(0, 1) + dateTime.getMonth().name().substring(1).toLowerCase() + " " + dateTime.getDayOfMonth() + ", " + dateTime.getYear();
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getPostImageName() {
        return postImageName;
    }

    public void setPostImageName(String postImageName) {
        this.postImageName = postImageName;
    }

    public byte[] getPostImage() {
        return postImage;
    }

    public void setPostImage(byte[] postImage) {
        this.postImage = postImage;
    }

    public Smoothie getSmoothie() {
        return smoothie;
    }

    public void setSmoothie(Smoothie smoothie) {
        this.smoothie = smoothie;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", dateTime=" + dateTime +
                ", smoothie=" + smoothie.getName() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return Objects.equals(id, post.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
