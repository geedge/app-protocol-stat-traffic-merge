package com.zdjizhi.common.pojo;

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
    private String protocol_label;
    private String app_full_path;

    public Tags(int vsys_id, String device_id, String device_group, String data_center, String protocol_label, String app_full_path) {
        this.vsys_id = vsys_id;
        this.device_id = device_id;
        this.device_group = device_group;
        this.data_center = data_center;
        this.protocol_label = protocol_label;
        this.app_full_path = app_full_path;
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

    public String getProtocol_label() {
        return protocol_label;
    }

    public void setProtocol_label(String protocol_label) {
        this.protocol_label = protocol_label;
    }

    public String getApp_full_path() {
        return app_full_path;
    }

    public void setApp_full_path(String app_full_path) {
        this.app_full_path = app_full_path;
    }
}
