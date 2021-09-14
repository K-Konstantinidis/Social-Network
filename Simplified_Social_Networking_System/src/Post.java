import java.io.Serializable;
import java.util.Date;

public class Post implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Date postDate;
	private String description;
	private User user;
	
	public Post(String postText, User u) {
		postDate = new Date();
		user = u;
		description = postText;
	}
	
	public void AddNewPost(User u) {
		u.addPostToList(this);
	}
	
	public void AddToFriendsPosts() {
		user.FriendsPosts(this);
	}

	@SuppressWarnings("deprecation")
	public String getPostDate() {
		return postDate.toLocaleString();
	}

	public String getDescription() {
		return description;
	}

	public User getUser() {
		return user;
	}
}
