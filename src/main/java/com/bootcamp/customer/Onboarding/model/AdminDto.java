package com.bootcamp.customer.Onboarding.model;

import java.util.List;

public class AdminDto {
    private Long userId;
    private String username;
    private String email;
    private String phoneNo;
    private String customerTypeName;
    private String documentStatus;

    public List<String> getPlanNames() {
        return planNames;
    }

    public void setPlanNames(List<String> planNames) {
        this.planNames = planNames;
    }

    private List<Long> planIds;
    private List<String> planNames;

    public AdminDto(Long userId, String username, String email, String phoneNo, String customerTypeName, String documentStatus, List<Long> planIds, List<String> planNames) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.phoneNo = phoneNo;
        this.customerTypeName = customerTypeName;
        this.documentStatus = documentStatus;
        this.planIds = planIds;  // Initialize planIds
        this.planNames = planNames; // Initialize planNames
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<Long> getPlanIds() {
        return planIds;
    }

    public void setPlanIds(List<Long> planIds) {
        this.planIds = planIds;
    }
}
