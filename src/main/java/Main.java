import Agents.AgentA;
import Agents.AgentB;
import Agents.routerAI;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import io.github.cdimascio.dotenv.Dotenv;

import java.util.Scanner;

class Main {
    static void main() {
        Dotenv dotenv = Dotenv.load();
        String apiKey = dotenv.get("2APIKEY");
        ChatMemory sharedMemory = MessageWindowChatMemory.withMaxMessages(20);
        IO.println(("Hello and welcome! If you wish to end the session, please type 'exit'"));

        routerAI routerAI = new routerAI(apiKey, sharedMemory);
        AgentA agentA = new AgentA(apiKey,sharedMemory);
        AgentB agentB = new AgentB(apiKey,sharedMemory);
        Scanner scanner = new Scanner(System.in);

        while (true){
            String userInput = scanner.nextLine();
            if (userInput.equalsIgnoreCase("exit")){
                IO.println("Goodbye!");
                break;
            }
            String route = routerAI.request(routerAI,userInput);
            if (route.equals("ERROR")) {
                IO.println("Sorry, I can only answer technical or financial questions.");
            }
            else if (route.equals("FINANCIAL")) {
                String response = agentB.request(userInput);
                IO.println(response);
            }
            else if (route.equals("TECHNICAL")) {
                String response = agentA.request(userInput);
                IO.println(response);
            }
            else {
                IO.println("Unexpected routing result.");
            }
        }
    }
}
