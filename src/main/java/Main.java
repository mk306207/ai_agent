import Agents.AgentA;
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
            else {
                String response = agentA.request(userInput);
                IO.println(response);
            }
        }
    }
}
