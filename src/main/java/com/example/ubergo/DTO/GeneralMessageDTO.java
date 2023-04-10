package com.example.ubergo.DTO;

public class GeneralMessageDTO {
    private int status;


    private String msg;



    private Object data;
    public GeneralMessageDTO(int status,String msg) {
        this.status = status;
        this.msg=msg;
    }
    public GeneralMessageDTO(int status,Object data) {
        this.status = status;
        this.data=data;
    }
    public GeneralMessageDTO(int status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }


}
