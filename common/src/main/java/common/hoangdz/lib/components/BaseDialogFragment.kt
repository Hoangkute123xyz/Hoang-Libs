package common.hoangdz.lib.components

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.annotation.CallSuper
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.viewbinding.ViewBinding
import common.hoangdz.lib.R
import common.hoangdz.lib.extensions.findFragment
import common.hoangdz.lib.extensions.inflateViewBinding
import common.hoangdz.lib.extensions.registerEventBusBy
import common.hoangdz.lib.extensions.unRegisterEventBus


/**
 * Created by Hoang Dep Trai on 05/31/2022.
 */

abstract class BaseDialogFragment<VB : ViewBinding> : DialogFragment(), BaseAndroidComponent<VB> {

    override val binding by lazy { inflateViewBinding<VB>(layoutInflater) }

    override fun isCancelable() = true

    override val needToSubscribeEventBus = false

    override fun getTheme() = R.style.DialogTheme

    protected open fun getDefaultDialogHeight(): Int {
        return 0
    }

    override fun onStart() {
        super.onStart()
        registerEventBusBy(needToSubscribeEventBus)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.setCanceledOnTouchOutside(isCancelable)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        val pref = context?.appInject<ContextEntryPoint>()?.pref()
//        binding.root.layoutParams.apply {
//            width = (((pref?.screenWidth ?: return) * 0.8).roundToInt())
//            height = getDefaultDialogHeight().takeIf { it > 0 } ?: WRAP_CONTENT
//            binding.root.layoutParams = this
//        }
        init(savedInstanceState)
    }

    override fun VB.initListener() {}

    override fun VB.initView(savedInstanceState: Bundle?) {}

    override fun VB.setupViewModel() {}

    @CallSuper
    open fun show(manager: FragmentManager) {
        val df = manager.findFragment(this) ?: this
        if (df is DialogFragment && !df.isAdded) {
            super.show(manager, javaClass.simpleName)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unRegisterEventBus()
    }
}