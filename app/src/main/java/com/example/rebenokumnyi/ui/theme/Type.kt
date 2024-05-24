package com.example.rebenokumnyi.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.rebenokumnyi.R

// Set of Material typography styles to start with
val appTypography = Typography(
    titleSmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.kanit)),
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 12.sp,
        letterSpacing = 0.sp
    ),
    bodySmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.kanit)),
        fontWeight = FontWeight.Normal,
        fontSize = 10.sp,
        lineHeight = 10.sp,
        letterSpacing = 0.sp
    ),
    displaySmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.kanit)),
        fontWeight = FontWeight.Normal,
        fontSize = 8.sp,
        lineHeight = 8.sp,
        letterSpacing = 0.sp
    ),
    bodyLarge = TextStyle( //OnContainer font
        fontFamily = FontFamily(Font(R.font.kanit)),
        fontWeight = FontWeight.Normal,
        fontSize = 13.sp,
        lineHeight = 13.sp,
        letterSpacing = 0.0.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.kanit)),
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 12.sp,
        letterSpacing = 0.0.sp
    ),
    titleLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.kanit)),
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        lineHeight = 18.sp,
        letterSpacing = 0.sp
    ),
    titleMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.kanit)),
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.kanit)),
        fontWeight = FontWeight.Medium,
        fontSize = 10.sp,
        lineHeight = 10.sp,
        letterSpacing = 0.sp
    ), labelMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.kanit)),
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 12.sp,
        letterSpacing = 0.sp
    )
    , labelLarge = TextStyle( //Buttons..
        fontFamily = FontFamily(Font(R.font.kanit)),
        fontWeight = FontWeight.Medium,
        fontSize = 13.sp,
        lineHeight = 13.sp,
        letterSpacing = 0.sp
    )
)