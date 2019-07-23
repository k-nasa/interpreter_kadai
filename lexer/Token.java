public final class Token {
  String type;
  String literal;

  Token(String type, char literal) {
    this.type = type;
    this.literal = String.valueOf(literal);
  }
  Token(String type, String literal) {
    this.type = type;
    this.literal = literal;
  }

  static String lookupIdent(String ident) {
    switch(ident) {
      case "fn":
        return TokenType.FUNCTION;
      case "let":
        return TokenType.LET;
    }
    return null;
  }
}
