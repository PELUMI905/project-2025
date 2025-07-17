NAME:ADESINA OLUWAPELUMI SAMUEL 
COURSE



import java.util.regex.*;
import java.util.ArrayList;
import java.util.List;

public class Scanner {

    // Define token types
    private enum TokenType {
        NUMBER("\\d+(\\.\\d*)?"),  // Integer or decimal number
        IDENT("[A-Za-z_][\\w]*"),  // Identifiers
        OP("[+\\-*/]"),            // Arithmetic operators
        NEWLINE("\\n"),            // Line endings
        SKIP("[ \\t]+"),           // Skip over spaces and tabs
        MISMATCH(".");             // Any other character

        public final String pattern;

        TokenType(String pattern) {
            this.pattern = pattern;
        }
    }

    // Token class to hold type and value
    private static class Token {
        public final TokenType type;
        public final String value;
        public final int line;
        public final int column;

        public Token(TokenType type, String value, int line, int column) {
            this.type = type;
            this.value = value;
            this.line = line;
            this.column = column;
        }

        @Override
        public String toString() {
            return String.format("(%s, '%s', %d, %d)", type.name(), value, line, column);
        }
    }

    // Scanner method to tokenize input
    public static List<Token> scan(String code) {
        List<Token> tokens = new ArrayList<>();
        StringBuilder tokenPatternsBuffer = new StringBuilder();
        for (TokenType tokenType : TokenType.values())
            tokenPatternsBuffer.append(String.format("|(?<%s>%s)", tokenType.name(), tokenType.pattern));
        Pattern tokenPatterns = Pattern.compile(tokenPatternsBuffer.substring(1));

        Matcher matcher = tokenPatterns.matcher(code);
        int line = 1;
        int lineStart = 0;

        while (matcher.find()) {
            for (TokenType t : TokenType.values()) {
                if (matcher.group(t.name()) != null) {
                    String value = matcher.group(t.name());
                    if (t == TokenType.NEWLINE) {
                        lineStart = matcher.end();
                        line++;
                    } else if (t == TokenType.SKIP) {
                        // Do nothing
                    } else if (t == TokenType.MISMATCH) {
                        throw new RuntimeException("Unexpected character: " + value + " at line " + line);
                    } else {
                        tokens.add(new Token(t, value, line, matcher.start() - lineStart));
                    }
                    break;
                }
            }
        }
        return tokens;
    }

    // Main method for testing
    public static void main(String[] args) {
        String code = "x = 3 + 42 * (s - t)\n";
        List<Token> tokens = scan(code);
        for (Token token : tokens) {
            System.out.println(token);
        }
    }
}
