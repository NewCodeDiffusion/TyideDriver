package com.codediffusion.tyidedriver.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class modelRegistered {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private Data data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {

        @SerializedName("driver_id")
        @Expose
        private String driverId;
        @SerializedName("form_type")
        @Expose
        private String formType;

        public String getDriverId() {
            return driverId;
        }

        public void setDriverId(String driverId) {
            this.driverId = driverId;
        }

        public String getFormType() {
            return formType;
        }

        public void setFormType(String formType) {
            this.formType = formType;
        }

    }


}
