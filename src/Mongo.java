import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

import java.util.Arrays;

import org.bson.Document;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;

public class Mongo {
	public static MongoClient client;
	
	static MongoCollection<Document> coll;
	 public static void listOfCollections(MongoDatabase db){
		 
	    System.out.println("List of collections");
	    MongoIterable<String> colls = db.listCollectionNames();
	    for(String s:colls){
	      System.out.println(s);
	    }
	  }
	 
	 public static void main(String args[]){
			try{
				client = new MongoClient("localhost", 27017);
				
				MongoDatabase db = client.getDatabase("Foursquare");
				System.out.println("Conected to DB succefully");
				listOfCollections(db);
				Query1(db);
				Query2(db);
				Query3(db);
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				client.close();
			}
		}
	 
	 public static void Query1(MongoDatabase db) {
			FindIterable<Document> it2=db.getCollection("restaurants")
					.find(new Document("grades.score", new Document("$lt",10)))
					.limit(2);
			for(Document d:it2){
				System.out.println(d);
			}
	 }
	 
	 public static void Query2(MongoDatabase db){
		 FindIterable<Document> it=db.getCollection("restaurants")
					.find(new Document("$or", Arrays.asList(new Document("cuisine",
							"American"), new Document("address.zipcode", "10075"))))
					.limit(5);
		 for (Document d:it){
			 System.out.println(d);
		 }
	 }
	 
	 public static void Query3(MongoDatabase db){
		 FindIterable<Document> it=db.getCollection("restaurants")
					.find().sort(new Document("borough",1).append("address.zipcode", 1))
					.skip(1000).limit(5);
		 for (Document d:it){
			 System.out.println(d);
		 }
	 }
}
