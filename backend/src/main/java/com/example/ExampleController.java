package com.example;

import org.springframework.stereotype.Controller;
import org.springframework.http.ResponseEntity;
import com.example.openapi.generated.api.ExampleApi;
import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

@Controller
public class ExampleController implements ExampleApi {

    ClientSession session;

    ExampleController(){
        MongoClient client = MongoClients.create("mongodb://mongo");
        MongoDatabase db = client.getDatabase("local");
        session = client.startSession();
        try {
            db.createCollection(session, "credentials");
            db.createCollection(session, "items");
        } catch (Exception e) {}

    }

    @Override
    public ResponseEntity<String> sayHelloToWorld() {
        return ResponseEntity.ok("Hello World");
    }

}
