package com.example.loginpanelwithdatabase;

public class UserModel {

    private int ID_COL ;
    private String LOGIN_COL;
    private String PASSWORD_COL;
    private String ISADMIN_COL;

    public UserModel(String LOGIN_COL, String PASSWORD_COL, String ISADMIN_COL) {
        this.LOGIN_COL = LOGIN_COL;
        this.PASSWORD_COL = PASSWORD_COL;
        this.ISADMIN_COL = ISADMIN_COL;
    }


    public int getID_COL() {
        return ID_COL;
    }

    public void setID_COL(int ID_COL) {
        this.ID_COL = ID_COL;
    }

    public String getLOGIN_COL() {
        return LOGIN_COL;
    }

    public void setLOGIN_COL(String LOGIN_COL) {
        this.LOGIN_COL = LOGIN_COL;
    }

    public String getPASSWORD_COL() {
        return PASSWORD_COL;
    }

    public void setPASSWORD_COL(String PASSWORD_COL) {
        this.PASSWORD_COL = PASSWORD_COL;
    }

    public String getISADMIN_COL() {
        return ISADMIN_COL;
    }

    public void setISADMIN_COL(String ISADMIN_COL) {
        this.ISADMIN_COL = ISADMIN_COL;
    }
}
