package CodedInspector;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Objects;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;

class TestObjectInspector {
	
	@Test
	public void testInspectSuperClass() throws Exception {
		
		//Arrange
		//Byte Array output MUST BE started out here to work. Has been tested when placed in @Before and @After
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	    System.setOut(new PrintStream(outContent));
		ObjectInspector inspector = new ObjectInspector();
		TestObject2 test = new TestObject2();
		test.field1 = 0;
		test.field2 = "Blah";
		test.field4 = 2;
		test.field5 = "test";

		//Recreate Expected Console Print out
		String expected = "Immediate Superclass:\tCodedInspector.TestObjectInspector$TestObject"+System.getProperty("line.separator")
		+ "Inspecting Superclass"+System.getProperty("line.separator")
		+ "==================================================="+System.getProperty("line.separator")
		+"Declared Constructor Amount: 1"+System.getProperty("line.separator")
		+ "Parameter Types: CodedInspector.TestObjectInspector"+System.getProperty("line.separator")
		+ "Constructor Modifier (int): 	1"+System.getProperty("line.separator")
		+ "Constructor Modifier (string): 	public"
		+System.getProperty("line.separator")
		+System.getProperty("line.separator")
		+System.getProperty("line.separator")
		+System.getProperty("line.separator")
		+System.getProperty("line.separator")
		+System.getProperty("line.separator")
		+System.getProperty("line.separator")
		+ "Declared Fields Amount: 3"+System.getProperty("line.separator")
		+ "Field Name:	field1"+System.getProperty("line.separator")
		+ "Field Value:\t"+test.field1+""+System.getProperty("line.separator")
		+ "Field Name:	field2"+System.getProperty("line.separator")
		+ "Field Value:\t"+test.field2.hashCode()+System.getProperty("line.separator")
		+ "Field Name:	this$0"+System.getProperty("line.separator")
		+ "Field Value:\t"+this.hashCode()+System.getProperty("line.separator")
		+ "====================================================="+System.getProperty("line.separator")
		+"Exploring SuperClass of Super Class CodedInspector.TestObjectInspector$TestObject"+System.getProperty("line.separator")
		+"Immediate Superclass:	java.lang.Object"+System.getProperty("line.separator")
		+"Inspecting Superclass"+System.getProperty("line.separator")
		+"==================================================="+System.getProperty("line.separator")
		+"Method Name: 	finalize"+System.getProperty("line.separator")
		+"Method Name: 	wait"+System.getProperty("line.separator")
		+"Method Name: 	wait"+System.getProperty("line.separator")
		+"Method Name: 	wait"+System.getProperty("line.separator")
		+"Method Name: 	equals"+System.getProperty("line.separator")
		+"Method Name: 	toString"+System.getProperty("line.separator")
		+"Method Name: 	hashCode"+System.getProperty("line.separator")
		+"Method Name: 	getClass"+System.getProperty("line.separator")
		+"Method Name: 	clone"+System.getProperty("line.separator")
		+"Method Name: 	notify"+System.getProperty("line.separator")
		+"Method Name: 	notifyAll"+System.getProperty("line.separator")
		+"Method Name: 	registerNatives"+System.getProperty("line.separator")
		+"Declared Constructor Amount: 1"+System.getProperty("line.separator")
		+"Constructor Modifier (int): 	1"+System.getProperty("line.separator")
		+"Constructor Modifier (string): 	public"+System.getProperty("line.separator")
		+System.getProperty("line.separator")
		+System.getProperty("line.separator")
		+System.getProperty("line.separator")
		+System.getProperty("line.separator")
		+System.getProperty("line.separator")
		+System.getProperty("line.separator")
		+"Declared Fields Amount: 0"+System.getProperty("line.separator")
		+"This superclass extends no superclass"+System.getProperty("line.separator")
		+"======================================================"+System.getProperty("line.separator")
		+"Finished Inspecting SuperClass"+System.getProperty("line.separator")
		+"End Exploring Super Class of Super Class CodedInspector.TestObjectInspector$TestObject"+System.getProperty("line.separator")
		+"====================================================="+System.getProperty("line.separator")
		+"======================================================"+System.getProperty("line.separator")
		+"Finished Inspecting SuperClass"+System.getProperty("line.separator");
		
		//Act
		inspector.inspectSuperClass(test);		
		
		//Assert
		assertEquals(expected, outContent.toString());
	}
	
	@Test
	public void testInspectInterface() throws Exception {
		//Arrange
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	    System.setOut(new PrintStream(outContent));
		ObjectInspector inspector = new ObjectInspector();

		TestObject1 test =  new TestObject1();
		inspector.inspectSuperInterface(test);
	
		//Recreate Expected Console Print out
		String expected = "Implemented Interface: CodedInspector.TestObjectInspector$TestInterface"+System.getProperty("line.separator")
				+"Inspecting Interface"+System.getProperty("line.separator")
				+"==================================================="+System.getProperty("line.separator")
				+"Method Name: 	blah"+System.getProperty("line.separator")
				+"Declared Constructors Amount: 0"+System.getProperty("line.separator")
				+"============================================================================="+System.getProperty("line.separator")
				+System.getProperty("line.separator")
				+System.getProperty("line.separator")
				+"Declared Fields Amount: 0"+System.getProperty("line.separator")
				+"============================================================================="+System.getProperty("line.separator")
				+"======================================================"+System.getProperty("line.separator")
				+"Finished Inspecting Interface"+System.getProperty("line.separator")
				+"This superinterface implements no other superinterfaces"+System.getProperty("line.separator");
		
		assertEquals(expected, outContent.toString());
	}
	
	@Test
	public void testInspectFields()
	{
		//Arrange
				ByteArrayOutputStream outContent = new ByteArrayOutputStream();
			    System.setOut(new PrintStream(outContent));
				ObjectInspector inspector = new ObjectInspector();
				TestObject test =  new TestObject();
				test.field1 = 0;
				test.field2 = "test";
				inspector.InspectFields(test, test.getClass(), false, new int [] {});

				//Recreate Expected Console Print out
				String expected = System.getProperty("line.separator")+System.getProperty("line.separator")+"Declared Fields of Class"+System.getProperty("line.separator")
				+"============================================="+System.getProperty("line.separator")
				+"Field Name: 	field1"+System.getProperty("line.separator")
				+"Field Type: 	int"+System.getProperty("line.separator")
				+"Field Value: "+test.field1+System.getProperty("line.separator")
				+"============================================="+System.getProperty("line.separator")
				+"Field Name: 	field2"+System.getProperty("line.separator")
				+"Field Type: 	java.lang.String"+System.getProperty("line.separator")
				+"Field Value: "+test.field2.hashCode()+System.getProperty("line.separator")
				+"============================================="+System.getProperty("line.separator")
				+"Field Name: 	this$0"+System.getProperty("line.separator")
				+"Field Type: 	CodedInspector.TestObjectInspector"+System.getProperty("line.separator")
				+"Field Value: "+this.hashCode()+System.getProperty("line.separator");
				
				assertEquals(expected, outContent.toString());
	}

	@Test
	public void testInspectFieldsNull() 
	{
			//Arrange
			ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		    System.setOut(new PrintStream(outContent));
			ObjectInspector inspector = new ObjectInspector();

			TestObject test =  new TestObject();
			inspector.InspectFields(test, test.getClass(), false, new int [] {});

			//Recreate Expected Console Print out
			String expected = System.getProperty("line.separator")+System.getProperty("line.separator")+"Declared Fields of Class"+System.getProperty("line.separator")
			+"============================================="+System.getProperty("line.separator")
			+"Field Name: 	field1"+System.getProperty("line.separator")
			+"Field Type: 	int"+System.getProperty("line.separator")
			+"Field Value: "+test.field1+System.getProperty("line.separator")
			+"============================================="+System.getProperty("line.separator")
			+"Field Name: 	field2"+System.getProperty("line.separator")
			+"Field Type: 	java.lang.String"+System.getProperty("line.separator")
			+"Field Value is null."+System.getProperty("line.separator")
			+"============================================="+System.getProperty("line.separator")
			+"Field Name: 	this$0"+System.getProperty("line.separator")
			+"Field Type: 	CodedInspector.TestObjectInspector"+System.getProperty("line.separator")
			+"Field Value: "+this.hashCode()+System.getProperty("line.separator");
			
			assertEquals(expected, outContent.toString());
	}
	
	@Test
	public void testInspectFieldsArray()
	{
		//Arrange
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	    System.setOut(new PrintStream(outContent));
		ObjectInspector inspector = new ObjectInspector();

		TestObject3 test =  new TestObject3();
		test.field9 = new int[] {1,2,3};
		inspector.InspectFields(test, test.getClass(), false, new int [] {});

		//Recreate Expected Console Print out
		String expected = System.getProperty("line.separator")+System.getProperty("line.separator")
		+"Declared Fields of Class"+System.getProperty("line.separator")
		+"============================================="+System.getProperty("line.separator")
		+"Field Name: 	field9"+System.getProperty("line.separator")
		+"Field Type: 	[I"+System.getProperty("line.separator")
		+"field9"+System.getProperty("line.separator")
		+"\tArray Length: 3"+System.getProperty("line.separator")
		+"\tArray Component Type: int"+System.getProperty("line.separator")
		+"\tArray Contents:"+System.getProperty("line.separator")
		+"\t==================================================="+System.getProperty("line.separator")
		+"\tIndex:0 Value: 1"+System.getProperty("line.separator")
		+"\t==================================================="+System.getProperty("line.separator")
		+"\tIndex:1 Value: 2"+System.getProperty("line.separator")
		+"\t==================================================="+System.getProperty("line.separator")
		+"\tIndex:2 Value: 3"+System.getProperty("line.separator")
		+"Field Value is null."+System.getProperty("line.separator")
		+"============================================="+System.getProperty("line.separator")
		+"Field Name: 	this$0"+System.getProperty("line.separator")
		+"Field Type: 	CodedInspector.TestObjectInspector"+System.getProperty("line.separator")
		+"Field Value: "+this.hashCode()+System.getProperty("line.separator");
		assertEquals(expected, outContent.toString());
	}

	@Test
	public void testGetPrimitiveValue()
	{
		ObjectInspector inspector = new ObjectInspector();
		String typeName = "int";
		Object testVal = 0;
		int expected = 0;
		Object actual = inspector.getPrimitiveValue(typeName, testVal);
		assertEquals(expected,actual);
	}
	
	@Test
	public void testGetPrimitiveValueViaWrapper()
	{
		ObjectInspector inspector = new ObjectInspector();
		String typeName = "int";
		Integer testVal = new Integer(1);
		int expected = 1;
		Object actual = inspector.getPrimitiveValue(typeName, testVal);
		assertEquals(expected,actual);
	}
	
	
	////////////////////Test Objects/////////////////////////////
	
	public class TestObject{
		public int field1;
		public String field2;
		public TestObject() {
			
		}
	}
	
	
	public class TestObject1 implements TestInterface{
		public int field0;
		public String field8;
		
		@Override
		public void blah() {
		}
	}
	
	
	public class TestObject2 extends TestObject{
		public int field4;
		public String field5;
		
		public TestObject2() {
			
		}
		
		public TestObject2(int field, String field2)
		{
			field4 = field;
			field2 = field5;
		}
		
		public int parentHashCode()
		{
			return super.hashCode();
		}
	}
	public class TestObject3{
		public int[] field9;
		public TestObject3() {
		}
	}
	
	
	public interface TestInterface{
		public abstract void blah();
		
	}
	
	
}





