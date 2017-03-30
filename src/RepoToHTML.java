import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

public class RepoToHTML {
	String path;
	String css_path;
	Repositorie my_repo;
	String templates_root="../templates";
	Configuration cfg;
	public RepoToHTML(String in_path,Repositorie my_repo,String templates){
		this.path=in_path;
		this.my_repo=my_repo;
		this.templates_root=templates;
		this.cfg=null;
	}
	
	public RepoToHTML(String in_path,Repositorie my_repo){
		this(in_path,my_repo,"../html/templates");
	}

	public void changePath(String new_path){
		path=new_path;
	}

	public void changeRepo(Repositorie new_repo){
		my_repo=new_repo;
	}

	private void init_lib(){
		Configuration cfg = new Configuration(Configuration.VERSION_2_3_25);
		try {
			cfg.setDirectoryForTemplateLoading(new File(templates_root));
			cfg.setDefaultEncoding("UTF-8");
			cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
			cfg.setLogTemplateExceptions(false);
		} catch (IOException e) {
			e.printStackTrace();
		}	
		this.cfg=cfg;		
	}		
	private void generatepage(Map root,String tpl_root,String out_root){
		try {
			Template temp = cfg.getTemplate(tpl_root);
			
			Writer out = new FileWriter(out_root);
			try {
				temp.process(root, out);
			} catch (TemplateException e) {
				e.printStackTrace();
			}
			
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void generateHTML(){
		System.out.println("Start of HTML output");
		this.init_lib();
		File dir = new File("output_page");
		dir.mkdir();
		this.path+="/output_page";
		Map<String, Object> root = new HashMap<>();
//generate the header (no need)
		root.put("cur_page","home");
		root.put("comittors", my_repo.get_writters().size());
		root.put("repo_name",my_repo.get_name());
		root.put("num_of_file",my_repo.num_of_files());
		root.put("num_of_lines",my_repo.num_of_lines());
		root.put("num_of_branches",my_repo.num_branch());
		root.put("num_of_commits",my_repo.num_commits());
		root.put("num_of_tags",my_repo.num_of_tags());
		this.generatepage(root, "index.tpl",this.path+"/home.html");
		System.out.println("End of home.html");

		root = new HashMap<>();
		root.put("cur_page","comittors");
		root.put("comittors", my_repo.get_writters().size());
		root.put("num_comittors", my_repo.get_writters().size());
		root.put("total_comitts", my_repo.num_commits());
		root.put("repo_name",my_repo.get_name());//my_repo.get_name()
		root.put("comittors", my_repo.get_writters());
		this.generatepage(root, "comittors.tpl",path+"/comittors.html");
		System.out.println("End of comittors.html");

		int i;
		dir = new File(path+"/branches");
		dir.mkdir();
		my_repo.num_commits_per_writter();
//		my_repo.commitors_branch();
		for(i=0;i<my_repo.get_branches().size();i++){
			root = new HashMap<>();
			root.put("cur_page","a_branch");
			root.put("repo_name",my_repo.get_name());
			root.put("the_branch",my_repo.get_branches().get(i));
//			System.out.println(temp);
//			System.out.println(path+"branches/"+my_repo.get_branches().get(i).getName()+".html");
			Set<Comittor> my_set=my_repo.get_branches().get(i).comittors_set();
			if(my_set!=null && my_set.size()!=0){
				Comittor[] all_comittors = null;
				all_comittors = my_set.toArray(new Comittor[my_set.size()]);
				int size=all_comittors.length;
				int[] comitts_of_comittors=new int[size];
				for(int j=0;j<all_comittors.length;j++){
					comitts_of_comittors[j]=all_comittors[j].amount[i];
				}
				root.put("the_comittors",all_comittors);
				root.put("comitts_of_comittors",comitts_of_comittors);
			}else{
				root.put("the_comittors",null);
				root.put("comitts_of_comittors",null);				
			}
			
			this.generatepage(root,"a_branch.tpl",path+"/branches/"
					+my_repo.get_branches().get(i).getName().replaceAll("/", "_")+".html");
		}
		System.out.println("End of sub_branche.html");
		
		root = new HashMap<>();
		root.put("cur_page","branches");
		root.put("repo_name",my_repo.get_name());
		root.put("branches", my_repo.get_branches());
		root.put("total_comitts", my_repo.num_commits());
		this.generatepage(root, "branches.tpl",path+"/branches.html");
		
		root= new HashMap<>();
		root.put("cur_page", "about");
		root.put("repo_name",my_repo.get_name());
		this.generatepage(root, "about.tpl",path+"/about.html");
		System.out.println("End of branches.html");

		System.out.println("End of output");
	}
	
}
