package org.arfath.springai.services;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.ResponseEntity;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.document.Document;
import org.springframework.ai.rag.advisor.RetrievalAugmentationAdvisor;
import org.springframework.ai.rag.generation.augmentation.ContextualQueryAugmenter;
import org.springframework.ai.rag.preretrieval.query.expansion.MultiQueryExpander;
import org.springframework.ai.rag.preretrieval.query.transformation.RewriteQueryTransformer;
import org.springframework.ai.rag.preretrieval.query.transformation.TranslationQueryTransformer;
import org.springframework.ai.rag.retrieval.join.ConcatenationDocumentJoiner;
import org.springframework.ai.rag.retrieval.search.VectorStoreDocumentRetriever;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.util.List;

@Service
public class chatservice {

    private final ChatClient chatClient;
    private final VectorStore vectorStore;


    public chatservice(ChatClient chatClient, VectorStore vectorStore) {
        this.chatClient = chatClient;
        this.vectorStore = vectorStore;
    }


    public void saveData(List<String> list) {
        List<Document> documentList = list.stream().map(l -> new Document(l)).toList();
        this.vectorStore.add(documentList);

    }
    public String getResponse(String query){

        RetrievalAugmentationAdvisor advisor = RetrievalAugmentationAdvisor
                .builder()
                .queryTransformers(
                        RewriteQueryTransformer
                                .builder()
                                .chatClientBuilder(chatClient.mutate())
                                .build(),
                        TranslationQueryTransformer
                                .builder()
                                .chatClientBuilder(chatClient.mutate())
                                .targetLanguage("french")
                                .build()

                )
                .queryExpander(MultiQueryExpander
                        .builder()
                        .chatClientBuilder(chatClient.mutate())
                        .build()
                )
                .documentRetriever(VectorStoreDocumentRetriever
                        .builder()
                        .vectorStore(vectorStore)
                        .topK(3)
                        .similarityThreshold(0.5)
                        .build()
                )
                .documentJoiner(new ConcatenationDocumentJoiner())
                .documentPostProcessors()
                .queryAugmenter(ContextualQueryAugmenter
                                .builder()
                                .build())
                .build();
        return chatClient
                .prompt()
                .advisors(advisor)
                .user(query)
                .call()
                .content();

    }
}

