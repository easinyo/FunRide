package com.dubinostech.rideshareapp.data;

public class ApIError {


        private int status_code;
        private String message;


        public ApIError() {
        }

        public int getStatus_code() {
            return status_code;
        }

        public void setStatus_code(int status_code) {
            this.status_code = status_code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }


