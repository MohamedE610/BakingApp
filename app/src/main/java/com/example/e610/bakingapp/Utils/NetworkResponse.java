package com.example.e610.bakingapp.Utils;


public interface NetworkResponse {


    void OnSuccess(String JsonData);
    void OnFailure(boolean Failure);
}
