package com.shl.practice;

import org.springframework.cglib.beans.BeanCopier;

import java.math.BigDecimal;

/**
 * Created by hongling.shl on 2018/6/13.
 */
public class BeanCopyTest {
	public static void main(String[] args) {
		BeanCopier beanCopier = BeanCopier.create(Demo1.class,Demo2.class,false);
		Demo1 demo1 = new Demo1();
		demo1.setName("shl");
		demo1.setScore(BigDecimal.valueOf(100));
		Demo2 demo2 = new Demo2();
		
		beanCopier.copy(demo1,demo2,null);
		System.out.println(demo2);
		
	}
	
	public static class Demo1{
		private String name;
		private BigDecimal score;
		
		public String getName() {
			return name;
		}
		
		public void setName(String name) {
			this.name = name;
		}
		
		public BigDecimal getScore() {
			return score;
		}
		
		public void setScore(BigDecimal score) {
			this.score = score;
		}
	}
	
	public static class Demo2{
		private String name;
		private BigDecimal score;
		private BigDecimal grade;
		
		public String getName() {
			return name;
		}
		
		public void setName(String name) {
			this.name = name;
		}
		
		public BigDecimal getScore() {
			return score;
		}
		
		public void setScore(BigDecimal score) {
			this.score = score;
		}
		
		public BigDecimal getGrade() {
			return grade;
		}
		
		public void setGrade(BigDecimal grade) {
			this.grade = grade;
		}
	}
}
