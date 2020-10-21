package seedu.address.logic.commands.play;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

import static java.util.Objects.requireNonNull;

public class StopCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Playmode Stopped!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.endGame();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
