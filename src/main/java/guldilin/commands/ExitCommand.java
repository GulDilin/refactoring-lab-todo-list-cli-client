package guldilin.commands;

import guldilin.error.ExitException;

import java.net.URL;
import java.util.Scanner;

public class ExitCommand implements Command {
    @Override
    public void execute(Scanner in, URL baseUrl) throws ExitException {
        System.out.println("Bye");
        throw new ExitException();
    }
}
