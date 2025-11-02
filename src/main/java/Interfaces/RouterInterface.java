package Interfaces;

import dev.langchain4j.service.SystemMessage;

public interface RouterInterface {
    @SystemMessage("You only answer with 1 out of these 3 words : 'TECHNICAL', 'FINANCIAL', 'ERROR'. TECHNICAL for technical questions, FINANCIAL for financial questions and ERROR for any other type of questions.")
    String text(String message);
}
