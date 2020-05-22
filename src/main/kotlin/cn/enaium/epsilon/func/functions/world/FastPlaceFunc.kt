package cn.enaium.epsilon.func.functions.world

import cn.enaium.epsilon.Epsilon.IMC
import cn.enaium.epsilon.event.EventAT
import cn.enaium.epsilon.event.events.UpdateEvent
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
class FastPlaceFunc : Func("FastPlace", GLFW.GLFW_KEY_B, Category.WORLD) {
    @EventAT
    fun on(updateEvent: UpdateEvent) {
        IMC.itemUseCooldown = 0
    }
}