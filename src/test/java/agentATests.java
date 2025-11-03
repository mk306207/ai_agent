import Agents.AgentA;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class agentATests {

    private static String apiKey;
    private static AgentA agentA;
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
        agentA = new AgentA(apiKey, memory);
    }

    @Test
    void testRAG_ApiAuthenticationQuestion() {
        String response = agentA.request("How do I generate an API key?");
        assertNotNull(response);
        assertTrue(response.contains("🧑‍💻"));
    }

    @Test
    void testRAG_ErrorTroubleshootingQuestion() {
        String response = agentA.request("What does a 401 error mean?");
        assertNotNull(response);
        assertTrue(response.toLowerCase().contains("api key"));
    }

    @Test
    void testRAG_SdkIntegrationQuestion() {
        String response = agentA.request("How do I use the Python SDK?");

        assertNotNull(response, "Agent should provide a response");
        assertTrue(
            response.toLowerCase().contains("python") &&
            response.toLowerCase().contains("pip"));
    }

    @Test
    void testRAG_WebhooksQuestion() {
        String response = agentA.request("How do I set up webhooks?");

        assertNotNull(response);
        assertTrue(
            response.toLowerCase().contains("webhook") &&
            response.toLowerCase().contains("endpoint"));
    }

    @Test
    void testNoKnowledge_OutsideDocs() {
        String response = agentA.request("How do I fix keyboard?");
        assertNotNull(response);
        assertTrue(
            response.toLowerCase().contains("don't have") ||
            response.toLowerCase().contains("complete information"));
    }

    @Test
    void testContext_RemembersPreviousQuestion() {
        agentA.request("Tell me about API keys");
        String response = agentA.request("How long do they expire?");

        assertNotNull(response);
        assertTrue(response.toLowerCase().contains("12") ||
            response.toLowerCase().contains("expire"));
    }
}


