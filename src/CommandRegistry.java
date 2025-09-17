import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CommandRegistry {
    private final Map<String, Command> commands = new HashMap<>();

    public CommandRegistry() {
        registerCommands();
    }

    public void registerCommands(){
        commands.put("append", (doc, args) -> {
            String text = String.join(" ", Arrays.copyOfRange(args, 1, args.length));
            doc.insertText(text);
        });
        commands.put("a", commands.get("append"));

        commands.put("newLine", (doc, args) -> doc.addNewLine());
        commands.put("nl", commands.get("newLine"));

        commands.put("insert", (doc, args) -> {
            if (args.length < 3) {
                System.out.println("Usage: insert <lineNum> <text>");
                return;
            }
            int lineNum = Integer.parseInt(args[1]);
            String text = String.join(" ", Arrays.copyOfRange(args, 2, args.length));

            while (doc.getLines().size() < lineNum + 1){
                doc.addNewLine();
            }

            int[] cursor = doc.getCursor();
            cursor[0] = lineNum;
            cursor[1] = doc.getLines().get(lineNum).length();

            doc.insertText(text);
        });

        commands.put("left", (doc, args) -> {
           int steps = args.length > 1 ? Integer.parseInt(args[1]) : 1;
           doc.moveLeft(steps);
        });

        commands.put("right", (doc, args) -> {
            int steps = args.length > 1 ? Integer.parseInt(args[1]) : 1;
            doc.moveRight(steps);
        });

        commands.put("up", (doc, args) -> {
            int steps = args.length > 1 ? Integer.parseInt(args[1]) : 1;
            doc.moveUp(steps);
        });

        commands.put("down", (doc, args) -> {
            int steps = args.length > 1 ? Integer.parseInt(args[1]) : 1;
            doc.moveDown(steps);
        });

        commands.put("delete", (doc, args) -> {
            int lineNum = doc.getCursor()[0];
            if (args.length > 1) lineNum = Integer.parseInt(args[1]);
            doc.deleteLine(lineNum);
        });
        commands.put("del", commands.get("delete"));

        commands.put("save", (Document doc, String[] args) -> {
            String name = args.length == 2 ? args[1] : "";
            doc.saveFile(name);
        });
    }

    public boolean executeCommand(String name, Document doc, String[] args){
        Command command = commands.get(name);
        if (command != null){
            command.execute(doc, args);
            return true;
        }
        return false;
    }
}
