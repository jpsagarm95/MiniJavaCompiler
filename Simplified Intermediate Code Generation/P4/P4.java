import java.util.HashMap;

import syntaxtree.Node;
import visitor.GJDepthFirst;
import visitor.GJNoArguDepthFirst;

public class P4 {
   public static void main(String [] args) {
      try {
         Node root = new MiniIRParser(System.in).Goal();
         HashMap<String , Integer> s = (HashMap<String , Integer>)root.accept(new GJNoArguDepthFirst()); // Your assignment part is invoked here.
         root.accept(new GJDepthFirst<Object, Object>(), s);
      }
      catch (ParseException e) {
         System.out.println(e.toString());
      }
   }
} 



