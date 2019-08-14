package com.example.gestex;

public class etudiant extends UserId {

    private String name;
    private String nickname;
    private String salle;
    private Boolean present;

    public etudiant(){

    }

    public etudiant(String name, String nickname, String salle) {
        this.name = name;
        this.nickname = nickname;
        this.salle = salle;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSalle() {
        return salle;
    }

    public void setSalle(String salle) {
        this.salle = salle;
    }

    public Boolean getPresent() {
        return present;
    }

    public void setPresent(Boolean present) {
        this.present = present;
    }
}
