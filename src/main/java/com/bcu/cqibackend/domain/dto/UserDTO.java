package com.bcu.cqibackend.domain.dto;

public class UserDTO {
    private String userName;

    private int Blocker;
    private int Critical;
    private int Major;
    private int Minor;
    private int Info;

    private int linesOfCode;

    private int technicalDebt;

    public UserDTO(){

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getBlocker() {
        return Blocker;
    }

    public void setBlocker(int blocker) {
        Blocker = blocker;
    }

    public int getCritical() {
        return Critical;
    }

    public void setCritical(int critical) {
        Critical = critical;
    }

    public int getMajor() {
        return Major;
    }

    public void setMajor(int major) {
        Major = major;
    }

    public int getMinor() {
        return Minor;
    }

    public void setMinor(int minor) {
        Minor = minor;
    }

    public int getInfo() {
        return Info;
    }

    public void setInfo(int info) {
        Info = info;
    }

    public int getLinesOfCode() {
        return linesOfCode;
    }

    public void setLinesOfCode(int linesOfCode) {
        this.linesOfCode = linesOfCode;
    }

    public int getTechnicalDebt() {
        return technicalDebt;
    }

    public void setTechnicalDebt(int technicalDebt) {
        this.technicalDebt = technicalDebt;
    }
}
