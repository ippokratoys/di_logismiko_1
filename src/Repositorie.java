import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Repositorie {
	private String path;
	private ArrayList<Comittor> comittors;
	private int total_commits;
	private ArrayList<Branch> branch;
	private int total_branches;
	private String my_name;
	public Repositorie(String path){
		//takes the path of the directory with the repository
		this.path=path;
		comittors=null;
		branch=null;
		total_commits=-1;
		total_branches=-1;
		my_name=this.set_name();
		this.num_of_files();
		this.num_of_lines();
		this.get_writters();
		this.get_branches();
		this.branch_update();
		this.commitors_branch();
		//checks if it's a repo else exits
	}

	public int num_of_files(){
		String s;//buffer gia to output
		Process get_files_proc;
		int num_files=0;
		int exit_value=0;
		s="git -C "+ path + " ls-files";
		try{
			get_files_proc = Runtime.getRuntime().exec(s);
			BufferedReader buffer_out = new BufferedReader(
					new InputStreamReader(get_files_proc.getInputStream()));
			//diabazei mia mia tis grammes
			while ((s = buffer_out.readLine()) != null){
				num_files++;
			}
			get_files_proc.waitFor();//perimenei na teliwsei
            exit_value=get_files_proc.exitValue();
		} catch (Exception e){}
		if(exit_value!=0){
			System.out.println("Error getting number of files");
			System.out.println("Git exit status : "+exit_value);
		}
		return num_files;
	}

	public int num_of_lines(){
		Process p1;
		String s;
		int lines,totallines=0;
		s="git -C "+path+" ls-files";
		try{
			p1=Runtime.getRuntime().exec(s);
			  BufferedReader br1 = new BufferedReader(new InputStreamReader(p1.getInputStream()));
		            while ((s = br1.readLine()) != null){
		            	try{
		            		BufferedReader reader = new BufferedReader(new FileReader(path+"/"+s));
		            		lines = 0;
		            		while (reader.readLine() != null) lines++;
		            		reader.close();
		            		totallines+=lines;
		            	}catch(Exception e){}

		            }
		            p1.waitFor();
		            p1.destroy();
		}catch(Exception e){}
		return totallines ;
	}

	public ArrayList<Comittor> get_writters(){
		if(comittors==null){//an den exei arxikopoithei
			comittors= new ArrayList<Comittor>();
			total_commits=0;
			String buff_str="git -C "+path+" shortlog -s -n --all";
			String split[];
			String name;
			int cur_commits;
			Process p_get_writters;
			try {
				p_get_writters=Runtime.getRuntime().exec(buff_str);
        		BufferedReader buff_read=new BufferedReader(new InputStreamReader(p_get_writters.getInputStream()));
        		while((buff_str=buff_read.readLine()) != null){
        			split=buff_str.trim().split("\t");
        			cur_commits=Integer.parseInt(split[0]);
        			name=split[1];
//        			writters_commits.add(Integer.parseInt(split[0]));
        			total_commits+=cur_commits;
//        			writters.add(split[1]);
        			comittors.add(new Comittor(name,cur_commits));
        		}
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.get_insertations_deletes();
		}
		return comittors;
	}

	public int num_commits(){
		if(total_commits==-1){
			this.get_writters();
		}
		return total_commits;
	}

	public int num_writters(){
		return this.get_writters().size();
	}

	public ArrayList<Comittor> num_commits_per_writter(){
		if(total_commits==-1){
			this.get_writters();
		}
		return comittors;
	}

	public int num_branch(){
		if(total_branches==-1){
			this.get_branches();
		}
		return total_branches;
	}

	public ArrayList<Branch> get_branches(){
		if(branch==null){
			total_branches=0;
			branch=new ArrayList<Branch>();
			String s,line;
			String[] split;
			String master_s=null;
			Process p;
			s="git -C "+path+" branch --all -r";
			try{
				p=Runtime.getRuntime().exec(s);
				BufferedReader br=new BufferedReader(new InputStreamReader(p.getInputStream()));
				while((line=br.readLine())!=null){
					if(line.contains("->")){
						split = line.split("->");
						master_s=split[1].replaceAll(" ","");//eat white spaces
						continue;
					}
					total_branches++;
					branch.add(new Branch(line.replace(" ",""),"01/01/1970","01/01/1970"));
				}
				int i;
				for(i=0;i<branch.size();i++){
					if(branch.get(i).name.equals(master_s)){
						branch.get(i).master=true;
						break;
					}
				}
			}catch(IOException e){
				e.printStackTrace();}
		}
		return branch;
	}
	public int num_of_tags(){
		int count;
		count=0;
		String s;//buffer gia to output
		Process get_files_proc;
		int exit_value=0;
		s="git -C "+ path + " tag -l";
		try{
			get_files_proc = Runtime.getRuntime().exec(s);
			BufferedReader buffer_out = new BufferedReader(
					new InputStreamReader(get_files_proc.getInputStream()));
			//diabazei mia mia tis grammes
			while ((s = buffer_out.readLine()) != null){
				count++;
			}
			get_files_proc.waitFor();//perimenei na teliwsei
            exit_value=get_files_proc.exitValue();
		} catch (Exception e){}
		if(exit_value!=0){
			System.out.println("Error getting number of files");
			System.out.println("Git exit status : "+exit_value);
		}
		return count;
	}
	public ArrayList<Branch>branch_update(){
		if(branch==null){
			this.get_branches();
		}
		Process branch_dates_p = null;
		String s,prev = null;
		String split[];
		String cmp_branch="origin/HEAD";
		int exit_value=0;
		int i,flag1=0,flag2=0;
		int flag;
		Comitt comitt;
		Comittor comittor = null;
		SimpleDateFormat format =new SimpleDateFormat
		("\"E MMM d kk:mm:ss yyyy Z");
		Date ndate;
		for(i=0;i<branch.size();i++){
			flag=0;
			if(branch.get(i).comitts==null){
				branch.get(i).comitts=new ArrayList<Comitt>();
				//the command
				s= "git -C "+ path +" log "+cmp_branch+".."+branch.get(i).getName().replaceAll(" ", "") +" --format=\"%cd.!.%cn.!.%H.!.%s.!.%d\"";
				try {
					branch_dates_p = Runtime.getRuntime().exec(s);
					BufferedReader buffer_out = new BufferedReader(
					new InputStreamReader(branch_dates_p.getInputStream()));
					flag1=0;
					while((s=buffer_out.readLine())!=null){
						flag1=1;
						flag=1;
						split=s.split(".!.");
						ndate=format.parse(split[0]);
						for(int j=0;j<comittors.size();j++){
							if(comittors.get(j).getName().equals(split[1])){
								comittor=comittors.get(j);
								break;
							}
						}
//						System.out.println("|"+split[3]+"|");
						if(split[4].contains("tag")){
							comitt=new Comitt(split[2],split[3],ndate,comittor,split[4].replaceAll("\"", ""));
						}else{
							comitt=new Comitt(split[2],split[3],ndate,comittor," ");
						}
						branch.get(i).comitts.add(comitt);
					}
					if(branch.get(i).master || flag==0){
						int flag3=0;
						s= "git -C "+ path +" log "+branch.get(i).getName().replaceAll(" ", "") +" --format=\"%cd.!.%cn.!.%H.!.%s.!.%d\"";
						branch_dates_p = Runtime.getRuntime().exec(s);
						BufferedReader buffer_out2 = new BufferedReader(
						new InputStreamReader(branch_dates_p.getInputStream()));
						while((s=buffer_out2.readLine())!=null){
							flag3=1;
							split=s.split(".!.");
							ndate=format.parse(split[0]);
							for(int j=0;j<comittors.size();j++){
								if(comittors.get(j).getName().equals(split[1])){
									comittor=comittors.get(j);
									break;
								}
							}
							if(split[4].contains("tag")){
								comitt=new Comitt(split[2],split[3],ndate,comittor,split[4].replaceAll("\"", ""));
							}else{
								comitt=new Comitt(split[2],split[3],ndate,comittor," ");
							}
							branch.get(i).comitts.add(comitt);
						}
					}
					
					try {
						branch_dates_p.waitFor();
						branch_dates_p.destroy();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			if(branch.get(i).comitts.size()!=0){
				branch.get(i).d_updated=branch.get(i).getComitts().get(0).getThe_date();
				branch.get(i).d_creation=branch.get(i).getComitts().get(branch.get(i).comitts.size()-1).getThe_date();
			}

		}
		return branch;
	}

	public String get_name(){
		return my_name;
	}

	private String set_name(){
		Process p;
		String s;
		String cur_name = null;
		s="git -C "+path+ " config --get remote.origin.url";
		String split[];
		try {
			p=Runtime.getRuntime().exec(s);
			BufferedReader br= new BufferedReader(new InputStreamReader(p.getInputStream()));
			s=br.readLine();
			split=s.split("/");
			cur_name=split[split.length-1];
		} catch (IOException e) {
			cur_name="Default";
//			e.printStackTrace();
		}
		System.out.println(cur_name);
		return cur_name;
	}
	public void commitors_branch(){
		int count;
		if(comittors==null){
			this.get_writters();
		}
		if(branch==null){
			this.get_branches();
			this.branch_update();
		}
		for(int k=0;k<comittors.size();k++){
			if(comittors.get(k).amount==null){
				comittors.get(k).amount=new int[branch.size()];
				for(int i=0;i<branch.size();i++)comittors.get(k).amount[i]=0;
			}
		}
		for(int i=0;i<branch.size();i++){
			for(int j=0;j<branch.get(i).comitts.size();j++){
				for(int k=0;k<comittors.size();k++){
					if(comittors.get(k).equals(branch.get(i).comitts.get(j).getThe_commitor())){
						comittors.get(k).amount[i]+=1;
						break;
					}
				}
			}
		}		
	}
	
	public Date getCreation_date(){
		if(branch==null){
			this.get_branches();
			this.branch_update();
		}
		for(int i=0;i<branch.size();i++){
			if(branch.get(i).master){
				if(branch.get(i).d_creation==null){
					break;
				}
				return branch.get(i).d_creation;
			}
		}
		
//an den brhke kati
		this.branch_update();
		for(int i=0;i<branch.size();i++){
			if(branch.get(i).master){
				return branch.get(i).d_creation;
			}
		}
		return null;
	}
	public void get_insertations_deletes(){
		Process p;
		Comittor comittor=null;
		String split1[],split2[];
		int flag=0;
		String s="git -C "+path+" log --shortstat --format=\"%an.!.\"";
		try {
			p=Runtime.getRuntime().exec(s);
			BufferedReader br= new BufferedReader(new InputStreamReader(p.getInputStream()));
			while((s=br.readLine())!=null){
				if(s.contains(".!.")){
					flag=0;
					for(int i=0;i<comittors.size();i++){
						if(comittors.get(i).name.equals(s.replaceAll(".!.","").replaceAll("\"",""))){
							flag=1;
							comittor=comittors.get(i);
							break;
						}
					}
				}
				if((s.contains("insertions") || s.contains("insertion") || s.contains("deletions") || s.contains("deletion") || s.contains("file changed") || s.contains("files changed"))&&flag==1){
					split1=s.split(",");
					for(int i=0;i<split1.length;i++){
						split2=split1[i].split(" ");
						//vazw to 1 gt ekei einai o arithmos
						if(split2[2].contains("file")|| split2[2].contains("files"))comittor.modified+= Integer.parseInt(split2[1]);	
						if(split2[2].contains("insertion")||split2[2].contains("insertions"))comittor.insertions+= Integer.parseInt(split2[1]);
						if(split2[2].contains("deletion")||split2[2].contains("deletions"))comittor.deletes+= Integer.parseInt(split2[1]);
////						
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}