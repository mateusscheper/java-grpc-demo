package com.example.demo.grpc;

import grpc.NewUserRequest;
import grpc.NewUserResponse;
import grpc.UserServiceGrpc;
import io.github.majusko.grpc.jwt.annotation.Allow;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;

@GRpcService
public class UserServiceImpl extends UserServiceGrpc.UserServiceImplBase {

    @Override
    @Allow(roles = {"ADMIN"})
    public void newUser(NewUserRequest request, StreamObserver<NewUserResponse> responseObserver) {
        System.out.println("I'm here");

        NewUserResponse response = NewUserResponse.newBuilder()
                .setMessage(request.getName() + ", " + request.getEmail())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
