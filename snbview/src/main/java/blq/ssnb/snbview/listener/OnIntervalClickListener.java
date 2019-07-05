package blq.ssnb.snbview.listener;

import android.view.View;

/**
 * <pre>
 * ================================================
 * 作者: BLQ_SSNB
 * 日期：2019-07-04
 * 邮箱: blq_ssnb@outlook.com
 * 修改次数: 1
 * 描述:
 * 设置点击的间隔，位置防止快速点击
 *
 * ================================================
 * </pre>
 */
public abstract class OnIntervalClickListener implements View.OnClickListener {

    /**
     * 默认间隔0.5秒
     */
    private static final long NORMAL_INTERVAL = 500;
    /**
     * 间隔时间
     */
    private long interval;
    /**
     * 最后一次点击的时间
     */
    private long lastEffectiveRecordTime = 0;

    public OnIntervalClickListener() {
        this(NORMAL_INTERVAL);
    }

    public OnIntervalClickListener(long interval) {
        this.interval = interval;
    }

    @Override
    public final void onClick(View v) {
        long clickTime = System.currentTimeMillis();
        if (clickTime - lastEffectiveRecordTime > interval) {
            //超过间隔时间即有效击穿 更新时间
            lastEffectiveRecordTime = clickTime;
            onEffectiveClick(v);
        } else {
            onIntercept(v);
        }
    }

    /**
     * 拦截时候的回调
     *
     * @param v
     */
    public void onIntercept(View v) {

    }

    /**
     * 有效点击的的回调
     *
     * @param v
     */
    public abstract void onEffectiveClick(View v);
}
