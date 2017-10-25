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
		// The name of the immediate superclass
		if (classObj.getSuperclass() != null) {
			System.out.println("Immediate Superclass:\t" + classObj.getSuperclass().getName());
			System.out.println("Inspecting Superclass\n===================================================");
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
			for (Constructor c : superClass.getDeclaredConstructors()) {
				// Param types
				for (Class param : c.getParameterTypes()) {
					System.out.println("Parameter Types: " + param.getName());
				}

				// modifiers
				System.out.println("Modifier (int): \t" + c.getModifiers());
				System.out.println("Modifier (string): \t" + Modifier.toString(c.getModifiers()));
			}
			for (Field f : superClass.getDeclaredFields()) {
				System.out.println("Field Name:\t" + f.getName());
				f.setAccessible(true);
				try {
					System.out.println("Field Value:\t" + getNonRecursiveValue(f.get(toInspect)));
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}

			System.out
					.println("======================================================\nFinished Inspecting SuperClass");
		}
		System.out.println();
		System.out.println();
		// the name of the interfaces the class implements
		Class[] superInterfaces = classObj.getInterfaces();
		for (Class inter : superInterfaces) {
			System.out.println("Implemented Interface: " + inter.getName());
			System.out.println("Inspecting Interface\n===================================================");
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
			for (Constructor c : inter.getDeclaredConstructors()) {
				// Param types
				for (Class param : c.getParameterTypes()) {
					System.out.println("Parameter Types: " + param.getName());
				}

				// modifiers
				System.out.println("Modifier (int): \t" + c.getModifiers());
				System.out.println("Modifier (string): \t" + Modifier.toString(c.getModifiers()));
			}
			for (Field f : inter.getDeclaredFields()) {
				System.out.println("Field Name:\t" + f.getName());
				try {
					System.out.println("Field Value:\t" + getNonRecursiveValue(f.get(inter)));
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
			System.out.println("======================================================\nFinished Inspecting Interface");
		}

		// The method of the class declared
		Method[] declaredMethods = classObj.getDeclaredMethods();

		for (Method m : declaredMethods) {
			// Find each:
			System.out.println("Method Name: \t" + m.getName());
			// Exceptions thrown
			Class[] exceptions = m.getExceptionTypes();
			for (Class e : exceptions) {
				System.out.println("Exception Name: \t" + e.getName());
			}
			// Parameter types
			Class[] parameters = m.getParameterTypes();
			for (Class param : parameters) {
				System.out.println("Parameter Name: \t" + param.getName());
			}
			// return types
			System.out.println("Return Type: \t" + m.getReturnType().getName());
			// modifiers
			System.out.println("Modifier (int): \t" + m.getModifiers());
			System.out.println("Modifier (string): \t" + Modifier.toString(m.getModifiers()));
		}

		Constructor[] constructors = classObj.getConstructors();
		// Get all Constructors
		for (Constructor c : constructors) {
			// Param types
			for (Class param : c.getParameterTypes()) {
				System.out.println("Parameter Types: " + param.getName());
			}

			// modifiers
			System.out.println("Modifier (int): \t" + c.getModifiers());
			System.out.println("Modifier (string): \t" + Modifier.toString(c.getModifiers()));
		}

		InspectFields(toInspect, classObj, isRecursive, new int[] {});
		// Must be able to handle arrays (names, component Type, length, all contents)

		// Carry over hashcodes recursively
	}

	private void InspectFields(Object toInspect, Class classObj, boolean isRecursive, int[] alreadyInspected) {
			
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
				Field[] f = classObj.getDeclaredFields();		

				for (int i = 0; i<f.length; i++)
				{
					//Type
					//Modifiers
					f[i].setAccessible(true);
					Object val = null;
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
								   Object obj = Array.get(array, index);
								   if (obj == null)
								   {
									   System.out.println("\t\tIndex: " +index + " Val: "+obj);
								   }
								   else if (isRecursive)
								   {
									   inspectRecursive(obj, isRecursive, appendInspected);
								   }
								   else
								   {
									   System.out.println("\t\t Index:"+index+" "+obj.hashCode());
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
						System.out.println("Field:"+ f[i].getName() +"\t Value: "+val.toString());
				}
			}

	}

	private Object getNonRecursiveValue(Object toGetVal) {
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
					System.out.println("Array Index: " + index + " Value: " + val);
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
	private void inspectRecursive(Object toInspect, boolean isRecursive, int[] alreadyInspected) {
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
			Class[] superInterfaces = classObj.getInterfaces();
			for (Class inter : superInterfaces) {
				System.out.println(tabOver + "Implemented Interface: " + inter.getName());
			}

			// The method of the class declared
			Method[] declaredMethods = classObj.getDeclaredMethods();

			for (Method m : declaredMethods) {
				// Find each:
				System.out.println(tabOver + "Method Name: \t" + m.getName());
				// Exceptions thrown
				Class[] exceptions = m.getExceptionTypes();
				for (Class e : exceptions) {
					System.out.println(tabOver + "Exception Name: \t" + e.getName());
				}
				// Parameter types
				Class[] parameters = m.getParameterTypes();
				for (Class param : parameters) {
					System.out.println(tabOver + "Parameter Name: \t" + param.getName());
				}
				// return types
				System.out.println(tabOver + "Return Type: \t" + m.getReturnType().getName());
				// modifiers
				System.out.println(tabOver + "Modifier (int): \t" + m.getModifiers());
				System.out.println(tabOver + "Modifier (string): \t" + Modifier.toString(m.getModifiers()));
			}

			Constructor[] constructors = classObj.getConstructors();
			// Get all Constructors
			for (Constructor c : constructors) {
				// Param types
				for (Class param : c.getParameterTypes()) {
					System.out.println(tabOver + "Parameter Types: " + param.getName());
				}

				// modifiers
				System.out.println(tabOver + "Modifier (int): \t" + c.getModifiers());
				System.out.println(tabOver + "Modifier (string): \t" + Modifier.toString(c.getModifiers()));

			}
			InspectFields(toInspect, classObj, isRecursive, appendInspected);
		}

	}

	private boolean isInspected(int hashCode, int[] alreadyInspected) {
		for (int hash : alreadyInspected) {
			if (hash == hashCode)
				return true;
		}
		return false;
	}

}
