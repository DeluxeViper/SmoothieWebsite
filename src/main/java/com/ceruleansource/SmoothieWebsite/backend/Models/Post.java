package com.ceruleansource.SmoothieWebsite.backend.Models;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"title", "smoothie_id"})
})
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    private String description;

    private LocalDateTime dateTime;

    @Lob
    @Basic
    private byte[] postImage;

    @OneToOne
    @JoinColumn(name = "smoothie_id")
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

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
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
                ", postImage=" + Arrays.toString(postImage) +
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
