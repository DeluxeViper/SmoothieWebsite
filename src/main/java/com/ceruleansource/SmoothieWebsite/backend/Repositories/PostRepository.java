package com.ceruleansource.SmoothieWebsite.backend.Repositories;

import com.ceruleansource.SmoothieWebsite.backend.Models.Post;
import com.ceruleansource.SmoothieWebsite.backend.Models.Smoothie;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface PostRepository extends PagingAndSortingRepository<Post, Long> {
    Set<Post> findByTitleAndSmoothie(String title, Smoothie smoothie);

    Optional<ArrayList<Post>> findAllByTitleContaining(String title);
 }
