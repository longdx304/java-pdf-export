package com.example.filepdf.services;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.resolver.font.DefaultFontProvider;
import com.itextpdf.kernel.pdf.PdfWriter;

@Service
public class PdfServiceImpl implements IPdfService {
	private Logger logger = LoggerFactory.getLogger(PdfServiceImpl.class);

	@Autowired
	private TemplateEngine templateEngine;
	@Autowired
	private Environment environment;

	@Override
	public String generatorFilePdf(Map<String, Object> params) {

		// Generate html content
		String htmlContent = generateHtmlContent(params);

		try {
			// Generate pdf
			ByteArrayOutputStream byteArrayOutputStream = generatePdf(htmlContent);

			// Save file pdf
			String blobUrl = uploadFilePdf(byteArrayOutputStream);

			return blobUrl;
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("Error: {0}", e);
			return "Error";
		}
	}

	/**
	 * Generates HTML content using the provided parameters.
	 * @param params a map of key-value pairs containing the variables to be used in the HTML template
	 * @return the generated HTML content as a string
	 */
	private String generateHtmlContent(Map<String, Object> params) {
		Context context = new Context();
		context.setVariables(params);
		String htmlContent = templateEngine.process("templatePdf", context);

		return htmlContent;
	}

	/**
	 * Generates a PDF document from the given HTML content.
	 *
	 * @param htmlContent the HTML content to convert to PDF
	 * @return a ByteArrayOutputStream containing the generated PDF document
	 */
	private ByteArrayOutputStream generatePdf(String htmlContent) {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

		PdfWriter pdfWriter = new PdfWriter(byteArrayOutputStream);

		DefaultFontProvider defaultFont = new DefaultFontProvider(false, true, false);

		ConverterProperties converterProperties = new ConverterProperties();

		converterProperties.setFontProvider(defaultFont);
		converterProperties.setCssApplierFactory(null);

		System.out.println("convert to pdf");
		HtmlConverter.convertToPdf(htmlContent, pdfWriter);

		return byteArrayOutputStream;
	}

	/**
	 * Uploads a PDF file to Azure Blob Storage.
	 * 
	 * @param byteArrayOutputStream the ByteArrayOutputStream containing the PDF file to upload
	 * @return the URL of the uploaded PDF file
	 * @throws IOException if there is an error uploading the file
	 */
	private String uploadFilePdf(ByteArrayOutputStream byteArrayOutputStream) throws IOException {
		String connectString = environment.getProperty("connectString");
		String containerName = environment.getProperty("containerName");

		BlobContainerClient blobContainerClient = new BlobServiceClientBuilder()
				.connectionString(connectString).buildClient()
				.getBlobContainerClient(containerName);

		BlobClient blobClient = blobContainerClient.getBlobClient("template.pdf");

		InputStream inputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
		blobClient.upload(inputStream, inputStream.available(), true);

		String blobUrl = blobClient.getBlobUrl();

		return blobUrl;
	}
}
