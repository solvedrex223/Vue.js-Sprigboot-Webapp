package com.example;

import org.springframework.stereotype.Controller;
import org.springframework.http.ResponseEntity;
import com.example.openapi.generated.api.*;

@Controller
public class ExampleController implements StoreApi{

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

    @Override
    public ResponseEntity<String> getItemById(Integer id) {
        return StoreApi.super.getItemById(id);
    }
}
