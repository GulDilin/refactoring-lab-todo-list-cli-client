package guldilin.commands;

import guldilin.error.ExitException;

import java.net.URL;
import java.util.Scanner;

public interface Command {
    void execute(Scanner in, URL baseUrl) throws ExitException;
}
