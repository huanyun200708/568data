package cn.com.hq.dao;

import java.util.List;

import cn.com.hq.entity.OnboardInfo;
import cn.com.hq.vo.OnboardInfoVO;

public interface OnboardDao {
	public void addOnboardInfo(OnboardInfo b);

	public List<OnboardInfoVO> queryAllOnboardInfo(String infoid);

	public int modifyOnboardInfo(OnboardInfo b);
}
