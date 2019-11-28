/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.widgets.factory

import dev.icerock.moko.widgets.TextWidget
import dev.icerock.moko.widgets.core.ViewBundle
import dev.icerock.moko.widgets.core.ViewFactoryContext
import dev.icerock.moko.widgets.style.view.TextAlignment
import dev.icerock.moko.widgets.style.view.WidgetSize
import dev.icerock.moko.widgets.utils.applyBackground
import dev.icerock.moko.widgets.utils.applyTextStyle
import dev.icerock.moko.widgets.utils.bind
import kotlinx.cinterop.readValue
import platform.CoreGraphics.CGRectZero
import platform.UIKit.NSTextAlignmentCenter
import platform.UIKit.NSTextAlignmentLeft
import platform.UIKit.NSTextAlignmentRight
import platform.UIKit.UILabel
import platform.UIKit.UILayoutConstraintAxisHorizontal
import platform.UIKit.UILayoutConstraintAxisVertical
import platform.UIKit.UILayoutPriorityDefaultHigh
import platform.UIKit.setContentCompressionResistancePriority
import platform.UIKit.translatesAutoresizingMaskIntoConstraints

actual class DefaultTextWidgetViewFactory actual constructor(
    style: Style
) : DefaultTextWidgetViewFactoryBase(style) {
    override fun <WS : WidgetSize> build(
        widget: TextWidget<out WidgetSize>,
        size: WS,
        viewFactoryContext: ViewFactoryContext
    ): ViewBundle<WS> {
        val label = UILabel(frame = CGRectZero.readValue()).apply {
            translatesAutoresizingMaskIntoConstraints = false
            applyBackground(style.background)

            setContentCompressionResistancePriority(
                UILayoutPriorityDefaultHigh,
                UILayoutConstraintAxisVertical
            )
            setContentCompressionResistancePriority(
                UILayoutPriorityDefaultHigh,
                UILayoutConstraintAxisHorizontal
            )

            numberOfLines = 0

            style.textStyle?.also { applyTextStyle(it) }

            when (style.textAlignment) {
                TextAlignment.LEFT -> textAlignment = NSTextAlignmentLeft
                TextAlignment.CENTER -> textAlignment = NSTextAlignmentCenter
                TextAlignment.RIGHT -> textAlignment = NSTextAlignmentRight
                null -> {
                }
            }
        }

        widget.text.bind { label.text = it.localized() }

        return ViewBundle(
            view = label,
            size = size,
            margins = style.margins
        )
    }
}
