import java.util.Date;

public class Comitt {
	private String id;
	private String Message;
	private Date the_date;
	private Comittor the_commitor;
	private String tag;
	public Comitt(String in_id,String in_message,Date in_date,Comittor in_commitor,String tag){
		id=in_id;
		Message=in_message;
		the_date=in_date;
		the_commitor=in_commitor;
		this.setTag(tag);
	}
	public Comitt(){
		this("F","U",null,null," ");
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMessage() {
		if(Message==null || Message==""){
			System.out.println("wth");
		}
		return Message;
	}
	public void setMessage(String message) {
		Message = message;
	}
	public Date getThe_date() {
		return the_date;
	}
	public void setThe_date(Date the_date) {
		this.the_date = the_date;
	}
	public Comittor getThe_commitor() {
		return the_commitor;
	}
	public void setThe_commitor(Comittor the_commitor) {
		this.the_commitor = the_commitor;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}

}
