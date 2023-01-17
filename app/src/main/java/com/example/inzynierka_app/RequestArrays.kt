package com.example.inzynierka_app

import com.example.inzynierka_app.model.ParamsRead
import com.example.inzynierka_app.model.ReadDataRequest

enum class RequestArrays(val array: ArrayList<ReadDataRequest>) {
    STEPS(
        arrayListOf(
            ReadDataRequest(1, "2.0", "PlcProgram.Read", ParamsRead("\"DB100\".mb_step_1")),
            ReadDataRequest(2, "2.0", "PlcProgram.Read", ParamsRead("\"DB100\".mb_step_2")),
            ReadDataRequest(3, "2.0", "PlcProgram.Read", ParamsRead("\"DB100\".mb_step_3")),
            ReadDataRequest(4, "2.0", "PlcProgram.Read", ParamsRead("\"DB100\".mb_step_4")),
            ReadDataRequest(5, "2.0", "PlcProgram.Read", ParamsRead("\"DB100\".mb_step_5")),
            ReadDataRequest(6, "2.0", "PlcProgram.Read", ParamsRead("\"DB100\".mb_step_6")),
            ReadDataRequest(7, "2.0", "PlcProgram.Read", ParamsRead("\"DB100\".mb_step_7")),
            ReadDataRequest(8, "2.0", "PlcProgram.Read", ParamsRead("\"DB100\".mb_step_8"))
        )
    ),
    ERRORS(
        arrayListOf(
            ReadDataRequest(1, "2.0", "PlcProgram.Read", ParamsRead("\"DB10\".mb_error_HOR_left")),
            ReadDataRequest(2, "2.0", "PlcProgram.Read", ParamsRead("\"DB10\".mb_error_HOR_right")),
            ReadDataRequest(3, "2.0", "PlcProgram.Read", ParamsRead("\"DB10\".mb_error_VTK_up")),
            ReadDataRequest(4, "2.0", "PlcProgram.Read", ParamsRead("\"DB10\".mb_error_VTK_down")),
            ReadDataRequest(5, "2.0", "PlcProgram.Read", ParamsRead("\"DB10\".mb_error_GRP_open")),
            ReadDataRequest(6, "2.0", "PlcProgram.Read", ParamsRead("\"DB10\".mb_catch_error")),
            ReadDataRequest(7, "2.0", "PlcProgram.Read", ParamsRead("\"DB10\".mb_not_halt"))
        )
    )
}