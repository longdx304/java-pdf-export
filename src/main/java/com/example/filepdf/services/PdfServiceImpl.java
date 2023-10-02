package com.example.filepdf.services;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.resolver.font.DefaultFontProvider;
import com.itextpdf.kernel.pdf.PdfWriter;

@Service
public class PdfServiceImpl implements IPdfService {
	private Logger logger = LoggerFactory.getLogger(PdfServiceImpl.class);

	@Autowired
	private TemplateEngine templateEngine;

	@Override
	public String generatorFilePdf(Map<String, Object> params) {
		System.out.println("Service --");

		String htmlContent = generateHtmlContent(params);
		// String htmlContent = templateEngine.process("templatePdf", context);
		try {
			ByteArrayOutputStream byteArrayOutputStream = generatePdf(htmlContent);

			// Save file pdf
			System.out.println("save file");
			saveFilePdf(byteArrayOutputStream);

			return "Success";
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("Error: {0}", e);
		}

		return "Success";
	}

	private String generateHtmlContent(Map<String, Object> params) {
		Context context = new Context();
		context.setVariables(params);
		String htmlContent = templateEngine.process("templatePdf", context);

		return htmlContent;
	}

	private ByteArrayOutputStream generatePdf(String htmlContent) {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

		PdfWriter pdfWriter = new PdfWriter(byteArrayOutputStream);

		DefaultFontProvider defaultFont = new DefaultFontProvider(false, true,
				false);

		ConverterProperties converterProperties = new ConverterProperties();

		converterProperties.setFontProvider(defaultFont);
		converterProperties.setCssApplierFactory(null);

		System.out.println("convert to pdf");
		HtmlConverter.convertToPdf(htmlContent, pdfWriter);

		return byteArrayOutputStream;
	}

	private void saveFilePdf(ByteArrayOutputStream byteArrayOutputStream) throws IOException {
		// File pdfFile = new File("src/main/resources/pdf/" + "test.pdf");
		File pdfFile = new File("src/main/resources/pdf/" + "template.pdf");
		pdfFile.getParentFile().mkdirs();
		byteArrayOutputStream.writeTo(new java.io.FileOutputStream(pdfFile));
		byteArrayOutputStream.close();
		byteArrayOutputStream.flush();
	}
}
