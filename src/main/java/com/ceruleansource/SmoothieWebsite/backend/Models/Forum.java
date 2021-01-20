package com.ceruleansource.SmoothieWebsite.backend.Models;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Forum {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany
    @JoinColumn(name = "forum_id")
    private Set<Post> posts = new HashSet<>();

    public Forum(){
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Post> getPosts() {
        return posts;
    }

    public void setPosts(Set<Post> posts) {
        this.posts = posts;
    }

    @Override
    public String toString() {
        return "Forum{" +
                "id=" + id +
                ", posts=" + posts +
                '}';
    }
}
