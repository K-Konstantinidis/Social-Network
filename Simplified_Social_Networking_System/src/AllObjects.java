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
