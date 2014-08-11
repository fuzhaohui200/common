package org.shine.common.mongodb;

import com.mongodb.*;
import com.mongodb.util.JSON;

import java.net.UnknownHostException;

/**
 * <b>function:</b>MongoDB 简单示例
 *
 * @author hoojo
 * @version 1.0
 * @createDate 2011-5-24 下午02:42:29
 * @file SimpleTest.java
 * @package com.hoo.test
 * @project MongoDB
 * @blog http://blog.csdn.net/IBM_hoojo
 * @email hoojo_@126.com
 */
public class SimpleTest {

    public static void main(String[] args) throws UnknownHostException, MongoException {
        Mongo mg = new Mongo();
        //查询所有的Database
        for (String name : mg.getDatabaseNames()) {
            System.out.println("dbName: " + name);
        }

        DB db = mg.getDB("test");
        //查询所有的聚集集合
        for (String name : db.getCollectionNames()) {
            System.out.println("collectionName: " + name);
        }

        DBCollection users = db.getCollection("users");

        //查询所有的数据
        DBCursor cur = users.find();
        while (cur.hasNext()) {
            System.out.println(cur.next());
        }
        System.out.println(cur.count());
        System.out.println(cur.getCursorId());
        System.out.println(JSON.serialize(cur));
    }
}