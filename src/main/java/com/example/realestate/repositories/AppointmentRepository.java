package com.example.realestate.repositories;

import com.example.realestate.models.Appointment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findByClientId(String clientId);

    List<Appointment> findByAgentId(String agentId);

    boolean existsByAgentIdAndDateAndTime(String agentId,String date, String time);

}