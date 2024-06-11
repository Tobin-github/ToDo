package top.tobin.shared.model

//{"success":true,"data":{
// "date":"20220406",
// "title":"正在求偶的凤头卡拉鹰，美国德克萨斯州 (© Alan Murphy/Minden Pictures)",
// "url":"https://cn.bing.com/th?id=OHR.NorthernCaracara_ZH-CN9538371843_1920x1080.jpg"}
// }

data class BingResult(
    val success: Boolean,
    val data: BingModel
)

data class BingModel(
    val date: String,
    val title: String,
    val url: String
)
