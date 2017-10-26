import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class ObjectInspector {

	public void inspect(Object toInspect, boolean isRecursive) {

		Class classObj = toInspect.getClass();

		// The name of the declaring class
		String declaringClass = classObj.getName();
		System.out.println("Declaring Class:\t" + declaringClass);

		inspectSuperClass(toInspect);
		
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		inspectSuperInterface(toInspect);
				System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		
		// The method of the class declared
		Method[] declaredMethods = classObj.getDeclaredMethods();

		System.out.println("Declared Methods of Class");
		System.out.println("======================================================");
		for (Method m : declaredMethods) {
			System.out.println();
			System.out.println();
			System.out.println("======================================================");
			// Find each:
			System.out.println("Method Name: \t" + m.getName());
			// Exceptions thrown
			System.out.println("Method Exceptions");
			Class[] exceptions = m.getExceptionTypes();
			for (Class e : exceptions) {
				System.out.println("Exception Name: \t" + e.getName());
			}
			// Parameter types
			System.out.println("Method Parameters");
			Class[] parameters = m.getParameterTypes();
			for (Class param : parameters) {
				System.out.println("Parameter Name: \t" + param.getName());
			}
			// return types
			System.out.println("Method Return Type: \t" + m.getReturnType().getName());
			// modifiers
			System.out.println("Method Modifier (int): \t" + m.getModifiers());
			System.out.println("Method Modifier (string): \t" + Modifier.toString(m.getModifiers()));
		}

		Constructor[] constructors = classObj.getDeclaredConstructors();
		// Get all Constructors
		System.out.println();
		System.out.println();
		System.out.println("Declared Constructors Amount: "+constructors.length);
		for (Constructor c : constructors) {
			System.out.println("Constructor");
			System.out.println("==============================================================================");
			// Param types
			for (Class param : c.getParameterTypes()) {
				System.out.println("Parameter Types: " + param.getName());
			}

			// modifiers
			System.out.println("Constructor Modifier (int): \t" + c.getModifiers());
			System.out.println("Constructor Modifier (string): \t" + Modifier.toString(c.getModifiers()));
			System.out.println();
			System.out.println();
		}

		InspectFields(toInspect, classObj, isRecursive, new int[] {});
		// Must be able to handle arrays (names, component Type, length, all contents)

		// Carry over hashcodes recursively
	}
	

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

						/*
						 * //Exceptions thrown Class[] exceptions = m.getExceptionTypes(); for(Class
						 * e:exceptions) { System.out.println("Exception Name: \t"+e.getName()); }
						 * //Parameter types Class[] parameters = m.getParameterTypes(); for(Class
						 * param:parameters) { System.out.println("Parameter Name: \t"+param.getName());
						 * } //return types
						 * System.out.println("Return Type: \t"+m.getReturnType().getName());
						 * //modifiers System.out.println("Modifier (int): \t"+m.getModifiers());
						 * System.out.println("Modifier (string): \t"+Modifier.toString(m.getModifiers()
						 * ));
						 */
					}
					
					System.out.println("Declared Constructors Amount: "+inter.getDeclaredConstructors().length);
					System.out.println("=============================================================================");
					for (Constructor c : inter.getDeclaredConstructors()) {
						// Param types
						for (Class param : c.getParameterTypes()) {
							System.out.println("Parameter Types: " + param.getName());
						}

						// modifiers
						System.out.println("Constructor Modifier (int): \t" + c.getModifiers());
						System.out.println("Constructor Modifier (string): \t" + Modifier.toString(c.getModifiers()));
						System.out.println();
						System.out.println();
					}
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
				// Find each:
				System.out.println("Method Name: \t" + m.getName());

				/*
				 * //Exceptions thrown Class[] exceptions = m.getExceptionTypes(); for(Class
				 * e:exceptions) { System.out.println("Exception Name: \t"+e.getName()); }
				 * //Parameter types Class[] parameters = m.getParameterTypes(); for(Class
				 * param:parameters) { System.out.println("Parameter Name: \t"+param.getName());
				 * } //return types
				 * System.out.println("Return Type: \t"+m.getReturnType().getName());
				 * //modifiers System.out.println("Modifier (int): \t"+m.getModifiers());
				 * System.out.println("Modifier (string): \t"+Modifier.toString(m.getModifiers()
				 * ));
				 */
			}
			System.out.println("Declared Constructor Amount: "+superClass.getDeclaredConstructors().length);;
			for (Constructor c : superClass.getDeclaredConstructors()) {
				// Param types
				for (Class param : c.getParameterTypes()) {
					System.out.println("Parameter Types: " + param.getName());
				}

				// modifiers
				System.out.println("Constructor Modifier (int): \t" + c.getModifiers());
				System.out.println("Constructor Modifier (string): \t" + Modifier.toString(c.getModifiers()));
				System.out.println();
				System.out.println();
			}
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

	public void InspectFields(Object toInspect, Class classObj, boolean isRecursive, int[] alreadyInspected) {
			
		if (isInspected(toInspect.hashCode(), alreadyInspected))
			return;
		else 
		{
			int[] appendInspected = new int[alreadyInspected.length+1];
			for(int i = 0; i< alreadyInspected.length;i++)
			{
				appendInspected[i] = alreadyInspected[i];
			}
			appendInspected[alreadyInspected.length] = toInspect.hashCode();
			
			String tabOver ="";
			for(int tabNum = 0;tabNum < alreadyInspected.length; tabNum++)
			{
				tabOver += "\t";
			}

				//The fields the class declares

			System.out.println();
			System.out.println();
System.out.println("Declared Fields of Class")	;
				Field[] f = classObj.getDeclaredFields();		

				for (int i = 0; i<f.length; i++)
				{
					//Type
					//Modifiers
					f[i].setAccessible(true);
					Object val = null;
					System.out.println("=============================================");
					System.out.println("Field Name: \t"+f[i].getName());
					System.out.println("Field Type: \t"+f[i].getType().getName());
					if (f[i].getType().isPrimitive())
					{	
						try 
						{

							switch(f[i].getType().getName())
							{
								case "Double":
									val = f[i].getDouble(toInspect);
									break;
								case "double":
									val = f[i].get(toInspect);
									break;
								case "Integer":
									val = f[i].getInt(toInspect);
									break;
								case "int":
									val = f[i].get(toInspect);
									break;
							    case "Void":
									val = ((Void)toInspect).toString();
									break;
								case "void":
									val = f[i].get(toInspect);
									break;
								case "Float":
									val = f[i].getFloat(toInspect);
									break;
								case "float":
									val = f[i].get(toInspect);
									break;
								case "Boolean":
									val = f[i].getBoolean(toInspect);
									break;
								case "boolean":
									val = f[i].get(toInspect);
									break;
								case "Character":
									val = f[i].getChar(toInspect);
									break;
								case "char":
									val = f[i].get(toInspect);
									break;
								case "Long":
									val = f[i].getLong(toInspect);
									break;
								case "long":
									val = f[i].get(toInspect);
									break;
								case "Short":
									val = f[i].getShort(toInspect);
									break;
								case "short":
									val = f[i].get(toInspect);
									break;
								case "Byte":
									val = f[i].getByte(toInspect);
									break;
								case "byte":
									val = f[i].get(toInspect);
									break;
								default:
									val = "Unattained";
									break;
							}
						}
						 catch (IllegalArgumentException | IllegalAccessException e) {
								e.printStackTrace();
						}
					}
					else if (f[i].getType().isArray())
					{	
						//Must be able to handle arrays (names, component Type, length, all contents)
						try {
							   f[i].setAccessible(true);
							   Class cType = f[i].getType().getComponentType();
							   Object array = f[i].get(toInspect);
							   System.out.println(f[i].getName());
							   System.out.println("\tArray Length: "+Array.getLength(array));
							   System.out.println("\tArray Component Type: "+ cType.getName());
							   System.out.println("\tArray Contents:");
							   
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
						    		      int[] accountForArray = new int[appendInspected.length+1];
									   for(int k = 0; k< appendInspected.length;k++)
									   {
										   accountForArray[k] = appendInspected[k];
									   }
									   accountForArray[appendInspected.length] = -1;
									   inspectRecursive(obj, isRecursive, accountForArray);
								   }
								   else
								   {
									   System.out.println(tabOver+"\tIndex:"+index+" Value: "+obj.hashCode());
								   }
							   }
						}
						 catch (IllegalArgumentException | IllegalAccessException e) {
								{
							System.out.println("Illegal Access Exception for Field: "+f[i].getName());
							val = "Unattained";
						}
						 }}
					else
					{
						//If field contains object:
						if (isRecursive)
							//if recursive => Inspect nested object
						{	
							try {
								if (f[i].get(toInspect) != null)
									inspectRecursive(f[i].get(toInspect), isRecursive, appendInspected);
								else 
									val = null;
							} catch (IllegalArgumentException | IllegalAccessException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							val = "Inspected Inner Object";
						}
						else
						{
							//If not => print hash code.
							try {
								val = f[i].get(toInspect).getClass().hashCode();
							} catch (IllegalArgumentException | IllegalAccessException e) {
								e.printStackTrace();
							}
						}
			
						
					}
					if (val != null)
						System.out.println("Field Value: "+val.toString());
				}
			}

	}

	public Object getNonRecursiveValue(Object toGetVal) {
		Object val = null;
		Class classObj = toGetVal.getClass();
		if (classObj.isPrimitive()) {
			try {
				switch (classObj.getName()) {
				case "Double":
					val = ((Double) toGetVal).doubleValue();
					break;
				case "Integer":
					val = ((Double) toGetVal).doubleValue();
					break;
				case "Void":
					val = ((Double) toGetVal).doubleValue();
					break;
				case "Float":
					val = ((Double) toGetVal).doubleValue();
					break;
				case "Boolean":
					val = ((Double) toGetVal).doubleValue();
					break;
				case "Char":
					val = ((Double) toGetVal).doubleValue();
					break;
				case "Long":
					val = ((Double) toGetVal).doubleValue();
					break;
				case "Short":
					val = ((Double) toGetVal).doubleValue();
					break;
				case "Byte":
					val = ((Double) toGetVal).doubleValue();
					break;
				default:
					val = "Unattained";
					break;
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}
		} else if (classObj.isArray()) {
			// Must be able to handle arrays (names, component Type, length, all contents)
			try {
				Array arrayObj = ((Array) toGetVal);
				System.out.println("\tArray Name: " + toGetVal.getClass().getName());
				System.out.println("\tArray Length: " + Array.getLength(arrayObj));
				System.out.println("\tArray Component Type: " + Array.class.getComponentType().getName());
				System.out.println("\tArray Contents:");
				for (int index = 0; index < Array.getLength(arrayObj); index++) {
					Object obj = Array.get(arrayObj, index);
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

	// Method to inspected recursively w/o worry about stack overflow
	public void inspectRecursive(Object toInspect, boolean isRecursive, int[] alreadyInspected) {
		if (isInspected(toInspect.hashCode(), alreadyInspected))
			return;
		else {
			int[] appendInspected = new int[alreadyInspected.length + 1];
			for (int i = 0; i < alreadyInspected.length; i++) {
				appendInspected[i] = alreadyInspected[i];
			}
			appendInspected[alreadyInspected.length] = toInspect.hashCode();

			String tabOver = "";
			for (int tabNum = 0; tabNum < alreadyInspected.length; tabNum++) {
				tabOver += "\t";
			}

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
			InspectFields(toInspect, classObj, isRecursive, appendInspected);
		}

	}

	public boolean isInspected(int hashCode, int[] alreadyInspected) {
		for (int hash : alreadyInspected) {
			if (hash == hashCode)
				return true;
		}
		return false;
	}

}
