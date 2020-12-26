package com.ceruleansource.SmoothieWebsite.backend.Repositories;

import com.ceruleansource.SmoothieWebsite.backend.Models.Smoothie;
import org.springframework.data.repository.CrudRepository;

public interface SmoothieRepository extends CrudRepository<Smoothie, Long> {
}
