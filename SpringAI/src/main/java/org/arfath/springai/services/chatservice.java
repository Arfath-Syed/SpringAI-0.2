package org.arfath.springai.services;

import org.arfath.springai.entity.tut;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SafeGuardAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.rag.advisor.RetrievalAugmentationAdvisor;
import org.springframework.ai.rag.generation.augmentation.ContextualQueryAugmenter;
import org.springframework.ai.rag.retrieval.search.VectorStoreDocumentRetriever;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Flux;

import javax.swing.*;
import java.util.List;
import java.util.Map;

@Service
public class chatservice {
    private final ChatClient chatClient;
    private final ChatMemory chatMemory;
    private final VectorStore vectorStore;

    @Value("classpath:/prompts/userMessage.st")
    private Resource userMessage;
    @Value("classpath:/prompts/systemMessage.st")
    private Resource systemMessage;

//    public chatservice(ChatClient.Builder builder) {
//        this.chatClient = builder;
//    }

    public chatservice(ChatClient chatClient, ChatMemory chatMemory, VectorStore vectorStore) {
        this.chatClient = chatClient;
        this.chatMemory = chatMemory;
        this.vectorStore = vectorStore;
    }


//    public chatservice(ChatClient.Builder builder){
//        this.chatClient = builder
//                .defaultOptions(
//                        OpenAiChatOptions
//                                .builder()
//                                .model("gpt-4o-mini")
//                                .temperature(0.3)
//                                .build()
//                )
//                .build();
//    }

    public ResponseEntity<String> chat(String q){
//        Prompt prompt1 = new Prompt(q, OpenAiChatOptions
//                .builder()
//                .model("gpt-4o-mini")
//                .temperature(0.3)
//                .maxTokens(100)
//                .build()
//        );

        Prompt prompt = new Prompt(q);

        String querystr = "Act as a expert in coding and programming, " +
                "always give code in java, now anwer this: {q} ";
        var resultResponse = chatClient
                .prompt()
                .user(u->u.text(querystr).param("q", q))
                .call()
                .content();


        return ResponseEntity.ok(resultResponse);

    }

//    prompt template
    public String chatTemplate(String q, String userId){

//        PromptTemplate template = new PromptTemplate(
//                "What is {techName}? give me example of {techExample}"
//        );
//
//        String renderedMessage = template.render(
//                Map.of(
//                        "techName", "Spring",
//                        "techExample", "Spring AI"
//                )
//        );
//
//        Prompt prompt = new Prompt(renderedMessage);
//        return chatClient.prompt(prompt).call().content();


//        SystemPromptTemplate system = SystemPromptTemplate
//                .builder()
//                .template("you are a helpful coding assistant. You are a expert in coding")
//                .build();
//        Message systemRender = system.createMessage();
//        PromptTemplate userTemplate = PromptTemplate
//                .builder()
//                .template("What is {techName}? give me example of {techExample}")
//                .build();
//
//        Message userRender = userTemplate.createMessage(Map.of(
//                "techName", "python",
//                "techExample", "Langchain"
//
//        ));
//
//        Prompt prompt = new Prompt(systemRender, userRender);
//        return chatClient
//                .prompt(prompt)
//                .call()
//                .content();

//        return chatClient.
//                prompt()
//                .system(s->s.text("you are a helpful coding assistant. You are a expert in coding"))
//                .user(u->u.text("What is {techName}? give me example of {techExample}").params(Map.of(
//                        "techName", "python",
//                        "techExample", "lang chain"
//                )))
//                .call()
//                .content();

        return chatClient
                .prompt()
                .advisors(advisorSpec ->
                        advisorSpec.param(chatMemory.CONVERSATION_ID, userId))
                .system(systemMessage)
                .user(u-> u.text(this.userMessage)
                        .param("concept", q))
                .call()
                .content();

    }

    public Flux<String> streamChat(String q) {

        return chatClient
                .prompt()
                .system(s -> s.text(systemMessage))
                .user(u -> u.text(userMessage)
                        .param("concept", q))
                .stream()
                .content();
    }

    public void saveData(List<String> list){
        List<Document> documentList = list.stream().map(l -> new Document(l)).toList();
        this.vectorStore.add(documentList);

    }

    public String ragChat(String query){

//        SearchRequest searchRequest = SearchRequest
//                .builder()
//                .topK(3)
//                .similarityThreshold(0.6)
//                .query(query)
//                .build();
//
//        List<Document> documents = vectorStore.similaritySearch(searchRequest);
//        List<String> documentList = documents.stream().map(Document::getText).toList();
//        String contextData = String.join(",", documentList);


        QuestionAnswerAdvisor questionAnswerAdvisor = QuestionAnswerAdvisor
                .builder(vectorStore)
                .searchRequest(SearchRequest
                        .builder()
                        .similarityThreshold(0.8)
                        .topK(6)
                        .build()).build();

        RetrievalAugmentationAdvisor retrievalAugmentationAdvisor = RetrievalAugmentationAdvisor
                .builder()
                .documentRetriever(VectorStoreDocumentRetriever.builder()
                        .similarityThreshold(0.5)
                        .topK(8)
                        .vectorStore(vectorStore)
                        .build())
                .queryAugmenter(ContextualQueryAugmenter
                        .builder()
                        .allowEmptyContext(true)
                        .build())
                .build();
        return chatClient
                .prompt()
//                .system(s -> s.text(systemMessage).param("documents",contextData))
//                .advisors(new QuestionAnswerAdvisor(vectorStore))
//                .advisors(questionAnswerAdvisor)
                .advisors(retrievalAugmentationAdvisor)
                .user(u -> u.text(userMessage).param("query", query))

                .call()
                .content();
    }

}
