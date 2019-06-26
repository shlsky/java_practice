package com.java.model;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Song Hongling
 * @date 2019/6/25
 */
@Data
@Accessors(chain = true)
public class Student {
	
	private String name;
	
	private School school;
}
