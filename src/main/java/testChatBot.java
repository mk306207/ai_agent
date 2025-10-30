import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import io.github.cdimascio.dotenv.Dotenv;

public class testChatBot {
    public static void main(String userInput) {
        Dotenv dotenv = Dotenv.load();
        String apiKey = dotenv.get("APIKEY");
        ChatLanguageModel model = OpenAiChatModel
                .builder().
                baseUrl("https://api.groq.com/openai/v1").
                apiKey(apiKey).
                modelName("llama-3.3-70b-versatile").
                build();
        String response = model.generate(userInput);
        System.out.println(response);
    }
}