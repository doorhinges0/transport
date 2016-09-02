 1. start kafka
 cd /home/yej/kafka/kafka_2.10-0.8.2.0
 bin/kafka-server-start.sh config/server.properties
 
 2. consumer 
  bin/kafka-console-consumer.sh --zookeeper localhost:2181 --topic gpssignal --from-beginning
 
 