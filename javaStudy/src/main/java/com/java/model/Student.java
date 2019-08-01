package com.java.model;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Table;

/**
 * @author Song Hongling
 * @date 2019/6/25
 */
@Data
@Table(name = "prize_info", schema = "growth", catalog = "")
@Accessors(chain = true)
public class Student {
	
	private String name;
	
	private School school;
}
