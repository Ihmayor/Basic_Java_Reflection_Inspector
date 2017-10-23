package CodedInspector;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class ObjectInspector {

	public void inspect(Object toInspect, boolean isRecursive )
	{
		
		Class classObj = toInspect.getClass();
				
		//The name of the declaring class
		String declaringClass = classObj.getDeclaringClass().getName();
		System.out.println("Declaring Class:\t"+declaringClass);
		//The name of the immediate superclass
		String superClass = classObj.getSuperclass().getName();
		System.out.println("Immediate Superclass:\t"+declaringClass);
		//the name of the interfaces the class implements 
		Class[] superInterfaces = classObj.getInterfaces();
		for (inter: superInterfaces)
		{
			
		}
		//The method of the class declared 
			//Find each:
			//Exceptions thrown
			//param types
			//return types
			//modifiers
		//Get all Constructors
			//Param types
			//modifiers
		
		//The fields the class declares
			//Type
			//Modifiers
			//If field contains object:
			//if recursive => Inspect nested object
			//If not => print hash code.
		
		//Must be able to handle arrays (names, component Type, length, all contents)
		
		//Carry over hashcodes recursively
		
		
		
		
		
		
	}
	
}
