import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		int number_of_files=0;
		int number_of_lines=0;

		ArrayList<Comittor> coms;
		ArrayList<Branch>   branches;

		Scanner reader = new Scanner(System.in);  // Reading from System.in
		System.out.println("Enter the path of repo(better full): ");
		String path= reader.nextLine();
		Repositorie my_repo = new Repositorie(path);
		reader.close();
		number_of_files=my_repo.num_of_files();
		number_of_lines=my_repo.num_of_lines();
		System.out.println("Number of Files : " + number_of_files);
		System.out.println("Number of lines in Files : " + number_of_lines);
		System.out.println("Number of branches: " + my_repo.num_branch());

		coms=my_repo.get_writters();
		System.out.println("Number of commiters : "+ coms.size() );
		int tags=my_repo.num_of_tags();
		System.out.println("Number of tags : "+tags);
		System.out.printf("Print the writers(%d) Commits(%d):\n",my_repo.num_writters(),my_repo.num_commits());
		branches=my_repo.get_branches();
		branches=my_repo.branch_update();
		RepoToHTML html=new RepoToHTML("./",my_repo,"html/templates");
		html.generateHTML();
	}

}