package com.learnkafkastreams.topology;

import com.learnkafkastreams.domain.Order;
import com.learnkafkastreams.domain.OrderType;
import com.learnkafkastreams.serdes.SerdesFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.*;

@Slf4j
public class OrdersTopology {
    public static final String ORDERS = "orders";
    public static final String GENERAL_ORDERS = "general_orders";
    public static final String RESTAURANT_ORDERS = "restaurant_orders";
    public static final String STORES = "stores";



    public static Topology buildTopology() {

        Predicate<String, Order> generalPredicate =  (key, order) -> order.orderType().equals(OrderType.GENERAL);
        Predicate<String, Order> restaurantPredicate =  (key, order) -> order.orderType().equals(OrderType.RESTAURANT);

        StreamsBuilder streamsBuilder = new StreamsBuilder();
        var ordersStream = streamsBuilder
                .stream(ORDERS,
                        Consumed.with(Serdes.String(), SerdesFactory.orderSerde()));

        ordersStream.print(Printed.<String, Order> toSysOut().withLabel("orders"));
        // using the operators split and branch we splitted and branched the original stream
        ordersStream
                .split(Named.as("General-restaurant-stream"))
                .branch(generalPredicate,
                    Branched.withConsumer(generalOrderStrem -> {
                        // following just for debugging purposes in development, not to do in prod
                        generalOrderStrem.print(Printed.<String, Order>toSysOut().withLabel("generalStream"));
                        generalOrderStrem.to(GENERAL_ORDERS,
                                Produced.with(Serdes.String(), SerdesFactory.orderSerde()));
                    })
                )
                .branch(restaurantPredicate,
                        Branched.withConsumer(restaurantOrderStream -> {
                            restaurantOrderStream
                                    .print(Printed.<String, Order>toSysOut().withLabel("restaurantStream"));
                            restaurantOrderStream
                                    .to(RESTAURANT_ORDERS,
                                    Produced.with(Serdes.String(), SerdesFactory.orderSerde()));
                        })
                );
        return streamsBuilder.build();
    }
}
