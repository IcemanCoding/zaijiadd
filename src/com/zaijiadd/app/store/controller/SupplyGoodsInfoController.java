package com.zaijiadd.app.store.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zaijiadd.app.common.controller.BaseController;
import com.zaijiadd.app.common.utils.ContainerUtils;
import com.zaijiadd.app.common.viewmodel.SupplyRequestViewModel;
import com.zaijiadd.app.store.entity.GoodsInfoEntity;
import com.zaijiadd.app.store.entity.SupplyGoodsInfoEntity;
import com.zaijiadd.app.store.service.SupplyGoodsInfoService;
import com.zaijiadd.app.user.entity.UserInfoEntity;
import com.zaijiadd.app.user.service.UserInfoService;
import com.zaijiadd.app.utils.constants.ConstantsForPage;

@RequestMapping("/supply")
@Controller
public class SupplyGoodsInfoController extends BaseController{
	
	@Autowired
	private SupplyGoodsInfoService supplyGoodsInfoService;
	
	@Autowired
	private UserInfoService userInfoService;
	
	/**
	 * 查看商品类型,分页,可根据商品类别分别查询
	 * categoryCode: 可选参数, 商品类别的编码
	 * pageNo: 可选参数,当前第几页
	 * @param request
	 * @param viewmodel
	 * @return
	 */
	@RequestMapping ( value = "/getSupplyGoods", method = RequestMethod.POST )
	@ResponseBody
	public Map<String, Object> getSupplyGoods(HttpServletRequest request, SupplyRequestViewModel viewmodel) {
		Map<String, Object> resData = new HashMap<String, Object>();
		
		//int userType = (int) request.getAttribute(ConstantsForLogin.USER_TYPE);//得到用户类型
		String pNo = request.getParameter(ConstantsForPage.PAGE_NO);//第几页
		int pageNo = 1;
		if(pNo != null) pageNo = Integer.parseInt(pNo);
		
		String categoryCode = request.getParameter("categoryCode");
		
		Map<String, Object> params = new HashMap<String,Object>();
		if(categoryCode != null)
			params.put("categoryCode", categoryCode.toUpperCase());
		params.put(ConstantsForPage.PAGE_SIZE, ConstantsForPage.PER_PAGE_SIZE);
		params.put(ConstantsForPage.START, ConstantsForPage.PER_PAGE_SIZE * (pageNo-1));
		
		List<SupplyGoodsInfoEntity> supplyGoodsInfoList = supplyGoodsInfoService.queryGoodsInfo(params);
		
		resData.put("data", supplyGoodsInfoList);
		
		return ContainerUtils.buildResSuccessMap( resData );
	}
	
	/**
	 * 可看自己商店下的所有商品信息,可根据类型来查看
	 * storeId : 可选, 不提供则是查看自己店铺中的产品,提供则是查看对应的商铺中的产品
	 * @param request
	 * @param viewmodel
	 * @return
	 */
	@RequestMapping ( value = "/getSupplyGoodsInStore", method = RequestMethod.POST )
	@ResponseBody
	public Map<String, Object> getSupplyGoodsInStore(HttpServletRequest request, SupplyRequestViewModel viewmodel) {
		Map<String, Object> resData = new HashMap<String, Object>();
		String pNo = request.getParameter(ConstantsForPage.PAGE_NO);//第几页
		int pageNo = 1;
		if(pNo != null) pageNo = Integer.parseInt(pNo);
		
		String categoryCode = request.getParameter("categoryCode");
		
		String storeIdStr = request.getParameter("storeId");
		
		int storeId = 0;
		if(storeIdStr == null){
			//得到用户信息
			UserInfoEntity userInfo = getCurrentLoginUserInfoInSession(request, viewmodel);
			//得到用户的店铺信息
			storeId = userInfoService.getUserStoreByUserId(userInfo.getUserId()).getStoreId();
		}else{
			storeId = Integer.parseInt(storeIdStr);
		}
		
		List<SupplyGoodsInfoEntity> supplyGoodsInfoList = supplyGoodsInfoService.queryGoodsInfoInStore(storeId, categoryCode, pageNo);
		
		resData.put("data", supplyGoodsInfoList);
		
		return ContainerUtils.buildResultMap( resData );
	}
	
	/**
	 * 查看某商品的详细信息
	 * 必须传递供应商品表中的id,参数名字必须为: supplyGoodsId
	 * @param supplyGoodsId
	 * @return
	 */
	@RequestMapping ( value = "/lookSupplyGoodsDetail", method = RequestMethod.POST )
	@ResponseBody
	public Map<String, Object> lookSupplyGoodsDetail(@RequestParam(required=true, value="supplyGoodsId")int supplyGoodsId) {
		Map<String, Object> resData = new HashMap<String, Object>();
		
		resData.put("data", supplyGoodsInfoService.getGoodsInfoById(supplyGoodsId));
		
		return ContainerUtils.buildResMap( resData, 1, "操作成功");
	}
	
	/**
	 * 进货
	 * @param request
	 * @param viewmodel
	 * @return
	 */
	@RequestMapping ( value = "/addSupplyGoods", method = RequestMethod.POST )
	@ResponseBody
	public Map<String, Object> addSupplyGoods(HttpServletRequest request, SupplyRequestViewModel viewmodel) {
		Map<String, Object> resData = new HashMap<String, Object>();
		
		//得到用户信息
		UserInfoEntity userInfo = getCurrentLoginUserInfoInSession(request, viewmodel);
		//得到用户的店铺信息
		int storeId = userInfoService.getUserStoreByUserId(userInfo.getUserId()).getStoreId();
		
		SupplyGoodsInfoEntity supplyGoodsInfo = new SupplyGoodsInfoEntity();
		
		//得到商品id
		int goodsInfoId = Integer.parseInt(request.getParameter("goodsInfoId"));//必须
		
		String priceStr = request.getParameter("price");//价格
		if(null != priceStr){
			supplyGoodsInfo.setPrice(Double.parseDouble(priceStr));
			resData.put("price", Double.parseDouble(priceStr));
		}
		
		String buyNumStr = request.getParameter("buyNum");//购买 数量
		if(null != buyNumStr){
			supplyGoodsInfo.setStock(Integer.parseInt(buyNumStr));
			resData.put("buyNum", Integer.parseInt(buyNumStr));
		}
		
		supplyGoodsInfo.setGoodsInfoId(goodsInfoId);
		supplyGoodsInfo.setUserStoreId(storeId);
		
		resData.put("goodsInfoId", goodsInfoId);
		
		//去查询是否已经有该货物
		SupplyGoodsInfoEntity isHasGoodsInfo = supplyGoodsInfoService.isHasGoodsInSupplyGoodsInfo(supplyGoodsInfo);
		
		//不存在 该物件,则新增加
		if(null == isHasGoodsInfo){
			supplyGoodsInfo.setStatus(1);//产品状态默认为在售
			supplyGoodsInfo.setSaleCount(0);//新产品默认为售出0件
			supplyGoodsInfoService.addSupplyGoods(supplyGoodsInfo);
		}else{//修改
			supplyGoodsInfo.setStatus(1);//进货成功 则自动修改成在售状态
			supplyGoodsInfo.setId(isHasGoodsInfo.getId());
			supplyGoodsInfoService.updateSupplyGoods(supplyGoodsInfo);
		}
		
		return ContainerUtils.buildResMap(resData, 1, "进货完成");
	}
	
	/**
	 * 批量下架 传递supply_goods_info中的id集合,以逗号隔开
	 * @param request
	 * @param viewmodel
	 * @return
	 */
	@RequestMapping ( value = "/undercarriage", method = RequestMethod.POST )
	@ResponseBody
	public Map<String, Object> batchUndercarriage(HttpServletRequest request, SupplyRequestViewModel viewmodel) {
		List<Integer> idList = new ArrayList<Integer>();
		
		String idsStr = request.getParameter("ids");
		String[] idsArr = idsStr.split(",");
		for(int i=0;i<idsArr.length;i++){
			idList.add(Integer.parseInt(idsArr[i]));
		}
		
		supplyGoodsInfoService.batchUndercarriage(idList);
		
		return ContainerUtils.buildHeadMap(null, 1, "已成功下架");
	}
	
	/**
	 * 交易完成则修改已售出与库存有量的数据
	 * @param saleCount
	 * @param id
	 * @return
	 */
	@RequestMapping ( value = "/saleOut", method = RequestMethod.POST )
	@ResponseBody
	public Map<String, Object> saleOut(
			@RequestParam(value="saleCount", required=true)int saleCount, 
			@RequestParam(value="id", required=true)int id){
		supplyGoodsInfoService.saleOutGoods(id, saleCount);
		return ContainerUtils.buildHeadMap(null, 1, "已成功售出");
	}
	
	/**
	 * 根据商品名称模糊查询得到物品信息
	 * pageNo: 当前加载的第几页数据
	 * goodsName: 商品名称
	 * @param request
	 * @param viewmodel
	 * @return
	 */
	@RequestMapping ( value = "/getGoodsInfo", method = RequestMethod.POST )
	@ResponseBody
	public Map<String, Object> getGoodsInfo(HttpServletRequest request, SupplyRequestViewModel viewmodel) {
		Map<String, Object> resData = new HashMap<String, Object>();
		
		String pNo = request.getParameter(ConstantsForPage.PAGE_NO);//第几页
		int pageNo = 1;
		if(pNo != null) pageNo = Integer.parseInt(pNo);
		
		String goodsName = request.getParameter("goodsName");
		
		Map<String, Object> params = new HashMap<String,Object>();
		params.put("goodsName", goodsName);
		params.put(ConstantsForPage.PAGE_SIZE, ConstantsForPage.PER_PAGE_SIZE);
		params.put(ConstantsForPage.START, ConstantsForPage.PER_PAGE_SIZE * (pageNo-1));
		
		List<GoodsInfoEntity> goodsInfoList = supplyGoodsInfoService.queryGoodsInfoByGoodsName(params);
		
		resData.put("data", goodsInfoList);
		
		return ContainerUtils.buildResSuccessMap( resData );
	}
	
}
