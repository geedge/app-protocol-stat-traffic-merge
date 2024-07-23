package com.zdjizhi.common.pojo;

import java.io.Serializable;

public class ResultData implements Serializable {
    public long timestamp_ms;
    public String name;

    public int vsys_id;
    public String device_id;
    public String device_group;
    public String data_center;
    public String protocol_stack_id;
    public String app_name;

    public long sessions;
    public long in_bytes;
    public long out_bytes;
    public long in_pkts;
    public long out_pkts;
    public long c2s_pkts;
    public long s2c_pkts;
    public long c2s_bytes;
    public long s2c_bytes;
    public long c2s_fragments;
    public long s2c_fragments;
    public long c2s_tcp_lost_bytes;
    public long s2c_tcp_lost_bytes;
    public long c2s_tcp_ooorder_pkts;
    public long s2c_tcp_ooorder_pkts;
    public long c2s_tcp_retransmitted_pkts;
    public long s2c_tcp_retransmitted_pkts;
    public long c2s_tcp_retransmitted_bytes;
    public long s2c_tcp_retransmitted_bytes;

    public long getTimestamp_ms() {
        return timestamp_ms;
    }

    public void setTimestamp_ms(long timestamp_ms) {
        this.timestamp_ms = timestamp_ms;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public void setProtocol_stack_id(String protocol_stack_id) {
        this.protocol_stack_id = protocol_stack_id;
    }

    public String getApp_name() {
        return app_name;
    }

    public void setApp_name(String app_name) {
        this.app_name = app_name;
    }

    public long getSessions() {
        return sessions;
    }

    public void setSessions(long sessions) {
        this.sessions = sessions;
    }

    public long getIn_bytes() {
        return in_bytes;
    }

    public void setIn_bytes(long in_bytes) {
        this.in_bytes = in_bytes;
    }

    public long getOut_bytes() {
        return out_bytes;
    }

    public void setOut_bytes(long out_bytes) {
        this.out_bytes = out_bytes;
    }

    public long getIn_pkts() {
        return in_pkts;
    }

    public void setIn_pkts(long in_pkts) {
        this.in_pkts = in_pkts;
    }

    public long getOut_pkts() {
        return out_pkts;
    }

    public void setOut_pkts(long out_pkts) {
        this.out_pkts = out_pkts;
    }

    public long getC2s_pkts() {
        return c2s_pkts;
    }

    public void setC2s_pkts(long c2s_pkts) {
        this.c2s_pkts = c2s_pkts;
    }

    public long getS2c_pkts() {
        return s2c_pkts;
    }

    public void setS2c_pkts(long s2c_pkts) {
        this.s2c_pkts = s2c_pkts;
    }

    public long getC2s_bytes() {
        return c2s_bytes;
    }

    public void setC2s_bytes(long c2s_bytes) {
        this.c2s_bytes = c2s_bytes;
    }

    public long getS2c_bytes() {
        return s2c_bytes;
    }

    public void setS2c_bytes(long s2c_bytes) {
        this.s2c_bytes = s2c_bytes;
    }

    public long getC2s_fragments() {
        return c2s_fragments;
    }

    public void setC2s_fragments(long c2s_fragments) {
        this.c2s_fragments = c2s_fragments;
    }

    public long getS2c_fragments() {
        return s2c_fragments;
    }

    public void setS2c_fragments(long s2c_fragments) {
        this.s2c_fragments = s2c_fragments;
    }

    public long getC2s_tcp_lost_bytes() {
        return c2s_tcp_lost_bytes;
    }

    public void setC2s_tcp_lost_bytes(long c2s_tcp_lost_bytes) {
        this.c2s_tcp_lost_bytes = c2s_tcp_lost_bytes;
    }

    public long getS2c_tcp_lost_bytes() {
        return s2c_tcp_lost_bytes;
    }

    public void setS2c_tcp_lost_bytes(long s2c_tcp_lost_bytes) {
        this.s2c_tcp_lost_bytes = s2c_tcp_lost_bytes;
    }

    public long getC2s_tcp_ooorder_pkts() {
        return c2s_tcp_ooorder_pkts;
    }

    public void setC2s_tcp_ooorder_pkts(long c2s_tcp_ooorder_pkts) {
        this.c2s_tcp_ooorder_pkts = c2s_tcp_ooorder_pkts;
    }

    public long getS2c_tcp_ooorder_pkts() {
        return s2c_tcp_ooorder_pkts;
    }

    public void setS2c_tcp_ooorder_pkts(long s2c_tcp_ooorder_pkts) {
        this.s2c_tcp_ooorder_pkts = s2c_tcp_ooorder_pkts;
    }

    public long getC2s_tcp_retransmitted_pkts() {
        return c2s_tcp_retransmitted_pkts;
    }

    public void setC2s_tcp_retransmitted_pkts(long c2s_tcp_retransmitted_pkts) {
        this.c2s_tcp_retransmitted_pkts = c2s_tcp_retransmitted_pkts;
    }

    public long getS2c_tcp_retransmitted_pkts() {
        return s2c_tcp_retransmitted_pkts;
    }

    public void setS2c_tcp_retransmitted_pkts(long s2c_tcp_retransmitted_pkts) {
        this.s2c_tcp_retransmitted_pkts = s2c_tcp_retransmitted_pkts;
    }

    public long getC2s_tcp_retransmitted_bytes() {
        return c2s_tcp_retransmitted_bytes;
    }

    public void setC2s_tcp_retransmitted_bytes(long c2s_tcp_retransmitted_bytes) {
        this.c2s_tcp_retransmitted_bytes = c2s_tcp_retransmitted_bytes;
    }

    public long getS2c_tcp_retransmitted_bytes() {
        return s2c_tcp_retransmitted_bytes;
    }

    public void setS2c_tcp_retransmitted_bytes(long s2c_tcp_retransmitted_bytes) {
        this.s2c_tcp_retransmitted_bytes = s2c_tcp_retransmitted_bytes;
    }
}
