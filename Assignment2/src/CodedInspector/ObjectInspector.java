package CodedInspector;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

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
		for (Class inter: superInterfaces)
		{
			System.out.println("Implemented Interface: "+inter.getName());
		}
		
		//The method of the class declared 
		Method[] declaredMethods = classObj.getDeclaredMethods();
		
		for (Method m: declaredMethods)
		{
			//Find each:
			System.out.println("Method Name: \t"+m.getName());
			//Exceptions thrown
			Class[] exceptions = m.getExceptionTypes();
			for(Class e:exceptions)
			{
				System.out.println("Method Name: \t"+e.getName());
			}
			//Parameter types
			Class[] parameters = m.getParameterTypes();
			for(Class param:parameters)
			{
				System.out.println("Method Name: \t"+param.getName());
			}
			//return types
			System.out.println("Method Name: \t"+m.getReturnType().getName());
			//modifiers
			System.out.println("Modifier (int): \t"+m.getModifiers());
			System.out.println("Modifier (string): \t"+Modifier.toString(m.getModifiers()));
		}
		
		Constructor[] constructors = classObj.getConstructors(); 
		//Get all Constructors
		for (Constructor c: constructors)
		{
			//Param types
			for (Class param:c.getParameterTypes())
			{
				System.out.println("Parameter Types: "+param.getName());
			}

			//modifiers
			System.out.println("Modifier (int): \t"+c.getModifiers());
			System.out.println("Modifier (string): \t"+Modifier.toString(c.getModifiers()));
			
		}

		//The fields the class declares
		Field[] f = classObj.getDeclaredFields();		

		for (int i = 0; i<f.length; i++)
		{
			//Type
			//Modifiers
		
			Object val;
			System.out.println("Field Type: \t"+f[i].getType().getName());
			if (f[i].getType().isPrimitive())
			{	
				try {
					
				switch(f[i].getType().getName())
				{
					case "Double":
							val = f[i].getDouble(toInspect);
						
						break;
					case "Integer":
						val = f[i].getInt(toInspect);
						break;
				    case "Void":
						val = ((Void)toInspect).toString();
						break;
					case "Float":
						val = f[i].getFloat(toInspect);
						break;
					case "Boolean":
						val = f[i].getBoolean(toInspect);
						break;
					case "Char":
						val = f[i].getChar(toInspect);
						break;
					case "Long":
						val = f[i].getLong(toInspect);
						break;
					case "Short":
						val = f[i].getShort(toInspect);
						break;
					case "Byte":
						val = f[i].getByte(toInspect);
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
					   Array arrayObj = (Array)f[i].get(toInspect);
					   System.out.println(f[i].getName());
					   System.out.println("\tArray Length: "+Array.getLength(arrayObj));
					   System.out.println("\tArray Component Type: "+ Array.class.getComponentType().getName());
					   System.out.println("\tArray Contents:");
					   for (int index = 0; index < Array.getLength(arrayObj); index++)
					   {
						   Object obj = Array.get(arrayObj, index);
						   if (isRecursive)
						   {
							   inspectRecursive(obj, isRecursive, new int[] {toInspect.hashCode()});
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
			}
			else
			{
				//If field contains object:
				if (isRecursive)
					//if recursive => Inspect nested object
					inspectRecursive(toInspect, isRecursive,new int[] {toInspect.hashCode()});
				else
				{
					//If not => print hash code.
					val = f[i].get(toInspect).getClass().hashCode();
				}
	
				
			}
			System.out.println("Field:"+ f[i].getName() +"\t Value: "+val);
			
		}
		
	
			
		//Must be able to handle arrays (names, component Type, length, all contents)
		
		//Carry over hashcodes recursively
		
		
		
		
		
		
	}
	
	//Method to inspected recursively w/o worry about stack overflow
	private void inspectRecursive(Object toInspect, boolean isRecursive, int[] alreadyInspected)
	{
		if (isInspected(toInspect.hashCode(), alreadyInspected))
			return;
		else 
		{
			int[] appendInspected = new int[alreadyInspected.length+1];
			for(int i: alreadyInspected)
			{
				appendInspected[i] = alreadyInspected[i];
			}
			appendInspected[alreadyInspected.length] = toInspect.hashCode();
			
			String tabOver ="";
			for(int tabNum = 0;tabNum < alreadyInspected.length; tabNum++)
			{
				tabOver += "\t";
			}
			
			
			Class classObj = toInspect.getClass();
			//The name of the declaring class
			String declaringClass = classObj.getDeclaringClass().getName();
			System.out.println(tabOver+"Declaring Class:\t"+declaringClass);
			//The name of the immediate superclass
			String superClass = classObj.getSuperclass().getName();
			System.out.println(tabOver+"Immediate Superclass:\t"+declaringClass);
			//the name of the interfaces the class implements 
			Class[] superInterfaces = classObj.getInterfaces();
			for (Class inter: superInterfaces)
			{
				System.out.println(tabOver+"Implemented Interface: "+inter.getName());
			}
			
			//The method of the class declared 
			Method[] declaredMethods = classObj.getDeclaredMethods();
			
			for (Method m: declaredMethods)
			{
				//Find each:
				System.out.println(tabOver+"Method Name: \t"+m.getName());
				//Exceptions thrown
				Class[] exceptions = m.getExceptionTypes();
				for(Class e:exceptions)
				{
					System.out.println(tabOver+"Method Name: \t"+e.getName());
				}
				//Parameter types
				Class[] parameters = m.getParameterTypes();
				for(Class param:parameters)
				{
					System.out.println(tabOver+"Method Name: \t"+param.getName());
				}
				//return types
				System.out.println(tabOver+"Method Name: \t"+m.getReturnType().getName());
				//modifiers
				System.out.println(tabOver+"Modifier (int): \t"+m.getModifiers());
				System.out.println(tabOver+"Modifier (string): \t"+Modifier.toString(m.getModifiers()));
			}
			
			Constructor[] constructors = classObj.getConstructors(); 
			//Get all Constructors
			for (Constructor c: constructors)
			{
				//Param types
				for (Class param:c.getParameterTypes())
				{
					System.out.println(tabOver+"Parameter Types: "+param.getName());
				}

				//modifiers
				System.out.println(tabOver+"Modifier (int): \t"+c.getModifiers());
				System.out.println(tabOver+"Modifier (string): \t"+Modifier.toString(c.getModifiers()));
				
			}

			//The fields the class declares
			Field[] f = classObj.getDeclaredFields();		

			for (int i = 0; i<f.length; i++)
			{
				//Type
				//Modifiers
			
				Object val = null;
				System.out.println(tabOver+"Field Name:\t"+f[i].getName());
				System.out.println(tabOver+"Field Type: \t"+f[i].getType().getName());
				if (f[i].getType().isPrimitive())
				{	
					try {
						switch(f[i].getType().getName())
						{
						case "Double":
							val = f[i].getDouble(toInspect);
							break;
						case "Integer":
							val = f[i].getInt(toInspect);
							break;
					    case "Void":
							val = ((Void)toInspect).toString();
							break;
						case "Float":
							val = f[i].getFloat(toInspect);
							break;
						case "Boolean":
							val = f[i].getBoolean(toInspect);
							break;
						case "Char":
							val = f[i].getChar(toInspect);
							break;
						case "Long":
							val = f[i].getLong(toInspect);
							break;
						case "Short":
							val = f[i].getShort(toInspect);
							break;
						case "Byte":
							val = f[i].getByte(toInspect);
							break;
						default:
							val = "Unattained";
							break;
						}
					}
					catch(IllegalAccessException ex)
					{
						System.out.println("Illegal Access Exception for"+f[i].getName());
						val = "Unattained";
					}
				}
				else if (f[i].getType().isArray())
				{	
					//Must be able to handle arrays (names, component Type, length, all contents)
					try {
						   Array arrayObj = (Array)f[i].get(toInspect);
						   System.out.println(f[i].getName());
						   System.out.println(tabOver+"Array Length: "+Array.getLength(arrayObj));
						   System.out.println(tabOver+"Array Component Type: "+ Array.class.getComponentType().getName());
						   System.out.println(tabOver+"Array Contents:");
						   for (int index = 0; index < Array.getLength(arrayObj); index++)
						   {
							   Object obj = Array.get(arrayObj, index);
							   inspectRecursive(obj, isRecursive, appendInspected);
						   }
					}
					catch(IllegalAccessException ex)
					{
						System.out.println("Illegal Access Exception for Field: "+f[i].getName());
						val = "Unattained";
					}
				}
				else
				{
					try {
						
						inspectRecursive(f[i].get(toInspect),isRecursive,appendInspected);
					}
					catch(IllegalAccessException ex)
					{
						System.out.println("Illegal Access Exception for Object Field"+f[i]);
						val = "Unattained";
					}
					
				}
				System.out.println(tabOver+"Field:"+ f[i].getName() +"\t Value: "+val);
				
			}
			
		}
	}
	
	
	private boolean isInspected(int hashCode, int[] alreadyInspected)
	{
		for (int hash: alreadyInspected)
		{
			if (hash == hashCode)
				return true;
		}
		return false;
	}
	
}
