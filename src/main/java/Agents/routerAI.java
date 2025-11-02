package Agents;

import Interfaces.RouterInterface;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;

public class routerAI {
    private ChatLanguageModel model;
    private RouterInterface router;
    public routerAI(String apiKey) {
        this.model = OpenAiChatModel
                .builder().
                baseUrl("https://api.groq.com/openai/v1").
                apiKey(apiKey).
                modelName("llama-3.3-70b-versatile").
                build();
        this.router = AiServices.builder(RouterInterface.class)
                .chatLanguageModel(model)
                .build();
    }

    public static String request(String userInput,String apiKey) {
        routerAI myRouter = new routerAI(apiKey);
        String route = myRouter.router.text(userInput);
        return route;
    }
}
