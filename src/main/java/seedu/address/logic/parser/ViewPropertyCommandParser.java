public class ViewPropertyCommandParser implements Parser<ViewPropertyCommand> {

    public ViewPropertyCommand parse(String args) throws ParseException {

        Index index = ParserUtil.parseIndex(args);

        return new ViewPropertyCommand(index);
    }
}