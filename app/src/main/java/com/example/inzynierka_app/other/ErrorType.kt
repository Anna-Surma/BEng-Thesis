package com.example.inzynierka_app.other

import com.example.inzynierka_app.R

enum class ErrorType(val errorName: Int, val errorDesc: Int) {
    LOGIN(0, R.string.incorrect_password_or_email_error),
    NETWORK(R.string.network_connection_error_name, R.string.network_connection_error_desc),
    HOR_LEFT(R.string.hor_left_sensor_error_name, R.string.hor_left_sensor_error_desc),
    HOR_RIGHT(R.string.hor_right_sensor_error_name, R.string.hor_right_sensor_error_desc),
    VTK_TOP(R.string.vtk_top_sensor_error_name, R.string.vtk_top_sensor_error_desc),
    VTK_DOWN(R.string.vtk_down_sensor_error_name, R.string.vtk_down_sensor_error_desc),
    GRIPPER(R.string.gripper_sensor_error_name, R.string.gripper_sensor_error_desc),
    PUT(R.string.put_error_name, R.string.put_error_desc),
    NOT_HALT(R.string.not_halt_error_name, R.string.not_hat_error_desc)
}