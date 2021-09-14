import java.util.ArrayList;

public class Main {
	public static void main(String[] args) {
		Group g1 = new Group("WebGurus", "A group for web passionates"); //Create an open group
		ClosedGroup g2 = new ClosedGroup("ExamSolutions", "Solutions to common exam questions"); //Create a closed group
		
		ArrayList<Group> groups = new ArrayList<Group>();//List with the 2 groups
		groups.add(g1);//Add the groups
		groups.add(g2);
		
		new GUI(groups);
	}
}
