package guldilin.commands;

import guldilin.dto.TaskDTO;
import guldilin.util.ClientFactoryBuilder;
import guldilin.util.StringUtils;
import guldilin.util.Requests;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class CreateCommand implements Command {
    private final String dateFormat = "yyyy-MM-dd";
    private final DateTimeFormatter dateFormatter;

    public CreateCommand() {
        this.dateFormatter = DateTimeFormatter.ofPattern(dateFormat);
    }

    @Override
    public void execute(Scanner in, URL baseUrl) {
        System.out.println("New task");
        String title = this.enterTitle(in);
        String description = enterDescription(in);
        LocalDate deadline = enterDeadline(in);
        String deadlineString = dateFormatter.format(deadline);
        List<String> tags = enterTags(in);
        TaskDTO task = TaskDTO
                .builder()
                .title(title)
                .description(description)
                .deadline(deadlineString)
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


    private String enterTitle(Scanner in) {
        String title = null;
        String userInput;
        while (title == null) {
            System.out.print("Title: ");
            userInput = in.nextLine();
            if (StringUtils.isBlank(userInput)) {
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
        System.out.println("Enter tags (finish on empty line):");
        while (true) {
            System.out.print(currentIndex + ": ");
            String tag = in.nextLine();
            if (StringUtils.isBlank(tag)) break;
            tags.add(tag);
            currentIndex++;
        }
        return tags;
    }

    private LocalDate enterDeadline(Scanner in) {
        while (true) {
            System.out.print("Deadline (" + dateFormat + "): ");
            String userInput = in.nextLine();
            try {
                return LocalDate.parse(userInput, dateFormatter);
            } catch (Exception e) {
                System.out.println("Cannot parse date format " + dateFormat);
            }
        }
    }

    private String enterDescription(Scanner in) {
        System.out.print("Description: ");
        return in.nextLine();
    }
}
