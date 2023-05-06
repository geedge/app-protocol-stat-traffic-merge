package com.zdjizhi.common.pojo;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author qidaijie
 * @Package com.zdjizhi.common.pojo
 * @Description:
 * @date 2023/4/2116:06
 */
public class AppProtocol {
    private Long timestamp;
    private int vsys_id;
    private String device_id;
    private String device_group;
    private String data_center;
    private String protocol_stack_id;
    private String app_name;
    private Long sessions;
    private Long in_bytes;
    private Long out_bytes;
    private Long in_pkts;
    private Long out_pkts;
    private Long c2s_pkts;
    private Long s2c_pkts;
    private Long c2s_bytes;
    private Long s2c_bytes;
    private Long c2s_fragments;
    private Long s2c_fragments;
    private Long c2s_tcp_lost_bytes;
    private Long s2c_tcp_lost_bytes;
    private Long c2s_tcp_ooorder_pkts;
    private Long s2c_tcp_ooorder_pkts;
    private Long c2s_tcp_retransmitted_pkts;
    private Long s2c_tcp_retransmitted_pkts;
    private Long c2s_tcp_retransmitted_bytes;
    private Long s2c_tcp_retransmitted_bytes;
    private String client_ip_sketch;

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public int getVsys_id() {
        return vsys_id;
    }

    public void setVsys_id(int vsys_id) {
        this.vsys_id = vsys_id;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getDevice_group() {
        return device_group;
    }

    public void setDevice_group(String device_group) {
        this.device_group = device_group;
    }

    public String getData_center() {
        return data_center;
    }

    public void setData_center(String data_center) {
        this.data_center = data_center;
    }

    public String getProtocol_stack_id() {
        return protocol_stack_id;
    }

    @JSONField(name = "protocol_label")
    public void setProtocol_stack_id(String protocol_stack_id) {
        this.protocol_stack_id = protocol_stack_id;
    }

    public String getApp_name() {
        return app_name;
    }

    @JSONField(name = "app_full_path")
    public void setApp_name(String app_name) {
        this.app_name = app_name;
    }

    public Long getSessions() {
        return sessions;
    }

    public void setSessions(Long sessions) {
        this.sessions = sessions;
    }

    public Long getIn_bytes() {
        return in_bytes;
    }

    public void setIn_bytes(Long in_bytes) {
        this.in_bytes = in_bytes;
    }

    public Long getOut_bytes() {
        return out_bytes;
    }

    public void setOut_bytes(Long out_bytes) {
        this.out_bytes = out_bytes;
    }

    public Long getIn_pkts() {
        return in_pkts;
    }

    public void setIn_pkts(Long in_pkts) {
        this.in_pkts = in_pkts;
    }

    public Long getOut_pkts() {
        return out_pkts;
    }

    public void setOut_pkts(Long out_pkts) {
        this.out_pkts = out_pkts;
    }

    public Long getC2s_pkts() {
        return c2s_pkts;
    }

    public void setC2s_pkts(Long c2s_pkts) {
        this.c2s_pkts = c2s_pkts;
    }

    public Long getS2c_pkts() {
        return s2c_pkts;
    }

    public void setS2c_pkts(Long s2c_pkts) {
        this.s2c_pkts = s2c_pkts;
    }

    public Long getC2s_bytes() {
        return c2s_bytes;
    }

    public void setC2s_bytes(Long c2s_bytes) {
        this.c2s_bytes = c2s_bytes;
    }

    public Long getS2c_bytes() {
        return s2c_bytes;
    }

    public void setS2c_bytes(Long s2c_bytes) {
        this.s2c_bytes = s2c_bytes;
    }

    public Long getC2s_fragments() {
        return c2s_fragments;
    }

    public void setC2s_fragments(Long c2s_fragments) {
        this.c2s_fragments = c2s_fragments;
    }

    public Long getS2c_fragments() {
        return s2c_fragments;
    }

    public void setS2c_fragments(Long s2c_fragments) {
        this.s2c_fragments = s2c_fragments;
    }

    public Long getC2s_tcp_lost_bytes() {
        return c2s_tcp_lost_bytes;
    }

    public void setC2s_tcp_lost_bytes(Long c2s_tcp_lost_bytes) {
        this.c2s_tcp_lost_bytes = c2s_tcp_lost_bytes;
    }

    public Long getS2c_tcp_lost_bytes() {
        return s2c_tcp_lost_bytes;
    }

    public void setS2c_tcp_lost_bytes(Long s2c_tcp_lost_bytes) {
        this.s2c_tcp_lost_bytes = s2c_tcp_lost_bytes;
    }

    public Long getC2s_tcp_ooorder_pkts() {
        return c2s_tcp_ooorder_pkts;
    }

    public void setC2s_tcp_ooorder_pkts(Long c2s_tcp_ooorder_pkts) {
        this.c2s_tcp_ooorder_pkts = c2s_tcp_ooorder_pkts;
    }

    public Long getS2c_tcp_ooorder_pkts() {
        return s2c_tcp_ooorder_pkts;
    }

    public void setS2c_tcp_ooorder_pkts(Long s2c_tcp_ooorder_pkts) {
        this.s2c_tcp_ooorder_pkts = s2c_tcp_ooorder_pkts;
    }

    public Long getC2s_tcp_retransmitted_pkts() {
        return c2s_tcp_retransmitted_pkts;
    }

    public void setC2s_tcp_retransmitted_pkts(Long c2s_tcp_retransmitted_pkts) {
        this.c2s_tcp_retransmitted_pkts = c2s_tcp_retransmitted_pkts;
    }

    public Long getS2c_tcp_retransmitted_pkts() {
        return s2c_tcp_retransmitted_pkts;
    }

    public void setS2c_tcp_retransmitted_pkts(Long s2c_tcp_retransmitted_pkts) {
        this.s2c_tcp_retransmitted_pkts = s2c_tcp_retransmitted_pkts;
    }

    public Long getC2s_tcp_retransmitted_bytes() {
        return c2s_tcp_retransmitted_bytes;
    }

    public void setC2s_tcp_retransmitted_bytes(Long c2s_tcp_retransmitted_bytes) {
        this.c2s_tcp_retransmitted_bytes = c2s_tcp_retransmitted_bytes;
    }

    public Long getS2c_tcp_retransmitted_bytes() {
        return s2c_tcp_retransmitted_bytes;
    }

    public void setS2c_tcp_retransmitted_bytes(Long s2c_tcp_retransmitted_bytes) {
        this.s2c_tcp_retransmitted_bytes = s2c_tcp_retransmitted_bytes;
    }

    public String getClient_ip_sketch() {
        return client_ip_sketch;
    }

    public void setClient_ip_sketch(String client_ip_sketch) {
        this.client_ip_sketch = client_ip_sketch;
    }
}
