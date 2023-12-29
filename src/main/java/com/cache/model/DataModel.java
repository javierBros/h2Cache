package com.cache.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class DataModel {
    @Id
    private String name;
    private String countryId;
    private Double probability;

    public void setName(String name) {
        this.name = name;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public void setProbability(Double probability) {
        this.probability = probability;
    }

    public String getName() {
        return name;
    }

    public String getCountryId() {
        return countryId;
    }

    public Double getProbability() {
        return probability;
    }
}
