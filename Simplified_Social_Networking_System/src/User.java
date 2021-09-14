import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;

import javax.swing.JOptionPane;

public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String name;
	private String mail;
	private ArrayList<User> ListOfFriends = new ArrayList<>();//List with users friends
	private boolean flag;
	private boolean flag1;
	private int i;
	private int j;
	private User someone;
	private String Names[] = new String[100];
	private ArrayList<String> NamesOf_User_FriendsToNotSuggest = new ArrayList<>(); //Array with the name of the logged in user and the names of their friends
	private ArrayList<String> NamesOf_User_UserFriends_FriendsFriends = new ArrayList<>(); //Array with the logged in user, their friends and the friends of their friends
	
	public ArrayList<User> listWithAllUsers = new ArrayList<>();
	public ArrayList<Post> listWithUserPosts = new ArrayList<>();
	public TreeSet<Post> treeWithPosts = new TreeSet<>(new FriendsPostsComperatorToDate()); //A tree with the posts of every user and their friends
	public static final int SCROLLBARS_BOTH = 0;
	
	public User()
	{
		name = null;
		mail = null;
	}
	
	public User(String name ,String mail)
	{
		setName(name);
		setMail(mail);
	}
	
	public boolean IsFriend(User user, String friendName)
	{
		flag1 = true;
		if(!user.getName().equals(friendName)) //If it is not the same person
		{
			//If the list with the friends is empty
			if(user.ListOfFriends.isEmpty())
			{
				flag1 = false;
			}
			else
			{
				for(i = 0; i < user.ListOfFriends.size(); i++)
				{
					flag = user.ListOfFriends.get(i).getName().equals(friendName);
					if(flag == false)
					{
						flag1 = false; //If the name is not in the list
					}
					else
					{
						flag1 = true; //If the name is in the list
						break;
					}
				}
			}
		}
		return flag1;
	}
	
	public void MakeFriend(User user, String friendName)
	{
		for(User u: listWithAllUsers) //For every user in the list with the users
		{
			if(u.getName().equals(friendName)) //When the friendName user is found
			{
				user.ListOfFriends.add(u);//Add friendName to users friend list
				u.ListOfFriends.add(user);//Add user to friendName friend list

				break;
			}
		}
		System.out.println(user.getName() +" and "+ friendName + " are now friends!" );
		JOptionPane.showMessageDialog(null, user.getName() +" and "+ friendName + " are now friends!");
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}
	
	public void addUserToList(User u) {
		listWithAllUsers.add(u); //Add user to the list with all the users
	}
	
	public void addPostToList(Post post) {
		listWithUserPosts.add(post); //Add post to the list with all the posts	
	}
	
	public void FriendsPosts(Post post)
	{
		treeWithPosts.add(post); //Add post to the tree of the user
		for(User u: post.getUser().ListOfFriends) { //For every user who is friend with the user who made the post
			u.treeWithPosts.add(post); ////Add post to their tree
		}
	}
	
	public String PrintFriendsPosts() 
	{
		String Friendposts = ""; //A variable for the posts
		Iterator<Post> iter = treeWithPosts.iterator(); //Make an iterator for the tree
		
		while(iter.hasNext()) 
		{
			Post fPosts = iter.next();
			Friendposts = Friendposts + fPosts.getUser().getName() + ", " + fPosts.getPostDate() + ", " + fPosts.getDescription() + "\n";
		}
		
		if(Friendposts.isEmpty())
			return "There are no recent posts";
		else
			return Friendposts;
	}
	
	public String SuggestedFriends() 
	{
		String SuggestedFriends = "";
		flag = false;
		flag1 = false;
		NamesOf_User_UserFriends_FriendsFriends.add(name);//Add in 1st place of the array the logged in user 
		
		for(i = 0; i < ListOfFriends.size(); i++)
		{
			NamesOf_User_UserFriends_FriendsFriends.add(ListOfFriends.get(i).getName());//Add the friends of the user in the array
		}
			
		for(j = 0; j < ListOfFriends.size(); j++) //For every friend of the user
		{
			for(int k = 0; k < ListOfFriends.get(j).ListOfFriends.size(); k++)
			{
				if(!NamesOf_User_UserFriends_FriendsFriends.contains(ListOfFriends.get(j).ListOfFriends.get(k).getName()))//If the user is not already in the array						
					{						
						NamesOf_User_UserFriends_FriendsFriends.add(ListOfFriends.get(j).ListOfFriends.get(k).getName());
					}
			}
		}

		NamesOf_User_FriendsToNotSuggest.add(name);//Add in 1st place of the array the logged in user
		
		for(i=0; i<ListOfFriends.size(); i++)
		{
			NamesOf_User_FriendsToNotSuggest.add(ListOfFriends.get(i).getName());//Add the friends of the user in the array
		}
		
		NamesOf_User_UserFriends_FriendsFriends.removeAll(NamesOf_User_FriendsToNotSuggest); //Remove the user and their friends from the list
		for(String sugNames: NamesOf_User_UserFriends_FriendsFriends)
			SuggestedFriends = SuggestedFriends + sugNames + " ";
		
		if(SuggestedFriends.isEmpty()) {
			return "No" + "\n" + "suggestions";
		}
		return SuggestedFriends;
	}
	
	public String PrintFriendsIfInfectedGUI()
	{
		String InfectedFriends = "";
		int isEmpty = 0;
		InfectedFriends += "***********************************************************************************" + "\n";
		InfectedFriends += "'" + this.name +"' has been infected. " + "The following users have to be tested:" + "\n";
		InfectedFriends += "***********************************************************************************" + "\n";
		
		flag = false;
		
		Names[0] = this.name;//Add in the 1st place of the array the name of the infected user, so after the comparisons the name will not appear again
		
		for(i = 0; i < ListOfFriends.size(); i++)
		{
			InfectedFriends += ListOfFriends.get(i).getName() + "\n";
			Names[i + 1] = ListOfFriends.get(i).getName();//Add the friends of the infected user in the array
			isEmpty++;
		}
		
		for(j = 0; j < ListOfFriends.size(); j++)
		{
			someone = ListOfFriends.get(j); //For every friend of the infected user
			for(int k = 0; k < someone.ListOfFriends.size(); k++)
			{
				flag = false;
				for(int t = 0; t < i + 1; t++)
				{
					if(Names[t].equals(someone.ListOfFriends.get(k).getName()))//If the name is already in the Infected array
					{
						flag = true;
						break;
					}
				}
				if(flag == false)
				{
					InfectedFriends += someone.ListOfFriends.get(k).getName() + "\n";
					Names[i + 1]=someone.ListOfFriends.get(k).getName();//Add the name in the array
					i++;
					isEmpty++;
				}
			}
		}
		if(isEmpty == 0) {
			InfectedFriends = "You have no friends to check for infection." + "\n" +"Make some friends and try again!";
			return InfectedFriends;
		}
		return InfectedFriends;
	}
}

class FriendsPostsComperatorToDate implements Comparator<Post>,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public int compare(Post o1, Post o2) 
	{
		String date1 = o1.getPostDate();
		String date2 = o2.getPostDate();
		
		return date2.compareTo(date1);
	}	
}
