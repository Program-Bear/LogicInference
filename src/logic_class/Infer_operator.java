package logic_class;
import java.util.*;
import java.lang.*;
class Inferrance {
	Inferrance premise;
	Inferrance conclusion;
	int type;// 0 shows null, 1 shows p, 2 shows q, 3 shows another
	int logic;
	final int NULL_INFER = 0;
	final int P_INFER = 1;
	final int Q_INFER = 2;
	final int OTHER = 3;
	
	Inferrance(){
		type = NULL_INFER;
		premise = null;
		conclusion = null;
		logic = total_logic();
	}
	Inferrance(int num){
		type = num;
		premise = null;
		conclusion = null;
		logic = total_logic();
	}
	Inferrance(Inferrance p, Inferrance c){
		type = OTHER;
		premise = p;
		conclusion = c;
		logic = total_logic();
	}
	
	boolean calculate(boolean p, boolean q) {
		if (type == NULL_INFER) return true;
		if (type == P_INFER) return p;
		if (type == Q_INFER) return q;
		if (premise.calculate(p,q) == true && conclusion.calculate(p,q) == false) {
			return false;
		}
		return true;
	}
	
	int total_logic() {
		int answer = 0;
		if (calculate(true,true)) answer += 1;
		if (calculate(true,false)) answer += 2;
		if (calculate(false,true)) answer += 4;
		if (calculate(false,false)) answer += 8;
		return answer;
	}
	
	public String toString() {
		if (type == NULL_INFER) return "null";
		if (type == P_INFER) return "p";
		if (type == Q_INFER) return "q";
		return "(" + premise.toString() + "-->" + conclusion.toString() + ")";
	}
}
public class Infer_operator {
	public static void main(String args[]) {
		int all_logic[] = new int[16];
		for (int i = 0; i < all_logic.length; i++) {
			all_logic[i] = 0;
		}
		ArrayList<Inferrance> infers = new ArrayList<Inferrance>();
		infers.add(new Inferrance(1));
		infers.add(new Inferrance(2));
		all_logic[infers.get(0).total_logic()] = 1;
		all_logic[infers.get(1).total_logic()] = 1;
		while(true) {
			int total_size = infers.size();
			
			System.out.println("count start");
			for (int i = 0; i < total_size; i++) {
				System.out.println(infers.get(i));
			}
			System.out.println("count end");
			for(int i = 0; i < total_size;i++) {
				int last_index = total_size - 1;
				Inferrance pre_infer = new Inferrance(infers.get(last_index), infers.get(i));
				Inferrance con_infer = new Inferrance(infers.get(i), infers.get(last_index));
				int pre_logic = pre_infer.logic;
				int con_logic = con_infer.logic;
				System.out.println(pre_infer + " " +  pre_logic);
				System.out.println(con_infer + " " + con_logic);
				boolean find = false;
				if (all_logic[pre_logic] == 0) {
					System.out.println("add " + pre_infer + " " +  pre_logic);
					all_logic[pre_logic] = 1;
					infers.add(pre_infer);
					find = true;
				}
				if (all_logic[con_logic] == 0) {
					System.out.println("add " + con_infer + " " + con_logic);
					all_logic[con_logic] = 1;
					infers.add(con_infer);
					find = true;
				}	
				if (find) break;
			}
			if (total_size == infers.size()) break;
		}
		for (int i = 0; i < infers.size(); i++) {
			System.out.println(infers.get(i) + " can produce " + infers.get(i).logic);
		}
	}
}
