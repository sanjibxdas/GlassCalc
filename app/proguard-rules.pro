# Proguard rules for Liquid Glass Calculator
-keepattributes *Annotation*
-keepattributes SourceFile,LineNumberTable

# Keep Compose Runtime and Foundation
-keep class androidx.compose.** { *; }

# Keep App Models and Config
-keep class com.example.** { *; }
