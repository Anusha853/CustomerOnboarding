package com.bootcamp.customer.Onboarding.model;

import java.util.List;

public class AdminDto {
    private String username;
    private String email;
    private String phoneNo;
    private String customerTypeName;
    private String documentStatus;
    private List<String> planNames;
    public AdminDto(String username, String email, String phoneNo, String customerTypeName, String documentStatus, List<String> planNames) {
        this.username = username;
        this.email = email;
        this.phoneNo = phoneNo;
        this.customerTypeName = customerTypeName;
        this.documentStatus = documentStatus;
        this.planNames = planNames;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getCustomerTypeName() {
        return customerTypeName;
    }

    public void setCustomerTypeName(String customerTypeName) {
        this.customerTypeName = customerTypeName;
    }

    public String getDocumentStatus() {
        return documentStatus;
    }

    public void setDocumentStatus(String documentStatus) {
        this.documentStatus = documentStatus;
    }

    public List<String> getPlanNames() {
        return planNames;
    }

    public void setPlanNames(List<String> planNames) {
        this.planNames = planNames;
    }
}
