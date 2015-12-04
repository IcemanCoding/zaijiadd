package com.zaijiadd.app.utils.constants;

public class ConstantsForOrder {
	
	public static final Integer HAS_LOGISTICS = 1;//有物流
	
	public static final Integer NO_LOGISTICS = 0;//没物流
	
	//order_info中的pay_status状态对应下边5种情况	
	
	//未支付状态
	public static final Integer NO_PAY_STATUS = 1;
	//已支付
	public static final Integer PAY_STATUS_SUCCESS = 2;
	
	public static final Integer DELIVER_GOODS = 4;//已发货eliver goods
	
	public static final Integer NO_DELIVER_GOODS = 3;//待发货eliver goods
	
	public static final Integer ORDER_DONE = 5;//交易完成
	
	
	// 订单初始状态
	public static final Integer TRANS_STATUS_INITIAL = 1;
	// 订单处理成功状态
	public static final Integer TRANS_STATUS_SUCCESS = 2;
	// 订单处理失败状态
	public static final Integer TRANS_STATUS_FAIL = 3;
	// 订单处理中状态
	public static final Integer TRANS_STATUS_HANDLE = 4;
	
	// 交易码：充值交易
	public static final String TRANS_CODE_RECHARGE = "100010";
	
	// 订单初始状态
	public static final Integer JD_TRANS_STATUS_INITIAL = 1;
	// 下单成功
	public static final Integer JD_TRANS_STATUS_BOOK_SUCCESS = 2;
	// 下单失败
	public static final Integer JD_TRANS_STATUS_BOOK_FAIL = 3;
	// 确认订单成功
	public static final Integer JD_TRANS_STATUS_COMFIRM_SUCCESS = 4;
	// 确认订单失败
	public static final Integer JD_TRANS_STATUS_COMFIRM_FAIL = 5;
	// 完成收货
	public static final Integer JD_TRANS_STATUS_FINISH = 6;

}
