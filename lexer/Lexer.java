public class Lexer {
  String input;
  int position;
  int readPosition;
  char ch;

  Lexer(String input) {
    this.input = input;
    this.readChar();
  }

  void readChar() {
    if(this.readPosition >= this.input.length()) {
      this.ch = '\0';
    } else {
      this.ch = this.input.charAt(this.readPosition);
    }

    this.position = this.readPosition;
    this.readPosition += 1;
  }

  Token nextToken() {
    Token token = new Token(TokenType.ILLEGAL, '\0');

    this.skipWhitespace();

    switch(this.ch) {
      case '=':
        token = new Token(TokenType.ASSIGN, this.ch);
        break;
      case ';':
        token = new Token(TokenType.SEMICOLON, this.ch);
        break;
      case '(':
        token = new Token(TokenType.LPAREN, this.ch);
        break;
      case ')':
        token = new Token(TokenType.RPAREN, this.ch);
        break;
      case ',':
        token = new Token(TokenType.COMMA, this.ch);
        break;
      case '+':
        token = new Token(TokenType.PLUS, this.ch);
        break;
      case '{':
        token = new Token(TokenType.LBRACE, this.ch);
        break;
      case '}':
        token = new Token(TokenType.RBRACE, this.ch);
        break;
      case '\0':
        token = new Token(TokenType.EOF, this.ch);
        break;
      default:
        if (isLetter(this.ch)) {
          String ident = this.readIdent();
          token = new Token(Token.lookupIdent(ident), ident);

          return token;
        } else if(isDigital(this.ch)){
          String digital = this.readNumber();
          token = new Token(TokenType.INT, digital);

          return token;
        } else {
          token = new Token(TokenType.ILLEGAL, '\0');
        }
    }

    this.readChar();
    return token;
  }

  String readIdent() {
    int position = this.position;

    while(isLetter(this.ch)) {
      this.readChar();
    }

    return this.input.substring(position, this.position);
  }

  String readNumber() {
    int position = this.position;

    while(isDigital(this.ch)) {
      this.readChar();
    }

    return this.input.substring(position, this.position);
  }

  void skipWhitespace() {
    while (isSpace(this.ch)) {
      this.readChar();
    }
  }


  boolean isLetter(char ch) {
    return
      'a' <= ch && ch <= 'z'
      || 'A' <= ch && ch <= 'Z'
      || ch == '_';
  }

  boolean isSpace(char ch) {
    return ch == ' ' || ch == '\t' || ch == '\n' || ch == '\r';
  }

  boolean isDigital(char ch) {
    return '0' <= ch && ch <= '9'
  }

}
