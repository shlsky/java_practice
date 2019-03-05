package com.java;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author hongling.shl
 * @date 2019/3/5
 */
public class LinkedHashMapTest {
	
	public static void main(String[] args) {
		LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<>(4, 0.75f, true);
		linkedHashMap.put("1", "1");
		linkedHashMap.put("2", "2");
		linkedHashMap.put("3", "3");
		linkedHashMap.put("4", "4");
		printList(linkedHashMap);
		linkedHashMap.get("1");
		printList(linkedHashMap);
		linkedHashMap.put("2", "2");
		printList(linkedHashMap);
		
		linkedHashMap.put("5", "5");
		linkedHashMap.put("6", "5");
		linkedHashMap.put("7", "5");
		linkedHashMap.put("8", "5");
		printList(linkedHashMap);
		
	}
	
	private static void printList(LinkedHashMap<String, String> linkedHashMap) {
		
		Set<Map.Entry<String, String>> set = linkedHashMap.entrySet();
		Iterator<Map.Entry<String, String>> iterator = set.iterator();
		
		while (iterator.hasNext()) {
			System.out.print(iterator.next() + "\t");
		}
		System.out.println();
	}
	
	public static void main1(String[] args) {
		LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<String, String>(16, 0.75f, true);
		linkedHashMap.put("111", "111");
		linkedHashMap.put("222", "222");
		linkedHashMap.put("333", "333");
		linkedHashMap.put("444", "444");
		loopLinkedHashMap(linkedHashMap);
		linkedHashMap.get("111");
		loopLinkedHashMap(linkedHashMap);
		linkedHashMap.put("222", "2222");
		printList(linkedHashMap);
	}
	
	public static void loopLinkedHashMap(LinkedHashMap<String, String> linkedHashMap) {
		Set<Map.Entry<String, String>> set = linkedHashMap.entrySet();
		Iterator<Map.Entry<String, String>> iterator = set.iterator();
		
		while (iterator.hasNext()) {
			System.out.print(iterator.next() + "\t");
		}
		System.out.println();
	}
}
