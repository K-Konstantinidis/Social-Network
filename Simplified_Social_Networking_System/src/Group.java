import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class Group implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String name;
	private String description;
	private ArrayList<String> ListOfMembersInGroup = new ArrayList<>();
	private boolean flag;
	private boolean flag1;
	private int number;
	private int i;
	
	public Group(String name, String description)
	{
		this.name = name;
		this.description = description;
	}
	
	public boolean IsInGroup(User user)
	{
		//If group is empty
		if(ListOfMembersInGroup.isEmpty())
		{
			flag1 = false;
		}
		else
		{
			flag = ListOfMembersInGroup.contains(user.getName());
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
	
	public void EnrollInGroup(User user)
	{
		flag = IsInGroup(user); //See if user is already in the group or not
		if(flag == false) //If they are not
		{
			ListOfMembersInGroup.add(user.getName()); //Add user in the group list
			System.out.println(user.getName() +" has been successfully enrolled in group "+ name);
			JOptionPane.showMessageDialog(null, user.getName() +" has been successfully enrolled in group "+ name);
		}
		else
		{
			JOptionPane.showMessageDialog(null, "You are already in this group");
		}
	}

	public int getNumber() {
		return number;
	}

	public int getI() {
		return i;
	}

	public String getDescription() {
		return description;
	}

	public String getName() {
		return name;
	}
}
