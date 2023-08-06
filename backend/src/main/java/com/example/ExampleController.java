package com.example;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.example.openapi.generated.api.*;
import com.example.openapi.generated.model.AllItemsInner;
import com.example.openapi.generated.model.ItemInfo;
import com.example.openapi.generated.model.LoginCredentials;
import com.example.openapi.generated.model.Order;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.mongodb.MongoCommandException;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import jakarta.validation.Valid;

@CrossOrigin
@Controller
public class ExampleController implements StoreApi {

    private ClientSession session;
    private MongoDatabase db;
    private MongoCollection items, credentials;

    ExampleController() throws Exception {
        MongoClient client = MongoClients.create("mongodb://mongo");
        session = client.startSession();
        db = client.getDatabase("local");
        createDB();
    }

    private void createDB() throws Exception {
        try {
            db.createCollection(session, "credentials");
            db.createCollection(session, "items");
            items = db.getCollection("items");
            credentials = db.getCollection("credentials");
            BufferedInputStream stream = new BufferedInputStream(
                    new FileInputStream("items.json"));
            ObjectReader js = new ObjectMapper().readerFor(Document.class);
            ArrayList<Document> ls = new ArrayList<>();
            Document[] arr = js.readValues(js.createParser(stream), Document[].class).next();
            for (Document element : arr) {
                ls.add(element);
            }
            items.insertMany(session, ls);
            stream.close();
            stream = new BufferedInputStream(
                    new FileInputStream("credentials.json"));
            js = new ObjectMapper().readerFor(Document.class);
            ls = new ArrayList<>();
            arr = js.readValues(js.createParser(stream), Document[].class).next();
            for (Document element : arr) {
                ls.add(element);
            }
            credentials.insertMany(session, ls);
            stream.close();
        } catch (Exception e) {
            if (e.getClass().getName().equals(MongoCommandException.class.getName())) {
                db.getCollection("credentials").drop();
                db.getCollection("items").drop();
                createDB();
            } else {
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
        AggregateIterable<Document> aggr = items.aggregate(session, Arrays.asList(new Document("$match",
                new Document("id", id))));
        Document doc = aggr.first();
        ItemInfo res = new ItemInfo();
        res.setId(doc.getInteger("id"));
        res.setName(doc.getString("name"));
        res.setPrice(new BigDecimal(doc.getDouble("price")));
        return new ResponseEntity<ItemInfo>(res, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<BigDecimal> calcOrder(@Valid Order order) {
        ArrayList<BigDecimal> list = (ArrayList<BigDecimal>) order.getPrices();
        BigDecimal total = BigDecimal.ZERO;
        for (BigDecimal item : list) {
            total = total.add(item);
        }
        return new ResponseEntity<BigDecimal>(total, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> authLogin(@Valid LoginCredentials loginCredentials) {
        // TODO Auto-generated method stub
        Document res = null;
        for (Document element : (AggregateIterable<Document>)credentials.aggregate(session, Arrays.asList(new Document("$match",new Document("user",loginCredentials.getUsername()).append("password", loginCredentials.getPassword()))))) {
            try {
                res = element;
            } catch (Exception e) {}
        }
        if(res != null){
            return new ResponseEntity<Void>(HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
    @Override
    public ResponseEntity<List<AllItemsInner>> getAllItems() {
        // TODO Auto-generated method stub
       Document res = null;
       ArrayList<AllItemsInner> list = new ArrayList<>();
       for (Document element : (AggregateIterable<Document>) items.aggregate(session, Arrays.asList(new Document("$match", new Document()))
   )) {
            AllItemsInner item = new AllItemsInner();
            item.setName(element.getString("name"));
            item.setImg(element.getString("img"));
            item.setPrice(new BigDecimal(element.getDouble("price")));
            item.setId(element.getInteger("id"));
            list.add(item);
       }
       return new ResponseEntity<List<AllItemsInner>>(list, HttpStatus.OK);
    }
}
