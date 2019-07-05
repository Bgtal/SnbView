package blq.ssnb.snbview.guideview;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;

/**
 * ================================================
 * 作者: SSNB
 * 日期: 2017/7/6
 * 邮箱: blq_ssnb@outlook.com
 * 修改次数: 1
 * 描述:
 * 用于绘制一个view 在全局位置
 * ================================================
 */

class IKnowBtnInfo {
    private Direction direction;
    private int offsetX,offsetY;
    private Bitmap iKnowBitmap;
    private boolean isVisible;

    private Rect btnRect = new Rect();
    private Point point = new Point(-1,-1);

    IKnowBtnInfo setIKnowBtnBitmap(Bitmap bitmap){
        iKnowBitmap = bitmap;
        return this;
    }

    IKnowBtnInfo setOffset(int offsetX,int offsetY){
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        return this;
    }


    IKnowBtnInfo setDirection(Direction direction) {
        this.direction = direction;
        return this;
    }

    IKnowBtnInfo setVisible(boolean visible) {
        this.isVisible = visible;
        return this;
    }

    Bitmap getiKnowBitmap() {
        return iKnowBitmap;
    }

    Point getBitmapStartPoint(int maxW, int maxH){
        if(iKnowBitmap!=null){
            int bitW = iKnowBitmap.getWidth();
            int bitH = iKnowBitmap.getHeight();
            int x,y;
            switch(direction){
                case LEFT_TOP:
                    x = 0;
                    y = 0;
                    break;
                case TOP:
                    x = (maxW-bitW)/2;
                    y = 0;
                    break;
                case RIGHT_TOP:
                    x = maxW - bitW;
                    y = 0;
                    break;
                case LEFT:
                    x = 0;
                    y = (maxH-bitH)/2;
                    break;
                case CENTER:
                    x = (maxW-bitW)/2;
                    y = (maxH-bitH)/2;
                    break;
                case RIGHT:
                    x = maxW - bitW;
                    y = (maxH-bitH)/2;
                    break;
                case LEFT_BOTTOM:
                    x = 0;
                    y = maxH - bitH;
                    break;
                case BOTTOM:
                    x = (maxW-bitW)/2;
                    y = maxH - bitH;
                    break;
                case RIGHT_BOTTOM:
                    x = maxW - bitW;
                    y = maxH - bitH;
                    break;
                default:
                    x = (maxW-bitW)/2;
                    y = maxH - bitH;

            }
            point.x = x+offsetX;
            point.y = y+offsetY;
            btnRect.set(point.x,point.y,point.x+bitW,point.y+bitH);
        }
        return point;
    }
    boolean isVisible(){
        return isVisible;
    }

    boolean isInBtn(float x,float y) {
        return iKnowBitmap != null
                && isVisible
                && btnRect.left <= x && x <= btnRect.right
                && btnRect.top <= y && y <= btnRect.bottom;
    }
}
