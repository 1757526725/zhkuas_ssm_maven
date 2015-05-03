package com.zhku.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;


public class CommonTest {


	public static void main(String[] args) {
		List<Cat> srcCatList = new ArrayList<Cat>();
		srcCatList.add(new Cat("1","tom"));
		srcCatList.add(new Cat("2","tom2"));
		srcCatList.add(new Cat("3","tom3"));
		Map<String,Cat> catMap = new HashMap<String,Cat>();
		for(Cat cat : srcCatList){
			catMap.put(cat.getId(), cat);
		}
		
		catMap.get("1").setName("tomModifiy");
		
		System.out.println(JSON.toJSONString(srcCatList));
	}
}
class Cat{
	private String id;
	private String name;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	Cat(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
}