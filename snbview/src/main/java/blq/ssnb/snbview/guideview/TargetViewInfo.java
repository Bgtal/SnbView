package blq.ssnb.snbview.guideview;

import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;

/**
 * ================================================
 * 作者: SSNB
 * 日期: 2017/7/4
 * 邮箱: blq_ssnb@outlook.com
 * 修改次数: 1
 * 描述:
 * 添加描述
 * ================================================
 */

class TargetViewInfo {
    private static final String tag = "TargetViewInfo";

    private View targetView;
    private Rect targetRealRect;
    private Rect innerRect;
    private Rect outRect;
    private int padding;

    private Path hollowPath;

    TargetViewInfo(){
        targetRealRect = new Rect();
        innerRect = new Rect();
        outRect = new Rect();
        targetView = null;
    }

    TargetViewInfo setTargetView(View targetView) {
        this.targetView = targetView;
        return this;
    }

    TargetViewInfo setTargetViewParam(int width,int height,int onScreenX,int onScreenY) {
        int right = onScreenX + width;
        int bottom = onScreenY + height;
        this.targetRealRect.set(onScreenX,onScreenY,right,bottom);
        this.innerRect.set(onScreenX-padding,onScreenY-padding,right+padding,bottom+padding);
        toTransPath();
        return this;
    }

    private void toTransPath(){

        if(hollowPath == null){
            hollowPath = new Path();
            RectF rectF = new RectF(innerRect);
            hollowPath.addRoundRect(rectF,4,4, Path.Direction.CW);
            outRect = new Rect(innerRect);
            return;
        }

        RectF pathBound = new RectF();
        hollowPath.computeBounds(pathBound ,true);
        boolean needTranslate = !(pathBound.centerX()==innerRect.centerX()
                &&pathBound.centerY()==innerRect.centerY());
        float k ;
        float k1 = innerRect.width()/pathBound.width();
        float k2 = innerRect.height()/pathBound.height();

        if(k1<1||k2<1){
            k = (k1>k2?k1:k2);
        }else{
            k = (k1>k2?k1:k2);
        }
        if(k!=1.0f){
            Matrix m = new Matrix();
            m.postScale(k,k);
            hollowPath.transform(m);
        }
        if(needTranslate){
            Matrix m = new Matrix();
            m.postTranslate(targetRealRect.centerX(),targetRealRect.centerY());
            hollowPath.transform(m);
        }
        hollowPath.computeBounds(pathBound,true);
        outRect = new Rect((int)pathBound.left,(int)pathBound.top,(int)pathBound.right,(int)pathBound.bottom);
    }

    TargetViewInfo setPadding(int padding){
        if(padding>=0){
            this.padding = padding;
        }
        return this;
    }

    TargetViewInfo setHollowPath(Path path){
        this.hollowPath = path == null ? null:new Path(path);
        return this;
    }

    View getTargetView() {
        return targetView;
    }

    Rect getOutRect(){
        return outRect;
    }

//    int getLocationCenterX(){
//        return targetRealRect.centerX();
//    }
//
//    int getLocationCenterY(){
//        return targetRealRect.centerY();
//    }
//
//    int getTargetViewWidth(){
//        return targetWidth;
//    }
//
//    int getTargetMaxEdge(){
//        return targetWidth>=targetHeight?targetWidth:targetHeight;
//    }
//

//    int getInscribedCircleR(){
//        return (int) (Math.sqrt(targetWidth*targetWidth+targetHeight*targetHeight)/2)+padding;
//    }

    Path getHollowPath() {
        return hollowPath;
    }

    boolean isInTarget(float x, float y){
        return innerRect.left<=x&&x<= innerRect.right
                && innerRect.top<=y&&y<= innerRect.bottom;
    }
}
