import java.util.HashMap;

import syntaxtree.Node;
import visitor.GJDepthFirst;
import visitor.GJNoArguDepthFirst;

public class P6 {
   public static void main(String [] args) {
      try {
         Node root = new MiniRAParser(System.in).Goal();
//         System.out.println("Program parsed successfully");
         HashMap<String , Object> s = (HashMap<String , Object>)root.accept(new GJNoArguDepthFirst()); // Your assignment part is invoked here.
         root.accept(new GJDepthFirst<Object, Object>(), s);
      }
      catch (ParseException e) {
         System.out.println(e.toString());
      }
   }
} 



