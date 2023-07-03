package com.leolaia.estudospring.integrationTests.vo;

import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.util.Objects;

@XmlRootElement
public class PersonVO implements Serializable {

    private Long id;
    private String first_name;
    private String last_name;
    private String address;
    private String gender;
    private Boolean enabled;

    public PersonVO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
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
        if (!(o instanceof PersonVO personVO)) return false;
        return Objects.equals(getId(), personVO.getId()) && Objects.equals(getFirst_name(), personVO.getFirst_name()) && Objects.equals(getLast_name(), personVO.getLast_name()) && Objects.equals(getAddress(), personVO.getAddress()) && Objects.equals(getGender(), personVO.getGender()) && Objects.equals(getEnabled(), personVO.getEnabled());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFirst_name(), getLast_name(), getAddress(), getGender(), getEnabled());
    }
}
