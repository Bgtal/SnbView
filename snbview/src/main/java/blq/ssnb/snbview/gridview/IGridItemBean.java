package blq.ssnb.snbview.gridview;

import androidx.annotation.IntDef;

/**
 * <pre>
 * ================================================
 * 作者: BLQ_SSNB
 * 日期：2020-03-24
 * 邮箱: blq_ssnb@outlook.com
 * 修改次数: 1
 * 描述:
 *      添加描述
 * ================================================
 * </pre>
 */
public interface IGridItemBean {
    int FLAG_IMG_VIEW = 0;
    int FLAG_IMG_BTN = 1;

    @IntDef(value = {FLAG_IMG_VIEW, FLAG_IMG_BTN})
    @interface FLAG {
    }

    @FLAG
    int getFlag();

    String getUrl();
}
