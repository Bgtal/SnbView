package blq.ssnb.snbview.addsub;

/**
 * <pre>
 * ================================================
 * 作者: BLQ_SSNB
 * 日期：2019-08-01
 * 邮箱: blq_ssnb@outlook.com
 * 修改次数: 1
 * 描述:
 *      添加描述
 * ================================================
 * </pre>
 */
public interface ActionCallBack {
    /**
     * 到达上界的回调
     *
     * @param topLimit 临界值
     */
    void onTopLimit(int topLimit);

    /**
     * 到达下界的回调
     *
     * @param bottomLimit 下界值
     */
    void onBottomLimit(int bottomLimit);

    /**
     * 当数值改变的时候回调
     *
     * @param number 改变后的值
     */
    void onNumberChange(int number);

    /**
     * 当输入的值错误的时候回调
     *
     * @param msg
     * @return true 表示替换为最后一次值，false 表示不替换
     */
    boolean typeError(String msg);
}
