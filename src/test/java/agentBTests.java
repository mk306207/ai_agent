import Agents.AgentB;
import Tools.AgentBTools;
import Tools.DBManager;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class agentBTests {

    private static String apiKey;
    private static AgentB agentB;
    private static AgentBTools tools;
    private ChatMemory memory;

    @BeforeAll
    static void setupClass() {
        Dotenv dotenv = Dotenv.load();
        apiKey = dotenv.get("APIKEY");
        assertNotNull(apiKey);
        tools = new AgentBTools();
    }

    @BeforeEach
    void setup() {
        memory = MessageWindowChatMemory.withMaxMessages(20);
        agentB = new AgentB(apiKey, memory);
    }

    @AfterEach
    void cleanup() {
        cleanupTestData();
    }

    private void cleanupTestData() {
        try (Connection conn = DBManager.getConnection()) {
            String sql = "DELETE FROM refund_cases WHERE customer_email LIKE 'test%@%'";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Cleanup error: " + e.getMessage());
        }
    }
    @Test
    void testOpenRefundCase_ValidData() {
        String result = tools.openRefundCase(
            "test@example.com",
            "12345",
            "Product damaged during shipping"
        );
        assertTrue(result.contains("successfully"));
        assertTrue(result.contains("12345"));
        assertTrue(result.contains("test@example.com"));
    }

    @Test
    void testOpenRefundCase_InvalidEmail() {
        String result = tools.openRefundCase(
            "not-an-email",
            "12345",
            "Product damaged"
        );

        assertTrue(result.contains("Error"));
        assertTrue(result.contains("email"));
    }

    @Test
    void testOpenRefundCase_EmptyEmail() {
        String result = tools.openRefundCase(
            "",
            "12345",
            "Product damaged"
        );

        assertTrue(result.contains("email") || result.contains("empty"));
    }

    @Test
    void testCheckRefundStatus_ExistingCase() {
        tools.openRefundCase(
            "test2@example.com",
            "ORD-67890",
            "Wrong item received"
        );

        String result = tools.checkRefundStatus(
            "test2@example.com",
            "ORD-67890"
        );

        assertTrue(result.contains("Pending") || result.contains("status"));
    }

    @Test
    void testCheckRefundStatus_EmptyEmail() {
        String result = tools.checkRefundStatus("", "ORD-12345");

        assertTrue(result.contains("Error") || result.contains("email"));
    }

    @Test
    void testBillingInfo() {
        String result = tools.listSubscriptionPlans();

        assertNotNull(result, "Should return billing information");
        assertTrue(result.contains("Basic"));
        assertTrue(result.contains("9.99"));
        assertTrue(result.contains("Standard"));
        assertTrue(result.contains("14.99"));
        assertTrue(result.contains("Premium"));
        assertTrue(result.contains("19.99"));
    }

    @Test
    void testAgentB_PricingQuestion() {
        String response = agentB.request("What are your pricing plans?");
        assertTrue(response.toLowerCase().contains("plan"));
    }

    @Test
    void testAgentB_RefundConversation() {
        String response1 = agentB.request("I want a refund");
        assertTrue(response1.contains("💵"));
        String response2 = agentB.request("test3@example.com");
        assertTrue(response2.contains("💵"));
        String response3 = agentB.request("ORD-11111");
        assertTrue(response3.contains("💵"));
        String response4 = agentB.request("Product was defective");
        assertTrue(response4.contains("💵"));
    }
}


