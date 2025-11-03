package Interfaces;

import dev.langchain4j.service.SystemMessage;

public interface RouterInterface {
    @SystemMessage("You only answer with 1 out of these 3 words : 'TECHNICAL', 'FINANCIAL', 'ERROR'. " +
            "TECHNICAL for technical questions, for example: API problems, api keys, error codes, SDK intergration and etc." +
            "FINANCIAL for financial questions for example: payments, billings, invoices, etc. " +
            "And ERROR for any other type of questions." +
            "Handle user inputs in english and polish language" +
            "        Examples:\n" +
            "        - API key not working - TECHNICAL\n" +
            "        - I want a refund - FINANCIAL\n" +
            "        - Chcę złożyć podanie o zwrot - FINANCIAL\n" +
            "        - What is your favorite color? - ERROR" +
            "IMPORTANT: If previous messages in the conversation history mention refund \n" +
            "        classify ALL subsequent user responses as FINANCIAL, including:\n" +
            "        - Email addresses (e.g., test@gmail.com)\n" +
            "        - Order numbers (e.g., 11234, ABC-123)\n" +
            "        - Reasons (e.g., No reason, Wrong product, N/A)\n" +
            "        - Single words (e.g., Yes, No, Cancel)" +
            "CONTEXT RULES:\n" +
            "        - If previous messages mention refund, classify follow-up responses as FINANCIAL ONLY if they contain:\n" +
            "          * Email addresses (e.g., test@gmail.com)\n" +
            "          * Order numbers (e.g., 123454)\n" +
            "          * Reasons (e.g., No reason, Wrong product)\n" +
            "        - If user asks a completely unrelated question (e.g., What's the weather?), classify as ERROR even in refund context\n" +
            "Examples:\n" +
            "        - How to store my API key? - TECHNICAL\n" +
            "        - I want a refund - FINANCIAL\n" +
            "        - [in refund context] test@gmail.com - FINANCIAL\n" +
            "        - [in refund context] 123454 - FINANCIAL\n" +
            "        - [in refund context] No reason - FINANCIAL\n" +
            "        - What's the weather? - ERROR (even in refund context)\n" +
            "        - What is your favorite color? - ERROR")
    String chat(String message);
}
