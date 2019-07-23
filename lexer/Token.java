package lexer;

import lexer.TokenType;

public final class Token {
  public String type;
  public String literal;

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
      case "true":
        return TokenType.TRUE;
      case "false":
        return TokenType.FALSE;
      case "if":
        return TokenType.IF;
      case "else":
        return TokenType.ELSE;
      case "return":
        return TokenType.RETURN;
      default:
        return TokenType.IDENT;
    }
  }

  String type() {
    return this.type;
  }
}
