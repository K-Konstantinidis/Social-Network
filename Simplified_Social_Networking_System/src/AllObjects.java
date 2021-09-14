import java.io.Serializable;
import java.util.ArrayList;

public class AllObjects implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private ArrayList<User> listWithUsers;
	private ArrayList<Group> listWithGroups;
	private ArrayList<Post> listWithPosts;
	
	public AllObjects(ArrayList<User> listWithAllUsers, ArrayList<Group> groups, ArrayList<Post> listWithUserPosts) {
		listWithUsers = listWithAllUsers;
		listWithPosts = listWithUserPosts;
		listWithGroups = groups;
	}

	public ArrayList<User> getListWithUsers() {
		return listWithUsers;
	}

	public ArrayList<Group> getListWithGroups() {
		return listWithGroups;
	}

	public ArrayList<Post> getListWithPosts() {
		return listWithPosts;
	}
	
}
