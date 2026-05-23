package com.example.realestate.models;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDate;

@Entity
@Table(name = "properties")
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // The agent who listed this property
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "agent_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "password", "photo"})
    private Agent agent;

    private String title;

    @Enumerated(EnumType.STRING)
    private PropertyType propertyType; // RESIDENTIAL or COMMERCIAL

    // RESIDENTIAL fields
    private Integer bedrooms;
    private Integer bathrooms;

    // COMMERCIAL field
    private Double squareFeet;

    // Location — Sri Lankan district
    private String district;

    private Double price;

    // Property status
    @Enumerated(EnumType.STRING)
    private PropertyStatus status = PropertyStatus.AVAILABLE;

    private LocalDate addedDate = LocalDate.now();

    // 4 property photos stored as Base64 strings
    @Column(columnDefinition = "LONGTEXT")
    private String photo1;

    @Column(columnDefinition = "LONGTEXT")
    private String photo2;

    @Column(columnDefinition = "LONGTEXT")
    private String photo3;

    @Column(columnDefinition = "LONGTEXT")
    private String photo4;

    public enum PropertyType { RESIDENTIAL, COMMERCIAL }
    public enum PropertyStatus { AVAILABLE, SOLD }

    public Property() {}

    // Getters and Setters
    public Long getId() { return id; }

    public Agent getAgent() { return agent; }
    public void setAgent(Agent agent) { this.agent = agent; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public PropertyType getPropertyType() { return propertyType; }
    public void setPropertyType(PropertyType propertyType) { this.propertyType = propertyType; }

    public Integer getBedrooms() { return bedrooms; }
    public void setBedrooms(Integer bedrooms) { this.bedrooms = bedrooms; }

    public Integer getBathrooms() { return bathrooms; }
    public void setBathrooms(Integer bathrooms) { this.bathrooms = bathrooms; }

    public Double getSquareFeet() { return squareFeet; }
    public void setSquareFeet(Double squareFeet) { this.squareFeet = squareFeet; }

    public String getDistrict() { return district; }
    public void setDistrict(String district) { this.district = district; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public PropertyStatus getStatus() { return status; }
    public void setStatus(PropertyStatus status) { this.status = status; }

    public LocalDate getAddedDate() { return addedDate; }
    public void setAddedDate(LocalDate addedDate) { this.addedDate = addedDate; }

    public String getPhoto1() { return photo1; }
    public void setPhoto1(String photo1) { this.photo1 = photo1; }

    public String getPhoto2() { return photo2; }
    public void setPhoto2(String photo2) { this.photo2 = photo2; }

    public String getPhoto3() { return photo3; }
    public void setPhoto3(String photo3) { this.photo3 = photo3; }

    public String getPhoto4() { return photo4; }
    public void setPhoto4(String photo4) { this.photo4 = photo4; }
}