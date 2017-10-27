package testPackage;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class Inspector {

	//Base Inspect Method to be called by the driver
	public void inspect(Object toInspect, boolean isRecursive) {
		//Fetch Class Object for further insepction
		Class classObj = toInspect.getClass();

		// The name of the declaring class
		String declaringClass = classObj.getName();
		System.out.println("Declaring Class:\t" + declaringClass);

		//Inspect SuperClasses
		inspectSuperClass(toInspect);
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		
		//Inspected Implemented Interfaces of this class
		inspectSuperInterface(toInspect);
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		
		//Inspect Methods
		inspectMethods(toInspect,"");
		
		//Inspect Constructors
	    inspectConstructors(toInspect,"");
		
		//Inspect Fields
		inspectFields(toInspect, classObj, isRecursive, new int[] {});
	}
	
	//inspect constructors of given object. Tab accordingly (if recursive)
	private void inspectConstructors(Object toInspect, String tabOver)
	{
		Class classObj;
		if (toInspect.getClass().getName() == "java.lang.Class")
			classObj = (Class)toInspect;
		else
			classObj = toInspect.getClass();
		// Get all Constructors
		Constructor[] constructors = classObj.getDeclaredConstructors();
		System.out.println();
		System.out.println();
		System.out.println(tabOver + "Declared Constructor Amount: "+constructors.length);
		// Get all Constructors
		for (Constructor c : constructors) {
			System.out.println(tabOver+"Constructor");
			System.out.println(tabOver+"===============================================");
			// Param types
			for (Class param : c.getParameterTypes()) {
				System.out.println(tabOver + "Parameter Types: " + param.getName());
			}

			// modifiers
			System.out.println(tabOver + "Constructor Modifier (int): \t" + c.getModifiers());
			System.out.println(tabOver + "Constructor Modifier (string): \t" + Modifier.toString(c.getModifiers()));
			System.out.println();
			System.out.println();
		}
	}
	
	//inspect methods of given object. Tab accordingly (if recursive)
	private void inspectMethods(Object toInspect, String tabOver)
	{
		Class classObj = toInspect.getClass();
		// The method of the class declared
		System.out.println(tabOver+"Declared Methods of Class");
		System.out.println(tabOver+"======================================================");
		Method[] declaredMethods = classObj.getDeclaredMethods();
		for (Method m : declaredMethods) {
			// Find each:
			System.out.println();
			System.out.println();
			System.out.println(tabOver+"======================================================");
		    System.out.println(tabOver + "Method Name: \t" + m.getName());
			// Exceptions thrown
			Class[] exceptions = m.getExceptionTypes();
			System.out.println(tabOver+"Method Exceptions Amount: "+exceptions.length );
			for (Class e : exceptions) {
				System.out.println(tabOver + "Exception Name: \t" + e.getName());
			}
			// Parameter types
			Class[] parameters = m.getParameterTypes();
			System.out.println(tabOver+"Method Parameters Amount: "+parameters.length );
			for (Class param : parameters) {
				System.out.println(tabOver + "Parameter Name: \t" + param.getName());
			}
			// return types
			System.out.println(tabOver + "Method Return Type: \t" + m.getReturnType().getName());
			// modifiers
			System.out.println(tabOver + "Method Modifier (int): \t" + m.getModifiers());
			System.out.println(tabOver + "Method Modifier (string): \t" + Modifier.toString(m.getModifiers()));
		}
	}
	
	//Inspect Super Interfaces
	public void inspectSuperInterface(Object childObj)
	{
		
		Class classObj = childObj.getClass();
		// the name of the interfaces the class implements
				Class[] superInterfaces = classObj.getInterfaces();
				for (Class inter : superInterfaces) {
					System.out.println("Implemented Interface: " + inter.getName());
					System.out.println("Inspecting Interface");
					System.out.println("===================================================");
					for (Method m : inter.getDeclaredMethods()) {
						// Find each:
						System.out.println("Method Name: \t" + m.getName());
					}
					inspectConstructors(inter, "");
					
					System.out.println();
					System.out.println();
					System.out.println("Declared Fields Amount: "+inter.getDeclaredFields().length);
					System.out.println("=============================================================================");
					for (Field f : inter.getDeclaredFields()) {
						System.out.println("Field Name:\t" + f.getName());
						try {
							System.out.println("Field Value:\t" + getNonRecursiveValue(f.get(inter)));
						} catch (IllegalArgumentException | IllegalAccessException e) {
							e.printStackTrace();
						}
					}
					System.out.println("======================================================");
					System.out.println("Finished Inspecting Interface");
					
					if (inter.getInterfaces().length > 0) {
						System.out.println("=====================================================");
						System.out.println("Exploring Super Interfaces of superinterface " + inter.getName() );
						inspectSuperInterface(inter);
						System.out.println("End Exploring Super Interfaces of superinterface "+ inter.getName());
						System.out.println("=====================================================");
					}
					else
					{
						System.out.println("This superinterface implements no other superinterfaces");
					}
				}

	}
	
	//Inspect Super Class
	public void inspectSuperClass(Object childObj)
	{
		Class classObj = childObj.getClass();
		// The name of the immediate superclass
		if (classObj.getSuperclass() != null) {
			System.out.println("Immediate Superclass:\t" + classObj.getSuperclass().getName());
			System.out.println("Inspecting Superclass");
			System.out.println("===================================================");
			Class superClass = classObj.getSuperclass();

			for (Method m : superClass.getDeclaredMethods()) {
				// Find each Method:
				System.out.println("Method Name: \t" + m.getName());
			}
			inspectConstructors(superClass,"");
			
			System.out.println();
			System.out.println();
			System.out.println();
			System.out.println();
			System.out.println("Declared Fields Amount: "+superClass.getDeclaredFields().length);;
				for (Field f : superClass.getDeclaredFields()) {
				System.out.println("Field Name:\t" + f.getName());
				f.setAccessible(true);
				try {
					System.out.println("Field Value:\t" + getNonRecursiveValue(f.get(childObj)));
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
			if (superClass.getSuperclass() != null) {
				System.out.println("=====================================================");
				System.out.println("Exploring SuperClass of Super Class " + superClass.getName() );
				inspectSuperClass(superClass);
				System.out.println("End Exploring Super Class of Super Class "+ superClass.getName());
				System.out.println("=====================================================");
			}
			else
			{
				System.out.println("This superclass extends no superclass");
			}

			
			System.out.println("======================================================");
			System.out.println("Finished Inspecting SuperClass");
		}
	}

	// Method to inspected recursively w/o worry about stack overflow
	public void inspectRecursive(Object toInspect, boolean isRecursive, int[] alreadyInspected) {
		if (isInspected(toInspect.hashCode(), alreadyInspected))
			return;
		else {
			int[] appendInspected = addHashCode(toInspect.hashCode(),alreadyInspected);

			String tabOver = calculateTabOver(alreadyInspected.length);

			Class classObj = toInspect.getClass();
			
			// The name of the declaring class
			String declaringClass = classObj.getName();
			System.out.println(tabOver + "Declaring Class:\t" + declaringClass);
			
			// The name of the immediate superclass
			String superClass = null;
			if (classObj.getSuperclass() != null)
				superClass = classObj.getSuperclass().getName();
			System.out.println(tabOver + "Immediate Superclass:\t" + superClass);
			
			// the name of the interfaces the class implements
			System.out.println(tabOver+"Implemented Interfaces of Class");
			System.out.println(tabOver+"======================================================");
			Class[] superInterfaces = classObj.getInterfaces();
			for (Class inter : superInterfaces) {
				System.out.println(tabOver + "Implemented Interface: " + inter.getName());
			}

			// The method of the class declared
			inspectMethods(toInspect,tabOver);
			inspectConstructors(toInspect,tabOver);
			inspectFields(toInspect, classObj, isRecursive, appendInspected);
		}

	}

	//Method to specifically inspect fields and print results to console
	public void inspectFields(Object toInspect, Class classObj, boolean isRecursive, int[] alreadyInspected) {
			
		if (isInspected(toInspect.hashCode(), alreadyInspected))
			return;
		else 
		{
			int[] appendInspected = addHashCode(toInspect.hashCode(), alreadyInspected);

			String tabOver = calculateTabOver(alreadyInspected.length);
			
			//The fields the class declares
			System.out.println();
			System.out.println();
			System.out.println(tabOver+"Declared Fields of Class")	;
			Field[] f = classObj.getDeclaredFields();		
			try 
			{
				for (int i = 0; i<f.length; i++)
				{
					//Type
					//Modifiers
					f[i].setAccessible(true);
					Object val = null;
					System.out.println(tabOver+"=============================================");
					System.out.println(tabOver+"Field Name: \t"+f[i].getName());
					System.out.println(tabOver+"Field Type: \t"+f[i].getType().getName());
					if (f[i].getType().isPrimitive())
					{	
							val = getPrimitiveValue(f[i].getType().getName(),f[i].get(toInspect));
						
					}
					else if (f[i].getType().isArray())
					{	
						//Must be able to handle arrays (names, component Type, length, all contents)
					   f[i].setAccessible(true);
					   Class cType = f[i].getType().getComponentType();
					   Object array = f[i].get(toInspect);
					   System.out.println(tabOver+f[i].getName());
					   System.out.println(tabOver+"\tArray Length: "+Array.getLength(array));
					   System.out.println(tabOver+"\tArray Component Type: "+ cType.getName());
					   System.out.println(tabOver+"\tArray Contents:");
					   
					   for (int index = 0; index < Array.getLength(array); index++)
					   {
							
						   System.out.println(tabOver+"\t===================================================");
						   Object obj = Array.get(array, index);
						   if (obj == null)
						   {
							   System.out.println(tabOver+"\tIndex: " +index + " Val: "+obj);
			    		   }
						   else if (isRecursive)
						   {
							   System.out.println(tabOver+"\tIndex: " +index + " Value: ");
				    		   //Add Inspected Hashcode for Tabbing purposes
							   int[] accountForArray = addHashCode(-1, appendInspected);
							   inspectRecursive(obj, isRecursive, accountForArray);
						   }
						   else
						   {
							   System.out.println(tabOver+"\tIndex:"+index+" Value: "+obj.hashCode());
						   }
					   }
					}
					//If field contains object:
					else
					{
						
						//if recursive => Inspect nested object
						if (isRecursive)
						{	
							if (f[i].get(toInspect) != null)
								inspectRecursive(f[i].get(toInspect), isRecursive, appendInspected);
							else 
								val = null;
							val = "Inspected Inner Object";
						}
					    //If not => print hash code.
						else
						{
							if (f[i].get(toInspect) != null)
								val = f[i].get(toInspect).hashCode();
						}
					
					}
					
				if (val != null)
					System.out.println(tabOver+"Field Value: "+val.toString());
				else 
					System.out.println(tabOver+"Field Value is null.");
				}
			}
			 catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
			}
		}
	}
	
	//Get the Base Non-Recursive Value of an object regardless what it is. 
	public Object getNonRecursiveValue(Object toGetVal) {
		Object val = null;
		Class classObj = toGetVal.getClass();
		if (classObj.isPrimitive()) {
			try {
				val = getPrimitiveValue(classObj.getName(), toGetVal);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}
		} else if (classObj.isArray()) {
			// Must be able to handle arrays (names, component Type, length, all contents)
			try {
				System.out.println("\tArray Name: " + toGetVal.getClass().getName());
				System.out.println("\tArray Length: " + Array.getLength(toGetVal));
				System.out.println("\tArray Component Type: " + Array.class.getComponentType().getName());
				System.out.println("\tArray Contents:");
				for (int index = 0; index < Array.getLength(toGetVal); index++) {
					Object obj = Array.get(toGetVal, index);
					if (obj != null)
						val = getNonRecursiveValue(obj);
					System.out.println("\tArray Index: " + index + " Value: " + val);
				}
				val = "Array Inspected";
			} catch (IllegalArgumentException e) {
				{
					System.out.println("Illegal Access Exception for Object" + toGetVal.getClass().getName());
					val = "Unattained";
				}
			}
		} else {
			// If not => print hash code.
			val = toGetVal.hashCode();
		}

		return val;
	}

	//Get value of a Primitive Object from Generic Object Type
	public Object getPrimitiveValue(String typeName, Object toGetVal)
	{
		Object val = "No Value";
		switch (typeName) {
		case "Double":
			val = ((Double) toGetVal).doubleValue();
			break;
		case "double":
			val = (double)toGetVal;
			break;
		case "Integer":
			val = ((Integer) toGetVal).intValue();
			break;
		case "int":
			val = (int)toGetVal;
			break;
		case "void":
		case "Void":
			val = ((Void) toGetVal);
			break;
		case "Float":
			val = ((Float) toGetVal).floatValue();
			break;
		case "float":
			val = (float)toGetVal;
			break;
		case "Boolean":
			val = ((Boolean) toGetVal).booleanValue();
			break;
		case "boolean":
			val = (boolean)toGetVal;
			break;
		case "Character":
			val = ((Character) toGetVal).charValue();
			break;
		case "char":
			val = (char)toGetVal;
			break;
		case "Long":
			val = ((Long) toGetVal).longValue();
			break;
		case "long":
			val = (long)toGetVal;
			break;
		case "Short":
			val = ((Short) toGetVal).shortValue();
			break;
		case "short":
			val = (short)toGetVal;
			break;
		case "Byte":
			val = ((Byte) toGetVal).byteValue();
			break;
		case "byte":
			val = (byte)toGetVal;
			break;
		default:
			val = "Unattained";
			break;
		}
		return val;
	}

	//Add hashcode to array of already inspected objects
	private int[] addHashCode(int hashCode, int[] alreadyInspected)
	{
		int[] appendInspected = new int[alreadyInspected.length+1];
		for(int i = 0; i< alreadyInspected.length;i++)
		{
			appendInspected[i] = alreadyInspected[i];
		}
		appendInspected[alreadyInspected.length] = hashCode;
		
		return appendInspected;
	}
	
	private String calculateTabOver(int repeatNum)
	{
		String tabOver = "";
		for (int tabNum = 0; tabNum < repeatNum; tabNum++) {
			tabOver += "\t";
		}
		return tabOver;
	}
	
	//Check If the method has been already inspected as to not inspect it again
	private boolean isInspected(int hashCode, int[] alreadyInspected) {
		for (int hash : alreadyInspected) {
			if (hash == hashCode)
				return true;
		}
		return false;
	}

}
