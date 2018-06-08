package com.shl.practice;

import com.shl.PersonDTO;

import java.util.ArrayList;
import java.util.List;

public class PartmentDTO {

    private String name;

    /**
     * 部门中人员
     */
    private List<PersonDTO> personDTOS = new ArrayList<PersonDTO>();

    public List<PersonDTO> getPersonDTOS() {
        return personDTOS;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
