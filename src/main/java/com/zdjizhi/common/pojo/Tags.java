package com.zdjizhi.common.pojo;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author qidaijie
 * @Package com.zdjizhi.common.pojo
 * @Description:
 * @date 2023/4/2311:48
 */
public class Tags {
    private int vsys_id;
    private String device_id;
    private String device_group;
    private String data_center;
    private String protocol_stack_id;
    private String app_name;

    public Tags(int vsys_id, String device_id, String device_group, String data_center, String protocol_stack_id, String app_name) {
        this.vsys_id = vsys_id;
        this.device_id = device_id;
        this.device_group = device_group;
        this.data_center = data_center;
        this.protocol_stack_id = protocol_stack_id;
        this.app_name = app_name;
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

    @JSONField(name = "decoded_path")
    public void setProtocol_stack_id(String protocol_stack_id) {
        this.protocol_stack_id = protocol_stack_id;
    }

    public String getApp_name() {
        return app_name;
    }

    @JSONField(name = "app")
    public void setApp_name(String app_name) {
        this.app_name = app_name;
    }

    @Override
    public String toString() {
        return vsys_id + device_id + device_group + data_center + protocol_stack_id + app_name;
    }
}
