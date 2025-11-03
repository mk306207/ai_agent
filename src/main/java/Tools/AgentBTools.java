package Tools;
import dev.langchain4j.agent.tool.Tool;
import Tools.DBManager;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class AgentBTools {
    private String INSERT_SQL = "INSERT INTO refund_cases (customer_email, order_id, reason) VALUES (?, ?, ?)";

    @Tool("Open a refund case for a customer, given their email, order ID, and reason for the refund.")
    public void openRefundCase(String email, String orderId,  String reason){
        //String sql = "INSERT INTO refund_cases VALUES (email, orderId, reason, 'Pending', CURRENT_TIMESTAMP);";
        PreparedStatement preparedStatement = null;
        try (Connection conn = DBManager.getConnection()){
            preparedStatement = conn.prepareStatement(INSERT_SQL);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, orderId);
            preparedStatement.setString(3, reason);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
