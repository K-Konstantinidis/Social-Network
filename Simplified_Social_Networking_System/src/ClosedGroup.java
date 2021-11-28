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

import javax.swing.JOptionPane;

public class ClosedGroup extends Group implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String name;
	private String description;
	private ArrayList<String> ListOfMembersInClosedGroup = new ArrayList<>();
	private boolean flag;
	private boolean flag1;
	private int number;
	private int i;

	public ClosedGroup(String name, String description) 
	{
		super(name, description);
		this.name = name;
		this.description = description;
	}


	public boolean IsInClosedGroup(User user)
	{
		//If group is empty
		if(ListOfMembersInClosedGroup.isEmpty())
		{
			flag1 = false;
		}
		else
		{
			flag = ListOfMembersInClosedGroup.contains(user.getName());
			if(flag == false)
			{
				flag1 = false; //If name is not in the list
			}
			else
			{
				flag1 = true; //If name is in the list
			}
		}
		return flag1;
	}
	
	public void EnrollInClosedGroup(User user) 
	{
		flag = IsInClosedGroup(user); //See if user is already in the group or not
		flag1 = false;
		
		if(ListOfMembersInClosedGroup.isEmpty())//If closed group list is empty
		{
			ListOfMembersInClosedGroup.add(user.getName()); //Add the user as the 1st one in the list
			System.out.println(user.getName() +" has been successfully enrolled in closed group "+ name);
			JOptionPane.showMessageDialog(null, user.getName() +" has been successfully enrolled in closed group "+ name);
			flag1 = true;
		}
		else if(flag == false) //If the list has members and user is not one of them
		{
			for(i = 0; i < ListOfMembersInClosedGroup.size(); i++)
			{
				if(user.IsFriend(user, ListOfMembersInClosedGroup.get(i))) //If user has a friend in the closed group
				{
					ListOfMembersInClosedGroup.add(user.getName()); //Add user in the list
					System.out.println(user.getName() +" has been successfully enrolled in group "+ name);
					JOptionPane.showMessageDialog(null, user.getName() +" has been successfully enrolled in closed group "+ name);
					flag1 = true;
					break;
				}
			}
			if(flag1 == false) //If the user has no friends in the closed group
			{
				JOptionPane.showMessageDialog(null, "You cannot enrol in this group");
			}
		}
		else //If user is already in the group
		{
			JOptionPane.showMessageDialog(null, "You are already in this group");
		}
	}
	
	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}


	public int getNumber() {
		return number;
	}
}
