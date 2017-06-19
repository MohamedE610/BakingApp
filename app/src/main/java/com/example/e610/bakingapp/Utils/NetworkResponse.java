package com.example.e610.bakingapp.Utils;


public interface NetworkResponse {


    void OnSuccess(String JsonData);
    void OnUpdate(boolean IsDataReceived);
}
