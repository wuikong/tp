package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.property.PropertyAddressContainsKeywordsPredicate;

/**
 * Shows only properties whose addresses contain any of the specified keywords.
 */
public class FilterPropertyCommand extends Command {

    public static final String COMMAND_WORD = "filterProperty";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all properties whose addresses contain any"
            + " of the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: " + PREFIX_ADDRESS + "/ADDRESS\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_ADDRESS + "/Clementi Punggol";

    private final PropertyAddressContainsKeywordsPredicate predicate;

    public FilterPropertyCommand(PropertyAddressContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPropertyList(predicate);
        model.updateFilteredPersonList(person -> model.getFilteredPropertyList().stream()
                .anyMatch(property -> person.getProperties().contains(property)));
        return new CommandResult(
                String.format(Messages.MESSAGE_PROPERTIES_LISTED_OVERVIEW, model.getFilteredPropertyList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FilterPropertyCommand)) {
            return false;
        }

        FilterPropertyCommand otherFilterPropertyCommand = (FilterPropertyCommand) other;
        return predicate.equals(otherFilterPropertyCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
