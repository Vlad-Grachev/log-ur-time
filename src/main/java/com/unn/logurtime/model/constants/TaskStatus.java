package com.unn.logurtime.model.constants;

public enum TaskStatus {
    OPEN("Open"),
    IN_PROGRESS("In progress"),
    CANCELLED("Cancelled"),
    CLOSED("Closed");

    private String stringValue;

    TaskStatus(String stringValue) {
        this.stringValue = stringValue;
    }

    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }
}
