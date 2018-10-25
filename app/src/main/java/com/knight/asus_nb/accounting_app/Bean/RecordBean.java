package com.knight.asus_nb.accounting_app.Bean;

public class RecordBean {
    private int id;
    //唯一标识
    private String uuid;
    //消费类型
    private String type;
    //消费金额
    private Double amount;
    //账单类型
    private Integer record_type;
    //备注信息
    private String remark;
    //日期
    private String day;
    //时间
    private String time;


//    ------------------------------------


    public Integer getRecord_type() {
        return record_type;
    }

    public void setRecord_type(Integer record_type) {
        this.record_type = record_type;
    }

    public int getId() {
        return id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setId(int id) {
        this.id = id;
    }
}
