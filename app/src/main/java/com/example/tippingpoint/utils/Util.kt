package com.example.tippingpoint.utils

fun getTotalTip(totalBill: Double, tipPercentage: Double): Double {
    val percentage = 100.00
    if (totalBill.toString().isNotEmpty() && totalBill >= 1) {
        return (totalBill * tipPercentage / percentage)
    }
    return 0.0
}

fun getTotalPerPerson(totalBill: Double, numOfPeople: Int, tipPercentage: Double): Double {
    val totalWithTip = getTotalTip(totalBill = totalBill, tipPercentage = tipPercentage) + totalBill
    return totalWithTip / numOfPeople.toDouble()
}
