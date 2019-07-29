package com.primecloud.huafenghuang.ui.home.usercenterfragment.activity.group;

/**
 * Created by ${qc} on 2019/5/14.
 */

public class TeamBean {

//    teamNumber	团队总人数	是	[string]
//            4	usedCount	已用名额	是	[string]
//            5	unusedCount	未用名额	是	[string]
//            6	member	我的会员	是	[string]
//            7	partner	我的合伙人	是	[string]
//            8	branchOffice	我的分公司	是	[string]
//            9	indirectOffice	间接分公司	是	[string]

    private String teamNumber;
    private String usedCount;
    private String unusedCount;
    private String member;
    private String partner;
    private String branchOffice;
    private String indirectOffice;

    public String getTeamNumber() {
        return teamNumber;
    }

    public void setTeamNumber(String teamNumber) {
        this.teamNumber = teamNumber;
    }

    public String getUsedCount() {
        return usedCount;
    }

    public void setUsedCount(String usedCount) {
        this.usedCount = usedCount;
    }

    public String getUnusedCount() {
        return unusedCount;
    }

    public void setUnusedCount(String unusedCount) {
        this.unusedCount = unusedCount;
    }

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public String getBranchOffice() {
        return branchOffice;
    }

    public void setBranchOffice(String branchOffice) {
        this.branchOffice = branchOffice;
    }

    public String getIndirectOffice() {
        return indirectOffice;
    }

    public void setIndirectOffice(String indirectOffice) {
        this.indirectOffice = indirectOffice;
    }

    @Override
    public String toString() {
        return "TeamBean{" +
                "teamNumber='" + teamNumber + '\'' +
                ", usedCount='" + usedCount + '\'' +
                ", unusedCount='" + unusedCount + '\'' +
                ", member='" + member + '\'' +
                ", partner='" + partner + '\'' +
                ", branchOffice='" + branchOffice + '\'' +
                ", indirectOffice='" + indirectOffice + '\'' +
                '}';
    }

}
