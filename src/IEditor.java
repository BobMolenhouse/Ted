public interface IEditor {

    /* processes the given editor command */
    boolean processCommand(String command);

    /* returns the line at the given line number */
    String getLine(int lineNbr);

    /* returns the current line */
    String getCurrentLine();



}