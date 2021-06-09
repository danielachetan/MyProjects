public class TableEntry {
    private String name;
    private String kind;
    private String type;
    private int lineOfDeclaration;
    private String accessModifier;
    private String scope;

    public TableEntry(String name, String kind, String type, int lineOfDeclaration, String accessModifier,
                      String scope) {
        this.name = name;
        this.kind = kind;
        this.type = type;
        this.lineOfDeclaration = lineOfDeclaration;
        this.accessModifier = accessModifier;
        this.scope = scope;
    }

    public String getName() {
        return name;
    }

    public String getKind() {
        return kind;
    }

    public String getType() {
        return type;
    }

    public int getLineOfDeclaration() {
        return lineOfDeclaration;
    }

    public String getAccessModifier() {
        return accessModifier;
    }

    public String getScope() {
        return scope;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setLineOfDeclaration(int lineOfDeclaration) {
        this.lineOfDeclaration = lineOfDeclaration;
    }

    public void setAccessModifier(String accessModifier) {
        this.accessModifier = accessModifier;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
