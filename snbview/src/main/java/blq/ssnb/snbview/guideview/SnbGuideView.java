package blq.ssnb.snbview.guideview;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import blq.ssnb.snbutil.SnbDisplayUtil;


/**
 * ================================================
 * 作者: SSNB
 * 日期: 2017/7/3
 * 邮箱: blq_ssnb@outlook.com
 * 修改次数: 1
 * 描述:
 * 引导view 只能通过 {@link Builder } 来构建
 * ================================================
 */

public final class SnbGuideView extends View {

    protected SnbGuideView(Context context) {
        this(context, null);
    }

    private SnbGuideView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    private SnbGuideView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private Paint mainPaint = new Paint();
    /**
     * 需要提示的view的相关信息
     */
    private TargetViewInfo targetViewInfo;
    private HintViewInfo hintViewInfo;
    private IKnowBtnInfo iKnowBtnInfo;

    private Paint mBgPaint;//背景paint
    private Paint mHollowPaint;//高亮部分的画笔

    private void init() {
        targetViewInfo = new TargetViewInfo();
        hintViewInfo = new HintViewInfo();
        iKnowBtnInfo = new IKnowBtnInfo();
        mBgPaint = new Paint();
        mHollowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mHollowPaint.setColor(Color.WHITE);
        mHollowPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        mHollowPaint.setAntiAlias(true);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    private boolean needCalculate = false;
    private OnDismissListener onDismissListener;
    void initBuilder(Builder builder) {
        mBgPaint.setColor(builder.bgColor);

        targetViewInfo.setTargetView(builder.targetView)
                .setPadding(SnbDisplayUtil.dp2Px(getContext(),builder.targetPadding))
                .setHollowPath(builder.hollowPath);

        hintViewInfo
                .setHintDirection(builder.hintDirection)
                .setOffset(SnbDisplayUtil.dp2Px(getContext(),builder.offsetX),
                        SnbDisplayUtil.dp2Px(getContext(),builder.offsetY))
                .setHintView(BitmapFactory.decodeResource(getContext().getResources(),builder.hintImage));

        iKnowBtnInfo
                .setDirection(builder.iKnowBtnDirection)
                .setIKnowBtnBitmap(BitmapFactory.decodeResource(getContext().getResources(),builder.iKnowBtn))
                .setOffset(SnbDisplayUtil.dp2Px(getContext(),builder.iKnowOffsetX),
                        SnbDisplayUtil.dp2Px(getContext(),builder.iKnowOffsetY))
                .setVisible(builder.iKnowBtnShow);

        cancelOnTargetViewOutside = builder.cancelOnTargetViewOutside;
        onDismissListener = builder.onDismissListener;
        needCalculate = true;
    }

    void release(){
        targetViewInfo = null;
        hintViewInfo = null;
        mBgPaint = null;
        mHollowPaint = null;
        onDismissListener = null;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (targetViewInfo.getTargetView() == null) {
            return;
        }
        canvas.drawPaint(mBgPaint);//画背景色
        drawHollow(canvas);
        drawHintView(canvas);
        drawIKnow(canvas);
        super.onDraw(canvas);
    }

    /**
     * 画高亮处的内容
     * @param canvas
     */
    private void drawHollow(Canvas canvas) {
        //首先获取设置的hollowPath
        Path mPath = targetViewInfo.getHollowPath();
        if(mPath ==null){
            return;
        }
        canvas.drawPath(mPath,mHollowPaint);
    }

    /**
     * 画提示的view
     * @param canvas
     */
    private void drawHintView(Canvas canvas) {
        if(hintViewInfo.getHintBitmap()==null){
            return;
        }
        Point point = hintViewInfo.getBitmapStartPoint(targetViewInfo.getOutRect());
        canvas.drawBitmap(hintViewInfo.getHintBitmap(),point.x,point.y,mainPaint);
    }

    private void drawIKnow(Canvas canvas) {
        if(iKnowBtnInfo.getiKnowBitmap()!=null&&iKnowBtnInfo.isVisible()){
            Point point = iKnowBtnInfo.getBitmapStartPoint(canvas.getWidth(),canvas.getHeight());
            canvas.drawBitmap(iKnowBtnInfo.getiKnowBitmap(),point.x,point.y,mainPaint);
        }
    }
    // view显影 相关操作
    public void show() {
        if (targetViewInfo.getTargetView() == null) {
            return;
        }
        if (ViewCompat.isAttachedToWindow(targetViewInfo.getTargetView())) {
            toShow();
        } else {
            ViewTreeObserver treeObserver = targetViewInfo.getTargetView().getViewTreeObserver();
            treeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    targetViewInfo.getTargetView().getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    toShow();
                }
            });
        }
    }

    private void toShow() {
        if (needCalculate) {
            calculateTargetViewLocation();
            needCalculate =false;
        }
        ((ViewGroup) ((Activity) getContext()).getWindow().getDecorView()).addView(this);
        this.setFocusable(true);
        this.setFocusableInTouchMode(true);
        requestFocus();
        invalidate();
    }

    /**
     * 计算view的属性
     */
    private void calculateTargetViewLocation() {
        View targetView = targetViewInfo.getTargetView();
        int[] pos = new int[2];
        targetViewInfo.getTargetView().getLocationOnScreen(pos);
        targetViewInfo.setTargetViewParam(targetView.getWidth(), targetView.getHeight(), pos[0], pos[1]);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {//监听返回按钮
            toRemoveSelf();
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    private boolean cancelOnTargetViewOutside;
    private boolean isActionDownInTargetView = false;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            isActionDownInTargetView = cancelOnTargetViewOutside || targetViewInfo.isInTarget(x, y)
                    ||iKnowBtnInfo.isInBtn(x,y);
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            if (cancelOnTargetViewOutside) {
                toRemoveSelf();
            } else {
                if (isActionDownInTargetView && (targetViewInfo.isInTarget(x, y)||iKnowBtnInfo.isInBtn(x,y))) {
                    toRemoveSelf();
                }
            }
            isActionDownInTargetView = false;
        }
        return true;
    }

    private void toRemoveSelf() {
        this.setFocusable(false);
        this.setFocusableInTouchMode(false);
        ((ViewGroup) ((Activity) getContext()).getWindow().getDecorView()).removeView(this);
        if(onDismissListener!=null){
            onDismissListener.onDismiss();
        }
//        release();
    }



    // 其他类
    public interface OnDismissListener{
        void onDismiss();
    }

}
