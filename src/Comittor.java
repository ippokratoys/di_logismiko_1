import java.util.ArrayList;

public class Comittor {
	String name;
	int num_of_commits;
	int amount[];
	int insertions;
	int deletes;
	int modified;
	public Comittor(String name,int commits){
		amount=null;
		this.name=name;
		this.num_of_commits=commits;
		insertions=0;
		deletes=0;
		modified=0;
	}

	public Comittor(){
		this("",-1);
	}

//geters-seters for name
	public String getName(){
		return name;
	}

	public void setName(String new_name){
		name = new_name;
	}

//geters-seters for num_of_commits
	public void setNum_of_commits(int num){
		num_of_commits=num;
	}

	public int getNum_of_commits(){
		return num_of_commits;
	}

	public int getInsertions() {
		return insertions;
	}

	public void setInsertions(int insertions) {
		this.insertions = insertions;
	}

	public int getDeletes() {
		return deletes;
	}

	public void setDeletes(int deletes) {
		this.deletes = deletes;
	}

	public int getModified() {
		return modified;
	}

	public void setModified(int modified) {
		this.modified = modified;
	}

	public double getPer_day(){
		//have to find his first date
		double amount=this.num_of_commits/365;
		return amount;
	}
	public double getPer_month(){
		//have to find his first date
		double amount=this.num_of_commits/12;
		return amount;
	}
	public double getPer_week(){
		//have to find his first date
		double amount=this.num_of_commits/52;
		return amount;		
	}

}
