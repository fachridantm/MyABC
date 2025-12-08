package com.outivox.myabc

import platform.UIKit.UIDevice

actual fun platform() = UIDevice.currentDevice.systemName + " " + UIDevice.currentDevice.systemVersion