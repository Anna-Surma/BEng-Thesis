package com.example.inzynierka_app.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.inzynierka_app.GripperActivity
import com.example.inzynierka_app.api.ApiClient
import com.example.inzynierka_app.databinding.FragmentLoginBinding
import com.example.inzynierka_app.model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient
    private lateinit var param: Params
    private lateinit var read_param: ParamsVar
    private lateinit var write_param: ParamsWriteVar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root


        apiClient = ApiClient()
        sessionManager = SessionManager(requireActivity())
        apiClient.initialize(requireContext())
        //requireActivity()
        //requireContext()

        param = Params("json", "json")
        read_param = ParamsVar("\"Data\".Random_Int")
        write_param = ParamsWriteVar("\"Data\".Random_Dint", 10)

        //TODO move to ViewModel
        loginUser(param)


        binding.getButton.setOnClickListener {
                //activity?.onBackPressed()
                apiClient.getService(requireContext())?.readData(ReadDataRequest("2.0", "PlcProgram.Read", 1, read_param))
                    ?.enqueue(object : Callback<DataResponse>{
                        override fun onFailure(call: Call<DataResponse>, t: Throwable) {
                            Log.i("LoginActivity", t.message.toString())
                        }

                        override fun onResponse(call: Call<DataResponse>, response: Response<DataResponse>) {
                            if(response.isSuccessful) {
                                val responseBody = response.body()
                                Log.i("LoginActivity", "UDAŁO SIE READ_DATA")

                                binding.PLCData.text = responseBody?.error?.message
                            }
                            else {
                                Log.i("LoginActivity", "NIE UDAŁO SIE PRZY UDANIU SIE READ_DATA")
                            }
                        }
                    })
        }

        binding.loginButton.setOnClickListener {
            val intent = Intent(activity, GripperActivity::class.java)
            startActivity(intent)

        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun loginUser(param: Params) {
        apiClient.getService(requireContext())?.login(LoginRequest(id = 0, jsonrpc = "2.0", method = "Api.Login", param))
            ?.enqueue(object : Callback<LoginResponse> {
                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {

                    Log.i("LoginActivity", t.message.toString())
                }

                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    val loginResponse = response.body()
                    if (response.isSuccessful) {
                        sessionManager.saveAuthToken(loginResponse?.result!!.token)
                        Log.i("LoginActivity", "UDAŁO SIE")
                        Log.i("LoginActivity", loginResponse?.result!!.token)
                        //fetchPosts()
                        //  read_data()
                    } else {
                        Log.i("LoginActivity", "NIE UDAŁO SIE PRZY UDANIU SIE")
                    }
                }
            })
    }
}
    //ReadDataRequest("2.0", "PlcProgram.Read", 1, read_param)

//    private fun fetchPosts() {
//        apiClient.getApiService(requireActivity()).fetchData()
//            .enqueue(object : Callback<DataResponse> {
//                override fun onFailure(call: Call<DataResponse>, t: Throwable) {
//                    Log.i("LoginActivity", "NIE UDAŁO SIE FETCH")
//                }
//
//                override fun onResponse(call: Call<DataResponse>, response: Response<DataResponse>) {
//                    Log.i("LoginActivity", "UDAŁO SIE FETCH")
//                    //read_data()
//                    val responseBody = response.body()
//                    if (response.isSuccessful) {
//                        if (responseBody != null) {
//                            binding.PLCData.text = responseBody.error.message
//                        }
//                    }
//                }
//            })
//    }

//    private fun read_data(){
//        apiClient.getApiService(requireActivity()).read_data(ReadDataRequest("2.0", "PlcProgram.Read", 1, read_param))
//            .enqueue(object : Callback<DataResponse> {
//                override fun onFailure(call: Call<DataResponse>, t: Throwable) {
//
//                    Log.i("LoginActivity", t.message.toString())
//                }
//
//                override fun onResponse(call: Call<DataResponse>, response: Response<DataResponse>) {
//                    val responseBody = response.body()
//
//                    if (responseBody?.jsonrpc == "2.0") {
//                        Log.i("LoginActivity", "UDAŁO SIE READ_DATA")
//                        binding.PLCData.text = responseBody.error.message
//                    } else {
//                        Log.i("LoginActivity", "NIE UDAŁO SIE PRZY UDANIU SIE READ_DATA")
//                    }
//                }
//            })
//    }

//        private fun write_data(){
//        apiClient.getApiService(requireActivity()).write_data(WriteDataRequest(1, "2.0", "PlcProgram.Write", write_param))
//            .enqueue(object : Callback<DataResponse> {
//                override fun onFailure(call: Call<DataResponse>, t: Throwable) {
//
//                    Log.i("LoginActivity", t.message.toString())
//                }
//
//                override fun onResponse(call: Call<DataResponse>, response: Response<DataResponse>) {
//                    val responseBody = response.body()
//
//                    if (responseBody?.jsonrpc == "2.0") {
//                        Log.i("LoginActivity", "UDAŁO SIE WRITE_DATA")
//                        binding.writeData.text = responseBody.error.message
//                    } else {
//                        Log.i("LoginActivity", "NIE UDAŁO SIE PRZY UDANIU SIE WRITE_DATA")
//                    }
//                }
//            })
//    }
