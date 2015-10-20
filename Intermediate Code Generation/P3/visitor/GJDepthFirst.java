//
// Generated by JTB 1.3.2
//

package visitor;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;

import syntaxtree.AllocationExpression;
import syntaxtree.AndExpression;
import syntaxtree.ArrayAllocationExpression;
import syntaxtree.ArrayAssignmentStatement;
import syntaxtree.ArrayLength;
import syntaxtree.ArrayLookup;
import syntaxtree.ArrayType;
import syntaxtree.AssignmentStatement;
import syntaxtree.Block;
import syntaxtree.BooleanType;
import syntaxtree.BracketExpression;
import syntaxtree.ClassDeclaration;
import syntaxtree.ClassExtendsDeclaration;
import syntaxtree.CompareExpression;
import syntaxtree.Expression;
import syntaxtree.ExpressionList;
import syntaxtree.ExpressionRest;
import syntaxtree.FalseLiteral;
import syntaxtree.FormalParameter;
import syntaxtree.FormalParameterList;
import syntaxtree.FormalParameterRest;
import syntaxtree.Goal;
import syntaxtree.Identifier;
import syntaxtree.IfStatement;
import syntaxtree.IntegerLiteral;
import syntaxtree.IntegerType;
import syntaxtree.MainClass;
import syntaxtree.MessageSend;
import syntaxtree.MethodDeclaration;
import syntaxtree.MinusExpression;
import syntaxtree.Node;
import syntaxtree.NodeList;
import syntaxtree.NodeListOptional;
import syntaxtree.NodeOptional;
import syntaxtree.NodeSequence;
import syntaxtree.NodeToken;
import syntaxtree.NotExpression;
import syntaxtree.PlusExpression;
import syntaxtree.PrimaryExpression;
import syntaxtree.PrintStatement;
import syntaxtree.Statement;
import syntaxtree.ThisExpression;
import syntaxtree.TimesExpression;
import syntaxtree.TrueLiteral;
import syntaxtree.Type;
import syntaxtree.TypeDeclaration;
import syntaxtree.VarDeclaration;
import syntaxtree.WhileStatement;
import visitor.GJNoArguDepthFirst.classToPro;

/**
 * Provides default methods which visit each node in the tree in depth-first
 * order.  Your visitors may extend this class.
 */
public class GJDepthFirst<R,A> implements GJVisitor<R,A> {
   //
   // Auto class visitors--probably don't need to be overridden.
   //
	
	HashMap<String, classToPro> classInfo = new HashMap<String,classToPro>();
	HashMap<String,String> varInfo ;
	HashMap<String,String> varTypeInfo ;
	String recentIde;
	String expList ;
	String returnFromMet;
	String className = "";
	// Global String to store the code
		String code = new String();
	// Label Counter
		int labelCounter = 0;
	// Temp Counter
		int tempCounter = 50;
		
	// Check to say that i am in function vars
		boolean inMet = false; 
		int fparams = 0;
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

   /**
    * f0 -> MainClass()
    * f1 -> ( TypeDeclaration() )*
    * f2 -> <EOF>
    */
   public R visit(Goal n, A argu) {
      R _ret=null;
      classInfo = (HashMap<String, classToPro>) argu;
      code = code + "MAIN\n";
      n.f0.accept(this, argu);
      code = code + "END\n";
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      System.out.println(code);
      return _ret;
   }

   /**
    * f0 -> "class"
    * f1 -> Identifier()
    * f2 -> "{"
    * f3 -> "public"
    * f4 -> "static"
    * f5 -> "void"
    * f6 -> "main"
    * f7 -> "("
    * f8 -> "String"
    * f9 -> "["
    * f10 -> "]"
    * f11 -> Identifier()
    * f12 -> ")"
    * f13 -> "{"
    * f14 -> PrintStatement()
    * f15 -> "}"
    * f16 -> "}"
    */
   public R visit(MainClass n, A argu) {
      R _ret=null;
      varInfo = new HashMap<String,String>();
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      n.f5.accept(this, argu);
      n.f6.accept(this, argu);
      n.f7.accept(this, argu);
      n.f8.accept(this, argu);
      n.f9.accept(this, argu);
      n.f10.accept(this, argu);
      n.f11.accept(this, argu);
      n.f12.accept(this, argu);
      n.f13.accept(this, argu);
      n.f14.accept(this, argu);
      n.f15.accept(this, argu);
      n.f16.accept(this, argu);
      varInfo = null;
      return _ret;
   }

   /**
    * f0 -> ClassDeclaration()
    *       | ClassExtendsDeclaration()
    */
   public R visit(TypeDeclaration n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "class"
    * f1 -> Identifier()
    * f2 -> "{"
    * f3 -> ( VarDeclaration() )*
    * f4 -> ( MethodDeclaration() )*
    * f5 -> "}"
    */
   public R visit(ClassDeclaration n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      className = (String)n.f1.f0.tokenImage;
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      n.f5.accept(this, argu);
      className = "";
      return _ret;
   }

   /**
    * f0 -> "class"
    * f1 -> Identifier()
    * f2 -> "extends"
    * f3 -> Identifier()
    * f4 -> "{"
    * f5 -> ( VarDeclaration() )*
    * f6 -> ( MethodDeclaration() )*
    * f7 -> "}"
    */
   public R visit(ClassExtendsDeclaration n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      className = (String)n.f1.f0.tokenImage;
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      n.f5.accept(this, argu);
      n.f6.accept(this, argu);
      n.f7.accept(this, argu);
      className = "";
      return _ret;
   }

   /**
    * f0 -> Type()
    * f1 -> Identifier()
    * f2 -> ";"
    */
   public R visit(VarDeclaration n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      if(inMet){
    	  String s = (String)n.f1.f0.tokenImage;
    	  varInfo.put(s, ("TEMP " + (tempCounter++)));
    	  if(n.f0.f0.which == 3){
    		  varTypeInfo.put(s, recentIde);
    	  }
      }
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "public"
    * f1 -> Type()
    * f2 -> Identifier()
    * f3 -> "("
    * f4 -> ( FormalParameterList() )?
    * f5 -> ")"
    * f6 -> "{"
    * f7 -> ( VarDeclaration() )*
    * f8 -> ( Statement() )*
    * f9 -> "return"
    * f10 -> Expression()
    * f11 -> ";"
    * f12 -> "}"
    */
   public R visit(MethodDeclaration n, A argu) {
      R _ret=null;
      inMet = true;
      tempCounter = 1;
      varInfo = new HashMap<String,String>();
      varTypeInfo = new HashMap<String, String>();
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      String metName = n.f2.f0.tokenImage;
      code = code + "\n" + className + "_" +  metName + " ";
      fparams = 1;
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      n.f5.accept(this, argu);
      n.f6.accept(this, argu);
      code = code + "[" + fparams + "]\n";
      code = code + "\nBEGIN\n";
      n.f7.accept(this, argu);
      n.f8.accept(this, argu);
      
      n.f9.accept(this, argu);
      String retExp = (String)n.f10.accept(this, argu);
      code = code + "RETURN\n";
      code = code + retExp + "\n";
      n.f11.accept(this, argu);
      n.f12.accept(this, argu);
      code = code + "END\n";
      inMet = false;
//      Iterator itr = varTypeInfo.keySet().iterator();
//      System.out.println(metName);
//      while(itr.hasNext()){
//    	String element = (String)itr.next(); 
//    	System.out.println(element + " " + varTypeInfo.get(element));
//      }
//      System.out.println();
      varInfo = null;
      varTypeInfo = null;
      return _ret;
   }

   /**
    * f0 -> FormalParameter()
    * f1 -> ( FormalParameterRest() )*
    */
   public R visit(FormalParameterList n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> Type()
    * f1 -> Identifier()
    */
   public R visit(FormalParameter n, A argu) {
      R _ret=null;
      String s = n.f1.f0.tokenImage;
      n.f0.accept(this, argu);
      fparams += 1;
      varInfo.put(s, ("TEMP " + (tempCounter++)));
      if(n.f0.f0.which == 3){
		  varTypeInfo.put(s, recentIde);
	  }
      
      n.f1.accept(this, argu);
      
      return _ret;
   }

   /**
    * f0 -> ","
    * f1 -> FormalParameter()
    */
   public R visit(FormalParameterRest n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> ArrayType()
    *       | BooleanType()
    *       | IntegerType()
    *       | Identifier()
    */
   public R visit(Type n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "int"
    * f1 -> "["
    * f2 -> "]"
    */
   public R visit(ArrayType n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "boolean"
    */
   public R visit(BooleanType n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "int"
    */
   public R visit(IntegerType n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> Block()
    *       | AssignmentStatement()
    *       | ArrayAssignmentStatement()
    *       | IfStatement()
    *       | WhileStatement()
    *       | PrintStatement()
    */
   public R visit(Statement n, A argu) {
      R _ret=null;
      _ret = n.f0.accept(this, argu);
      //code = code + (String)_ret;// should still think of it change
      //System.out.println(_ret);
      return _ret;
   }

   /**
    * f0 -> "{"
    * f1 -> ( Statement() )*
    * f2 -> "}"
    */
   public R visit(Block n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      _ret = n.f1.accept(this, argu);
//      System.out.println(_ret + " in block");
      n.f2.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> Identifier()
    * f1 -> "="
    * f2 -> Expression()
    * f3 -> ";"
    */
   public R visit(AssignmentStatement n, A argu) {
      R _ret=null;
      // Assuming identifier is passed from down
      
      String ide = (String)n.f0.accept(this, argu);
      String s = getTemp(ide, className);
      n.f1.accept(this, argu);
      String exp = (String)n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      if(varInfo.containsKey(ide)){
    	  _ret =  (R)( "MOVE " + s + " " + exp + "\n");
      }else{
    	  _ret =  (R)( "HSTORE " + s + " " + exp + "\n");
      }
      code = code + (String)_ret;
      return _ret;
   }

   /**
    * f0 -> Identifier()
    * f1 -> "["
    * f2 -> Expression()
    * f3 -> "]"
    * f4 -> "="
    * f5 -> Expression()
    * f6 -> ";"
    */
   public R visit(ArrayAssignmentStatement n, A argu) {
      R _ret=null;
      String ideInit = (String)n.f0.accept(this, argu);
      String ide = getTemp(ideInit, className);
      n.f1.accept(this, argu);
      String exp1 = (String)n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      String exp2 = (String)n.f5.accept(this, argu);
      n.f6.accept(this, argu);
      
      String s = new String();
      s = "HSTORE PLUS ";
      if(varInfo.containsKey(ideInit)){
    	  s = s + ide + " PLUS \n";
    	  s = s + " \nBEGIN\n";
    	  String holdRet = " TEMP " + (tempCounter++) + " ";
    	  s = s + " MOVE " + holdRet + " TIMES " + exp1 + " 4\n";
    	  String holdCheck = " TEMP " + (tempCounter++) + " ";
    	  s = s + " HLOAD " + holdCheck + " " + ide +  " 0\n";
    	  String jumpLabel = "L" + (labelCounter++);
    	  s = s + " CJUMP MINUS 1 LT " + holdRet +  " TIMES " + holdCheck + " 4 " + jumpLabel + "\n";
    	  s = s + " ERROR\n";
    	  s = s + jumpLabel + " NOOP\n";
    	  s = s + "RETURN\n";
    	  s = s + holdRet + "\n";
    	  s = s + "END\n";
    	  s = s + "4 0 " + exp2 + "\n";
      }else{
    	  String dup = new String();
    	  String classTemp = " TEMP " + (tempCounter++);
    	  dup = "\nBEGIN\n";
    	  dup = dup + "HLOAD " + classTemp + " " + ide + "\n";
    	  dup = dup + "RETURN\n";
    	  dup = dup + classTemp + "\n";
    	  dup = dup + "END\n";
    	  s = s + " " + dup + "PLUS\n";
    	  s = s + " \nBEGIN\n";
    	  String holdRet = " TEMP " + (tempCounter++) + " ";
    	  s = s + " MOVE " + holdRet + " TIMES " + exp1 + " 4\n";
    	  String holdCheck = " TEMP " + (tempCounter++) + " ";
    	  s = s + " HLOAD " + holdCheck + " " + dup +  " 0\n";
    	  String jumpLabel = "L" + (labelCounter++);
    	  s = s + " CJUMP MINUS 1 LT " + holdRet +  " TIMES " + holdCheck + " 4 " + jumpLabel + "\n";
    	  s = s + " ERROR\n";
    	  s = s + jumpLabel + " NOOP\n";
    	  s = s + "RETURN\n";
    	  s = s + holdRet + "\n";
    	  s = s + "END\n";
    	  s = s + "4 0 " + exp2 + "\n";
      }
      _ret = (R)s;
      code = code + (String)_ret;
      return _ret;
   }

   /**
    * f0 -> "if"
    * f1 -> "("
    * f2 -> Expression()
    * f3 -> ")"
    * f4 -> Statement()
    * f5 -> "else"
    * f6 -> Statement()
    */
   public R visit(IfStatement n, A argu) {
      R _ret=null;
      
      String firstLabel = "L" + (labelCounter++);
      String finalLabel = "L" + (labelCounter++);
      
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      String exp = (String)n.f2.accept(this, argu);
      code  = code + "CJUMP " + exp + " " + firstLabel + "\n";
      n.f3.accept(this, argu);
      String sta1 = (String)n.f4.accept(this, argu);
      code = code + "\n";
      code = code + "JUMP " + finalLabel + "\n";
      code = code + firstLabel + "\t";
      n.f5.accept(this, argu);
      String sta2 = (String)n.f6.accept(this, argu);
      code = code + "\n";
      code = code + finalLabel + " NOOP \n";
//      String s = new String();
//      String firstLabel = "L" + (labelCounter++);
//      String finalLabel = "L" + (labelCounter++);
//      s = "CJUMP " + exp + " " + firstLabel + "\n";
//      s = s + sta1 + "\n";
//      s = s + "JUMP " + finalLabel + "\n";
//      s = s + firstLabel + "\t" + sta2 + "\n";
//      s = s + finalLabel + " NOOP \n";
//      _ret  = (R)s;
      return _ret;
   }

   /**
    * f0 -> "while"
    * f1 -> "("
    * f2 -> Expression()
    * f3 -> ")"
    * f4 -> Statement()
    */
   public R visit(WhileStatement n, A argu) {
      R _ret=null;
      String firstLabel = "L" + (labelCounter++);
      String finalLabel = "L" + (labelCounter++);
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      String exp = (String)n.f2.accept(this, argu);
      code = code + "\n" + firstLabel + "\t CJUMP " + exp + " " + finalLabel + "\n";
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      code = code + "\n";
      code = code + " JUMP " + firstLabel + "\n";
      code = code + finalLabel + "\t NOOP\n";
//      String s = new String();
//      String firstLabel = "L" + (labelCounter++);
//      String finalLabel = "L" + (labelCounter++);
//      s = "\n" + firstLabel + "\t CJUMP " + exp + " " + finalLabel + "\n";
//      s = s + sta + "\n";
//      s = s + " JUMP " + firstLabel + "\n";
//      s = s + finalLabel + "\t NOOP\n";
//      _ret = (R)s;
      return _ret;
   }

   /**
    * f0 -> "System.out.println"
    * f1 -> "("
    * f2 -> Expression()
    * f3 -> ")"
    * f4 -> ";"
    */
   public R visit(PrintStatement n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      String exp = (String)n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
//      System.out.println(exp + " in print");
      code = code + "PRINT " + exp + "\n";
      return _ret;
   }

   /**
    * f0 -> AndExpression()
    *       | CompareExpression()
    *       | PlusExpression()
    *       | MinusExpression()
    *       | TimesExpression()
    *       | ArrayLookup()
    *       | ArrayLength()
    *       | MessageSend()
    *       | PrimaryExpression()
    */
   public R visit(Expression n, A argu) {
      R _ret=null;
      _ret = n.f0.accept(this, argu);
//      System.out.println(_ret);
      return _ret;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "&"
    * f2 -> PrimaryExpression()
    */
   public R visit(AndExpression n, A argu) {
      R _ret=null;
      String exp1 = (String)n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      String exp2 = (String)n.f2.accept(this, argu);
      String s = new String();
      s = "\nBEGIN\n";
      String retTemp = " TEMP " + (tempCounter++);
      String jumpLabel = " L" + (labelCounter++);
      s = s + " MOVE " + retTemp + " 0\n";
      s = s + " CJUMP " + exp1 + " " + jumpLabel + "\n";
      s = s + " CJUMP " + exp2 + " " + jumpLabel + "\n";
      s = s + " MOVE " + retTemp + " 1\n";
      s = s + jumpLabel +  "\t NOOP \n";
      s = s + "RETURN\n";
      s = s + retTemp + "\n";
      s = s + "END\n";
      _ret = (R)s;
      
      
      
      return _ret;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "<"
    * f2 -> PrimaryExpression()
    */
   public R visit(CompareExpression n, A argu) {
      R _ret=null;
      // Code for the LT operator
      String s = new String();
      String exp1 = (String)n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      String exp2 = (String)n.f2.accept(this, argu);
      s = "LT " + exp1 + " " + exp2 + " ";
      _ret = (R)s;
      return _ret;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "+"
    * f2 -> PrimaryExpression()
    */
   public R visit(PlusExpression n, A argu) {
      R _ret=null;
      // Code for the PLUS operator
      String s = new String();
      String exp1 = (String)n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      String exp2 = (String)n.f2.accept(this, argu);
      s = "PLUS " + exp1 + " " + exp2 + " ";
      _ret = (R)s;
      return _ret;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "-"
    * f2 -> PrimaryExpression()
    */
   public R visit(MinusExpression n, A argu) {
      R _ret=null;
      // Code for the MINUS operator
      String s = new String();
      String exp1 = (String)n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      String exp2 = (String)n.f2.accept(this, argu);
      s = "MINUS " + exp1 + " " + exp2 + " ";
      _ret = (R)s;
      return _ret;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "*"
    * f2 -> PrimaryExpression()
    */
   public R visit(TimesExpression n, A argu) {
      R _ret=null;
      // Code for the TIMES operator
      String s = new String();
      String exp1 = (String)n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      String exp2 = (String)n.f2.accept(this, argu);
      s = "TIMES " + exp1 + " " + exp2 + " ";
      _ret = (R)s;
      return _ret;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "["
    * f2 -> PrimaryExpression()
    * f3 -> "]"
    */
   public R visit(ArrayLookup n, A argu) {
      R _ret=null;
      String exp1 = (String)n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      String exp2 = (String)n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      String s = new String();
      s = "\nBEGIN\n";
      String retTemp = " TEMP "+ (tempCounter++);
      s = s + " HLOAD " + retTemp + " PLUS " + exp1 + " PLUS " ;
      s = s + "\nBEGIN\n";
      String lenTemp = " TEMP " + (tempCounter++);
      s = s + " MOVE " + lenTemp + " TIMES " + exp2 + " 4\n";
      String lenOfArray = " TEMP " + (tempCounter++);
      s = s + " HLOAD " + lenOfArray + " " + exp1 + " 0\n";
      String jumpLabel = "L"+ (labelCounter++);
      s = s + " CJUMP MINUS 1 LT " + lenTemp + " TIMES " + lenOfArray + " 4 " + jumpLabel + "\n";
      s = s + "ERROR\n";
      s = s + jumpLabel + "\tNOOP\n";
      s = s + "RETURN\n";
      s = s + lenTemp;
      s = s + "END\n";
      s = s + " 4  0 \n";
      s = s + "RETURN\n";
      s = s + retTemp + "\n";
      s = s + "END\n";
      _ret = (R)s;
      return _ret;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "."
    * f2 -> "length"
    */
   public R visit(ArrayLength n, A argu) {
      R _ret=null;
      String exp = (String)n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      String s = new String();
      s = "\nBEGIN\n";// Still has doubt check it
      String retTemp = " TEMP " + (tempCounter++) ;
      s = s + "HLOAD " + retTemp + " " + exp + " 0\n";
      s = s + "RETURN\n";
      s = s + retTemp + "\n";
      s = s + "END\n";
      _ret = (R)s;
      return _ret;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "."
    * f2 -> Identifier()
    * f3 -> "("
    * f4 -> ( ExpressionList() )?
    * f5 -> ")"
    */
   public R visit(MessageSend n, A argu) {
      R _ret=null;
      boolean correct = false;
      String primExp = (String)n.f0.accept(this, argu);
      String primIde;
      String typeOfIde = new String();
      // need to make many changes so skipping for time being
      //System.out.println(primExp);
      
    	  primIde = recentIde;
      
//      System.out.println(primIde + " this is recent ide");
      n.f1.accept(this, argu);
      String ide = (String)n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      String exp = (String)n.f4.accept(this, argu);
//      System.out.println(exp + " Checking exp");
      n.f5.accept(this, argu);
      
      if(n.f0.f0.which == 8){
    	  typeOfIde = returnFromMet;
    	  correct = true;
      }else if(n.f0.f0.which == 6){
    	  typeOfIde = primIde;
    	  correct = true;
      }
      if(varInfo.containsKey(primIde)){
    	  typeOfIde = varTypeInfo.get(primIde);
      }else{
    	  if(!primExp.equals(" TEMP 0 ") && !correct){
    		  int off = getOffset(className, primIde);
    		  typeOfIde = (String)classInfo.get(className).varType.get(off/4 - 1);
    	  }
      }
      if(primExp.equals(" TEMP 0 ")){
    	  typeOfIde = className;
      }
      
//      System.out.println(typeOfIde + " " + ide + " TOCHECK WHAT I AM RETURNING " + returnFromMet);
	  int distance = getMetOffset(typeOfIde, ide);
      returnFromMet = (String)classInfo.get(typeOfIde).metret.get(distance/4);
//      int distance = 8;// need to remove this line and make above line live
      String store = new String();
      store = " CALL\n";
      store = store + "\nBEGIN\n";
      String tempToHoldClass = "TEMP " + (tempCounter++);
      String tempToGoToFunctionTable = "TEMP " + (tempCounter++);
      String retTemp = "TEMP " + (tempCounter++);
      store = store + " MOVE " + tempToHoldClass + " " + primExp + "\n";
      store = store + " HLOAD " + tempToGoToFunctionTable + " " + tempToHoldClass + " 0\n";
      store = store + " HLOAD " + retTemp + " " + tempToGoToFunctionTable + " " + distance + "\n";
      store = store + "RETURN\n";
      store = store + retTemp + "\n";
      store = store + "END\n";
      store = store + "( " + tempToHoldClass + " " + expList + " )\n";
//      System.out.println(store);
      _ret = (R)store;
      expList = new String();
      return _ret;
   }
   
   public int getMetOffset(String name, String metName){
//	   if(name == null)//This is a fix need to be removed probably
//		   return 0;
	   
	   classToPro c = classInfo.get(name);
//	   if(!name.equals("main")){//This is a fix need to be removed probably
		   Iterator itr = c.methods.iterator();
		   while(itr.hasNext()){
			   String element = (String)itr.next();
			   String a[] = element.split("_", 2);
			   if(metName.equals(a[1])){
				   return c.methods.indexOf(element)*4;
			   }
		   }
//	   }
	   return 0;
   }
   
   //has to remove from here
//   public int getOffset(String name , String varName){
//	   String classVar = name + "_" + varName;
//	   classToPro c = classInfo.get(name);
//	   Iterator itr = c.vars.iterator();
//	   while(itr.hasNext()){
//		   String element = (String)itr.next();
//		   if(classVar.equals(element)){
//			   int index = c.vars.indexOf(classVar);
//			   return (index + 1 )*4;
//		   }
//	   }
//	   return getOffset(c.parentname, varName);	   
//   }
   //has to remove upto here
   /**
    * f0 -> Expression()
    * f1 -> ( ExpressionRest() )*
    */
   public R visit(ExpressionList n, A argu) {
      R _ret=null;
      expList = new String();
      String first = (String)n.f0.accept(this, argu);
      expList = first + " " ;
      String next = (String)n.f1.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> ","
    * f1 -> Expression()
    */
   public R visit(ExpressionRest n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      String s = (String)n.f1.accept(this, argu);
      expList = expList + ( " " + s + " ");
//      System.out.println(s + " in the eexpression rest");
      return _ret;
   }

   /**
    * f0 -> IntegerLiteral()
    *       | TrueLiteral()
    *       | FalseLiteral()
    *       | Identifier()
    *       | ThisExpression()
    *       | ArrayAllocationExpression()
    *       | AllocationExpression()
    *       | NotExpression()
    *       | BracketExpression()
    */
   public R visit(PrimaryExpression n, A argu) {
      R _ret=null;
      String s = (String)n.f0.accept(this, argu);;
      // Sending up whatever we get
      switch(n.f0.which){
      	case 3: if(varInfo.containsKey(s)){
      				s = getTemp(s , className);
      				_ret = (R)s;
      			}else{
      				s = getTemp(s , className);
      				String retTemp = " TEMP " + (tempCounter++);
      				String c = new String();
      				c = " \nBEGIN\n";
      				c = c + " HLOAD " + retTemp + " " + s + "\n";
      				c = c + " RETURN\n";
      				c = c + " " + retTemp + "\n";
      				c = c + " END\n";
      				_ret = (R)c;
      			}
      			break;
      	default: _ret = (R)s;
      }
//      System.out.println(_ret);
      return _ret;
   }
   
   public int getOffset(String name , String varName){
	   String classVar = name + "_" + varName;
	   classToPro c = classInfo.get(name);
	   Iterator itr = c.vars.iterator();
	   while(itr.hasNext()){
		   String element = (String)itr.next();
		   if(classVar.equals(element)){
			   int index = c.vars.indexOf(classVar);
			   return (index + 1 )*4;
		   }
	   }
	   return getOffset(c.parentname, varName);	   
   }
   
   public String getTemp(String s, String name){
	   if(varInfo.containsKey(s)){
		   return varInfo.get(s);
	   }else{
		   int index = getOffset(name, s);
		   String line = " TEMP 0 " + index + " ";
		   return line;
	   }
   }
   /**
    * f0 -> <INTEGER_LITERAL>
    */
   public R visit(IntegerLiteral n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
   // Sending up whatever we get
      _ret = (R)n.f0.tokenImage;
      return _ret;
   }

   /**
    * f0 -> "true"
    */
   public R visit(TrueLiteral n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      _ret = (R)"1";
      return _ret;
   }

   /**
    * f0 -> "false"
    */
   public R visit(FalseLiteral n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      _ret = (R)"0";
      return _ret;
   }

   /**
    * f0 -> <IDENTIFIER>
    */
   public R visit(Identifier n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      recentIde = n.f0.tokenImage;
      _ret = (R)n.f0.tokenImage;
      return _ret;
   }

   /**
    * f0 -> "this"
    */
   public R visit(ThisExpression n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      _ret = (R)(" TEMP 0 ");
      return _ret;
   }

   /**
    * f0 -> "new"
    * f1 -> "int"
    * f2 -> "["
    * f3 -> Expression()
    * f4 -> "]"
    */
   public R visit(ArrayAllocationExpression n, A argu) {
      R _ret=null;
      String s = new String();
      s = "\nBEGIN\n";
      
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      String exp = (String)n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      String base = "TEMP " + (tempCounter++);
      String counter = "TEMP " + (tempCounter++);
      s = s + "MOVE " + base + " HALLOCATE TIMES PLUS " + exp + " 1 4\n";
      s = s+ "MOVE " + counter + " 4\n";
      String firstLabel = "L" + (labelCounter++);
      String finalLabel = "L" + (labelCounter++);
      s = s + firstLabel + "\t CJUMP LT " + counter + " TIMES PLUS " + exp + " 1 4 " + finalLabel + "\n";
      s = s + "HSTORE PLUS " + base + " " + counter + " 0 0\n";
      s = s + "MOVE " + counter + " PLUS " + counter + " 4\n";
      s = s + "JUMP " + firstLabel + "\n";
      s = s + finalLabel + "\t" + "HSTORE " + base + " 0 " + exp + " \n";
      s = s + "RETURN\n";
      s = s + base + "\n";
      s = s + "END\n";
      
      _ret = (R)s;
      return _ret;
   }

   /**
    * f0 -> "new"
    * f1 -> Identifier()
    * f2 -> "("
    * f3 -> ")"
    */
   public R visit(AllocationExpression n, A argu) {
      R _ret=null;
      classToPro currentIde;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      String ide = (String)n.f1.f0.tokenImage;
      currentIde = classInfo.get(ide);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      int numOfMets = currentIde.methods.size();
      int numOfVars = currentIde.vars.size();
      String store = new String();
      store = "\nBEGIN\n";
      String Vtable = "TEMP " + (tempCounter++);
      String Ctable = "TEMP " + (tempCounter++); // Pointer to hold the class table
      store = store + "MOVE " + Vtable + " " + "HALLOCATE " + (numOfMets*4) + "\n";
      store = store + "MOVE " + Ctable + " " + "HALLOCATE " + ((numOfVars + 1) * 4) + "\n";
      int offset = 0;
      Iterator<String> itrOfMets = currentIde.methods.iterator();
      while(itrOfMets.hasNext()){
    	  store = store + "HSTORE " + Vtable + " " + offset + " " + itrOfMets.next() + "\n";
    	  offset += 4;
      }
      String counter = "TEMP " + (tempCounter++);
      store = store + "MOVE " + counter + " 4\n";
      String firstLabel = "L" + (labelCounter++);
      String finalLabel = "L" + (labelCounter++);
      store = store + firstLabel + "\t" + "CJUMP " + "LT " + counter + " " +  ((numOfVars + 1) * 4) + " " + finalLabel + "\n";
      store = store + "HSTORE " + "PLUS " + Ctable + " " + counter + " 0 0\n";
      store = store + "MOVE " + counter + " PLUS " + counter + " 4\n";
      store = store + "JUMP " + firstLabel + "\n";
      store = store + finalLabel + "\t" + "HSTORE " + Ctable + " 0 " + Vtable + "\n";
      store = store + "RETURN\n";
      store = store + Ctable + "\n";
      store = store + "END\n";
      _ret = (R)store;
//      System.out.println(_ret);
      return _ret;
   }

   /**
    * f0 -> "!"
    * f1 -> Expression()
    */
   public R visit(NotExpression n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      String s = (String)n.f1.accept(this, argu);
      _ret = (R)("MINUS 1 " + s);

      return _ret;
   }

   /**
    * f0 -> "("
    * f1 -> Expression()
    * f2 -> ")"
    */
   public R visit(BracketExpression n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      _ret = n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return _ret;
   }

}