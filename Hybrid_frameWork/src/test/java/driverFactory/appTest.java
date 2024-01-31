package driverFactory;

import org.testng.annotations.Test;

public class appTest {
@Test
public void kickstart() throws Throwable {
	driverScript ds=new driverScript();
	ds.startTest();
}
}
