package com.employment.WorkLife.DTO;

import java.sql.Date;

public class SalaryDTO {
    private Integer employeeId;
    private Date endDate;
    private Date startDate;
    private String amount;

    public SalaryDTO(Integer employeeId, Date endDate, Date startDate, String amount) {
        this.employeeId = employeeId;
        this.endDate = endDate;
        this.startDate = startDate;
        this.amount = amount;
    }

    public SalaryDTO() {
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
