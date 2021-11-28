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
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class GUI extends JFrame implements Serializable{	

	private User user1 = new User(); //Make a new user to call the user methods
	private boolean flag;
	private boolean flag1;
	private User userPost;
	private Post posts; 
	
	private JButton newUser = new JButton("New User");
	private JTextField inputName = new JTextField("user name", 10);
	private JTextField inputMail = new JTextField("user mail", 10);
	private JButton UserPage = new JButton("Enter User Page");
	private JButton PotentialInfections = new JButton("Show Potential Infections");
	private JButton PamakBook = new JButton("Save MyBook");
	private JPanel mainpanel = new JPanel();
	
	private JList<String> listView = new JList<String>();
	private DefaultListModel<String> model = new DefaultListModel<String>();
	private AllObjects object;
	private ArrayList<Group> AllGroups = new ArrayList<>();
		
	public GUI(ArrayList<Group> groups)
	{	
		AllGroups = groups;
		try {
			FileInputStream fIn2 = new FileInputStream("Book.ser");
			if(fIn2.read() == -1) { //If the file is empty
				System.out.println("File is empty");
				fIn2.close();
			}else {
				FileInputStream fIn = new FileInputStream("Book.ser");
				ObjectInputStream in = new ObjectInputStream(fIn);
			
				object = (AllObjects) in.readObject(); //Add the list with the users, the list with the groups and the list with the posts from the file
				
				user1.listWithAllUsers = object.getListWithUsers();//Add in a list all the users and their relations
				user1.listWithUserPosts = object.getListWithPosts();//Add in a list all the posts
				AllGroups = object.getListWithGroups();//Add the groups
					
				in.close();
				fIn.close();
				System.out.println("File read");
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		for(Group g: AllGroups)
			model.addElement(g.getName()); //Add the groups to a model
		
		listView.setModel(model);
		
		mainpanel.add(newUser);
		mainpanel.add(inputName);
		mainpanel.add(inputMail);
		mainpanel.add(UserPage);
		mainpanel.add(PotentialInfections);
		mainpanel.add(PamakBook);		

		newUser.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				String name = inputName.getText(); //Get the name
				String mail = inputMail.getText(); //Get the mail
				flag = false;
				if(!user1.listWithAllUsers.isEmpty()) //If there are users
				{
					for(User u: user1.listWithAllUsers) //For every user in the list
					{
						if(name.equals(u.getName())) //If their name equals to the on given in the text field
						{
							JOptionPane.showMessageDialog(null, "User " + name + " already taken");
							flag = false;
							break;
						}
						else
							flag = true;
					}
					if(flag == true)
						createUser(name, mail); //If there is no user with the same name
				}
				else
					createUser(name, mail); //If there are no users create the 1st one
			}
		});
		
		UserPage.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				String name = inputName.getText(); //Get the name
				for(User u: user1.listWithAllUsers)
				{
					if(!u.getName().equals(name))
						flag1 = false; 
					else
					{
						flag1 = true;
						userPost = u; //Users u posts
						break;
					}
				}
				if(flag1 == false) { //If user is not in the list of users
					if(name.isEmpty()) {
						JOptionPane.showMessageDialog(null, "You have to enter a Username");
					}
					else {
						JOptionPane.showMessageDialog(null, "User '" + name + "' Not Found");
					}
				}
				else
					newUserPage(userPost, listView, AllGroups);
			}
		});
		
		PotentialInfections.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				String name = inputName.getText(); 
				for(User u: user1.listWithAllUsers) //For every user in the list with the users
				{
					if(!u.getName().equals(name)) //If name is not in the list
						flag1 = false;
					else
					{
						flag1 = true;
						userPost = u;
						break;
					}
				}
				if(flag1 == false) //If name is not in the list
					if(name.isEmpty()) {
						JOptionPane.showMessageDialog(null, "You have to enter a Username");
					}
					else {
						JOptionPane.showMessageDialog(null, "User '" + name + "' Not Found");
					}
				else
					newUserPage2(userPost);
			}
		});
		
		PamakBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/*Create an object variable which contains: a list with the users and 
				  their relations, a list with the groups and a list with the posts*/
				if(!user1.listWithAllUsers.isEmpty() && !user1.listWithUserPosts.isEmpty()) {
					object = new AllObjects(user1.listWithAllUsers, AllGroups, user1.listWithUserPosts);
					try {
						FileOutputStream fOut = new FileOutputStream("Book.ser");
						ObjectOutputStream out = new ObjectOutputStream(fOut);
						
						out.writeObject(object); //Add all the list in the file
						
						out.close();
						fOut.close();
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					}		
					System.out.println("File created / updated");
				}else {
					JOptionPane.showMessageDialog(null, "There are no users and/or posts, so you cannot save the current file." +"\n" + "First add a user and a post and then try again!");
				}
			}
		});
		
		this.setContentPane(mainpanel);
		this.setResizable(false);
		this.setVisible(true);
		this.setSize(400, 150);
		this.setTitle("Main Panel");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	protected void createUser(String name, String mail) 
	{
		if(name.isEmpty()) { //If there is no name
			JOptionPane.showMessageDialog(null, "You have to enter a Username");
		}
		else if(name.length() > 15) { //Name must be less than 16 characters
			JOptionPane.showMessageDialog(null, "Username must be less than 16 characters");
		}
		else {
			if(!isValid(mail)) {// If the email isn't valid
				JOptionPane.showMessageDialog(null, "The email is not valid");
			}else {
				User u = new User(name, mail); //Create new user
				user1.addUserToList(u); //Add user in the list
				System.out.println("New User: " + name + " Created");
				JOptionPane.showMessageDialog(null, "New User: " + name + " Created");
				inputMail.setText(""); //Clear mail text field
				inputName.setText(""); //Clear name text field
			}
		}
	}

	void newUserPage(User user, JList<String> listView, ArrayList<Group> groups) 
	{
		JTextField userName = new JTextField(user.getName());
		JTextField userMail = new JTextField(user.getMail());
		JButton backToLoginButton = new JButton("Back to Login Screen");
		JTextArea postDescription = new JTextArea();
		JButton postButton = new JButton("Post");
		JLabel recentPosts = new JLabel("Recent Posts by Friends");
		JTextArea allTheRecentPosts = new JTextArea(user.PrintFriendsPosts()); //Show all the friends posts
		JLabel suggestedFriends = new JLabel("Suggested Friends");
		JTextArea nameOfSuggestedFriends = new JTextArea(user.SuggestedFriends()); //Show the suggested friends
		JPanel panel = new JPanel(new GridBagLayout());
		panel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		JButton friendButton = new JButton("Become Friends");
		JTextField makeFriend = new JTextField("Enter name...",10);
		JButton groupButton = new JButton("Enter Group");
				
		gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.ipadx = 20;
        gbc.insets = new Insets(0, 4, 50, 4);
		panel.add(userName, gbc);
		
        gbc.gridx++;
        panel.add(userMail, gbc);
        
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = GridBagConstraints.RELATIVE;
		panel.add(backToLoginButton, gbc);
		
		gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.ipadx = 0;
        gbc.insets.set(0, 4, 0, 4);
        gbc.gridwidth = 3;
        postDescription.setLineWrap(true);
        postDescription.setPreferredSize(new Dimension(220,150));
        postDescription.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		panel.add(postDescription, gbc);

		gbc.gridx = 2;
		panel.add(postButton, gbc);
		
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.insets = new Insets(10, 2, 0, 2);
		gbc.gridwidth = 1;
		panel.add(recentPosts, gbc);
		
		gbc.fill = GridBagConstraints.NONE;
		gbc.gridx = 1;
		gbc.gridwidth = 2;
		allTheRecentPosts.setLineWrap(true);
		allTheRecentPosts.setWrapStyleWord(true);
        allTheRecentPosts.setPreferredSize(new Dimension(250 ,150));
        allTheRecentPosts.setAutoscrolls(true);
        allTheRecentPosts.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        allTheRecentPosts.setEditable(false);
		panel.add(allTheRecentPosts, gbc);
		
		gbc.fill = GridBagConstraints.NONE;
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 1;
		panel.add(suggestedFriends, gbc);
		
		gbc.fill = GridBagConstraints.NONE;
		gbc.gridx = 1;
		gbc.gridwidth = 1;
		nameOfSuggestedFriends.setLineWrap(true);
		nameOfSuggestedFriends.setPreferredSize(new Dimension(40 ,50));
		panel.add(nameOfSuggestedFriends, gbc);	
		
		gbc.fill = GridBagConstraints.NONE;
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 0;
		gbc.insets = new Insets(0,0,0,0);
		panel.add(makeFriend);
		
		gbc.fill = GridBagConstraints.NONE;
		gbc.gridx = GridBagConstraints.RELATIVE;;
		panel.add(friendButton);
		
		gbc.fill = GridBagConstraints.NONE;
		gbc.gridx = 0;
		gbc.gridy = 4;
		panel.add(listView);
		
		gbc.fill = GridBagConstraints.NONE;
		gbc.gridx = 1;
		panel.add(groupButton);		
		
		backToLoginButton.addActionListener(new ActionListener(){
			//Back to log in panel
			public void actionPerformed(ActionEvent e) {
				setContentPane(mainpanel);
				setVisible(true);
				setSize(400, 150);
				setTitle("Main Panel");
				setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			}
		});
		
		postButton.addActionListener(new ActionListener(){
			//Add a new post
			public void actionPerformed(ActionEvent e) {
				String post = postDescription.getText();
				if(!post.isEmpty()) {
					posts = new Post(post, user); //Make new post for the user
					posts.AddNewPost(user1); //Add post to the posts list
					posts.AddToFriendsPosts(); //Add post to the friends posts lists
					postDescription.setText(""); //Clear post text field
				}else {
					JOptionPane.showMessageDialog(null, "You cannot make an empty post");
				}
			}
		});
		
		friendButton.addActionListener(new ActionListener() {
			//Make new friend
			public void actionPerformed(ActionEvent e) {
				String friendName = makeFriend.getText();
				flag = false;
				for(User u: user1.listWithAllUsers) //For every user
				{
					if(u.getName().equals(friendName)) //If user exists
					{
						flag = true;
						break;
					}
					else
						flag = false;
				}
				if(flag == true) //If user exists
				{
					flag1 = user1.IsFriend(user, friendName);
					if(flag1 == false) //If they are not already friends
					{
						user1.MakeFriend(user, friendName); //Make user friend with friendName
					}
					else
						JOptionPane.showMessageDialog(null, "You are already friends or you are trying to become friend with yourself");
				}
				else
					JOptionPane.showMessageDialog(null, "The user you are trying to connect with, does not exist");
			}
		});
		
		groupButton.addActionListener(new ActionListener() {
			//Add to group
			public void actionPerformed(ActionEvent e) {
				String selectedGroupName = (String) listView.getSelectedValue(); //Get the group name
				
				Group selectedGroup = null;
				for(Group g: AllGroups)
				{
					if(g.getName().equals(selectedGroupName))
						selectedGroup = g; //Find which is the selected group
				}
				
				if(selectedGroup == null) { //If there is no selected group
					JOptionPane.showMessageDialog(null, "You have not selected a group");
				}
				else if(selectedGroup.getName().equals("WebGurus"))//Open Group
				{
					selectedGroup.EnrollInGroup(user); //Add the user in the group
				}
				else //Closed Group
				{
					((ClosedGroup) selectedGroup).EnrollInClosedGroup(user); //Try to enroll the user in the closed group
				}
			}
		});
		
		this.setContentPane(panel);
		this.setSize(1000, 550);
		this.setVisible(true);
		this.setTitle("User Page");
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	void newUserPage2(User user)
	{
		JTextArea Infection = new JTextArea(user.PrintFriendsIfInfectedGUI());
		JButton backToLoginButton = new JButton("Back to Login Screen");
		JPanel panel = new JPanel();
		
		Infection.setEditable(false);
		
		panel.setLayout(null);
		Infection.setBounds(10, 5, 430, 250);
		panel.add(Infection);
		
		backToLoginButton.setBounds(145, 280, 180, 25);
		panel.add(backToLoginButton);
		
		backToLoginButton.addActionListener(new ActionListener(){
			//Back to log in panel
			public void actionPerformed(ActionEvent e) {
				setContentPane(mainpanel);
				setVisible(true);
				setSize(400, 150);
				setTitle("Main Panel");
				setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			}
		});
		
		//Show the potential infections
		this.setContentPane(panel);
		
		this.setVisible(true);
		this.setResizable(false);
		this.setSize(470, 350);
		this.setTitle("Potential Infections");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static boolean isValid(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                            "[a-zA-Z0-9_+&*-]+)*@" +
                            "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                            "A-Z]{2,7}$";
                              
        Pattern pat = Pattern.compile(emailRegex); //Compile the regex into a pattern
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

}
