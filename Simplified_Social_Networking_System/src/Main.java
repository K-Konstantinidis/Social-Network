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
