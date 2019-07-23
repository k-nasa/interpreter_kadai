import java.util.Scanner;

public class Repl {
  static String PROMPT = ">>";

  public static void Start() {
    Scanner scanner = new Scanner(System.in);

    while(true) {
      System.out.print(PROMPT);

      String input = scanner.nextLine();

      Lexer l = new Lexer(input);

      Token token;

      for(token = l.nextToken(); token.type != TokenType.EOF; token = l.nextToken()) {
        System.out.println(token);
      }
    }
  }
}
