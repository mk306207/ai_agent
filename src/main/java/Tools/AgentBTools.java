package Tools;
import dev.langchain4j.agent.tool.Tool;
import Tools.DBManager;
import dev.langchain4j.agent.tool.P;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AgentBTools {
    private String INSERT_SQL = "INSERT INTO refund_cases (customer_email, order_id, reason) VALUES (?, ?, ?)";
    private Map<String,Double> plans = new HashMap<>(){{
        put("Basic", 9.99);
        put("Premium",19.99);
        put("Standard",14.99);
    }};

    @Tool("Open a refund case for a customer, given their email, order ID, and reason for the refund.")
    public String openRefundCase(@P("email") String email, @P("orderId") String orderId, @P("reason") String reason ) {
        if (email == null || email.trim().isEmpty()) {
            return "Error: Email cannot be empty.";
        }
        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            return "Error: Invalid email format.";
        }
        if (orderId == null || orderId.trim().isEmpty()) {
            return "Error: Order ID cannot be empty.";
        }

        PreparedStatement preparedStatement = null;
        try (Connection conn = DBManager.getConnection()) {
            preparedStatement = conn.prepareStatement(INSERT_SQL);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, orderId);
            preparedStatement.setString(3, reason);
            preparedStatement.executeUpdate();
            return "Refund case opened successfully for order " + orderId + ". You will receive an update via email at " + email + " within 2-3 business days.";
        } catch (Exception e) {
            return "Error: Failed to open refund case. Please try again later.";
        }
    }

    @Tool("Check the status of a refund case using the customer's email and order ID.")
    public String checkRefundStatus(@P("email") String email, @P("orderId") String orderId) {
        // Validation
        if (email == null || email.trim().isEmpty()) {
            return "Error: Email cannot be empty.";
        }
        if (orderId == null || orderId.trim().isEmpty()) {
            return "Error: Order ID cannot be empty.";
        }

        String SQL = "SELECT status FROM refund_cases WHERE customer_email = ? AND order_id = ?";
        PreparedStatement preparedStatement = null;
        try (Connection conn = DBManager.getConnection()) {
            preparedStatement = conn.prepareStatement(SQL);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, orderId);
        String result = "";
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    String status = rs.getString("status");
                    return "Refund case status for order " + orderId + ": " + status;
                } else {
                    return "No refund case found for email: " + email + " and order ID: " + orderId;
                }
            }
        } catch (SQLException e) {
            return "Error: Failed to check refund status. Please try again later.";
        }
    }
    @Tool("Provide a list of available subscription plans and their prices.")
    public String listSubscriptionPlans() {
        StringBuilder response = new StringBuilder("Available Subscription Plans:\n");
        for (Map.Entry<String, Double> entry : plans.entrySet()) {
            response.append("- ").append(entry.getKey()).append(": $").append(entry.getValue()).append(" per month\n");
        }
        return response.toString();
    }
}
