package com.samkecy.spim;

public class ListItems {


        private String Contactname, contactnum, thumbnail;

        public ListItems(String name, String number, String imageuri) {
            this.Contactname = name;
            this.contactnum = number;
            this.thumbnail = imageuri;
        }

        public String getContactname() {
            return Contactname;
        }

        public void setContactname(String contactname) {
            Contactname = contactname;
        }

        public String getContactnum() {
            return contactnum;
        }

        public void setContactnum(String contactnum) {
            this.contactnum = contactnum;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

    }

