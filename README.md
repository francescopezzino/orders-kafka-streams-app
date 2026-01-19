### Branch data from a KStream's channel (orders) and publish into two separated channels/ topics (general and restaurant orders)

#### Run OrdersKafkaStreamApp, then instantiate the following two console for the two consumers (branched) and then run com/learnkafkastreams/producer/OrdersMockDataProducer.java

- docker exec -it broker bash
- kafka-console-consumer --bootstrap-server localhost:9092 --topic restaurant_orders
- 
```
[appuser@broker ~]$ kafka-console-consumer --bootstrap-server localhost:9092 --topic restaurant_orders
{
	"orderId": 54321,
	"locationId": "store_1234",
	"finalAmount": 15.0,
	"orderType": "RESTAURANT",
	"orderLineItems": [
		{
			"item": "Pizza",
			"count": 2,
			"amount": 12.0
		},
		{
			"item": "Coffee",
			"count": 1,
			"amount": 3.0
		}
	],
	"orderedDateTime": "2026-01-19T16:57:49.7398746"
}
{
	"orderId": 12345,
	"locationId": "store_4567",
	"finalAmount": 27.0,
	"orderType": "RESTAURANT",
	"orderLineItems": [
		{
			"item": "Bananas",
			"count": 2,
			"amount": 2.0
		},
		{
			"item": "Iphone Charger",
			"count": 1,
			"amount": 25.0
		}
	],
	"orderedDateTime": "2026-01-19T16:57:49.7398746"
}

```



- docker exec -it broker bash
- kafka-console-consumer --bootstrap-server localhost:9092 --topic general_orders

```
[appuser@broker ~]$ kafka-console-consumer --bootstrap-server localhost:9092 --topic general_orders
{
	"orderId": 12345,
	"locationId": "store_1234",
	"finalAmount": 27.0,
	"orderType": "GENERAL",
	"orderLineItems": [
		{
			"item": "Bananas",
			"count": 2,
			"amount": 2.0
		},
		{
			"item": "Iphone Charger",
			"count": 1,
			"amount": 25.0
		}
	],
	"orderedDateTime": "2026-01-19T16:57:49.7398746"
}
{
	"orderId": 12345,
	"locationId": "store_4567",
	"finalAmount": 27.0,
	"orderType": "GENERAL",
	"orderLineItems": [
		{
			"item": "Bananas",
			"count": 2,
			"amount": 2.0
		},
		{
			"item": "Iphone Charger",
			"count": 1,
			"amount": 25.0
		}
	],
	"orderedDateTime": "2026-01-19T16:57:49.7398746"
}

```

