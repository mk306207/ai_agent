import java.util.Scanner;

class Main {
    static void main() {
        IO.println(String.format("Hello and welcome!"));
        testChatBot myChat = new testChatBot();
        Scanner scanner = new Scanner(System.in);

        while(true) {
            String userInput = scanner.nextLine();
            if (userInput.equals("exit")) {
                IO.println("Goodbye!");
                break;
            }

            testChatBot.main(userInput);
        }
        while (true){
            String userInput = scanner.nextLine();
            String route = routerAI.request(userInput);
            if (route.equals("ERROR")) {
                return;
            }
            else {
                IO.println(route);
            }
        }
    }
}
