package com.example.inzynierka_app

import com.example.inzynierka_app.model.ParamsWrite
import com.example.inzynierka_app.model.WriteDataRequest

enum class Steps (val id: Int, val text: Int, val drawable: Int, val request: ArrayList<WriteDataRequest>) {
    STEP1(1, R.string.step_1, R.drawable.step_one, (arrayListOf(
        WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"DB100\".mb_step_2", false)),
        WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"DB100\".mb_step_3", false)),
        WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"DB100\".mb_step_4", false)),
        WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"DB100\".mb_step_5", false)),
        WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"DB100\".mb_step_6", false)),
        WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"DB100\".mb_step_7", false)),
        WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"DB100\".mb_step_8", false)),
        WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"DB100\".mb_step_1", true))
    ))),
    STEP2(2, R.string.step_2, R.drawable.right_down_open, (arrayListOf(
        WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"DB100\".mb_step_1", false)),
        WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"DB100\".mb_step_3", false)),
        WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"DB100\".mb_step_4", false)),
        WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"DB100\".mb_step_5", false)),
        WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"DB100\".mb_step_6", false)),
        WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"DB100\".mb_step_7", false)),
        WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"DB100\".mb_step_8", false)),
        WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"DB100\".mb_step_2", true))
    ))),
    STEP3(3, R.string.step_3, R.drawable.right_down_close, (arrayListOf(
        WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"DB100\".mb_step_1", false)),
        WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"DB100\".mb_step_2", false)),
        WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"DB100\".mb_step_4", false)),
        WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"DB100\".mb_step_5", false)),
        WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"DB100\".mb_step_6", false)),
        WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"DB100\".mb_step_7", false)),
        WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"DB100\".mb_step_8", false)),
        WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"DB100\".mb_step_3", true))
    ))),
    STEP4(4, R.string.step_4, R.drawable.right_up_close, (arrayListOf(
        WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"DB100\".mb_step_1", false)),
        WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"DB100\".mb_step_2", false)),
        WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"DB100\".mb_step_3", false)),
        WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"DB100\".mb_step_5", false)),
        WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"DB100\".mb_step_6", false)),
        WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"DB100\".mb_step_7", false)),
        WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"DB100\".mb_step_8", false)),
        WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"DB100\".mb_step_4", true))
    ))),
    STEP5(5, R.string.step_5, R.drawable.left_up_close, (arrayListOf(
        WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"DB100\".mb_step_1", false)),
        WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"DB100\".mb_step_2", false)),
        WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"DB100\".mb_step_3", false)),
        WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"DB100\".mb_step_4", false)),
        WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"DB100\".mb_step_6", false)),
        WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"DB100\".mb_step_7", false)),
        WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"DB100\".mb_step_8", false)),
        WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"DB100\".mb_step_5", true))
    ))),
    STEP6(6, R.string.step_6, R.drawable.left_down_close, (arrayListOf(
        WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"DB100\".mb_step_1", false)),
        WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"DB100\".mb_step_2", false)),
        WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"DB100\".mb_step_3", false)),
        WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"DB100\".mb_step_4", false)),
        WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"DB100\".mb_step_5", false)),
        WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"DB100\".mb_step_7", false)),
        WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"DB100\".mb_step_8", false)),
        WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"DB100\".mb_step_6", true))
    ))),
    STEP7(7, R.string.step_7, R.drawable.left_down_open, (arrayListOf(
        WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"DB100\".mb_step_1", false)),
        WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"DB100\".mb_step_2", false)),
        WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"DB100\".mb_step_3", false)),
        WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"DB100\".mb_step_4", false)),
        WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"DB100\".mb_step_5", false)),
        WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"DB100\".mb_step_6", false)),
        WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"DB100\".mb_step_8", false)),
        WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"DB100\".mb_step_7", true))
    ))),
    STEP8(8, R.string.step_8, R.drawable.left_up_open, (arrayListOf(
        WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"DB100\".mb_step_1", false)),
        WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"DB100\".mb_step_2", false)),
        WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"DB100\".mb_step_3", false)),
        WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"DB100\".mb_step_4", false)),
        WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"DB100\".mb_step_5", false)),
        WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"DB100\".mb_step_6", false)),
        WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"DB100\".mb_step_7", false)),
        WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"DB100\".mb_step_8", true))
    )))
}
