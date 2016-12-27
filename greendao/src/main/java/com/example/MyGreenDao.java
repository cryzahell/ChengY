package com.example;

import org.greenrobot.greendao.generator.DaoGenerator;
import org.greenrobot.greendao.generator.Entity;
import org.greenrobot.greendao.generator.Schema;

public class MyGreenDao {

    public static void main(String[] args) {
        try {
            Schema schema = new Schema(1, "com.ox.greendao");
            //城市
            addCity(schema);
            //画
            addEntityHua(schema);
            //时间区间
            addEntityTimeArea(schema);
            new DaoGenerator().generateAll(schema, "E:\\ChengY\\app\\src\\main\\java-gen");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addCity(Schema schema) {
        Entity city = schema.addEntity("City");
        city.addIdProperty();
        city.addStringProperty("name").notNull();
    }

    private static void addEntityTimeArea(Schema schema) {
        Entity timeArea = schema.addEntity("TimeArea");
        timeArea.addIdProperty();
        timeArea.addLongProperty("huaId");
        timeArea.addLongProperty("startTime").notNull();
        timeArea.addLongProperty("endTime").notNull();
    }

    private static void addEntityHua(Schema schema) {
        Entity hua = schema.addEntity("Hua");
        hua.addIdProperty();
        hua.addStringProperty("name").notNull();
        hua.addLongProperty("cityId");
        hua.addBooleanProperty("hasDrawn").notNull();
    }

}
