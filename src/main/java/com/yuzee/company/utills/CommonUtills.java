package com.yuzee.company.utills;

import java.util.Arrays;

public class CommonUtills {
	
	private CommonUtills () {}
	
	public static String getEnumNames(Class<? extends Enum<?>> e) {
		return Arrays.toString(Arrays.stream(e.getEnumConstants()).map(Enum::name).toArray(String[]::new));
	}

}
