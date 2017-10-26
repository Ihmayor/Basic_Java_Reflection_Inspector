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

	
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

	@Before
	public void setUpStreams() {
	    System.setOut(new PrintStream(outContent));
	    System.setErr(new PrintStream(errContent));
	}

	@After
	public void cleanUpStreams() {
	    System.setOut(null);
	    System.setErr(null);
	    
	    
	}
	@Test
	public void testInspectSuperClass() throws Exception {
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	    System.setOut(new PrintStream(outContent));

		ObjectInspector inspector = new ObjectInspector();
		
		TestObject2 test = new TestObject2();
		test.field1 = 0;
		test.field2 = "Blah";
		test.field4 = 2;
		test.field5 = "test";
		
		
		inspector.inspectSuperClass(test);
		Class classObj = test.getClass();
		Class superClass = classObj.getSuperclass();
		
		//Write Lines.
		String expected = "Immediate Superclass:\tCodedInspector.TestObjectInspector$TestObject\n"
				+ "Inspecting Superclass\n"
				+ "===================================================\n"
				+"Declared Constructor Amount: 1\n"
				+ "Parameter Types: CodedInspector.TestObjectInspector\n"
				+ "Constructor Modifier (int): 	1\n"
				+ "Constructor Modifier (string): 	public\n\n\n\n\n\n\n"
				+ "Declared Fields Amount: 3\n"
				+ "Field Name:	field1\n"
				+ "Field Value:\t"+test.field1+"\n"
				+ "Field Name:	field2\n"
				+ "Field Value:\t"+test.field2.hashCode()+"\n"
				+ "Field Name:	this$0\n"
				+ "Field Value:\t1121647253\n"
				+ "=====================================================\n"
				+"Exploring SuperClass of Super Class CodedInspector.TestObjectInspector$TestObject\n"
				+"Immediate Superclass:	java.lang.Object\n"
				+"Inspecting Superclass\n"
				+"===================================================\n"
				+"Method Name: 	finalize\n"
				+"Method Name: 	wait\n"
				+"Method Name: 	wait\n"
				+"Method Name: 	wait\n"
				+"Method Name: 	equals\n"
				+"Method Name: 	toString\n"
				+"Method Name: 	hashCode\n"
				+"Method Name: 	getClass\n"
				+"Method Name: 	clone\n"
				+"Method Name: 	notify\n"
				+"Method Name: 	notifyAll\n"
				+"Method Name: 	registerNatives\n"
				+"Declared Constructor Amount: 1\n"
				+"Constructor Modifier (int): 	1\n"
				+"Constructor Modifier (string): 	public\n"
				+"\n\n\n\n\n\n"
				+"Declared Fields Amount: 0\n"
				+"This superclass extends no superclass\n"
				+"======================================================\n"
				+"Finished Inspecting SuperClass\n"
				+"End Exploring Super Class of Super Class CodedInspector.TestObjectInspector$TestObject\n"
				+"=====================================================\n"
				+"======================================================\n"
				+"Finished Inspecting SuperClass";

		assertEquals(expected, outContent.toString());
		
	}
	
	
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
	
	public class TestObject3 extends TestObject2{
		public int field6;
		public String field7;
	}
	
	public interface TestInterface{
		public abstract void blah();
		
	}
	
	
}





