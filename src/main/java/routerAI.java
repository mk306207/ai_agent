import Interfaces.RouterInterface;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import io.github.cdimascio.dotenv.Dotenv;

public class routerAI {
    Dotenv dotenv = Dotenv.load();
    String apiKey = dotenv.get("APIKEY");
    ChatLanguageModel model = OpenAiChatModel
            .builder().
            baseUrl("https://api.groq.com/openai/v1").
            apiKey(apiKey).
            modelName("llama-3.3-70b-versatile").
            build();
    RouterInterface router = AiServices.builder(RouterInterface.class)
            .chatLanguageModel(model)
            .build();

    public static String request(String userInput) {
        routerAI myRouter = new routerAI();
        String route = myRouter.router.text(userInput);
        return route;
    }
}
