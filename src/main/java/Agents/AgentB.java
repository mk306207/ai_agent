package Agents;

import Interfaces.AgentA_Interface;
import Interfaces.AgentB_Interface;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;

public class AgentB {
    private AgentB_Interface myAgent;
    private ChatLanguageModel model;

    public AgentB(String apiKey){
        this.model = OpenAiChatModel
                .builder()
                .baseUrl("https://api.groq.com/openai/v1")
                .apiKey(apiKey)
                .modelName("llama-3.3-70b-versatile")
                .build();
        this.myAgent = AiServices.builder(AgentB_Interface.class)
                .chatLanguageModel(model)
                .build();
    }
    public String request(String userInput){
        String response = myAgent.text(userInput);
        return "💵: " + response;
    }

}
