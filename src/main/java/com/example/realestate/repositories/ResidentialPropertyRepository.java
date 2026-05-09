package com.example.realestate.repositories;

import com.example.realestate.models.ResidentialProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResidentialPropertyRepository extends JpaRepository<ResidentialProperty, Long> {
}
