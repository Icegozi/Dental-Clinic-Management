/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mynewteeth.backend.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Us
 */
public class TaiKhoan {
    private String accountName;
    private String password;
    private Date createdDay;
    private int state;
    
    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreatedDay() {
        return createdDay;
    }

    public void setCreatedDay(Date createdDay) {
        this.createdDay = createdDay;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
    
    public TaiKhoan() {
        
    }

    public TaiKhoan(String accountName, String password, Date createDay, int state) {
        this.accountName = accountName;
        this.password = password;
        this.createdDay = createDay;
        this.state = state;
    }

    @Override
    public String toString() {
        return null;
    }
    
    public String[] getFragmentInfo() {
        String accState = (state == 1) ? "Hoạt động" : "Bị chặn";
        return new String[]{accountName, password, dateFormat.format(createdDay), accState};
    }
    
    public String convertToString() {
        return accountName + "#" + password + "#" + dateFormat.format(createdDay) + "#" + String.valueOf(state);
    }
}
