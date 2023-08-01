package com.example;

import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import org.apache.tomcat.util.json.JSONParser;
import org.bson.Document;
import org.springframework.http.ResponseEntity;
import com.example.openapi.generated.api.*;
import com.example.openapi.generated.model.ItemInfo;
import com.mongodb.MongoCommandException;
import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

@Controller
public class ExampleController implements StoreApi{

    private ClientSession session;
    private MongoDatabase db;
    private MongoCollection items, credentials;

    ExampleController() throws Exception{
        MongoClient client = MongoClients.create("mongodb://mongo");
        session = client.startSession();
        db = client.getDatabase("local");
        createDB();
    }

    private void createDB() throws Exception{
        try {
            db.createCollection(session,"credentials");
            db.createCollection(session,"items");
            items = db.getCollection("items");
            credentials = db.getCollection("credentials");
            JSONParser js = new JSONParser(this.getClass().getResourceAsStream("/BOOT-INF/classes/items.json"));
            ArrayList<Document> items_documents = new ArrayList<>();
            for (Object object : js.parseArray()) {
                items_documents.add(new Document((HashMap<String,?>) object));
            }
            items.insertMany(session,items_documents);
        } catch (Exception e) {
            if (e.getClass().getName().equals(MongoCommandException.class.getName())) {
                db.getCollection("credentials").drop();
                db.getCollection("items").drop();
                createDB();
            }
            else{
                throw e;
            }
        }
    }

    @Override
    public ResponseEntity<String> sayHelloToWorld() {
        return ResponseEntity.ok("Hello World");
    }

    @Override
    public ResponseEntity<ItemInfo> getItemById(Integer id) {
        // TODO Auto-generated method stub
        return StoreApi.super.getItemById(id);
    }
}
