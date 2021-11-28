/*************************************************************************
	Copyright © 2021 Konstantinidis Konstantinos

	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at 

	      http://www.apache.org/licenses/LICENSE-2.0

	Unless required by applicable law or agreed to in writing, software 
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and 
	limitations under the License.
*************************************************************************/
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
