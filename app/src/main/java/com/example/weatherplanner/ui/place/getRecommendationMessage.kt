package com.example.weatherplanner.ui.place

import com.example.weatherplanner.data.model.Place
import com.example.weatherplanner.data.model.WeatherApiResponse

fun getRecommendationMessage(place: Place, weatherInfo: WeatherApiResponse?): String {
    val weather = weatherInfo?.current?.condition?.text?.lowercase() ?: ""
    val name = place.place_name
    val cat = place.category_name.lowercase()
    val keywordTemplates = listOf(
        "보드게임" to "%s에서 친구들과 보드게임 한 판 어떠세요?",
        "노래방" to "%s에서 스트레스 날려보는 건 어때요?",
        "분식" to "%s에서 따끈한 분식으로 출출함을 달래보세요.",
        "일식" to "%s에서 일본 가정식 먹으며 하루를 마무리해보세요.",
        "전시" to "%s에서 예술 감상으로 하루를 채워보세요.",
        "테마파크" to "%s에서 신나는 어트랙션을 즐겨보세요!",
        "키즈카페" to "%s에서 아이들과 즐거운 시간 보내보세요."
    )
    for ((keyword, template) in keywordTemplates) {
        if (cat.contains(keyword) || name.contains(keyword)) {
            return String.format(template, name)
        }
    }
    return when {

        // ☔ 비 오는 날
        weather.contains("rain") && place.category_group_code == "CE7" ->
            "비 오는 날엔 아늑한 카페에서 빗소리 들으며 커피 한 잔 어떠세요?"
        weather.contains("rain") && place.category_group_code == "FD6" ->
            "비 오는 날엔 따뜻한 음식점에서 기분 전환해보세요!"
        weather.contains("rain") && place.category_group_code == "AT4" ->
            "비가 내려도, 실내 관광지에서 특별한 추억을 쌓아보세요."
        weather.contains("rain") && place.category_group_code == "CT1" ->
            "비 오는 날엔 전시관이나 박물관에서 여유로운 시간을 보내세요."

        // ❄️ 눈 오는 날
        weather.contains("snow") && place.category_group_code == "CE7" ->
            "눈 내리는 날엔 창밖 풍경 좋은 카페에서 따뜻한 음료를 즐겨보세요."
        weather.contains("snow") && place.category_group_code == "FD6" ->
            "눈 오는 날엔 가까운 맛집에서 따뜻한 식사 어떠세요?"
        weather.contains("snow") && place.category_group_code == "AT4" ->
            "눈 오는 날, 실내 관광지에서 포근한 시간을 보내세요."
        weather.contains("snow") && place.category_group_code == "CT1" ->
            "눈이 오는 날엔 조용한 문화시설에서 감성 충전해보세요."

        // ☀️ 맑은 날
        (weather.contains("clear") || weather.contains("sunny")) && place.category_group_code == "CE7" ->
            "맑은 날엔 햇살 가득한 카페 테라스에서 여유를 즐겨보세요."
        (weather.contains("clear") || weather.contains("sunny")) && place.category_group_code == "FD6" ->
            "좋은 날씨엔 야외 좌석이 있는 음식점에서 식사해보세요!"
        (weather.contains("clear") || weather.contains("sunny")) && place.category_group_code == "AT4" ->
            "산책하기 좋은 날씨! 가까운 명소에서 바람을 느껴보세요."
        (weather.contains("clear") || weather.contains("sunny")) && place.category_group_code == "CT1" ->
            "맑은 날엔 문화공간에서 새로운 전시를 감상해보세요."

        // 🌥️ 흐린 날
        (weather.contains("cloud") || weather.contains("overcast")) && place.category_group_code == "CE7" ->
            "흐린 날엔 카페에서 여유롭게 책을 읽어보는 건 어떨까요?"
        (weather.contains("cloud") || weather.contains("overcast")) && place.category_group_code == "FD6" ->
            "구름 낀 날엔 분위기 좋은 식당에서 특별한 한 끼를 즐겨보세요."
        (weather.contains("cloud") || weather.contains("overcast")) && place.category_group_code == "AT4" ->
            "흐린 날씨, 조용한 공원 산책도 색다른 기분을 줄 거예요."
        (weather.contains("cloud") || weather.contains("overcast")) && place.category_group_code == "CT1" ->
            "잔잔한 하루, 전시관이나 미술관에서 새로운 영감을 받아보세요."

        // 🔥 더운 날 (기온 28도 이상)
        (weatherInfo?.current?.temp_c ?: 0.0) >= 28 && place.category_group_code == "CE7" ->
            "더운 날엔 시원한 아이스 커피와 함께 카페에서 휴식하세요."
        (weatherInfo?.current?.temp_c ?: 0.0) >= 28 && place.category_group_code == "FD6" ->
            "무더운 날씨엔 시원한 음식점에서 몸과 마음을 식혀보세요."
        (weatherInfo?.current?.temp_c ?: 0.0) >= 28 && place.category_group_code == "AT4" ->
            "더운 날엔 실내 관광지에서 즐거운 하루를 보내보세요."
        (weatherInfo?.current?.temp_c ?: 0.0) >= 28 && place.category_group_code == "CT1" ->
            "더울 땐 시원한 문화시설에서 여유를 즐기세요."

        // 🧥 추운 날 (기온 5도 이하)
        (weatherInfo?.current?.temp_c ?: 99.0) <= 5 && place.category_group_code == "CE7" ->
            "쌀쌀한 날엔 따뜻한 카페에서 힐링하세요."
        (weatherInfo?.current?.temp_c ?: 99.0) <= 5 && place.category_group_code == "FD6" ->
            "추운 날씨엔 뜨끈한 음식점에서 식사로 몸을 녹여보세요."
        (weatherInfo?.current?.temp_c ?: 99.0) <= 5 && place.category_group_code == "AT4" ->
            "추운 날엔 실내 관광지에서 오붓한 시간을 보내세요."
        (weatherInfo?.current?.temp_c ?: 99.0) <= 5 && place.category_group_code == "CT1" ->
            "쌀쌀한 하루, 따뜻한 문화시설에서 감성을 채워보세요."

        // 기타/기본 메시지
        place.category_group_code == "CE7" ->
            "오늘 하루, 카페에서 여유로운 시간을 보내보세요."
        place.category_group_code == "FD6" ->
            "맛집 탐방으로 특별한 하루를 만들어보세요!"
        place.category_group_code == "AT4" ->
            "가까운 관광지에서 일상에 작은 변화를 줘보세요."
        place.category_group_code == "CT1" ->
            "문화시설에서 새로운 영감을 얻어보세요."
        else -> "오늘 날씨에 추천드리는 장소예요!"
    }
    
    return "오늘은 ${name}에서 특별한 시간을 보내보세요!"
}