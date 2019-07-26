package repl;

import lexer.*;
import parser.*;
import ast.*;
import evaluator.*;
import object.*;

import java.util.Scanner;
import java.util.ArrayList;

public class Repl {
  static String PROMPT = ">>";

  public static void Start() {
    Scanner scanner = new Scanner(System.in);

    while(true) {
      System.out.print(PROMPT);

      String input = scanner.nextLine();

      Lexer l = new Lexer(input);
      Parser p = new Parser(l);

      Program program = p.ParseProgram();

      if(p.errors.size() != 0) {
        printParserErrors(p.errors);
        continue;
      }


      object.Object evaluated = Evaluator.Eval(program);

      if(evaluated != null) {
        System.out.println(evaluated.inspect());
      }
    }
  }

  static void printParserErrors(ArrayList<String> errors) {
    for(String s : errors){
      System.out.println(s);
    }
  }
}
