package net.cong.ANManagement;

public class ACMachinery {
    //装备类型
    private String MachineryType;
    //生产厂家
    private String Manufacturer;
    //序列号
    private String SerialNumber;
    //主要使用区域
    private String MajorAreas;
    //购买者姓名
    private String BuyerNmae;
    //购买者联系方式
    private String BuyerPhone;

    public String getMachineryType() {
        return MachineryType;
    }

    public void setMachineryType(String machineryType) {
        MachineryType = machineryType;
    }

    public String getManufacturer() {
        return Manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        Manufacturer = manufacturer;
    }

    public void setSerialNumber(String serialNumber) {
        SerialNumber = serialNumber;
    }

    public String getSerialNumber() {
        return SerialNumber;
    }

    public void setMajorAreas(String majorAreas) {
        MajorAreas = majorAreas;
    }

    public String getMajorAreas() {
        return MajorAreas;
    }

    public void setBuyerNmae(String buyerNmae) {
        BuyerNmae = buyerNmae;
    }

    public String getBuyerNmae() {
        return BuyerNmae;
    }

    public void setBuyerPhone(String buyerPhone) {
        BuyerPhone = buyerPhone;
    }

    public String getBuyerPhone() {
        return BuyerPhone;
    }
}
