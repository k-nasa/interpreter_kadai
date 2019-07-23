package lexer;

public final class TokenType {
  public static final String ILLEGAL = "ILLEGAL";
  public static final String EOF = "EOF";

  public static final String IDENT = "IDENT";
  public static final String INT = "INT";

  public static final String ASSIGN = "=";
  public static final String PLUS = "+";
  public static final String MINUS = "-";
  public static final String ASTERISK = "*";
  public static final String SLASH = "/";
  public static final String BANG = "!";
  public static final String LT = "<";
  public static final String GT = ">";
  public static final String EQ = "==";
  public static final String NOT_EQ = "!=";

  public static final String COMMA = ",";
  public static final String SEMICOLON = ";";

  public static final String LPAREN = "(";
  public static final String RPAREN = ")";
  public static final String LBRACE = "{";
  public static final String RBRACE = "}";

  public static final String FUNCTION = "FUNCTION";
  public static final String LET = "LET";
  public static final String TRUE = "TRUE";
  public static final String FALSE = "FALSE";
  public static final String IF = "IF";
  public static final String ELSE = "ELSE";
  public static final String RETURN = "RETURN";
}
