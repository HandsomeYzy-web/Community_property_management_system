package MainFunction;

public class OwnerMaintenance extends Owner{
    private String item;
    private String time;
    public OwnerMaintenance (String name, String roomNum, String phoneNum, String item,String time){
        super(name, roomNum, phoneNum);
        this.item=item;
        this.time=time;
    }

    public OwnerMaintenance(String roomNUm,String item,String time){
        super(roomNUm);
        this.item=item;
        this.time=time;
    }
    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return  "房号："+super.getRoomNum()+"\r\n"+
                "报修项目："+item+"\r\n"+
                "预计上门时间："+time+"\r\n";
    }
}
