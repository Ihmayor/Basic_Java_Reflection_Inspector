

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class myInspector 
{
	Object obj = null;
	Class classObj =null;
	
	//write a constructor which accepts an object to be introspected
	public myInspector(Object inObject)
	{
			obj = inObject;
	}
	
	//write method to start introspection 
	public void startIntrospection()
	{
		//identify the class this object belongs to
		classObj = obj.getClass();
	
		//get method belonging to the class
		Method[] m = classObj.getMethods();
		
		//for each method in the array, do the following
		// -> print the method name
		// -> print the class in which the method is declared using getDeclaringClass() method
		// -> get method parameters and print type of each parameter
		// -> print method return type
		for (int i = 0; i < m.length; i++)
		{
			System.out.println("Method Name: "+m[i].getName().toString());
			System.out.println("Declaring Class: "+m[i].getDeclaringClass().getName().toString());
			Class[] params = m[i].getParameterTypes();
			for(int j = 0; j < params.length;j++)
			{
				System.out.println("Parameter: "+params[j].getName().toString());
			}
			System.out.println("Return: "+m[i].getReturnType().getName().toString());
			System.out.print("");
		}
		
		
		try
		{
			
		   //print the values of the field
			
			
			//set fields reflectively
			
			
			//print the changed values
			
			Field f1 = classObj.getField("firstName");
			Field f2 = classObj.getField("lastName");
			
			System.out.println("f1 value:"+f1.get(obj));
			System.out.println("f2 value:"+f2.get(obj));
			
		    f1.set(obj, "Bob");
		    f2.set(obj, "The Builder");

		    System.out.println("f1 value:"+f1.get(obj));
			System.out.println("f2 value:"+f2.get(obj));
			
			
			System.out.println("Modifer Int: "+f1.getModifiers() + " Modifier String: "+Modifier.toString(f1.getModifiers()));
			
			//get field from a class
			Field[] f = classObj.getFields();
			for (int i = 0; i<f.length; i++)
			{
				Object val;
				if (f[i].getType().isPrimitive())
				{	
					switch(f[i].getType().getName())
					{
					case "Double":
						val = f[i].getDouble(obj);
						break;
					case "Integer":
						val = f[i].getInt(obj);
						break;
				    case "Void":
						val = ((Void)obj).toString();
						break;
					case "Float":
						val = f[i].getFloat(obj);
						break;
					case "Boolean":
						val = f[i].getBoolean(obj);
						break;
					case "Char":
						val = f[i].getChar(obj);
						break;
					case "Long":
						val = f[i].getLong(obj);
						break;
					case "Short":
						val = f[i].getShort(obj);
						break;
					case "Byte":
						val = f[i].getByte(obj);
						break;
					default:
						val = "Unattained";
						break;
					}
				}
				else
					val = f[i].get(obj); 
				
				System.out.println("Field:"+ f[i].getName() +"\t Value: "+val);
				
			}
			
		
			
			
			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
		try {
			Constructor[] cons = classObj.getConstructors();
			
			for (Constructor c: cons)
			{
				System.out.println("Constructor: \t"+c.getName());
				System.out.println("Modifier: \t" + Modifier.toString(c.getModifiers()));
				Class[] params = c.getParameterTypes();
				for (Class p: params)
				{
					System.out.println("Param Types: \t"+p.getTypeName() );
				}
			}
		}
		catch(Exception ex)
		{
			

			
			
		}
		
	}

	
	

}
