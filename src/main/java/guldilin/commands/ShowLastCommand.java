package guldilin.commands;

import guldilin.dto.TasksListDTO;
import guldilin.util.ClientFactoryBuilder;
import guldilin.util.Requests;
import guldilin.util.StringUtils;
import lombok.Data;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ShowLastCommand implements Command {
    private final boolean useTags;

    public ShowLastCommand() {
        this.useTags = false;
    }

    public ShowLastCommand(boolean useTags) {
        this.useTags = useTags;
    }

    private List<String> enterTags(Scanner in) {
        while (true) {
            System.out.print("Enter search tags (space separated): ");
            String userLine = in.nextLine();
            if (StringUtils.isBlank(userLine)) {
                System.out.println("Cannot be blank");
                continue;
            }
            return Arrays.asList(StringUtils.normalize(userLine).split(" "));
        }

    }

    @Override
    public void execute(Scanner in, URL baseUrl) {
        List<String> tags = useTags ? enterTags(in) : Collections.emptyList();
        String tagsQuery = tags.stream().map(tag -> "tags=" + tag).collect(Collectors.joining("&"));
        System.out.println(tagsQuery);
        Client client = ClientFactoryBuilder.getClient();
        Response response = client
                .target(baseUrl + "/api/tasks?" + tagsQuery)
                .request(MediaType.APPLICATION_JSON)
                .get();
        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
            System.out.println("Last tasks: ");
            @Data
            class Index {
                private int value;

                public void increment() {
                    value++;
                }
            }
            Index idx = new Index();
            TasksListDTO tasksListDTO = response.readEntity(TasksListDTO.class);
            tasksListDTO.getResult()
                    .forEach(e -> {
                        System.out.print(idx.getValue() + ".");
                        System.out.println(e);
                        System.out.println();
                        idx.increment();
                    });
            if (tasksListDTO.getResult().size() < 1) System.out.println("\t\tTasks not found");
            System.out.println("============");
        } else {
            System.out.println("Task loading error");
            Requests.handleResponseError(response);
        }
    }
}
