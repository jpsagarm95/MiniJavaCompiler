//
// Generated by JTB 1.3.2
//

package visitor;
import syntaxtree.*;
import java.util.*;

/**
 * Provides default methods which visit each node in the tree in depth-first
 * order.  Your visitors may extend this class.
 */
public class GJDepthFirst<R,A> implements GJVisitor<R,A> {
   //
   // Auto class visitors--probably don't need to be overridden.
   //
   public R visit(NodeList n, A argu) {
      R _ret=null;
      int _count=0;
      for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
         e.nextElement().accept(this,argu);
         _count++;
      }
      return _ret;
   }

   public R visit(NodeListOptional n, A argu) {
      if ( n.present() ) {
         R _ret=null;
         int _count=0;
         for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
            e.nextElement().accept(this,argu);
            _count++;
         }
         return _ret;
      }
      else
         return null;
   }

   public R visit(NodeOptional n, A argu) {
      if ( n.present() )
         return n.node.accept(this,argu);
      else
         return null;
   }

   public R visit(NodeSequence n, A argu) {
      R _ret=null;
      int _count=0;
      for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
         e.nextElement().accept(this,argu);
         _count++;
      }
      return _ret;
   }

   public R visit(NodeToken n, A argu) { return null; }

   //
   // User-generated visitor methods below
   //
   HashMap<String, Integer> maxTemp;
   int tempCounter;
   String code = new String(); 
   /**
    * f0 -> "MAIN"
    * f1 -> StmtList()
    * f2 -> "END"
    * f3 -> ( Procedure() )*
    * f4 -> <EOF>
    */
   public R visit(Goal n, A argu) {
      R _ret=null;
      maxTemp = (HashMap<String, Integer>) argu;
//    Iterator itr = maxTemp.keySet().iterator();
//    while(itr.hasNext()){
//  	  String element = (String)itr.next();
//  	  System.out.println(element + " -> " + maxTemp.get(element));
//    }
      tempCounter = maxTemp.get("Main") + 1;
      code = code + "\nMAIN\n";
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      code = code + "\nEND\n";
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      System.out.println(code);
      return _ret;
   }

   /**
    * f0 -> ( ( Label() )? Stmt() )*
    */
   public R visit(StmtList n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> Label()
    * f1 -> "["
    * f2 -> IntegerLiteral()
    * f3 -> "]"
    * f4 -> StmtExp()
    */
   public R visit(Procedure n, A argu) {
      R _ret=null;
      String name = (String)n.f0.accept(this, argu);
      tempCounter = maxTemp.get(name) + 1;
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      String noOfParams = n.f2.f0.tokenImage;
      code = code + name + " [" + noOfParams + "]\n"; 
      n.f3.accept(this, argu);
      code = code + "\nBEGIN\n";
      String retTemp = (String)n.f4.accept(this, argu);
      code = code + "\nRETURN\n";
      code = code + " " + retTemp + " \n";
      code = code + "\nEND\n";
      return _ret;
   }

   /**
    * f0 -> NoOpStmt()
    *       | ErrorStmt()
    *       | CJumpStmt()
    *       | JumpStmt()
    *       | HStoreStmt()
    *       | HLoadStmt()
    *       | MoveStmt()
    *       | PrintStmt()
    */
   public R visit(Stmt n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "NOOP"
    */
   public R visit(NoOpStmt n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      
      code = code + " NOOP \n";
      return _ret;
   }

   /**
    * f0 -> "ERROR"
    */
   public R visit(ErrorStmt n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      code = code + " ERROR \n";
      return _ret;
   }

   /**
    * f0 -> "CJUMP"
    * f1 -> Exp()
    * f2 -> Label()
    */
   public R visit(CJumpStmt n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      String exp = (String)n.f1.accept(this, argu);
      
      String dummyTemp = " TEMP " + (tempCounter++) + " ";
      code = code + " MOVE " + dummyTemp + " " + exp + "\n";
      code = code + " CJUMP " + dummyTemp + " ";
      n.f2.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "JUMP"
    * f1 -> Label()
    */
   public R visit(JumpStmt n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      
      code = code + " JUMP ";
      n.f1.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "HSTORE"
    * f1 -> Exp()
    * f2 -> IntegerLiteral()
    * f3 -> Exp()
    */
   public R visit(HStoreStmt n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      String exp1 = (String)n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      String exp2 = (String)n.f3.accept(this, argu);
      String dummyTemp1 = " TEMP " + (tempCounter++) + " ";
      String dummyTemp2 = " TEMP " + (tempCounter++) + " ";
      code = code + " MOVE " + dummyTemp1 + " " + exp1 + "\n";
      code = code + " MOVE " + dummyTemp2 + " " + exp2 + "\n";
      code = code + " HSTORE "+ dummyTemp1 + " " + n.f2.f0.tokenImage + " " + dummyTemp2 + "\n";
      return _ret;
   }

   /**
    * f0 -> "HLOAD"
    * f1 -> Temp()
    * f2 -> Exp()
    * f3 -> IntegerLiteral()
    */
   public R visit(HLoadStmt n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      String temp1 = (String)n.f1.accept(this, argu);
      String exp = (String)n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      String dummyTemp = " TEMP " + (tempCounter++) + " ";
      code = code + " MOVE " + dummyTemp + " " + exp + "\n";
      code = code + " HLOAD " + temp1 + " " + dummyTemp + " " + n.f3.f0.tokenImage + " \n";
      return _ret;
   }

   /**
    * f0 -> "MOVE"
    * f1 -> Temp()
    * f2 -> Exp()
    */
   public R visit(MoveStmt n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      String temp = (String)n.f1.accept(this, argu);
      String exp = (String)n.f2.accept(this, argu);
      code = code + " MOVE " + temp + " " + exp + "\n";
      return _ret;
   }

   /**
    * f0 -> "PRINT"
    * f1 -> Exp()
    */
   public R visit(PrintStmt n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      String exp = (String)n.f1.accept(this, argu);
      String dummyTemp = " TEMP " + (tempCounter++) + " ";
      code = code + " MOVE " + dummyTemp + " " + exp + "\n";
      code = code + " PRINT " + dummyTemp + "\n";
      return _ret;
   }

   /**
    * f0 -> StmtExp()
    *       | Call()
    *       | HAllocate()
    *       | BinOp()
    *       | Temp()
    *       | IntegerLiteral()
    *       | Label()
    */
   public R visit(Exp n, A argu) {
      R _ret=null;
      _ret = n.f0.accept(this, null);
      if(argu instanceof ArrayList ){
    	  if(n.f0.which == 5){
    		  String dummyTemp = " TEMP "+ (tempCounter++) + " ";
    		  code = code + " MOVE " + dummyTemp + " " + (String) _ret + " \n";
    		  ((ArrayList) argu).add(dummyTemp);
    		  return _ret;
    	  }
    	  ((ArrayList) argu).add(_ret);
      }
      return _ret;
   }

   /**
    * f0 -> "BEGIN"
    * f1 -> StmtList()
    * f2 -> "RETURN"
    * f3 -> Exp()
    * f4 -> "END"
    */
   public R visit(StmtExp n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      String exp = (String)n.f3.accept(this, argu);//Assuming a temp returns from down
      String dummyTemp = " TEMP " + (tempCounter++) + " ";
      code = code + " MOVE " + dummyTemp + " " + exp + " \n";
      n.f4.accept(this, argu);
      _ret = (R)dummyTemp;
      return _ret;
   }

   /**
    * f0 -> "CALL"
    * f1 -> Exp()
    * f2 -> "("
    * f3 -> ( Exp() )*
    * f4 -> ")"
    */
   public R visit(Call n, A argu) {
      R _ret=null;
      
      n.f0.accept(this, argu);
      String exp = (String)n.f1.accept(this, argu);
      String dummyTemp = " TEMP " + (tempCounter++)+ " ";
      code = code + " MOVE " + dummyTemp + " " + exp + "\n";
      n.f2.accept(this, argu);
      ArrayList h = new ArrayList();
      n.f3.accept(this, (A)h);
      
      
      
      String expList = new String();
      Iterator itr = h.iterator();
      while(itr.hasNext()){
    	  expList = expList + " " + itr.next() + " ";
      }
      
      
      
      
      
      n.f4.accept(this, argu);
      
      String callTemp = " TEMP " + (tempCounter++)+ " ";
      code = code + " MOVE " + callTemp + " CALL " + dummyTemp + " (" + expList + ")\n"; 
      _ret = (R)callTemp;
      return _ret;
   }

   /**
    * f0 -> "HALLOCATE"
    * f1 -> Exp()
    */
   public R visit(HAllocate n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      String exp = (String)n.f1.accept(this, argu);
      String dummyTemp = " TEMP " + (tempCounter++) + " ";
      code = code + " MOVE " + dummyTemp + " " + exp + "\n";
      String retTemp = " TEMP " + (tempCounter++) + " ";
      code = code + " MOVE " + retTemp + " HALLOCATE " + dummyTemp + " \n";
      _ret = (R)(retTemp);
      return _ret;
   }

   /**
    * f0 -> Operator()
    * f1 -> Exp()
    * f2 -> Exp()
    */
   public R visit(BinOp n, A argu) {
      R _ret=null;
      String op = (String)n.f0.accept(this, argu);
      String temp = (String)n.f1.accept(this, argu);
      String simpleExp = (String)n.f2.accept(this, argu);
      String dummyTemp1 = " TEMP " + (tempCounter++) + " ";
      String dummyTemp2 = " TEMP " + (tempCounter++) + " ";
      code = code + " MOVE " + dummyTemp1 + " " + temp + "\n";
      code = code + " MOVE " + dummyTemp2 + " " + simpleExp + "\n";
      String retTemp = " TEMP " + (tempCounter++) + " ";
      code = code + " MOVE " + retTemp + " " + op + " " + dummyTemp1 + " " + dummyTemp2 + " \n";
      _ret = (R)(retTemp);
      return _ret;
   }

   /**
    * f0 -> "LT"
    *       | "PLUS"
    *       | "MINUS"
    *       | "TIMES"
    */
   public R visit(Operator n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      switch(n.f0.which){
      case 0:	_ret = (R)" LT ";
      			break;
      case 1:	_ret = (R)" PLUS ";
				break;
      case 2:	_ret = (R)" MINUS ";
				break;
      case 3:	_ret = (R)" TIMES ";
				break;
      }
      return _ret;
   }

   /**
    * f0 -> "TEMP"
    * f1 -> IntegerLiteral()
    */
   public R visit(Temp n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      String value = n.f1.f0.tokenImage;
      _ret = (R)(" TEMP " + value + " ");
      return _ret;
   }

   /**
    * f0 -> <INTEGER_LITERAL>
    */
   public R visit(IntegerLiteral n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      _ret = (R)n.f0.tokenImage;
      return _ret;
   }

   /**
    * f0 -> <IDENTIFIER>
    */
   public R visit(Label n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      if(!maxTemp.containsKey(n.f0.tokenImage)){
    	  code = code + " " + n.f0.tokenImage + " \n";
      }
      _ret = (R)n.f0.tokenImage;
      return _ret;
   }

}
