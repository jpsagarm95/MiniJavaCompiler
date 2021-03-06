//
// Generated by JTB 1.3.2
//

package visitor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.SortedMap;

import javax.swing.SortOrder;

import syntaxtree.BinOp;
import syntaxtree.CJumpStmt;
import syntaxtree.Call;
import syntaxtree.ErrorStmt;
import syntaxtree.Exp;
import syntaxtree.Goal;
import syntaxtree.HAllocate;
import syntaxtree.HLoadStmt;
import syntaxtree.HStoreStmt;
import syntaxtree.IntegerLiteral;
import syntaxtree.JumpStmt;
import syntaxtree.Label;
import syntaxtree.MoveStmt;
import syntaxtree.NoOpStmt;
import syntaxtree.Node;
import syntaxtree.NodeList;
import syntaxtree.NodeListOptional;
import syntaxtree.NodeOptional;
import syntaxtree.NodeSequence;
import syntaxtree.NodeToken;
import syntaxtree.Operator;
import syntaxtree.PrintStmt;
import syntaxtree.Procedure;
import syntaxtree.SimpleExp;
import syntaxtree.Stmt;
import syntaxtree.StmtExp;
import syntaxtree.StmtList;
import syntaxtree.Temp;

/**
 * Provides default methods which visit each node in the tree in depth-first
 * order.  Your visitors may extend this class.
 */
public class GJNoArguDepthFirst<R> implements GJNoArguVisitor<R> {
   //
   // Auto class visitors--probably don't need to be overridden.
   //
   public R visit(NodeList n) {
      R _ret=null;
      int _count=0;
      for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
         e.nextElement().accept(this);
         _count++;
      }
      return _ret;
   }

   public R visit(NodeListOptional n) {
      if ( n.present() ) {
         R _ret=null;
         int _count=0;
         for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
            e.nextElement().accept(this);
            _count++;
         }
         return _ret;
      }
      else
         return null;
   }

   public R visit(NodeOptional n) {
      if ( n.present() )
         return n.node.accept(this);
      else
         return null;
   }

   public R visit(NodeSequence n) {
      R _ret=null;
      int _count=0;
      for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
         e.nextElement().accept(this);
         _count++;
      }
      return _ret;
   }

   public R visit(NodeToken n) { return null; }

   class liveRange implements Comparable{
	   public int name;
	   public int start;
	   public int end;
	   public liveRange(int startValue, int endValue, int nameOfVar){
		   start = startValue;
		   end = endValue;
		   name = nameOfVar;
	   }
	   

	@Override
	public int compareTo(Object arg0) {
		// TODO Auto-generated method stub
		return this.start - ((liveRange) arg0).start;
	}
   }
   //
   // User-generated visitor methods below
   //

   
   public class Register{
	   public int name;
	   public int regNum;
	   public String sorr;
	   public Register(int nameOfVar, int register , String sorr){
		   name = nameOfVar;
		   regNum = register;
		   this.sorr = sorr;
	   }
   }
   
   public class activeRegister{
	   public int name;
	   public int start;
	   public int end;
	   public int register;
	   public activeRegister(int name, int start, int end, int register){
		   this.name = name;
		   this.start = start;
		   this.end = end;
		   this.register = register;
	   }
   }
   
   //new
   
   
   //new
   
   public HashMap<Integer, Register> linearScan(ArrayList<liveRange> range){
	   int stackLocation = 0;
	   ArrayList<activeRegister> active = new ArrayList<activeRegister>();
	   HashMap<Integer, Register> allocation = new HashMap<Integer, Register>();
	   ArrayList<Integer> freeRegisters = new ArrayList<Integer>();
	   for(int i = 0 ; i < 18 ; i++){
		   freeRegisters.add(i);
	   }
	   Iterator<liveRange> itr = range.iterator();
	   while(itr.hasNext()){
		   liveRange element = itr.next();
		   //expireOldIntervals();
		   while(active.size() > 0){
			   activeRegister j = active.get(0);
			   if(j.end >= element.start){
				   break;
			   }
			   int num = j.register;
			   freeRegisters.add(num);
			   allocation.put(j.name, new Register(j.name, j.register, "r"));
			   active.remove(0);
		   }
		   if(active.size() == 18){
			   //spillAtInterval();
			   int endOfSpill = active.get(active.size()-1).end;
			   
			   if(endOfSpill > element.end){
				   activeRegister spill = active.get(active.size() - 1);
				   activeRegister temp = new activeRegister(element.name, element.start, element.end, spill.register);
				   //location[spill] = new stack location;
				   allocation.put(spill.name, new Register(spill.name, stackLocation++, "s"));
				   active.remove(active.size() - 1);
				   active = addIncOrder(active, temp);
			   }else{
				   //location[temp] = new stack location;
				   allocation.put(element.name, new Register(element.name, stackLocation++ , "s"));
			   }
		   }else{
			   int num = freeRegisters.get(0);
			   freeRegisters.remove(0);
			   activeRegister hold = new activeRegister(element.name, element.start, element.end, num);
			   active = addIncOrder(active, hold);
		   }
	   }
	   while(active.size() > 0){
		   activeRegister hold = active.get(0);
		   allocation.put(hold.name, new Register(hold.name, hold.register, "r"));
		   active.remove(0);
	   }
	   return allocation;
   }
   
   public ArrayList<activeRegister> addIncOrder(ArrayList<activeRegister> active, activeRegister hold){
	   int index = 0;
	   Iterator<activeRegister> itr = active.iterator();
	   while(itr.hasNext()){
		   activeRegister element = itr.next();
		   if(hold.end <= element.end){
			   active.add(index, hold);
			   return active;
		   }
		   index++;
	   }
	   active.add(active.size(), hold);
	   return active;
   }
   //new
   public class jumpOrCjump{
	   public String label;
	   public String jOrCj;
	   public jumpOrCjump(String label, String jOrCj){
		   this.label = label;
		   this.jOrCj = jOrCj;
	   }
   }
   public HashMap<Integer, jumpOrCjump> succForJump = new HashMap<Integer, jumpOrCjump>();
   public HashMap<String, Integer> statementNumberOfLabel = new HashMap<String, Integer>();
   public HashMap<Integer, ArrayList<Integer>> successors = new HashMap<Integer, ArrayList<Integer>>();
   public HashMap<Integer, Integer> location = new HashMap<Integer,Integer>();
   public ArrayList<Integer> end = new ArrayList<Integer>();
   public ArrayList<String> namesOfFunctions = new ArrayList<String>();
   public HashMap<Integer, ArrayList<Integer>> in = new HashMap<Integer, ArrayList<Integer>>();
   public HashMap<Integer, ArrayList<Integer>> out = new HashMap<Integer, ArrayList<Integer>>();
   public HashMap<Integer, ArrayList<Integer>> def = new HashMap<Integer, ArrayList<Integer>>();
   public HashMap<Integer, ArrayList<Integer>> use = new HashMap<Integer, ArrayList<Integer>>();
   public HashMap<Integer, ArrayList<Integer>> inNew = new HashMap<Integer, ArrayList<Integer>>();
   public HashMap<Integer, ArrayList<Integer>> outNew = new HashMap<Integer, ArrayList<Integer>>();
   public HashMap<String, ArrayList<liveRange>> slots = new HashMap<String, ArrayList<liveRange>>();
   public HashMap<String, ArrayList<liveRange>> newSlots = new HashMap<String, ArrayList<liveRange>>();
   public HashMap<String , HashMap<Integer, Register>> finalReg = new HashMap<String, HashMap<Integer,Register>>();
   public int magicNumber = 0;
   public int funcNumber = 0;
   public HashMap<String, Integer> maxArgu = new HashMap<String, Integer>();
   //new
   public HashMap<Integer, liveRange> liveList = new HashMap<Integer, liveRange>();
   
   /**
    * f0 -> "MAIN"
    * f1 -> StmtList()
    * f2 -> "END"
    * f3 -> ( Procedure() )*
    * f4 -> <EOF>
    */
   public R visit(Goal n) {
      R _ret=null;
      funcNumber = 0;
      namesOfFunctions.add("main");
      n.f0.accept(this);
      n.f1.accept(this);
      end.add(statementNumber - 1);
      n.f2.accept(this);
      maxArgu.put("main", funcNumber);
//      System.out.println(" main " + funcNumber );
      n.f3.accept(this);
      n.f4.accept(this);
      statementNumber--;
      //new
      
//      Iterator<Integer> itrOfUse = use.keySet().iterator();
//      while(itrOfUse.hasNext()){
//    	  Integer element = itrOfUse.next();
//    	  ArrayList<Integer> newHold = use.get(element);
//    	  System.out.println();
//    	  System.out.println(element + " ---> ");
//    	  Iterator<Integer> itr = newHold.iterator();
//    	  while(itr.hasNext()){
//    		  System.out.println(itr.next());
//    	  }
//      }
//      
//      System.out.println("Def starts ");
//      
//      itrOfUse = def.keySet().iterator();
//      while(itrOfUse.hasNext()){
//    	  Integer element = itrOfUse.next();
//    	  ArrayList<Integer> newHold = def.get(element);
//    	  System.out.println();
//    	  System.out.println(element + " ---> ");
//    	  Iterator<Integer> itr = newHold.iterator();
//    	  while(itr.hasNext()){
//    		  System.out.println(itr.next());
//    	  }
//      }
      
      //new
      
      
      
      
      // Building successors
      for(int i = 1 ; i <= statementNumber ; i++){
    	  if(i == statementNumber){
    		  successors.put(i, null);
    		  break;
    	  }
    	  ArrayList<Integer> temp = new ArrayList<Integer>();
    	  if(end.contains(i)){
    		  
    	  }else{
    		  temp.add(i+1);
    	  }
    	  successors.put(i, temp);
      }
      
      Iterator<Integer> itrForSucc = succForJump.keySet().iterator();
      while(itrForSucc.hasNext()){
    	  Integer element = itrForSucc.next();
    	  jumpOrCjump hold = succForJump.get(element);
    	  ArrayList<Integer> temp = null; 
    	  if(hold.jOrCj.equals("j")){
    		  temp = new ArrayList<Integer>();
    		  temp.add(statementNumberOfLabel.get(hold.label));
    		  successors.put(element, temp);
    	  }else{
    		  temp = successors.get(element);
    		  temp.add(statementNumberOfLabel.get(hold.label));
    		  successors.put(element, temp);
//    		  System.out.println(element + " " + statementNumberOfLabel.get(hold.label));
//    		  System.out.println(temp.get(0) + " " + temp.get(1));
    	  }
      }
      
      
//      itrForSucc = successors.keySet().iterator();
//      while(itrForSucc.hasNext()){
//    	  Integer element = itrForSucc.next();
//    	  ArrayList<Integer> hold = successors.get(element);
//    	  if(hold != null){
//	    	  Iterator<Integer> bla = hold.iterator();
//	    	  System.out.println(element + " ---->");
//	    	  while(bla.hasNext()){
//	    		  System.out.println(bla.next());
//	    	  }
//    	  }
//    	  System.out.println();
//      }
//       Building successors
      
      // Building in and out
      
      for(int i = 0 ; i <= statementNumber ; i++){
    	  in.put(i, new ArrayList<Integer>());
    	  out.put(i, new ArrayList<Integer>());
      }
      
      boolean check = true;
      while(check){
    	  check = false;
    	  for(int i = 1 ; i <= statementNumber ; i++){
    		  inNew.put(i, in.get(i));
    		  outNew.put(i, out.get(i));
    		  ArrayList<Integer> temp = new ArrayList<Integer>();
    		  Iterator<Integer> itr = out.get(i).iterator();
    		  while(itr.hasNext()){
    			  Integer element = itr.next();
    			  if(def.get(i).contains(element)){
    				  continue;
    			  }else{
    				  temp.add(element);
    			  }
    		  }
    		  itr = use.get(i).iterator();
    		  while(itr.hasNext()){
    			  Integer element = itr.next();
    			  if(temp.contains(element)){
    				  continue;
    			  }else{
    				  temp.add(element);
    			  }
    		  }
    		  in.put(i, temp);
    		  // 4th statement
    		  if(i == statementNumber)
    			  continue;
    		  itr = successors.get(i).iterator();
    		  ArrayList<Integer> hold = new ArrayList<Integer>();
    		  while(itr.hasNext()){
    			  Integer element = itr.next();
    			  Iterator<Integer> iterOfIn = in.get(element).iterator();
    			  while(iterOfIn.hasNext()){
    				  Integer value = iterOfIn.next();
    				  if(hold.contains(value)){
    					  continue;
    				  }else{
    					  hold.add(value);
    				  }
    			  }
    		  }
    		  out.put(i, hold);
    	  }
    	  
    	  
    	  for(int i = 1; i <=statementNumber ; i++){
    		  Iterator<Integer> itr = in.get(i).iterator();
    		  ArrayList<Integer> temp = inNew.get(i);
    		  while(itr.hasNext()){
    			  if(temp.contains(itr.next())){
    				  continue;
    			  }else{
    				  check = true;
    			  }
    		  }
    		  itr = out.get(i).iterator();
    		  temp = outNew.get(i);
    		  while(itr.hasNext()){
    			  if(temp.contains(itr.next())){
    				  continue;
    			  }else{
    				  check = true;
    			  }
    		  }
    	  }
//    	  System.out.println(" iteration starts -----------------");
//    	  for(int i = 1 ; i <= statementNumber ; i++){
//        	  Iterator<Integer> itr = in.get(i).iterator();
//        	  System.out.println();
//        	  System.out.println(" " + i + " --->");
//        	  while(itr.hasNext()){
//        		  System.out.println(itr.next());
//        	  }
//        	  System.out.println();
//          }
      }
      //remove
//      for(int i = 1; i <= statementNumber; i++){
//    	  System.out.println();
//    	  System.out.println(i);
//	      Iterator<Integer> it0r = def.get(i).iterator();
//	      while(it0r.hasNext()){
//	    	  
//	    	  System.out.println(it0r.next());
//	      }
//      }
      //remove
      
//      System.out.println(" iteration starts -----------------");
//	  for(int i = 1 ; i <= statementNumber ; i++){
//    	  Iterator<Integer> itr = in.get(i).iterator();
//    	  System.out.println();
//    	  System.out.println(i + " --->");
//    	  while(itr.hasNext()){
//    		  System.out.println(itr.next());
//    	  }
//    	  System.out.println();
//      }
      
      // Building in and out
//      
//      
//      Iterator<Integer> itr = live.keySet().iterator();
//      while(itr.hasNext()){
//    	  Integer element = itr.next();
//    	  ArrayList<Integer> hold = live.get(element);
////    	  System.out.println();
////    	  System.out.println(element);
//    	  int start = hold.get(1);
//    	  int end = hold.get(hold.size() - 1);
//    	  liveRange node = new liveRange(start, end, element);
//    	  liveList.put(element, node);
////    	  System.out.println(start);
////    	  System.out.println(end);
////    	  Iterator<Integer> liveitr = hold.iterator();
////    	  while(liveitr.hasNext()){
////    		  System.out.println(liveitr.next());
////    	  }
////    	  System.out.println();
//      }
//      
////      itr = liveList.keySet().iterator();
////      while(itr.hasNext()){
////    	  int element = itr.next();
////    	  liveRange node = liveList.get(element);
////    	  System.out.println();
////    	  System.out.println(element);
////    	  System.out.println(node.start);
////    	  System.out.println(node.end);
////    	  System.out.println();
////      }
//      
//      Iterator<String>iter = statementNumberOfLabel.keySet().iterator();
//      while(iter.hasNext()){
//    	  String element = iter.next();
//    	  System.out.println(element + " " + (statementNumberOfLabel.get(element)));
//      }
//      
//      itr = succForJump.keySet().iterator();
//      while(itr.hasNext()){
//    	  Integer element = itr.next();
//    	  System.out.println(element + " " + (succForJump.get(element).label)+ " " +(succForJump.get(element).jOrCj));
//      }
      
      
      //new
      ArrayList<liveRange> range = new ArrayList<liveRange>();
      HashMap<Integer, liveRange> newHold = new HashMap<Integer, liveRange>();
      
      for(int i = 1; i <= statementNumber; i++){
    	  
    	  ArrayList<Integer> temp = in.get(i);
    	  Iterator<Integer> itrOfInList = temp.iterator();
    	  while(itrOfInList.hasNext()){
    		  Integer element = itrOfInList.next();
    		  if(newHold.containsKey(element)){
    			  continue;
    		  }else{
    			  newHold.put(element, new liveRange(i, -1, element));
    		  }
    	  }
    	  if(i == statementNumber){
    		  Iterator<Integer> itrOfNewHold = newHold.keySet().iterator();
    		  while(itrOfNewHold.hasNext()){
    			  Integer element = itrOfNewHold.next();
    			  liveRange helper = new liveRange(newHold.get(element).start, i, element);
    			  range.add(helper);
    		  }
    		  continue;
    	  }
    	  
    	  temp = in.get(i+1);
    	  Iterator<Integer> itrOfNewHold = newHold.keySet().iterator();
    	  ArrayList<Integer> track = new ArrayList<Integer>();
    	  while(itrOfNewHold.hasNext()){
    		  Integer element = itrOfNewHold.next();
    		  if(temp.contains(element)){
    			  continue;
    		  }else{
    			  liveRange helper = new liveRange(newHold.get(element).start, i, element);
    			  range.add(helper);
    			  track.add(element);
    		  }
    	  }
    	  
    	  Iterator<Integer> itrOfTrack = track.iterator();
    	  while(itrOfTrack.hasNext()){
    		  newHold.remove(itrOfTrack.next());
    	  }
      }
      Collections.sort(range);
      //tester
//      Iterator<liveRange> itrOfRange = range.iterator();
//      while(itrOfRange.hasNext()){
//    	  liveRange hold = itrOfRange.next();
//    	  System.out.println();
//    	  System.out.println(hold.name + " ---> " + hold.start + " - " + hold.end);
//      }
      
//      Iterator<Integer> jaffa = end.iterator();
//      while(jaffa.hasNext()){
//    	  Integer element = jaffa.next();
//    	  Iterator<liveRange> itr = range.iterator();
//    	  while(itr.hasNext()){
//    		  liveRange buss = itr.next();
//    		  if( ( buss.start <= element ) && ( buss.end > element)){
//    			  System.out.println(" buss babyb ");
//    			  System.out.println(buss.name + " " + buss.start + " " + buss.end);
//    		  }
//    	  }
//      }
//      Iterator<String> jaffa = namesOfFunctions.iterator();
//      Iterator<Integer> jaffa1 = end.iterator();
//      while(jaffa.hasNext()){
//    	  System.out.println(jaffa.next());
//    	  System.out.println(jaffa1.next());
//      }
      //tester
      
      
      
    Iterator<Integer> endOfFunc = end.iterator();
    while(endOfFunc.hasNext()){
  	  Integer element = endOfFunc.next();
//  	  Iterator<liveRange> raIterator = newRange.iterator();
  	  ArrayList<liveRange> temp = new ArrayList<liveRange>();
  	  liveRange hold;
  	  while(range.size() != 0){
  		  hold = range.get(0);
  		  if(hold.end <= element){
  			  temp.add(new liveRange(hold.start, hold.end, hold.name));
  			  range.remove(0);
  		  }else{
  			  break;
  		  }
  	  }
  	  slots.put(namesOfFunctions.get(0), temp);
  	  namesOfFunctions.remove(0);
    }
    ArrayList<liveRange> blowRange ;
    Iterator<String> itrOfSlots = slots.keySet().iterator();
    while(itrOfSlots.hasNext()){
    	String element = itrOfSlots.next();
    	blowRange = new ArrayList<liveRange>();
	    ArrayList<liveRange> newRange = slots.get(element);
	    int index = 0;
	    while(newRange.size() != 0){
	  	  index = 0;
	  	  liveRange hold = newRange.get(index);
	  	  liveRange temp = new liveRange(hold.start, hold.end, hold.name);
	  	  while(newRange.size() > index ){
	  		  if(newRange.get(index).name == hold.name){
	  			  liveRange now = newRange.get(index);
	  			  if(temp.end < now.end){
	  				  temp.end = now.end;
	  			  }
	  			  newRange.remove(index);
	  		  }else{
	  			  index++;
	  		  }
	  	  }
	  	  blowRange.add(temp);
	    }
	    
//	    Iterator<liveRange> itr = blowRange.iterator();
//	    while(itr.hasNext()){
//	    	liveRange hold = itr.next();
//	    	System.out.println(hold.name + " --> " + hold.start + " " + hold.end);
//	    }
	    
	    Collections.sort(blowRange);
	    newSlots.put(element, blowRange);
    }
    
//    Iterator<String> itrOfNewSlots = newSlots.keySet().iterator();
//    while(itrOfNewSlots.hasNext()){
//    	String element = itrOfNewSlots.next();
//    	System.out.println();
//    	System.out.println(element);
//	    Iterator<liveRange> itrOfNewRange = newSlots.get(element).iterator();
//	    while(itrOfNewRange.hasNext()){
//	  	  liveRange hold = itrOfNewRange.next();
//	  	  System.out.println(" " + hold.name + " --> " + hold.start + " - " +hold.end);
//	    }
//	    System.out.println();
//    }
    
    
    Iterator<String> itrOfNewSlots = newSlots.keySet().iterator();
    while(itrOfNewSlots.hasNext()){
    	String element = itrOfNewSlots.next();
//    	System.out.println();
//    	System.out.println(element);
//	    Iterator<liveRange> itrOfNewRange = newSlots.get(element).iterator();
//	    while(itrOfNewRange.hasNext()){
//	  	  liveRange hold = itrOfNewRange.next();
//	  	  System.out.println(" " + hold.name + " --> " + hold.start + " - " +hold.end);
//	    }
//	    System.out.println();
    	HashMap<Integer, Register> hi = linearScan(newSlots.get(element));
    	hi.put(-1, new Register(-1, maxArgu.get(element), "value"));
	    finalReg.put(element, hi);
//	    Iterator<Integer> itr = finalReg.get(element).keySet().iterator();
//	    while(itr.hasNext()){
//	    	Integer blue = itr.next();
//	    	Register hel = finalReg.get(element).get(blue);
//	    	System.out.println(hel.name + " " + hel.regNum + " " + hel.sorr);
//	    }
//	    System.out.println("I am out");
    }
    
      
      
//      System.out.println("Slots printing");
//      Iterator<String> itrOfSlots = slots.keySet().iterator();
//      while(itrOfSlots.hasNext()){
//    	  String element = itrOfSlots.next();
//    	  System.out.println();
//    	  System.out.println(element);
//    	  ArrayList<liveRange> temp = slots.get(element);
//    	  Iterator<liveRange> itrOfTemp = temp.iterator();
//    	  while(itrOfTemp.hasNext()){
//    		  liveRange hold = itrOfTemp.next();
//    		  System.out.println(hold.name + " ---> " + hold.start + " - " + hold.end);
//    	  }
//    	  System.out.println();
//      }
      
      //new
      _ret = (R)finalReg;
      return _ret;
   }

   /**
    * f0 -> ( ( Label() )? Stmt() )*
    */
   public R visit(StmtList n) {
      R _ret=null;
      n.f0.accept(this);
      return _ret;
   }

   public boolean notLabel = true;
   public boolean entering = false;
   /**
    * f0 -> Label()
    * f1 -> "["
    * f2 -> IntegerLiteral()
    * f3 -> "]"
    * f4 -> StmtExp()
    */
   public R visit(Procedure n) {
      R _ret=null;
      funcNumber = 0;
      notLabel = false;
      String label = (String)n.f0.accept(this);
      notLabel = true;
      namesOfFunctions.add(label);
      n.f1.accept(this);
      n.f2.accept(this);
      entering = true;
      arguments = Integer.parseInt((String)n.f2.f0.tokenImage);
      n.f3.accept(this);
      n.f4.accept(this);
      maxArgu.put(label, funcNumber);
//      System.out.println(label + "  " + funcNumber );
      return _ret;
   }

   public boolean inCall = false;
   public int arguments = -1;
   public int statementNumber = 1;
   public HashMap<Integer, ArrayList<Integer>> live = new HashMap<Integer, ArrayList<Integer>>();
   
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
   public R visit(Stmt n) {
      R _ret=null;
      //new
      use.put(statementNumber, new ArrayList<Integer>());
      def.put(statementNumber, new ArrayList<Integer>());
      if(entering){
    	  entering = false;
    	  ArrayList<Integer> newHold = def.get(statementNumber - 1);
    	  for(int i = 0 ; i < arguments ; i++){
    		  newHold.add(i);
    	  }
    	  def.put(statementNumber - 1 , newHold);
      }
      //new
      n.f0.accept(this);
      statementNumber++;
      return _ret;
   }

   /**
    * f0 -> "NOOP"
    */
   public R visit(NoOpStmt n) {
      R _ret=null;
      n.f0.accept(this);
      return _ret;
   }

   /**
    * f0 -> "ERROR"
    */
   public R visit(ErrorStmt n) {
      R _ret=null;
      n.f0.accept(this);
      return _ret;
   }

   /**
    * f0 -> "CJUMP"
    * f1 -> Temp()
    * f2 -> Label()
    */
   public R visit(CJumpStmt n) {
      R _ret=null;
      n.f0.accept(this);
      Integer temp = (Integer)n.f1.accept(this);
      
      //new
      
      ArrayList<Integer> newHold = use.get(statementNumber);
      newHold.add(temp);
      use.put(statementNumber, newHold);
      
      //new
      
      notLabel = false;
      String label = (String)n.f2.accept(this);
      succForJump.put(statementNumber, new jumpOrCjump(label, "c"));
      notLabel = true;
      if(temp >= arguments){
	      ArrayList<Integer> hold = live.get(temp);
	      hold.add(statementNumber);
	      live.put(temp, hold);
      }
      return _ret;
   }

   /**
    * f0 -> "JUMP"
    * f1 -> Label()
    */
   public R visit(JumpStmt n) {
      R _ret=null;
      n.f0.accept(this);
      notLabel = false;
      String label = (String)n.f1.accept(this);
      succForJump.put(statementNumber, new jumpOrCjump(label, "j"));
      notLabel = true;
      return _ret;
   }

   /**
    * f0 -> "HSTORE"
    * f1 -> Temp()
    * f2 -> IntegerLiteral()
    * f3 -> Temp()
    */
   public R visit(HStoreStmt n) {
      R _ret=null;
      n.f0.accept(this);
      Integer temp1 = (Integer)n.f1.accept(this);
      //new
      	ArrayList<Integer> newHold = use.get(statementNumber);
      	newHold.add(temp1);
      	
      //new
      
      
      ArrayList<Integer> hold = null;
      if(temp1 >= arguments){
	      hold = live.get(temp1);
	      hold.add(statementNumber);
	      live.put(temp1, hold);
      }
      n.f2.accept(this);

      Integer temp2 = (Integer)n.f3.accept(this);
      //new
      
      newHold.add(temp2);
      use.put(statementNumber, newHold);
      //new
      if(temp2 >= arguments){
      hold = live.get(temp2);
      hold.add(statementNumber);
      live.put(temp2, hold);
      }
      return _ret;
   }

   /**
    * f0 -> "HLOAD"
    * f1 -> Temp()
    * f2 -> Temp()
    * f3 -> IntegerLiteral()
    */
   public R visit(HLoadStmt n) {
      R _ret=null;
      n.f0.accept(this);
      Integer temp1 = (Integer)n.f1.accept(this);
      //new
      ArrayList<Integer> newHold = def.get(statementNumber);
      newHold.add(temp1);
      def.put(statementNumber, newHold);
      //new
      ArrayList<Integer> hold = null;
      if(temp1 >= arguments){
	      if(live.containsKey(temp1)){
	    	  hold = live.get(temp1);
	      }else{
	    	  hold = new ArrayList<Integer>();
	      }
	      hold.add(-1);
	      hold.add(statementNumber);
	      live.put(temp1, hold);
      }
      Integer temp2 = (Integer)n.f2.accept(this);
      //new
      
      newHold = use.get(statementNumber);
      newHold.add(temp2);
      use.put(statementNumber, newHold);
      
      //new
      if(temp2 >= arguments){
	      hold = live.get(temp2);
	      hold.add(statementNumber);
	      live.put(temp2, hold);
      }
      n.f3.accept(this);
      return _ret;
   }

   /**
    * f0 -> "MOVE"
    * f1 -> Temp()
    * f2 -> Exp()
    */
   public R visit(MoveStmt n) {
      R _ret=null;
      n.f0.accept(this);
      Integer temp = (Integer)n.f1.accept(this);
      //new
      
      ArrayList<Integer> newHold = def.get(statementNumber);
//      System.out.println(statementNumber);
//      System.out.println(temp);
      newHold.add(temp);
      def.put(statementNumber, newHold);
      
      //new
      ArrayList<Integer> hold = null;
      if(temp >= arguments){
	      if(live.containsKey(temp)){
	    	  hold = live.get(temp);
	      }else{
	    	  hold = new ArrayList<Integer>();
	      }
	      hold.add(-1);
	      hold.add(statementNumber);
	      live.put(temp, hold);
      }
      n.f2.accept(this);
      return _ret;
   }

   /**
    * f0 -> "PRINT"
    * f1 -> SimpleExp()
    */
   public R visit(PrintStmt n) {
      R _ret=null;
      n.f0.accept(this);
      n.f1.accept(this);
      return _ret;
   }

   /**
    * f0 -> Call()
    *       | HAllocate()
    *       | BinOp()
    *       | SimpleExp()
    */
   public R visit(Exp n) {
      R _ret=null;
      n.f0.accept(this);
      return _ret;
   }

   /**
    * f0 -> "BEGIN"
    * f1 -> StmtList()
    * f2 -> "RETURN"
    * f3 -> SimpleExp()
    * f4 -> "END"
    */
   public R visit(StmtExp n) {
      R _ret=null;
      n.f0.accept(this);
      n.f1.accept(this);
      n.f2.accept(this);
      
      use.put(statementNumber, new ArrayList<Integer>());
      def.put(statementNumber, new ArrayList<Integer>());
      n.f3.accept(this);
      end.add(statementNumber);
//      System.out.println(statementNumber);
      n.f4.accept(this);
      statementNumber++;
      return _ret;
   }

   /**
    * f0 -> "CALL"
    * f1 -> SimpleExp()
    * f2 -> "("
    * f3 -> ( Temp() )*
    * f4 -> ")"
    */
   public R visit(Call n) {
      R _ret=null;
      n.f0.accept(this);
      n.f1.accept(this);
      n.f2.accept(this);
      inCall = true;
      n.f3.accept(this);
      if(funcNumber < magicNumber){
    	  funcNumber = magicNumber;
      }
      magicNumber = 0;
      inCall = false;
      n.f4.accept(this);
      return _ret;
   }

   /**
    * f0 -> "HALLOCATE"
    * f1 -> SimpleExp()
    */
   public R visit(HAllocate n) {
      R _ret=null;
      n.f0.accept(this);
      n.f1.accept(this);
      return _ret;
   }

   /**
    * f0 -> Operator()
    * f1 -> Temp()
    * f2 -> SimpleExp()
    */
   public R visit(BinOp n) {
      R _ret=null;
      n.f0.accept(this);
      Integer temp = (Integer)n.f1.accept(this);
      //new
      
      ArrayList<Integer> newHold = use.get(statementNumber);
      newHold.add(temp);
      use.put(statementNumber, newHold);
      
      //new
      
      
      if(temp >= arguments){
	      ArrayList<Integer> hold = live.get(temp);
	      hold.add(statementNumber);
	      live.put(temp, hold);
      }
      n.f2.accept(this);
      return _ret;
   }

   /**
    * f0 -> "LT"
    *       | "PLUS"
    *       | "MINUS"
    *       | "TIMES"
    */
   public R visit(Operator n) {
      R _ret=null;
      n.f0.accept(this);
      return _ret;
   }

   /**
    * f0 -> Temp()
    *       | IntegerLiteral()
    *       | Label()
    */
   public R visit(SimpleExp n) {
      R _ret=null;
      if(n.f0.which == 0){
    	  Integer temp = (Integer)n.f0.accept(this);
    	  //new
    	  
    	  ArrayList<Integer> newHold = use.get(statementNumber);
//    	  System.out.println(statementNumber);
    	  newHold.add(temp);
    	  use.put(statementNumber, newHold);
    	  
    	  //new
    	  if(temp >= arguments){
	    	  ArrayList<Integer> hold = live.get(temp);
	    	  hold.add(statementNumber);
	    	  live.put(temp, hold);
    	  }
      }else{
    	  if(n.f0.which == 2)
    		  notLabel = false;
    	  n.f0.accept(this);
    	  if(n.f0.which == 2)
    		  notLabel = true;
      }
      return _ret;
   }

   /**
    * f0 -> "TEMP"
    * f1 -> IntegerLiteral()
    */
   public R visit(Temp n) {
      R _ret=null;
      n.f0.accept(this);
      n.f1.accept(this);
      Integer temp = Integer.parseInt(((String)n.f1.f0.tokenImage));
      //new
      
      if(inCall){
    	  magicNumber++;
    	  ArrayList<Integer> newHold = use.get(statementNumber);
    	  newHold.add(temp);
    	  use.put(statementNumber, newHold);
      }
      
      //new
      
      
      if(temp >= arguments){
	      if(inCall){
	    	  ArrayList<Integer> hold = live.get(temp);
	    	  hold.add(statementNumber);
	    	  live.put(temp, hold);
	      }
      }
      _ret = (R)temp;
      return _ret;
   }

   /**
    * f0 -> <INTEGER_LITERAL>
    */
   public R visit(IntegerLiteral n) {
      R _ret=null;
      n.f0.accept(this);
      return _ret;
   }

   /**
    * f0 -> <IDENTIFIER>
    */
   public R visit(Label n) {
      R _ret=null;
      n.f0.accept(this);
      if(notLabel){
    	  statementNumberOfLabel.put(n.f0.tokenImage, statementNumber);
      }
      _ret = (R)n.f0.tokenImage;
      return _ret;
   }

}
