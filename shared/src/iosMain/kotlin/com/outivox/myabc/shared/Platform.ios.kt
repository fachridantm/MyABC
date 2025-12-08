package com.outivox.myabc.shared

import platform.UIKit.UIDevice

actual fun platform() = UIDevice.currentDevice.systemName + " " + UIDevice.currentDevice.systemVersion