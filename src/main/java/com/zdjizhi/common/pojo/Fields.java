package com.zdjizhi.common.pojo;

/**
 * @author qidaijie
 * @Package com.zdjizhi.common.pojo
 * @Description:
 * @date 2023/4/2311:47
 */
public class Fields {
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
    private byte[] client_ip_sketch;

    public Fields(Long sessions, Long in_bytes, Long out_bytes, Long in_pkts, Long out_pkts, Long c2s_pkts, Long s2c_pkts, Long c2s_bytes, Long s2c_bytes, Long c2s_fragments, Long s2c_fragments, Long c2s_tcp_lost_bytes, Long s2c_tcp_lost_bytes, Long c2s_tcp_ooorder_pkts, Long s2c_tcp_ooorder_pkts, Long c2s_tcp_retransmitted_pkts, Long s2c_tcp_retransmitted_pkts, Long c2s_tcp_retransmitted_bytes, Long s2c_tcp_retransmitted_bytes, byte[] client_ip_sketch) {
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
        this.client_ip_sketch = client_ip_sketch;
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

    public byte[] getClient_ip_sketch() {
        return client_ip_sketch;
    }

    public void setClient_ip_sketch(byte[] client_ip_sketch) {
        this.client_ip_sketch = client_ip_sketch;
    }
}
