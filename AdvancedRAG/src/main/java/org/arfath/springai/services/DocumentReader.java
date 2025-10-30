package org.arfath.springai.services;

import org.springframework.ai.document.Document;
import org.springframework.ai.reader.ExtractedTextFormatter;
import org.springframework.ai.reader.JsonReader;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentReader {

    @Value("classpath:sample_data.json")
    private Resource jsonResource;

    @Value("classpath:cricket_rules.pdf")
    private Resource pdfResource;

    public List<Document> readDocumentsFromJSON(){
        JsonReader jsonReader = new JsonReader(jsonResource);
        return jsonReader.read();
    }

    public List<Document> readDocumentsFromPDF(){
        PagePdfDocumentReader pagePdfDocumentReader = new PagePdfDocumentReader(pdfResource,
                PdfDocumentReaderConfig.builder()
                        .withPageTopMargin(0)
                        .withPageExtractedTextFormatter(ExtractedTextFormatter.builder()
                                .withNumberOfBottomTextLinesToDelete(0)
                                .build())
                        .withPagesPerDocument(1)
                        .build());


        return pagePdfDocumentReader.read();
    }


}
