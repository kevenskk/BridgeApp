package com.example.bridgereportapp;

public class ReportModal {
    private String bridgeName;
    private String bridgeLocation;
    private String bridgeDate;
    private String bridgeState;
    private int id;

    // creating getter and setter methods
    public String getBridgeName() {
        return bridgeName;
    }

    public void setBridgeName(String bridgeName) {
        this.bridgeName = bridgeName;
    }

    public String getBridgeLocation() {
        return bridgeLocation;
    }

    public void setBridgeLocation(String bridgeLocation) {
        this.bridgeLocation = bridgeLocation;
    }

    public String getBridgeDate() {
        return bridgeDate;
    }

    public void setBridgeDate(String bridgeDate) {
        this.bridgeDate = bridgeDate;
    }

    public String getBridgeState() {
        return bridgeState;
    }

    public void setBridgeState(String bridgeState) {
        this.bridgeState = bridgeState;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // constructor method
    public ReportModal(String bridgeName, String bridgeLocation, String bridgeDate, String bridgeState) {

        this.bridgeName = bridgeName;
        this.bridgeLocation = bridgeLocation;
        this.bridgeDate = bridgeDate;
        this.bridgeState = bridgeState;
    }
}

