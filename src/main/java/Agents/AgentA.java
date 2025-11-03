package Agents;

import Interfaces.AgentA_Interface;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentSplitter;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.embedding.onnx.allminilml6v2.AllMiniLmL6V2EmbeddingModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class AgentA {
    private AgentA_Interface myAgentA;
    private ChatLanguageModel model;
    private EmbeddingModel embeddingModel;
    private EmbeddingStore<TextSegment> embeddingStore;
    private List<Document> documents;
    public AgentA(String apiKey, ChatMemory sharedMemory){
        this.model = OpenAiChatModel
                .builder()
                .baseUrl("https://api.groq.com/openai/v1")
                .apiKey(apiKey)
                .modelName("llama-3.3-70b-versatile")
                .build();

        this.embeddingModel = new AllMiniLmL6V2EmbeddingModel();

        this.embeddingStore = new InMemoryEmbeddingStore<>();

        this.documents = loadDocumentsFromFolder("docs");
        DocumentSplitter splitter = DocumentSplitters.recursive(300, 50);
        EmbeddingStoreIngestor ingestor = EmbeddingStoreIngestor.builder()
                .documentSplitter(splitter)
                .embeddingModel(embeddingModel)
                .embeddingStore(embeddingStore)
                .build();

        ingestor.ingest(documents);

        EmbeddingStoreContentRetriever retriever = EmbeddingStoreContentRetriever.builder()
                .embeddingStore(embeddingStore)
                .embeddingModel(embeddingModel)
                .maxResults(5)
                .minScore(0.7)
                .build();

        this.myAgentA = AiServices.builder(AgentA_Interface.class)
                .chatLanguageModel(model)
                .contentRetriever(retriever)
                .chatMemory(sharedMemory)
                .build();


        System.out.println("   Loaded " + documents.size() + " documents");
    }
    public String request(String userInput){
        String response = myAgentA.chat(userInput);
        return "🧑‍💻: " + response;
    }
    private List<Document> loadDocumentsFromFolder(String folderPath) {
        List<Document> docs = new ArrayList<>();
        Path folder = Paths.get(folderPath);
        if (!Files.exists(folder)) {
            System.err.println("Folder not found: " + folderPath);
            return docs;
        }

        try (Stream<Path> paths = Files.walk(folder)) {
            paths.filter(Files::isRegularFile)
                    .forEach(p -> {
                        try {
                            String text = Files.readString(p);
                            Document doc = Document.from(text);
                            docs.add(doc);
                        } catch (IOException e) {
                            System.err.println("Can't read file " + p + ": " + e.getMessage());
                        }
                    });
        } catch (IOException e) {
            System.err.println("Error when reading docs from " + folderPath + ": " + e.getMessage());
        }
        return docs;
    }
}
