# DataStream
A simple demo for analysing log data by using flink.

This project can output processed results to Elasticsearch, Kafka, HBase, HDFS/S3 (Parquet/CSV), JDBC databases (MySQL/Postgres), Redis, ClickHouse, Cassandra, Iceberg/Hive data lakes, and a dead-letter queue for failed records, and it exposes monitoring metrics (e.g., Prometheus).



```mermaid
flowchart TD
  A["Log Point<br/>(Application log instrumentation)"] --> B[Processing]
  B --> F[Map]
  F --> G[KeyBy]
  G --> H[Window]
  H --> I[Aggregate]
  I --> J[Sink]
  J --> K[Dashboard]
  J --> L[Dead Letter]
  J --> M[Monitoring]

