package com.ysx.admin.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class Snippet {
	public static void main(String[] args) {
		List<Map<String, Object>> humans = new ArrayList<Map<String, Object>>();
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("id", 2);
		Map<String, Object> map1=new HashMap<String, Object>();
		map1.put("id", 3);
		Map<String, Object> map2=new HashMap<String, Object>();
		map2.put("id", 1);
		humans.add(map);
		humans.add(map1);
		humans.add(map2);
//		humans.stream().sorted((Map<String,Object> h1, Map<String,Object> h2) -> Integer.valueOf(h1.get("id").toString()).compareTo(Integer.valueOf(h2.get("id").toString())));
//		Stream.of(humans).forEach(Map param ->     System.out.println(param.get("id")));
		humans.forEach(param -> System.out.println(param.get("id")));
//		for(int i=0;i<humans.size();i++){
//			System.out.println(humans.get(i).get("id"));
//		}
		Collections.sort(humans, new Comparator<Map<String, Object>>() {

			public int compare(Map<String, Object> o1, Map<String, Object> o2) {
				// o1，o2是list中的Map，可以在其内取得值，按其排序，此例为升序，s1和s2是排序字段值
				int s1 = (int) o1.get("id");
				int s2 = (int) o2.get("id");

				if (s1 > s2) {
					return 1;
				} else {
					return -1;
				}
			}
		});
	}
}

