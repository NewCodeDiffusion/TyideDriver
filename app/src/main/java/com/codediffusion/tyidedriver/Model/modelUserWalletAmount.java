package com.codediffusion.tyidedriver.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class modelUserWalletAmount {
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

        @SerializedName("total_wallet_amount")
        @Expose
        private String totalWalletAmount;

        public String getTotalWalletAmount() {
            return totalWalletAmount;
        }

        public void setTotalWalletAmount(String totalWalletAmount) {
            this.totalWalletAmount = totalWalletAmount;
        }

    }
}
