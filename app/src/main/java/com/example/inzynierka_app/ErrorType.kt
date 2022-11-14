package com.example.inzynierka_app

enum class ErrorType (val errorName: Int, val errorDesc: Int){
    LOGIN(0, R.string.incorrect_password_or_email_error),
    NETWORK(R.string.network_connection_error_name, R.string.network_connection_error_desc),
    TOP_RIGHT(R.string.top_right_sensor_error_name, R.string.top_right_sensor_error_desc),
    TOP_LEFT(R.string.top_left_sensor_error_name, R.string.top_left_sensor_error_desc),
    LOWER_RIGHT(R.string.lower_right_sensor_error_name, R.string.lower_right_sensor_error_desc),
    LOWER_LEFT(R.string.lower_left_sensor_error_name, R.string.lower_left_sensor_error_desc),
    GRIPPER(R.string.gripper_sensor_error_name, R.string.gripper_sensor_error_desc)
}