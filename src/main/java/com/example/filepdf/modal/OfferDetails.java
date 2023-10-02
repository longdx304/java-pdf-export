package com.example.filepdf.modal;

import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan
public class OfferDetails {

	private Long createdAt;
	private Long updatedAt;
	private Long hash;
	private String offerDetails;
	private Long solarId;
	private String solarBrand;
	private String batteryModel;
	private Long batteryId;
	private String batteryBrand;
	private Integer warranty;
	private Double pricePerKwp;
	private Double vat;
	private Integer noOfPanels;
	private Integer noOfBatteries;
	private Double maxCapacity;
	private Double batteryCapacity;
	private Double totalProduction;
	private Double solarInvestment;
	private Double batteryInvestment;
	private Double solarInvestmentWithoutVAT;
	private Double batteryInvestmentWithoutVAT;
	private Double investment;
	private Double gainPerYear;
	private String revenues;
	private String monthlyEnergyEstimation;
	private Double carbonOneYear;
	private Double carbonTwentyFiveYears;
	private Integer treeOneYear;
	private Double totalRevenue;
	private Double returnOnInvestment;
	private Double premieSolar;
	private Double premieBattery;
	private Double premie;
	private Double greenCertificate;
	private Long inverterId;
	private Integer noOfInverters;
	private Integer noOfOptimizers;
	private Double solarDiscount;
	private Double solarExtraCost;
	private Double batteryDiscount;
	private Double batteryExtraCost;
	private Double recyclingCost;
	private Long updatedBy;
	private Long userProfileId;

	// getters and setters
}
