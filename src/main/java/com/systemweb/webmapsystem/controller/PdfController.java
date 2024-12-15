package com.systemweb.webmapsystem.controller;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.systemweb.webmapsystem.model.PdfEditRequest;

import jakarta.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@RestController
public class PdfController {

    @PostMapping("/edit-pdf")
    public void editPdf(@RequestBody PdfEditRequest request, HttpServletResponse response) throws IOException {
        // Load the original PDF
        File pdfFile = new File("path/to/pdfs/" + request.getFile());
        PDDocument document = PDDocument.load(pdfFile);

        // Iterate over the edits and add them to the document
        for (PdfEditRequest.Edit edit : request.getEdits()) {
            PDPage page = document.getPage(0);  // Assuming the first page
            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contentStream.newLineAtOffset(Float.parseFloat(edit.getX()), Float.parseFloat(edit.getY())); // Position
            contentStream.showText(edit.getText());
            contentStream.endText();
            contentStream.close();
        }

        // Save the modified document
        File editedPdfFile = File.createTempFile("edited_", ".pdf");
        document.save(editedPdfFile);
        document.close();

        // Send the edited PDF as a response
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=" + request.getFile());
        Files.copy(editedPdfFile.toPath(), response.getOutputStream());
    }
}
