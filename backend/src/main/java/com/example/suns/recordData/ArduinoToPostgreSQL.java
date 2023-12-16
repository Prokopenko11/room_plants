package com.example.suns.recordData;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class ArduinoToPostgreSQL {

    private static SerialPort serialPort;

    public static void main(String[] args) {
        serialPort = SerialPort.getCommPort("/dev/ttyACM0"); // Укажите нужный порт

        try {
            // Открываем последовательный порт	</dependencies>
            serialPort.openPort();

            // Настройка последовательного порта
            serialPort.setBaudRate(9600);
            serialPort.setNumDataBits(8);
            serialPort.setNumStopBits(1);
            serialPort.setParity(SerialPort.NO_PARITY);

            SerialPortDataListener dataListener = new MySerialPortDataListener(serialPort);
            serialPort.addDataListener(dataListener);

            // Задержка для тестирования (замените на бесконечный цикл или другую логику)
            while (true);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (serialPort != null && serialPort.isOpen()) {
                serialPort.closePort();
            }
        }

    }

    private static class MySerialPortDataListener implements SerialPortDataListener {
        private SerialPort serialPort;
        private StringBuilder receivedData = new StringBuilder();

        public MySerialPortDataListener( SerialPort serialPort) {
            this.serialPort = serialPort;
        }

        @Override
        public int getListeningEvents() {
            return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
        }

        @Override
        public void serialEvent(SerialPortEvent event) {
            if (event.getEventType() == SerialPort.LISTENING_EVENT_DATA_AVAILABLE) {
                byte[] newData = new byte[serialPort.bytesAvailable()];
                serialPort.readBytes(newData, newData.length);

                String newDataString = new String(newData);

                // Объединяем новые данные с предыдущими
                receivedData.append(newDataString);

                // Проверяем, есть ли в полученных данных символ новой строки
                if (receivedData.indexOf("\n") != -1) {
                    // Разбиваем данные по символу новой строки
                    String[] lines = receivedData.toString().split("\n");

                    // Перебираем строки (если их несколько)
                    for (String line : lines) {
                        // Пропускаем пустые строки
                        if (!line.trim().isEmpty()) {
                            // Выводим данные на консоль
                            System.out.println("Latest data: " + line);
                            // Вставка в базу данных
                            if (line.length() == 6) {
                                try {
                                    insertDataIntoDatabase(line);
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }
                    }
                    // Очищаем буфер полученных данных
                    receivedData.setLength(0);
                }
            }
        }
    }

    private static void insertDataIntoDatabase(String data) throws SQLException {
        Calendar calendar = new GregorianCalendar();
        int yearNow = calendar.get(Calendar.YEAR);
        int monthNow = calendar.get(Calendar.MONTH) + 1;
        int dayOfMonthNow = calendar.get(Calendar.DAY_OF_MONTH);
        int dayOfWeekNow = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        int hourNow = calendar.get(Calendar.HOUR_OF_DAY);
        int minNow = calendar.get(Calendar.MINUTE);

        WorkWithDB.updateCurrentTemperature(hourNow,
                minNow,
                yearNow,
                monthNow,
                dayOfMonthNow,
                data);

        if (hourNow == 0) {
            WorkWithDB.deleteTodayData();
            WorkWithDB.insertTodayData(hourNow, data);
        } else if (!WorkWithDB.isExistNote(hourNow)) {
            WorkWithDB.insertTodayData(hourNow, data);

            if (hourNow == 23 && minNow <= 10
                    && !WorkWithDB.isExistWeekNote(yearNow, monthNow, dayOfMonthNow)) {
                WorkWithDB.insertLastWeekData(
                        yearNow,
                        monthNow,
                        dayOfMonthNow,
                        dayOfWeekNow);
            }
        }
    }
}
