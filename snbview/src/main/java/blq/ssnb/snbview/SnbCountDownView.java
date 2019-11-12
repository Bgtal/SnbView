package blq.ssnb.snbview;

import android.content.Context;
import android.content.res.TypedArray;
import androidx.appcompat.widget.AppCompatTextView;
import android.util.AttributeSet;

import blq.ssnb.snbutil.SnbCountDownTimer;
import blq.ssnb.snbutil.constant.SnbTimeConstant;

/**
 * <pre>
 * ================================================
 * 作者: BLQ_SSNB
 * 日期：2018/10/19
 * 邮箱: blq_ssnb@outlook.com
 * 修改次数: 1
 * 描述:
 * 带有倒计时功能的TextView
 * ================================================
 * </pre>
 */
public class SnbCountDownView extends AppCompatTextView {
    public SnbCountDownView(Context context) {
        this(context, null);
    }

    public SnbCountDownView(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.SnbCountDownViewStyle);
    }

    /**
     * 倒计时工具
     */
    private SnbCountDownTimer mCountDownTimer;
    /**
     * 总倒计时时间（毫秒）
     */
    private int mTotalTime = 60;
    private int mIntervalTime = 1;
    /**
     * 初始化显示的内容
     */
    private String mInitialWords = "点击倒计时";
    /**
     * 结束显示的内容
     */
    private String mFinishWords = "重新计时";

    public SnbCountDownView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs, defStyleAttr);
        initView();
        initData();
    }

    private void initAttrs(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SnbCountDownView, defStyleAttr, R.style.DefSnbSmartSearchEditStyle);
        mTotalTime = typedArray.getInteger(R.styleable.SnbCountDownView_snb_totalTime, mTotalTime);
        mIntervalTime = typedArray.getInteger(R.styleable.SnbCountDownView_snb_interval, mIntervalTime);
        String tInitialWords = typedArray.getString(R.styleable.SnbCountDownView_snb_initialText);
        String tFinishWords = typedArray.getString(R.styleable.SnbCountDownView_snb_finishText);
        typedArray.recycle();

        mInitialWords = tInitialWords == null ? mInitialWords : tInitialWords;
        mFinishWords = tFinishWords == null ? mFinishWords : tFinishWords;
    }

    /**
     * 初始化View
     */
    private void initView() {
        setText(mInitialWords);
    }

    private void initData() {
        mCountDownTimer = new SnbCountDownTimer(mTotalTime * SnbTimeConstant.ONE_SECONDS, mIntervalTime * SnbTimeConstant.ONE_SECONDS) {
            @Override
            protected void onTick(long remainingMillisecond) {
                if (mFinishWords != null) {
                    setText(mFinishWords + "(" + remainingMillisecond / SnbTimeConstant.ONE_SECONDS + ")");
                } else {
                    setText(String.valueOf(remainingMillisecond / SnbTimeConstant.ONE_SECONDS));
                }
            }

            @Override
            protected void onFinish() {
                setEnabled(true);
                setText(mFinishWords);
            }
        };
    }

    /**
     * 设置点击事件
     *
     * @param clickListener OnClickListener
     */
    public void setOnBtnClickListener(OnClickListener clickListener) {
        setOnClickListener(clickListener);
    }

    /**
     * 开始计时
     * 一般在 {@link #setOnBtnClickListener(OnClickListener)} 方法中执行
     */
    public void start() {
        setEnabled(false);
        mCountDownTimer.start();
    }

    /**
     * 结束计时
     * 界面destroy的时候调用
     */
    public void stop() {
        mCountDownTimer.stop();
        setEnabled(true);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mCountDownTimer.stop();
    }
}
