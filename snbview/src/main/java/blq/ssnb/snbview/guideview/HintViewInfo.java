package blq.ssnb.snbview.guideview;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;

/**
 * ================================================
 * 作者: SSNB
 * 日期: 2017/7/5
 * 邮箱: blq_ssnb@outlook.com
 * 修改次数: 1
 * 描述:
 * 添加描述
 * ================================================
 */

class HintViewInfo {

    private Direction hintDirection;
    private int offsetX;
    private int offsetY;
    private Bitmap hintBitmap;
    HintViewInfo setHintView(Bitmap drawId){
        hintBitmap =drawId;
        return this;
    }

    HintViewInfo setHintDirection(Direction hintDirection) {
        this.hintDirection = hintDirection;
        return this;
    }

    HintViewInfo setOffset(int offsetX, int offsetY) {
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        return this;
    }

    int getOffsetX(){
        return offsetX;
    }

    int getOffsetY(){
        return offsetY;
    }

    int getHintWidth(){
        return hintBitmap.getWidth();
    }

    int getHintHeight(){
        return hintBitmap.getHeight();
    }

    Bitmap getHintBitmap() {
        return hintBitmap;
    }

    Direction getHintDirection() {
        return hintDirection;
    }

    Point getBitmapStartPoint(Rect targetRect){
        Point startPoint = new Point();
        int x, y;
        int xk=1,yk=1;
        /**
         *      xy 分别不同方位对应的点
         *     .-----.-----.
         *     |           |
         *     |           |
         *     .           .
         *     |           |
         *     |           |
         *     .-----.-----.
         *
         */
        switch (hintDirection) {
            case LEFT_TOP:
                x = targetRect.left;
                y = targetRect.top;
                xk = -1;
                yk = -1;
                break;
            case TOP:
                x = targetRect.centerX();
                y = targetRect.top;
                xk = 1;
                yk = -1;
                break;
            case RIGHT_TOP:
                x = targetRect.right;
                y = targetRect.top;
                xk = 1;
                yk = -1;
                break;
            case LEFT:
                x = targetRect.left;
                y = targetRect.centerY();
                xk = -1;
                yk = 1;
                break;
            case RIGHT:
                x = targetRect.right;
                y = targetRect.centerY();
                xk = 1;
                yk = 1;
                break;
            case LEFT_BOTTOM:
                x = targetRect.left;
                y = targetRect.bottom;
                xk = -1;
                yk = 1;
                break;
            case BOTTOM:
                x = targetRect.centerX();
                y = targetRect.bottom;
                xk = 1;
                yk = 1;
                break;
            case RIGHT_BOTTOM:
                x = targetRect.right;
                y = targetRect.bottom;
                xk = 1;
                yk = 1;
                break;
            default:
                x = targetRect.centerX();
                y = targetRect.bottom;
                xk = 1;
                yk = 1;
        }
        int width = getHintWidth();
        int height = getHintHeight();
        int startX = x;
        int startY = y;
        if (x < targetRect.centerX()) {
            startX =x-width ;
        }else if(x == targetRect.centerX()){
            startX = x-width/2;
        }
        if(y<targetRect.centerY()){
            startY = y-height;
        }else if(y == targetRect.centerY()){
            startY = y-height/2;
        }
        startX+=(xk*getOffsetX());
        startY+=(yk*getOffsetY());
        startPoint.x = startX;
        startPoint.y = startY;
        return startPoint;
    }
}
