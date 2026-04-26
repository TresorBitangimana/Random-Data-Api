package org.tresor.randomdataapi;

import com.mongodb.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Aggregates;
import io.github.cdimascio.dotenv.Dotenv;
import org.bson.Document;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/random")
@CrossOrigin("*")
public class App {

    Dotenv dotenv = Dotenv.load();
    String connectionString = dotenv.get("CONNECTION_STRING");

    ServerApi serverApi = ServerApi.builder()
            .version(ServerApiVersion.V1)
            .build();

    MongoClientSettings settings = MongoClientSettings.builder()
            .applyConnectionString(new ConnectionString(connectionString))
            .serverApi(serverApi)
            .build();

    /**
     * users api call method
     * @param id
     * @return {id} numbers of random users.
     */
    @GetMapping({"/users", "/users/{id}"})
    List<User> getUsers(@PathVariable(required = false) Integer id){

        if(id == null){
            id = 10;
        }

        try(MongoClient mongoClient = MongoClients.create(settings)){

            MongoDatabase database = mongoClient.getDatabase("sample_mflix");
            MongoCollection<Document> collection = database.getCollection("users");

            // $sample randomly selects the number of documents specified by the id
            List<Document> randomUsers = collection.aggregate(
                    List.of(Aggregates.sample(id))
            ).into(new ArrayList<>());

            List<User> users = new ArrayList<>();

            //iterates through the document to get only the name, email, and
            // password from the documents retrieved
            for(Document doc : randomUsers){
                User user = new User(
                        doc.getString("name"), doc.getString("email"), doc.getString("password")
                );
                users.add(user);
            }

            //returns the ArrayList of users
            return users;

        }catch(MongoException e){
            System.out.println(e);
        }
        return null;
    }

    /**
     * grades apu call method
     * @param id
     * @return  {id} numbers of random grades
     */
    @GetMapping({"/grades", "/grades/{id}"})
    List<Grades> getGrades(@PathVariable(required = false) Integer id){

        if(id == null){
            id = 10;
        }

        try(MongoClient mongoClient = MongoClients.create(settings)){

            MongoDatabase database = mongoClient.getDatabase("sample_training");
            MongoCollection<Document> collection = database.getCollection("grades");

            List<Document> randomDocs = collection.aggregate(
                    List.of(Aggregates.sample(id))
            ).into(new ArrayList<>());

            ArrayList<Grades> grades = new ArrayList<>();

            for(Document doc : randomDocs){
                Grades grade = new Grades(
                        doc.getDouble("student_id"),
                        doc.getDouble("class_id"),
                        doc.getList("scores", Document.class )
                );
                grades.add(grade);
            }

            return grades;

        }catch(MongoException e){
            System.out.println(e);
        }

        return null;
    }

    /**
     * companies api call method
     * @param id
     * @return {id} number of random companies
     */
    @GetMapping({"companies", "companies/{id}"})
    List<Company> getCompanies(@PathVariable(required = false) Integer id){

        try(MongoClient mongoClient = MongoClients.create(settings)){

            MongoDatabase database = mongoClient.getDatabase("sample_training");
            MongoCollection<Document> collection =  database.getCollection("companies");

            List<Document> randomDocs = collection.aggregate(
                    List.of(Aggregates.sample(id))
            ).into(new ArrayList<>());

            ArrayList<Company> companies = new ArrayList<>();

            for(Document doc : randomDocs){
                Company company = new Company(
                        doc.getString("name"),
                        doc.getString("permalink"),
                        doc.getString("crunchbase_url"),
                        doc.getString("homepage_url"),
                        doc.getString("blog_url"),
                        doc.getString("twitter_username"),
                        doc.getString("category_code"),

                        doc.getInteger("number_of_employees", 0),
                        doc.getInteger("founded_year", 0),
                        doc.getInteger("founded_month", 0),
                        doc.getInteger("founded_day", 0),

                        doc.getString("description"),
                        doc.getString("created_at"),
                        doc.getString("overview"),

                        doc.getList("products", Document.class),
                        doc.getList("relationships", Document.class),
                        doc.getList("competitions", Document.class),
                        doc.getList("providership", Document.class),

                        doc.getString("total_money_raise"),

                        doc.getList("investments", Document.class),
                        doc.getList("offices", Document.class),
                        doc.getList("milestones", Document.class)
                );

                companies.add(company);
            }

            return companies;

        }catch(MongoException e){
            System.out.println(e);
        }
        return null;
    }

}
