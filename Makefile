build:
	javac Main.java lexer/Lexer.java lexer/Token.java lexer/TokenType.java ast/Expression.java ast/Identifier.java ast/LetStatement.java ast/Node.java ast/Program.java ast/Statement.java parser/Parser.java ast/ReturnStatement.java ast/ExpressionStatement.java ast/IntegerLiteral.java ast/PrefixExpression.java ast/InfixExpression.java  ast/IfExpression.java ast/BooleanLiteral.java ast/BlockStatement.java -Xlint:unchecked
run:
	java Main
clean:
	rm ./**/*.class
