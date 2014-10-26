package org.shine.common.mongodb;

import com.google.common.base.Stopwatch;
import com.mongodb.*;
import org.w3c.dom.Document;

import java.io.Console;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by fuzhaohui on 14/10/23.
 */
public class MongoIndex {

    private DBCollection mongoCollection;

    public MongoIndex() {
        try{
            MongoClient mongoClient = new MongoClient();
            DB db = mongoClient.getDB("test");
            mongoCollection = db.getCollection("users");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void InsertBigData()
    {
        Random random = new Random();
        for (int i = 1; i < 100000; i++)
        {
            DBObject doc = new BasicDBObject();

            doc.put("ID", i);
            doc.put("Data", "data" + random.nextFloat());

            mongoCollection.save(doc);
        }

        System.out.println("当前有" + mongoCollection.find().count() + "条数据");
    }

    public void CreateIndexForData(){
        DBObject indexObj = new BasicDBObject();
        indexObj.put("Date", 1);
        mongoCollection.createIndex(indexObj);
    }

    public void SortForData(){
        DBObject sortObj = new BasicDBObject();
        sortObj.put("Data", 1);
        mongoCollection.find().sort(sortObj);
    }

    public static void Main(String[] args) {
        MongoIndex indexBll = new MongoIndex();
        indexBll.InsertBigData();

        Stopwatch watch1 = new Stopwatch();
        watch1.start();
        for (int i = 0; i < 1; i++) indexBll.SortForData();
        System.out.println("无索引排序执行时间：" + watch1.elapsed(TimeUnit.SECONDS));

        indexBll.CreateIndexForData();

        Stopwatch watch2 = new Stopwatch();
        watch2.start();
        for (int i = 0; i < 1; i++) indexBll.SortForData();
        System.out.println("有索引排序执行时间：" + watch1.elapsed(TimeUnit.SECONDS));

    }

}
