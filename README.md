# app-protocol-stat-traffic-merge

Live Traffic Chart统计程序，基于协议栈拆分多流聚合，存储到协议与应用统计表中，使用增量窗口计算，周期15秒。

## 数据源    
以下不论基于哪种计算，Topic均为NETWORK-TRAFFIC-METRICS 
### 1.app-protocol-stat-traffic-agent程序基于已关闭/过渡会话日志统计Application and Protocol Metrics，聚合粒度为1秒。   
### 2.功能端进行统计产生的Application and Protocol Metrics数据，聚合粒度为1秒。

## 统计操作
### 1.过滤name是traffic_application_protocol_stat的数据。
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
