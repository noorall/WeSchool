package com.noorall.weschool.login

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton

class LoadingButton (initBtn: CircularProgressButton){
    private var btn:CircularProgressButton=initBtn
    private var state:Boolean=false
    public fun start()
    {
        btn.startAnimation()
    }
    public fun stop()
    {
        btn.stopAnimation()
    }
    public fun revert()
    {
        btn.revertAnimation()
    }
    public fun clean()
    {
        btn.clearAnimation()
    }

}