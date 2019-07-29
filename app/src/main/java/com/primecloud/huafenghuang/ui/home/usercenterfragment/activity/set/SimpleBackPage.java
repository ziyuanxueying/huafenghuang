package com.primecloud.huafenghuang.ui.home.usercenterfragment.activity.set;


import com.primecloud.huafenghuang.R;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.activity.set.fragment.AboutFragment;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.activity.set.fragment.ChangePasswordFragment;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.activity.set.fragment.ChangePhone1Fragment;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.activity.set.fragment.ChangePhone2Fragment;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.activity.set.fragment.InvitationFragment;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.activity.set.fragment.NickFragment;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.activity.set.fragment.SafeFragment;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.activity.set.fragment.SuggestFragment;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.activity.set.fragment.XieYiFragment;

public enum SimpleBackPage {

	Change_Safe(1, R.mipmap.ic_launcher, SafeFragment.class), //账号与安全

	Change_About(2, R.mipmap.ic_launcher, AboutFragment.class), //关于我们

	Change_Phone1(3, R.mipmap.ic_launcher, ChangePhone1Fragment.class), //修改手机号1

	Change_Phone2(4,R.mipmap.ic_launcher, ChangePhone2Fragment.class),//修改手机号2

	Change_Passwprd(5, R.mipmap.ic_launcher, ChangePasswordFragment.class), //修改密码

	Change_Invitave(6, R.mipmap.ic_launcher, InvitationFragment.class), //邀请好友

	Change_XieYi(7,R.mipmap.ic_launcher, XieYiFragment.class), //协议

	Change_Nick(8,R.mipmap.ic_launcher, NickFragment.class), //修改昵称

	Change_Suggest(9,R.mipmap.ic_launcher, SuggestFragment.class);//建议反馈


	private int title;
	private Class<?> clz;
	private int value;

	private SimpleBackPage(int value, int title, Class<?> clz) {
		this.value = value;
		this.title = title;
		this.clz = clz;
	}

	public int getTitle() {
		return title;
	}

	public void setTitle(int title) {
		this.title = title;
	}

	public Class<?> getClz() {
		return clz;
	}

	public void setClz(Class<?> clz) {
		this.clz = clz;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public static SimpleBackPage getPageByValue(int val) {
		for (SimpleBackPage p : values()) {
			if (p.getValue() == val)
				return p;
		}
		return null;
	}

}

