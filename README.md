# GRL2FSM
Input: Goal model specification using XtGRL notation with CTL. 
Output: A compliant FSM. 
The Steps of execution are: 
•	We provide an XtGRL grammar which is written using Xtext plug-in in eclipse. One needs to create the Xtext file containing the grammar at first.
•	Then create an acceleo project and include the xtext in the runtime environment.
•	This project will contain an ndsl file where we need to specify our input.
•	We create an acceleo module file goalextract.mtl which parses the given input (input.ndsl) and extracts the actor name,goal name,child goals, CTL string and so on.
•	Once this step is done we need to create a java project in the same module where we put all the code for generating the compliant finite state model (ProcessGoal.java). 
•	The code in goalextract.mtl uses java wrapper services to call the functions in ProcessGoal.java. 
•	We require another acceleo module file ProcessGoalmtl.mtl which contains all the java functions. This module file contains the java function definitions and invokes the java wrapper services. 
•	When all the above steps are done we need to run configurations of goalextract.mtl file and set the source(input) and target(output) folder and then apply the settings. 
•	Now we are done, we can execute it to check for the output. 
Remarks: Three input examples have been provided along with the console output and the compliant FSM generated.
