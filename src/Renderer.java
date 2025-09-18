import java.util.Arrays;
import java.util.List;

public class Renderer {
    public static void display(Document doc){
        System.out.println("\033[2J");
        System.out.println("Cursor:" + Arrays.toString(doc.getCursor()));
        System.out.println();

        int[] cursor = doc.getCursor();
        List<StringBuilder> lines = doc.getLines();

        for (int i = 0; i < lines.size(); i++){
            System.out.print(i + " ");
            if (i == cursor[0]){
                String line = lines.get(i).toString();
                int col = cursor[1];
                String before = line.substring(0, Math.min(col, line.length()));
                char cursorChar = col < line.length() ? line.charAt(col) : ' ';
                String after = col < line.length() ? line.substring(col + 1) : "";

                // Display highlighted Cursor
                System.out.print(before);
                System.out.print("[" + cursorChar + "]");
                System.out.println(after); // \n
            } else {
                System.out.println(lines.get(i));
            }
        }
    }
}
