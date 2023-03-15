package MainFunction;

public class Owner {
    private String name;
    private String roomNum;
    private String phoneNum;

    public Owner() {
    }

    public Owner(String name, String roomNum, String phoneNum) {
        this.name = name;
        this.roomNum = roomNum;
        this.phoneNum = phoneNum;
    }

    public Owner(String roomNum){
        this.roomNum=roomNum;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(String roomNum) {
        this.roomNum = roomNum;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    @Override
    public String toString() {
        return  "房主："+name+"\r\n" +
                "房号："+roomNum+"\r\n" +
                "电话："+phoneNum;
    }
}
