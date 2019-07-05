package blq.ssnb.snbview;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.lang.ref.WeakReference;

/**
 * <pre>
 * ================================================
 * 作者: Blq_s
 * 日期: 2018/3/2
 * 邮箱: blq_ssnb@outlook.com
 * 修改次数: 1
 * 描述:
 * 一个搜索框view
 * ================================================
 * </pre>
 */

public class SnbSmartSearchEdit extends LinearLayout {

    public SnbSmartSearchEdit(Context context) {
        this(context, null);
    }

    public SnbSmartSearchEdit(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, R.attr.SnbDrawableTextViewStyle);
    }

    private SmartSearchOption searchOption;
    private SmartSearchAction searchAction;
    private InputMethodManager imm;

    public SnbSmartSearchEdit(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View.inflate(context, R.layout.snb_smart_search_edit, this);
        initData(context);
        initAttrs(context, attrs, defStyleAttr);
        initView();
        bindEvent();
    }

    private void initData(Context context) {
        searchOption = new SmartSearchOption();
        searchAction = new SmartSearchAction(Looper.myLooper());
        imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    private ImageView searchIconView;
    private ImageView clearBtn;
    private EditText editText;


    private void initAttrs(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SnbSmartSearchEdit, defStyleAttr, R.style.DefSnbSmartSearchEditStyle);
        String text = typedArray.getString(R.styleable.SnbSmartSearchEdit_snb_text);
        searchOption.nowContentStr = text == null ? searchOption.nowContentStr : text;
        searchOption.textColor = typedArray.getColor(R.styleable.SnbSmartSearchEdit_snb_textColor, searchOption.textColor);
        searchOption.textSize = typedArray.getDimension(R.styleable.SnbSmartSearchEdit_snb_textSize, searchOption.textSize);
        String hint = typedArray.getString(R.styleable.SnbSmartSearchEdit_snb_hint);
        searchOption.hintStr = hint == null ? searchOption.hintStr : hint;
        searchOption.hintColor = typedArray.getColor(R.styleable.SnbSmartSearchEdit_snb_hintColor, searchOption.hintColor);
        searchOption.searchIcon = typedArray.getResourceId(R.styleable.SnbSmartSearchEdit_snb_searchIcon, searchOption.searchIcon);
        searchOption.clearIcon = typedArray.getResourceId(R.styleable.SnbSmartSearchEdit_snb_clearBtnIcon, searchOption.clearIcon);
        searchOption.isAutoSearch = typedArray.getBoolean(R.styleable.SnbSmartSearchEdit_snb_autoSearch, searchOption.isAutoSearch);
        searchOption.intervalTime = typedArray.getInt(R.styleable.SnbSmartSearchEdit_snb_autoSearchIntervalTime, (int) searchOption.intervalTime);
        searchOption.isSmartClearButton = typedArray.getBoolean(R.styleable.SnbSmartSearchEdit_snb_smartClearBtn, searchOption.isSmartClearButton);
        searchOption.isFocusLostSearch = typedArray.getBoolean(R.styleable.SnbSmartSearchEdit_snb_focusLostAutoSearch, searchOption.isFocusLostSearch);
        searchOption.isAlwaysShowClearBtn = typedArray.getBoolean(R.styleable.SnbSmartSearchEdit_snb_alwaysShowClearBtn, searchOption.isAlwaysShowClearBtn);
        typedArray.recycle();
    }

    private void initView() {
        searchIconView = findViewById(R.id.img_search_icon);
        clearBtn = findViewById(R.id.img_clear_btn);
        editText = findViewById(R.id.edit_search_content);
        setHintText(searchOption.hintStr);
        setHintTextColor(searchOption.hintColor);
        setText(searchOption.nowContentStr);
        setTextSize(searchOption.textSize);
        setTextColor(searchOption.textColor);
        setSearchIcon(searchOption.searchIcon);
        setClearBtnIcon(searchOption.clearIcon);
        setAutoSearch(searchOption.isAutoSearch);
        setIntervalTime(searchOption.intervalTime);
        setClearBtnSmart(searchOption.isSmartClearButton);
        setFocusLostSearchEnable(searchOption.isFocusLostSearch);
        setAlwaysShowClearBtn(searchOption.isAlwaysShowClearBtn);
        smartClearBtn(searchOption.nowContentStr);
    }

    private void bindEvent() {
        editText.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {//如果是焦点获得的话
                searchAction.isDoSearchAction = false;//设置
                showSoftInput();
            } else {
                if (searchAction.isDoSearchAction) {//如果是用户点击搜索按钮调用的
                    searchOption.lastContentStr = searchOption.nowContentStr;
                    searchOption.onSoftInputSearch(searchOption.nowContentStr);
                } else if (searchOption.isFocusLostSearch) {//如果焦点小时自动搜索开启
                    searchOption.lastContentStr = searchOption.nowContentStr;
                    searchOption.onLostFocusSearch(searchOption.nowContentStr);
                }//否则什么都不干
                hideSoftInput();
            }
        });

        editText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                searchAction.isDoSearchAction = true;
                focusLose();//会触发OnFocusChangeListener
                return true;
            }
            return false;
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                searchOption.nowContentStr = s.toString();//设置最新的内容

                smartClearBtn(searchOption.nowContentStr);
                if (searchOption.isAutoSearch) {//如果是自动搜索
                    //重置搜索关键词
                    searchAction.resetAutoSearchAction(s.toString(), searchOption.intervalTime);
                }
            }
        });

        clearBtn.setOnClickListener(v -> {
            editText.setText("");//会触发TextChangedListener
            searchOption.onClearBtnClick(v);
        });

        searchAction.setOnHandleMsgListener(msg -> {
            if (searchOption != null) {
                if (!searchOption.lastContentStr.equals(msg) && searchOption.nowContentStr.equals(msg)) {
                    //如果与最后一次的文字不一样
                    //并且发过来的文字和当前最新的文字一样
                    // 那么就调用自动搜索
                    searchOption.lastContentStr = msg;//更新
                    searchOption.onAutoSearch(msg);
                }
            }
        });

    }

    private void smartClearBtn(String msg) {
        if (!searchOption.isAlwaysShowClearBtn && searchOption.isSmartClearButton) {//如果是自动隐藏清除按钮
            clearBtn.setVisibility(msg.length() > 0 ? VISIBLE : GONE);//判断是否隐藏
        }
    }

    /**
     * 显示软键盘
     */
    private void showSoftInput() {
        imm.showSoftInput(editText, InputMethodManager.SHOW_FORCED);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    /**
     * 隐藏软键盘
     */
    private void hideSoftInput() {
        imm.hideSoftInputFromWindow(SnbSmartSearchEdit.this.getWindowToken(), 0);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        focusObtain();//触摸到layout的框框内那么就认为需要搜索，让edit获得焦点
        return super.onTouchEvent(event);
    }

    private class SmartSearchOption {
        /**
         * 最后一次文本内容
         */
        private String lastContentStr = "";
        /**
         * 当前文本内容
         */
        private String nowContentStr = "";
        /**
         * 文字大小
         */
        private float textSize = 14;
        /**
         * 文字颜色
         */
        private int textColor = 0xFF444444;
        /**
         * 提示内容
         */
        private String hintStr = "";
        /**
         * 提示颜色文字
         */
        private int hintColor = 0xFF949494;
        /**
         * 搜索图标
         */
        @DrawableRes
        private int searchIcon = R.drawable.snb_ic_search_black_24dp;
        /**
         * 清理图标
         */
        @DrawableRes
        private int clearIcon = R.drawable.snb_ic_clear_black_24dp;

        /**
         * 是否自动搜索
         */
        private boolean isAutoSearch = true;

        /**
         * 自动搜索的延迟时间
         */
        private long intervalTime = 1500;

        /**
         * 清除按钮是否自动隐藏
         */
        private boolean isSmartClearButton = true;

        private boolean isFocusLostSearch = false;

        private boolean isAlwaysShowClearBtn = false;

        private OnClickListener onClearBtnClickListener;

        private OnSearchActionListener searchActionListener;

        public void onSoftInputSearch(String keyWord) {
            if (searchActionListener != null) {
                searchActionListener.onSoftInputSearch(keyWord);
            }
        }

        public void onAutoSearch(String keyWord) {
            if (searchActionListener != null) {
                searchActionListener.onAutoSearch(keyWord);
            }
        }

        public void onLostFocusSearch(String keyWord) {
            if (searchActionListener != null) {
                searchActionListener.onLostFocusSearch(keyWord);
            }
        }

        public void onClearBtnClick(View v) {
            if (onClearBtnClickListener != null) {
                onClearBtnClickListener.onClick(v);
            }
        }

    }

    private class SmartSearchAction {

        private Handler mHandler;
        private MRunnable mRunnable;
        private OnHandleMsgListener listener;

        /**
         * 是否按下搜索按钮
         */
        private boolean isDoSearchAction;

        private SmartSearchAction(Looper looper) {
            mHandler = new Handler(looper) {
                @Override
                public void handleMessage(Message msg) {
                    String keyWord = (String) msg.obj;//接收到的关键词
                    if (listener != null) {
                        listener.onHandle(keyWord);
                    }
                }
            };
            mRunnable = new MRunnable(this);
        }

        private void setOnHandleMsgListener(OnHandleMsgListener listener) {
            this.listener = listener;
        }

        /**
         * 更新自动搜索的内容和时间
         *
         * @param keyWord     关键词
         * @param delayMillis 自动搜索的间隔时间
         */
        private void resetAutoSearchAction(String keyWord, long delayMillis) {
            if (mHandler != null) {
                mHandler.removeCallbacks(mRunnable);
                mRunnable.setKeyWord(keyWord);
                mHandler.postDelayed(mRunnable, delayMillis);
            }
        }
    }

    /**
     * 用于发送关键词的定时任务
     */
    private class MRunnable implements Runnable {

        /**
         * 关键词
         */
        private String keyWord;
        /**
         * Action弱引用
         */
        private WeakReference<SmartSearchAction> weakReference;

        private MRunnable(SmartSearchAction searchAction) {
            weakReference = new WeakReference<>(searchAction);
        }

        /**
         * 更新关键词
         *
         * @param keyWord
         */
        private void setKeyWord(String keyWord) {
            this.keyWord = keyWord;
        }

        @Override
        public void run() {
            SmartSearchAction option = weakReference.get();
            if (option != null) {
                if (option.mHandler != null) {
                    Message msg = new Message();
                    msg.obj = keyWord;
                    /**
                     * 时机一到发送关键词
                     */
                    option.mHandler.sendMessage(msg);
                }
            }

        }
    }

    /**
     * 定时任务执行的回调
     */
    private interface OnHandleMsgListener {
        void onHandle(String msg);
    }

    /**
     * 设置文本内容
     *
     * @param text 文本内容
     */
    public void setText(String text) {
        searchOption.nowContentStr = text;
        editText.setText(text);
    }

    /**
     * 设置提示语言
     *
     * @param hintStr
     */
    public void setHintText(String hintStr) {
        searchOption.hintStr = hintStr;
        editText.setHint(hintStr);
    }

    public void setTextSize(float sp) {
        searchOption.textSize = sp;
        editText.setTextSize(TypedValue.COMPLEX_UNIT_PX, sp);
    }

    public void setTextColor(int color) {
        searchOption.textColor = color;
        editText.setTextColor(color);
    }

    public void setHintTextColor(int hintTextColor) {
        searchOption.hintColor = hintTextColor;
        editText.setHintTextColor(hintTextColor);
    }


    /**
     * 设置坐标的搜索按钮图标
     *
     * @param searchIcon
     */
    public void setSearchIcon(@DrawableRes int searchIcon) {
        searchOption.searchIcon = searchIcon;
        searchIconView.setImageResource(searchIcon);
    }

    /**
     * 设置清除按钮的图标
     *
     * @param clearIcon
     */
    public void setClearBtnIcon(@DrawableRes int clearIcon) {
        searchOption.clearIcon = clearIcon;
        clearBtn.setImageResource(clearIcon);
    }

    /**
     * 设置是否自动搜索
     *
     * @param autoSearch true
     */
    public void setAutoSearch(boolean autoSearch) {
        searchOption.isAutoSearch = autoSearch;
    }

    /**
     * 设置自动搜索间隔时间
     *
     * @param intervalTime 间隔时间(毫秒)
     */
    public void setIntervalTime(long intervalTime) {
        searchOption.intervalTime = intervalTime;
    }

    /**
     * 是否自动显隐清理按钮
     * 当{@link #setAlwaysShowClearBtn(boolean)}设置为true 时设置该参数不会产生效果
     *
     * @param isSmart true 将自动显隐
     */
    public void setClearBtnSmart(boolean isSmart) {
        searchOption.isSmartClearButton = isSmart;
    }

    /**
     * 设置是否允许焦点丢失后自动搜索
     *
     * @param enable true 当焦点丢失的时候会回调
     */
    public void setFocusLostSearchEnable(boolean enable) {
        searchOption.isFocusLostSearch = enable;
    }

    /**
     * 是否一直显示清除按钮
     * 当为true 的时候{@link #setClearBtnSmart(boolean)}的设置将无效
     *
     * @param alwaysShow true 一直显示
     */
    public void setAlwaysShowClearBtn(boolean alwaysShow) {
        searchOption.isAlwaysShowClearBtn = alwaysShow;
        if (alwaysShow) {
            clearBtn.setVisibility(VISIBLE);
        } else {
            clearBtn.setVisibility(GONE);
        }
    }

    /**
     * 设置清除按钮的监听
     *
     * @param onClearBtnClickListener
     */
    public void setOnClearBtnClickListener(OnClickListener onClearBtnClickListener) {
        this.searchOption.onClearBtnClickListener = onClearBtnClickListener;
    }

    /**
     * 设置搜索监听
     *
     * @param searchActionListener 搜索监听
     */
    public void setOnSearchActionListener(OnSearchActionListener searchActionListener) {
        searchOption.searchActionListener = searchActionListener;
    }

    /**
     * 调用该方法会使搜索的edit获得焦点
     */
    public void focusObtain() {
        if (!editText.isFocused()) {//没有焦点就请求焦点
            editText.requestFocus();
        } else {//有焦点就弹框弹出焦点
            showSoftInput();
        }
    }

    /**
     * 调用该方法会使搜索的edit失去焦点
     * 如果edit之前是有焦点状态，那么同时会关闭键盘
     */
    public void focusLose() {
        if (editText.isFocused()) {
            editText.clearFocus();
        }
    }

    /**
     * 查询的返回监听
     */
    public interface OnSearchActionListener {
        /**
         * 当点击软键盘的搜索的时候返回
         *
         * @param searchKey 当前输入框中的文字
         */
        void onSoftInputSearch(String searchKey);

        /**
         * 当焦点丢失的时候返回（软键盘导致的焦点丢失不会触发该方法）
         *
         * @param searchKey 当前输入框中的文字
         */
        void onLostFocusSearch(String searchKey);

        /**
         * 制动搜索
         *
         * @param searchKey
         */
        void onAutoSearch(String searchKey);
    }
}
