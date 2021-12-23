package guldilin;

import guldilin.commands.Command;
import guldilin.commands.Parser;
import guldilin.error.ErrorMessage;
import guldilin.error.ExitException;
import guldilin.util.ClientFactoryBuilder;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URL;
import java.util.Scanner;

public class MainHandler {
    private final URL baseUrl;
    private final Scanner scanner;

    public MainHandler(URL url) {
        this.baseUrl = url;
        this.scanner = new Scanner(System.in);
    }

    public boolean isServerAvailable() {
        final Client client = ClientFactoryBuilder.getClient();
        Response response = client.target(baseUrl + "/api/status/")
                .request(MediaType.APPLICATION_JSON)
                .get();
        return response.getStatus() == Response.Status.OK.getStatusCode();
    }

    private void printMenu() {
        System.out.println("Menu");
        System.out.println("\t1.\tAdd task");
        System.out.println("\t2.\tSearch task");
        System.out.println("\t3.\tLast tasks");
        System.out.println("\t\t4.\tExit");
    }

    private void printPromt() {
        System.out.print("$>");
    }

    private void handleUserInput() {
        boolean isWorking = true;
        String userString = "";
        Parser parser = new Parser();

        printMenu();
        while (isWorking) {
            printPromt();
            userString = this.scanner.nextLine();
            if (!this.isCommandCorrect(userString)) continue;
            Command command = parser.parse(userString);
            if (command == null) continue;
            try {
                command.execute(this.scanner, this.baseUrl);
            } catch (ExitException e) {
                isWorking = false;
            }
        }
    }

    private boolean isCommandCorrect(String command) {
        try {
            Integer.parseInt(command);
            return true;
        } catch (NumberFormatException e) {
            System.out.println(ErrorMessage.COMMAND_NOT_FOUND);
            return false;
        }
    }

    public void start() {
        System.out.println("Start client with server URL: " + baseUrl);

        handleUserInput();
    }
}
