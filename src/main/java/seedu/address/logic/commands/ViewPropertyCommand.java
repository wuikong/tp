package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.property.Property;

public class ViewPropertyCommand extends Command {

    public static final String COMMAND_WORD = "viewProperty";

    private final Index index;

    public ViewPropertyCommand(Index index) {
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {

        List<Person> lastShownList = model.getFilteredPersonList();
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToView = lastShownList.get(index.getZeroBased());

        if (personToView.getProperty().isNull()) {
            throw new CommandException(Messages.MESSAGE_INVALID_NO_PROPERTY);
        }

        Property property = personToView.getProperty();

        return new CommandResult(property.toString());
    }
}