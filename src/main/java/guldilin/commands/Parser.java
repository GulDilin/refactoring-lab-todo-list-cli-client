package guldilin.commands;

import guldilin.error.ErrorMessage;

public class Parser {
    public Command parse(String commandLine) {
        switch (Integer.parseInt(commandLine)) {
            case 1: return new CreateCommand();
            case 4: return new ExitCommand();
            default: {
                System.out.println(ErrorMessage.COMMAND_NOT_FOUND);
            }
        }
        return null;
    }
}
