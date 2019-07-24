build:
	javac Main.java lexer/Lexer.java lexer/Token.java lexer/TokenType.java ast/expressions/Expression.java ast/expressions/Identifier.java ast/statements/LetStatement.java ast/Node.java ast/Program.java ast/Statement.java parser/Parser.java ast/statements/ReturnStatement.java ast/expressions/ExpressionStatement.java ast/expressions/IntegerLiteral.java ast/expressions/PrefixExpression.java ast/expressions/InfixExpression.java  -Xlint:unchecked
run:
	java Main
clean:
	rm ./**/*.class
