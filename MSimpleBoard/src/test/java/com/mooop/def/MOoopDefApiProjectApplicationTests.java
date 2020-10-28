package com.mooop.def;

import java.util.Scanner;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

public class MOoopDefApiProjectApplicationTests {

	@Test
	public void tst1() {
		
		System.out.println("Input Number ::");
		Scanner scanner = new Scanner(System.in);
		int num = scanner.nextInt();
		int dividenum = num/10;
		System.out.println("dividenum : "+dividenum);
	}

}
