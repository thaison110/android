package com.sonnv.kidsonline.util

enum class Gender(val type: Int, val gender: String) {
    FEMALE(0, "Nữ"),
    MALE(1, "Nam"),
    OTHER(2, "Khác")
}

enum class DayOfWeek(val dayOfWeek: Int, val dayOfWeekE:String, val value: String) {
    CN(1, "CN","Chủ Nhật"),
    T2(2, "Th 2","Thứ Hai"),
    T3(3, "Th 3","Thứ Ba"),
    T4(4, "Th 4","Thứ Tư"),
    T5(5, "Th 5","Thứ Năm"),
    T6(6, "Th 6","Thứ Sáu"),
    T7(7, "Th 7","Thứ Bảy"),
}

enum class RelationShip(val key: Int, val value: String) {
    ME(0, "Mẹ"),
    BO(1, "Bố"),
    ONG(2, "Ông"),
    BA(3, "Bà"),
    CO(4, "Cô"),
    DI(5, "Dì"),
    CHU(6, "Chú"),
    BAC(7, "Bác"),
    ANH(8, "Anh"),
    CHI(9, "Chị")
}

enum class DonveType(val value: String) {
    DUNG_GIO("Đúng giờ"),
    DON_SOM("Đón sớm"),
    DON_MUON("Đón muộn")
}

enum class NotifyType(val type: Int, val value: String) {
    HD_DON_TRA(1, "Hoạt động đón trả"),
    DUYET_DON_PHEP(2, "Duyệt đón phép"),
    DUYET_DON_DAN_THUOC(3, "Duyệt đơn dặn thuốc"),
    DUYET_DON_DON_VE(4, "Duyệt đơn đón về"),
    TIN_TUC(5, "Tin tức")
}