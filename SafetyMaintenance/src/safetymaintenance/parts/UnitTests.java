package safetymaintenance.parts;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Set;

import org.junit.Test;

public class UnitTests {

	@Test
	public void GraphClassUnitTest() {
		try {
			GraphClass graph = new GraphClass();
			Set<String> cc = graph.getCriticalClasses();	

			assertEquals(cc.isEmpty(), false);
		}  catch (Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void GraphClassUnitTest2() {
		try {
			GraphClass graph = new GraphClass();
			ArrayList<String> design = graph.getDesign("R20");
			ArrayList<String> assumption = graph.getAssumptions("R20");
			ArrayList<String> fmeca = graph.getFMECA("R20");
			
			assertEquals(design.toString(), "[D1]");
			assertEquals(fmeca.toString(), "[F2, F3, F5, F6]");
			assertEquals(assumption.toString(), "[A4, A5, A6, A7, A8, A9]");

		}  catch (Exception e){
			e.printStackTrace();
		}
	}
}
