package com.example.filepdf.modal;

import java.util.Date;

import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan
public class Params {

	private Long id;
	private String name;
	private String email;
	private String lang;
	private String phoneNumber;
	private String region;
	private String province;
	private String city;
	private String postalCode;
	private String street;
	private String number;
	private Double markerLat;
	private Double markerLong;
	private Integer householdSize;
	private Double yearlyConsumption;
	private String phaseType;
	private Integer houseAge;
	private String roofType;
	private Double roofSize;
	private Double roofHeight;
	private Double roofSlope;
	private String roofOrientation;
	private String comment;
	private OfferDetails offerDetails;
	private String status;
	private Date createdAt;
	private Date updatedAt;
	private String note;
	private String updatedBy;
	private String assignee;
	private String installer;
	private String requestNo;
	private Double finalInvoice;

	// getters and setters
}
