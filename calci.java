import java.util.*; 
  
public class calci 
{ 
   public static double read_exp(String expression) 
   { 
      //Check if the first number is negative
      if(expression.charAt(0)=='-')
      {
         expression = "0"+expression;
      }
      char[] ar_tok = expression.toCharArray(); 
      Stack<Double> values = new Stack<Double>();  // For storing numbers 
      Stack<Character> sta_obj = new Stack<Character>(); //For storing operators

      for (int i = 0; i < ar_tok.length; i++)
      {
         if (ar_tok[i] == ' ') 
            continue; 
         if ((ar_tok[i] >= '0' && ar_tok[i] <= '9') || ar_tok[i] == '.')
         { 
            StringBuffer buff_s = new StringBuffer(); 
            while (i < ar_tok.length && ((ar_tok[i] >= '0' && ar_tok[i] <= '9') || ar_tok[i] == '.'))
            {
               try
               {
                  if(ar_tok[i]=='.' && buff_s.indexOf(".")!=-1)
                  {
                     throw new Exception();
                  }
               }
               catch (Exception e)
               {
                  System.out.println("Too many .");
                  return Double.NaN;
               }
               buff_s.append(ar_tok[i++]);
            }
            i--;
            if(buff_s.indexOf(".")==-1)
            {
               buff_s.append(".0");
            }
            values.push(Double.parseDouble(buff_s.toString()));
         }
         else if (ar_tok[i] == '(') 
            sta_obj.push(ar_tok[i]);
         else if (ar_tok[i] == ')') 
         {
            try{
            while (sta_obj.peek() != '(') 
               values.push(implement_operation(sta_obj.pop(), values.pop(), values.pop())); 
            }
            catch (Exception e){
               System.out.println("Not a valid expression");
               return Double.NaN;
            }
            sta_obj.pop(); 
         } 
         else if (ar_tok[i] == '+' || ar_tok[i] == '-' || 
         ar_tok[i] == '*' || ar_tok[i] == '/') 
         {
            try{
            while (!sta_obj.empty() && hasPrecedence(ar_tok[i], sta_obj.peek())) 
               values.push(implement_operation(sta_obj.pop(), values.pop(), values.pop())); 
            }
            catch (Exception e){
               System.out.println("Invalid Expression");
               return Double.NaN;
            }
            sta_obj.push(ar_tok[i]); 
         }
         else{
            try{
               throw new Exception();
            }
            catch (Exception e){
               System.out.println(ar_tok[i]+" -> Not a number");
               return Double.NaN;
            }
         }
      }
      try{
         while (!sta_obj.empty()) 
         values.push(implement_operation(sta_obj.pop(), values.pop(), values.pop()));
      }
      catch (Exception e)
      {
         System.out.println("Not a valid expression");
         return Double.NaN;
      }
      return values.pop();
   } 

   public static boolean hasPrecedence(char oper_1, char oper_2)
   { 
      if (oper_2 == '(' || oper_2 == ')') 
         return false; 
      if ((oper_1 == '*' || oper_1 == '/') && (oper_2 == '+' || oper_2 == '-')) 
         return false; 
      else
      return true;
   }
 
   public static double implement_operation(char oper, double var2, double var1) 
      { 
      switch (oper)
      { 
         case '+': 
            return var1 + var2; 
         case '-': 
            return var1 - var2; 
         case '*': 
            return var1 * var2; 
         case '/': 
            try{
            if (var2 == 0) 
               throw new
               ArithmeticException();
            }
            catch (ArithmeticException e){
               System.out.println("Division by zero.. immpossible!");
               return Double.NaN;
            }
            return var1/var2;
      } 
      return 0;
   } 
  
// Main section 
   public static void main(String[] args) 
   { 
      Scanner sc=new Scanner(System.in);
      System.out.println("******Enter the expression******");
      String input = sc.nextLine();
      System.out.println("******OUTPUT******");
      Double ans = Double.valueOf(calci.read_exp(input));
      if(ans.isNaN()==false)
      {
         if((ans == Math.floor(ans)) && !Double.isInfinite(ans))
            System.out.println(String.format("%.0f", ans));
         else
            System.out.println(ans);
      }
   }
}