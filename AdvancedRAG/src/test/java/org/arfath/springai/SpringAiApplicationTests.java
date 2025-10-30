package org.arfath.springai;

import org.arfath.springai.helper.Helper;
import org.arfath.springai.services.DocumentReader;
import org.arfath.springai.services.DocumentTransformer;
import org.arfath.springai.services.chatservice;
import org.junit.jupiter.api.Test;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class SpringAiApplicationTests {

    @Autowired
    private DocumentReader documentReader;

    @Autowired
    private DocumentTransformer documentTransformer;

    @Autowired
    private VectorStore vectorStore;

    @Test
    void testDataReader(){
        System.out.println("reading data from json");
        var documents = documentReader.readDocumentsFromJSON();
        System.out.println(documents.size());
        documents.forEach(
                item -> System.out.println(item));
    }

    @Test
    void testpdfDataReader(){
        System.out.println("reading data from pdf");
        var documents = documentReader.readDocumentsFromPDF();
        System.out.println(documents.size());
        documents.forEach(
                item -> System.out.println(item));
        System.out.println("----------------------");

        System.out.println("now we are transforming document");

        List<Document> transformed = documentTransformer.transform(documents);
        System.out.println(transformed.size());

        System.out.println("loading the data to database");
        vectorStore.add(transformed);

        System.out.println("data loaded to database");




    }



}
