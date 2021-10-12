package com.codediffusion.tyidedriver.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class modelNewBooking {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;

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

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public class Datum {

        @SerializedName("booking_table_id")
        @Expose
        private String bookingTableId;
        @SerializedName("user_name")
        @Expose
        private String userName;
        @SerializedName("user_img")
        @Expose
        private String userImg;
        @SerializedName("car_name")
        @Expose
        private String carName;
        @SerializedName("car_type")
        @Expose
        private String carType;
        @SerializedName("trip_type")
        @Expose
        private String tripType;
        @SerializedName("trip_for")
        @Expose
        private String tripFor;
        @SerializedName("departure_date")
        @Expose
        private String departureDate;
        @SerializedName("departure_time")
        @Expose
        private String departureTime;
        @SerializedName("return_date")
        @Expose
        private String returnDate;
        @SerializedName("from_place")
        @Expose
        private String fromPlace;
        @SerializedName("to_place")
        @Expose
        private String toPlace;
        @SerializedName("running_distance")
        @Expose
        private String runningDistance;
        @SerializedName("number_of_days")
        @Expose
        private String numberOfDays;
        @SerializedName("booking_status")
        @Expose
        private String bookingStatus;
        @SerializedName("package_name")
        @Expose
        private String packageName;
        @SerializedName("note")
        @Expose
        private String note;
        @SerializedName("booking_amount")
        @Expose
        private String bookingAmount;
        @SerializedName("company_commission")
        @Expose
        private String companyCommission;
        @SerializedName("your_price")
        @Expose
        private String yourPrice;

        public String getBookingTableId() {
            return bookingTableId;
        }

        public void setBookingTableId(String bookingTableId) {
            this.bookingTableId = bookingTableId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserImg() {
            return userImg;
        }

        public void setUserImg(String userImg) {
            this.userImg = userImg;
        }

        public String getCarName() {
            return carName;
        }

        public void setCarName(String carName) {
            this.carName = carName;
        }

        public String getCarType() {
            return carType;
        }

        public void setCarType(String carType) {
            this.carType = carType;
        }

        public String getTripType() {
            return tripType;
        }

        public void setTripType(String tripType) {
            this.tripType = tripType;
        }

        public String getTripFor() {
            return tripFor;
        }

        public void setTripFor(String tripFor) {
            this.tripFor = tripFor;
        }

        public String getDepartureDate() {
            return departureDate;
        }

        public void setDepartureDate(String departureDate) {
            this.departureDate = departureDate;
        }

        public String getDepartureTime() {
            return departureTime;
        }

        public void setDepartureTime(String departureTime) {
            this.departureTime = departureTime;
        }

        public String getReturnDate() {
            return returnDate;
        }

        public void setReturnDate(String returnDate) {
            this.returnDate = returnDate;
        }

        public String getFromPlace() {
            return fromPlace;
        }

        public void setFromPlace(String fromPlace) {
            this.fromPlace = fromPlace;
        }

        public String getToPlace() {
            return toPlace;
        }

        public void setToPlace(String toPlace) {
            this.toPlace = toPlace;
        }

        public String getRunningDistance() {
            return runningDistance;
        }

        public void setRunningDistance(String runningDistance) {
            this.runningDistance = runningDistance;
        }

        public String getNumberOfDays() {
            return numberOfDays;
        }

        public void setNumberOfDays(String numberOfDays) {
            this.numberOfDays = numberOfDays;
        }

        public String getBookingStatus() {
            return bookingStatus;
        }

        public void setBookingStatus(String bookingStatus) {
            this.bookingStatus = bookingStatus;
        }

        public String getPackageName() {
            return packageName;
        }

        public void setPackageName(String packageName) {
            this.packageName = packageName;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        public String getBookingAmount() {
            return bookingAmount;
        }

        public void setBookingAmount(String bookingAmount) {
            this.bookingAmount = bookingAmount;
        }

        public String getCompanyCommission() {
            return companyCommission;
        }

        public void setCompanyCommission(String companyCommission) {
            this.companyCommission = companyCommission;
        }

        public String getYourPrice() {
            return yourPrice;
        }

        public void setYourPrice(String yourPrice) {
            this.yourPrice = yourPrice;
        }

    }



}
