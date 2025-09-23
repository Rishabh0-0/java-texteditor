public class Operation {
    private String type;
    private String content;
    private int line;
    private int pos;
    private int[] cursor;

    public Operation(String type, String content, int line, int pos, int[] cursor) {
        this.type = type;
        this.content = content;
        this.line = line;
        this.pos = pos;
        this.cursor = cursor;
    }

    public String getType() {
        return type;
    }

    public String getContent() {
        return content;
    }

    public int getLine() {
        return line;
    }

    public int getPos() {
        return pos;
    }

    public int[] getCursor() {
        return cursor;
    }
}
