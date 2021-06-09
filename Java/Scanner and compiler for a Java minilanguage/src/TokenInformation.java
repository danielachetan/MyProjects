public class TokenInformation {
    private final String typeCode;
    private final int lineNumber;
    private final String tokenID;
    private final String token;

    public TokenInformation(String typeCode, int lineNumber, String tokenID, String token) {
        this.typeCode = typeCode;
        this.lineNumber = lineNumber;
        this.tokenID = tokenID;
        this.token = token;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public String getTokenID() {
        return tokenID;
    }

    public String getToken() {
        return token;
    }
}
