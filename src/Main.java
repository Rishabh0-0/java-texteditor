import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        List<StringBuilder> page = new ArrayList<>();
        page.add(new StringBuilder());
        int[] cursor = {0, 0};
        int desiredColumn = cursor[1];

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            System.out.println("\033[2J");
            System.out.println("Cursor:" + Arrays.toString(cursor));
            System.out.println();

            // Document display
            for (int i = 0; i < page.size(); i++){
                System.out.print(i + " ");
                if (i == cursor[0]){
                    String line = new String(page.get(i));
                    String before = line.substring(0, cursor[1]);
                    char cursorChar = cursor[1] < line.length() ? line.charAt(cursor[1]) : ' ';
                    String after = cursor[1] < line.length() ? line.substring(cursor[1] + 1, line.length()) : "";

                    // Display highlighted Cursor
                    System.out.print(before);
                    System.out.print("[" + cursorChar + "]");
                    System.out.print(after + "\n");
                } else {
                    System.out.print(page.get(i) + "\n");
                }
            }


            // Handle Command prompt tokens
            System.out.print("\n\n> ");
            String[] tokens = br.readLine().split(" ");
            String command = tokens[0];
            int lineNum = cursor[0];
            String content = "";

            // Handle Commands
            switch (command) {
                case "append", "a":
                    content = String.join(" ", Arrays.copyOfRange(tokens, 1, tokens.length));
                    page.get(cursor[0]).insert(cursor[1], content);
                    cursor[1] += content.length();
                    desiredColumn = cursor[1];
                    break;

                case "insert", "i":
                    content = String.join(" ", Arrays.copyOfRange(tokens, 2, tokens.length));
                    lineNum = Integer.parseInt(tokens[1]);
                    while (page.size() < lineNum + 1){
                        page.add(new StringBuilder());
                    }
                    page.get(lineNum).append(content);
                    cursor[0] = lineNum;
                    cursor[1] = page.get(lineNum).length();
                    desiredColumn = cursor[1];
                    break;

                case "delete", "del":
                    int deleteIndex = cursor[0];
                    if (tokens.length > 1){
                        for (char c: tokens[1].toCharArray()){
                            if (!Character.isDigit(c)){
                                break;
                            }
                        }
                        deleteIndex = Integer.parseInt(tokens[1]);
                    }

                    if (deleteIndex < 0 || deleteIndex >= page.size()){
                        System.out.println("Invalid index");
                        break;
                    }

                    if (page.size() == 1){
                        page.get(0).delete(0, page.get(0).length());
                        cursor[1] = 0;
                        desiredColumn = 0;
                    } else {
                        page.remove(deleteIndex);
                        if (deleteIndex == cursor[0]){
                            cursor[0] = Math.max(0, cursor[0] - 1);
                            cursor[1] = Math.min(page.get(cursor[0]).length(), desiredColumn);
                        } else if (deleteIndex < cursor[0]){
                            cursor[0]--;
                            cursor[1] = Math.min(page.get(cursor[0]).length(), desiredColumn);
                        }
                    }
                    break;

                case "addNewLine", "nl":
                    page.add(cursor[0] + 1, new StringBuilder());
                    cursor[0]++;
                    cursor[1] = 0;
                    desiredColumn = cursor[1];
                    break;

                case "left":
                    int leftSteps = 1;
                    if (tokens.length > 1){
                        for (char c: tokens[1].toCharArray()){
                            if (!Character.isDigit(c)){
                                break;
                            }
                        }
                        leftSteps = Integer.parseInt(tokens[1]);
                    }
                    cursor[1] = Math.max(0, cursor[1] - leftSteps);
                    desiredColumn = cursor[1];
                    break;

                case "right":
                    int rightSteps = 1;
                    if (tokens.length > 1){
                        for (char c: tokens[1].toCharArray()){
                            if (!Character.isDigit(c)){
                                break;
                            }
                        }
                        rightSteps = Integer.parseInt(tokens[1]);
                    }
                    cursor[1] = Math.min(page.get(cursor[0]).length(), cursor[1] + rightSteps);
                    desiredColumn = cursor[1];
                    break;

                case "up":
                    int upSteps = 1;
                    if (tokens.length > 1){
                        for (char c: tokens[1].toCharArray()){
                            if (!Character.isDigit(c)){
                                break;
                            }
                        }
                        upSteps = Integer.parseInt(tokens[1]);
                    }
                    cursor[0] = Math.max(0, cursor[0] - upSteps);
                    cursor[1] = Math.min(page.get(cursor[0]).length(), desiredColumn);
                    break;

                case "down":
                    int downSteps = 1;
                    if (tokens.length > 1){
                        for (char c: tokens[1].toCharArray()){
                            if (!Character.isDigit(c)){
                                break;
                            }
                        }
                        downSteps = Integer.parseInt(tokens[1]);
                    }
                    cursor[0] = Math.min(page.size() - 1, cursor[0] + downSteps);
                    cursor[1] = Math.min(page.get(cursor[0]).length(), desiredColumn);
                    break;

                case "quit", "q":
                    return;

                default:
                    System.out.println("invalid command! " + command);
                    break;
            }
            System.out.println(desiredColumn);
            fixCursor(page, cursor);
        }
    }

    public static void fixCursor(List<StringBuilder> page, int[] cursor){
        cursor[0] = Math.max(0, Math.min(cursor[0], page.size() - 1));
        cursor[1] = Math.max(0, Math.min(cursor[1], page.get(cursor[0]).length()));
    }
}