package Interfaces;

import dev.langchain4j.service.SystemMessage;

public interface AgentB_Interface {
    @SystemMessage("You are a financial expert. Answer the questions related to finance in a professional and concise manner." +
            "You have access to tools that can help you provide accurate financial help. " +
            "If user forgot to provide necessary information, ask them to provide it and remember that you asked about it.")
    String chat(String message);
}
