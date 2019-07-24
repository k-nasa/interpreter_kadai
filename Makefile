build:
	javac Main.java lexer/Lexer.java lexer/Token.java lexer/TokenType.java ast/Expression.java ast/Identifier.java ast/LetStatement.java ast/Node.java ast/Program.java ast/Statement.java ast/Parser.java ast/ReturnStatement.java ast/ExpressionStatement.java -Xlint:unchecked
run:
	java Main
