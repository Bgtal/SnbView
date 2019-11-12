package blq.ssnb.snbview.addsub;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.regex.Pattern;

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
public class SnbAddSubLogicHelper {

    private WeakReference<View> mAddView;
    private WeakReference<TextView> mMsgView;
    private WeakReference<View> mSubView;

    private Option mOption;

    private ActionCallBack mActionCallBack;

    public SnbAddSubLogicHelper() {
        mOption = new Option();
    }

    public SnbAddSubLogicHelper(Option option) {
        mOption = new Option();
        mOption.updateOption(option);
    }

    // <editor-fold defaultstate="collapsed" desc="内部判断使用">

    private void bindEvent() {
        if (isViewExist(mSubView)) {
            mSubView.get().setOnClickListener(v -> {
                int temp = mOption.currentNumber - mOption.step;//计算改变后的值
                updateText(temp);
            });
        }

        if (isViewExist(mAddView)) {
            mAddView.get().setOnClickListener(v -> {
                int temp = mOption.currentNumber + mOption.step;

                updateText(temp);
            });
        }

        if (isViewExist(mMsgView)) {
            if (mMsgView.get() instanceof EditText) {
                mMsgView.get().setOnFocusChangeListener((v, hasFocus) -> {
                    if (!hasFocus) {//如果没有交点了就要判断这个输入的值是不是符合规则
                        if (mMsgView != null && mMsgView.get() != null) {
                            String msg = mMsgView.get().getText().toString().trim();
                            boolean isNumber = Pattern.matches("[-]?[0-9]*", msg);
                            if (isNumber) {
                                updateText(Integer.valueOf(msg));
                            } else {
                                boolean isReturn = true;
                                if (mActionCallBack != null) {
                                    isReturn = mActionCallBack.typeError("请输入正常数字");
                                }
                                if (isReturn) {
                                    int lastNumber = mOption.currentNumber;
                                    mOption.currentNumber = mOption.bottomLimit - 1;
                                    updateText(lastNumber);
                                }
                            }
                        }
                    }
                });
                mMsgView.get().setOnKeyListener((v, keyCode, event) -> false);
            }
        }
    }

    /**
     * 文字
     * @param temp
     */
    private void updateText(int temp) {
        //如果到达下界了
        if (temp <= mOption.bottomLimit) {
            temp = mOption.bottomLimit;
            if (mActionCallBack != null) {
                mActionCallBack.onBottomLimit(temp);
            }
        }

        //如果到达上界了
        if (temp >= mOption.topLimit) {
            temp = mOption.topLimit;
            if (mActionCallBack != null) {
                mActionCallBack.onTopLimit(temp);
            }
        }

        if (mOption.currentNumber != temp) {//当最后一个值与新的值不相等表示改变了
            //否者就认为没变
            mOption.currentNumber = temp;
            if (isViewExist(mMsgView)) {
                mMsgView.get().setText(String.valueOf(mOption.currentNumber));
            }
            if (mActionCallBack != null) {
                mActionCallBack.onNumberChange(temp);
            }
        }
    }

    /**
     * 重置
     */
    public void reset(){
        updateText(mOption.defaultNumber);
    }

    private boolean isViewExist(WeakReference weak) {
        return weak != null && weak.get() != null;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="外部调用">

    public void setOption(Option option) {
        this.mOption.updateOption(option);
        updateText(this.mOption.currentNumber);

    }

    public void attachView(View subView, TextView msgView, View addView) {
        if (subView != null && msgView != null && addView != null) {
            mSubView = new WeakReference<>(subView);
            mMsgView = new WeakReference<>(msgView);
            mAddView = new WeakReference<>(addView);
            bindEvent();
            updateText(mOption.defaultNumber);
        } else {
            throw new IllegalArgumentException("三个基础的控件不能为空");
        }
    }

    public void detach() {
        if (mAddView != null) {
            mAddView.clear();
            mAddView = null;
        }
        if (mSubView != null) {
            mSubView.clear();
            mSubView = null;
        }
        if (mMsgView != null) {
            mMsgView.clear();
            mMsgView = null;
        }
    }

    public void setActionCallBack(ActionCallBack callBack) {
        this.mActionCallBack = callBack;
    }
    // </editor-fold>


    public static class Option {
        private int topLimit = 100;
        private int bottomLimit = 0;
        private int step = 1;
        private int currentNumber = 1;
        private int defaultNumber = 1;

        public int getTopLimit() {
            return topLimit;
        }

        public Option setTopLimit(int topLimit) {
            this.topLimit = topLimit;
            return this;
        }

        public int getBottomLimit() {
            return bottomLimit;
        }

        public Option setBottomLimit(int bottomLimit) {
            this.bottomLimit = bottomLimit;
            return this;
        }

        public int getStep() {
            return step;
        }

        public Option setStep(int step) {
            this.step = step;
            return this;
        }

        public Option setDefaultNumber(int defaultNumber) {
            this.defaultNumber = defaultNumber;
            return this;
        }

        private void updateOption(Option option) {
            if (option != null) {
                this.topLimit = option.topLimit;
                this.bottomLimit = option.bottomLimit;
                this.step = option.step;
            }
        }
    }

}
