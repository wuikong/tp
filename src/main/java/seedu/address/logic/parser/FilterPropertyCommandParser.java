package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SIZE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TYPE;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import seedu.address.logic.commands.FilterPropertyCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.property.Price;
import seedu.address.model.property.PropertyMatchesFilterPredicate;
import seedu.address.model.property.PropertyType;
import seedu.address.model.property.Size;

/**
 * Parses input arguments and creates a new FilterPropertyCommand object.
 */
public class FilterPropertyCommandParser implements Parser<FilterPropertyCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FilterPropertyCommand
     * and returns a FilterPropertyCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public FilterPropertyCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(
                args, PREFIX_ADDRESS, PREFIX_TYPE, PREFIX_PRICE, PREFIX_SIZE);

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, FilterPropertyCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_ADDRESS, PREFIX_TYPE, PREFIX_PRICE, PREFIX_SIZE);

        List<String> addressKeywords = argMultimap.getValue(PREFIX_ADDRESS)
                .map(this::parseAddressKeywords)
                .orElse(List.of());
        List<String> typeKeywords = List.of();
        if (argMultimap.getValue(PREFIX_TYPE).isPresent()) {
            typeKeywords = parseTypeKeywords(argMultimap.getValue(PREFIX_TYPE).get());
        }

        Optional<long[]> optionalPriceRange = Optional.empty();
        if (argMultimap.getValue(PREFIX_PRICE).isPresent()) {
            optionalPriceRange = parseRange(argMultimap.getValue(PREFIX_PRICE).get(), PREFIX_PRICE);
        }

        Optional<long[]> optionalSizeRange = Optional.empty();
        if (argMultimap.getValue(PREFIX_SIZE).isPresent()) {
            optionalSizeRange = parseRange(argMultimap.getValue(PREFIX_SIZE).get(), PREFIX_SIZE);
        }

        if (addressKeywords.isEmpty() && typeKeywords.isEmpty()
                && optionalPriceRange.isEmpty() && optionalSizeRange.isEmpty()) {
            throw new ParseException(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, FilterPropertyCommand.MESSAGE_USAGE));
        }

        long[] priceRange = optionalPriceRange.orElse(new long[] {0, Long.MAX_VALUE});
        long[] sizeRange = optionalSizeRange.orElse(new long[] {0, Long.MAX_VALUE});

        return new FilterPropertyCommand(new PropertyMatchesFilterPredicate(
                addressKeywords, typeKeywords, priceRange[0], priceRange[1], sizeRange[0], sizeRange[1]));
    }

    private List<String> parseAddressKeywords(String rawAddress) {
        return Arrays.stream(rawAddress.trim().split("\\s+"))
                .filter(keyword -> !keyword.isBlank())
                .collect(Collectors.toList());
    }

    private List<String> parseTypeKeywords(String rawType) throws ParseException {
        String[] typeKeywords = rawType.trim().split("\\s+");
        if (rawType.trim().isBlank()) {
            throw new ParseException(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, FilterPropertyCommand.MESSAGE_USAGE));
        } else if (typeKeywords.length != 1 || !PropertyType.isValidPropertyType(typeKeywords[0])) {
            throw new ParseException(MESSAGE_INVALID_TYPE);
        }
        return List.of(typeKeywords[0]);
    }

    private Optional<long[]> parseRange(String rawRange, Prefix prefix) throws ParseException {
        String[] parts = rawRange.trim().split("\\s+");

        if (parts.length != 2 || parts[0].isBlank() || parts[1].isBlank()) {
            throw new ParseException(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, FilterPropertyCommand.MESSAGE_USAGE));
        }

        long first = parseNumericBoundary(parts[0], prefix);
        long second = parseNumericBoundary(parts[1], prefix);
        if (first > second) {
            throw new ParseException(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, FilterPropertyCommand.MESSAGE_USAGE));
        }

        return Optional.of(new long[] {first, second});
    }

    private long parseNumericBoundary(String rawValue, Prefix prefix) throws ParseException {
        if (prefix.equals(PREFIX_PRICE)) {
            Price price = ParserUtil.parsePrice(rawValue);
            return Long.parseLong(price.value);
        }
        if (prefix.equals(PREFIX_SIZE)) {
            Size size = ParserUtil.parseSize(rawValue);
            return Long.parseLong(size.value);
        }
        throw new ParseException(String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, FilterPropertyCommand.MESSAGE_USAGE));
    }
}
