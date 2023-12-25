package com.zdjizhi.common.pojo;

/**
 * @author qidaijie
 * @Package com.zdjizhi.common.pojo
 * @Description:
 * @date 2023/4/2311:47
 */
public class Fields {
    private long sessions;
    private long in_bytes;
    private long out_bytes;
    private long in_pkts;
    private long out_pkts;
    private long c2s_pkts;
    private long s2c_pkts;
    private long c2s_bytes;
    private long s2c_bytes;
    private long c2s_fragments;
    private long s2c_fragments;
    private long c2s_tcp_lost_bytes;
    private long s2c_tcp_lost_bytes;
    private long c2s_tcp_ooorder_pkts;
    private long s2c_tcp_ooorder_pkts;
    private long c2s_tcp_retransmitted_pkts;
    private long s2c_tcp_retransmitted_pkts;
    private long c2s_tcp_retransmitted_bytes;
    private long s2c_tcp_retransmitted_bytes;

    public Fields(long sessions, long in_bytes, long out_bytes, long in_pkts, long out_pkts, long c2s_pkts, long s2c_pkts, long c2s_bytes, long s2c_bytes, long c2s_fragments, long s2c_fragments, long c2s_tcp_lost_bytes, long s2c_tcp_lost_bytes, long c2s_tcp_ooorder_pkts, long s2c_tcp_ooorder_pkts, long c2s_tcp_retransmitted_pkts, long s2c_tcp_retransmitted_pkts, long c2s_tcp_retransmitted_bytes, long s2c_tcp_retransmitted_bytes) {
        this.sessions = sessions;
        this.in_bytes = in_bytes;
        this.out_bytes = out_bytes;
        this.in_pkts = in_pkts;
        this.out_pkts = out_pkts;
        this.c2s_pkts = c2s_pkts;
        this.s2c_pkts = s2c_pkts;
        this.c2s_bytes = c2s_bytes;
        this.s2c_bytes = s2c_bytes;
        this.c2s_fragments = c2s_fragments;
        this.s2c_fragments = s2c_fragments;
        this.c2s_tcp_lost_bytes = c2s_tcp_lost_bytes;
        this.s2c_tcp_lost_bytes = s2c_tcp_lost_bytes;
        this.c2s_tcp_ooorder_pkts = c2s_tcp_ooorder_pkts;
        this.s2c_tcp_ooorder_pkts = s2c_tcp_ooorder_pkts;
        this.c2s_tcp_retransmitted_pkts = c2s_tcp_retransmitted_pkts;
        this.s2c_tcp_retransmitted_pkts = s2c_tcp_retransmitted_pkts;
        this.c2s_tcp_retransmitted_bytes = c2s_tcp_retransmitted_bytes;
        this.s2c_tcp_retransmitted_bytes = s2c_tcp_retransmitted_bytes;
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
