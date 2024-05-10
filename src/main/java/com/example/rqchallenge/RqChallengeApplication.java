package com.example.rqchallenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RqChallengeApplication {

    public static final String MY_TOKEN="ghp_seO7JSoNqZ1Jlsafyj4VkNlibNtjU80iPfV5"
    public static final String AZURE_CONNECTION_STRING = "DefaultEndpointsProtocol=http;AccountName=devstoreaccount1;AccountKey=Eby8vdM02xNOcqFlqUwJPLlmEtlCDXJ1OUzFT50uSRZ6IFsuFq2UVErCz4I6tq/K1SZFPTOtr/KBHBeksoGMGw==;BlobEndpoint=http://0.0.0.0:64625/devstoreaccount1";

    public static void main(String[] args) {
        SpringApplication.run(RqChallengeApplication.class, args);
    }

}
