package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
class DemoApplicationTests {

	Calc  cal = new Calc();
	@Test
	void contextLoads() {
		int nu1 = 30;
		int num2 = 40;
        int result = cal.add (nu1,num2);
		int expect = 70;
		assertThat(result).isEqualTo(expect);

	}


}
 class Calc {
	 public int add(int i, int num2) {

		 return i+ num2;
	 }
 }
