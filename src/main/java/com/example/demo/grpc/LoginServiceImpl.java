package com.example.demo.grpc;

import com.example.demo.service.LoginService;
import grpc.LoginRequest;
import grpc.LoginResponse;
import grpc.LoginServiceGrpc;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;

@GRpcService
public class LoginServiceImpl extends LoginServiceGrpc.LoginServiceImplBase {

    private final LoginService loginService;

    public LoginServiceImpl(LoginService loginService) {
        this.loginService = loginService;
    }

    @Override
    public void login(LoginRequest request, StreamObserver<LoginResponse> responseObserver) {
        String token = loginService.login(request);

        if (token == null) {
            StatusRuntimeException statusRuntimeException = Status.INVALID_ARGUMENT.
                    withDescription("E-mail or password invalid.")
                    .asRuntimeException();
            responseObserver.onError(statusRuntimeException);
            throw statusRuntimeException;
        }

        LoginResponse response = LoginResponse.newBuilder().setMessage(token).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
