package com.example.traficlight

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

class TrafficLightView(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {

    init {
        // Enables software rendering to allow shadow effects
        setLayerType(LAYER_TYPE_SOFTWARE, null)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Define dimensions
        val lightRadius = width / 6f
        val spacing = lightRadius * 2.5f
        val centerX = width / 2f
        val containerPadding = 50f

        // Draw the traffic light body
        drawTrafficLightBody(canvas, centerX, spacing, lightRadius, containerPadding)

        // Draw traffic light circles
        drawTrafficLightCircle(canvas, centerX, spacing, lightRadius, Color.RED)
        drawTrafficLightCircle(canvas, centerX, spacing * 2.5f, lightRadius, Color.YELLOW)
        drawTrafficLightCircle(canvas, centerX, spacing * 4f, lightRadius, Color.GREEN)
    }

    private fun drawTrafficLightBody(
        canvas: Canvas,
        centerX: Float,
        spacing: Float,
        lightRadius: Float,
        padding: Float
    ) {
        // Calculate the dimensions of the rectangle
        val rectWidth = lightRadius * 2.5f
        val rectHeight = spacing * 5f
        val left = centerX - rectWidth / 2
        val top = spacing / 3
        val right = centerX + rectWidth / 2
        val bottom = top + rectHeight

        // Paint for the traffic light body
        val bodyPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.DKGRAY
            style = Paint.Style.FILL
            shader = LinearGradient(
                left, top, right, bottom,
                Color.BLACK, Color.DKGRAY,
                Shader.TileMode.CLAMP
            )
        }

        // Draw the rounded rectangle for the body
        canvas.drawRoundRect(
            left - padding,
            top - padding,
            right + padding,
            bottom + padding,
            60f, 60f, bodyPaint
        )
    }

    private fun drawTrafficLightCircle(
        canvas: Canvas,
        centerX: Float,
        centerY: Float,
        lightRadius: Float,
        lightColor: Int
    ) {
        // Paint for the outer border (shadow effect)
        val borderPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.BLACK
            style = Paint.Style.FILL
            setShadowLayer(20f, 0f, 10f, Color.BLACK)
        }

        // Paint for the traffic light's colored circle
        val lightPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = lightColor
            style = Paint.Style.FILL
            shader = RadialGradient(
                centerX, centerY, lightRadius,
                Color.WHITE, lightColor,
                Shader.TileMode.CLAMP
            )
        }

        // Paint for the glass effect (reflection)
        val glassEffectPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
            color = Color.WHITE
            strokeWidth = 6f
            alpha = 60
        }

        // Draw the border (shadow effect)
        canvas.drawCircle(centerX, centerY, lightRadius + 20, borderPaint)

        // Draw the main traffic light circle
        canvas.drawCircle(centerX, centerY, lightRadius, lightPaint)

        // Draw the glass effect for reflection
        val ovalRect = RectF(
            centerX - lightRadius / 2,
            centerY - lightRadius / 1.5f,
            centerX + lightRadius / 2,
            centerY + lightRadius / 3
        )
        canvas.drawArc(ovalRect, -30f, 60f, false, glassEffectPaint)
    }
}
