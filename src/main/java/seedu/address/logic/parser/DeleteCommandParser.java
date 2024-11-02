package seedu.address.logic.parser;

// import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_CONTACT_NOT_IN_ADDRESS_BOOK;
import static seedu.address.logic.Messages.MESSAGE_NAME_FIELD_MISSING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.contact.Name;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME);

        if (argMultimap.getValue(PREFIX_NAME).isEmpty()) {
            try {
                Index index = ParserUtil.parseIndex(args);
                return new DeleteCommand(index);
            } catch (ParseException ex) {
                throw new ParseException(
                        String.format(MESSAGE_NAME_FIELD_MISSING, DeleteCommand.MESSAGE_USAGE), ex);
            }
        }

        String preamble = argMultimap.getPreamble();
        if (preamble.matches("^[0-9]+$")) {
            throw new ParseException(DeleteCommand.MESSAGE_MULTIPLE_WAYS_FORBIDDEN);
        }

        String str = argMultimap.getValue(PREFIX_NAME).get();
        try {
            Name name = ParserUtil.parseName(str);
            return new DeleteCommand(name);
        } catch (Exception ex) {
            throw new ParseException(MESSAGE_CONTACT_NOT_IN_ADDRESS_BOOK);
            // did not use the 2 parameter exception
        }
    }
}
