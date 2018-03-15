package com.cinekancha.utils;

import java.util.Collection;

/**
 * Created by aayushsubedi on 3/8/18.
 */

public class ListUtils {
	public static boolean isEmpty(Collection c) {
		return c == null || c.isEmpty();
	}
	
	public static int getSize(Collection c) {
		return c == null ? 0 : c.size();
	}
}
