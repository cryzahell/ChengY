package com.example;

import org.greenrobot.greendao.generator.DaoGenerator;
import org.greenrobot.greendao.generator.Entity;
import org.greenrobot.greendao.generator.Schema;

public class MyGreenDao {

    public static void main(String[] args) {
        try {
            Schema schema = new Schema(1, "com.ox.greendao");
            //画
            addEntityHua(schema);
            //时间区间
            addEntityTimeArea(schema);
            new DaoGenerator().generateAll(schema, "E:\\ChengY\\app\\src\\main\\java-gen");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addEntityTimeArea(Schema schema) {
        Entity timeArea = schema.addEntity("TimeArea");
        timeArea.addLongProperty("startTime").notNull();
        timeArea.addLongProperty("endTime").notNull();
    }

    private static void addEntityHua(Schema schema) {
        Entity hua = schema.addEntity("Hua");
        hua.addIdProperty();
        hua.addStringProperty("name").notNull();
        hua.addBooleanProperty("hasDrawn").notNull();
    }

}
