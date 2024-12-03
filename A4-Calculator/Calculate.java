import java.util.Scanner;

import javax.management.RuntimeErrorException;

import java.io.StringReader;
import java.util.ArrayDeque;

/** 
 * Class to interpret and compute the result of arithmetic expressions 
 * in INFIX format - 
 */
public class Calculate {

  /**
   * Set precedence for the operators
   * @param operator an object containing the operator
   * @return an int representing the level of precedence
   */
  public static int precedence(Object operator){
    if((operator.equals('+')) || (operator.equals('-'))){
      return 1;
    }
    else if((operator.equals('*'))||operator.equals('/')){
      return 2;
    }
    else if(operator.equals('^') || operator.equals('(')){
      return 3;
    }
    else{
      return 0;
    }
  }

  /**
   * Converting String inputs into a queue
   * @param input A string containing the calculation
   * @return a queue consisting of every element of calculation
   */
  public static ArrayDeque<Object> convertInfix(String input){
    // Read the token and store it as a *queue*
    ArrayDeque<Object> token = Tokenizer.readTokens(input);

    // Declare a variable to store the *output queue*
    ArrayDeque<Object> queue = new ArrayDeque<Object>();

    // Declare a *stack* to store the operators
    ArrayDeque<Object> stack = new ArrayDeque<Object>();

    // Use loop to read through each element in token
    while(token.size()!=0){
      // Declare a variable to store each element in the token
      Object element = token.removeFirst();

      // If: token is a NUMBER
      // Add it to the output Queue
      if(element instanceof Double){
        queue.add(element);
      }
      // If: token is an OPERATOR
      else{
        // If: element is left associative operators
        // Push the operator into stack
        if(stack.isEmpty()){
          stack.push(element);
        }

        // If: element == "("
        // Push element to stack because it has highest precedence
        else if(element.equals("(")){
          stack.push(element);
        }

        // If: element == ")"
        //     If: there is a "("
        //         Pop operators of the stack and add them to output queue
        //         untill the top element of stack is "("
        //     If: there is no "("
        //         Throw a exception: mismatched parentheses
        else if(element.equals(")")){

          if (stack.isEmpty() || !stack.contains("(")) {
            throw new RuntimeException("Java Calculate: Error - mismatched parentheses");
          }

          if (!stack.isEmpty() && stack.contains("(")) {
            while(!stack.pop().equals("(")){
              queue.add(stack.pop());
            }

          }

          // Here I encounter a problem that the operator inside () cannot be added into queue
          // I mannually add them, but I wonder whether there are other ways to do it

          if (stack.contains("+")) {
            queue.add(stack.remove("+"));
          }
          if (stack.contains("-")) {
            queue.add(stack.remove("-"));
          }
          if (stack.contains("*")) {
            queue.add(stack.remove("*"));
          }
          if (stack.contains("/")) {
            queue.add(stack.remove("/"));
          }
          if (stack.contains("^")) {
            queue.add(stack.remove("^"));
          }

          stack.remove("(");
          
          // javac Calculate.java
          // java Calculate "9-0"
        }

        // If: current last operator in stack 
        //     has HIGHER/EQUAL precedence with the element
        // Pop out the current last operator AND add it into output queue
        // Push element to stack
        else{
          if(((element.equals("+"))||(element.equals("-")))||((element.equals("*"))||(element.equals("/")))){
            while(!(stack.isEmpty())&& precedence(element)<=precedence(stack.getFirst())) {
              queue.add(stack.pop());
              stack.push(element);
            }
          }

          // If: exponential symbol
          // Push ^ in stack
          //     If: the element after ^ is a double, add the double to queue
          //     If: the element after ^ is an operator, pop ^ to queue
          if(element.equals("^")){
            while(!(token.isEmpty())) {
              stack.push(element);
  
              if(token.getFirst()instanceof Double){
                queue.add(token.getFirst());
              }
              else{
                queue.add(stack.pop());
              }
            }
          }
        }
        
      }
    }

    if(token.size()==0 && stack.size()!=0){
      if(stack.getFirst().equals('(')){
        System.out.println(stack);
        throw new RuntimeException("Java Infix: Error - Mismatched Parentheses");
      }
      else{
        while(stack.size()!=0){
          queue.add(stack.pop());
        }
      }
      return queue;
    }
    else{
      return queue;
    }
  }

  /** Run short test */
  public static void main(String[] args) {

    if (args.length == 0) {
      // If no arguments passed, print instructions
      System.err.println("Usage:  java Calculate <expr>");
    } else {
      // Otherwise, echo what was read in for now
      // Scanner input = new Scanner(new StringReader(args[0]));
      // while (input.hasNext()) {
        // System.out.println(input.next());
      // }

      System.out.println(convertInfix("1 + 2"));
      System.out.println(Postfix.calcPostfix(convertInfix("1 + 2")));

      System.out.println(convertInfix("3 - 1"));
      System.out.println(Postfix.calcPostfix(convertInfix("3 - 1")));

      System.out.println(convertInfix("7 * 2"));
      System.out.println(Postfix.calcPostfix(convertInfix("7 * 2")));

      System.out.println(convertInfix("10 / 2"));
      System.out.println(Postfix.calcPostfix(convertInfix("10 / 2")));

      System.out.println(convertInfix("3 ^ 2"));
      System.out.println(Postfix.calcPostfix(convertInfix("3 ^ 2")));

      System.out.println(convertInfix("2 + ( 3 * 7 )"));
      System.out.println(Postfix.calcPostfix(convertInfix("2 + ( 3 * 7 )")));

      System.out.println(convertInfix("(3+2)/5"));
      System.out.println(Postfix.calcPostfix(convertInfix("( 3 + 2 ) / 5")));
    }
  }

}