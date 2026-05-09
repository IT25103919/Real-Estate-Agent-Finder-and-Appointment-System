package com.example.realestate.repositories;

import com.example.realestate.models.CommercialProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommercialPropertyRepository extends JpaRepository<CommercialProperty, Long> {
}
