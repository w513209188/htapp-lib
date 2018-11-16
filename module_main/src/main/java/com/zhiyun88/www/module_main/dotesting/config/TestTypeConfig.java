package com.zhiyun88.www.module_main.dotesting.config;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({
        TestTypeConfig.WJST,
        TestTypeConfig.SJST,
}
)
@Retention(RetentionPolicy.SOURCE)
public @interface TestTypeConfig {
    //问卷试题
    int WJST=1;
    //试卷试题
    int SJST=2;

}
