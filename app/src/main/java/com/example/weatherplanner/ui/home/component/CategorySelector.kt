package com.example.weatherplanner.ui.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// 순서를 보장하는 카테고리 맵
val categoryMap = linkedMapOf(
    "ALL" to "전체",
    "FD6" to "음식점",
    "CT1" to "문화시설",
    "PK6" to "주차장",
    "AD5" to "숙박",
    "CS2" to "편의점",
    "OL7" to "주유소",
    "PM9" to "약국",
    "BK9" to "은행"
)

@Composable
fun CategorySelector(
    selectedCategory: String,
    onCategorySelected: (String) -> Unit,
    categoryOptions: Map<String, String>
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(bottom = 12.dp)
    ) {
        items(categoryOptions.entries.toList()) { (code, label) ->
            val isSelected = selectedCategory == code
            Button(
                onClick = { onCategorySelected(code) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
                    contentColor = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                ),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)
            ) {
                Text(text = label, fontSize = 14.sp)
            }
        }
    }

    // 카테고리 아래 여백
    Spacer(modifier = Modifier.height(8.dp))
}