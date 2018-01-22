package com.weixinpay.model;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import cn.com.hq.util.JsonUtils;
import cn.com.hq.util.PropertiesUtils;
import cn.com.hq.util.QueryAppKeyLib;
import cn.com.hq.util.StringUtil;

import com.chaboshi.util.CBS;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.weixinpay.service.PayService;

public class WXJL {

	private String Code;
	private String Message;
	private String modelName;
	private String seriesName;
	private String vin;
	private String makeReportDate;
	private String reportNo;
	private String manufacturer;
	private String makeDate;
	private String productionArea;
	private String carType;
	private String transmissionType;
	private String displacement;
	private String effluentStandard;
	private String carFireFlag;
	private String carWaterFlag;
	private String carComponentRecordsFlag;
	private String carConstructRecordsFlag;
	private String carOutsideRecordsFlag;
	private String mileageIsNormalFlag;
	private String mileageEstimate;
	private String lastMainTainTime;
	private String mainTainTimes;
	private String lastRepairTime;
	private String mileageEveryYear;
	private List<WXJLRecord> normalRepairRecords;
	private List<WXJLRecord> constructAnalyzeRepairRecords;
	private List<WXJLRecord> componentAnalyzeRepairRecords;
	private List<WXJLRecord> outsideAnalyzeRepairRecords;
	private String brand;
	private String score;
	private static PayService payService = new PayService();   
	private static Logger logger = Logger.getLogger(WXJL.class);
	public String getCode() {
		return Code;
	}

	public void setCode(String code) {
		Code = code;
	}

	public String getMessage() {
		return Message;
	}

	public void setMessage(String message) {
		Message = message;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getSeriesName() {
		return seriesName;
	}

	public void setSeriesName(String seriesName) {
		this.seriesName = seriesName;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public String getMakeReportDate() {
		return makeReportDate;
	}

	public void setMakeReportDate(String makeReportDate) {
		this.makeReportDate = makeReportDate;
	}

	public String getReportNo() {
		return reportNo;
	}

	public void setReportNo(String reportNo) {
		this.reportNo = reportNo;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getMakeDate() {
		return makeDate;
	}

	public void setMakeDate(String makeDate) {
		this.makeDate = makeDate;
	}

	public String getProductionArea() {
		return productionArea;
	}

	public void setProductionArea(String productionArea) {
		this.productionArea = productionArea;
	}

	public String getCarType() {
		return carType;
	}

	public void setCarType(String carType) {
		this.carType = carType;
	}

	public String getTransmissionType() {
		return transmissionType;
	}

	public void setTransmissionType(String transmissionType) {
		this.transmissionType = transmissionType;
	}

	public String getDisplacement() {
		return displacement;
	}

	public void setDisplacement(String displacement) {
		this.displacement = displacement;
	}

	public String getEffluentStandard() {
		return effluentStandard;
	}

	public void setEffluentStandard(String effluentStandard) {
		this.effluentStandard = effluentStandard;
	}

	public String getCarFireFlag() {
		if(!StringUtil.isEmpty(carFireFlag)){
			switch (carFireFlag) {
				case "0":carFireFlag = "否";break;
				default:carFireFlag = "是";break;
			}
		}
		return carFireFlag;
	}

	public void setCarFireFlag(String carFireFlag) {
		if(!StringUtil.isEmpty(carFireFlag)){
			switch (carFireFlag) {
				case "0":carFireFlag = "否";break;
				default:carFireFlag = "是";break;
			}
		}
		this.carFireFlag = carFireFlag;
	}

	public String getCarWaterFlag() {
		if(!StringUtil.isEmpty(carWaterFlag)){
			switch (carWaterFlag) {
				case "0":carWaterFlag = "否";break;
				default:carWaterFlag = "是";break;
			}
		}
		return carWaterFlag;
	}

	public void setCarWaterFlag(String carWaterFlag) {
		if(!StringUtil.isEmpty(carWaterFlag)){
			switch (carWaterFlag) {
				case "0":carWaterFlag = "否";break;
				default:carWaterFlag = "是";break;
			}
		}
		this.carWaterFlag = carWaterFlag;
	}

	public String getCarComponentRecordsFlag() {
		if(!StringUtil.isEmpty(carComponentRecordsFlag)){
			switch (carComponentRecordsFlag) {
				case "0":carComponentRecordsFlag = "否";break;
				default:carComponentRecordsFlag = "是";break;
			}
		}
		return carComponentRecordsFlag;
	}

	public void setCarComponentRecordsFlag(String carComponentRecordsFlag) {
		if(!StringUtil.isEmpty(carComponentRecordsFlag)){
			switch (carComponentRecordsFlag) {
				case "0":carComponentRecordsFlag = "否";break;
				default:carComponentRecordsFlag = "是";break;
			}
		}
		this.carComponentRecordsFlag = carComponentRecordsFlag;
	}

	public String getCarConstructRecordsFlag() {
		if(!StringUtil.isEmpty(carConstructRecordsFlag)){
			switch (carConstructRecordsFlag) {
				case "0":carConstructRecordsFlag = "否";break;
				default:carConstructRecordsFlag = "是";break;
			}
		}
		return carConstructRecordsFlag;
	}

	public void setCarConstructRecordsFlag(String carConstructRecordsFlag) {
		if(!StringUtil.isEmpty(carConstructRecordsFlag)){
			switch (carConstructRecordsFlag) {
				case "0":carConstructRecordsFlag = "否";break;
				default:carConstructRecordsFlag = "是";break;
			}
		}
		this.carConstructRecordsFlag = carConstructRecordsFlag;
	}

	public String getCarOutsideRecordsFlag() {
		if(!StringUtil.isEmpty(carOutsideRecordsFlag)){
			switch (carOutsideRecordsFlag) {
				case "0":carOutsideRecordsFlag = "否";break;
				default:carOutsideRecordsFlag = "是";break;
			}
		}
		return carOutsideRecordsFlag;
	}

	public void setCarOutsideRecordsFlag(String carOutsideRecordsFlag) {
		if(!StringUtil.isEmpty(carOutsideRecordsFlag)){
			switch (carOutsideRecordsFlag) {
				case "0":carOutsideRecordsFlag = "否";break;
				default:carOutsideRecordsFlag = "是";break;
			}
		}
		this.carOutsideRecordsFlag = carOutsideRecordsFlag;
	}

	public String getMileageIsNormalFlag() {
		if(!StringUtil.isEmpty(mileageIsNormalFlag)){
			switch (mileageIsNormalFlag) {
				case "0":mileageIsNormalFlag = "否";break;
				default:mileageIsNormalFlag = "是";break;
			}
		}
		return mileageIsNormalFlag;
	}

	public void setMileageIsNormalFlag(String mileageIsNormalFlag) {
		
		if(!StringUtil.isEmpty(mileageIsNormalFlag)){
			switch (mileageIsNormalFlag) {
				case "0":mileageIsNormalFlag = "否";break;
				default:mileageIsNormalFlag = "是";break;
			}
		}
		this.mileageIsNormalFlag = mileageIsNormalFlag;
	}

	public String getMileageEstimate() {
		if(!StringUtil.isEmpty(mileageEstimate)){
			switch (mileageEstimate) {
				case "0":mileageEstimate = "没有估出来";break;
				case "":mileageEstimate = "没有估出来";break;
				default:break;
			}
		}
		return mileageEstimate;
	}

	public void setMileageEstimate(String mileageEstimate) {
		if(!StringUtil.isEmpty(mileageEstimate)){
			switch (mileageEstimate) {
				case "0":mileageEstimate = "没有估出来";break;
				default:break;
			}
		}
		this.mileageEstimate = mileageEstimate;
	}

	public String getLastMainTainTime() {
		return lastMainTainTime;
	}

	public void setLastMainTainTime(String lastMainTainTime) {
		this.lastMainTainTime = lastMainTainTime;
	}

	public String getMainTainTimes() {
		return mainTainTimes;
	}

	public void setMainTainTimes(String mainTainTimes) {
		this.mainTainTimes = mainTainTimes;
	}

	public String getLastRepairTime() {
		return lastRepairTime;
	}

	public void setLastRepairTime(String lastRepairTime) {
		this.lastRepairTime = lastRepairTime;
	}

	public String getMileageEveryYear() {
		return mileageEveryYear;
	}

	public void setMileageEveryYear(String mileageEveryYear) {
		this.mileageEveryYear = mileageEveryYear;
	}



	public List<WXJLRecord> getNormalRepairRecords() {
		return normalRepairRecords;
	}

	public void setNormalRepairRecords(List<WXJLRecord> normalRepairRecords) {
		this.normalRepairRecords = normalRepairRecords;
	}

	public List<WXJLRecord> getConstructAnalyzeRepairRecords() {
		return constructAnalyzeRepairRecords;
	}

	public void setConstructAnalyzeRepairRecords(List<WXJLRecord> constructAnalyzeRepairRecords) {
		this.constructAnalyzeRepairRecords = constructAnalyzeRepairRecords;
	}

	public List<WXJLRecord> getComponentAnalyzeRepairRecords() {
		return componentAnalyzeRepairRecords;
	}

	public void setComponentAnalyzeRepairRecords(List<WXJLRecord> componentAnalyzeRepairRecords) {
		this.componentAnalyzeRepairRecords = componentAnalyzeRepairRecords;
	}

	public List<WXJLRecord> getOutsideAnalyzeRepairRecords() {
		return outsideAnalyzeRepairRecords;
	}

	public void setOutsideAnalyzeRepairRecords(List<WXJLRecord> outsideAnalyzeRepairRecords) {
		this.outsideAnalyzeRepairRecords = outsideAnalyzeRepairRecords;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		WXJL.logger = logger;
	}


	public static void setOrderFee(HttpServletRequest request, OrderInfo order, int memberLevel) {
		if (memberLevel == 0) {
			String normal = PropertiesUtils.getPropertyValueByKey("cheliangbaoyangQueryPrice_normal");
			order.setTotal_fee(Integer.valueOf(normal));// 设置价格
		} else if (memberLevel == 1) {
			String middle = PropertiesUtils.getPropertyValueByKey("cheliangbaoyangQueryPrice_middle");
			order.setTotal_fee(Integer.valueOf(middle));// 设置价格
		} else if (memberLevel == 2) {
			String high = PropertiesUtils.getPropertyValueByKey("cheliangbaoyangQueryPrice_high");
			order.setTotal_fee(Integer.valueOf(high));// 设置价格
		}
	}

	public static String queryResult(HttpServletRequest request, String orderId) {
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").enableComplexMapKeySerialization().disableHtmlEscaping().create();
		OrderInfo order = payService.getQueryOrderByorderId(orderId);
		String vin = request.getParameter("vin");
		String enginno = request.getParameter("enginno") == null?"":request.getParameter("enginno");
		String licenseplate =  request.getParameter("licenseplate") == null?"":request.getParameter("licenseplate");
		order.setQueryCondition("&vin=" + vin+"&enginno=" + enginno+"&licenseplate=" + licenseplate);
		// order.setOpenid("oUm4A0UA7pG6t-TQUVsLQqRppNl8");
		//先检查一下品牌是否支持查询
		String s1 =  CBS.getInstance(QueryAppKeyLib.baoyangUserId,QueryAppKeyLib.baoyangUserKey).getCheckBrand(vin);
		System.out.println(s1);
		logger.info("BYJL-pre-QueryResult1:\r\n" + s1);
		Map<String,String> m1 = JsonUtils.json2Map(s1);
		if(!"1106".equals(m1.get("Code"))){
			order.setQueryResult("查询失败");
			payService.updateFinancePayContent(order);
			return "{\"errorMessage\":\"" +m1.get("Message") + "\",\"success\":false}";
		}
		String s =  CBS.getInstance(QueryAppKeyLib.baoyangUserId,QueryAppKeyLib.baoyangUserKey).getBuyReport(vin, enginno,licenseplate, QueryAppKeyLib.baoyangCallBackUrl);
		//String s =  DemoData.WBJL;//测试代码
		s = s.replaceAll("\":\\s*,", "\":\\\"\\\",");
		WXJL w = gson.fromJson(s, WXJL.class);
		if(!"0".equals(w.getCode()) && !"".equals(w.getCode())){
			logger.info("BYJL-pre-QueryResult2:\r\n" + w.getMessage());
			order.setQueryResult("查询失败");
			payService.updateFinancePayContent(order);
			return "{\"errorMessage\":\"查询失败\",\"success\":false}";
		}else{
			logger.info("BYJL-pre-QueryResult2:\r\n" + gson.toJson(w));
			order.setQueryResult("&orderId="+orderId);
			payService.updateFinancePayContent(order);//测试代码
			//order.setQueryResult("&orderId="+orderId);
			return "{\"errorMessage\":\"报告生成中，耐心等待1~3分钟，请在记录里查看记录详情\",\"submitOrder\":1}";
		}
// System.out.println("s:\r\n"+s);
		// 设置请求器的配置
		/*String orderid = "";
		try {
			if (StringUtil.isEmpty(condition) || condition.indexOf("result") > -1) {
				HttpGet httpGet = new HttpGet(orderurl);
				HttpClient httpClient = SSLUtil.getHttpClient();
				HttpResponse res = httpClient.execute(httpGet);
				HttpEntity entity = res.getEntity();
				String clztresult = EntityUtils.toString(entity, "UTF-8");
				System.out.println(clztresult);
				queryResult = clztresult;
				BYJLorder border = gson.fromJson(queryResult, BYJLorder.class);
				if (!"0".equals(border.getError_code())) {
					return "{\"errorMessage\":\"" + border.getReason() + "\",\"success\":false}";
				}
				orderid = border.getResult().getOrder_id();
				// 等待5秒，等待报告生成
				// Thread.sleep(5000);
			} else {
				orderid = condition.replace("&orderId=", "");
			}
			HttpGet httpGet = new HttpGet(url + "&orderId=" + orderid);
			HttpClient httpClient = SSLUtil.getHttpClient();
			HttpResponse res = httpClient.execute(httpGet);
			HttpEntity entity = res.getEntity();
			String clztresult = EntityUtils.toString(entity, "UTF-8");
			queryResult = clztresult;
			System.out.println("BYJL:\r\n" + queryResult);
			logger.info("BYJL:\r\n" + queryResult);
			WXJL b = gson.fromJson(queryResult, WXJL.class);
			order.setQueryCondition("&vin=" + vin);

			if (!"0".equals(b.getError_code())) {
				order.setQueryResult("&orderId=" + orderid);
				payService.updateFinancePayContent(order);
				if ("227005".equals(b.getError_code()) || "订单创建成功，正在生成报告，请稍后再试".equals(b.getReason().trim())) {
					Runnable myRunnable = new MyRunnable(orderId);
					Thread thread1 = new Thread(myRunnable);
					thread1.start();
					return "{\"errorMessage\":\"报告生成中，耐心等待1分钟，请在记录里查看记录详情\",\"submitOrder\":1}";
				}
				return "{\"errorMessage\":\"" + b.getReason() + "\",\"submitOrder\":1}";
			}

			queryResult = gson.toJson(b);
			System.out.println("QueryResult : " + queryResult);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("保养记录查询失败");
			logger.error(StringUtil.errInfo(e));
			return "{\"errorMessage\":\"查询错误,请确认输入数据是否正确\",\"success\":false}";
		}

		return queryResult;*/
	}
	
	public WXJL translateWBJL(WXJL w){
		w.setBrand(w.getBrand());
		w.setCarComponentRecordsFlag(w.getCarComponentRecordsFlag());
		w.setCarConstructRecordsFlag(w.getCarConstructRecordsFlag());
		w.setCarFireFlag(w.getCarFireFlag());
		w.setCarOutsideRecordsFlag(w.getCarOutsideRecordsFlag());
		w.setCarType(w.getCarType());
		w.setCarWaterFlag(w.getCarWaterFlag());
		w.setCode(w.getCode());
		w.setComponentAnalyzeRepairRecords(w.getComponentAnalyzeRepairRecords());
		w.setConstructAnalyzeRepairRecords(w.getConstructAnalyzeRepairRecords());
		w.setDisplacement(w.getDisplacement());
		w.setEffluentStandard(w.getEffluentStandard());
		w.setLastMainTainTime(w.getLastMainTainTime());
		w.setLastRepairTime(w.getLastRepairTime());
		w.setMainTainTimes(w.getMainTainTimes());
		w.setMakeDate(w.getMakeDate());
		w.setMakeReportDate(w.getMakeReportDate());
		w.setManufacturer(w.getManufacturer());
		w.setMessage(w.getMessage());
		w.setMileageEstimate(w.getMileageEstimate());
		w.setMileageEveryYear(w.getMileageEveryYear());
		w.setMileageIsNormalFlag(w.getMileageIsNormalFlag());
		w.setModelName(w.getModelName());
		w.setNormalRepairRecords(w.getNormalRepairRecords());
		w.setOutsideAnalyzeRepairRecords(w.getOutsideAnalyzeRepairRecords());
		w.setProductionArea(w.getProductionArea());
		w.setReportNo(w.getReportNo());
		w.setScore(w.getScore());
		w.setSeriesName(w.getSeriesName());
		w.setTransmissionType(w.getTransmissionType());
		w.setVin(w.getVin());
		return w;
	}
}
