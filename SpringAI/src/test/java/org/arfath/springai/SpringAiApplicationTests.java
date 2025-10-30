package org.arfath.springai;

import org.arfath.springai.helper.Helper;
import org.arfath.springai.services.chatservice;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringAiApplicationTests {

    @Autowired
    private chatservice chatservice;

    @Test
    void savedatatovectordb(){
        System.out.println("saving data to vector database");
        this.chatservice.saveData(Helper.getData());
        System.out.println("data saved successfully");
    }



}
