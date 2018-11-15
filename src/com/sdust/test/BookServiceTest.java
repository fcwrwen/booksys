package com.sdust.test;

import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;

public class BookServiceTest extends BaseTest{

    @Test
    @Transactional
    @Rollback(false)
    public void test(){
        System.out.println(new File("upload").getAbsolutePath());
    }
}
