package org.arfath.springai.services;

import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentTransformer {


    public List<Document> transform(List<Document> documents){
        TokenTextSplitter tokenTextSplitter = new TokenTextSplitter(200,400,10,5000,true);
        return tokenTextSplitter.transform(documents);



    }
}
