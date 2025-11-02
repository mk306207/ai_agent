package Interfaces;

import dev.langchain4j.service.SystemMessage;

public interface AgentB_Interface {
    @SystemMessage("You are a financial expert. Answer the questions related to finance in a professional and concise manner.")
    String text(String message);
}
