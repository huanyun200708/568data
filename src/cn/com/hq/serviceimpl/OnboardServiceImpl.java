package cn.com.hq.serviceimpl;

import java.util.List;

import cn.com.hq.dao.OnboardDao;
import cn.com.hq.daoImpl.OnboardDaoImpl;
import cn.com.hq.entity.OnboardInfo;
import cn.com.hq.service.OnboardService;
import cn.com.hq.vo.OnboardInfoVO;

public class OnboardServiceImpl implements OnboardService {

	private OnboardDao dao = new OnboardDaoImpl();
	
	@Override
	public void addOnboardInfo(OnboardInfo b) {
		dao.addOnboardInfo(b);
	}

	@Override
	public List<OnboardInfoVO> queryAllOnboardInfo() {
		return dao.queryAllOnboardInfo("");
	}

	@Override
	public int modifyOnboardInfo(OnboardInfo b) {
		return dao.modifyOnboardInfo(b);
	}

	@Override
	public List<OnboardInfoVO> queryAllOnboardInfoById(String id) {
		return dao.queryAllOnboardInfo(id);
	}

}
