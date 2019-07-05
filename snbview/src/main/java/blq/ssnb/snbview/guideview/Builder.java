package blq.ssnb.snbview.guideview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Path;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.view.View;

/**
 * ================================================
 * 作者: SSNB
 * 日期: 2017/7/4
 * 邮箱: blq_ssnb@outlook.com
 * 修改次数: 1
 * 描述:
 * {@link SnbGuideView } 的 Builder
 * 用于构建引导view
 * ================================================
 */

public final class Builder {
    protected Context context;
    private SnbGuideView nGuideView;
    private boolean needBuild = true;//是否需要从新build - 如果参数改变从新show的时候就需要build过
    public Builder(Context context){
        this.context = context;
        nGuideView = new SnbGuideView(context);
    }

    /**
     * 需要重新调用build
     * 参数重新设置过后就需要调用该方法
     */
    private void needBuild(){
        needBuild = true;
    }

    /**
     * 可以直接build或者直接调用show
     * @return 当前对象
     */
    public Builder build(){
        if(nGuideView==null){
            nGuideView = new SnbGuideView(context);
//            nGuideView.release();
        }
        nGuideView.initBuilder(this);
        needBuild = false;
        return this;
    }

    /**
     * 显示GuideView
     */
    public void show(){
        if(needBuild){
            build();
        }
        nGuideView.show();
    }

    //-----------背景相关
    @ColorInt
    int bgColor = Color.argb(102,0,0,0);//背景颜色

    /**
     * 设置背景色
     * @param bgColor 背景颜色
     * @return 当前对象
     */
    public Builder bgColor(@ColorInt int bgColor){
        this.bgColor = bgColor;
        needBuild();
        return this;
    }
    //---------------
    //-----------高亮部分显示---------
    View targetView;//目标view
    int targetPadding = 8;
    Path hollowPath;

    /**
     * 目标view
     * @param targetView 要导航的目标view
     * @return Builder
     */
    public Builder targetView(View targetView){
        if(targetView!=null&&!targetView.equals(this.targetView)){
            this.targetView = targetView;
            needBuild();
        }
        return this;
    }

    /**
     * 目标view的绘制高亮部分时候的padding值
     ----------------
     |       |--------|  --- padding
     |    --------    |
     |   | target |   |
     |   |  View  |   |
     |    --------    |
     |                |
     ----------------
     * @param padding 边距
     *
     * @return 当前对象
     */
    public Builder targetPadding(int padding){
        if(this.targetPadding != padding){
            this.targetPadding = padding;
            needBuild();
        }
        return this;
    }

    /**
     * 高亮部分的图形，以原点作为图形的中心否者显示会有偏差
     * @param path 高亮的路径
     * @return 当前对象
     */
    public Builder hollowPath(Path path){
        this.hollowPath = path;
        needBuild();
        return this;
    }

    //---------------
    //------------提示部分参数
    @DrawableRes
    int hintImage;
    Direction hintDirection = Direction.BOTTOM;//提示view相对于目标view的方位
    int offsetX = 0;//提示view相对于目标位置的偏移
    int offsetY = 0;//提示view相对于目标位置的偏移

    /**
     * 要显示的提示图片
     * @param hintImage 资源id
     * @return 当前对象
     */
    public Builder hintView( @DrawableRes int hintImage){
        this.hintImage = hintImage;
        needBuild();
        return this;
    }

    /**
     *  提示内容在目标view的方位
     * @param direction 提示方向
     * @return 当前对象
     */
    public Builder hintDirection(Direction direction){
        if(this.hintDirection != direction){
            this.hintDirection = direction;
            needBuild();
        }
        return this;
    }

    /**
     * 提示内容x偏移量(+：远离目标view，-：接近目标view)
     * @param offsetX 单位dp
     * @return 当前对象
     */
    public Builder hintOffsetX(int offsetX){
        if(this.offsetX !=offsetX){
            this.offsetX = offsetX;
            needBuild();
        }
        return this;
    }

    /**
     * 提示内容y偏移量 (+：远离目标view，-：接近目标view)
     * @param offsetY 单位dp
     * @return 当前对象
     */
    public Builder hintOffsetY(int offsetY){
        if(this.offsetY !=offsetY) {
            this.offsetY = offsetY;
            needBuild();
        }
        return this;
    }
    //-------------
    //------------我知道了button
    @DrawableRes
    int iKnowBtn;
    public Builder iKnowBtn( @DrawableRes int iKnowBtn){
        this.iKnowBtn = iKnowBtn;
        needBuild();
        return this;
    }

    Direction iKnowBtnDirection = Direction.BOTTOM;
    public Builder iKnowBtnDirection(Direction direction){
        this.iKnowBtnDirection = direction;
        needBuild();
        return this;
    }

    int iKnowOffsetX = 0,iKnowOffsetY = 0;
    public Builder iKnowOffsetX(int offsetX){
        if(this.iKnowOffsetX !=offsetX) {
            this.iKnowOffsetX = offsetX;
            needBuild();
        }
        return this;
    }
    public Builder iKnowOffsetY(int offsetY){
        if(this.iKnowOffsetY !=offsetY) {
            this.iKnowOffsetY = offsetY;
            needBuild();
        }
        return this;
    }

    boolean iKnowBtnShow = true;
    public Builder iKnowBtnShow(boolean show){
        iKnowBtnShow = show;
        return this;
    }
    //----------view 的其他参数

    /**
     * 是否只在高亮处点击才能取消
     */
    boolean cancelOnTargetViewOutside = true;
    SnbGuideView.OnDismissListener onDismissListener;
    /**
     * 是否在高亮处外取消
     * @param can true 能够点击任意地方取消，false 反之只能在高亮处点击取消
     *            默认true
     * @return 当前对象
     */
    public Builder cancelOnTargetViewOutside(boolean can){
        if(this.cancelOnTargetViewOutside != can){
            this.cancelOnTargetViewOutside = can;
            needBuild();
        }
        return this;
    }

    /**
     * 界面取消通知
     * @param dismissListener 界面消失的监听
     * @return 当前对象
     */
    public Builder setOnDismissListener(SnbGuideView.OnDismissListener dismissListener){
        this.onDismissListener = dismissListener;
        needBuild();
        return this;
    }

}
