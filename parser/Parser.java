package parser;

import lexer.*;
import ast.*;
import java.util.*;

public class Parser {
  static int LOWESE = 1;
  static int EQUALS = 2;
  static int LESSGRATER = 3;
  static int SUM = 4;
  static int PRODUCT = 5;
  static int PREFIX = 6;
  static int CALL = 7;
  static int NOT_FOUND = 0;

  public Lexer l;

  public Token currentToken;
  public Token peekToken;

  public ArrayList<String> errors;

  public Parser(Lexer l) {
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

    while(!this.peekTokenIs(TokenType.SEMICOLON) && precedunce < this.peekPrecedence()) {
      this.nextToken();

      left = parseInfixExpression(left);
    }

    return left;
  }

  Expression parsePrefix(String t) {
    switch(t) {
      case TokenType.IDENT:
        return new Identifier(this.currentToken, this.currentToken.literal);
      case TokenType.INT:
        return parseIntegerLiteral();
      case TokenType.BANG:
        return parsePrefixExpression();
      case TokenType.MINUS:
        return parsePrefixExpression();
      default:
        this.noPrefixParseError(t);
    }

    return null;
  }

  Expression parseInfixExpression(Expression left) {
    InfixExpression expression = new InfixExpression();
    expression.token = this.currentToken;
    expression.operator = this.currentToken.literal;

    expression.left = left;

    int precedunce = this.currentPrecedence();
    this.nextToken();

    expression.right = this.parseExpression(precedunce);

    return expression;
  }

  Expression parsePrefixExpression() {
  PrefixExpression expression = new PrefixExpression();

  expression.token = this.currentToken;
  expression.operator = this.currentToken.literal;

    this.nextToken();

  expression.right = this.parseExpression(PREFIX);

  return expression;
  }

  Expression parseIntegerLiteral() {
    IntegerLiteral literal = new IntegerLiteral();

    literal.token = this.currentToken;
    literal.value = Integer.valueOf(this.currentToken.literal);

    return literal;
  }

  ReturnStatement parseReturnStatement() {
    ReturnStatement stmt = new ReturnStatement();

    this.nextToken();

    while(!this.currentTokenIs(TokenType.SEMICOLON))
      this.nextToken();

    return stmt;
  }

  int currentPrecedence() {
    if(getPrecedunces(this.currentToken.type) != NOT_FOUND) {
      return getPrecedunces(this.currentToken.type);
    }

    return LOWESE;
  }

  int peekPrecedence() {
    if(getPrecedunces(this.peekToken.type) != NOT_FOUND) {
      return getPrecedunces(this.peekToken.type);
    }

    return LOWESE;
  }

  void peekError(String type) {
    this.errors.add("expected next token is " + type + ", got " + this.peekToken.type);
  }

  void noPrefixParseError(String t) {
    this.errors.add("no prefix parse function for " + t + " found");
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

  int getPrecedunces(String t) {
    switch(t) {
      case TokenType.EQ:
        return EQUALS;
      case TokenType.NOT_EQ:
        return EQUALS;

      case TokenType.LT:
        return LESSGRATER;
      case TokenType.GT:
        return LESSGRATER;

      case TokenType.PLUS:
        return SUM;
      case TokenType.MINUS:
        return SUM;

      case TokenType.SLASH:
        return PRODUCT;
      case TokenType.ASTERISK:
        return PRODUCT;
    }
    return NOT_FOUND;
  }
}
