package Interfaces;

import dev.langchain4j.service.SystemMessage;

public interface RouterInterface {
    @SystemMessage("You only answer with 1 out of these 3 words : 'TECHNICAL', 'FINANCIAL', 'ERROR'. " +
            "TECHNICAL for technical questions, for example: API problems, api keys, error codes, SDK intergration and etc." +
            "FINANCIAL for financial questions for example: payments, billings, invoices, etc. " +
            "And ERROR for any other type of questions.")
    String chat(String message);
}
