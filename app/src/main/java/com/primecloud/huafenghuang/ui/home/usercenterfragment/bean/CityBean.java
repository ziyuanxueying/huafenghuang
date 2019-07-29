package com.primecloud.huafenghuang.ui.home.usercenterfragment.bean;

import com.bigkoo.pickerview.model.IPickerViewData;

import java.util.List;

/**
 * Created by ${qc} on 2019/5/9.
 */

public class CityBean implements IPickerViewData {


    /**
     * ProvinceID : 110000
     * ProvinceName : 北京市
     * Citys : [{"CityID":"110100","CityName":"北京市"}]
     */

    private String ProvinceID;
    private String ProvinceName;
    private List<CitysBean> Citys;

    public String getProvinceID() {
        return ProvinceID;
    }

    public void setProvinceID(String ProvinceID) {
        this.ProvinceID = ProvinceID;
    }

    public String getProvinceName() {
        return ProvinceName;
    }

    public void setProvinceName(String ProvinceName) {
        this.ProvinceName = ProvinceName;
    }

    public List<CitysBean> getCitys() {
        return Citys;
    }

    public void setCitys(List<CitysBean> Citys) {
        this.Citys = Citys;
    }

    @Override
    public String getPickerViewText() {
        return ProvinceName;
    }

    public static class CitysBean {
        /**
         * CityID : 110100
         * CityName : 北京市
         */

        private String CityID;
        private String CityName;

        public String getCityID() {
            return CityID;
        }

        public void setCityID(String CityID) {
            this.CityID = CityID;
        }

        public String getCityName() {
            return CityName;
        }

        public void setCityName(String CityName) {
            this.CityName = CityName;
        }
    }
}
