package com.example.protobuf;

import com.example.protobuf.model.CatModel;
import com.example.protobuf.model.Date;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;

import java.util.Arrays;

public class ProtoApplication {
    public static void main(String[] args) throws InvalidProtocolBufferException {
        System.out.println("Hello Proto");


        CatModel.Builder catBuilder = CatModel.newBuilder()
                .setAge(2)
                .setBreed(CatModel.CatBreed.TABBY)
                .setIsVaccinated(true)
                .setName("Toohey")
                .setWeight(3)
                .setNextVaccination(Date.newBuilder()
                        .setDay(22)
                        .setMonth(12)
                        .setYear(2020)
                        .build())
                .addAllFavoriteFoods(Arrays.asList("Tuna", "Chicken"));

        String json = JsonFormat.printer().print(catBuilder);
        System.out.println(json);
    }
}
