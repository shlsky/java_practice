/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.shl.practice;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Output;
import com.shl.PersonDTO;

/**
 *
 * @author songhongling
 * @version $Id: KryoTest.java, v 0.1 2018年04月26日 下午9:07 songhongling Exp $
 */
public class KryoTest {

    public static void main(String[] args) {
        PersonDTO personDTO = new PersonDTO("shl","27");
        Kryo kryo = new Kryo();
        Output output = null;
        try {
            output = new Output(new FileOutputStream("file.bin"));
            kryo.writeClassAndObject(output, personDTO);
            output.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}