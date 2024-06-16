package com.example.lavarapido.domain.entities.client;

public class CPF {

    private String cpf;

    public CPF(String cpf) {
        this.cpf = cpf;
    }

    public String getCpf() {
        return cpf;
    }

    @Override
    public String toString() {
        return cpf;
    }

}
