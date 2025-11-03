import Agents.AgentA;
import Agents.AgentB;
import Agents.routerAI;
import Agents.testChatBot;
import io.github.cdimascio.dotenv.Dotenv;

import java.util.Scanner;

class Main {
    static void main() {
        Dotenv dotenv = Dotenv.load();
        String apiKey = dotenv.get("APIKEY");

        IO.println(String.format("Hello and welcome!"));

        testChatBot myChat = new testChatBot();
        routerAI routerAI = new routerAI(apiKey);
        AgentA agentA = new AgentA(apiKey);
        AgentB agentB = new AgentB(apiKey);
        Scanner scanner = new Scanner(System.in);

//        while(true) {
//            String userInput = scanner.nextLine();
//            if (userInput.equals("exit")) {
//                IO.println("Goodbye!");
//                break;
//            }
//
//            Agents.testChatBot.main(userInput);
//        }
        while (true){
            String userInput = scanner.nextLine();
            String route = routerAI.request(userInput,apiKey);
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
