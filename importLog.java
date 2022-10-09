import com.mongodb.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.bson.Document;
import org.json.JSONArray;

import javax.print.Doc;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class importLog {
    public static void main(String[]args){
        String path ="access.log";
        MongoClient client = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = client.getDatabase("logs");
        MongoCollection<Document> toys = database.getCollection("access");
        System.out.println(toys.countDocuments());
        CSVParser parser = new CSVParserBuilder().withSeparator(' ').build();
        try {
            CSVReader reader = new CSVReaderBuilder(new FileReader(path)).withCSVParser(parser).build();
            String[] nextRecord;

            // we are going to read data line by line
            while ((nextRecord = reader.readNext()) != null) {
                //127.0.0.1 - - "15/Oct/2011:11:49:11 -0400" "GET / HTTP/1.1" 200 44
                Document doc = new Document();
                doc.append("ipaddress", nextRecord[0]);
                doc.append("time", nextRecord[3]);
                doc.append("req", nextRecord[4]);
                doc.append("status", nextRecord[5]);
                doc.append("size", nextRecord[6]);
                toys.insertOne(doc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}