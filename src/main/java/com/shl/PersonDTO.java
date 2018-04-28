/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.shl;

/**
 *
 * @author songhongling
 * @version $Id: PersonDTO.java, v 0.1 2018年04月26日 下午9:08 songhongling Exp $
 */
public class PersonDTO {

    /** 姓名 */
    private String name;

    /** 年龄 */
    private String age;

    public PersonDTO(String name,String age){
        this.name = name;
        this.age=age;
    }

    public PersonDTO(){

    }
    /**
     * Getter method for property name.
     *
     * @return property value of name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter method for property name.
     *
     * @param name value to be assigned to property name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter method for property age.
     *
     * @return property value of age
     */
    public String getAge() {
        return age;
    }

    /**
     * Setter method for property age.
     *
     * @param age value to be assigned to property age
     */
    public void setAge(String age) {
        this.age = age;
    }
}