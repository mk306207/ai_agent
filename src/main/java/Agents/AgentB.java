package Agents;

import Interfaces.AgentA_Interface;
import Interfaces.AgentB_Interface;
import Tools.AgentBTools;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;

public class AgentB {
    private AgentB_Interface myAgent;
    private ChatLanguageModel model;

    public AgentB(String apiKey, ChatMemory sharedMemory){
        this.model = OpenAiChatModel
                .builder()
                .baseUrl("https://api.groq.com/openai/v1")
                .apiKey(apiKey)
                .modelName("llama-3.3-70b-versatile")
                .build();
        this.myAgent = AiServices.builder(AgentB_Interface.class)
                .chatLanguageModel(model)
                .tools(new AgentBTools())
                .chatMemory(sharedMemory)
                .build();
    }
    public String request(String userInput){
        String response = myAgent.chat(userInput);
        return "💵: " + response;
    }

}
