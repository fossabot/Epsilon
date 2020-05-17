package cn.enaium.epsilon.mixin

import cn.enaium.epsilon.event.events.Render3DEvent
import net.minecraft.client.render.GameRenderer
import net.minecraft.client.util.math.MatrixStack
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo


/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
@Mixin(GameRenderer::class)
class GameRendererMixin {
    @Inject(at = [At(value = "FIELD", target = "Lnet/minecraft/client/render/GameRenderer;renderHand:Z", ordinal = 0)], method = ["renderWorld"])
    private fun renderWorld(tickDelta: Float, limitTime: Long, matrixStack: MatrixStack, ci: CallbackInfo) {
        Render3DEvent(tickDelta, limitTime, matrixStack).call()
    }
}