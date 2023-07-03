package com.leolaia.estudospring.data.vo.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.dozermapper.core.Mapping;
import jakarta.persistence.Column;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.util.Objects;

@JsonPropertyOrder({"id", "first_name", "last_name", "gender", "address"})
public class PersonVO extends RepresentationModel<PersonVO> implements Serializable {

    @JsonProperty("id")
    @Mapping("id")
    private Long key;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
    private String address;
    private String gender;

    private Boolean enabled;

    public PersonVO() {
    }
    public Long getKey() {
        return key;
    }

    public void setKey(Long key) {
        this.key = key;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PersonVO vo)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(getKey(), vo.getKey()) && Objects.equals(getFirstName(), vo.getFirstName()) && Objects.equals(getLastName(), vo.getLastName()) && Objects.equals(getAddress(), vo.getAddress()) && Objects.equals(getGender(), vo.getGender()) && Objects.equals(getEnabled(), vo.getEnabled());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getKey(), getFirstName(), getLastName(), getAddress(), getGender(), getEnabled());
    }
}
