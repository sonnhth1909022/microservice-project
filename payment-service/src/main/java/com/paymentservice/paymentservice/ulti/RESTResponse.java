package com.paymentservice.paymentservice.ulti;


import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class RESTResponse {
    private HashMap<String, Object> response;

    // MUST be private.
    private RESTResponse() {
        this.response = new HashMap<>();
    }

    public HashMap<String, Object> getResponse() {
        return response;
    }

    public void setResponse(HashMap<String, Object> response) {
        this.response = response;
    }

    public void addResponse(String key, Object value) {
        this.response.put(key, value);
    }


    public static class SuccessNoData {

        private int status;
        private String message;
//        private RESTPagination pagination;

        public SuccessNoData() {
            this.status = 200;
            this.message = "Success!";
        }



        public SuccessNoData setStatus(int status) {
            this.status = status;
            return this;
        }

        public SuccessNoData setMessage(String message) {
            this.message = message;
            return this;
        }

//        public Success setPagination(RESTPagination pagination) {
//            this.pagination = pagination;
//            return this;
//        }

        public HashMap<String, Object> build() {
            RESTResponse restResponse = new RESTResponse();
            restResponse.addResponse("status", this.status);
            restResponse.addResponse("message", this.message);
//            if (this.pagination != null) {
//                restResponse.addResponse("pagination", this.pagination);
//            }
            return restResponse.getResponse();
        }

        public HashMap<String, Object> buildDatas() {
            RESTResponse restResponse = new RESTResponse();
            restResponse.addResponse("status", this.status);
            restResponse.addResponse("message", this.message);
//            if (this.pagination != null) {
//                restResponse.addResponse("pagination", this.pagination);
//            }
            return restResponse.getResponse();
        }


    }

        public static class Error {

        private HashMap<String, String> errors;
        private int status;
        private String message;
        private String detail;

        public Error() {
            this.errors = new HashMap<>();
            this.status = 0;
            this.message = "";
            this.detail = "";
        }

            public int getStatus() {
                return status;
            }

            public String getMessage() {
                return message;
            }

            public String getDetail() {
                return detail;
            }

            public Error setStatus(int status) {
            this.status = status;
            return this;
        }

            public Error setDetail(String detail) {
                this.detail = detail;
                return this;
            }

            public Error setMessage(String message) {
            this.message = message;
            return this;
        }

        public Error addError(String key, String value) {
            this.errors.put(key, value);
            return this;
        }

        public Error addErrors(HashMap<String, String> errors) {
            this.errors.putAll(errors);
            return this;
        }

        public HashMap<String, Object> build() {
            RESTResponse restResponse = new RESTResponse();
            restResponse.addResponse("status", this.status);
            restResponse.addResponse("message", this.message);
            restResponse.addResponse("detail",this.detail);
            String errorKey = "error";
            if (this.errors.size() > 1) {
                errorKey = "errors";
            }
            restResponse.addResponse(errorKey, this.errors);
            return restResponse.getResponse();
        }

        public Error getDataError()
        {
            return new Error()
                    .setStatus(HttpStatus.NOT_FOUND.value())
                    .setMessage("Lấy dữ liêu thất bại");

        }

        public Error saveDataError()
        {
            return new Error()
                    .setStatus(HttpStatus.NOT_FOUND.value())
                    .setMessage("Thêm mới dữ liệu thất bại ");
        }

        public Error checkErrorWithMessage(String message)
        {
            return new Error()
                    .setStatus(HttpStatus.NOT_FOUND.value())
                    .setMessage(message);
        }

        public Error badRequestWithMessage(String message)
        {
            return new Error()
                     .setStatus(HttpStatus.BAD_REQUEST.value())
                     .setMessage(message);
        }
    }

    public static class SimpleError {

        private int code;
        private String message;

        public SimpleError() {
            this.code = 0;
            this.message = "";
        }

        public SimpleError setCode(int code) {
            this.code = code;
            return this;
        }

        public SimpleError setMessage(String message) {
            this.message = message;
            return this;
        }

        public HashMap<String, Object> build() {
            RESTResponse restResponse = new RESTResponse();
            restResponse.addResponse("status", this.code);
            restResponse.addResponse("message", this.message);
            return restResponse.getResponse();
        }
    }


    public static class Success {

        private int status;
        private String message;
        private List<Object> datas;
        private List<Object> data;
        private RESTPagination pagination;
        private Date timestamp;

        public Success() {
            this.status = 1;
            this.message = "Success!";
            this.datas = new ArrayList<>();
            this.data = new ArrayList<>();
        }



        public Success setStatus(int status) {
            this.status = status;
            return this;
        }

        public Success setMessage(String message) {
            this.message = message;
            return this;
        }

        public Success setPagination(RESTPagination pagination) {
            this.pagination = pagination;
            return this;
        }

        public Success addData(Object obj) {
            this.datas.add(obj);
            return this;
        }

        public Success addDatas(List listObj) {
            this.datas.addAll(listObj);
            return this;
        }

        public Success setTimestamp(Date date)
        {
            this.timestamp = date;
            return this;
        }

        public HashMap<String, Object> build() {
            RESTResponse restResponse = new RESTResponse();
            restResponse.addResponse("status", this.status);
            restResponse.addResponse("message", this.message);
            restResponse.addResponse("timestamp",this.timestamp);

            if (this.datas.size() == 1) {
                restResponse.addResponse("data", this.datas.get(0));

            } else {
                restResponse.addResponse("datas", this.datas);
            }
            if (this.pagination != null) {
                restResponse.addResponse("pagination", this.pagination);
            }
            return restResponse.getResponse();
        }

        public HashMap<String, Object> buildDatas() {
            RESTResponse restResponse = new RESTResponse();
            restResponse.addResponse("status", this.status);
            restResponse.addResponse("message", this.message);
            restResponse.addResponse("datas", this.datas);
            if (this.pagination != null) {
                restResponse.addResponse("pagination", this.pagination);
            }
            return restResponse.getResponse();
        }


    }

    //them moi thanh cong
    public static HashMap<String, Object> success(Object ob, String message)
    {
        HashMap<String, Object> restResponse = new Success()
                .setStatus(HttpStatus.OK.value())
                .setTimestamp(new Date())
                .setMessage(message)
                .addData(ob)
                .build();
        return restResponse;
    }




}
