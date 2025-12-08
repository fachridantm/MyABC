package com.outivox.myabc.authentication.util

import platform.UIKit.UIDevice

actual fun platform() = PlatformComponent(UIDevice.currentDevice.systemName + " " + UIDevice.currentDevice.systemVersion)
