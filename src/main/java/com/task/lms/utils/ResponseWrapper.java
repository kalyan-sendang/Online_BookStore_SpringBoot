package com.task.lms.utils;

public class ResponseWrapper {
    private int statusCode;
    private String message;
    private boolean success;

    private long totalItems;
    private int totalPages;

    private Object response;


    public ResponseWrapper() {
    }

    public ResponseWrapper(int statusCode, String message, boolean success, long totalItems, int totalPages, Object response) {
        this.statusCode = statusCode;
        this.message = message;
        this.success = success;
        this.totalItems = totalItems;
        this.totalPages = totalPages;
        this.response = response;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public long getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(long totalItems) {
        this.totalItems = totalItems;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public Object getResponse() {
        return response;
    }

    public void setResponse(Object response) {
        this.response = response;
    }
}
