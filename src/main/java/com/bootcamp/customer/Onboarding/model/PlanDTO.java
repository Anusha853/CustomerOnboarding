package com.bootcamp.customer.Onboarding.model;

    public class PlanDTO {

        private String planName;
        private String planDescription;
        private double price;
        private int validityDays;
        private boolean activation;



        public boolean isActivation() {
            return activation;
        }

        public void setActivation(boolean activation) {
            this.activation = activation;
        }


        public PlanDTO(String planName, String planDescription, double price, int validityDays,boolean activation) {
            this.planName = planName;
            this.planDescription = planDescription;
            this.price = price;
            this.validityDays = validityDays;
            this.activation=activation;
        }

    }

