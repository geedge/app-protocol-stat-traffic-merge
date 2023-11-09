# app-protocol-stat-traffic-merge

Live Traffic Chart统计程序，基于协议栈拆分多流聚合，存储到协议与应用统计表中，使用增量窗口计算，统计周期5秒，watermark5秒。

## 数据     

以下不论基于哪种计算，Topic均为NETWORK-TRAFFIC-METRICS 

### 1.app-protocol-stat-traffic-agent程序基于已关闭/过渡会话日志统计Application and Protocol Metrics，聚合粒度为1秒。（TSG 23.05版本）  

### 2.功能端进行统计产生的Application and Protocol Metrics数据，聚合粒度为1秒。（≥TSG 23.05版本）

<br/>

## 简介  

### 1.过滤Measurement Name是traffic_application_protocol_stat的数据。

### 2.基于Tags内容进行分组统计。

### 3.拆分protocol_stack_id协议树为多个节点

#### 例如，ETHERNET.IPv4.TCP.https.kingsoft.wps_office，每个节点ID为：

##### ETHERNET

##### ETHERNET.IPv4

##### ETHERNET.IPv4.TCP

##### ETHERNET.IPv4.TCP.https

##### ETHERNET.IPv4.TCP.https.kingsoft

##### ETHERNET.IPv4.TCP.https.kingsoft.wps_office

### 4.app_name仅在终端节点输出。

### 5.输出结果时Measurement Name=application_protocol_stat。

<br/>

## 启动

###  Standalone:  

`flink run [-p parallelism] -c com.zdjizhi.topology.ApplicationProtocolTopology app-protocol-stat-traffic-merge-[version].jar app.properties`

###  Yarn:

`flink run -t yarn-per-job -Djobmanager.memory.process.size=1024m -Dtaskmanager.memory.process.size=2048m -Dyarn.application.name=APP-PROTOCOL-STAT-TRAFFIC-MERGE  -Dtaskmanager.numberOfTaskSlots=1 -d -p 3 -c com.zdjizhi.topology.ApplicationProtocolTopology app-protocol-stat-traffic-merge-[version].jar app.properties`

<br/>

## 配置项说明

|配置项|类型|必填|默认值|含义|
|--|--|--|--|--|
|source.kafka.topic|STRING|Y||数据源的Kafka Topic 名称|
|source.kafka.props.*|STRING|N||数据源的Kafka 消费者连接相关参数|
|startup.mode|STRING|N|group|数据源消费策略（group：从当前消费组的偏移量开始，latest：从分区最新的偏移量开始，earliest：从分区最早的偏移量开始）|
|sink.kafka.topic|STRING|Y||数据输出的Kafka Topic 名称|
|sink.kafka.props.*|STRING|N||数据输出的Kafka 生产者连接相关参数|
|count.window.time|INT|N|5|聚合窗口大小（单位：秒）|
|watermark.max.orderness|INT|N|5|乱序数据的最大延迟时间（单位：秒）|
|log.failures.only|BOOLEAN|N|false|true：生产者出现错误时任务失败，false：只记录错误信息|
|measurement.name|STRING|N|application_protocol_stat|数据输出时的指标标识名称|
