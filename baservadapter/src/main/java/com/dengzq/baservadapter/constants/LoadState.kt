package com.dengzq.baservadapter.constants

/**
 * <p>author    dengzq</P>
 * <p>date      2018/5/30 上午10:12</p>
 * <p>package   com.dengzq.baservadapter.constants</p>
 * <p>readMe    Enum state for load more action;</p>
 */
enum class LoadState {

    /**
     * Normal status, loading layout is gone as well as error layout;
     */
    NORMAL,

    /**
     * Loading status,show loading layout if it's exist;
     */
    LOADING,

    /**
     * Error status, show error layout if it's exist;
     */
    ERROR;
}