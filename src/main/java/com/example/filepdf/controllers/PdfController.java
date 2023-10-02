package com.example.filepdf.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.filepdf.services.IPdfService;

@RestController
@RequestMapping("/api")
public class PdfController {
	@Autowired
	private IPdfService iPdfService;

	@GetMapping("/")
	public String home() {
		return "Export file pdf";
	}

	@PostMapping("/generate-pdf")
	public String generateFilePdf(@RequestBody Map<String, Object> params) {
		return iPdfService.generatorFilePdf(params);
	}
}
