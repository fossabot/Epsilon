package cn.enaium.epsilon.utils

import cn.enaium.epsilon.Epsilon.MC
import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.DrawableHelper
import net.minecraft.client.render.BufferRenderer
import net.minecraft.client.render.Tessellator
import net.minecraft.client.render.VertexFormats
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.util.Identifier
import net.minecraft.util.math.Matrix4f
import org.lwjgl.opengl.GL11
import java.awt.Color

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
object Render2DUtils {

    val scaledWidth: Int
        get() = MinecraftClient.getInstance().window.scaledWidth

    val scaledHeight: Int
        get() = MinecraftClient.getInstance().window.scaledHeight

    fun drawRect(matrixStack: MatrixStack, x1: Int, y1: Int, x2: Int, y2: Int, color: Int) {
        DrawableHelper.fill(matrixStack, x1, y1, x2, y2, color)
    }

    fun drawRect(matrix4f: Matrix4f, x1: Double, y1: Double, x2: Double, y2: Double, color: Int) {
        fill(matrix4f, x1, y1, x2, y2, color)
    }

    fun drawRectWH(matrixStack: MatrixStack, x: Int, y: Int, width: Int, height: Int, color: Int) {
        DrawableHelper.fill(matrixStack, x, y, x + width, y + height, color)
    }

    fun drawRectWH(matrixStack: MatrixStack, x: Double, y: Double, width: Double, height: Double, color: Int) {
        fill(matrixStack.peek().model, x, y, x + width, y + height, color)
    }

    fun drawImage(image: String, x: Double, y: Double, width: Double, height: Double) {
        GL11.glDisable(2929)
        GL11.glEnable(3042)
        GL11.glDepthMask(false)
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA)
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f)
        MC.textureManager.bindTexture(Identifier("epsilon", image))
        blit(x, y, 0.0f, 0.0f, width, height, width, height)
        GL11.glDepthMask(true)
        GL11.glDisable(3042)
        GL11.glEnable(2929)
    }

    fun drawHorizontalLine(matrixStack: MatrixStack, i: Int, j: Int, k: Int, l: Int) {
        var i = i
        var j = j
        if (j < i) {
            val m = i
            i = j
            j = m
        }
        drawRect(matrixStack, i, k, j + 1, k + 1, l)
    }

    fun drawVerticalLine(matrixStack: MatrixStack, i: Int, j: Int, k: Int, l: Int) {
        var j = j
        var k = k
        if (k < j) {
            val m = j
            j = k
            k = m
        }
        drawRect(matrixStack, i, j + 1, i + 1, k, l)
    }

    private fun fill(matrix4f: Matrix4f, x1: Double, y1: Double, x2: Double, y2: Double, color: Int) {
        var x1 = x1
        var y1 = y1
        var x2 = x2
        var y2 = y2
        var j: Double
        if (x1 < x2) {
            j = x1
            x1 = x2
            x2 = j
        }
        if (y1 < y2) {
            j = y1
            y1 = y2
            y2 = j
        }
        val f = (color shr 24 and 255).toFloat() / 255.0f
        val g = (color shr 16 and 255).toFloat() / 255.0f
        val h = (color shr 8 and 255).toFloat() / 255.0f
        val k = (color and 255).toFloat() / 255.0f
        val bufferBuilder = Tessellator.getInstance().buffer
        RenderSystem.enableBlend()
        RenderSystem.disableTexture()
        RenderSystem.defaultBlendFunc()
        bufferBuilder.begin(7, VertexFormats.POSITION_COLOR)
        bufferBuilder.vertex(matrix4f, x1.toFloat(), y2.toFloat(), 0.0f).color(g, h, k, f).next()
        bufferBuilder.vertex(matrix4f, x2.toFloat(), y2.toFloat(), 0.0f).color(g, h, k, f).next()
        bufferBuilder.vertex(matrix4f, x2.toFloat(), y1.toFloat(), 0.0f).color(g, h, k, f).next()
        bufferBuilder.vertex(matrix4f, x1.toFloat(), y1.toFloat(), 0.0f).color(g, h, k, f).next()
        bufferBuilder.end()
        BufferRenderer.draw(bufferBuilder)
        RenderSystem.enableTexture()
        RenderSystem.disableBlend()
    }

    private fun blit(x: Double, y: Double, u: Float, v: Float, width: Double, height: Double, texWidth: Double, texHeight: Double) {
        blit(x, y, width, height, u, v, width, height, texWidth, texHeight)
    }

    private fun blit(x: Double, y: Double, width: Double, height: Double, u: Float, v: Float, uWidth: Double, vHeight: Double, texWidth: Double, texHeight: Double) {
        innerBlit(x, x + width, y, y + height, 0.0, uWidth, vHeight, u, v, texWidth, texHeight)
    }

    private fun innerBlit(xStart: Double, xEnd: Double, yStart: Double, yEnd: Double, z: Double, width: Double, height: Double, u: Float, v: Float, texWidth: Double, texHeight: Double) {
        innerBlit(xStart, xEnd, yStart, yEnd, z, (u + 0.0f) / texWidth.toFloat(), (u + width.toFloat()) / texWidth.toFloat(), (v + 0.0f) / texHeight.toFloat(), (v + height.toFloat()) / texHeight.toFloat())
    }

    private fun innerBlit(xStart: Double, xEnd: Double, yStart: Double, yEnd: Double, z: Double, uStart: Float, uEnd: Float, vStart: Float, vEnd: Float) {
        val bufferBuilder = Tessellator.getInstance().buffer
        bufferBuilder.begin(7, VertexFormats.POSITION_TEXTURE)
        bufferBuilder.vertex(xStart, yEnd, z).texture(uStart, vEnd).next()
        bufferBuilder.vertex(xEnd, yEnd, z).texture(uEnd, vEnd).next()
        bufferBuilder.vertex(xEnd, yStart, z).texture(uEnd, vStart).next()
        bufferBuilder.vertex(xStart, yStart, z).texture(uStart, vStart).next()
        bufferBuilder.end()
        RenderSystem.enableAlphaTest()
        BufferRenderer.draw(bufferBuilder)
    }

    fun setColor(color: Color) {
        GL11.glColor4f(color.red / 255.0f, color.green / 255.0f, color.blue / 255.0f, color.alpha / 255.0f)
    }

    fun setColor(rgba: Int) {
        val r = rgba and 0xFF
        val g = rgba shr 8 and 0xFF
        val b = rgba shr 16 and 0xFF
        val a = rgba shr 24 and 0xFF
        GL11.glColor4b(r.toByte(), g.toByte(), b.toByte(), a.toByte())
    }

    fun toRGBA(c: Color): Int {
        return c.red or (c.green shl 8) or (c.blue shl 16) or (c.alpha shl 24)
    }

    fun isHovered(mouseX: Int, mouseY: Int, x: Int, y: Int, width: Int, height: Int): Boolean {
        return mouseX >= x && mouseX - width <= x && mouseY >= y && mouseY - height <= y
    }

    fun isHovered(mouseX: Double, mouseY: Double, x: Double, y: Double, width: Double, height: Double): Boolean {
        return mouseX >= x && mouseX - width <= x && mouseY >= y && mouseY - height <= y
    }

    fun isHovered(mouseX: Float, mouseY: Float, x: Float, y: Float, width: Float, height: Float): Boolean {
        return mouseX >= x && mouseX - width <= x && mouseY >= y && mouseY - height <= y
    }

    fun scissorBox(x: Int, y: Int, width: Int, height: Int) {
        scissor(x, y, x + width, y + height)
    }

    private fun scissor(startX: Int, startY: Int, endX: Int, endY: Int) {
        val width = endX - startX
        val height = endY - startY
        val bottomY: Int = scaledHeight - endY
        val factor: Double = MC.window.scaleFactor
        val scissorX = (startX * factor).toInt()
        val scissorY = (bottomY * factor).toInt()
        val scissorWidth = (width * factor).toInt()
        val scissorHeight = (height * factor).toInt()
        GL11.glScissor(scissorX, scissorY, scissorWidth, scissorHeight)
    }
}