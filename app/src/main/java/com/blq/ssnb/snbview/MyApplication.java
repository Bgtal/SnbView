package com.blq.ssnb.snbview;

import blq.ssnb.baseconfigure.AbsApplication;
import blq.ssnb.snbutil.SnbLog;

/**
 * <pre>
 * ================================================
 * 作者: BLQ_SSNB
 * 日期：2019-07-04
 * 邮箱: blq_ssnb@outlook.com
 * 修改次数: 1
 * 描述:
 *      添加描述
 * ================================================
 * </pre>
 */
public class MyApplication extends AbsApplication {
    @Override
    protected void initSnb() {
        SnbLog.getGlobalBuilder().isOpen(true);
    }

    @Override
    protected void initBugly() {

    }

    @Override
    protected void initNetWork() {

    }

    @Override
    protected void initSingle() {

    }
}
