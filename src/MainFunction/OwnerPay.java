package MainFunction;

public class OwnerPay extends Owner{
    private double arrears;
    private String arrearsProject;
    public OwnerPay (String name, String roomNum, String phoneNum , double arrears, String arrearsProject){
        super(name, roomNum, phoneNum);
        this.arrears=arrears;
        this.arrearsProject=arrearsProject;
    }

    @Override
    public String toString() {
        return "房主："+this.getRoomNum()+"\r\n" +
                "欠费金额："+arrears+"\r\n"+
                "欠费项目："+arrearsProject;
    }
}
