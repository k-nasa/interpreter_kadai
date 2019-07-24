package parser;

import lexer.*;
import ast.*;
import java.util.ArrayList;

public class Parser {
  static int LOWESE = 1;
  static int EQUALS = 2;
  static int LESSGRATER = 3;
  static int SUM = 4;
  static int PRODUCT = 5;
  static int PREFIX = 6;
  static int CALL = 7;

  Lexer l;

  Token currentToken;
  Token peekToken;

  ArrayList<String> errors;

  Parser(Lexer l) {
    this.l = l;

    this.errors = new ArrayList<String>();

    // currentTokenとpeekTokenをセットする
    this.nextToken();
    this.nextToken();

  }

  void nextToken() {
    this.currentToken = this.peekToken;
    this.peekToken = this.l.nextToken();
  }

  public Program ParseProgram() {
    Program program = new Program();

    while(this.currentToken.type != TokenType.EOF) {
      Statement stmt = this.parseStatement();

      if(stmt != null) {
        program.statements.add(stmt);
      }

      this.nextToken();
    }

    return program;
  }

  Statement parseStatement() {
    switch(this.currentToken.type) {
      case TokenType.LET:
        return this.parseLetStatement();
      case TokenType.RETURN:
        return this.parseReturnStatement();
      default:
          return this.parseExpressionStatment();
    }
  }

  LetStatement parseLetStatement() {
    LetStatement stmt = new LetStatement();

    if(!this.expectPeek(TokenType.IDENT))
      return null;

    stmt.name = new Identifier(this.currentToken, this.currentToken.literal);

    if(!this.expectPeek(TokenType.ASSIGN))
      return null;

    while(!this.currentTokenIs(TokenType.SEMICOLON))
      this.nextToken();

    return stmt;
  }

  void peekError(String type) {
    this.errors.add("expected next token is " + type + ", got " + this.peekToken.type);
  }

  ReturnStatement parseReturnStatement() {
    ReturnStatement stmt = new ReturnStatement();

    this.nextToken();

    while(!this.currentTokenIs(TokenType.SEMICOLON))
      this.nextToken();

    return stmt;
  }

  ExpressionStatement parseExpressionStatment() {
    ExpressionStatement stmt = new ExpressionStatement();

    stmt.token = this.currentToken;
    stmt.expression = this.parseExpression(LOWESE);

    if(this.peekTokenIs(TokenType.SEMICOLON))
      this.nextToken();

    return stmt;
  }

  Expression parseExpression(int precedunce) {
    Expression left = parsePrefix(this.currentToken.type);

    return left;
  }

  Expression parsePrefix(String t) {
    switch(t) {
      case TokenType.IDENT:
        return new Identifier(this.currentToken, this.currentToken.literal);
      case TokenType.INT:
        return parseIntegerLiteral();
    }

    return null;
  }

  Expression parseIntegerLiteral() {
    IntegerLiteral literal = new IntegerLiteral();

    literal.token = this.currentToken;
    literal.value = Integer.valueOf(this.currentToken.literal);

    return literal;
  }

  boolean currentTokenIs(String type) {
    return this.currentToken.type == type;
  }

  boolean peekTokenIs(String type) {
    return this.peekToken.type == type;
  }

  boolean expectPeek(String type) {
    if(this.peekTokenIs(type)) {
      this.nextToken();
      return true;
    }

    this.peekError(type);
    return false;
  }
}
