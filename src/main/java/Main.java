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
                return;
            }

            testChatBot.main(userInput);
        }
    }
}
