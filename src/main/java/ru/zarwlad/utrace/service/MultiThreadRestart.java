package ru.zarwlad.utrace.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.zarwlad.utrace.util.client.UtraceClient;

import java.io.IOException;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MultiThreadRestart implements Runnable{
    List<String> strings;

    @Override
    public void run() {
        for (String string : strings) {
            try {
                UtraceClient.getMsgResetErrors(string);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
