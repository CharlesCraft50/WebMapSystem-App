package com.systemweb.webmapsystem.web;

import org.springframework.web.multipart.MultipartFile;

public class ResidentDTO {

    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String suffix;
    private int age;
    private String gender;
    private String occupation;
    private String mobileNo;
    private String telephoneNo;
    private String birthPlace;
    private String permanentAddress;
    private String temporaryAddress;
    private String nationality;
    private String civilStatus;
    private String birthDate;
    private MultipartFile photoInput;  
    private String votingEligibility;
    private Double latitude;
    private Double longitude;
    private Boolean pwd;
    private Boolean isDeceased;
    
    public ResidentDTO() {}

    public Double getLatitude() {
        return latitude;
    }
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
    public Double getLongitude() {
        return longitude;
    }
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
    // Getters and Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getMiddleName() {
        return middleName;
    }
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getSuffix() {
        return suffix;
    }
    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getOccupation() {
        return occupation;
    }
    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }
    public String getMobileNo() {
        return mobileNo;
    }
    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }
    public String getTelephoneNo() {
        return telephoneNo;
    }
    public void setTelephoneNo(String telephoneNo) {
        this.telephoneNo = telephoneNo;
    }
    public String getBirthPlace() {
        return birthPlace;
    }
    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }
    public String getNationality() {
        return nationality;
    }
    public void setNationality(String nationality) {
        this.nationality = nationality;
    }
    public String getCivilStatus() {
        return civilStatus;
    }
    public void setCivilStatus(String civilStatus) {
        this.civilStatus = civilStatus;
    }
    public String getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }
    public MultipartFile getPhotoInput() {
        return photoInput;
    }
    public void setPhotoInput(MultipartFile photoInput) {
        this.photoInput = photoInput;
    }

    public String getVotingEligibility() {
        return votingEligibility;
    }

    public void setVotingEligibility(String votingEligibility) {
        this.votingEligibility = votingEligibility;
    }
    public String getPermanentAddress() {
        return permanentAddress;
    }
    public void setPermanentAddress(String permanentAddress) {
        this.permanentAddress = permanentAddress;
    }
    public String getTemporaryAddress() {
        return temporaryAddress;
    }
    public void setTemporaryAddress(String temporaryAddress) {
        this.temporaryAddress = temporaryAddress;
    }

    public boolean getPwd() {
        return pwd != null ? pwd.booleanValue() : false; 
    }

    public void setPwd(boolean pwd) {
        this.pwd = pwd;
    }

    public boolean getDeceased() {
        return isDeceased != null ? isDeceased.booleanValue(): false;
    }

    public void setDeceased(boolean isDeceased) {
        this.isDeceased = isDeceased;
    }

    
}