import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Branch {
	private static SimpleDateFormat dateformat=new SimpleDateFormat("dd/MM/yyyy");
	String name;
	Date d_creation;
	Date d_updated;
	ArrayList<Comitt> comitts;
	Boolean master;
	public static void setformat_s(String new_format) {
		dateformat = new SimpleDateFormat(new_format);
	}

	public Branch(String name,String creation,String update) {
		this.name=name;
		this.master=false;
		this.comitts=null;
		try {
			this.d_creation=dateformat.parse(creation);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			this.d_updated=dateformat.parse(update);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public Branch(){
		this("default","01/01/1970","01/01/1970");
	}

	public static SimpleDateFormat getDateformat() {
		return dateformat;
	}

	public static void setDateformat(SimpleDateFormat dateformat) {
		Branch.dateformat = dateformat;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getD_creation() {
		return d_creation;
	}

	public void setD_creation(Date d_creation) {
		this.d_creation = d_creation;
	}

	public Date getD_updated() {
		return d_updated;
	}

	public void setD_updated(Date d_updated) {
		this.d_updated = d_updated;
	}

	public ArrayList<Comitt> getComitts() {
		return comitts;
	}

	public void setComitts(ArrayList<Comitt> comitts) {
		this.comitts = comitts;
	}
	
	public int getNum_comitts(){
		return comitts.size();
	}
	
	public Set<Comittor> comittors_set(){
		Set<Comittor> my_comittors = new HashSet<Comittor>();
		int i;
		String cur_name;
		if(comitts==null || comitts.size()==0){
			return null;
		}
		for(i=0;i<comitts.size();i++){
			my_comittors.add(comitts.get(i).getThe_commitor());	
		}
		return my_comittors;//set me tous comittors
	}
}