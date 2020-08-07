package com.uvaan.sampleprojectapi.utils;

import org.springframework.lang.Nullable;

public class CustomUtils {

	public static boolean isEmpty(@Nullable Object str) {
		return (str == null || "".equals(str.toString().trim()));

	}

	public static boolean hasValidValue(@Nullable Object longObj) {
		return (null == longObj || ((Long)longObj).longValue() <= 0);
	}
}
