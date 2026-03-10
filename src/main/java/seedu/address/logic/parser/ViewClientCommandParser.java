package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.Arrays;

import seedu.address.logic.commands.ViewClientCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new ViewClientCommand object
 */
public class ViewClientCommandParser implements Parser<ViewClientCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ViewClientCommand
     * and returns a ViewClientCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public ViewClientCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME);

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewClientCommand.MESSAGE_USAGE));
        }

        String name = argMultimap.getValue(PREFIX_NAME).orElse("");

        String[] nameKeywords = name.split("\\s+");

        return new ViewClientCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}
