import Agents.routerAI;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class routerTests {

    private static String apiKey;
    private static routerAI router;
    private ChatMemory memory;

    @BeforeAll
    static void setupClass() {
        Dotenv dotenv = Dotenv.load();
        apiKey = dotenv.get("APIKEY");
        assertNotNull(apiKey);
    }

    @BeforeEach
    void setup() {
        memory = MessageWindowChatMemory.withMaxMessages(20);
        router = new routerAI(apiKey, memory);
    }

    @Test
    void testRouter_ApiKeyQuestion() {
        String result = routerAI.request(router, "How do I generate an API key?");
        assertEquals("TECHNICAL", result);
    }

    @Test
    void testRouter_ErrorQuestion() {
        String result = routerAI.request(router, "What does a 401 error mean?");
        assertEquals("TECHNICAL", result);
    }

    @Test
    void testRouter_SdkQuestion() {
        String result = routerAI.request(router, "How do I use the Python SDK?");
        assertEquals("TECHNICAL", result);
    }

    @Test
    void testRouter_WebhookQuestion() {
        String result = routerAI.request(router, "How do I configure webhooks?");
        assertEquals("TECHNICAL", result);
    }

    @Test
    void testRouter_AuthenticationQuestion() {
        String result = routerAI.request(router, "How do I authenticate with the API?");
        assertEquals("TECHNICAL", result);
    }

    @Test
    void testRouter_RefundQuestion() {
        String result = routerAI.request(router, "I want a refund");

        assertEquals("FINANCIAL", result);
    }

    @Test
    void testRouter_PricingQuestion() {
        String result = routerAI.request(router, "What are your pricing plans?");

        assertEquals("FINANCIAL", result);
    }

    @Test
    void testRouter_BillingQuestion() {
        String result = routerAI.request(router, "How much does the Premium plan cost?");

        assertEquals("FINANCIAL", result);
    }

    @Test
    void testRouter_WeatherQuestion() {
        String result = routerAI.request(router, "What's the weather today?");

        assertEquals("ERROR", result);
    }

    @Test
    void testRouter_GeneralKnowledgeQuestion() {
        String result = routerAI.request(router, "What is the capital of France?");
        assertEquals("ERROR", result);
    }

    @Test
    void testRouter_ContextAwareRefund() {
        String result1 = routerAI.request(router, "I want a refund");
        assertEquals("FINANCIAL", result1);
        String result2 = routerAI.request(router, "john@example.com");
        assertEquals("FINANCIAL", result2);
    }

    @Test
    void testRouter_ContextSwitching() {
        String result1 = routerAI.request(router, "How do I generate an API key?");
        assertEquals("TECHNICAL", result1);

        String result2 = routerAI.request(router, "Actually, I want a refund");
        assertEquals("FINANCIAL", result2);
    }
}