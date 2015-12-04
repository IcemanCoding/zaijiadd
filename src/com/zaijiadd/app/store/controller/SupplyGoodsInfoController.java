package com.zaijiadd.app.store.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import com.zaijiadd.app.common.controller.BaseController;
import com.zaijiadd.app.common.utils.ContainerUtils;
import com.zaijiadd.app.common.utils.CreateFileUtils;
import com.zaijiadd.app.common.utils.ExcelUtils;
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
		String categoryIdStr = request.getParameter("categoryId");
		Integer categoryId = null;
		if(categoryIdStr != null && !"".equals(categoryIdStr)){
			categoryId = Integer.parseInt(categoryIdStr);
		}
		
		Map<String, Object> params = new HashMap<String,Object>();
		if(categoryCode != null)
			params.put("categoryCode", categoryCode.toUpperCase());
		params.put(ConstantsForPage.PAGE_SIZE, ConstantsForPage.PER_PAGE_SIZE);
		params.put(ConstantsForPage.START, ConstantsForPage.PER_PAGE_SIZE * (pageNo-1));
		params.put("categoryId", categoryId);
		
		List<SupplyGoodsInfoEntity> supplyGoodsInfoList = supplyGoodsInfoService.queryGoodsInfo(params);
		
		resData.put("data", supplyGoodsInfoList);
		
		return ContainerUtils.buildResultMap( resData );
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
		
		String categoryIdStr = request.getParameter("categoryId");
		Integer categoryId = null;
		if(categoryIdStr != null && !"".equals(categoryIdStr)){
			categoryId = Integer.parseInt(categoryIdStr);
		}
		
		String storeIdStr = request.getParameter("storeId");
		
		int storeId = 0;
		if(storeIdStr == null){
			//得到用户信息
			UserInfoEntity userInfo = getCurrentLoginUserInfoInSession(getUserSessionId(request));
			//得到用户的店铺信息
			storeId = userInfoService.getUserStoreByUserId(userInfo.getUserId()).getStoreId();
		}else{
			storeId = Integer.parseInt(storeIdStr);
		}
		
		List<SupplyGoodsInfoEntity> supplyGoodsInfoList = supplyGoodsInfoService.queryGoodsInfoInStore(storeId, categoryId, categoryCode, pageNo);
		
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
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	@RequestMapping ( value = "/opSupplyGoods", method = RequestMethod.POST )
	@ResponseBody
	public Map<String, Object> addSupplyGoods(HttpServletRequest request, SupplyRequestViewModel viewmodel)
			throws IllegalStateException, IOException {
		Map<String, Object> resData = new HashMap<String, Object>();
		
		//得到用户信息
		UserInfoEntity userInfo = getCurrentLoginUserInfoInSession(getUserSessionId(request));
		//得到用户的店铺信息
		int storeId = userInfoService.getUserStoreByUserId(userInfo.getUserId()).getStoreId();
		
		String picUrl = uploadImg(request, userInfo.getUserId());//上传图片到服务器
		
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
		
		if(picUrl != null && !"".equals(picUrl.trim())){
			supplyGoodsInfoService.updateGoodsImgUrl(goodsInfoId, picUrl);//修改图片路径 
		}
		return ContainerUtils.buildResMap(resData, 1, "操作成功");
	}
	
	/**
	 * 批量下架 传递supply_goods_info中的id集合,以逗号隔开
	 * @param request
	 * @param viewmodel
	 * @return
	 */
	@RequestMapping ( value = "/undercarriage", method = RequestMethod.POST )
	@ResponseBody
	public Map<String, Object> batchUndercarriage(HttpServletRequest request) {
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
	 * 批量修改产品类型 传category_type中的类型id,
	 * goods_info中的id集合,以逗号隔开
	 * categoryId: 产品类型的id
	 * ids：要修改的产品id集合
	 * @param request
	 * @param viewmodel
	 * @return
	 */
	@RequestMapping ( value = "/batchUpdateGoodInfoType", method = RequestMethod.POST )
	@ResponseBody
	public Map<String, Object> batchUpdateGoodInfoType(@RequestParam(required=true, value="categoryId")int categoryId,
			@RequestParam(required=true, value="ids")String ids) {
		Map<String, Object> resData = new HashMap<String, Object>();
		
		int updateNo = supplyGoodsInfoService.batchUpdateGoodInfoType(categoryId, ids);
		
		if(updateNo > 0){
			return ContainerUtils.buildHeadMap(null, 1, "已成功修改产品类型");
		}else{
			return ContainerUtils.buildResultNoKnowErrMap(resData);
		}
		
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
		
		return ContainerUtils.buildResultMap( resData );
	}
	
	/**
	 * 添加商品
	 * @param goodsTypeId 商品类别id
	 * @param goodsName   商品名称
	 * @param spec		    规格
	 * @param pic		    图片
	 * @param price		    单价
	 * @param buyNum	    数量
	 * @return
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	@RequestMapping ( value = "/addGoodsInfo", method = RequestMethod.POST )
	@ResponseBody
	public Map<String, Object> addGoodsInfo(HttpServletRequest request,
			@RequestParam("goodsTypeId")int goodsTypeId,
			@RequestParam("goodsName")String goodsName,
			@RequestParam("spec")String spec,
			//@RequestParam("pic")String pic,
			@RequestParam("price")double price,
			@RequestParam("buyNum")int buyNum) throws IllegalStateException, IOException {
		Map<String, Object> resData = new HashMap<String, Object>();
		
		
		//得到用户信息
		UserInfoEntity userInfo = getCurrentLoginUserInfoInSession(getUserSessionId(request));
		
		String picUrl = uploadImg(request, userInfo.getUserId());
		GoodsInfoEntity goodsInfo = new GoodsInfoEntity();
		
		goodsInfo.setCategoryId(goodsTypeId);
		goodsInfo.setGoodsName(goodsName);
		goodsInfo.setSpec(spec);
		goodsInfo.setPic(picUrl);
		goodsInfo.setStatus(1);
		
		int supplyGoodsInfoId = supplyGoodsInfoService.addGoodsInfo(goodsInfo);
		if(supplyGoodsInfoId > 0){
			SupplyGoodsInfoEntity supplyGoodsInfo = new SupplyGoodsInfoEntity();
			supplyGoodsInfo.setGoodsInfoId(goodsInfo.getId());
			supplyGoodsInfo.setPrice(price);
			supplyGoodsInfo.setSaleCount(0);
			supplyGoodsInfo.setStock(buyNum);
			supplyGoodsInfo.setStatus(1);
			supplyGoodsInfo.setUserStoreId(userInfoService.getUserStoreByUserId(userInfo.getUserId()).getStoreId());
			
			supplyGoodsInfoService.addSupplyGoods(supplyGoodsInfo);
		}else{
			return ContainerUtils.buildResultNoKnowErrMap(resData);
		}
		return ContainerUtils.buildResultMap(resData);
	}
	
/*	@RequestMapping(value = "/upImg")
	@ResponseBody*/
	public String uploadImg(HttpServletRequest request, int userId) throws IllegalStateException, IOException {
		Enumeration headerNames = request.getHeaderNames();
		String reqHost = null;	
		while ( headerNames.hasMoreElements() ) {
			String key = ( String ) headerNames.nextElement();
			String value = request.getHeader( key );
			if ( key.equalsIgnoreCase( "host" ) ) {
				reqHost = value;
				break;
			}
		}
		
		String realRoad = request.getSession().getServletContext().getContextPath();
		
		String picUrl = "http://" + reqHost + realRoad;
		
		// 解析器解析request的上下文
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		// 先判断request中是否包涵multipart类型的数据，
		if (multipartResolver.isMultipart(request)) {
			// 再将request中的数据转化成multipart类型的数据
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			Iterator iter = multiRequest.getFileNames();
			while (iter.hasNext()) {
				MultipartFile file = multiRequest.getFile((String) iter.next());
				if (file != null) {
					String fileName = file.getOriginalFilename();
					fileName = System.currentTimeMillis()+"_"+userId+"_"+fileName;
					
					String path = request.getSession().getServletContext().getRealPath("/images");//"D:/" + fileName;
					
					CreateFileUtils.createDir(path);
					CreateFileUtils.createFile(path+File.separator+fileName);
					
					File localFile = new File(path+File.separator+fileName);					
					//http://localhost:8080/ddhomeapp/images/qwe.png
					// 写文件到本地
					file.transferTo(localFile);
					
					picUrl += "/images/" + fileName;
				}
			}
		}else{
			picUrl = "";
		}
		return picUrl;
	}
	
	
	/**
	 * excel批量导入客户
	 * @author: lyq
	 * @date: Jun 6, 2014 10:52:21 AM
	 * @param file
	 * @param model
	 * @return
	 */
	  @RequestMapping(value = "addCrmManagerByExcel.php")
	public ModelAndView  addCrmManagerByExcel(ModelMap model,HttpServletRequest request){
	        MultipartHttpServletRequest multipartRequest  =  (MultipartHttpServletRequest) request; 
	        
	        MultipartFile file  =  multipartRequest.getFile("file");
	        try{
	        	ExcelUtils.SaveFileFromInputStream(file.getInputStream(), request.getRealPath("/upload"), "java.xls");
	        }catch(Exception ex){
	            System.out.println("上传文件失败");
	            ex.printStackTrace();
	             
	        }   
	        File file1 = new File(request.getRealPath("/upload")+"/java.xls");
	        String[][] result=null;
	        try {
	            result = ExcelUtils.getData(file1, 1);
	        } catch (FileNotFoundException e) {
	     
	            e.printStackTrace();
	        } catch (IOException e) {
	 
	            e.printStackTrace();
	        }
	        if(result==null){
	            return null;
	        }
	        
	        System.out.println(result);
	        
/*	       int rowLength = result.length;
	       //默认从1 开始    本来是为0 剔除掉
	      Date  date= new Date();
	       for(int i=0;i<rowLength;i++) {
	            CrmManager crm =new  CrmManager();
	            crm.setCrmCreatetime(date);
	            crm.setCrmStatus(1);
	           for(int j=1;j<result[i].length;j++) {//默认从1开始添加
	          //客户ID        客户名     客户类型        客户电话        客户状态        创建时间        放出时间        跟入时间        跟入人     放出人
	          //27            法规和热       特级     1212121     1           41773       2014-05-14 10:20:48.0       yq1012       
	          //整理   id 去掉 。 创建时间 换成当前时间，，，放出时间     跟入时间        跟入人     放出人----全不要
	          //格式如上
	          // System.out.print(result[i][j]+"\t\t");
	     
	                if(j==1){//客户名字
	                    crm.setCrmName(result[i][j]);
	                     
	                }
	                if(j==2){//客户类型
	                    crm.setCrmType(result[i][j]);
	                     
	                }
	                if(j==3){//客户电话
	                    crm.setCrmPhone(result[i][j]);
	                }
	             
	            }
	           crmService.saveCrmUser(crm);
	 
	       }
*/	       return null;
	  }
	
}
