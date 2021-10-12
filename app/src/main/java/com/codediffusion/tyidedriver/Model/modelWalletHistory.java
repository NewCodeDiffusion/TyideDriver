package com.codediffusion.tyidedriver.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class modelWalletHistory {

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
    public class WalletHistory {

        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("old_amount")
        @Expose
        private String oldAmount;
        @SerializedName("amount")
        @Expose
        private String amount;
        @SerializedName("new_amount")
        @Expose
        private Integer newAmount;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("add_date")
        @Expose
        private String addDate;
        @SerializedName("add_time")
        @Expose
        private String addTime;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getOldAmount() {
            return oldAmount;
        }

        public void setOldAmount(String oldAmount) {
            this.oldAmount = oldAmount;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public Integer getNewAmount() {
            return newAmount;
        }

        public void setNewAmount(Integer newAmount) {
            this.newAmount = newAmount;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getAddDate() {
            return addDate;
        }

        public void setAddDate(String addDate) {
            this.addDate = addDate;
        }

        public String getAddTime() {
            return addTime;
        }

        public void setAddTime(String addTime) {
            this.addTime = addTime;
        }

    }

    public class Data {

        @SerializedName("wallet_history_list")
        @Expose
        private List<WalletHistory> walletHistoryList = null;

        public List<WalletHistory> getWalletHistoryList() {
            return walletHistoryList;
        }

        public void setWalletHistoryList(List<WalletHistory> walletHistoryList) {
            this.walletHistoryList = walletHistoryList;
        }

    }


}
