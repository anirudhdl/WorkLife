package com.employment.WorkLife.DTO;

public class AddressDTO {
    private Integer addressId;
    private String street;
    private String city;
    private String state;
    private String Postal_code;

    public AddressDTO(Integer addressId, String street, String city, String state, String postal_code) {
        this.addressId = addressId;
        this.street = street;
        this.city = city;
        this.state = state;
        Postal_code = postal_code;
    }

    public AddressDTO() {
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
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

    public String getPostal_code() {
        return Postal_code;
    }

    public void setPostal_code(String postal_code) {
        this.Postal_code = postal_code;
    }
}
