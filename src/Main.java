import java.io.BufferedReader;
import java.io.InputStreamReader;


public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Document doc = new Document();
        CommandRegistry registry = new CommandRegistry();


        while (true) {
            Renderer.display(doc);

            System.out.println("\n\n>");
            String[] tokens = br.readLine().split(" ");
            String cmd = tokens[0];

            if (cmd.equals("quit") || cmd.equals("q")) break;

            if (!registry.executeCommand(cmd, doc, tokens)) {
                System.out.println("Invalid Command!");
            }

            doc.fixCursor();
        }

    }
}