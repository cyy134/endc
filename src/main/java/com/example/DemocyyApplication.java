package com.example;

import com.example.server.NettyServer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.Resource;

@SpringBootApplication
public class DemocyyApplication implements CommandLineRunner {

    @Resource
    NettyServer nettyServer;

    public static void main(String[] args) {
        SpringApplication.run(DemocyyApplication.class, args);
        System.out.print("............");
    }

    @Override
    public void run(String... args) throws Exception {
        nettyServer.start();
    }
}
