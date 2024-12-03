import java.util.Scanner;
import java.io.StringReader;
import java.util.ArrayDeque;

/** 
 * Class to interpret and compute the result of arithmetic expressions 
 * in POSTFIX format - 
 */
public class Postfix {
  // A method for calculation
  /**
   * 
   * @param queue
   * @return
   */
  public static Double calcPostfix(ArrayDeque<Object> queue){

    // Declare a new variable called stack
    ArrayDeque <Double> stack = new ArrayDeque <Double> ();

    // Use while loop to do the calculation
    while(!queue.isEmpty()){
      // Read each element one by one from input queue
      Object element;

      // If: number, add to stack
      if ((element = queue.removeFirst()) instanceof Double) {
        stack.push((Double)element);
        
      }
      // If: operator, pop last 2 and compute
      else{
        Character operator = (Character)element;

        // Make sure that the 1st poped is the 2nd in calculation
        Double num2 = stack.pop();
        Double num1 = stack.pop();

        if(operator.equals('+')) {
          // Push the result of calculation back to the stack
          stack.push(num1 + num2);
        }

        if(operator.equals('-')){
          // Push the result of calculation back to the stack
          stack.push(num1 - num2);
        }

        if(operator.equals('*')){
          // Push the result of calculation back to the stack
          stack.push(num1 * num2);
        }

        if(operator.equals('/')) {
          // push the result of calculation back to the stack
          stack.push(num1/num2);
        }

        if(operator.equals('^')){
          // push the result of calculation back to the stack
          stack.push(Math.pow(num1, num2));
        }

      }
    
    }
    
    // If: only one element in stack, output the result
    if(stack.size() == 1){
      return stack.getLast();
    }
    // If: more than one elements in stack, throw exception
    else{
      throw new RuntimeException("Java Postfix: Error - Malform");
    }
    
  }

  /** Run short test */
  public static void main(String[] args) {

    if (args.length == 0) {
      // If no arguments passed, print instructions
      System.err.println("Usage:  java Postfix <expr>");
    } else {
      // Otherwise, echo what was read in for now
      // Scanner input = new Scanner(new StringReader(args[0]));
      // while (input.hasNext()) {
        //System.out.println(input.next());
      //}
      ArrayDeque<Object> token1 = Tokenizer.readTokens("3 2 +");
      System.out.println(token1);
      System.out.println(calcPostfix(token1));

      ArrayDeque<Object> token2 = Tokenizer.readTokens("4 6 -");
      System.out.println(token2);
      System.out.println(calcPostfix(token2));

      ArrayDeque<Object> token3 = Tokenizer.readTokens("5 7 *");
      System.out.println(token3);
      System.out.println(calcPostfix(token3));
      
      ArrayDeque<Object> token4 = Tokenizer.readTokens("3 12 /");
      System.out.println(token4);
      System.out.println(calcPostfix(token4));
    }
  }

}
