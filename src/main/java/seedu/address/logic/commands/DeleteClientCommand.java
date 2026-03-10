package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Deletes a client identified using its displayed index from the address book.
 */
public class DeleteClientCommand extends Command {

    public static final String COMMAND_WORD = "deleteClient";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the client identified by the index number used in the displayed client list.\n"
            + "All properties of the client will also be deleted.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_CLIENT_SUCCESS = "Deleted Client: %1$s";

    private final Index targetIndex;

    public DeleteClientCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToDelete = lastShownList.get(targetIndex.getZeroBased());

        // Assume that the format will include property information if it exists
        // Otherwise, call Messages.format on editedPerson instead of personToDelete
        String formattedPersonToDelete = Messages.format(personToDelete);

        if (!personToDelete.getProperties().isEmpty()) {
            DeletePropertyCommand deletePropertyCommand = new DeletePropertyCommand(targetIndex);
            deletePropertyCommand.execute(model);
        }

        Person editedPerson = lastShownList.get(targetIndex.getZeroBased());

        model.deletePerson(editedPerson);
        return new CommandResult(String.format(MESSAGE_DELETE_CLIENT_SUCCESS, formattedPersonToDelete));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteClientCommand)) {
            return false;
        }

        DeleteClientCommand otherDeleteCommand = (DeleteClientCommand) other;
        return targetIndex.equals(otherDeleteCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
