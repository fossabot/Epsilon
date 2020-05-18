package cn.enaium.epsilon.func.funcs.render

import cn.enaium.epsilon.func.Category
import cn.enaium.epsilon.func.Func
import cn.enaium.epsilon.func.FuncAT
import org.lwjgl.glfw.GLFW

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
@FuncAT
class ClickGUIFunc : Func("ClickGUI", GLFW.GLFW_KEY_RIGHT_SHIFT, Category.RENDER) {
    override fun onEnable() {

        enable()
        super.onEnable()
    }
}