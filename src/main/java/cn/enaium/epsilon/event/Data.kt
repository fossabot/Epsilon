package cn.enaium.epsilon.event

import java.lang.reflect.Method

class Data(val source: Any, val target: Method, val priority: EventPriority)