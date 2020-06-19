package cn.enaium.epsilon

import cn.enaium.epsilon.command.CommandManager
import cn.enaium.epsilon.config.ConfigManager
import cn.enaium.epsilon.event.EventManager
import cn.enaium.epsilon.func.FuncManager
import cn.enaium.epsilon.mixin.IMinecraftClientMixin
import cn.enaium.epsilon.plugin.PluginManager
import cn.enaium.epsilon.setting.SettingManager
import net.minecraft.client.MinecraftClient
import java.io.File


/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
object Epsilon {

    var NAME = "Epsilon"
    var VERSION = "pre10"
    var GAME = "1.16-rc1"
    var AUTHOR = "Enaium"
    var DIR = MinecraftClient.getInstance().runDirectory.toString() + "/" + NAME + "/"
    var MC: MinecraftClient = MinecraftClient.getInstance()

    lateinit var eventManager: EventManager
    lateinit var funcManager: FuncManager
    lateinit var settingManager: SettingManager
    lateinit var commandManager: CommandManager
    lateinit var pluginManager: PluginManager
    lateinit var configManager: ConfigManager

    fun run() {
        File(DIR).mkdir()
        eventManager = EventManager()
        funcManager = FuncManager()
        settingManager = SettingManager()
        commandManager = CommandManager()
        pluginManager = PluginManager()
        configManager = ConfigManager()
        configManager.load()
    }

    fun stop() {
        configManager.save()
    }

}