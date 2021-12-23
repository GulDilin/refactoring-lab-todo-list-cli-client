package guldilin.commands;

import guldilin.dto.ErrorDTO;
import guldilin.dto.TaskDTO;
import guldilin.util.ClientFactoryBuilder;
import guldilin.util.Requests;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class CreateCommand implements Command {
    @Override
    public void execute(Scanner in, URL baseUrl) {
        System.out.println("New task");
        String title = this.enterTitle(in);
        String description = enterDescription(in);
        Date deadline = enterDeadline(in);
        System.out.println(deadline);
        List<String> tags = enterTags(in);
        TaskDTO task = TaskDTO
                .builder()
                .title(title)
                .description(description)
                .deadline(deadline)
                .tags(tags)
                .build();

        Client client = ClientFactoryBuilder.getClient();
        Response response = client
                .target(baseUrl + "/api/tasks/")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(task));
        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
            System.out.println("Task created");
        } else {
            System.out.println("Task creation error");
            Requests.handleResponseError(response);
        }
    }

    private boolean isBlank(String str) {
        return str.isEmpty() || str.replaceAll(" ", "").isEmpty();
    }

    private String enterTitle(Scanner in) {
        String title = null;
        String userInput = null;
        while (title == null) {
            System.out.print("Title: ");
            userInput = in.nextLine();
            if (this.isBlank(userInput)) {
                System.out.println("Cannot be blank");
                continue;
            }
            title = userInput;
        }
        return title;
    }

    private List<String> enterTags(Scanner in) {
        List<String> tags = new LinkedList<>();
        int currentIndex = 1;
        while (true) {
            System.out.print(currentIndex + ": ");
            String tag = in.nextLine();
            if (this.isBlank(tag)) break;
            tags.add(tag);
            currentIndex++;
        }
        return tags;
    }

    private Date enterDeadline(Scanner in) {
        while (true) {
            System.out.print("Deadline: ");
            String userInput = in.nextLine();
            SimpleDateFormat parser = new SimpleDateFormat("dd.MM.yyyy");
            try {
                return parser.parse(userInput);
            } catch (ParseException e) {
                System.out.println("Cannot parse date format dd.MM.yyyy");
            }
        }
    }

    private String enterDescription(Scanner in) {
        System.out.println("Description: ");
        return in.nextLine();
    }
}
