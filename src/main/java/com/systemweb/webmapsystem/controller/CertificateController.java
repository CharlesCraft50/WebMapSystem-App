package com.systemweb.webmapsystem.controller;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/pdf")
public class CertificateController {

    // Endpoint to upload and edit PDF
    @PostMapping("/edit")
    public ResponseEntity<byte[]> editPdf(@RequestParam("file") MultipartFile file, @RequestParam Map<String, String> params) {
        try {
            // Load the existing PDF
            PDDocument document = PDDocument.load(file.getInputStream());

            // Edit the first page of the PDF
            PDPage page = document.getPage(0);
            PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true);

            // Set the font and start editing text
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);

            // Example of adding text: We modify name and address based on the params from the request
            String name = params.getOrDefault("name", "Default Name");
            String address = params.getOrDefault("address", "Default Address");

            // Draw the text at specific coordinates on the page
            contentStream.beginText();
            contentStream.newLineAtOffset(50, 600); // Position of text on the page
            contentStream.showText("Name: " + name);
            contentStream.newLineAtOffset(0, -15);  // Move to next line
            contentStream.showText("Address: " + address);
            contentStream.endText();

            // Close the content stream
            contentStream.close();

            // Save the modified PDF to byte array
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            document.save(byteArrayOutputStream);
            document.close();

            // Return the modified PDF as response
            byte[] editedPdfBytes = byteArrayOutputStream.toByteArray();
            return ResponseEntity.ok()
                    .header("Content-Type", "application/pdf")
                    .header("Content-Disposition", "inline; filename=edited_certificate.pdf")
                    .body(editedPdfBytes);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }
}
