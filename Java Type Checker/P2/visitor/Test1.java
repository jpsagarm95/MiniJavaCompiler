package visitor;

public class Test1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
			String a = "sagar,suraj,ravi,kumari";
			String b = "sagar,suraj,ravi";
			String front[] = a.split(",", 2);
			String back[] = b.split(",", 2);
			while(front.length == 2 && back.length == 2){
				System.out.println(front[0]);
				System.out.println(back[0]);
				front = front[1].split(",", 2);
				back = back[1].split(",", 2);
			}
			if(front.length == 2 || back.length == 2){
				System.out.println("Error");
			}
			System.out.println(front[0]);
			System.out.println(back[0]);
			
	}

}
