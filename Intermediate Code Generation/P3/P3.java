import java.util.HashMap;

import syntaxtree.*;
import visitor.*;

public class P3 {
   public static void main(String [] args) {
      try {
         Node root = new MiniJavaParser(System.in).Goal();
         
         HashMap<String,String> s = (HashMap<String,String>)root.accept(new GJNoArguDepthFirst()); // Your assignment part is invoked here.
         root.accept(new GJDepthFirst<Object, Object>(), s);
      }
      catch (ParseException e) {
         System.out.println(e.toString());
      }
   }
} 



