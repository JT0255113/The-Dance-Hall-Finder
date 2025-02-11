package com.techelevator.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class DanceHall {

    private int danceHallId;

    @NotBlank(message = "The field `Dance Hall Name` should not be blank.")
    private String name;

    @NotBlank(message = "The field `Street Address` should not be blank.")
    private String streetAddress;

    @NotBlank(message = "The field `City` should not be blank.")
    private String city;

    @NotBlank(message = "The field `State` should not be blank.")
    private String state;

    @NotBlank(message = "The field `Zip Code` should not be blank.")
    private String zipCode;

    @NotNull(message = "The field `User Id` should not be null.")
    private Integer userId;

    private String imageUrl;
    private String websiteUrl;

    private String danceStyle;

    public DanceHall() {}

    // Constructor
    public DanceHall(int danceHallId, String name, String streetAddress, String city, String state, String zipCode, String imageUrl, String websiteUrl, String danceStyle) {
        this.danceHallId = danceHallId;
        this.name = name;
        this.streetAddress = streetAddress;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.imageUrl = imageUrl;
        this.websiteUrl = websiteUrl;
        this.danceStyle = danceStyle;
    }

    // Constructor with userId (overloaded)
    public DanceHall(int danceHallId, String name, String streetAddress, String city, String state, String zipCode, Integer userId, String imageUrl, String websiteUrl, String danceStyle) {
        this.danceHallId = danceHallId;
        this.name = name;
        this.streetAddress = streetAddress;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.userId = userId;
        this.imageUrl = imageUrl;
        this.websiteUrl = websiteUrl;
        this.danceStyle = danceStyle;
    }

    // Getters and setters
    public String getDanceStyle() {
        return danceStyle;
    }

    public void setDanceStyle(String danceStyle) {
        this.danceStyle = danceStyle;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public int getDanceHallId() {
        return danceHallId;
    }

    public void setDanceHallId(int danceHallId) {
        this.danceHallId = danceHallId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    @Override
    public String toString() {
        return "DanceHall{" +
                "danceHallId=" + danceHallId +
                ", name='" + name + '\'' +
                ", streetAddress='" + streetAddress + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", userId=" + userId +
                ", imageUrl='" + imageUrl + '\'' +
                ", websiteUrl='" + websiteUrl + '\'' +
                ", danceStyle='" + danceStyle + '\'' +
                '}';
    }
}
