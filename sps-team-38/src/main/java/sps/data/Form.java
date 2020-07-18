package com.google.sps.data;

public final class Form {

    private final long id;
    private final long timestamp;
    private final String title;
    private final String ownerName;
    private final String ownerEmail;
    private final String description;
    private final String sessionUrl;
    private final String expiryDate;
    private final int capacity;

  public Form(long id, long timestamp, String title, String ownerName, String ownerEmail,
   String description, int capacity, String expiryDate, String sessionUrl) {
    this.id = id;
    this.timestamp = timestamp;
    this.title = title;
    this.ownerEmail = ownerEmail;
    this.description = description;
    this.sessionUrl = sessionUrl;
    this.expiryDate = expiryDate;
    this.capacity = capacity;
  }
}