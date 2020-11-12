package unittests;
import model.*;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class Test1 {

	@Test
	void test() {
		Customer test=new Customer("testA", "TestB", -1);
		
		
		assertFalse(test.validate(500,-1000));
		//assertTrue(test.validate(100));
	}//end of test

}//end of class
