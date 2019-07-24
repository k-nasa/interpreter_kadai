build:
	javac Main.java lexer/Lexer.java lexer/Token.java lexer/TokenType.java ast/Expression.java ast/Identifier.java ast/statements/LetStatement.java ast/Node.java ast/Program.java ast/Statement.java parser/Parser.java ast/statements/ReturnStatement.java ast/ExpressionStatement.java ast/IntegerLiteral.java ast/PrefixExpression.java ast/InfixExpression.java  -Xlint:unchecked
run:
	java Main
clean:
	rm ./**/*.class
