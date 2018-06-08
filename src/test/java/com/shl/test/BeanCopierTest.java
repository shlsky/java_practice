package com.shl.test;

import com.shl.PersonDTO;
import com.shl.practice.PartmentDTO;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.cglib.beans.BeanCopier;

import java.util.List;

public class BeanCopierTest {

    @Test
    public void test() {

        PartmentDTO partmentDTO = new PartmentDTO();
        partmentDTO.setName("partment1");
        for (int i = 0; i < 2; i++) {
            partmentDTO.getPersonDTOS().add(new PersonDTO("dada", i + ""));
        }
        BeanCopier copier = BeanCopier.create(PartmentDTO.class, PartmentTarget.class, false);

        PartmentTarget partmentTarget = new PartmentTarget();
        copier.copy(partmentDTO, partmentTarget, null);
        Assert.assertEquals(partmentDTO.getPersonDTOS(), partmentTarget.getPersonDTOS());
        Assert.assertEquals(partmentDTO.getName(), partmentTarget.getName());
        partmentTarget.setName("partment3");

        Assert.assertEquals(partmentDTO.getName(), partmentTarget.getName());
    }

    private class PartmentTarget {

        private String name;

        /**
         * 部门中人员
         */
        private List<PersonDTO> personDTOS;

        public List<PersonDTO> getPersonDTOS() {
            return personDTOS;
        }

        public void setPersonDTOS(List<PersonDTO> personDTOS) {
            this.personDTOS = personDTOS;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
