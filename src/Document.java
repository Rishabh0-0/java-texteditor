import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Document {
    private String currentFileName = "untitled";
    private final List<StringBuilder> lines = new ArrayList<>();
    private final int[] cursor = {0, 0};
    private int desiredColumn = 0;

    public Document () {
        lines.add(new StringBuilder());
    }

    public List<StringBuilder> getLines() {
        return lines;
    }

    public int[] getCursor() {
        return cursor;
    }

    public void insertText(String text) {
        lines.get(cursor[0]).insert(cursor[1], text);
        cursor[1] += text.length();
        desiredColumn = cursor[1];
    }

    public void addNewLine() {
        lines.add(cursor[0] + 1, new StringBuilder());
        cursor[0]++;
        cursor[1] = 0;
        desiredColumn = cursor[1];
    }

    public void deleteLine(int index) {
        if (lines.size() == 1){
            lines.get(0).setLength(0);
            cursor[1] = 0;
            desiredColumn = 0;
            return;
        }
        lines.remove(index);
        cursor[0] = Math.max(0, cursor[0] - 1);
        cursor[1] = Math.min(lines.get(cursor[0]).length(), desiredColumn);
    }

    // File Handling
    public void saveFile(String name){
        name = name.trim();
        String fileName = !name.isEmpty() ? name : currentFileName;
        try {
            File file = new File(fileName + ".txt");
            FileWriter writer = new FileWriter(file.getName());

            for (StringBuilder line: lines){
                writer.write(line.toString() + "\n");
            }
            System.out.println("File saved..");
            writer.close();
        } catch (IOException e) {
            System.out.println("Unable to save file!");
            e.printStackTrace();
        }


    }

    // Navigation
    public void moveLeft(int steps){
        cursor[1] = Math.max(0, cursor[1] - steps);
        desiredColumn = cursor[1];
    }

    public void moveRight(int steps){
        cursor[1] = Math.min(lines.get(cursor[0]).length(), cursor[1] + steps);
        desiredColumn = cursor[1];
    }

    public void moveUp(int steps){
        cursor[0] = Math.max(0, cursor[0] - steps);
        cursor[1] = Math.min(lines.get(cursor[0]).length(), desiredColumn);
    }

    public void moveDown(int steps){
        cursor[0] = Math.min(lines.size() - 1, cursor[0] + steps);
        cursor[1] = Math.min(lines.get(cursor[0]).length(), desiredColumn);
    }

    // Cursor Safety
    public void fixCursor() {
        cursor[0] = Math.max(0, Math.min(cursor[0], lines.size() - 1));
        cursor[1] = Math.max(0, Math.min(cursor[1], lines.get(cursor[0]).length()));
    }

}
